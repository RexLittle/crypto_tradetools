package com.crypto_tools.exchangapi.huobi.rest;

import com.crypto_tools.exchangapi.ApiServiceGenerator;

public class HuobiRestClient {

    private final HuobiRestUrlList huobiRestUrl;


    public HuobiRestClient(String apiKey, String secret){
        this.huobiRestUrl = ApiServiceGenerator.createService(HuobiRestUrlList.class,apiKey,secret);
    }

    public HuobiTickerResp getAllTokenBaseData() {
        return ApiServiceGenerator.executeSync(huobiRestUrl.getAllTokenBaseData());
    };


}
