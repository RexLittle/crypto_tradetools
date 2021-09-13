package com.crypto_tools.exchangapi.huobi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HuobiTickerResp {

    private HuobiTickerData[] data;
    public HuobiTickerData[] getData() {
        return data;
    }

    public void setData(HuobiTickerData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

}

