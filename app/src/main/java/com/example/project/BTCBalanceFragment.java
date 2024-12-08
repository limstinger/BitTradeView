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

    private LineChart BTCBalanceChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_btc_balance, container, false);
        BTCBalanceChart = view.findViewById(R.id.btc_balance_chart);
        loadCachedData(); // 캐시된 데이터 로드
        fetchBTCBalanceData(); // API 호출로 BTC Balance 데이터 가져오기
        return view;
    }

    private void fetchBTCBalanceData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayBTCBalanceData(response.body());
                    CacheManager.getInstance().saveBTCBalanceData(response.body()); // 캐시에 데이터 저장
                } else {
                    Log.e("BTCBalanceFragment", "Failed to fetch data: " + response.errorBody());
                    BTCBalanceChart.setNoDataText("Failed to load data from server.");
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                Log.e("BTCBalanceFragment", "Network error: " + t.getMessage());
                BTCBalanceChart.setNoDataText("Network error. Showing cached data.");
            }
        });
    }

    private void loadCachedData() {
        List<Trade> cachedTrades = CacheManager.getInstance().getBTCBalanceData();
        if (cachedTrades != null && !cachedTrades.isEmpty()) {
            displayBTCBalanceData(cachedTrades);
        } else {
            BTCBalanceChart.setNoDataText("No cached data available.");
        }
    }

    private void displayBTCBalanceData(List<Trade> trades) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            Trade trade = trades.get(i);
            entries.add(new Entry(i, (float) trade.getBtc_balance())); // BTC Balance 사용
        }
        LineDataSet dataSet = new LineDataSet(entries, "BTC Balance Over Time");
        LineData lineData = new LineData(dataSet);
        BTCBalanceChart.setData(lineData);
        BTCBalanceChart.invalidate();
    }
}