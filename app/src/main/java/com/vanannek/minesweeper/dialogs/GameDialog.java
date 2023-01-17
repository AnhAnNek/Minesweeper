package com.vanannek.minesweeper.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vanannek.minesweeper.R;

public class GameDialog extends Dialog {

    private TextView alertTitleTxt, alertMessageTxt;
    private Button alertYesBtn, alertNoBtn;

    public GameDialog(LayoutInflater inflater) {
        super(inflater.getContext());
        View view = inflater.inflate(R.layout.game_dialog, null);
        setContentView(view);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init(view);
    }

    private void init(View view) {
        alertTitleTxt = view.findViewById(R.id.alertTitleTxt);
        alertMessageTxt = view.findViewById(R.id.alertMessageTxt);
        alertYesBtn = view.findViewById(R.id.alertYesBtn);
        alertNoBtn = view.findViewById(R.id.alertNoBtn);
    }

    public void setTitle(String title) {
        alertTitleTxt.setText(title);
    }

    public void setMessage(String message) {
        alertMessageTxt.setText(message);
    }

    public void setYesButton(View.OnClickListener listener) {
        alertYesBtn.setOnClickListener(listener);
    }

    public void setNoButton(View.OnClickListener listener) {
        alertNoBtn.setOnClickListener(listener);
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void show() {
        super.show();
    }
}
