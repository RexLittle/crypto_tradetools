package com.crypto_tools.exchangapi.huobi.rest;

public class HuobiTickerData {


    private float amount;
    private int count;
    private float open;
    private float close;
    private float low;
    private float high;
    private float vol;
    private String symbol;
    private float bid;
    private float bidSize;
    private float ask;
    private float askSize;
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getVol() {
        return vol;
    }

    public void setVol(float vol) {
        this.vol = vol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getBidSize() {
        return bidSize;
    }

    public void setBidSize(float bidSize) {
        this.bidSize = bidSize;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public float getAskSize() {
        return askSize;
    }

    public void setAskSize(float askSize) {
        this.askSize = askSize;
    }

    @Override
    public String toString() {
        return "{" +
                "amount=" + amount +
                ", count=" + count +
                ", open=" + open +
                ", close=" + close +
                ", low=" + low +
                ", high=" + high +
                ", vol=" + vol +
                ", symbol='" + symbol + '\'' +
                ", bid=" + bid +
                ", bidSize=" + bidSize +
                ", ask=" + ask +
                ", askSize=" + askSize +
                '}';
    }
}
