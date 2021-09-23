package com.crypto_tools.exchangapi.kucoin.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class KuCoinMarketResp {
    private String type;
    private String topic;
    private String subject;
    private MarketRespData data;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MarketRespData getData() {
        return data;
    }

    public void setData(MarketRespData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", topic='" + topic + '\'' +
                ", subject='" + subject + '\'' +
                ", data=" + data +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class MarketRespData {
        private String sequence;
        private MarketData data;
        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public MarketData getData() {
            return data;
        }

        public void setData(MarketData data) {
            this.data = data;
        }
        @Override
        public String toString() {
            return "{" +
                    "sequence='" + sequence + '\'' +
                    ", data=" + data +
                    '}';
        }



        @JsonIgnoreProperties(ignoreUnknown = true)
        public class MarketData {

            private String trading;
            private String symbol;
            private String buy;
            private String sell;
            private String sort;
            private String volValue;
            private String baseCurrency;
            private String market;
            private String quoteCurrency;
            private String symbolCode;
            private String datetime;
            private String high;
            private String vol;
            private String low;
            private String changePrice;
            private String changeRate;
            private String lastTradedPrice;
            private String board;
            private String mark;

            public String getTrading() {
                return trading;
            }

            public void setTrading(String trading) {
                this.trading = trading;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getBuy() {
                return buy;
            }

            public void setBuy(String buy) {
                this.buy = buy;
            }

            public String getSell() {
                return sell;
            }

            public void setSell(String sell) {
                this.sell = sell;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getVolValue() {
                return volValue;
            }

            public void setVolValue(String volValue) {
                this.volValue = volValue;
            }

            public String getBaseCurrency() {
                return baseCurrency;
            }

            public void setBaseCurrency(String baseCurrency) {
                this.baseCurrency = baseCurrency;
            }

            public String getMarket() {
                return market;
            }

            public void setMarket(String market) {
                this.market = market;
            }

            public String getQuoteCurrency() {
                return quoteCurrency;
            }

            public void setQuoteCurrency(String quoteCurrency) {
                this.quoteCurrency = quoteCurrency;
            }

            public String getSymbolCode() {
                return symbolCode;
            }

            public void setSymbolCode(String symbolCode) {
                this.symbolCode = symbolCode;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getVol() {
                return vol;
            }

            public void setVol(String vol) {
                this.vol = vol;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getChangePrice() {
                return changePrice;
            }

            public void setChangePrice(String changePrice) {
                this.changePrice = changePrice;
            }

            public String getChangeRate() {
                return changeRate;
            }

            public void setChangeRate(String changeRate) {
                this.changeRate = changeRate;
            }

            public String getLastTradedPrice() {
                return lastTradedPrice;
            }

            public void setLastTradedPrice(String lastTradedPrice) {
                this.lastTradedPrice = lastTradedPrice;
            }

            public String getBoard() {
                return board;
            }

            public void setBoard(String board) {
                this.board = board;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

        }


    }

}



