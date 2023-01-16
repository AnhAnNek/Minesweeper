package com.vanannek.minesweeper.models;

import android.content.Context;
import android.media.MediaPlayer;

import com.vanannek.minesweeper.R;

public class MusicPlayer {

    private static MusicPlayer instance;
    private MediaPlayer mediaPlayer;
    private boolean turnOn;
    private boolean playing;

    private MusicPlayer() {
        turnOn = true;
        playing = false;
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            synchronized (MusicPlayer.class) {
                if (instance == null) {
                    instance = new MusicPlayer();
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