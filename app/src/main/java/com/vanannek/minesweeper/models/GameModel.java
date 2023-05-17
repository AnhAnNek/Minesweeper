package com.vanannek.minesweeper.models;

import android.content.Context;

public class GameModel {

    public final int MINES_CELL = 10;
    public final int FLAG_CELL = 9;
    public final int UNTOUCHED_CELL = -1;

    private Context context;
    private GameMode gameMode;
    private int qtyFlags;
    private int moves;
    private int[][] minesTable;


    public GameModel(GameMode gameMode) {
        this.context = context;
        this.gameMode = gameMode;
        setValues();
        minesTable = new int[getRow()][getColumn()];
    }

    private void setValues() {
//        qtyFlags.postValue(gameMode.MINES);
//        moves.postValue(getRow() * getColumn() - getMines());
    }

//    private boolean isWin() {
//        return moves.getValue() == 0;
//    }
//
//    private boolean isValidCell(int row, int col) {
//        return row >= 0 && row < getRow() && col >= 0 && col < getColumn();
//    }
//
//    private boolean isMines(int row, int col) {
//        return minesTable[row][col] == MINES_CELL;
//    }
//
//    private boolean untouched(ImageView[][] gameTable, int row, int col) {
//        return Utils.areEquals(gameTable[row][col].getValue().getDrawable(), images.get(UNTOUCHED_CELL));
//    }
//
//    private boolean isFlag(ImageView[][] gameTable, int row, int col) {
//        return Utils.areEquals(gameTable[row][col].getValue().getDrawable(), images.get(FLAG_CELL));
//    }

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

    public int getMoves() {
        return moves;
    }
}
