package com.crypto_tools.exchangapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.codec.binary.Hex;

import javax.websocket.Session;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ExchangesWebSocketListener<T> extends WebSocketListener {
    private CallBack<T> callback;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectReader objectReader;
    private boolean closing = false;

    public ExchangesWebSocketListener(CallBack<T> callback, Class<T> eventClass) {
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventClass);
    }

    public ExchangesWebSocketListener(CallBack<T> callback, TypeReference<T> eventTypeReference) {
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventTypeReference);
    }

    /**
     * 1,binanceは送信せずデータ受信出来る 3分ごとにping受信して、受信してから10分以内にpong返す
     * 2,okexはsymbolsを送信しないとデータ受信出来ない 30秒ごとにping送信
     * 3,kucoinはpublictokenを受信し、受けたIDを含め送信しないデータ受信出来ない
     * 10sごとにpingしないと切断される(コードには7s設定)
     */

     @Override
    public void onOpen(WebSocket webSocket, Response response) {
        switch (ApiServiceGenerator.getExchangeName()){
            case "okex":ApiServiceGenerator.WSBsend(null);
            break;
            default:break;
        }
    }

    @Override
    public void onMessage(okhttp3.WebSocket webSocket, String text) {
        System.out.println(text);
        T event;
        try {
            switch (ApiServiceGenerator.getExchangeName()){
                case "binance":
                    event = this.objectReader.readValue(text);
                    this.callback.onResponse(event);
                    break;

                case "okex":
                    if(!text.contains("event")) {
                        event = this.objectReader.readValue(text);
                        this.callback.onResponse(event);
                    }

                    break;

                case "kucoin":
                    if(text.contains("welcome")){
                         ApiServiceGenerator.WSBsend(text);
                    }else if(!text.contains("welcome") && !text.contains("true")){
                        event = this.objectReader.readValue(text);
                        this.callback.onResponse(event);
                    }
                    break;
                    default:break;
            }


        } catch (IOException e) {
            throw new Exception(e);
        }

    }

    @SneakyThrows
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray())).readObject();

        System.out.println(new String(Hex.decodeHex(bytes.hex().toCharArray()),"UTF-8"));
//        T event = this.objectReader.readValue(bytes);
//        this.objectReader.
//        this.callback.onResponse(event);

    }

    @Override
    public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
        this.closing = true;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        if (!this.closing) {
            this.callback.onFailure(t);
        }
    }


}
