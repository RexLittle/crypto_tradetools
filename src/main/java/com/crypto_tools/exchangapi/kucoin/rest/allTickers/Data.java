package com.crypto_tools.exchangapi.kucoin.rest.allTickers;

import java.util.Arrays;

public class Data {
    private String time;
    private TickerData[] ticker;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TickerData[] getTicker() {
        return ticker;
    }

    public void setTicker(TickerData[] ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "{" +
                "time='" + time + '\'' +
                ", ticker=" + Arrays.toString(ticker) +
                '}';
    }


}
