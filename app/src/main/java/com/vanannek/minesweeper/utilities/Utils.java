package com.vanannek.minesweeper.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.vanannek.minesweeper.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class Utils {

    public static final int LOST = 99;
    public static final int WIN = 100;
    public static final int FLAG_CHANGE = 101;

    public static final int HOUR_PER_MILLISECOND = 3600000;
    public static final int MINUTE_PER_SECOND = 60;
    public static final int SECOND_PER_MILLISECOND = 1000;

    public static final String MUSIC_OFF = "No music";
    public static final String MUSIC_ON = "Music";
    public static final String CLICK_SOUND_OFF = "No click sound";
    public static final String CLICK_SOUND_ON = "Click sound";

    public static final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static String getCurrentDate() {
        return formatDate.format(new Date());
    }

    public static void makeButtonAnimation(Context context, Button myButton) {
        myButton.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_scale_in));
        myButton.setAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_scale_out));
    }

    public static boolean areEquals(Drawable drawable1, Drawable drawable2) {
        return Objects.equals(drawable1.getConstantState(), drawable2.getConstantState());
    }
}
