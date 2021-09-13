package com.crypto_tools.exchangapi;

public class ApiConfig {

    private static String binance_base_domain = "binance.com";
    private static String okex_base_domain = "okex.com";
    private static String huobi_base_domain = "api.huobi.pro";
    private static String kubi_base_url = "https://api.kucoin.com";


    public static String getBaseDomain(String exchange){
        switch (exchange) {
            case "binance": return binance_base_domain;
            case "okex": return okex_base_domain;
            case "huobi": return huobi_base_domain;
            default:
                System.out.println(exchange);
                return null;
        }

    };

    public static String getBaseUrl(String exchange) {
        switch (exchange) {
            case "binance": return String.format("https://api.%s", getBaseDomain("binance"));
            case "okex": return String.format("https://www.%s", getBaseDomain("okex"));
            case "huobi": return String.format("https://%s",getBaseDomain("huobi"));
            case "kucoin": return kubi_base_url;
            default: return null;
        }
    }


    public static String getStreamBaseUrl(String exchange) {
        switch (exchange) {
            case "binance": return String.format("wss://stream.%s:9443/ws/", getBaseDomain("binance"));
            case "okex": return String.format("wss://wsaws.%s:8443/ws/v5/public/", getBaseDomain("okex"));
            case "huobi": return String.format("wss://%s/ws/",getBaseDomain("huobi"));
            case "kucoin": return "wss://ws-api.kucoin.com/endpoint";
            default: return null;
        }
    }
}
