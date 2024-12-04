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

public class BTCBalanceFragment extends Fragment {

    private LineChart krwBalanceChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_krw_balance, container, false);
        krwBalanceChart = view.findViewById(R.id.krw_balance_chart);
        loadCachedData(); // 캐시된 데이터를 먼저 로드
        fetchKRWBalanceData(); // API 데이터를 호출
        return view;
    }

    private void fetchKRWBalanceData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayKRWBalanceData(response.body());
                    CacheManager.getInstance().saveKRWBalanceData(response.body()); // 데이터를 캐시에 저장
                } else {
                    Log.e("KRWBalanceFragment", "Failed to fetch data: " + response.errorBody());
                    krwBalanceChart.setNoDataText("Failed to load data from server.");
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                Log.e("KRWBalanceFragment", "Network error: " + t.getMessage());
                krwBalanceChart.setNoDataText("Network error. Showing cached data.");
            }
        });
    }

    private void loadCachedData() {
        List<Trade> cachedTrades = CacheManager.getInstance().getKRWBalanceData();
        if (cachedTrades != null && !cachedTrades.isEmpty()) {
            displayKRWBalanceData(cachedTrades);
        } else {
            krwBalanceChart.setNoDataText("No cached data available.");
        }
    }

    private void displayKRWBalanceData(List<Trade> trades) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            Trade trade = trades.get(i);
            entries.add(new Entry(i, (float) trade.getKrw_balance()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "KRW Balance Over Time");
        LineData lineData = new LineData(dataSet);
        krwBalanceChart.setData(lineData);
        krwBalanceChart.invalidate();
    }
}