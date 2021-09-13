package com.crypto_tools.exchangapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Error {
    private int code;
    private String msg;

    public Error(Error error) {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString() {
        return (new ToStringBuilder(this, Constants.TO_STRING_BUILDER_STYLE)).append("code", this.code).append("msg", this.msg).toString();
    }
}
