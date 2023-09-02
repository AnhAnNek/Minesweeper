package com.vanannek.minesweeper.models;

import com.vanannek.minesweeper.enums.EMode;

public class GameModel {

    public final int MINES_CELL = 10;
    public final int FLAG_CELL = 9;
    public final int UNTOUCHED_CELL = -1;
    private EMode gameMode;
    private int qtyFlags = 0;
    private int moves;
    private int[][] minesTable;


    public GameModel(EMode gameMode) {
        this.gameMode = gameMode;
        minesTable = new int[getRow()][getColumn()];
    }

    public int getRow() {
        return gameMode.ROW;
    }

    public int getColumn() {
        return gameMode.COLUMN;
    }

    public int getMines() {
        return gameMode.MINES;
    }

    public int getQtyFlags() {
        return qtyFlags;
    }

    public void setQtyFlags(int qtyFlags) {
        this.qtyFlags = qtyFlags;
    }

    public int getMoves() {
        return moves;
    }

}
