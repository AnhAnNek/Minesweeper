package com.vanannek.minesweeper.models;

import java.io.Serializable;

public enum GameMode {
    Easy(9, 9, 12),
    Normal(16, 9, 60),
    Difficult(20, 10, 180);

    public final int ROW, COLUMN, MINES;

    GameMode(int row, int column, int mines) {
        this.ROW = row;
        this.COLUMN = column;
        this.MINES = mines;
    }
}
