package com.vanannek.minesweeper.listeners;

import androidx.recyclerview.widget.RecyclerView;

public interface HistoryListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int position);
}
