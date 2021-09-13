package com.crypto_tools.exchangapi.kucoin.rest;

public class KuCoinEvent<T> {

    private String id;
    private String type;
    private String topic;
    private Boolean privateChannel;
    private Boolean response;
    private T data;
    private String subject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getPrivateChannel() {
        return privateChannel;
    }

    public void setPrivateChannel(Boolean privateChannel) {
        this.privateChannel = privateChannel;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "KucoinEvent{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", privateChannel=" + privateChannel +
                ", response=" + response +
                ", data=" + data +
                ", subject='" + subject + '\'' +
                '}';
    }


}
