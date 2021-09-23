package com.crypto_tools.exchangapi;

import com.binance.api.client.BinanceApiError;
import com.binance.api.client.security.AuthenticationInterceptor;
import com.crypto_tools.controller.config.SessionPool;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestUrlList;
import com.crypto_tools.exchangapi.huobi.rest.HuobiRestUrlList;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerResp;
import com.crypto_tools.exchangapi.kucoin.rest.KuCoinRestUrlList;
import com.crypto_tools.exchangapi.okex.rest.OkexRestUrlList;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerData;
import com.crypto_tools.exchangapi.okex.rest.OkexTickerResp;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.websocket.Session;
import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ApiServiceGenerator {
    private static OkHttpClient sharedClient;
    private static final Converter.Factory converterFactory = JacksonConverterFactory.create();

    private static OkHttpClient wbClient;
    private static final Converter<ResponseBody, Error> errorBodyConverter;
    public static WebSocket webSocket;
    public static ExchangesWebSocketListener<?> websocketlistener;
    public static String channelUrl;
    public static String userId;


    public static String getExchangeName() {
        return exchangeName;
    }

    private static String exchangeName;


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, (String)null, (String)null);
    }

    public static <S> S createService(Class<S> urlClass, String apiKey, String secret) {
        Retrofit.Builder retrofitBuilder = null;
        if (urlClass.equals(BinanceRestUrlList.class)) {
            retrofitBuilder = (new Retrofit.Builder()).baseUrl(ApiConfig.getBaseUrl("binance")).addConverterFactory(converterFactory);
        } else if(urlClass.equals(OkexRestUrlList.class)) {
            retrofitBuilder = (new Retrofit.Builder()).baseUrl(ApiConfig.getBaseUrl("okex")).addConverterFactory(converterFactory);
        } else if(urlClass.equals(HuobiRestUrlList.class)){
            retrofitBuilder = (new Retrofit.Builder()).baseUrl(ApiConfig.getBaseUrl("huobi")).addConverterFactory(converterFactory);
        } else if(urlClass.equals(KuCoinRestUrlList.class)) {
            retrofitBuilder = (new Retrofit.Builder()).baseUrl(ApiConfig.getBaseUrl("kucoin")).addConverterFactory(converterFactory);
        }


        if (!StringUtils.isEmpty(apiKey) && !StringUtils.isEmpty(secret)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
            retrofitBuilder.client(adaptedClient);
        } else {
            retrofitBuilder.client(sharedClient);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(urlClass);
    }

    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Error error = getApiError(response);
                throw new Exception(error);
            }
        } catch (IOException var3) {
            throw new Exception(var3);
        }
    }


    public static synchronized Closeable connectWebSocket(String channel, ExchangesWebSocketListener<?> listener,String exchange) {
        channelUrl = channel;
        websocketlistener = listener;
        exchangeName = exchange;
        String streamingUrl = String.format("%s%s", ApiConfig.getStreamBaseUrl(exchange), channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        webSocket = sharedClient.newWebSocket(request, websocketlistener);

        return () -> {
            final int code = 1000;
            websocketlistener.onClosing(webSocket, code, null);
            webSocket.close(code, null);
            websocketlistener.onClosed(webSocket, code, null);
        };
    };

    public static void reConnect(){
            connectWebSocket(channelUrl,websocketlistener,exchangeName);
    }

    public static synchronized void close(){
        final int code = 1000;
        websocketlistener.onClosing(webSocket, code, null);
        webSocket.close(code, null);
        websocketlistener.onClosed(webSocket, code, null);
    }

    public static synchronized void WSBsend(String text){
        //okex
        if(exchangeName.equals("okex")){
            if(text == null){
                StringBuilder req = new StringBuilder();
                String dataTemp;
                OkexTickerData[] allTickerBaseData = ExchangeFactory.createExchangeFactory().createOkexClients().createRestClient().getAllTokenBaseData().getData();
                int dataLength = ExchangeFactory.createExchangeFactory().createOkexClients().createRestClient().getAllTokenBaseData().getData().length;
                int times;
                int sendAmount;
                int position = -1;

                //送信回数計算,args部分の送信制限1回あたり4090 byteまで
                for (int i = 0; i < dataLength; i++) {
                    dataTemp = allTickerBaseData[i].getInstId();
                    req.append("{\"channel\":\"tickers\",\"instId\":\""+ dataTemp +"\"},");
                }
                times = req.substring(0,req.length() - 1).getBytes().length / 4090 + 1;
                sendAmount = (dataLength - dataLength % times) / times;
                req.setLength(0);

                //データバイトの制限あるため数回に分けて送信
                for (int i = 0; i < 5; i++) {
                    //始めの5回
                    for (int j = 0; j < sendAmount; j++) {
                        dataTemp = allTickerBaseData[++position].getInstId();
                        req.append("{\"channel\":\"tickers\",\"instId\":\""+ dataTemp +"\"},");
                    }
                    webSocket.send("{\"op\"" + ":" + "\"subscribe\",\"args\"" + ":" +"["+ req.substring(0,req.length() - 1) +"]}");
                    req.setLength(0);
                }

                //残りの1回
                for (int k = 0; k <  sendAmount + dataLength % 6; k++) {
                    dataTemp = allTickerBaseData[++position].getInstId();
                    req.append("{\"channel\":\"tickers\",\"instId\":\""+ dataTemp +"\"},");
                }
                webSocket.send("{\"op\"" + ":" + "\"subscribe\",\"args\"" + ":" +"["+ req.substring(0,req.length() - 1) +"]}");
            }else if(text.equalsIgnoreCase("ping")){
                webSocket.send(text);
            }
        //huobi
        } else if(exchangeName.equals("huobi")) {
            StringBuilder req = new StringBuilder();
            String inst;
            req.append("{");
            HuobiTickerResp allTokenBaseData = ExchangeFactory.createExchangeFactory().createHuobiClients().createRestClient().getAllTokenBaseData();
            for (int i = 0; i < allTokenBaseData.getData().length; i++) {
                inst = allTokenBaseData.getData()[i].getSymbol();
                req.append("\"sub\": \"market."+ inst + ".ticker\",");
            }
            req.append("}");


            System.out.println(req.substring(0,req.length() - 2));
            webSocket.send("{\"sub\":\"market.btcusdt.ticker\"}");
        //kucoin
        } else if(exchangeName.equals("kucoin")){

            if(text.contains("TickersLastPrice")){
                webSocket.send("{\"id\":\""+ text.substring(7,text.indexOf(",")-1) +"\", \"type\":\"subscribe\", \"topic\":" +
                        "\"/market/ticker:all\", \"privateChannel\":false, \"response\":false }");;
            }else if(text.contains("Market")){
                webSocket.send("{\"id\":\""+ text.substring(7,text.indexOf(",")-1) +"\", \"type\":\"subscribe\", \"topic\":" +
                        "\"/market/snapshot:USDS,BTC,KCS,ALTS\", \"privateChannel\":false, \"response\":false }");
            }

        }
    }



    public static Error getApiError(Response<?> response) throws IOException, Exception {
        return (Error)errorBodyConverter.convert(response.errorBody());
    }




    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = (new okhttp3.OkHttpClient.Builder()).dispatcher(dispatcher).pingInterval(120, TimeUnit.SECONDS).build();
        errorBodyConverter = (Converter<ResponseBody, Error>) converterFactory.responseBodyConverter(Error.class, new Annotation[0], (Retrofit)null);
    }
}
