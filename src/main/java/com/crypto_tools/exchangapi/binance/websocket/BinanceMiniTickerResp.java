package com.crypto_tools.exchangapi.binance.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class BinanceMiniTickerResp {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("o")
    private String openPrice;

    @JsonProperty("h")
    private String highPrice;

    @JsonProperty("l")
    private String lowPrice;

    @JsonProperty("v")
    private String totalTradedBaseAssetVolume;

    @JsonProperty("q")
    private String totalTradedQuoteAssetVolume;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getTotalTradedBaseAssetVolume() {
        return totalTradedBaseAssetVolume;
    }

    public void setTotalTradedBaseAssetVolume(String totalTradedBaseAssetVolume) {
        this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume;
    }

    public String getTotalTradedQuoteAssetVolume() {
        return totalTradedQuoteAssetVolume;
    }

    public void setTotalTradedQuoteAssetVolume(String totalTradedQuoteAssetVolume) {
        this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume;
    }

    @Override
    public String toString() {
        return  "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", symbol='" + symbol + '\'' +
                ", openPrice='" + openPrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                ", totalTradedBaseAssetVolume='" + totalTradedBaseAssetVolume + '\'' +
                ", totalTradedQuoteAssetVolume='" + totalTradedQuoteAssetVolume + '\'' +
                '}';
    }




}
