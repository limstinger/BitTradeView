package com.example.project;

import java.util.List;

public class CacheManager {

    private static CacheManager instance;
    private List<Trade> btcPriceData;
    private List<Trade> krwBalanceData;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void saveBTCPriceData(List<Trade> data) {
        this.btcPriceData = data;
    }

    public List<Trade> getBTCPriceData() {
        return btcPriceData;
    }

    public void saveKRWBalanceData(List<Trade> data) {
        this.krwBalanceData = data;
    }

    public List<Trade> getKRWBalanceData() {
        return krwBalanceData;
    }
}
