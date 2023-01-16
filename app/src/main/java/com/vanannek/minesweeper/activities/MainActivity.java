package com.vanannek.minesweeper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.dialogs.GameDialog;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.models.CountUpTimer;
import com.vanannek.minesweeper.models.Engine;
import com.vanannek.minesweeper.models.GameMode;
import com.vanannek.minesweeper.models.MusicPlayer;
import com.vanannek.minesweeper.dialogs.SelectModeDialog;
import com.vanannek.minesweeper.utilities.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SelectModeDialog.SelectModeDialogListener {

    private final int CELL_DIMENSION = 100;
    private final int CELL_PADDING = 2;

    private TextView flagsTxt;
    private TableLayout minesTableLayout;
    private ImageView flagImg, newGameImg, settingImg;

    private MyDatabaseHelper myDB;
    private boolean firstClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSelectModeDialog();

        ClickSound.getInstance().init(this);
        MusicPlayer.getInstance().init(this);
        MusicPlayer.getInstance().start();

        myDB = new MyDatabaseHelper(this);
        initToolbar();
        setToolbarListeners();
        CountUpTimer.getInstance().setView( findViewById(R.id.chronometer) );
    }

    private void showSelectModeDialog() {
        SelectModeDialog selectModeDialog = new SelectModeDialog();
        selectModeDialog.show(getSupportFragmentManager(), "Select mode dialog");
    }

    @Override
    public void startGame(GameMode gameMode) {
        newGameImg.setImageDrawable(getResources().getDrawable(R.drawable.smile));
        Engine.getInstance().init(this, gameMode);
        updateFlags();
        CountUpTimer.getInstance().reset();
        initGameTable();
        firstClick = true;
    }

    private void initToolbar() {
        flagsTxt = findViewById(R.id.flagsTxt);
        flagImg = findViewById(R.id.flagImg);
        newGameImg = findViewById(R.id.newGameImg);
        settingImg = findViewById(R.id.settingImg);
    }

    private void setToolbarListeners() {
        flagImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            Engine engine = Engine.getInstance();
            if (engine.isTurnOnFlag()) {
                engine.setTurnOnFlag(false);
                flagImg.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                engine.setTurnOnFlag(true);
                flagImg.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });
        newGameImg.setOnClickListener(v -> {
            showSelectModeDialog();
            updateFlags();
        });
        settingImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        });
    }

    private void updateFlags() {
        flagsTxt.setText(String.valueOf(Engine.getInstance().getFlags()));
    }

    private void initGameTable() {
        minesTableLayout = findViewById(R.id.minesTableLayout);
        Engine.getInstance().createGameTableLayout(minesTableLayout,
                CELL_DIMENSION + 2 * CELL_PADDING, CELL_PADDING);
    }

    @Override
    public void onClick(View v) {
        ClickSound.getInstance().play();
        Pair<Integer, Integer> pos = (Pair) v.getTag();
        Engine engine = Engine.getInstance();
        if (firstClick) {
            engine.startGame(pos.first, pos.second);
            CountUpTimer.getInstance().start();
            firstClick = false;

            printMinesTableInLog();
        }

        int result = engine.openCellProcess(pos.first, pos.second);
        if (result == Utils.WIN) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(engine.getGameMode()), "Win");
            confirmGameWin();
        } else if (result == Utils.LOST) {
            myDB.add(CountUpTimer.getInstance().getCurrentTime(),
                    Utils.getCurrentDate(), String.valueOf(engine.getGameMode()), "Lose");
            confirmGameLose();
        } else if (result == Utils.FLAG_CHANGE) {
            flagsTxt.setText(String.valueOf(engine.getFlags()));
        } else {
            Log.i("Main", String.valueOf(engine.getMoves()));
        }
    }

    private void printMinesTableInLog() {
        Log.i("Main", Engine.getInstance().toString());
    }

    private void confirmGameLose() {
        CountUpTimer.getInstance().stop();
        newGameImg.setImageDrawable( getResources().getDrawable(R.drawable.sad) );
        Engine.getInstance().showAllMines();
        openLoseDialog();
    }

    private void openLoseDialog() {
        final GameDialog dialog = new GameDialog(getLayoutInflater());
        dialog.setTitle("You lose!");
        dialog.setMessage("Are you want to refresh game?");
        dialog.setYesButton(v -> {
            refreshGame();
            dialog.dismiss();
        });
        dialog.setNoButton(v -> dialog.dismiss());
        dialog.create();
        dialog.show();
    }

    private void confirmGameWin() {
        CountUpTimer.getInstance().stop();
        Engine.getInstance().showAllMines();
        openWinDialog();
    }

    private void openWinDialog() {
        final GameDialog dialog = new GameDialog(getLayoutInflater());
        dialog.setTitle("You Win!!!");
        dialog.setMessage(String.format("Time: %s\nAre you want to refresh game?",
                CountUpTimer.getInstance().getCurrentTime()));
        dialog.setYesButton(v -> {
            refreshGame();
            dialog.dismiss();
        });
        dialog.setNoButton(v -> dialog.dismiss());
        dialog.create();
        dialog.show();
    }

    private void refreshGame() {
        firstClick = true;
        Engine.getInstance().refresh();
        flagImg.setImageDrawable( getResources().getDrawable(R.drawable.red_flag_btn) );
        updateFlags();
        newGameImg.setImageDrawable( getResources().getDrawable(R.drawable.smile) );
        CountUpTimer.getInstance().reset();
    }
}