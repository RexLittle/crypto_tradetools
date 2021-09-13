package com.crypto_tools.service;

import com.crypto_tools.model.ExchangeMarketData;
import com.crypto_tools.model.JsonFormatBean;

import java.util.List;

public interface Service {

    List<JsonFormatBean> Arbitrage();
    List<ExchangeMarketData> marketData();

}
