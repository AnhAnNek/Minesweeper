package com.vanannek.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer {

    private TextView textView;
    private Timer timer;
    private TimerTask timerTask;
    private double time = 0.0;

    public CountUpTimer(Context context) {
        textView = (TextView) ((Activity)context).findViewById(R.id.timer_tv);
        textView.setText(formatTime(0, 0));
        timer = new Timer();
    }

    public void resetTapped() {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(textView.getContext());
        resetAlert.setTitle("Reset Timer?");
        resetAlert.setMessage("Are you sure you want to the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (timerTask != null) {
                    timerTask.cancel();
                    time = 0.0;
                    textView.setText(formatTime(0, 0));
                }

            }
        });

        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    public void startTapped() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                time++;
                textView.setText(getTimerText());
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public String getTimerText() {
//        int rounded = (int) Math.round(time) % ;
//        int seconds = ;
//        int minutes = ;
        return formatTime(0, 0);
    }

    private String formatTime(int seconds, int minutes) {
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void stopTapped() {
        timerTask.cancel();
    }
}
