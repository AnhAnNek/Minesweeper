package com.vanannek.minesweeper.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vanannek.minesweeper.R;
import com.vanannek.minesweeper.database.MyDatabaseHelper;
import com.vanannek.minesweeper.models.History;
import com.vanannek.minesweeper.utilities.Utils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Activity activity;
    private Context context;
    private final List<History> itemsHistory;

    public HistoryAdapter(Activity activity, Context context, List<History> itemsHistory) {
        this.activity = activity;
        this.context = context;
        this.itemsHistory = itemsHistory;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_container_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.result_tv.setText(String.valueOf(itemsHistory.get(position).result));
        holder.completion_time_tv.setText(String.valueOf(itemsHistory.get(position).completeTime));
        holder.game_mode_tv.setText(String.valueOf(itemsHistory.get(position).gameMode));
        holder.date_tv.setText(Utils.formatDate.format(itemsHistory.get(position).date));
    }

    @Override
    public int getItemCount() {
        return itemsHistory.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView result_tv, completion_time_tv, game_mode_tv, date_tv;
        private ConstraintLayout historyLayout;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            result_tv = itemView.findViewById(R.id.result_tv);
            completion_time_tv = itemView.findViewById(R.id.completion_time_tv);
            game_mode_tv = itemView.findViewById(R.id.game_mode_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            historyLayout = itemView.findViewById(R.id.historyItemLayout);

            // Animation Recyclerview
        }
    }

    public void removeItem(int position) {
        // delete into database
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        myDB.deleteOneRow(itemsHistory.get(position).id);

        itemsHistory.remove(position);

        // this will update recyclerview means refresh it
        notifyItemRemoved(position);
    }

    public void restoreItem(History history, int position) {
        itemsHistory.add(position, history);
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        myDB.add(history.completeTime, Utils.formatDate.format(history.date),
                history.gameMode, history.result);
        notifyItemInserted(position);
    }
}
