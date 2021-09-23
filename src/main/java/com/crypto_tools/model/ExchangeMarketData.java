package com.crypto_tools.model;

public class ExchangeMarketData {
    private String pair;
    private String lastprice;
    private String _24hChanges;
    private String _24hVolume;


    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getLastprice() {
        return lastprice;
    }

    public void setLastprice(String lastprice) {
        this.lastprice = lastprice;
    }

    public String get_24hChanges() {
        return _24hChanges;
    }

    public void set_24hChanges(String _24hChanges) {
        this._24hChanges = _24hChanges;
    }

    public String get_24hVolume() {
        return _24hVolume;
    }

    public void set_24hVolume(String _24hVolume) {
        this._24hVolume = _24hVolume;
    }
    @Override
    public String toString() {
        return "{" +
                "pair:'" + pair + '\'' +
                ", lastPrice:'" + lastprice + '\'' +
                ", _24hChanges:'" + _24hChanges + '\'' +
                ", _24hVolume:'" + _24hVolume + '\'' +
                '}';
    }


}
