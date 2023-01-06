package com.vanannek.minesweeper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.vanannek.minesweeper.utilities.Position;
import com.vanannek.minesweeper.utilities.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Minesweeper {

    public static final int LOST = 99;
    public static final int WIN = 100;
    public static final int FLAG_CHANGE = 101;

    public static final int MINES_CELL = 10;
    public static final int FLAG_CELL = 9;
    public static final int UNTOUCHED_CELL = -1;

    private Context context;

    private int row, column, mines, flags, moves;
    private boolean turnOnFlag = false;
    private int aroundX[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private int aroundY[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    private int[][] minesTable;
    private Map<Integer, Drawable> images;
    private ImageView[][] gameTable;

    public Minesweeper(Context context, int row, int column, int mines) {
        this.context = context;
        this.row = row;
        this.column = column;
        this.mines = mines;
    }

    public Minesweeper(Context context, GameMode gameMode) {
        this(context, gameMode.ROW, gameMode.COLUMN, gameMode.MINES);
    }

    public void startGame() {
        this.flags = mines;
        this.moves = row * column - mines;
        initImages();
        initMinesTable();
        initGameTable();
        setImageForTable();
    }

    private void initImages() {
        Resources res = context.getResources();
        images = new HashMap<>();
        images.put(Minesweeper.MINES_CELL, res.getDrawable(R.drawable.bomb));
        images.put(FLAG_CELL, res.getDrawable(R.drawable.flag));
        images.put(UNTOUCHED_CELL, res.getDrawable(R.drawable.untouched));
        images.put(0, res.getDrawable(R.drawable.border));
        images.put(1, res.getDrawable(R.drawable.one));
        images.put(2, res.getDrawable(R.drawable.two));
        images.put(3, res.getDrawable(R.drawable.three));
        images.put(4, res.getDrawable(R.drawable.four));
        images.put(5, res.getDrawable(R.drawable.five));
        images.put(6, res.getDrawable(R.drawable.six));
        images.put(7, res.getDrawable(R.drawable.seven));
        images.put(8, res.getDrawable(R.drawable.eight));
    }

    public void initGameTable() {
        gameTable = new ImageView[row][column];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                gameTable[i][j] = new ImageView(context);
                gameTable[i][j].setTag(new Pair(i, j));
                gameTable[i][j].setOnClickListener((View.OnClickListener) context);
            }
        }
    }

    private void initMinesTable() {
        minesTable = new int[this.row][this.column];
        int row, col, countMines = 0;
        Random random = new Random();

        // random mines
        while (countMines < mines) {
            row = random.nextInt(this.row);
            col = random.nextInt(this.column);
            if (isMines(row, col)) continue;
            minesTable[row][col] = MINES_CELL;
            countMines++;
        }

        // generate number
        int nextI, nextJ;
        for (int i = 0; i < minesTable.length; i++) {
            for (int j = 0; j < minesTable[0].length; j++) {
                if (isMines(i, j)) continue;

                // count around
                countMines = 0;
                for (int k = 0; k < aroundX.length; k++) {
                    nextI = i + aroundX[k];
                    nextJ = j + aroundY[k];
                    if (isValid(nextI, nextJ) && isMines(nextI, nextJ))
                        countMines++;
                }
                minesTable[i][j] = countMines;
            }
        }
    }

    private void setImageForTable() {
        for (int i = 0; i < gameTable.length; i++)
            for (int j = 0; j < gameTable[0].length; j++)
                gameTable[i][j].setImageDrawable( images.get(UNTOUCHED_CELL) );
    }

    public void refresh() {
        this.flags = mines;
        this.moves = row * column - mines;
        initMinesTable();
        setImageForTable();
    }

    public int openCellProcess(int row, int column) {
        boolean temp = untouched(row, column);
        if (turnOnFlag) {
            if (flags > 0 && temp) {
                gameTable[row][column].setImageDrawable( images.get(FLAG_CELL) );
                flags--;
            } else if (flags < this.mines && isFlag(row, column)) {
                gameTable[row][column].setImageDrawable( images.get(UNTOUCHED_CELL) );
                flags++;
            }
            return FLAG_CHANGE;
        }

        if (temp && isMines(row, column)) {
            return LOST;
        } else if (temp) {
            show(row, column);
            if (isWin())
                return WIN;
        }
        return 0;
    }

    private void show(int row, int col) {
        gameTable[row][col].setImageDrawable( images.get(minesTable[row][col]) );
        moves--;
        if (minesTable[row][col] > 0) return;
        int i, j, nextI, nextJ;
        Queue<Position> positions = new LinkedList<>();
        positions.add(new Position(row, col));
        while (!positions.isEmpty()) {
            i = positions.peek().getRow();
            j = positions.peek().getCol();
            positions.remove();
            for (int k = 0; k < aroundX.length; k++) {
                nextI = i + aroundX[k];
                nextJ = j + aroundY[k];
                if (isValid(nextI, nextJ) && untouched(nextI, nextJ)) {
                    gameTable[nextI][nextJ].setImageDrawable( images.get(minesTable[nextI][nextJ]) );
                    moves--;
                    if (minesTable[nextI][nextJ] > 0) continue;
                    positions.add(new Position(nextI, nextJ));
                }
            }
        }
    }

    public void showAllMines() {
        for (int i = 0; i < this.row; i++)
            for (int j = 0; j < this.column; j++)
                gameTable[i][j].setImageDrawable( images.get(minesTable[i][j]) );
    }

    private boolean isWin() {
        return moves == 0;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.column;
    }

    private boolean isMines(int row, int col) {
        return minesTable[row][col] == MINES_CELL;
    }

    private boolean untouched(int row, int col) {
        return Utils.areEquals(gameTable[row][col].getDrawable(), images.get(UNTOUCHED_CELL));
    }

    private boolean isFlag(int row, int col) {
        return Utils.areEquals(gameTable[row][col].getDrawable(), images.get(FLAG_CELL));
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++)
                result += String.format("%3d ", minesTable[i][j]);
            result += "\n";
        }
        return result;
    }

    // setters/getters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public int getFlags() {
        return flags;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isTurnOnFlag() {
        return turnOnFlag;
    }

    public void setTurnOnFlag(boolean turnOnFlag) {
        this.turnOnFlag = turnOnFlag;
    }

    public ImageView[][] getGameTable() {
        return gameTable;
    }
}