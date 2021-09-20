package com.crypto_tools.controller;

import com.crypto_tools.controller.config.SessionPool;
import com.crypto_tools.service.impl.ServiceImpl;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketDispatcher {

    private static ServiceImpl service = new ServiceImpl();
    public static Map<String, Closeable> WBSClient = new ConcurrentHashMap<>();

    public static void dispatcher(String userId,String message) throws InterruptedException, IOException {
        switch(message){
            case "binance":sendBinanceData(userId);
                        break;
            case "okex":sendOkexData(userId);
                        break;
            case "kucoin":sendKuCoinData(userId);
                        break;
            default:break;
        }
    }


    public static void sendFirst(String userId) throws InterruptedException, IOException {
        System.out.println("binance0");
        service.binanceMarkeDataRest(userId);
        service.binanceMarketDataWebSocket(userId);

    }

    public static void sendBinanceData(String userId) throws InterruptedException, IOException {
        service.binanceMarketDataWebSocket(userId).close();
        System.out.println("binance1");
        service.binanceMarkeDataRest(userId);
        service.binanceMarketDataWebSocket(userId);
    }
    public static void sendOkexData(String userId) throws InterruptedException, IOException {
//        System.out.println(WBSClient.get(userId));
//        WBSClient.get(userId).close();
//        System.out.println("okex1");
//        service.okexMarketDataRest(userId);
//        WBSClient.put(userId,service.okexMarketDataWebSocket(userId));
    }
    public static void sendKuCoinData(String userId) throws InterruptedException, IOException {
//        System.out.println(WBSClient.get(userId));
//        WBSClient.get(userId).close();
//        System.out.println("kucoin1");
//        service.kuCoinMarketDataRest(userId);
//        WBSClient.put(userId,service.okexMarketDataWebSocket(userId));
    }

    public static void closeWBClient_Exchange(String userId) throws IOException {
        System.out.println("方法关闭了");
        WBSClient.get(userId).close();
    }




}
