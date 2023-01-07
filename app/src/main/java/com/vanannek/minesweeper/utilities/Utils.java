package com.vanannek.minesweeper.utilities;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

public final class Utils {

    public static final int LOST = 99;
    public static final int WIN = 100;
    public static final int FLAG_CHANGE = 101;

    public static final int HOUR_PER_MILLISECOND = 3600000;
    public static final int MINUTE_PER_SECOND = 60;
    public static final int SECOND_PER_MILLISECOND = 1000;

    public static final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private Utils() {
    }

    public static String getCurrentDate() {
        return formatDate.format(new Date());
    }

    public static boolean areEquals(Drawable drawable1, Drawable drawable2) {
        return Objects.equals(drawable1.getConstantState(), drawable2.getConstantState());
    }
}
