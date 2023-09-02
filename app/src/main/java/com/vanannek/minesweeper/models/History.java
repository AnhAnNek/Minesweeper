package com.vanannek.minesweeper.models;

import androidx.room.Entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity(tableName = "my_history")
@AllArgsConstructor
@NoArgsConstructor
public class History {
    public String id, completeTime;
    public Date date;
    public String gameMode, result;
}
