package com.crypto_tools.exchangapi.okex.websocket;

import com.crypto_tools.exchangapi.CallBack;

import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.ExchangesWebSocketListener;

import java.io.Closeable;

public class OkexWebSocketClient {



    public Closeable MarketTickersEvent(CallBack<OkexTickersResp> callback) {

        return ApiServiceGenerator.connectWebSocket("", new ExchangesWebSocketListener(callback, OkexTickersResp.class),"okex");

    }

}
