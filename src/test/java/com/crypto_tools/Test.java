package com.crypto_tools;

import com.binance.api.client.*;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.*;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerData;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerResp;
import com.crypto_tools.exchangapi.kucoin.rest.PublicToken;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinTickerResp;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;
import com.crypto_tools.model.ExchangeMarketData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestClient;
import com.crypto_tools.exchangapi.binance.websocket.BinanceMiniTickerResp;
import com.crypto_tools.exchangapi.okex.rest.OkexRestClient;
import com.crypto_tools.exchangapi.okex.websocket.OkexTickersResp;
import com.crypto_tools.exchangapi.okex.websocket.OkexWebSocketClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;


import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@SpringBootTest(classes = Test.class)
class Test {


	@org.junit.jupiter.api.Test
	public void binance() {
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
		BinanceApiRestClient client = factory.newRestClient();
		//获取全部交易对
		List<TickerPrice> allPrices = client.getAllPrices();
		TickerStatistics tickerStatistics = client.get24HrPriceStatistics("NEOETH");
		String binanceData = allPrices.toString().replaceAll("TickerPrice|\\[|\\]|symbol=|price=| ","");
		String[] binanceDatabArray = binanceData.split(",");
		LinkedHashMap<String, String> binanceDataMap = new LinkedHashMap<>();
		int i = 0;
		int j = 1;
		for (i = 0; i < binanceDatabArray.length-1; i=i+2) {
				binanceDataMap.put(binanceDatabArray[i],binanceDatabArray[j]);
				j= j+2;
		}

		System.out.println(binanceData);
		System.out.println(Arrays.toString(binanceDatabArray));
		System.out.println(binanceDataMap);
	}




	@org.junit.jupiter.api.Test
	public void test() throws JsonProcessingException {
	    //Biance
		BinanceApiClientFactory biFactory = BinanceApiClientFactory.newInstance();
		BinanceApiRestClient biClient = biFactory.newRestClient();
		//トレードぺーア獲得
		List<TickerPrice> biAllPrices = biClient.getAllPrices();
		String binanceData = biAllPrices.toString().replaceAll("TickerPrice|\\[|\\]|symbol=|price=","")
													.replace(" ","");
		String[] binanceDatabArray = binanceData.split(",");
		LinkedHashMap<String, String> binanceDataMap = new LinkedHashMap<>();
		int i = 0;
		int j = 1;
		for (i = 0; i < binanceDatabArray.length-1; i=i+2) {
			binanceDataMap.put(binanceDatabArray[i],binanceDatabArray[j]);
			j= j+2;
		}


		/**
		 * 	//Bithumb
		 * 		BithumbApiClientFactory bitFactory = BithumbApiClientFactory.newInstance();
		 * 		BithumbApiRestClient bitClient = bitFactory.newRestClient();
		 * 		com.crypto_tools.bithumb.bean.TickerPrice bitAllPrices = bitClient.getAllPrices();
		 * 		LinkedHashMap<String, String> bithumbMap = new LinkedHashMap<>();
		 * 		int k = 0;
		 * 		while (true) {
		 * 			try {
		 * 				bithumbMap.put(bitAllPrices.getData()[k].getS().replace("-",""),bitAllPrices.getData()[k].getC());
		 * 				k++;
		 *                        } catch (ArrayIndexOutOfBoundsException e) {
		 * 				break;
		 *            }* 		}
		 *
		 * 		ObjectMapper objectMapper = new ObjectMapper();
		 *
		 *
		 *
		 *
		 * 		//両取引所の比べ
		 * 		Iterator<String> BiIter = binanceDataMap.keySet().iterator();
		 * 		Iterator<String> BitIter = bithumbMap.keySet().iterator();
		 * 		List<JsonFormatBean> jsonFormatBeans = new ArrayList<>();
		 * 		while(BiIter.hasNext()) {
		 * 			String biKey = BiIter.next();
		 * 			while (BitIter.hasNext()) {
		 * 				String bitKey = BitIter.next();
		 * 				if(biKey.equals(bitKey)) {
		 * 					jsonFormatBeans.add(new JsonFormatBean(biKey,binanceDataMap.get(biKey),bithumbMap.get(bitKey)));
		 *                }
		 *            }
		 * 			BitIter = bithumbMap.keySet().iterator();
		 *        }
		 * 		String a = objectMapper.writeValueAsString(jsonFormatBeans);
		 * 		System.out.println(a);
		 */


	}

