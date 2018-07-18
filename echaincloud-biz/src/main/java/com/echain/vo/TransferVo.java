package com.echain.vo;

public class TransferVo {
    private String rate;

    private String eth;

    private String rmb;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public TransferVo() {
        this.rate = "0";
        this.eth = "0";
        this.rmb = "0";
    }

    public TransferVo(String rate, String eth, String rmb) {
        this.rate = rate;
        this.eth = eth;
        this.rmb = rmb;
    }
}
