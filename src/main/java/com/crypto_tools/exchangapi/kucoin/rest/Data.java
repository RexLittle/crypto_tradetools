package com.crypto_tools.exchangapi.kucoin.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private List<InstanceServer> instanceServers;
    private String token;

    public List<InstanceServer> getInstanceServers() {
        return instanceServers;
    }

    public void setInstanceServers(List<InstanceServer> instanceServers) {
        this.instanceServers = instanceServers;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
                "instanceServers=" + instanceServers +
                ", token='" + token + '\'' +
                '}';
    }

}
