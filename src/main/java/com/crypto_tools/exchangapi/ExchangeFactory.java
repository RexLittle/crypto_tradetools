package com.crypto_tools.exchangapi;

import com.crypto_tools.exchangapi.binance.BinanceClients;
import com.crypto_tools.exchangapi.huobi.HuobiClients;
import com.crypto_tools.exchangapi.kucoin.KuCoinCLients;
import com.crypto_tools.exchangapi.okex.OkexClients;

public class ExchangeFactory {
    private static ExchangeFactory factory = new ExchangeFactory();

    public ExchangeFactory(){}

    public static ExchangeFactory createExchangeFactory() {
        return factory;
    }


    public BinanceClients createBinanceClients() {
        return new BinanceClients();
    }

    public OkexClients createOkexClients() {
        return new OkexClients();
    }

    public HuobiClients createHuobiClients() {return  new HuobiClients();}

    public KuCoinCLients createKuCoinClients(){return new KuCoinCLients();}


}
