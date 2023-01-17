package com.vanannek.minesweeper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.models.GameMode;

public class SelectModeDialog extends AppCompatDialogFragment {

    private Button easyModeBtn, normalModeBtn, difficultModeBtn;
    private SelectModeDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.SelectModeDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_mode_dialog, null);
        builder.setView(view);
        init(view);
        setListeners();
        return builder.create();
    }

    private void init(View view) {
        easyModeBtn = view.findViewById(R.id.easyModeBtn);
        normalModeBtn = view.findViewById(R.id.normalModeBtn);
        difficultModeBtn = view.findViewById(R.id.difficultModeBtn);
    }

    private void setListeners() {
        easyModeBtn.setOnClickListener(v -> {
            listener.startGame(GameMode.Easy);
            dismiss();
            Log.i("SelectModeDialog", "EASY MODE");
        });
        normalModeBtn.setOnClickListener(v -> {
            listener.startGame(GameMode.Normal);
            dismiss();
            Log.i("SelectModeDialog", "NORMAL MODE");
        });
        difficultModeBtn.setOnClickListener(v -> {
            listener.startGame(GameMode.Difficult);
            dismiss();
            Log.i("SelectModeDialog", "DIFFICULT MODE");
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SelectModeDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() +
                    "must implement SelectModeDialogListener");
        }
    }

    public interface SelectModeDialogListener {
        void startGame(GameMode gameMode);
    }
}
