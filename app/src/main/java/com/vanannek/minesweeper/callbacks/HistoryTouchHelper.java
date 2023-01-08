package com.vanannek.minesweeper.callbacks;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.adapters.HistoryAdapter;
import com.vanannek.minesweeper.listeners.HistoryListener;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HistoryTouchHelper extends ItemTouchHelper.Callback {

    private Context context;
    private HistoryListener historyListener;

    public HistoryTouchHelper(Context context, HistoryListener historyListener) {
        this.context = context;
        this.historyListener = historyListener;
    }

    // Let's override some other important method
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // this method we will get movement of recyclerview item ok
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // same in swiped
        historyListener.onSwiped(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){
        // To limit the maximum width with a reduced dX
        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX / 3, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.delete_color))
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .addSwipeLeftLabel("Delete")
                .setSwipeLeftLabelColor(ContextCompat.getColor(context, R.color.white))
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((HistoryAdapter.HistoryViewHolder) viewHolder).itemView;
        // this will set color of item when we drag and leave any position so we want to it original color
        foregroundView.setBackgroundColor(ContextCompat.getColor(((HistoryAdapter.HistoryViewHolder) viewHolder)
                .itemView.getContext(), R.color.white));
        getDefaultUIUtil().clearView(foregroundView);

        // this will clear view when we swipe and drag
    }

    // we completed this callback code let's jump on the main activity
}
