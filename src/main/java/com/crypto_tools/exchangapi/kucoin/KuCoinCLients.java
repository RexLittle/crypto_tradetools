package com.crypto_tools.exchangapi.kucoin;

import com.crypto_tools.exchangapi.ExchangeClients;
import com.crypto_tools.exchangapi.kucoin.rest.KuCoinRestClient;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinWebSocketClient;

public class KuCoinCLients extends ExchangeClients<KuCoinRestClient, KuCoinWebSocketClient> {

    @Override
    public KuCoinRestClient createRestClient() {
        return new KuCoinRestClient(apiKey,secret);
    }

    @Override
    public KuCoinWebSocketClient createWebSocketClient() {
        return new KuCoinWebSocketClient();
    }
}
