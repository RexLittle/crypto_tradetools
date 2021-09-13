package com.crypto_tools.exchangapi.okex.websocket;

import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;

public class OkexTickersResp {
    public OkexTickerEventArg getArg() {
        return arg;
    }

    public void setArg(OkexTickerEventArg arg) {
        this.arg = arg;
    }

    public OkexTickerData[] getData() {
        return data;
    }

    public void setData(OkexTickerData[] data) {
        this.data = data;
    }

    private OkexTickerEventArg arg;
    private OkexTickerData[] data;
}
