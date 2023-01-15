package com.vanannek.minesweeper.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class HistoryActivity extends AppCompatActivity implements HistoryListener {

    private AppCompatImageView backImg, binImg;
    private RecyclerView historyRecyclerView;
    private ImageView emptyImg;
    private TextView emptyTxt;
    private MyDatabaseHelper myDB;
    private List<History> itemsHistory;
    private HistoryAdapter historyAdapter;
    private ConstraintLayout historyLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        backImg = findViewById(R.id.backImg);
        binImg = findViewById(R.id.binImg);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        emptyImg = findViewById(R.id.emptyImg);
        emptyTxt = findViewById(R.id.emptyTxt);
        historyLayout = findViewById(R.id.historyLayout);
        backImg.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
        binImg.setOnClickListener(v -> {
            if (itemsHistory.isEmpty()) {
                Toast.makeText(this, "Haven't element to delete!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            confirmDeleteAll();
        });

        myDB = new MyDatabaseHelper(this);
        initHistoryItems();
        historyAdapter = new HistoryAdapter(HistoryActivity.this, this, itemsHistory);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

        ItemTouchHelper.Callback callback = new HistoryTouchHelper(HistoryActivity.this, this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(historyRecyclerView);
    }

    private void confirmDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all !");
        builder.setMessage("Are you want to delete all history?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteAllData();
                reloadActivity();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    private void reloadActivity() {
        finish();
        startActivity(getIntent());
    }

    private void initHistoryItems() {
        itemsHistory = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor == null || cursor.getCount() == 0) {
            emptyImg.setVisibility(View.VISIBLE);
            emptyTxt.setVisibility(View.VISIBLE);
        } else {
            emptyImg.setVisibility(View.GONE);
            emptyTxt.setVisibility(View.GONE);
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
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        // we will delete and also we want to undo
        String name = itemsHistory.get(viewHolder.getAdapterPosition()).getClass().getName();

        // backup of removed item for undo
        final History deletedHistory = itemsHistory.get(viewHolder.getAdapterPosition());
        final int deletedIndex = viewHolder.getAdapterPosition();

        // remove the item from recyclerview
        historyAdapter.removeItem(viewHolder.getAdapterPosition());

        // showing snack bar for undo
        Snackbar snackbar = Snackbar.make(historyLayout, name + "removed...!", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", v -> {
            historyAdapter.restoreItem(deletedHistory, deletedIndex);
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();

        // let's run our project finally
    }
}