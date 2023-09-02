package com.vanannek.minesweeper.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vanannek.minesweeper.models.History;

import java.util.List;

@Dao
public
interface HistoryDAO {

    @Query("SELECT * FROM my_history")
    List<History> getAll();

    @Insert
    void insertAll(History history);

    @Delete
    void delete(History history);

    @Update
    void update(History history);
}
