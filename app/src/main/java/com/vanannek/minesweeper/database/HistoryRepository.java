package com.vanannek.minesweeper.database;

import com.vanannek.minesweeper.database.HistoryDAO;
import com.vanannek.minesweeper.models.History;

import java.util.List;

public class HistoryRepository {

    private HistoryDAO historyDAO;

    public List<History> getAll() {
        return historyDAO.getAll();
    }

    public void insertAll(History history) {
        historyDAO.insertAll(history);
    }

    public void delete(History history) {
        historyDAO.delete(history);
    }

    public void update(History history) {
        historyDAO.update(history);
    }
}
