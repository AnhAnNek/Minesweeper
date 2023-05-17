package com.vanannek.minesweeper.viewmodels;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.dialogs.SelectModeDialog;
import com.vanannek.minesweeper.models.CountUpTimer;
import com.vanannek.minesweeper.models.Engine;
import com.vanannek.minesweeper.models.GameMode;
import com.vanannek.minesweeper.models.GameModel;

public class MainViewModel
        implements SelectModeDialog.SelectModeDialogListener {

    private final int CELL_DIMENSION = 100;
    private final int CELL_PADDING = 2;

    private final int aroundX[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    private final int aroundY[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    private boolean firstClick;
    private GameModel gameModel;
    private ImageView[][] gameTable;

    private MutableLiveData<Boolean> mLiveDataStatusFlag;
    private MutableLiveData<Drawable> mLiveDataGameImg;

    private MyDatabaseHelper myDB;



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

    private void initGameTable() {
//        myDB = new MyDatabaseHelper(getApplication());
//        statusFlag = true;
//        firstClick = true;
//
//        ClickSound.getInstance().init(this);
//        MusicPlayer.getInstance().init(this);
//        MusicPlayer.getInstance().start();
//
//        showSelectModeDialog();
//
//        gameModel = new GameModel();
//
//        CountUpTimer.getInstance().setView( findViewById(R.id.chronometer) );
    }

//    public void onClickFlagImg() {
//        ClickSound.getInstance().play();
        Engine engine = Engine.getInstance();
//        if (engine.isTurnOnFlag()) {
//            engine.setTurnOnFlag(false);
//            mFlagImageMutableData.postValue(R.drawable.red_flag_btn);
//        } else {
//            engine.setTurnOnFlag(true);
//            mFlagImageMutableData.postValue(R.drawable.green_flag_btn);
//        }
//    }

//    private void initGameTable() {
//        minesTableLayout = findViewById(R.id.minesTableLayout);
//        Engine.getInstance().createGameTableLayout(minesTableLayout,
//                CELL_DIMENSION + 2 * CELL_PADDING, CELL_PADDING);
//    }

    private void printMinesTableInLog() {
        Log.i("Main", Engine.getInstance().toString());
    }

    private void confirmGameLose() {
//        CountUpTimer.getInstance().stop();
//        newGameImg.setImageDrawable( getResources().getDrawable(R.drawable.sad) );
//        Engine.getInstance().showAllMines();
//        openLoseDialog();
    }

    private void showSelectModeDialog() {
//        SelectModeDialog selectModeDialog = new SelectModeDialog();
//        selectModeDialog.show(getSupportFragmentManager(), "Select mode dialog");
    }

    private void showSettingDialog() {
//        SettingDialog settingDialog = new SettingDialog();
//        settingDialog.show(getSupportFragmentManager(), "Select mode dialog");
    }

    private void openLoseDialog() {
//        final GameDialog dialog = new GameDialog(getLayoutInflater());
//        dialog.setTitle("You lose!");
//        dialog.setMessage("Are you want to refresh game?");
//        dialog.setYesButton(v -> {
//            refreshGame();
//            dialog.dismiss();
//        });
//        dialog.setNoButton(v -> dialog.dismiss());
//        dialog.create();
//        dialog.show();
    }

    private void confirmGameWin() {
        CountUpTimer.getInstance().stop();
        Engine.getInstance().showAllMines();
        openWinDialog();
    }

    private void openWinDialog() {
//        final GameDialog dialog = new GameDialog(getLayoutInflater());
//        dialog.setTitle("You Win!!!");
//        dialog.setMessage(String.format("Time: %s\nAre you want to refresh game?",
//                CountUpTimer.getInstance().getCurrentTime()));
//        dialog.setYesButton(v -> {
//            refreshGame();
//            dialog.dismiss();
//        });
//        dialog.setNoButton(v -> dialog.dismiss());
//        dialog.create();
//        dialog.show();
    }

    private void refreshGame() {
//        firstClick = true;
//        Engine.getInstance().refresh();
//        flagImg.setImageDrawable( getResources().getDrawable(R.drawable.red_flag_btn) );
////        updateFlags();
//        newGameImg.setImageDrawable( getResources().getDrawable(R.drawable.smile) );
//        CountUpTimer.getInstance().reset();
    }

    @Override
    public void startGame(GameMode gameMode) {
//        mLiveDataGameImg.postValue( getApplication().getResources().getDrawable(R.drawable.smile) );
//        Engine.getInstance().init(getApplication(), gameMode);
////        updateFlags();
//        CountUpTimer.getInstance().reset();
////        initGameTable();
//        firstClick = true;
    }

    public int getQtyFlags() {
        return gameModel.getQtyFlags();
    }
}