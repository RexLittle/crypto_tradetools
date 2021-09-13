package com.crypto_tools.exchangapi;

import org.apache.commons.lang3.builder.ToStringStyle;

public class Constants {
//    public static final String API_KEY_HEADER = "X-MBX-APIKEY";
//    public static final String ENDPOINT_SECURITY_TYPE_APIKEY = "APIKEY";
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY_HEADER = "APIKEY: #";
//    public static final String ENDPOINT_SECURITY_TYPE_SIGNED = "SIGNED";
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = "SIGNED: #";
    public static final long DEFAULT_RECEIVING_WINDOW = 60000L;
//    public static final long DEFAULT_MARGIN_RECEIVING_WINDOW = 5000L;
    public static ToStringStyle TO_STRING_BUILDER_STYLE;

    static {
        TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;
    }
}
