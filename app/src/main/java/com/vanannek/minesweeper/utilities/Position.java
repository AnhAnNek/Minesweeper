package com.vanannek.minesweeper.utilities;

import java.util.Objects;

public class Position {

    private int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}