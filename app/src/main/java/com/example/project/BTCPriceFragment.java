package com.example.project;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BTCPriceFragment extends Fragment {

    private LineChart btcPriceChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_btc_price, container, false);
        btcPriceChart = view.findViewById(R.id.btc_price_chart);
        loadCachedData(); // 캐시된 데이터를 먼저 로드
        fetchBTCPriceData(); // API 데이터를 호출
        return view;
    }

    private void fetchBTCPriceData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBTCPriceData(response.body());
                    CacheManager.getInstance().saveBTCPriceData(response.body()); // 데이터를 캐시에 저장
                } else {
                    Log.e("BTCPriceFragment", "Failed to fetch data: " + response.errorBody());
                    btcPriceChart.setNoDataText("Failed to load data from server.");
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                Log.e("BTCPriceFragment", "Network error: " + t.getMessage());
                btcPriceChart.setNoDataText("Network error. Showing cached data.");
            }
        });
    }

    private void loadCachedData() {
        List<Trade> cachedTrades = CacheManager.getInstance().getBTCPriceData();
        if (cachedTrades != null && !cachedTrades.isEmpty()) {
            displayBTCPriceData(cachedTrades);
        } else {
            btcPriceChart.setNoDataText("No cached data available.");
        }
    }

    private void displayBTCPriceData(List<Trade> trades) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            Trade trade = trades.get(i);
            entries.add(new Entry(i, (float) trade.getBtc_krw_price()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "BTC Price Over Time");
        LineData lineData = new LineData(dataSet);
        btcPriceChart.setData(lineData);
        btcPriceChart.invalidate();
    }
}