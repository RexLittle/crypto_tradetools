package com.crypto_tools.service.impl;


import com.crypto_tools.controller.WebSocketDispatcher;
import com.crypto_tools.controller.config.SessionPool;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.binance.rest.Binance24hPriceResp;
import com.crypto_tools.exchangapi.binance.websocket.BinanceAllMarketTickersResp;
import com.crypto_tools.exchangapi.binance.websocket.BinanceWebSocketClient;
import com.crypto_tools.exchangapi.kucoin.rest.KuCoinRestClient;
import com.crypto_tools.exchangapi.kucoin.rest.allTickers.TickerData;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinMarketResp;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinWebSocketClient;
import com.crypto_tools.exchangapi.okex.rest.OkexRestClient;
import com.crypto_tools.exchangapi.okex.websocket.OkexTickersResp;
import com.crypto_tools.exchangapi.okex.websocket.OkexWebSocketClient;
import com.crypto_tools.model.ExchangeMarketData;
import com.crypto_tools.model.JsonFormatBean;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestClient;
import com.crypto_tools.exchangapi.binance.rest.BinanceTickeResp;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerResp;
import com.crypto_tools.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private static Logger log = LoggerFactory.getLogger(ServiceImpl.class);
    private DecimalFormat decimalFormat = new DecimalFormat("0.##");

    @Override
    public List<JsonFormatBean> Arbitrage() {
        //Biance

        BinanceRestClient biClient =  ExchangeFactory.createExchangeFactory().createBinanceClients().createRestClient();
        //获取全部交易对
        List<BinanceTickeResp> biAllPrices = biClient.getAllTokenBaseData();
        String binanceData = biAllPrices.toString().replaceAll("BinanceTickerData|\\[|\\]|symbol=|price=","")
                .replace(" ","");
        String[] binanceDatabArray = binanceData.split(",");
        LinkedHashMap<String, String> binanceDataMap = new LinkedHashMap<>();
        int i = 0;
        int j = 1;
        for (i = 0; i < binanceDatabArray.length-1; i=i+2) {
            binanceDataMap.put(binanceDatabArray[i],binanceDatabArray[j]);
            j= j+2;
        }

        System.out.println(binanceData);


        //Bithumb
        OkexTickerResp okRest = ExchangeFactory.createExchangeFactory().createOkexClients().createRestClient().getAllTokenBaseData();
        OkexTickerData[] bitAllPrices = okRest.getData();
        LinkedHashMap<String, String> bithumbMap = new LinkedHashMap<>();
        int k = 0;
        while (true) {
            try {
                bithumbMap.put(bitAllPrices[k].getInstId().replace("-",""),bitAllPrices[k].getLast());
                k++;
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        //両取引所の比べ
        Iterator<String> BiIter = binanceDataMap.keySet().iterator();
        Iterator<String> BitIter = bithumbMap.keySet().iterator();

        List<JsonFormatBean> jsonFormatBeans = new ArrayList<>();
        while(BiIter.hasNext()) {
            String biKey = BiIter.next();
            while (BitIter.hasNext()) {
                String bitKey = BitIter.next();
                if(biKey.equals(bitKey)) {
                    jsonFormatBeans.add(new JsonFormatBean(biKey,binanceDataMap.get(biKey),bithumbMap.get(bitKey)));
                }
            }
            BitIter = bithumbMap.keySet().iterator();
        }
        /**
         *     //Biance
         *         BinanceApiClientFactory biFactory = BinanceApiClientFactory.newInstance();
         *         BinanceApiRestClient biClient = biFactory.newRestClient();
         *         //获取全部交易对
         *         List<TickerPrice> biAllPrices = biClient.getAllPrices();
         *         String binanceData = biAllPrices.toString().replaceAll("TickerPrice|\\[|\\]|symbol=|price=","")
         *                 .replace(" ","");
         *         String[] binanceDatabArray = binanceData.split(",");
         *         LinkedHashMap<String, String> binanceDataMap = new LinkedHashMap<>();
         *         int i = 0;
         *         int j = 1;
         *         for (i = 0; i < binanceDatabArray.length-1; i=i+2) {
         *             binanceDataMap.put(binanceDatabArray[i],binanceDatabArray[j]);
         *             j= j+2;
         *         }
         *
         *
         *         //Bithumb
         *         BithumbApiClientFactory bitFactory = BithumbApiClientFactory.newInstance();
         *         BithumbApiRestClient bitClient = bitFactory.newRestClient();
         *         com.price_gap_site.bithumb.bean.TickerPrice bitAllPrices = bitClient.getAllPrices();
         *         LinkedHashMap<String, String> bithumbMap = new LinkedHashMap<>();
         *         int k = 0;
         *         while (true) {
         *             try {
         *                 bithumbMap.put(bitAllPrices.getData()[k].getS().replace("-",""),bitAllPrices.getData()[k].getC());
         *                 k++;
         *             } catch (ArrayIndexOutOfBoundsException e) {
         *                 break;
         *             }
         *         }
         *
         *
         *         //両取引所の比べ
         *         Iterator<String> BiIter = binanceDataMap.keySet().iterator();
         *         Iterator<String> BitIter = bithumbMap.keySet().iterator();
         *         List<JsonFormatBean> jsonFormatBeans = new ArrayList<>();
         *         while(BiIter.hasNext()) {
         *             String biKey = BiIter.next();
         *             while (BitIter.hasNext()) {
         *                 String bitKey = BitIter.next();
         *                 if(biKey.equals(bitKey)) {
         *                     jsonFormatBeans.add(new JsonFormatBean(biKey,binanceDataMap.get(biKey),bithumbMap.get(bitKey)));
         *                 }
         *             }
         *             BitIter = bithumbMap.keySet().iterator();
         *         }
         *
         */

        return jsonFormatBeans;

    }

    @Override
    public List<ExchangeMarketData> binanceMarkeDataRest() {
        BinanceRestClient rest = ExchangeFactory.createExchangeFactory().createBinanceClients().createRestClient();
        List<ExchangeMarketData> mData = new ArrayList<>();
        List<Binance24hPriceResp> restData = rest.get24hPrice();
        for (int i = 0; i < restData.size(); i++) {
            ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
            exchangeMarketData.setPair(restData.get(i).getSymbol());
            exchangeMarketData.setLastprice(restData.get(i).getLastPrice().replaceAll("0+?$", "").replaceAll("[.]$", ""));
            exchangeMarketData.set_24hChanges(restData.get(i).getPriceChangePercent().replaceAll("0+?$", "").replaceAll("[.]$", ""));
            exchangeMarketData.set_24hVolume(restData.get(i).getVolume().replaceAll("0+?$", "").replaceAll("[.]$", ""));
            mData.add(exchangeMarketData);
        }
//        SessionPool.sendMessage(userId,mData.toString());

        return mData;
    }

    @Override
    public Closeable binanceMarketDataWebSocket(String userId) throws InterruptedException, IOException {
        ApiServiceGenerator.userId = userId;
        BinanceWebSocketClient webSocket = ExchangeFactory.createExchangeFactory().createBinanceClients().createWebSocketClient();
        List<ExchangeMarketData> mData = new ArrayList<>();
        Closeable wb = webSocket.MarketTickersEvent(new CallBack<List<BinanceAllMarketTickersResp>>() {
            @Override
            public void onResponse(List<BinanceAllMarketTickersResp> var1) throws IOException, InterruptedException {
                for (int i = 0; i < var1.size(); i++) {
                    Thread.sleep(3000);
                    ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
                    exchangeMarketData.setPair(var1.get(i).getS());
                    exchangeMarketData.setLastprice(var1.get(i).getC());
                    exchangeMarketData.set_24hChanges(var1.get(i).getP());
                    exchangeMarketData.set_24hVolume(var1.get(i).getV());
                    mData.add(exchangeMarketData);
                }
                if(SessionPool.sessions.get(userId).isOpen()){
                    SessionPool.sendMessage(userId, mData.toString());
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                log.info("binanceMarketDataWebSocketでエラー出ました、原因は{" + cause + "}");
            }
        });
         return wb;
    }

    @Override
    public List<ExchangeMarketData> okexMarketDataRest() {
        OkexRestClient rest = ExchangeFactory.createExchangeFactory().createOkexClients().createRestClient();
        List<ExchangeMarketData> mData = new ArrayList<>();
        OkexTickerData[] restData = rest.getAllTokenBaseData().getData();
        for (int i = 0; i < restData.length; i++) {
            ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
            exchangeMarketData.setPair(restData[i].getInstId().replace("-",""));
            exchangeMarketData.setLastprice(restData[i].getLast());
            exchangeMarketData.set_24hChanges(decimalFormat.format((Double.valueOf(restData[i].getLast())
                    - Double.valueOf(restData[i].getOpen24h())) /Double.valueOf(restData[i].getOpen24h()) * 100));
            exchangeMarketData.set_24hVolume(restData[i].getVol24h());
            mData.add(exchangeMarketData);
        }
//        SessionPool.sendMessage(userId,mData.toString());

        return mData;
    }

    @Override
    public Closeable okexMarketDataWebSocket(String userId) throws InterruptedException {
        ApiServiceGenerator.userId = userId;
        OkexWebSocketClient webSocket =  ExchangeFactory.createExchangeFactory().createOkexClients().createWebSocketClient();
        List<ExchangeMarketData> mData = new ArrayList<>();
        Closeable wb = webSocket.MarketTickersEvent(new CallBack<OkexTickersResp>() {
            @Override
            public void onResponse(OkexTickersResp var1) throws InterruptedException, IOException {
                Thread.sleep(3000);
                    OkexTickerData[] wsbData = var1.getData();
                    for (int i = 0; i < wsbData.length; i++) {
                        ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
                        exchangeMarketData.setPair(wsbData[i].getInstId().replace("-",""));
                        exchangeMarketData.setLastprice(wsbData[i].getLast());
                        exchangeMarketData.set_24hChanges(String.valueOf((Double.valueOf(wsbData[i].getLast())
                                - Double.valueOf(wsbData[i].getOpen24h())) /Double.valueOf(wsbData[i].getOpen24h()) * 100));
                        exchangeMarketData.set_24hVolume(wsbData[i].getVol24h());
                        mData.add(exchangeMarketData);
                    }
                if(SessionPool.sessions.get(userId).isOpen()){
                    SessionPool.sendMessage(userId, mData.toString());
                }else{
                    System.out.println("ok已删除");
//                    WebSocketDispatcher.closeWBClient_Exchange();
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                log.info("okexMarketDataWebSocketでエラー出ました、原因は{" + cause + "}");
            }
        });

        return wb;

    }

    @Override
    public List<ExchangeMarketData> kuCoinMarketDataRest() {
        KuCoinRestClient rest = ExchangeFactory.createExchangeFactory().createKuCoinClients().createRestClient();
        TickerData[] restData = rest.getAllTokenData().getData().getTicker();
        List<ExchangeMarketData> mData = new ArrayList<>();
        for (int i = 0; i < restData.length; i++) {
            ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
            exchangeMarketData.setPair(restData[i].getSymbol().replace("-",""));
            exchangeMarketData.setLastprice(restData[i].getLast());
            exchangeMarketData.set_24hChanges(decimalFormat.format(Double.valueOf(restData[i].getChangeRate()) * 100 ));
            exchangeMarketData.set_24hVolume(restData[i].getVol());
            mData.add(exchangeMarketData);
        }
//        SessionPool.sendMessage(userId,mData.toString());
        return mData;
    }

    @Override
    public Closeable kuCoinMarketDataWebSocket(String userId) throws InterruptedException {
        ApiServiceGenerator.userId = userId;
        KuCoinWebSocketClient webSocket = ExchangeFactory.createExchangeFactory().createKuCoinClients().createWebSocketClient();
        Closeable wb = webSocket.Market(new CallBack<KuCoinMarketResp>() {
            @Override
            public void onResponse(KuCoinMarketResp var1) throws InterruptedException, IOException {
                Thread.sleep(3000);
                KuCoinMarketResp.MarketRespData.MarketData wsbdata = var1.getData().getData();
                ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
                exchangeMarketData.setPair(wsbdata.getBaseCurrency()+wsbdata.getQuoteCurrency().replace("-",""));
                exchangeMarketData.setLastprice(wsbdata.getLastTradedPrice());
                exchangeMarketData.set_24hChanges(wsbdata.getChangeRate());
                exchangeMarketData.set_24hVolume(wsbdata.getVol());

                if(SessionPool.sessions.get(userId).isOpen()){
                    SessionPool.sendMessage(userId, exchangeMarketData.toString());
                }else{
                    System.out.println("kucoin已删除");
//                    WebSocketDispatcher.closeWBClient_Exchange();
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                log.info("KuCoinWebSocketでエラーが出ました、原因は{" + cause + "}");
            }
        });
        return wb;
    }


}
