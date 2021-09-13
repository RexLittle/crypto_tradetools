package com.crypto_tools.exchangapi.okex.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OkexRestUrlList {


    @GET("/api/v5/market/tickers?instType=SPOT")
    Call<OkexTickerResp> getAllTokenBaseData();
}
