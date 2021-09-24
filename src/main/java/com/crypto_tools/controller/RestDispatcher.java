package com.crypto_tools.controller;

import com.crypto_tools.model.ExchangeMarketData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crypto_tools.model.JsonFormatBean;
import com.crypto_tools.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;


@org.springframework.stereotype.Controller
public class RestDispatcher {

    @Autowired
    ServiceImpl service;

    @RequestMapping(value = "/arbitrage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<JsonFormatBean> arbirage() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return service.Arbitrage();
    }

    @RequestMapping(value = "/binanceMarket", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ExchangeMarketData> binanceData(){
        return service.binanceMarkeDataRest();
    }


    @RequestMapping(value = "/okexMarket", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ExchangeMarketData> okexData(){
        return service.okexMarketDataRest();
    }


    @RequestMapping(value = "/kuCoinMarket", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ExchangeMarketData> kuCoinData(){
        return service.kuCoinMarketDataRest();
    }




}
