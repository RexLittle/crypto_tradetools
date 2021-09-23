package com.crypto_tools.exchangapi.kucoin.rest.allTickers;

import java.util.Arrays;

public class KuCoinTickerResp {
    private String code;
    private Data data;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

}

