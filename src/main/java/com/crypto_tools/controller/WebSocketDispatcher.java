package com.crypto_tools.controller;
import com.crypto_tools.controller.config.SessionPool;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketDispatcher {
    private static Logger log = LoggerFactory.getLogger(WebSocketDispatcher.class);
    private static ServiceImpl service = new ServiceImpl();;
    public static Map<String, ServiceImpl> services = new ConcurrentHashMap<>();
    public static Closeable wb;
//
//    public static void sendBinanceData(String userId) throws InterruptedException, IOException {
//        log.info("user ID:" + userId + "にbinanceのデータ送信します");
//        service.binanceMarkeDataRest(userId);
//        wb = service.binanceMarketDataWebSocket(userId);
//
//
//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @SneakyThrows
//                public void run() {
//                    if(!SessionPool.havingSession(userId)){
//                        wb.close();
//                    }
//                }
//            }, 1000, 2000);
//
//
//    }
//    public static void sendOkexData(String userId) throws InterruptedException, IOException {
//        log.info("user ID:" + userId + "にokexのデータ送信します");
//        service.okexMarketDataRest(userId);
//        wb = service.okexMarketDataWebSocket(userId);
//    }
//    public static void sendKuCoinData(String userId) throws InterruptedException, IOException {
//        log.info("userID:" + userId + "にkucoinのデータ送信します");
//        service.kuCoinMarketDataRest(userId);
//        wb = service.kuCoinMarketDataWebSocket(userId);
//    }
//
//    public  static void closeWBClient_Exchange() throws IOException, InterruptedException {
//        log.info("exchange_wb切断します");
//        wb.close();
//    }




}
