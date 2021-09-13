package com.crypto_tools.service.impl;


import com.crypto_tools.exchangapi.okex.rest.OkexRestClient;
import com.crypto_tools.model.ExchangeMarketData;
import com.crypto_tools.model.JsonFormatBean;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestClient;
import com.crypto_tools.exchangapi.binance.rest.BinanceTickeResp;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerResp;
import com.crypto_tools.service.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@org.springframework.stereotype.Service
public class Serviceimpl implements Service {







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
    public List<ExchangeMarketData> marketData() {

        List<ExchangeMarketData> mData = new ArrayList<>();

        ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
        OkexRestClient rest = factory.createOkexClients().createRestClient();
        OkexTickerData[] restData = rest.getAllTokenBaseData().getData();

        for (int i = 0; i < restData.length; i++) {
            ExchangeMarketData exchangeMarketData = new ExchangeMarketData();
            exchangeMarketData.setPair(restData[i].getInstId());
            exchangeMarketData.setLastprice(restData[i].getLast());
            //need fix
            exchangeMarketData.set_24hChanges(restData[i].getLast());
            exchangeMarketData.set_24hHigh(restData[i].getHigh24h());
            exchangeMarketData.set_24hLow(restData[i].getLow24h());
            //need fix
            exchangeMarketData.setMarketCap(restData[i].getHigh24h());
            exchangeMarketData.set_24hVolume(restData[i].getVol24h());
        }

        return null;
    }


}
