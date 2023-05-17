package com.vanannek.minesweeper.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
