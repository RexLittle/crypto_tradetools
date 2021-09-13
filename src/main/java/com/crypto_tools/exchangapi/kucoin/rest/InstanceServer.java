package com.crypto_tools.exchangapi.kucoin.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceServer {

    private String endpoint;
    private String protocol;
    private boolean encrypt;
    private long pingInterval;
    private long pingTimeout;
    public long getPingInterval() {
        return pingInterval;
    }

    public void setPingInterval(long pingInterval) {
        this.pingInterval = pingInterval;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public long getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(long pingTimeout) {
        this.pingTimeout = pingTimeout;
    }



    @Override
    public String toString() {
        return "InstanceServer{" +
                "pingInterval=" + pingInterval +
                ", endpoint='" + endpoint + '\'' +
                ", protocol='" + protocol + '\'' +
                ", encrypt=" + encrypt +
                ", pingTimeout=" + pingTimeout +
                '}';
    }

}
