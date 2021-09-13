package com.crypto_tools.exchangapi.huobi.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HuobiRestUrlList {
    @GET("/market/tickers")
    Call<HuobiTickerResp> getAllTokenBaseData();
}
