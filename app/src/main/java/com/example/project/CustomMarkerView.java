package com.example.project;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import android.content.Context;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent;
    private List<Trade> trades;
    private String chartType;  // Identifier for chart type

    public CustomMarkerView(Context context, int layoutResource, List<Trade> trades, String chartType) {
        super(context, layoutResource);
        this.trades = trades;
        this.chartType = chartType;  // Store the chart type
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Trade trade = trades.get((int)e.getX());
        String originalTime = trade.getTimestamp();
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        try {
            Date date = sdfInput.parse(originalTime);
            String formattedDate = sdfOutput.format(date);
            String displayText = getDisplayText(trade, formattedDate);
            tvContent.setText(displayText);
        } catch (Exception ex) {
            tvContent.setText("Error parsing date");
        }
        super.refreshContent(e, highlight);
    }

    private String getDisplayText(Trade trade, String formattedDate) {
        switch (chartType) {
            case "BTCPrice":
                return "At: " + formattedDate + "\nBTC Price: " + trade.getBtc_krw_price();
            case "BTCBalance":
                return "At: " + formattedDate + "\nBTC Balance: " + trade.getBtc_balance();
            case "KRWBalance":
                return "At: " + formattedDate + "\nKRW Balance: " + trade.getKrw_balance();
            default:
                return "At: " + formattedDate;
        }
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}