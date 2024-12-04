package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {
    private List<Trade> tradeList;

    public TradeAdapter(List<Trade> tradeList) {
        this.tradeList = tradeList;
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trade, parent, false);
        return new TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        Trade trade = tradeList.get(position);
        holder.timestampTextView.setText("Timestamp: " + trade.getTimestamp());
        holder.decisionTextView.setText("Decision: " + trade.getDecision());
        holder.reasonTextView.setText("Reason: " + trade.getReason());
    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    // 새로운 데이터로 업데이트
    public void updateData(List<Trade> newTradeList) {
        tradeList.clear();
        tradeList.addAll(newTradeList);
        notifyDataSetChanged();
    }

    static class TradeViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTextView, decisionTextView, reasonTextView;

        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(R.id.textview_timestamp);
            decisionTextView = itemView.findViewById(R.id.textview_decision);
            reasonTextView = itemView.findViewById(R.id.textview_reason);
        }
    }
}