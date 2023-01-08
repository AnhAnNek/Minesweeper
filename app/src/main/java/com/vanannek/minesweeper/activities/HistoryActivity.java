package com.vanannek.minesweeper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    private AppCompatImageView image_back;
    private RecyclerView historyRecyclerView;
    private ImageView empty_iv;
    private TextView empty_tv;
    private MyDatabaseHelper myDB;
    private List<History> itemsHistory;
    private HistoryAdapter historyAdapter;
    private ConstraintLayout historyLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        image_back = findViewById(R.id.image_back);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        empty_iv = findViewById(R.id.empty_iv);
        empty_tv = findViewById(R.id.empty_tv);
        historyLayout = findViewById(R.id.historyLayout);
        image_back.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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

    public void initHistoryItems() {
        itemsHistory = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor == null || cursor.getCount() == 0) {
            empty_iv.setVisibility(View.VISIBLE);
            empty_tv.setVisibility(View.VISIBLE);
        } else {
            empty_iv.setVisibility(View.GONE);
            empty_tv.setVisibility(View.GONE);
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