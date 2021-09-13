package com.crypto_tools.exchangapi.binance;

import com.crypto_tools.exchangapi.ExchangeClients;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestClient;
import com.crypto_tools.exchangapi.binance.websocket.BinanceWebSocketClient;

public class BinanceClients extends ExchangeClients<BinanceRestClient,BinanceWebSocketClient> {


    @Override
    public BinanceRestClient createRestClient() {
        return new BinanceRestClient(apiKey,secret);
    }

    @Override
    public BinanceWebSocketClient createWebSocketClient() {
        return new BinanceWebSocketClient();
    }



}
