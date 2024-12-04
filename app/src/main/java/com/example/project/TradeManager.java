package com.example.project;

import java.util.ArrayList;
import java.util.List;

public class TradeManager {
    private static TradeManager instance;
    private List<Trade> allTrades;

    private TradeManager() {
        allTrades = new ArrayList<>();
    }

    public static TradeManager getInstance() {
        if (instance == null) {
            instance = new TradeManager();
        }
        return instance;
    }

    public List<Trade> getAllTrades() {
        return allTrades;
    }

    public void setAllTrades(List<Trade> trades) {
        allTrades.clear();
        allTrades.addAll(trades);
    }
}