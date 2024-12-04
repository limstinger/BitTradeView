package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FilteredTradeFragment extends Fragment {
    private RecyclerView recyclerView;
    private TradeAdapter tradeAdapter;
    private EditText timestampInput;
    private List<Trade> filteredTrades = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_trade, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_filtered);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tradeAdapter = new TradeAdapter(filteredTrades);
        recyclerView.setAdapter(tradeAdapter);

        timestampInput = view.findViewById(R.id.edit_text_timestamp);
        view.findViewById(R.id.button_filter).setOnClickListener(v -> filterTradesByTimestamp());

        return view;
    }

    private void filterTradesByTimestamp() {
        String timestamp = timestampInput.getText().toString().trim();
        if (TextUtils.isEmpty(timestamp)) {
            Toast.makeText(getContext(), "Please enter a timestamp.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Trade> allTrades = TradeManager.getInstance().getAllTrades();
        filteredTrades.clear();

        for (Trade trade : allTrades) {
            if (trade.getTimestamp().contains(timestamp)) {
                filteredTrades.add(trade);
            }
        }

        if (filteredTrades.isEmpty()) {
            Toast.makeText(getContext(), "No trades found for the given timestamp.", Toast.LENGTH_SHORT).show();
        }

        tradeAdapter.notifyDataSetChanged();
    }
}