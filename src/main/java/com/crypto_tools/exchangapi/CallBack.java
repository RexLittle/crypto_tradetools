package com.crypto_tools.exchangapi;

import okhttp3.WebSocket;

import java.io.IOException;

@FunctionalInterface
public interface CallBack<T> {


    void onResponse(T var1) throws IOException, InterruptedException;

    default void onFailure(Throwable cause) {
    }



}
