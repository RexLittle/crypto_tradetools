package com.crypto_tools.exchangapi.okex.rest;

import com.crypto_tools.exchangapi.ApiServiceGenerator;

public class OkexRestClient {
    private final OkexRestUrlList OkexRestUrlList;

    public OkexRestClient(String apiKey, String secret){
        this.OkexRestUrlList = ApiServiceGenerator.createService(OkexRestUrlList.class,apiKey,secret);
    }

    public OkexTickerResp getAllTokenBaseData() {
        return ApiServiceGenerator.executeSync(OkexRestUrlList.getAllTokenBaseData());
    }





}
