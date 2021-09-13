package com.crypto_tools.exchangapi;

public abstract class ExchangeClients<T,S> {
    protected String apiKey;
    protected String secret;

    public abstract T createRestClient();
    public abstract S createWebSocketClient();


}
