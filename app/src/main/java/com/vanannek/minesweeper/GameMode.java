package com.vanannek.minesweeper;

public enum GameMode {
    Easy(10, 9, 12),
    Normal(20, 16, 60),
    Difficult(30, 24, 180);

    public final int ROW, COLUMN, MINES;

    GameMode(int row, int column, int mines) {
        this.ROW = row;
        this.COLUMN = column;
        this.MINES = mines;
    }
}
