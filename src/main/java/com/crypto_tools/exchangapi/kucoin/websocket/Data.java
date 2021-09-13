package com.crypto_tools.exchangapi.kucoin.websocket;

public class Data {

    private String sequence;
    private String price;
    private String size;
    private String bestAsk;
    private String bestAskSize;
    private String bestBid;
    private String bestBidSize;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(String bestAsk) {
        this.bestAsk = bestAsk;
    }

    public String getBestAskSize() {
        return bestAskSize;
    }

    public void setBestAskSize(String bestAskSize) {
        this.bestAskSize = bestAskSize;
    }

    public String getBestBid() {
        return bestBid;
    }

    public void setBestBid(String bestBid) {
        this.bestBid = bestBid;
    }

    public String getBestBidSize() {
        return bestBidSize;
    }

    public void setBestBidSize(String bestBidSize) {
        this.bestBidSize = bestBidSize;
    }


    @Override
    public String toString() {
        return "{" +
                "sequence='" + sequence + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", bestAsk='" + bestAsk + '\'' +
                ", bestAskSize='" + bestAskSize + '\'' +
                ", bestBid='" + bestBid + '\'' +
                ", bestBidSize='" + bestBidSize + '\'' +
                ", time='" + time + '\'' +
                '}';
    }




}
