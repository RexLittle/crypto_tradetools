package com.crypto_tools.model;

public class JsonFormatBean {

    String pair;
    String exchange1Price;
    String exchange2Price;
    String priceGap;
    String priceGapPercent;
    public String getPriceGap() {
        return priceGap;
    }

    public void setPriceGap(String priceGap) {
        this.priceGap = priceGap;
    }

    public String getPriceGapPercent() {
        return priceGapPercent;
    }

    public void setPriceGapPercent(String priceGapPercent) {
        this.priceGapPercent = priceGapPercent;
    }



    public JsonFormatBean(String pair, String exchange1Price, String exchange2Price,String priceGap,String priceGapPercent) {
       this.pair = pair;
       this.exchange1Price = exchange1Price;
       this.exchange2Price = exchange2Price;
       this.priceGap = priceGap;
       this.priceGapPercent = priceGapPercent;
    }

    public String getpair() {
        return pair;
    }

    public void setpair(String pair) {
        this.pair = pair;
    }

    public String getexchange1Price() {
        return exchange1Price;
    }

    public void setexchange1Price(String exchange1Price) {
        exchange1Price = exchange1Price;
    }

    public String getexchange2Price() {
        return exchange2Price;
    }

    public void setexchange2Price(String exchange2Price) {
        exchange2Price = exchange2Price;
    }
    @Override
    public String toString() {
        return "JsonFormatBean{" +
                "pair='" + pair + '\'' +
                ", exchange1Price='" + exchange1Price + '\'' +
                ", exchange2Price='" + exchange2Price + '\'' +
                ", priceGap='" + priceGap + '\'' +
                ", priceGapPercent='" + priceGapPercent + '\'' +
                '}';
    }



}
