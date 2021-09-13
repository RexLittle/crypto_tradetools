package com.crypto_tools.exchangapi.huobi.websocket;

import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.ExchangesWebSocketListener;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerData;

import java.io.Closeable;

public class HuobiWebSocketClient {
    public Closeable MarketTickersEvent(CallBack<HuobiTickerData> callback) {
        return ApiServiceGenerator.connectWebSocket("", new ExchangesWebSocketListener(callback, HuobiTickerData.class),"huobi");

    }
}
