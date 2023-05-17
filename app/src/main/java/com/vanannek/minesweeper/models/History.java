package com.vanannek.minesweeper.models;

import android.arch.persistence.room.Entity;

import java.util.Date;


@Entity(tableName = "my_history")
public class History {
    public String id, completeTime;
    public Date date;
    public String gameMode, result;
}
