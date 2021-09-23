package com.crypto_tools.exchangapi.kucoin.websocket;

import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.ExchangesWebSocketListener;


import java.io.Closeable;

//kucoinがwebsocketを利用するためidを含めて送信する必要がある
public class KuCoinWebSocketClient {
    public Closeable Market(CallBack<KuCoinMarketResp> callback) {
        String channel = "?token=" + new ExchangeFactory().createKuCoinClients().createRestClient().getPublicToken().getData().getToken();
        return ApiServiceGenerator.connectWebSocket(channel, new ExchangesWebSocketListener(callback, KuCoinMarketResp.class, KuCoinWebSocketSendConstants.Market_Snapshot),"kucoin");

    }

    public Closeable AllTickersLastPrice(CallBack<KuCoinTickerResp> callback) {
        String channel = "?token=" + new ExchangeFactory().createKuCoinClients().createRestClient().getPublicToken().getData().getToken();
        return ApiServiceGenerator.connectWebSocket(channel, new ExchangesWebSocketListener(callback, KuCoinTickerResp.class, KuCoinWebSocketSendConstants.All_Symbols_Ticker),"kucoin");

    }
}
