package com.vanannek.minesweeper.models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.utilities.Utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Engine {

    private static Engine instance = null;

    private Engine() {
    }

    public static Engine getInstance() {
        if (instance == null) {
            synchronized (Engine.class) {
                if (instance == null) {
                    instance = new Engine();
                }
            }
        }
        return instance;
    }

    private final int MINES_CELL = 10;
    private final int FLAG_CELL = 9;
    private final int UNTOUCHED_CELL = -1;

    private Context context;

    private MutableLiveData<GameMode> gameMode;
    private MutableLiveData<Integer> flags;
    private int moves;
    private MutableLiveData<Boolean> statusFlag;
    private int aroundX[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private int aroundY[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    private int[][] minesTable;
    private Map<Integer, Drawable> images;
    private MutableLiveData<ImageView[][]> gameTable;

    public void init(Context context, GameMode gameMode) {
        statusFlag.postValue(false);
        setValues(context, gameMode);
        initImages();
        initGameTable();
        refreshGameTable();
    }

    private void setValues(Context context, GameMode gameMode) {
        this.context = context;
        this.gameMode.postValue(gameMode);
//        this.flags.postValue(getMines());
//        this.moves = getRow() * getColumn() - getMines();
    }

    private void initImages() {
        Resources res = context.getResources();
        images = new HashMap<>();
        images.put(MINES_CELL, res.getDrawable(R.drawable.mines));
        images.put(FLAG_CELL, res.getDrawable(R.drawable.flag));
        images.put(UNTOUCHED_CELL, res.getDrawable(R.drawable.untouched));
        images.put(0, res.getDrawable(R.drawable.zero));
        images.put(1, res.getDrawable(R.drawable.one));
        images.put(2, res.getDrawable(R.drawable.two));
        images.put(3, res.getDrawable(R.drawable.three));
        images.put(4, res.getDrawable(R.drawable.four));
        images.put(5, res.getDrawable(R.drawable.five));
        images.put(6, res.getDrawable(R.drawable.six));
        images.put(7, res.getDrawable(R.drawable.seven));
        images.put(8, res.getDrawable(R.drawable.eight));
    }

    private void initGameTable() {
//        gameTable.postValue(new ImageView[getRow()][getColumn()]);
//        for (int i = 0; i < gameTable.getValue().length; i++) {
//            for (int j = 0; j < gameTable.getValue()[0].length; j++) {
//                gameTable.getValue()[i][j] = new ImageView(context);
//                gameTable.getValue()[i][j].setTag(new Pair(i, j));
//                gameTable.getValue()[i][j].setSoundEffectsEnabled(false);
//                gameTable.getValue()[i][j].setOnClickListener((View.OnClickListener) context);
//            }
//        }
    }

    public void createGameTableLayout(TableLayout gameTableLayout, int cellSize, int cellPadding) {
//        gameTableLayout.removeAllViewsInLayout();
//        for (int i = 0; i < getRow(); i++) {
//            TableRow tableRow = new TableRow(context);
//            tableRow.setLayoutParams(new TableRow.LayoutParams(cellSize * getColumn(), cellSize));
//            tableRow.setGravity(Gravity.CENTER);
//            for (int j = 0; j < getColumn(); j++) {
//                gameTable.getValue()[i][j].setLayoutParams(new TableRow.LayoutParams(cellSize, cellSize));
//                gameTable.getValue()[i][j].setPadding(cellPadding, cellPadding, cellPadding, cellPadding);
//                gameTable.getValue()[i][j].setBackground(
//                        context.getResources().getDrawable(R.drawable.background_primary_border));
//                tableRow.addView(gameTable.getValue()[i][j]);
//            }
//            gameTableLayout.addView(tableRow, new TableRow.LayoutParams(cellSize * getColumn(), cellSize));
//        }
    }

    public void startGame(int rowNoMines, int colNoMines) {
//        minesTable = new int[getRow()][getColumn()];
//        randomMines(rowNoMines, colNoMines);
//        generateNumber();
    }

    private void randomMines(int rowNoMines, int colNoMines) {
//        int row, col, countMines = 0;
//        Random random = new Random();
//        while (countMines < getMines()) {
//            row = random.nextInt( getRow() );
//            col = random.nextInt( getColumn() );
//            if (isMines(row, col) || (row == rowNoMines && col == colNoMines)) continue;
//            minesTable[row][col] = MINES_CELL;
//            countMines++;
//        }
    }

    private void generateNumber() {
        for (int i = 0; i < minesTable.length; i++)
            for (int j = 0; j < minesTable[0].length; j++) {
                if (isMines(i, j)) continue;
                minesTable[i][j] = countMinesAround(i, j);
            }
    }

    private int countMinesAround(int row, int col) {
        int minesAround = 0, nextRow, nextCol;
        for (int k = 0; k < aroundX.length; k++) {
            nextRow = row + aroundX[k];
            nextCol = col + aroundY[k];
            if (!isValid(nextRow, nextCol) || !isMines(nextRow, nextCol)) continue;
            minesAround++;
        }
        return minesAround;
    }

    private void refreshGameTable() {
        for (int i = 0; i < gameTable.getValue().length; i++)
            for (int j = 0; j < gameTable.getValue()[0].length; j++)
                gameTable.getValue()[i][j].setImageDrawable( images.get(UNTOUCHED_CELL) );
    }

    public void refresh() {
//        this.flags.postValue( getMines() );
//        this.moves = getRow() * getColumn() - getMines();
//        refreshGameTable();
    }

    public int openCellProcess(int row, int column) {
        boolean temp = untouched(row, column);
//        if (statusFlag) {
//            if (temp && flags.getValue() > 0) {
//                gameTable.getValue()[row][column].setImageDrawable( images.get(FLAG_CELL) );
//                flags.postValue( flags.getValue() - 1 );
//            } else if (isFlag(row, column) && flags.getValue() < getMines()) {
//                gameTable.getValue()[row][column].setImageDrawable( images.get(UNTOUCHED_CELL) );
//                flags.postValue( flags.getValue() - 1 );
//            }
//            return Utils.FLAG_CHANGE;
//        }

        if (temp && isMines(row, column)) {
            return Utils.LOST;
        } else if (temp) {
            show(row, column);
            if (isWin())
                return Utils.WIN;
        }
        return 0;
    }

    private void show(int row, int col) {
        gameTable.getValue()[row][col].setImageDrawable( images.get(minesTable[row][col]) );
        moves--;
        if (minesTable[row][col] > 0) return;
        int i, j, nextI, nextJ;
        Queue<Pair<Integer, Integer>> positions = new LinkedList<>();
        positions.add(new Pair(row, col));
        while (!positions.isEmpty()) {
            i = positions.peek().first;
            j = positions.peek().second;
            positions.remove();
            for (int k = 0; k < aroundX.length; k++) {
                nextI = i + aroundX[k];
                nextJ = j + aroundY[k];
                if (!isValid(nextI, nextJ) || !untouched(nextI, nextJ)) continue;
                gameTable.getValue()[nextI][nextJ].setImageDrawable( images.get(minesTable[nextI][nextJ]) );
                moves--;
                if (minesTable[nextI][nextJ] > 0) continue;
                positions.add(new Pair(nextI, nextJ));
            }
        }
    }

    public void showAllMines() {
//        for (int i = 0; i < getRow(); i++)
//            for (int j = 0; j < getColumn(); j++)
//                gameTable.getValue()[i][j].setImageDrawable( images.get(minesTable[i][j]) );
    }

    private boolean isWin() {
        return moves == 0;
    }

    private boolean isValid(int row, int col) {
//        return row >= 0 && row < getRow() && col >= 0 && col < getColumn();
        return true;
    }

    private boolean isMines(int row, int col) {
        return minesTable[row][col] == MINES_CELL;
    }

    private boolean untouched(int row, int col) {
        return Utils.areEquals(gameTable.getValue()[row][col].getDrawable(), images.get(UNTOUCHED_CELL));
    }

    private boolean isFlag(int row, int col) {
        return Utils.areEquals(gameTable.getValue()[row][col].getDrawable(), images.get(FLAG_CELL));
    }

    @Override
    public String toString() {
//        String result = "";
//        for (int i = 0; i < getRow(); i++) {
//            for (int j = 0; j < getColumn(); j++)
//                result += String.format("%3d ", minesTable[i][j]);
//            result += "\n";
//        }
//        return result;
        return "";
    }

    // setters/getters
//    public int getRow() {
//        return gameMode.getValue().ROW;
//    }
//
//    public int getColumn() {
//        return gameMode.getValue().COLUMN;
//    }
//
//    public int getMines() {
//        return gameMode.getValue().MINES;
//    }
//
//    public LiveData<GameMode> getLiveDataGameMode() {
//        return gameMode;
//    }
//
//    public LiveData<Integer> getLiveDataFlags() {
//        return flags;
//    }
//
//    public int getMoves() {
//        return moves;
//    }
//
//    public LiveData<Boolean> getLiveDataStatusFlag() {
//        return statusFlag;
//    }
//
//    public LiveData<ImageView[][]> getLiveDataGameTable() {
//        return gameTable;
//    }
}