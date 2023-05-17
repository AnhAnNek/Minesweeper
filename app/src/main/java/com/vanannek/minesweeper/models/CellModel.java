package com.vanannek.minesweeper.models;

public class CellModel {
    public static final int BLANK = -1;
    public static final int MINES = 0;

    private int value;
    private boolean isRevealed;
    private boolean isFlagged;

    public CellModel(int value) {
        this.value = value;
        isRevealed = false;
        isFlagged = false;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
