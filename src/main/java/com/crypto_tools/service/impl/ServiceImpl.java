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
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private static Logger log = LoggerFactory.getLogger(ServiceImpl.class);
    private DecimalFormat decimalFormat = new DecimalFormat("0.##");

    @Override
    public List<JsonFormatBean> compare(String ex1,String ex2) {
        ExchangeFactory exchangeFactory = ExchangeFactory.createExchangeFactory();
        BinanceRestClient binanceRest;
        OkexRestClient okexRest;
        KuCoinRestClient kuCoinRest;
        List<Binance24hPriceResp> binanceData;
        OkexTickerData[] okexData;
        TickerData[] kuCoinData;
        LinkedHashMap<String, String> dataMap1 = new LinkedHashMap<>();
        LinkedHashMap<String, String> dataMap2 = new LinkedHashMap<>();
        switch(ex1){
            case "binance":
                binanceRest = exchangeFactory.createBinanceClients().createRestClient();
                binanceData = binanceRest.get24hPrice();
                for(Binance24hPriceResp data:binanceData){dataMap1.put(data.getSymbol(),data.getLastPrice());}
                break;
            case "okex":
                okexRest = exchangeFactory.createOkexClients().createRestClient();
                okexData = okexRest.getAllTokenBaseData().getData();
                for(OkexTickerData data:okexData){
                    dataMap1.put(data.getInstId().replace("-",""),data.getLast());
                }
                break;
            case "kucoin":
                kuCoinRest = exchangeFactory.createKuCoinClients().createRestClient();
                kuCoinData = kuCoinRest.getAllTokenData().getData().getTicker();
                for(TickerData data:kuCoinData){dataMap1.put(data.getSymbol().replace("-",""),data.getLast());}
                break;
            default:break;
        }

        switch(ex2){
            case "binance":
                binanceRest = exchangeFactory.createBinanceClients().createRestClient();
                binanceData = binanceRest.get24hPrice();
                for(Binance24hPriceResp data:binanceData){dataMap2.put(data.getSymbol(),data.getLastPrice());}
                break;
            case "okex":
                okexRest = exchangeFactory.createOkexClients().createRestClient();
                okexData = okexRest.getAllTokenBaseData().getData();
                for(OkexTickerData data:okexData){
                    dataMap2.put(data.getInstId().replace("-",""),data.getLast());
                }
                break;
            case "kucoin":
                kuCoinRest = exchangeFactory.createKuCoinClients().createRestClient();
                kuCoinData = kuCoinRest.getAllTokenData().getData().getTicker();
                for(TickerData data:kuCoinData){dataMap2.put(data.getSymbol().replace("-",""),data.getLast());}
                break;
            default:break;

        }

        //両取引所の比べ
        Iterator<String> dm1 = dataMap1.keySet().iterator();
        Iterator<String> dm2 = dataMap2.keySet().iterator();
        String ex1price;
        String ex2price;
        Double ex1priceNum;
        Double ex2priceNum;
        BigDecimal gap;
        BigDecimal gapPercent;
        BigDecimal subtract1;
        BigDecimal subtract2;
        List<JsonFormatBean> jsonFormatBeans = new ArrayList<>();
        while(dm1.hasNext()) {
            String dm1Key = dm1.next();
            while (dm2.hasNext()) {
                String dm2Key = dm2.next();
                if(dm1Key.equals(dm2Key)) {
                    ex1price = dataMap1.get(dm1Key).replaceAll("0+?$", "").replaceAll("[.]$", "");
                    ex2price = dataMap2.get(dm2Key).replaceAll("0+?$", "").replaceAll("[.]$", "");
                    ex1priceNum = Double.valueOf(ex1price);
                    ex2priceNum  = Double.valueOf(ex2price);
                    subtract1 = new BigDecimal(ex1price).subtract(new BigDecimal(ex2price));
                    subtract2 = new BigDecimal(ex2price).subtract(new BigDecimal(ex1price));
                    gap = ex1priceNum>ex2priceNum?subtract1:subtract2;
                    if (0 != BigDecimal.ZERO.compareTo(new BigDecimal(ex1price)) && 0 != BigDecimal.ZERO.compareTo(new BigDecimal(ex2price))) {
                            gapPercent = ex1priceNum>ex2priceNum?subtract1.divide(new BigDecimal(ex2price), 4).multiply(new BigDecimal("100")):
                                    subtract2.divide(new BigDecimal(ex1price), 4).multiply(new BigDecimal("100"));
                    }else{
                        gapPercent = new BigDecimal("0");
                    }


                    jsonFormatBeans.add(new JsonFormatBean(dm1Key,ex1price,ex2price,
                            gap.toString(),gapPercent.toString()));
                }
            }
            dm2 = dataMap2.keySet().iterator();
        }

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
