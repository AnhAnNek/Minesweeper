package com.vanannek.minesweeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.models.MainSound;
import com.vanannek.minesweeper.utilities.Utils;

public class SettingActivity extends AppCompatActivity {

    private AppCompatImageView imageBackImg;
    private Button historyBtn, mainSoundBtn, clickSoundBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        setText();
        setListeners();
    }

    private void init() {
        imageBackImg = findViewById(R.id.backImg);
        historyBtn = findViewById(R.id.historyBtn);
        mainSoundBtn = findViewById(R.id.mainSoundBtn);
        clickSoundBtn = findViewById(R.id.clickSoundBtn);
    }

    private void setText() {
        if (MainSound.getInstance().isTurnOn()) mainSoundBtn.setText(Utils.MAIN_SOUND_ON);
        else mainSoundBtn.setText(Utils.MAIN_SOUND_OFF);
        if (ClickSound.getInstance().isTurnOn()) clickSoundBtn.setText(Utils.CLICK_SOUND_ON);
        else clickSoundBtn.setText(Utils.CLICK_SOUND_OFF);
    }

    private void setListeners() {
        imageBackImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            startMainActivity();
        });
        historyBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            startHistoryActivity();
        });
        mainSoundBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            if (MainSound.getInstance().isTurnOn()) {
                MainSound.getInstance().setTurnOn(false);
                MainSound.getInstance().pause();
                mainSoundBtn.setText(Utils.MAIN_SOUND_OFF);
            } else {
                MainSound.getInstance().setTurnOn(true);
                MainSound.getInstance().start();
                mainSoundBtn.setText(Utils.MAIN_SOUND_ON);
            }
        });
        clickSoundBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            if (ClickSound.getInstance().isTurnOn()) {
                ClickSound.getInstance().setTurnOn(false);
                clickSoundBtn.setText(Utils.CLICK_SOUND_OFF);
            } else {
                ClickSound.getInstance().setTurnOn(true);
                clickSoundBtn.setText(Utils.CLICK_SOUND_ON);
            }
        });
    }

    public void startMainActivity() {
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void startHistoryActivity() {
        Intent intent = new Intent(SettingActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}