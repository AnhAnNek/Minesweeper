package com.vanannek.minesweeper.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.databinding.ActivityMainBinding;
import com.vanannek.minesweeper.models.ClickSound;
import com.vanannek.minesweeper.viewmodels.MainViewModel;

import java.security.Provider;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setOnCLicks();
    }

    private void setOnCLicks() {
        binding.flagImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            if (viewModel.isTurnOnFlag()) {
                viewModel.setTurnOnFlag(false);
                binding.flagImg.setImageDrawable(getResources().getDrawable(R.drawable.red_flag_btn));
            } else {
                viewModel.setTurnOnFlag(true);
                binding.flagImg.setImageDrawable(getResources().getDrawable(R.drawable.green_flag_btn));
            }
        });

        binding.newGameImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            viewModel.showSelectModeDialog();
            viewModel.updateFlags();
        });

        binding.settingImg.setOnClickListener(v -> {
            ClickSound.getInstance().play();
            viewModel.showSettingDialog();
        });
    }


}