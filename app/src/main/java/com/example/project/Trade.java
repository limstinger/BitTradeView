package com.example.project;

public class Trade {
    private String timestamp;
    private String decision;
    private double percentage;
    private String reason;
    private double btc_balance;
    private double krw_balance;
    private double btc_avg_buy_price;
    private double btc_krw_price;
    private String reflection;

    // Getters and Setters for each field
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public double getBtc_balance() { return btc_balance; }
    public void setBtc_balance(double btc_balance) { this.btc_balance = btc_balance; }

    public double getKrw_balance() { return krw_balance; }
    public void setKrw_balance(double krw_balance) { this.krw_balance = krw_balance; }

    public double getBtc_avg_buy_price() { return btc_avg_buy_price; }
    public void setBtc_avg_buy_price(double btc_avg_buy_price) { this.btc_avg_buy_price = btc_avg_buy_price; }

    public double getBtc_krw_price() { return btc_krw_price; }
    public void setBtc_krw_price(double btc_krw_price) { this.btc_krw_price = btc_krw_price; }

    public String getReflection() { return reflection; }
    public void setReflection(String reflection) { this.reflection = reflection; }
}
