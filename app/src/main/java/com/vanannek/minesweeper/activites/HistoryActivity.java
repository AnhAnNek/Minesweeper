package com.vanannek.minesweeper.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.adapters.HistoryAdapter;
import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.models.History;
import com.vanannek.minesweeper.utilities.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private AppCompatImageView image_back;
    private RecyclerView historyRecyclerView;
    private MyDatabaseHelper myDB;
    private List<History> itemsHistory;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        image_back = findViewById(R.id.image_back);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        image_back.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabaseHelper(this);
        initItemsHistory();
        historyAdapter = new HistoryAdapter(HistoryActivity.this, this, itemsHistory);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    public void initItemsHistory() {
        itemsHistory = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            // TODO
        } else {
            while (cursor.moveToNext()) {
                History history = new History();
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
            Collections.sort(itemsHistory, (obj1, obj2) -> obj1.date.compareTo(obj2.date));
        }
    }
}