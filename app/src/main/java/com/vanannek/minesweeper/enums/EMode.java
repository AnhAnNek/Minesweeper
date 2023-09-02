package com.vanannek.minesweeper.enums;

public enum EMode {
    Easy(9, 9, 12),
    Normal(16, 9, 60),
    Difficult(20, 10, 180);

    public final int ROW, COLUMN, MINES;

    EMode(int row, int column, int mines) {
        this.ROW = row;
        this.COLUMN = column;
        this.MINES = mines;
    }
}
