package com.vanannek.minesweeper.utilities;

import android.graphics.drawable.Drawable;

import java.util.Objects;

public final class Utils {

    public static final int HOUR_PER_MILLISECOND = 3600000;
    public static final int MINUTE_PER_SECOND = 60;
    public static final int SECOND_PER_MILLISECOND = 1000;

    private Utils() {
    }

    public static boolean areEquals(Drawable drawable1, Drawable drawable2) {
        return Objects.equals(drawable1.getConstantState(), drawable2.getConstantState());
    }
}
