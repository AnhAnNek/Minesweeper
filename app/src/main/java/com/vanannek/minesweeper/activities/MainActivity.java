package com.vanannek.minesweeper.activities;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.models.CountUpTimer;
import com.vanannek.minesweeper.models.Engine;
import com.vanannek.minesweeper.models.GameMode;
import com.vanannek.minesweeper.models.MainSound;
import com.vanannek.minesweeper.utilities.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int CELL_DIMENSION = 100;
    private final int CELL_PADDING = 2;

    private TextView flagsTxt;
    private TableLayout minesTableLayout;
    private ImageView flagImg, refreshImg, settingImg;

    private MyDatabaseHelper myDB;
    private boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainSound.getInstance().init(this);
        MainSound.getInstance().start();

        Engine.getInstance().init(this, Utils.gameMode);
        initToolbar();
        setToolbarListeners();
        initGameTable();
        myDB = new MyDatabaseHelper(this);

        CountUpTimer.getInstance().setView( findViewById(R.id.chronometer) );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initToolbar() {
        flagsTxt = findViewById(R.id.flagsTxt);
        flagImg = findViewById(R.id.flagImg);
        refreshImg = findViewById(R.id.refreshImg);
        settingImg = findViewById(R.id.settingImg);
    }

    private void setToolbarListeners() {
        flagImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            if (Engine.getInstance().isTurnOnFlag()) {
                Engine.getInstance().setTurnOnFlag(false);
                flagImg.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                Engine.getInstance().setTurnOnFlag(true);
                flagImg.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });
        refreshImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        });
        settingImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
//            Intent intent = new Intent(this, HistoryActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        flagsTxt.setText(String.valueOf(Engine.getInstance().getFlags()));
    }

    private void printMinesTableInLog() {
        Log.i("Main", Engine.getInstance().toString());
    }

    private void initGameTable() {
        minesTableLayout = findViewById(R.id.minesTableLayout);
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
                gameTable[i][j].setBackground( getResources().getDrawable(R.drawable.background_primary_border) );
                tableRow.addView(gameTable[i][j]);
            }
            minesTableLayout.addView(tableRow, new TableRow.LayoutParams(
                    (CELL_DIMENSION + 2 * CELL_PADDING) * column,
                    CELL_DIMENSION + 2 * CELL_PADDING));
        }
    }

    @Override
    public void onClick(View v) {
        ClickSound.getInstance().play();
        Pair<Integer, Integer> pos = (Pair) v.getTag();
        if (firstClick) {
            Engine.getInstance().startGame(pos.first, pos.second);
            CountUpTimer.getInstance().start();

            printMinesTableInLog();

            firstClick = false;
        }

        int result = Engine.getInstance().openCellProcess(pos.first, pos.second);
        if (result == Utils.WIN) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(Utils.gameMode), "Win");
            confirmGameWin();
        } else if (result == Utils.LOST) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(Utils.gameMode), "Lost");
            confirmGameLost();
        } else if (result == Utils.FLAG_CHANGE) {
            flagsTxt.setText(String.valueOf(Engine.getInstance().getFlags()));
        } else {
            Log.i("Main", String.valueOf(Engine.getInstance().getMoves()));
        }
    }

    private void confirmGameLost() {
        CountUpTimer.getInstance().stop();
        refreshImg.setImageDrawable( getResources().getDrawable(R.drawable.sad) );
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
        firstClick = true;
        Engine.getInstance().refresh();
        flagImg.setImageDrawable( getResources().getDrawable(R.drawable.red_flag_btn) );
        flagsTxt.setText(String.valueOf(Engine.getInstance().getFlags()));
        refreshImg.setImageDrawable( getResources().getDrawable(R.drawable.smile) );
        CountUpTimer.getInstance().reset();

        printMinesTableInLog();
    }
}