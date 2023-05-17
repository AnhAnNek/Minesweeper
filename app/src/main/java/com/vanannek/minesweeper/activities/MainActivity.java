package com.vanannek.minesweeper.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.databinding.ActivityMainBinding;
import com.vanannek.minesweeper.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the same view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        MainViewModel viewModel = new MainViewModel();
        binding.setMainViewModel(viewModel);
        setContentView(binding.getRoot());
        setToolbarListeners();
        setObservers();
    }

    private void setToolbarListeners() {
        binding.flagImg.setOnClickListener(v -> {
            viewModel.onClickFlagImg();
        });
        binding.newGameImg.setOnClickListener(v -> {
//            viewModel.onClickSelectGameMode();
        });
        binding.settingImg.setOnClickListener(v -> {
//            viewModel.onClickSettingImg();
        });
    }

    private void setObservers() {
//        viewModel.getLiveDataGameMode().observe(this, new Observer<GameMode>() {
//            @Override
//            public void onChanged(GameMode gameMode) {
//                viewModel.startGame(gameMode);
//            }
//        });
        viewModel.getLiveDataStatusFlag().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean statusFlag) {
                int drawableId = statusFlag ? R.drawable.green_flag_btn : R.drawable.red_flag_btn;
                binding.flagImg.setImageDrawable(getResources().getDrawable(drawableId));
            }
        });
        viewModel.getLiveDataFlags().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.flagsTxt.setText(integer);
            }
        });
        viewModel.getLiveDataGameTable().observe(this, new Observer<ImageView[][]>() {
            @Override
            public void onChanged(ImageView[][] imageViews) {
//                viewModel.createGameTableLayout(binding.minesTableLayout);
            }
        });
    }
}