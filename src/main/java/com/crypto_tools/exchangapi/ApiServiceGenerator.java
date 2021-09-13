package com.crypto_tools.exchangapi;

import com.binance.api.client.BinanceApiError;
import com.binance.api.client.security.AuthenticationInterceptor;
import com.crypto_tools.exchangapi.binance.rest.BinanceRestUrlList;
import com.crypto_tools.exchangapi.huobi.rest.HuobiRestUrlList;
import com.crypto_tools.exchangapi.huobi.rest.HuobiTickerResp;
import com.crypto_tools.exchangapi.kucoin.rest.KuCoinRestUrlList;
import com.crypto_tools.exchangapi.okex.rest.OkexRestUrlList;
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
import java.util.concurrent.TimeUnit;

public class ApiServiceGenerator {
    private static final OkHttpClient sharedClient;
    private static final Converter.Factory converterFactory = JacksonConverterFactory.create();
    private static final Converter<ResponseBody, Error> errorBodyConverter;
    private static WebSocket webSocket;
    private static ExchangesWebSocketListener<?> websocketlistener;

    public static String getExchangeName() {
        return exchangeName;
    }

    public static void setExchangeName(String exchangeName) {
        ApiServiceGenerator.exchangeName = exchangeName;
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


    public static Closeable connectWebSocket(String channel, ExchangesWebSocketListener<?> listener, String exchange) {
        String streamingUrl = String.format("%s%s", ApiConfig.getStreamBaseUrl(exchange), channel);
        Request request = new Request.Builder().url(streamingUrl).build();
        websocketlistener = listener;
        exchangeName = exchange;
        webSocket = sharedClient.newWebSocket(request, websocketlistener);

        return () -> {
            listener.onClosing(webSocket, 3000, (String) null);
            webSocket.close(3000, (String) null);
            listener.onClosed(webSocket, 3000, (String) null);
        };
    };



    public static void WSBsend(String text){
        if(exchangeName.equals("okex")){
            StringBuilder req = new StringBuilder();
            String inst;
            OkexTickerResp allTokenBaseData = ExchangeFactory.createExchangeFactory().createOkexClients().createRestClient().getAllTokenBaseData();
            for (int i = 0; i < allTokenBaseData.getData().length; i++) {
                inst = allTokenBaseData.getData()[i].getInstId();
                req.append("{\"channel\":\"tickers\",\"instId\":\""+ inst +"\"},");
            }
            webSocket.send("{\"op\"" + ":" + "\"subscribe\",\"args\"" + ":" +"["+ req.substring(0,req.length() - 1) +"]}");
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
        } else if(exchangeName.equals("kucoin")){

            webSocket.send("{\"id\":\""+ text.substring(7,text.indexOf(",")-1) +"\", \"type\":\"subscribe\", \"topic\":\"/market/ticker:all\", \"privateChannel\":false, \"response\":false }");
        }
    }

    public static Error getApiError(Response<?> response) throws IOException, Exception {
        return (Error)errorBodyConverter.convert(response.errorBody());
    }



    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }

    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(500);
        dispatcher.setMaxRequests(500);
        sharedClient = (new okhttp3.OkHttpClient.Builder()).dispatcher(dispatcher).pingInterval(5, TimeUnit.SECONDS).build();
        errorBodyConverter = (Converter<ResponseBody, Error>) converterFactory.responseBodyConverter(BinanceApiError.class, new Annotation[0], (Retrofit)null);
    }
}
