package com.crypto_tools.exchangapi.binance.rest;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface BinanceRestUrlList {


    @GET("/api/v1/ticker/allPrices")
    Call<List<BinanceTickeResp>> getAllTokenBaseData();

    @GET("/api/v3/ticker/24hr")
    Call<List<Binance24hPriceResp>> get24hPrice();
}
