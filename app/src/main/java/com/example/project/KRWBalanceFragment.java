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

public class KRWBalanceFragment extends Fragment {

    private LineChart krwBalanceChart;
    private List<Trade> tradesList; // 거래 데이터를 저장할 리스트

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_krw_balance, container, false);
        krwBalanceChart = view.findViewById(R.id.krw_balance_chart);

        fetchKRWBalanceData(); // API 데이터를 호출
        return view;
    }

    private void fetchKRWBalanceData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tradesList = response.body();
                    displayKRWBalanceData(tradesList);
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

    private void displayKRWBalanceData(List<Trade> trades) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < trades.size(); i++) {
            Trade trade = trades.get(i);
            entries.add(new Entry(i, (float) trade.getKrw_balance()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "KRW Balance Over Time");
        dataSet.setColor(Color.BLUE); // 차트 선의 색상 변경
        dataSet.setLineWidth(2f); // 선의 두께 설정
        dataSet.setDrawCircles(false); // 데이터 포인트에 원 표시 안 함
        dataSet.setDrawValues(false); // 데이터 값 표시 안 함

        LineData lineData = new LineData(dataSet);
        krwBalanceChart.setData(lineData);
        krwBalanceChart.animateX(1500); // 차트 애니메이션

        // 마커 뷰 설정
        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.custom_marker_view, trades, "KRWBalance");
        krwBalanceChart.setMarker(mv); // 차트에 마커 뷰 설정

        krwBalanceChart.invalidate(); // 차트 갱신
    }
}