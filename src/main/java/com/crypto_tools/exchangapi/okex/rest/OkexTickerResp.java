package com.crypto_tools.exchangapi.okex.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OkexTickerResp {

    private String code;
    private String msg;
    private OkexTickerData[] data;


    public OkexTickerData[] getData() {
        return data;
    }

    public void setData(OkexTickerData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OkexTickerData{" +
                "code:'" + code + '\'' +
                ", msg:'" + msg + '\'' +
                ", data:" + Arrays.toString(data) +
                '}';
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
