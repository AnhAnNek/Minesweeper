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

    public static final int aroundX[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    public static final int aroundY[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy HH:mm");

    public static String getCurrentDate() {
        return formatDate.format(new Date());
    }

    public static boolean areEquals(Drawable drawable1, Drawable drawable2) {
        return Objects.equals(drawable1.getConstantState(), drawable2.getConstantState());
    }
}
