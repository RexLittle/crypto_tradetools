package com.crypto_tools.exchangapi.binance.websocket;


import com.fasterxml.jackson.core.type.TypeReference;
import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.ExchangesWebSocketListener;

import java.io.Closeable;
import java.util.List;

public class BinanceWebSocketClient  {



    public Closeable MiniMarketTickersEvent(CallBack<List<BinanceMiniTickerResp>> callback) {
        String channel = "!miniTicker@arr";
        return ApiServiceGenerator.connectWebSocket(channel, new ExchangesWebSocketListener(callback, new TypeReference<List<BinanceMiniTickerResp>>(){}),"binance");

    }

    public Closeable MarketTickersEvent(CallBack<List<BinanceAllMarketTickersResp>> callback) {
        String channel = "!ticker@arr";
        return ApiServiceGenerator.connectWebSocket(channel, new ExchangesWebSocketListener(callback, new TypeReference<List<BinanceAllMarketTickersResp>>(){}),"binance");

    }

}
