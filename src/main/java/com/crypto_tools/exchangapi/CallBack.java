package com.crypto_tools.exchangapi;
@FunctionalInterface
public interface CallBack<T> {
    void onResponse(T var1);

    default void onFailure(Throwable cause) {
    }
}
