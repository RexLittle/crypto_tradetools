package com.crypto_tools.service;

import com.crypto_tools.model.ExchangeMarketData;
import com.crypto_tools.model.JsonFormatBean;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
@org.springframework.stereotype.Service
public interface Service {

    List<JsonFormatBean> Arbitrage();

    List<ExchangeMarketData> binanceMarkeDataRest();

    Closeable binanceMarketDataWebSocket(String sessionId) throws InterruptedException, IOException;

    List<ExchangeMarketData> okexMarketDataRest();

    Closeable okexMarketDataWebSocket(String sessionId) throws InterruptedException;

    List<ExchangeMarketData> kuCoinMarketDataRest();

    Closeable kuCoinMarketDataWebSocket(String sessionId) throws InterruptedException;

}
