package com.vanannek.minesweeper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.adapters.HistoryAdapter;
import com.vanannek.minesweeper.callbacks.HistoryTouchHelper;
import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.listeners.HistoryListener;
import com.vanannek.minesweeper.models.History;
import com.vanannek.minesweeper.utilities.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryDialog extends AppCompatDialogFragment implements HistoryListener {

    private static final String TAG = "HistoryDialog";

    private AppCompatImageView closeImg, binImg;
    private RecyclerView historyRecyclerView;
    private RelativeLayout emptyRelativeLayout, historyLayout;

    private MyDatabaseHelper myDB;
    private List<History> itemsHistory;
    private HistoryAdapter historyAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.history_dialog, null);
        builder.setView(view);
        initViews(view);
        setListeners();

        myDB = new MyDatabaseHelper(getActivity());
        showHistoryItems();
        return builder.create();
    }

    private void initViews(View view) {
        closeImg = view.findViewById(R.id.closeImg);
        binImg = view.findViewById(R.id.binImg);
        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        emptyRelativeLayout = view.findViewById(R.id.emptyRelativeLayout);
        historyLayout = view.findViewById(R.id.historyLayout);
    }

    private void setListeners() {
        closeImg.setOnClickListener(v -> {
            dismiss();
        });
        binImg.setOnClickListener(v -> {
            if (itemsHistory.isEmpty()) {
                Toast.makeText(getContext(), "Haven't element to delete!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            openDeleteAllDialog();
        });
    }

    private void showHistoryItems() {
        initHistoryItems();
        setEmptyRelativeLayout();
        setRecyclerView();
        setTouchHelperAttachToRecyclerView();
    }

    private void openDeleteAllDialog() {
        final GameDialog dialog = new GameDialog(getLayoutInflater());
        dialog.setTitle("Delete All!!!");
        dialog.setMessage("Are you want to delete all history?");
        dialog.setYesButton(v -> {
            myDB.deleteAllData();
            reloadHistory();
            dialog.dismiss();
        });
        dialog.setNoButton(v -> dialog.dismiss());
        dialog.create();
        dialog.show();
    }

    private void reloadHistory() {
        Log.i(TAG, String.valueOf(itemsHistory.size()));
        showHistoryItems();
    }

    private void initHistoryItems() {
        itemsHistory = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor == null || cursor.getCount() == 0) return;
        while (cursor.moveToNext()) {
            History history = new History();
            history.id = cursor.getString(0);
            history.completeTime = cursor.getString(1);
            try {
                history.date = Utils.formatDate.parse(cursor.getString(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            history.gameMode = cursor.getString(3);
            history.result = cursor.getString(4);
            itemsHistory.add(history);
        }
        Collections.sort(itemsHistory, (obj1, obj2) -> obj2.date.compareTo(obj1.date));
    }

    private void setRecyclerView() {
        historyAdapter = new HistoryAdapter(getActivity(), itemsHistory);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setEmptyRelativeLayout() {
        if (itemsHistory.isEmpty()) emptyRelativeLayout.setVisibility(View.VISIBLE);
        else emptyRelativeLayout.setVisibility(View.GONE);
    }

    private void setTouchHelperAttachToRecyclerView() {
        ItemTouchHelper.Callback callback = new HistoryTouchHelper(getActivity(), this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(historyRecyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        // backup of removed item for undo
        final History deletedHistory = itemsHistory.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        // remove the item from recyclerview
        historyAdapter.removeItem(viewHolder.getAdapterPosition());

        // showing snack bar for undo
        Snackbar snackbar = Snackbar.make(historyLayout,
                "You removed item...!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> {
            historyAdapter.restoreItem(deletedHistory, deletedIndex);
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();

        // let's run our project finally
    }
}