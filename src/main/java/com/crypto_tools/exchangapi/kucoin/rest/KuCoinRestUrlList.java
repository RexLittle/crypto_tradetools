package com.crypto_tools.exchangapi.kucoin.rest;

import com.crypto_tools.exchangapi.kucoin.rest.allTickers.KuCoinTickerResp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KuCoinRestUrlList {
    @POST("/api/v1/bullet-public")
    Call<PublicToken> getPublicToken();

    @GET("/api/v1/market/allTickers")
    Call<KuCoinTickerResp> getAllTokenData();
}
