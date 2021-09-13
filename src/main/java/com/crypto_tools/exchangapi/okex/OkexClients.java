package com.crypto_tools.exchangapi.okex;

import com.crypto_tools.exchangapi.ExchangeClients;
import com.crypto_tools.exchangapi.okex.rest.OkexRestClient;
import com.crypto_tools.exchangapi.okex.websocket.OkexWebSocketClient;

public class OkexClients extends ExchangeClients<OkexRestClient,OkexWebSocketClient> {


    @Override
    public OkexRestClient createRestClient() {
        return new OkexRestClient(apiKey,secret);
    }

    @Override
    public OkexWebSocketClient createWebSocketClient() {
        return new OkexWebSocketClient();
    }
}
