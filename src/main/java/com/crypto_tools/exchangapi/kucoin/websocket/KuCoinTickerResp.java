package com.crypto_tools.exchangapi.kucoin.websocket;

public class KuCoinTickerResp {

    private String type;
    private String topic;
    private String subject;
    private Data data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KuCoinTickerResp{" +
                "type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", subject='" + subject + '\'' +
                ", data=" + data +
                '}';
    }



}

