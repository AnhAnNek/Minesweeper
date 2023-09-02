package com.vanannek.minesweeper.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
