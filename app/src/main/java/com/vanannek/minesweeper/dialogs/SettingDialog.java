package com.vanannek.minesweeper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.SwitchCompat;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.models.MusicPlayer;

public class SettingDialog extends AppCompatDialogFragment {

    private Button historyBtn;
    private SwitchCompat musicSwitch, clickSoundSwitch;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SettingDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_dialog, null);
        builder.setView(view);
        init(view);
        setListeners();
        return builder.create();
    }

    private void init(View view) {
        historyBtn = view.findViewById(R.id.historyBtn);
        musicSwitch = view.findViewById(R.id.musicSwitch);
        musicSwitch.setChecked(MusicPlayer.getInstance().isTurnOn());
        clickSoundSwitch = view.findViewById(R.id.clickSoundSwitch);
        clickSoundSwitch.setChecked(ClickSound.getInstance().isTurnOn());
    }

    private void setListeners() {
        historyBtn.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            openHistoryDialog();
        });
        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ClickSound.getInstance().play();
            MusicPlayer musicPlayer = MusicPlayer.getInstance();
            if (isChecked) {
                musicPlayer.setTurnOn(true);
                musicPlayer.start();
            } else {
                musicPlayer.setTurnOn(false);
                musicPlayer.pause();
            }
        });
        clickSoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ClickSound sound = ClickSound.getInstance();
            sound.play();
            sound.setTurnOn( !sound.isTurnOn() );
        });
    }

    private void openHistoryDialog() {
        HistoryDialog historyDialog = new HistoryDialog();
        historyDialog.show(getActivity().getSupportFragmentManager(), "Select mode dialog");
    }
}
