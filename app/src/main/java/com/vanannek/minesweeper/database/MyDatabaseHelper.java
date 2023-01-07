package com.vanannek.minesweeper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";

    private Context context;
    private static final String DATABASE_NAME = "Minesweeper.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_history";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_COMPLETION_TIME = "completion_time";
    private static final String COLUMN_CURRENT_DATE = "date";
    private static final String COLUMN_GAME_MODE = "game_mode";
    private static final String COLUMN_RESULT = "result";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_COMPLETION_TIME + " TEXT, " +
                        COLUMN_CURRENT_DATE + " TEXT, " +
                        COLUMN_GAME_MODE + " TEXT, " +
                        COLUMN_RESULT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean add(String completeTime, String currentDate, String gameMode, String result) {
        if (completeTime.equals("") || currentDate.equals("") ||
                gameMode.equals("") || result.equals("")) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COMPLETION_TIME, completeTime);
        cv.put(COLUMN_CURRENT_DATE, currentDate);
        cv.put(COLUMN_GAME_MODE, gameMode);
        cv.put(COLUMN_RESULT, result);
        if (db.insert(TABLE_NAME, null, cv) != -1) {
            Log.i(TAG, "Add successfully");
            return true;
        } else {
            Log.i(TAG, "Add error");
            return false;
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteOneRow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int flag = db.delete(TABLE_NAME, "_id = ?", new String[]{id});
        if (flag != -1) {
            Log.i(TAG, "Delete one row successful");
        } else {
            Log.i(TAG, "Delete one row error");
        }
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}