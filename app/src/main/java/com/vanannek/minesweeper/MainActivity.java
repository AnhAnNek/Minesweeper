package com.vanannek.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FLAG_CELL = 9;
    private final int MINES_CELL = 10;
    private final int UNTOUCHED_CELL = -1;

    private final int CELL_DIMENSION = 80;
    private final int CELL_PADDING = 2;

    private int row, column, mines, flags, moves;

    private TextView flags_tv, timer_tv;
    private TableLayout mines_tl;
    private ImageButton flag_ib, refresh_ib, setting_ib;
    private boolean turnOnFlag = false;

    private Map<Integer, Drawable> imageViewMap;
    private int aroundX[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private int aroundY[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    private int[][] minesTable;
    private ImageView[][] gameTable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flags_tv = findViewById(R.id.flags_tv);
        timer_tv = findViewById(R.id.timer_tv);
        mines_tl = findViewById(R.id.mines_tl);
        flag_ib = findViewById(R.id.flag_ib);
        refresh_ib = findViewById(R.id.refresh_ib);
        setting_ib = findViewById(R.id.setting_ib);

        flag_ib.setOnClickListener(v -> {
            if (turnOnFlag) {
                turnOnFlag = false;
                flag_ib.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                turnOnFlag = true;
                flag_ib.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });

        refresh_ib.setOnClickListener(v -> {
            // refresh activity
            finish();
            startActivity(getIntent());
//            startGame();
        });

        setting_ib.setOnClickListener(v -> {
            Toast.makeText(this, "No prepare", Toast.LENGTH_SHORT).show();
        });

        // init size
        row = 8;
        column = 8;
        mines = 10;
        flags = mines;
        moves = row * column - mines;

        imageViewMap = new HashMap<>();
        imageViewMap.put(FLAG_CELL, getResources().getDrawable(R.drawable.flag));
        imageViewMap.put(MINES_CELL, getResources().getDrawable(R.drawable.bomb));
        imageViewMap.put(UNTOUCHED_CELL, getResources().getDrawable(R.drawable.untouched));
        imageViewMap.put(0, getResources().getDrawable(R.drawable.border));
        imageViewMap.put(1, getResources().getDrawable(R.drawable.one));
        imageViewMap.put(2, getResources().getDrawable(R.drawable.two));
        imageViewMap.put(3, getResources().getDrawable(R.drawable.three));
        imageViewMap.put(4, getResources().getDrawable(R.drawable.four));
        imageViewMap.put(5, getResources().getDrawable(R.drawable.five));
        imageViewMap.put(6, getResources().getDrawable(R.drawable.six));
        imageViewMap.put(7, getResources().getDrawable(R.drawable.seven));
        imageViewMap.put(8, getResources().getDrawable(R.drawable.eight));
        initMinesTable();
        initDisplayTable();
        initGameTableLayout();
//        displayMinesTable();
        updateTextView();

        // to print result table
        TextView result_tv = findViewById(R.id.result_tv);
        result_tv.setVisibility(View.VISIBLE);
        result_tv.setText(getMinesTable());
    }

    private String getMinesTable() {
        String result = "";
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                result += String.format("%d ", minesTable[i][j]);
            }
            result += "\n";
        }
        return result;
    }

    private void updateTextView() {
        flags_tv.setText(String.valueOf(flags));
    }

    private void initMinesTable() {
        minesTable = new int[this.row][this.column];
        int countMines = 0;
        int row, col;
        Random random = new Random();

        // random mines
        while (countMines < mines) {
            row = random.nextInt(this.row);
            col = random.nextInt(this.column);
            if (minesTable[row][col] == MINES_CELL) continue;
            minesTable[row][col] = MINES_CELL;
            countMines++;
        }

        // generate number
        int nextI, nextJ;
        for (int i = 0; i < minesTable.length; i++) {
            for (int j = 0; j < minesTable[0].length; j++) {
                if (minesTable[i][j] == MINES_CELL) continue;

                // count around
                countMines = 0;
                for (int k = 0; k < aroundX.length; k++) {
                    nextI = i + aroundX[k];
                    nextJ = j + aroundY[k];
                    if (isValid(nextI, nextJ) && minesTable[nextI][nextJ] == MINES_CELL)
                        countMines++;
                }
                minesTable[i][j] = countMines;
            }
        }
    }

    private void initDisplayTable() {
        gameTable = new ImageView[row][column];
        for (int i = 0; i < gameTable.length; i++) {
            for (int j = 0; j < gameTable[0].length; j++) {
                gameTable[i][j] = new ImageView(this);
                gameTable[i][j].setImageDrawable(imageViewMap.get(UNTOUCHED_CELL));
                gameTable[i][j].setTag(new Pair(i, j));
                gameTable[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Pair<Integer, Integer> pos = (Pair) v.getTag();
        int row = pos.first, col = pos.second;
        Drawable current = gameTable[row][col].getDrawable();
        if (turnOnFlag) {
            if (flags > 0 && areEquals(current, imageViewMap.get(UNTOUCHED_CELL))) {
                gameTable[row][col].setImageDrawable(imageViewMap.get(FLAG_CELL));
                flags--;
            }
            if (flags < this.mines && areEquals(current, imageViewMap.get(FLAG_CELL))) {
                gameTable[row][col].setImageDrawable(imageViewMap.get(UNTOUCHED_CELL));
                flags++;
            }
        } else {
            if (areEquals(current, imageViewMap.get(UNTOUCHED_CELL)) && minesTable[row][col] == MINES_CELL) {
                confirmGameLost();
            } else if (areEquals(current, imageViewMap.get(UNTOUCHED_CELL))) {
                show(row, col);
                if (moves == 0) {
                    confirmGameWin();
                }
            } else {
                if (areEquals(current, imageViewMap.get(FLAG_CELL))) {
                    Toast.makeText(this, "Please turn off the flag", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cell is already a number", Toast.LENGTH_SHORT).show();
                }
            }
        }
        updateTextView();
    }

    private boolean areEquals(Drawable drawable1, Drawable drawable2) {
        return Objects.equals(drawable1.getConstantState(), drawable2.getConstantState());
    }

    private void confirmGameLost() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Lost?");
        builder.setMessage("Are you want to refresh game?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // refresh activity
                finish();
                startActivity(getIntent());
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                displayMinesTable();
            }
        });

        builder.create().show();
    }

    private void confirmGameWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You win!!!");
        builder.setMessage(String.format("Time: %s\n", timer_tv.getText().toString()) +
                "Are you want to refresh game?");

        builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.column;
    }

    private void show(int i, int j) {
        int minesAround = minesTable[i][j];
        gameTable[i][j].setImageDrawable(imageViewMap.get(minesAround));
        moves--;
        if (minesAround > 0)
            return;
        for (int k = 0; k < aroundX.length; k++) {
            int nextI = i + aroundX[k];
            int nextJ = j + aroundY[k];
            if (isValid(nextI, nextJ) &&
                    areEquals(gameTable[nextI][nextJ].getDrawable(),
                        imageViewMap.get(UNTOUCHED_CELL))) {
                show(nextI, nextJ);
            }
        }
    }

    private void initGameTableLayout() {
        for (int i = 0; i < this.row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams((CELL_DIMENSION + 2 * CELL_PADDING) *
                    this.column, CELL_DIMENSION + 2 * CELL_PADDING));
            tableRow.setGravity(Gravity.CENTER);

            for (int j = 0; j < this.column; j++) {
                gameTable[i][j].setLayoutParams(new TableRow.LayoutParams(
                        CELL_DIMENSION + 2 * CELL_PADDING,
                        CELL_DIMENSION + 2 * CELL_PADDING));
                gameTable[i][j].setPadding(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING);
                gameTable[i][j].setBackground(getResources().getDrawable(R.drawable.border));
                tableRow.addView(gameTable[i][j]);
            }
            mines_tl.addView(tableRow, new TableRow.LayoutParams(
                    (CELL_DIMENSION + 2 * CELL_PADDING) * this.column,
                    CELL_DIMENSION + 2 * CELL_PADDING));
        }
    }

    public void displayMinesTable() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                gameTable[i][j].setImageDrawable( imageViewMap.get(minesTable[i][j]) );
            }
        }
    }
}