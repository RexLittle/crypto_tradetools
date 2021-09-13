package com.crypto_tools.exchangapi.huobi;

import com.crypto_tools.exchangapi.ExchangeClients;
import com.crypto_tools.exchangapi.huobi.rest.HuobiRestClient;
import com.crypto_tools.exchangapi.huobi.websocket.HuobiWebSocketClient;

public class HuobiClients extends ExchangeClients<HuobiRestClient, HuobiWebSocketClient> {
    @Override
    public HuobiRestClient createRestClient() {
        return new HuobiRestClient(apiKey, secret);
    }

    @Override
    public HuobiWebSocketClient createWebSocketClient() {
        return new HuobiWebSocketClient();
    }
}
