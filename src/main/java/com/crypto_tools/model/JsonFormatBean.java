package com.crypto_tools.model;

public class JsonFormatBean {

    String symbol;
    String exchange1Price;
    String exchange2Price;

    public JsonFormatBean(String symbol, String exchange1Price, String exchange2Price) {
       this.symbol = symbol;
       this.exchange1Price = exchange1Price;
       this.exchange2Price = exchange2Price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
        return "{" +
                "symbol='" + symbol + '\'' +
                ", exchange1Price='" + exchange1Price + '\'' +
                ", exchange2Price='" + exchange2Price + '\'' +
                '}';
    }


}
