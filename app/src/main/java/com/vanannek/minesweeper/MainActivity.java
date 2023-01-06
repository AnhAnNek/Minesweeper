package com.vanannek.minesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int cellDimension = 100;
    private final int CELL_PADDING = 2;

    private Minesweeper game;

    private TextView flags_tv;
    private TableLayout mines_tl;
    private ImageView flag_iv, refresh_iv, setting_iv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Minesweeper(this, GameMode.Easy);
        game.startGame();
        initToolbar();
        initGameTable();

        printMinesTableInLog();

        CountUpTimer.getInstance().setView( findViewById(R.id.chronometer) );
        CountUpTimer.getInstance().start();
    }

    private void initToolbar() {
        flags_tv = findViewById(R.id.flags_tv);
        flag_iv = findViewById(R.id.flag_iv);
        refresh_iv = findViewById(R.id.refresh_iv);
        setting_iv = findViewById(R.id.setting_iv);
        flag_iv.setOnClickListener(v -> {
            if (game.isTurnOnFlag()) {
                game.setTurnOnFlag(false);
                flag_iv.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                game.setTurnOnFlag(true);
                flag_iv.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });
        refresh_iv.setOnClickListener(v -> refreshGame());
        setting_iv.setOnClickListener(v -> {
            Toast.makeText(this, "No prepare", Toast.LENGTH_SHORT).show();
        });
        flags_tv.setText(String.valueOf(game.getFlags()));
    }

    private void printMinesTableInLog() {
        Log.i("Main", game.toString());
    }

    private void initGameTable() {
        mines_tl = findViewById(R.id.mines_tl);
        int row = game.getRow(), column = game.getColumn();
        ImageView[][] gameTable = game.getGameTable();
        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    (cellDimension + 2 * CELL_PADDING) * column,
                    cellDimension + 2 * CELL_PADDING));
            tableRow.setGravity(Gravity.CENTER);

            for (int j = 0; j < column; j++) {
                gameTable[i][j].setLayoutParams(new TableRow.LayoutParams(
                        cellDimension + 2 * CELL_PADDING,
                        cellDimension + 2 * CELL_PADDING));
                gameTable[i][j].setPadding(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING);
                gameTable[i][j].setBackground( getResources().getDrawable(R.drawable.border) );
                tableRow.addView(gameTable[i][j]);
            }
            mines_tl.addView(tableRow, new TableRow.LayoutParams(
                    (cellDimension + 2 * CELL_PADDING) * column,
                    cellDimension + 2 * CELL_PADDING));
        }
    }

    @Override
    public void onClick(View v) {
        Pair<Integer, Integer> pos = (Pair) v.getTag();
        int result = game.openCellProcess(pos.first, pos.second);
        if (result == Minesweeper.WIN) {
            confirmGameWin();
        } else if (result == Minesweeper.LOST) {
            confirmGameLost();
        } else if (result == Minesweeper.FLAG_CHANGE) {
            flags_tv.setText(String.valueOf(game.getFlags()));
        }

        Log.i("Main", String.valueOf(game.getMoves()));
    }

    private void confirmGameLost() {
        CountUpTimer.getInstance().stop();
        refresh_iv.setImageDrawable( getResources().getDrawable(R.drawable.sad) );
        game.showAllMines();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Lost?");
        builder.setMessage("Are you want to refresh game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refreshGame();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    private void confirmGameWin() {
        CountUpTimer.getInstance().stop();
        game.showAllMines();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You win!!!");
        builder.setMessage(String.format("Time: %s\n", CountUpTimer.getInstance().getCurrentTime()) +
                "Are you want to refresh game?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                refreshGame();
            }
        });
        builder.create().show();
    }

    private void refreshGame() {
        game.refresh();
        flags_tv.setText(String.valueOf(game.getFlags()));
        refresh_iv.setImageDrawable( getResources().getDrawable(R.drawable.smile) );
        CountUpTimer.getInstance().restart();

        printMinesTableInLog();
    }
}