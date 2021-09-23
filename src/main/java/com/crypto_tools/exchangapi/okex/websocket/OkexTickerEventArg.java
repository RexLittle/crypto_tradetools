package com.crypto_tools.exchangapi.okex.websocket;

public class OkexTickerEventArg {

    private String channel;
    private String instId;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }


    @Override
    public String toString() {
        return "OkexTickerEventArg{" +
                "channel:'" + channel + '\'' +
                ", instId:'" + instId + '\'' +
                '}';
    }

}
