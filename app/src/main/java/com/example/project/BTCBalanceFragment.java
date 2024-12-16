package com.example.project;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import android.graphics.Color;

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
                    CacheManager.getInstance().saveBTCBalanceData(response.body()); // 데이터를 캐시에 저장
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
            entries.add(new Entry(i, (float) trade.getBtc_balance()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "BTC Balance Over Time");
        dataSet.setColor(Color.RED); // 선 색상 설정
        dataSet.setLineWidth(2f); // 선 두께 설정
        dataSet.setDrawCircles(false); // 데이터 포인트에 원 표시 안 함
        dataSet.setDrawValues(false); // 데이터 값 표시 안 함

        LineData lineData = new LineData(dataSet);
        BTCBalanceChart.setData(lineData);
        BTCBalanceChart.animateX(1500); // 차트 애니메이션

        // CustomMarkerView에 chartType 추가
        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.custom_marker_view, trades, "BTCBalance");
        BTCBalanceChart.setMarker(mv); // 차트에 마커 뷰 설정

        BTCBalanceChart.invalidate(); // 차트 갱신
    }
}