package com.okcoin.rest.vo;

public class Ticker {
    /**
     * 买一价
     */
    private String buy;

    /**
     * 最高价
     */
    private String high;

    /**
     * 最新成交价
     */
    private String last;

    /**
     * 最低价
     */
    private String low;

    /**
     * 卖一价
     */
    private String sell;

    /**
     * 成交量(最近的24小时)
     */
    private String vol;


    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}
