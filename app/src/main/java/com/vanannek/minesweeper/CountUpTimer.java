package com.vanannek.minesweeper;

import android.os.SystemClock;
import android.widget.Chronometer;

import com.vanannek.minesweeper.utilities.Utils;

public class CountUpTimer {

    private static CountUpTimer instance = null;
    private Chronometer chronometer;
    private boolean running;

    private CountUpTimer() {
    }

    public static CountUpTimer getInstance() {
        if (instance == null) {
            instance = new CountUpTimer();
        }
        return instance;
    }

    public void setView(Chronometer chronometer) {
        this.chronometer = chronometer;
        this.chronometer.setFormat("%s");
        this.chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void start() {
        if (running) return;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        running = true;
    }

    public void stop() {
        if (!running) return;
        chronometer.stop();
        running = false;
    }

    public void reset() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        running = false;
    }

    public void restart() {
        reset();
        start();
        running = true;
    }

    public String getCurrentTime() {
        return formatTime(SystemClock.elapsedRealtime() - chronometer.getBase());
    }

    // milliseconds must less equals than HOURS_PER_MILLISECONDS
    public String formatTime(long milliseconds) {
        milliseconds %= Utils.HOUR_PER_MILLISECOND;
        int minutes = (int) ((milliseconds / Utils.SECOND_PER_MILLISECOND) / Utils.MINUTE_PER_SECOND);
        int seconds = (int) ((milliseconds / Utils.SECOND_PER_MILLISECOND) % Utils.MINUTE_PER_SECOND);
        return String.format("%02d:%02d", minutes, seconds);
    }
}