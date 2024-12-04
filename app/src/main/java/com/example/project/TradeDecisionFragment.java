package com.example.project;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class TradeDecisionFragment extends Fragment {

    private PieChart tradeDecisionChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trade_decision, container, false);
        tradeDecisionChart = rootView.findViewById(R.id.trade_decision_chart);
        setupPieChart(); // PieChart 설정
        fetchTradeDecisionData();
        return rootView;
    }

    private void setupPieChart() {
        tradeDecisionChart.setUsePercentValues(true);
        tradeDecisionChart.getDescription().setEnabled(false);
        tradeDecisionChart.setExtraOffsets(5, 10, 5, 5);

        tradeDecisionChart.setDragDecelerationFrictionCoef(0.95f);

        tradeDecisionChart.setDrawHoleEnabled(true);
        tradeDecisionChart.setHoleColor(Color.WHITE);
        tradeDecisionChart.setTransparentCircleRadius(61f);

        // 범례 설정
        Legend legend = tradeDecisionChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f);
    }

    private void fetchTradeDecisionData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayTradeDecisionData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                tradeDecisionChart.setNoDataText("Failed to load data.");
            }
        });
    }

    private void displayTradeDecisionData(List<Trade> trades) {
        int hold = 0, sell = 0, buy = 0;
        for (Trade trade : trades) {
            switch (trade.getDecision().toLowerCase()) {
                case "hold":
                    hold++;
                    break;
                case "sell":
                    sell++;
                    break;
                case "buy":
                    buy++;
                    break;
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        if (hold > 0) entries.add(new PieEntry(hold, "Hold"));
        if (sell > 0) entries.add(new PieEntry(sell, "Sell"));
        if (buy > 0) entries.add(new PieEntry(buy, "Buy"));

        PieDataSet dataSet = new PieDataSet(entries, "Trade Decisions");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // 각 항목에 대한 색상 설정
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(66, 133, 244)); // Blue for Hold
        colors.add(Color.rgb(219, 68, 55));  // Red for Sell
        colors.add(Color.rgb(15, 157, 88));  // Green for Buy
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.WHITE);

        tradeDecisionChart.setData(pieData);
        tradeDecisionChart.invalidate(); // 업데이트

        // 애니메이션 추가
        tradeDecisionChart.animateY(1000);
    }
}