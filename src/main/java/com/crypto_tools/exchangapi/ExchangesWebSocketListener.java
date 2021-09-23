package com.crypto_tools.exchangapi;


import com.crypto_tools.controller.WebSocketDispatcher;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExchangesWebSocketListener<T> extends WebSocketListener {
    private CallBack<T> callback;
    private String sendtype;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectReader objectReader;
    private boolean closing = false;
    private String[] pong = new String[2];
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private static Logger log = LoggerFactory.getLogger(WebSocketDispatcher.class);

    public ExchangesWebSocketListener(CallBack<T> callback, Class<T> eventClass) {
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventClass);
    }

    public ExchangesWebSocketListener(CallBack<T> callback, Class<T> eventClass,String sendtype) {
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventClass);
        this.sendtype = sendtype;
    }


    public ExchangesWebSocketListener(CallBack<T> callback, TypeReference<T> eventTypeReference) {
        this.callback = callback;
        this.objectReader = mapper.readerFor(eventTypeReference);
    }


    /**
     * 1,binanceは送信せずデータ受信出来る 3分ごとにping受信して、受信してから10分以内にpong返す.
     * 2,okexはsymbolsを送信しないとデータ受信出来ない 15秒ごとにping送信.
     * 3,kucoinはpublictokenを受信し、受けたIDを含め送信しないデータ受信出来ない
     * 10sごとにpingする
     */

     @Override
    public void onOpen(WebSocket webSocket, Response response) {
        switch (ApiServiceGenerator.getExchangeName()){
            case "okex":ApiServiceGenerator.WSBsend(null);
                scheduler.scheduleAtFixedRate(()->{
                    ApiServiceGenerator.WSBsend("ping");
                },3,15, TimeUnit.SECONDS);
                break;
            default:break;
        }
    }

    @Override
    public void onMessage(okhttp3.WebSocket webSocket, String text) {
//       System.out.println(text);
        T event;
        try {
            switch (ApiServiceGenerator.getExchangeName()){
                case "binance":
                    event = this.objectReader.readValue(text);
                    this.callback.onResponse(event);
                    break;

                case "okex":
                    if(!text.contains("event") && !text.contains("pong")) {
                        event = this.objectReader.readValue(text);
                        this.callback.onResponse(event);
                    }

                    break;

                case "kucoin":
                    if(text.equals("pong")){
                        pong[1] = text;
                    }
                    if(text.contains("welcome")){
                        //ID,チャンネル購読の文字列転入
                        ApiServiceGenerator.WSBsend(text+ sendtype);
                        //7sごとにping
                        scheduler.scheduleAtFixedRate(()->{
                            ApiServiceGenerator.WSBsend("ping");
                        },3,7, TimeUnit.SECONDS);

                    }else if(text.contains("data")){
                        event = this.objectReader.readValue(text);
                        this.callback.onResponse(event);
                    }
                    break;
                    default:break;
            }


        } catch (IOException | InterruptedException e) {
            throw new Exception(e);
        }

    }

    @SneakyThrows
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray())).readObject();

        System.out.println(new String(Hex.decodeHex(bytes.hex().toCharArray()),"UTF-8"));

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
