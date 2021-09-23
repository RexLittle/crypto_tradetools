package com.crypto_tools;

import com.binance.api.client.*;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.*;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import com.crypto_tools.exchangapi.binance.websocket.BinanceAllMarketTickersResp;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerData;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerResp;
import com.crypto_tools.exchangapi.kucoin.rest.PublicToken;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinMarketResp;
import com.crypto_tools.exchangapi.kucoin.websocket.KuCoinTickerResp;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;
import com.crypto_tools.model.ExchangeMarketData;
import com.crypto_tools.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestClient;
import com.crypto_tools.exchangapi.binance.websocket.BinanceMiniTickerResp;
import com.crypto_tools.exchangapi.okex.rest.OkexRestClient;
import com.crypto_tools.exchangapi.okex.websocket.OkexTickersResp;
import com.crypto_tools.exchangapi.okex.websocket.OkexWebSocketClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.Closeable;
import java.io.IOException;
import java.util.*;

import static com.crypto_tools.exchangapi.ApiServiceGenerator.userId;

@SpringBootTest(classes = functionTest.class)
class functionTest {


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
	public void binanceWebSocketTest() throws InterruptedException, IOException {
		userId = "123";
		 ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
//		factory.createBinanceClients().createWebSocketClient().MiniMarketTickersEvent(new CallBack<List<BinanceMiniTickerResp>>() {
//			@Override
//			public void onResponse(List<BinanceMiniTickerResp> var1) {
//				System.out.println(var1.toString());
//			}
//
//			@Override
//			public void onFailure(Throwable cause) {
//				System.out.println(cause);
//			}
//		});

		Closeable ws = factory.createBinanceClients().createWebSocketClient().MarketTickersEvent(new CallBack<List<BinanceAllMarketTickersResp>>() {
			@Override
			public void onResponse(List<BinanceAllMarketTickersResp> var1) {
				System.out.println("币安在跑");
//				System.out.println(var1);
			}

			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

		ws.close();


		while (true) {
			Thread.sleep(1000);
		}
	}

	@org.junit.jupiter.api.Test
	public void binanceRest() {
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		BinanceRestClient rest = factory.createBinanceClients().createRestClient();
		System.out.println(rest.getAllTokenBaseData().get(0).getPrice());
	}

	@org.junit.jupiter.api.Test
	public void OkexRest() {
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		OkexRestClient rest = factory.createOkexClients().createRestClient();
		System.out.println(Arrays.toString(rest.getAllTokenBaseData().getData()));
	}

	@org.junit.jupiter.api.Test
	public void OkexWebSocketTest() throws InterruptedException {

		userId = "123";
		OkexWebSocketClient webSocketClient =  ExchangeFactory.createExchangeFactory().createOkexClients().createWebSocketClient();

		webSocketClient.MarketTickersEvent(new CallBack<OkexTickersResp>() {
			@Override
			public void onResponse(OkexTickersResp var1) {
//				System.out.println(Arrays.toString(var1.getData()));

			}


			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

		while(true){
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
		new ExchangeFactory().createKuCoinClients().createWebSocketClient().Market(new CallBack<KuCoinMarketResp>() {
			@Override
			public void onResponse(KuCoinMarketResp var1) {
				System.out.println(var1);
			}

			@Override
			public void onFailure(Throwable cause) {
				System.out.println(cause);
			}
		});

//		new ExchangeFactory().createKuCoinClients().createWebSocketClient().AllTickersLastPrice(new CallBack<KuCoinTickerResp>() {
//			@Override
//			public void onResponse(KuCoinTickerResp var1) {
//				System.out.println(var1);
//			}
//		});



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
			exchangeMarketData.set_24hChanges(String.valueOf((Double.valueOf(restData[i].getLast())
					- Double.valueOf(restData[i].getOpen24h())) /Double.valueOf(restData[i].getOpen24h()) * 100));
			exchangeMarketData.set_24hVolume(restData[i].getVol24h());
		}

		long endTime = System.currentTimeMillis();
		System.out.println("time：" + (endTime - startTime) + "ms");

	}


	@org.junit.jupiter.api.Test
	public void stringToByte() {
		String s = "{\"channel\":\"tickers\",\"instId\":\"BCD-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MDT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ORS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LINK-BTC\"},{\"channel\":\"tickers\",\"instId\":\"NEO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"KNC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NULS-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DAI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LUNA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XEM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"TRIO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BHP-BTC\"},{\"channel\":\"tickers\",\"instId\":\"PAX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ATOM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"AGLD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RSR-ETH\"},{\"channel\":\"tickers\",\"instId\":\"BTG-BTC\"},{\"channel\":\"tickers\",\"instId\":\"IOST-ETH\"},{\"channel\":\"tickers\",\"instId\":\"CRV-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LSK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XRP-USDK\"},{\"channel\":\"tickers\",\"instId\":\"MITH-BTC\"},{\"channel\":\"tickers\",\"instId\":\"IOTA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MOF-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XRP-USDC\"},{\"channel\":\"tickers\",\"instId\":\"USDC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CRO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"SWFTC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"APM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AAVE-ETH\"},{\"channel\":\"tickers\",\"instId\":\"VIB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"QTUM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BCH-USDK\"},{\"channel\":\"tickers\",\"instId\":\"INT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CSPR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XEC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AXS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XRP-ETH\"},{\"channel\":\"tickers\",\"instId\":\"APIX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BCH-USDC\"},{\"channel\":\"tickers\",\"instId\":\"ELF-BTC\"},{\"channel\":\"tickers\",\"instId\":\"PNK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"VELO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ITC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WING-USDT\"},{\"channel\":\"tickers\",\"instId\":\"YFI-BTC\"},{\"channel\":\"tickers\",\"instId\":\"WBTC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"CTXC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MANA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BCH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LON-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ETC-USDC\"},{\"channel\":\"tickers\",\"instId\":\"VSYS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETH-DAI\"},{\"channel\":\"tickers\",\"instId\":\"SWRV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETC-USDK\"},{\"channel\":\"tickers\",\"instId\":\"GAS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FRONT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CELR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CELO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"1INCH-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ENJ-BTC\"},{\"channel\":\"tickers\",\"instId\":\"KONO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SAND-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LAT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NDN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KLAY-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SUN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AAC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CRO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CVT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KLAY-BTC\"},{\"channel\":\"tickers\",\"instId\":\"FLM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ETC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"XMR-ETH\"},{\"channel\":\"tickers\",\"instId\":\"SOL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"API3-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CRO-USDK\"},{\"channel\":\"tickers\",\"instId\":\"PHA-ETH\"},{\"channel\":\"tickers\",\"instId\":\"RVN-BTC\"},{\"channel\":\"tickers\",\"instId\":\"YFI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SWFTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"YFI-ETH\"},{\"channel\":\"tickers\",\"instId\":\"TRA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"SBTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"NMR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XLM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"UTK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FUN-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CHAT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AAVE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PAX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TCT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOGE-USDK\"},{\"channel\":\"tickers\",\"instId\":\"COMP-BTC\"},{\"channel\":\"tickers\",\"instId\":\"API3-ETH\"},{\"channel\":\"tickers\",\"instId\":\"LON-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PAY-BTC\"},{\"channel\":\"tickers\",\"instId\":\"THETA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MATIC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOGE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"YFII-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ANW-USDT\"},{\"channel\":\"tickers\",\"instId\":\"YOU-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PMA-USDK\"},{\"channel\":\"tickers\",\"instId\":\"PROPS-ETH\"},{\"channel\":\"tickers\",\"instId\":\"RFUEL-ETH\"},{\"channel\":\"tickers\",\"instId\":\"XRP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LPT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XEM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CHZ-BTC\"},{\"channel\":\"tickers\",\"instId\":\"TAI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EDEN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PRQ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OMI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CRV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZEN-BTC\"},{\"channel\":\"tickers\",\"instId\":\"EGLD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WBTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PLG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AST-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LINK-ETH\"},{\"channel\":\"tickers\",\"instId\":\"NU-BTC\"},{\"channel\":\"tickers\",\"instId\":\"HC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NEO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RNT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BLOC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"HC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"YOU-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BNT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EOS-USDC\"},{\"channel\":\"tickers\",\"instId\":\"MANA-ETH\"},{\"channel\":\"tickers\",\"instId\":\"NANO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DCR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CHZ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BCX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"USDC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EOS-USDK\"},{\"channel\":\"tickers\",\"instId\":\"WXT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"INT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"NAS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MINA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EOS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"GRT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PHA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SKL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KSM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BHP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MXT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"POLS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FSN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WNCG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"IOST-USDT\"},{\"channel\":\"tickers\",\"instId\":\"VSYS-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LTC-USDK\"},{\"channel\":\"tickers\",\"instId\":\"EGT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"STRK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRADE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LAMB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LTC-USDC\"},{\"channel\":\"tickers\",\"instId\":\"BTC-USDC\"},{\"channel\":\"tickers\",\"instId\":\"SFG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CFG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CEL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DNA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BNT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"GTO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CONV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AVAX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BTC-USDK\"},{\"channel\":\"tickers\",\"instId\":\"CVP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CMT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ILV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KAN-ETH\"},{\"channel\":\"tickers\",\"instId\":\"REVV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ABT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ATOM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ARDR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ANT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LBA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CRV-ETH\"},{\"channel\":\"tickers\",\"instId\":\"GHST-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WAVES-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BOX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ADA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DIA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ALPHA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TUSD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SHIB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SNT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ADA-ETH\"},{\"channel\":\"tickers\",\"instId\":\"DHT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DGB-BTC\"},{\"channel\":\"tickers\",\"instId\":\"AERGO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FIL-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CFX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OKT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SRM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SUSHI-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ERN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"UNI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XSR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZYRO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PROPS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EXE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CNTM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KCASH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SNX-ETH\"},{\"channel\":\"tickers\",\"instId\":\"EM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"REP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRIO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ALGO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XPO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DNA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CQT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TOPC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"INT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"THETA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TUSD-BTC\"},{\"channel\":\"tickers\",\"instId\":\"USDT-USDK\"},{\"channel\":\"tickers\",\"instId\":\"HBAR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CLV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PMA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BTT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZEC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CELO-BTC\"},{\"channel\":\"tickers\",\"instId\":\"HBAR-USDK\"},{\"channel\":\"tickers\",\"instId\":\"SUSHI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BETH-ETH\"},{\"channel\":\"tickers\",\"instId\":\"NULS-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ICX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BCD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"WGRT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LINK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRUE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SOL-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LUNA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"IOST-BTC\"},{\"channel\":\"tickers\",\"instId\":\"YEE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"GTO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRUE-BTC\"},{\"channel\":\"tickers\",\"instId\":\"REN-BTC\"},{\"channel\":\"tickers\",\"instId\":\"AVAX-ETH\"},{\"channel\":\"tickers\",\"instId\":\"OKB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CMT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"UNI-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XTZ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MIR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OKB-USDC\"},{\"channel\":\"tickers\",\"instId\":\"WTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OKB-USDK\"},{\"channel\":\"tickers\",\"instId\":\"ICP-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ELF-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SOL-ETH\"},{\"channel\":\"tickers\",\"instId\":\"RVN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NU-USDT\"},{\"channel\":\"tickers\",\"instId\":\"REN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ANC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"IOTA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BCH-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ICP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"HBAR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"EGT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CGS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BSV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PICKLE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XMR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ADA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ZEN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ACT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BSV-USDC\"},{\"channel\":\"tickers\",\"instId\":\"SOC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BADGER-USDT\"},{\"channel\":\"tickers\",\"instId\":\"UBTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DORA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BSV-USDK\"},{\"channel\":\"tickers\",\"instId\":\"FTM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RIO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NAS-BTC\"},{\"channel\":\"tickers\",\"instId\":\"FIL-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ONT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LOON-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DGB-USDT\"},{\"channel\":\"tickers\",\"instId\":\"GAS-BTC\"},{\"channel\":\"tickers\",\"instId\":\"LRC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EGLD-BTC\"},{\"channel\":\"tickers\",\"instId\":\"QTUM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"KNC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DYDX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NEAR-ETH\"},{\"channel\":\"tickers\",\"instId\":\"DOT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"DASH-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ARK-BTC\"},{\"channel\":\"tickers\",\"instId\":\"PAY-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FLOW-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ICX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MKR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ZRX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"HEGIC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"FIL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DAO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MITH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RSR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LET-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRX-ETH\"},{\"channel\":\"tickers\",\"instId\":\"BAT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XCH-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CQT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"GHST-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ZIL-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XPR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TMTG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETH-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BZZ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AUCTION-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MXC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ORBS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LSK-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ONT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"COVER-USDT\"},{\"channel\":\"tickers\",\"instId\":\"UMA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BADGER-BTC\"},{\"channel\":\"tickers\",\"instId\":\"FORTH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EOS-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ZRX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ZEC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"CTC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ONT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"BAL-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DMD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OMG-BTC\"},{\"channel\":\"tickers\",\"instId\":\"KAR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BAND-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XLM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SNX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOGE-BTC\"},{\"channel\":\"tickers\",\"instId\":\"YGG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"VIB-BTC\"},{\"channel\":\"tickers\",\"instId\":\"GUSD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EGT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"KSM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"XUC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ATOM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MLN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BAT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MANA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"GAS-ETH\"},{\"channel\":\"tickers\",\"instId\":\"LTC-ETH\"},{\"channel\":\"tickers\",\"instId\":\"CVT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"CVC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"EOS-ETH\"},{\"channel\":\"tickers\",\"instId\":\"TRX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XLM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"HYC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"VALUE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NEAR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"SLP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ETC-OKB\"},{\"channel\":\"tickers\",\"instId\":\"XEM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DCR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZEC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ACT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"OXT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MOF-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KINE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KSM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RFUEL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"STORJ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"INX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XMR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XTZ-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BSV-BTC\"},{\"channel\":\"tickers\",\"instId\":\"EFI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DASH-ETH\"},{\"channel\":\"tickers\",\"instId\":\"GRT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"GNX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"OMG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CMT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"SRM-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ALV-USDT\"},{\"channel\":\"tickers\",\"instId\":\"UNI-ETH\"},{\"channel\":\"tickers\",\"instId\":\"BAL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PST-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ZIL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FLOW-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OKT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"COMP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ALGO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WAVES-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZIL-ETH\"},{\"channel\":\"tickers\",\"instId\":\"KISHU-USDT\"},{\"channel\":\"tickers\",\"instId\":\"RSR-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ETC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"SWFTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MKR-ETH\"},{\"channel\":\"tickers\",\"instId\":\"OKB-BTC\"},{\"channel\":\"tickers\",\"instId\":\"DASH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AAVE-BTC\"},{\"channel\":\"tickers\",\"instId\":\"BABYDOGE-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LEO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"HEGIC-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MDA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"HDAO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ENJ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"GLM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZRX-ETH\"},{\"channel\":\"tickers\",\"instId\":\"VRA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KAN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"QTUM-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ALPHA-BTC\"},{\"channel\":\"tickers\",\"instId\":\"STX-BTC\"},{\"channel\":\"tickers\",\"instId\":\"TRX-USDK\"},{\"channel\":\"tickers\",\"instId\":\"OKT-ETH\"},{\"channel\":\"tickers\",\"instId\":\"XCH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"TRX-USDC\"},{\"channel\":\"tickers\",\"instId\":\"WBTC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"XRP-OKB\"},{\"channel\":\"tickers\",\"instId\":\"LTC-OKB\"},{\"channel\":\"tickers\",\"instId\":\"BETH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"JST-USDT\"},{\"channel\":\"tickers\",\"instId\":\"BTC-DAI\"},{\"channel\":\"tickers\",\"instId\":\"SNC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"TORN-USDT\"},{\"channel\":\"tickers\",\"instId\":\"OKB-ETH\"},{\"channel\":\"tickers\",\"instId\":\"1INCH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FAIR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PERP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ROAD-USDT\"},{\"channel\":\"tickers\",\"instId\":\"IQ-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DOGE-ETH\"},{\"channel\":\"tickers\",\"instId\":\"NULS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PST-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ZKS-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NANO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"NEAR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LMCH-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ANT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"FLOW-ETH\"},{\"channel\":\"tickers\",\"instId\":\"ETH-USDK\"},{\"channel\":\"tickers\",\"instId\":\"AE-BTC\"},{\"channel\":\"tickers\",\"instId\":\"WNXM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"SNT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"ETH-USDC\"},{\"channel\":\"tickers\",\"instId\":\"VITE-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MEME-USDT\"},{\"channel\":\"tickers\",\"instId\":\"PPT-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DEP-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AKITA-USDT\"},{\"channel\":\"tickers\",\"instId\":\"JFI-USDT\"},{\"channel\":\"tickers\",\"instId\":\"CVC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"REP-ETH\"},{\"channel\":\"tickers\",\"instId\":\"GAL-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MKR-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ETM-USDT\"},{\"channel\":\"tickers\",\"instId\":\"KP3R-USDT\"},{\"channel\":\"tickers\",\"instId\":\"DMG-USDT\"},{\"channel\":\"tickers\",\"instId\":\"ARK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"AVAX-USDT\"},{\"channel\":\"tickers\",\"instId\":\"WXT-BTC\"},{\"channel\":\"tickers\",\"instId\":\"MASK-USDT\"},{\"channel\":\"tickers\",\"instId\":\"LRC-BTC\"},{\"channel\":\"tickers\",\"instId\":\"YOYO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"MCO-USDT\"},{\"channel\":\"tickers\",\"instId\":\"XRP-BTC\"},{\"channel\":\"tickers\",\"instId\":\"NEO-ETH\"},{\"channel\":\"tickers\",\"instId\":\"STX-USDT\"}";
		byte[] b = s.getBytes();
		System.out.println(b.length);
	}

	@org.junit.jupiter.api.Test
	public void binance24hPrice(){
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		BinanceRestClient rest = factory.createBinanceClients().createRestClient();
		System.out.println(rest.get24hPrice().toString());
	}

	@org.junit.jupiter.api.Test
	public void KuCoinAllTokenData(){
		ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
		System.out.println(factory.createKuCoinClients().createRestClient().getAllTokenData().getData());

	}

	@org.junit.jupiter.api.Test
	public void idMaker(){
		int n = 6;
		String str = "";
		for (int i = 0; i < n; i++) {
			str += (char)(Math.random()*26+'a');
			str +=  String.valueOf(new Random().nextInt(9));
		}

		System.out.println(str);
	}


	@org.junit.jupiter.api.Test
	public void addressCheck() throws IOException, InterruptedException {

		ServiceImpl service = new ServiceImpl();

		System.out.println(service.binanceMarketDataWebSocket(null));
		System.out.println(service.okexMarketDataWebSocket(null));

		while(true){
			Thread.sleep(3000);
		}

	}


	@org.junit.jupiter.api.Test
	public void subs(){
		String str = "userId:e2j2i8o7v0t8,okex";
		System.out.println(str.indexOf(","));
		System.out.println(str.substring(7,str.indexOf(",")));
		System.out.println(str.substring(str.indexOf(","),str.length()));
	}

	@org.junit.jupiter.api.Test
	public void stopClient() throws InterruptedException, IOException {
			userId = "123";
			ExchangeFactory factory = ExchangeFactory.createExchangeFactory();
//		factory.createBinanceClients().createWebSocketClient().MiniMarketTickersEvent(new CallBack<List<BinanceMiniTickerResp>>() {
//			@Override
//			public void onResponse(List<BinanceMiniTickerResp> var1) {
//				System.out.println(var1.toString());
//			}
//
//			@Override
//			public void onFailure(Throwable cause) {
//				System.out.println(cause);
//			}
//		});

			Closeable ws = factory.createBinanceClients().createWebSocketClient().MarketTickersEvent(new CallBack<List<BinanceAllMarketTickersResp>>() {
				@Override
				public void onResponse(List<BinanceAllMarketTickersResp> var1) {
					System.out.println("币安在跑");
//				System.out.println(var1);
				}

				@Override
				public void onFailure(Throwable cause) {
					System.out.println(cause);
				}
			});

			ApiServiceGenerator.close();

			factory.createOkexClients().createWebSocketClient().MarketTickersEvent(new CallBack<OkexTickersResp>() {
				@Override
				public void onResponse(OkexTickersResp var1) throws InterruptedException {
					Thread.sleep(5000);
					System.out.println("okex在跑");
//				System.out.println(Arrays.toString(var1.getData()));

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




}