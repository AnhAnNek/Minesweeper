package com.vanannek.minesweeper.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.vanannek.minesweeper.R;

public class MainSound {

    private static MainSound instance;
    private MediaPlayer mediaPlayer;
    private boolean turnOn;
    private boolean playing;

    private MainSound() {
        turnOn = true;
        playing = false;
    }

    public static MainSound getInstance() {
        if (instance == null) {
            synchronized (MainSound.class) {
                if (instance == null) {
                    instance = new MainSound();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.minecraft_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
    }

    public void start() {
        if (instance == null || !turnOn || playing) return;
        mediaPlayer.start();
        playing = true;
    }

    public void pause() {
        if (instance == null || turnOn || !playing) return;
        mediaPlayer.pause();
        playing = false;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}