	@org.junit.jupiter.api.Test
	public void binanceWebSocket() throws InterruptedException {
		BinanceApiClientFactory biFactory = BinanceApiClientFactory.newInstance();
		BinanceApiWebSocketClient binanceApiWebSocketClient = biFactory.newWebSocketClient();
		binanceApiWebSocketClient.onAllMarketTickersEvent(new BinanceApiCallback<List<TickerEvent>>() {
			@Override
			public void onResponse(List<TickerEvent> response) {
				System.out.println(response.get(1).getSymbol());
			}
		});

		while (true) {
			Thread.sleep(1000);
		}
	}



	@org.junit.jupiter.api.Test
	public void binanceWebSocketTest() throws InterruptedException, JsonProcessingException {
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		factory.createBinanceClients().createWebSocketClient().MarketTickersEvent(new CallBack<List<BinanceMiniTickerResp>>() {
			@Override
			public void onResponse(List<BinanceMiniTickerResp> var1) {
				System.out.println(var1.toString());
			}

			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

		while (true) {
			Thread.sleep(1000);
		}
	}

	@org.junit.jupiter.api.Test
	public void binanceTradePair() {
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		BinanceRestClient rest = factory.createBinanceClients().createRestClient();
		System.out.println(rest.getAllTokenBaseData().get(0).getPrice());
	}

	@org.junit.jupiter.api.Test
	public void OkexTradePair() {
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		OkexRestClient rest = factory.createOkexClients().createRestClient();
		System.out.println(Arrays.toString(rest.getAllTokenBaseData().getData()));
	}

	@org.junit.jupiter.api.Test
	public void OkexWebSocketTest() throws InterruptedException {
		OkexWebSocketClient webSocketClient =  ExchangeFactory.createExchangeFactory().createOkexClients().createWebSocketClient();

		webSocketClient.MarketTickersEvent(new CallBack<OkexTickersResp>() {
			@Override
			public void onResponse(OkexTickersResp var1) {
				System.out.println(Arrays.toString(var1.getData()));

			}


			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

		while (true){
			Thread.sleep(2000);
		}
	}

	@org.junit.jupiter.api.Test
	public void HuobiRestTest() {
		HuobiTickerResp allTokenBaseData = ExchangeFactory.createExchangeFactory().createHuobiClients().createRestClient().getAllTokenBaseData();
		System.out.println(Arrays.toString(allTokenBaseData.getData()));
	}

	@org.junit.jupiter.api.Test
	public void HuobiWebSocketTest() throws InterruptedException {
		ExchangeFactory.createExchangeFactory().createHuobiClients().createWebSocketClient().MarketTickersEvent(new CallBack<HuobiTickerData>() {
			@Override
			public void onResponse(HuobiTickerData var1) {
				System.out.println(var1);
			}


			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

		while (true) {
			Thread.sleep(2000);
		}
	}




	@org.junit.jupiter.api.Test
	public void kucoinpublictoken() throws InterruptedException {
		PublicToken publicToken = new ExchangeFactory().createKuCoinClients().createRestClient().getPublicToken();
		System.out.println(publicToken.getData().getToken());

		while (true) {
			Thread.sleep(2000);
		}
	}

	@org.junit.jupiter.api.Test
	public void kucoinwebsocket() throws InterruptedException {
		new ExchangeFactory().createKuCoinClients().createWebSocketClient().MarketTickersEvent(new CallBack<KuCoinTickerResp>() {
			@Override
			public void onResponse(KuCoinTickerResp var1) {
				System.out.println(var1);
			}

			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});



		while (true) {
			Thread.sleep(2000);
		}
	}


	@org.junit.jupiter.api.Test
	public void timeTest(){
		long startTime = System.currentTimeMillis();

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

		long endTime = System.currentTimeMillis();
		System.out.println("time：" + (endTime - startTime) + "ms");

	}


}
