package com.crypto_tools.exchangapi;

public abstract class Config {
    protected String base_domain;

    public void setBaseDomain(String domain) {
        this.base_domain = domain;
    };
    public String getBaseDomain(){
        return base_domain;
    };
    public abstract String getBaseUrl();
    public abstract String getStreamBaseUrl();
}