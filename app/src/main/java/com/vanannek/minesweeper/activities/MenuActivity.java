package com.vanannek.minesweeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.models.GameMode;
import com.vanannek.minesweeper.utilities.Utils;

public class MenuActivity extends AppCompatActivity {

    private Button easyModeBtn, normalModeBtn, difficultModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ClickSound.getInstance().init(this);

        init();
        setListeners();
    }

    private void init() {
        easyModeBtn = findViewById(R.id.easyModeBtn);
        normalModeBtn = findViewById(R.id.normalModeBtn);
        difficultModeBtn = findViewById(R.id.difficultModeBtn);
    }

    private void setListeners() {
        easyModeBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            startMainActivity(GameMode.Easy);
        });
        normalModeBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            startMainActivity(GameMode.Normal);
        });
        difficultModeBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            startMainActivity(GameMode.Difficult);
        });
    }

    private void startMainActivity(GameMode gameMode) {
        Intent intent = new Intent(this, MainActivity.class);
        Utils.gameMode = gameMode;
        startActivity(intent);
    }
}