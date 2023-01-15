package com.vanannek.minesweeper.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.vanannek.minesweeper.R;

public class ClickSound {

    private static ClickSound instance;
    private MediaPlayer mediaPlayer;
    private boolean turnOn;

    private ClickSound() {
    }

    public static ClickSound getInstance() {
        if (instance == null) {
            synchronized (ClickSound.class) {
                if (instance == null) {
                    instance = new ClickSound();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        turnOn = true;
        mediaPlayer = MediaPlayer.create(context, R.raw.sound_click);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.pause();
        });
    }

    public void play() {
        if (instance == null || !turnOn) return;
        mediaPlayer.start();
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
