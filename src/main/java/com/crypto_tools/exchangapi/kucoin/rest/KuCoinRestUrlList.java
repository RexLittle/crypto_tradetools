package com.crypto_tools.exchangapi.kucoin.rest;

import retrofit2.Call;
import retrofit2.http.POST;

public interface KuCoinRestUrlList {



    @POST("/api/v1/bullet-public")
    Call<PublicToken> getPublicToken();
}
