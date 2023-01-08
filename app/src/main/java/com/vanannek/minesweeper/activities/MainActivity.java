package com.vanannek.minesweeper.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.models.CountUpTimer;
import com.vanannek.minesweeper.models.GameMode;
import com.vanannek.minesweeper.models.Engine;
import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.utilities.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int CELL_DIMENSION = 100;
    private final int CELL_PADDING = 2;

    private TextView flags_tv;
    private TableLayout mines_tl;
    private ImageView flag_iv, refresh_iv, setting_iv;

    private GameMode gameMode;
    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameMode = GameMode.Easy;
        Engine.getInstance().startGame(this, gameMode);
        initToolbar();
        initGameTable();
        myDB = new MyDatabaseHelper(this);

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
            if (Engine.getInstance().isTurnOnFlag()) {
                Engine.getInstance().setTurnOnFlag(false);
                flag_iv.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                Engine.getInstance().setTurnOnFlag(true);
                flag_iv.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });
        refresh_iv.setOnClickListener(v -> refreshGame());
        setting_iv.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
        flags_tv.setText(String.valueOf(Engine.getInstance().getFlags()));
    }

    private void printMinesTableInLog() {
        Log.i("Main", Engine.getInstance().toString());
    }

    private void initGameTable() {
        mines_tl = findViewById(R.id.mines_tl);
        int row = Engine.getInstance().getRow(), column = Engine.getInstance().getColumn();
        ImageView[][] gameTable = Engine.getInstance().getGameTable();
        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    (CELL_DIMENSION + 2 * CELL_PADDING) * column,
                    CELL_DIMENSION + 2 * CELL_PADDING));
            tableRow.setGravity(Gravity.CENTER);

            for (int j = 0; j < column; j++) {
                gameTable[i][j].setLayoutParams(new TableRow.LayoutParams(
                        CELL_DIMENSION + 2 * CELL_PADDING,
                        CELL_DIMENSION + 2 * CELL_PADDING));
                gameTable[i][j].setPadding(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING);
                gameTable[i][j].setBackground( getResources().getDrawable(R.drawable.border) );
                tableRow.addView(gameTable[i][j]);
            }
            mines_tl.addView(tableRow, new TableRow.LayoutParams(
                    (CELL_DIMENSION + 2 * CELL_PADDING) * column,
                    CELL_DIMENSION + 2 * CELL_PADDING));
        }
    }

    @Override
    public void onClick(View v) {
        Pair<Integer, Integer> pos = (Pair) v.getTag();
        int result = Engine.getInstance().openCellProcess(pos.first, pos.second);
        if (result == Utils.WIN) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(gameMode), "Win");
            confirmGameWin();
        } else if (result == Utils.LOST) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(gameMode), "Lost");
            confirmGameLost();
        } else if (result == Utils.FLAG_CHANGE) {
            flags_tv.setText(String.valueOf(Engine.getInstance().getFlags()));
        } else {
            Log.i("Main", String.valueOf(Engine.getInstance().getMoves()));
        }
    }

    private void confirmGameLost() {
        CountUpTimer.getInstance().stop();
        refresh_iv.setImageDrawable( getResources().getDrawable(R.drawable.sad) );
        Engine.getInstance().showAllMines();

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
        Engine.getInstance().showAllMines();

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
        Engine.getInstance().refresh();
        flags_tv.setText(String.valueOf(Engine.getInstance().getFlags()));
        refresh_iv.setImageDrawable( getResources().getDrawable(R.drawable.smile) );
        CountUpTimer.getInstance().restart();

        printMinesTableInLog();
    }
}