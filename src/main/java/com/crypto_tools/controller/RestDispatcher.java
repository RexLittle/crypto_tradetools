package com.crypto_tools.controller;

import com.crypto_tools.model.ExchangeMarketData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.crypto_tools.model.JsonFormatBean;
import com.crypto_tools.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;


@org.springframework.stereotype.Controller
public class RestDispatcher {

    @Autowired
    ServiceImpl service;

    @RequestMapping(value = "compare/{exchange1}&{exchange2}", method = RequestMethod.GET)
    @ResponseBody
    public List<JsonFormatBean> compare(@PathVariable("exchange1")String ex1,@PathVariable("exchange2")String ex2) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return service.compare(ex1,ex2);
    }

    @RequestMapping(value = "/binanceMarket", method = RequestMethod.GET)
    @ResponseBody
    public List<ExchangeMarketData> binanceData(){
        return service.binanceMarkeDataRest();
    }


    @RequestMapping(value = "/okexMarket", method = RequestMethod.GET)
    @ResponseBody
    public List<ExchangeMarketData> okexData(){
        return service.okexMarketDataRest();
    }


    @RequestMapping(value = "/kuCoinMarket", method = RequestMethod.GET)
    @ResponseBody
    public List<ExchangeMarketData> kuCoinData(){
        return service.kuCoinMarketDataRest();
    }

    @RequestMapping("/marketsite")
    public String marketSite(){
        return "ExchangeMarket";
    }

}
