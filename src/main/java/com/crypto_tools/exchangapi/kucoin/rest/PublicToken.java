package com.crypto_tools.exchangapi.kucoin.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicToken {

    private String code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }





}

