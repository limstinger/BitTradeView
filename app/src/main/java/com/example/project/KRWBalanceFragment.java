package com.example.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class KRWBalanceFragment extends Fragment {

    private LineChart krwBalanceChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_krw_balance, container, false);
        krwBalanceChart = view.findViewById(R.id.krw_balance_chart);
        fetchKRWBalanceData();
        return view;
    }

    private void fetchKRWBalanceData() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getTrades().enqueue(new Callback<List<Trade>>() {
            @Override
            public void onResponse(Call<List<Trade>> call, Response<List<Trade>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayKRWBalanceData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Trade>> call, Throwable t) {
                krwBalanceChart.setNoDataText("Failed to load data.");
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
        LineData lineData = new LineData(dataSet);
        krwBalanceChart.setData(lineData);
        krwBalanceChart.invalidate();
    }
}