package com.crypto_tools.exchangapi.binance.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceAllMarketTickersResp {
    private String e;
    private String E;
    private String s;
    private String P;
    private String p;
    private String w;
    private String x;
    private String c;
    private String Q;
    private String b;
    private String B;
    private String a;
    private String A;
    private String o;
    private String h;
    private String l;
    private String v;
    private String q;
    private String O;
    private String C;
    private String F;
    private String L;
    private String n;
    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String get_p() {
        return p;
    }

    public void set_p(String p) {
        this.p = p;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "{" +
                "e='" + e + '\'' +
                ", E='" + E + '\'' +
                ", s='" + s + '\'' +
                ", P='" + P + '\'' +
                ", p='" + p + '\'' +
                ", w='" + w + '\'' +
                ", x='" + x + '\'' +
                ", c='" + c + '\'' +
                ", Q='" + Q + '\'' +
                ", b='" + b + '\'' +
                ", B='" + B + '\'' +
                ", a='" + a + '\'' +
                ", A='" + A + '\'' +
                ", o='" + o + '\'' +
                ", h='" + h + '\'' +
                ", l='" + l + '\'' +
                ", v='" + v + '\'' +
                ", q='" + q + '\'' +
                ", O='" + O + '\'' +
                ", C='" + C + '\'' +
                ", F='" + F + '\'' +
                ", L='" + L + '\'' +
                ", n='" + n + '\'' +
                '}';
    }
}
