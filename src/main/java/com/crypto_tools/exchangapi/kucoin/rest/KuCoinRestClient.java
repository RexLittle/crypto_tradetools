package com.crypto_tools.exchangapi.kucoin.rest;

import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.kucoin.rest.allTickers.KuCoinTickerResp;

public class KuCoinRestClient {

    private final KuCoinRestUrlList kucoinresturllist;

     public KuCoinRestClient(String apiKey, String secret){
        this.kucoinresturllist = ApiServiceGenerator.createService(KuCoinRestUrlList.class,apiKey,secret);
    }

    public PublicToken getPublicToken() {
        return ApiServiceGenerator.executeSync(kucoinresturllist.getPublicToken());
    }


    public KuCoinTickerResp getAllTokenData(){
        return ApiServiceGenerator.executeSync(kucoinresturllist.getAllTokenData());
    }
}
