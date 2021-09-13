package com.crypto_tools.exchangapi.binance.rest;

import com.crypto_tools.exchangapi.ApiServiceGenerator;


import java.util.List;

public class BinanceRestClient {

    private final BinanceRestUrlList binanceRestUrl;


    public BinanceRestClient(String apiKey, String secret){
        this.binanceRestUrl = ApiServiceGenerator.createService(BinanceRestUrlList.class,apiKey,secret);
    }



    public List<BinanceTickeResp> getAllTokenBaseData() {
        return ApiServiceGenerator.executeSync(binanceRestUrl.getAllTokenBaseData());
    };


}
