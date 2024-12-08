package com.example.project;

import java.util.List;

public class CacheManager {

    private static CacheManager instance;
    private List<Trade> btcBalanceData; // BTC Balance 데이터를 저장할 변수 추가
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

    // BTC Balance 데이터를 저장하는 메서드
    public void saveBTCBalanceData(List<Trade> data) {
        this.btcBalanceData = data;
    }

    // BTC Balance 데이터를 가져오는 메서드
    public List<Trade> getBTCBalanceData() {
        return btcBalanceData;
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
