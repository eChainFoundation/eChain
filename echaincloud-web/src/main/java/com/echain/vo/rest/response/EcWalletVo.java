package com.echain.vo.rest.response;

import com.echain.entity.EcWallet;
import com.echain.enumaration.WalletType;

public class EcWalletVo {
    /**
     * ID
     */
    private Long id;

    /**
     * 钱包地址
     */
    private String wallet;

    /**
     * 钱包类型：1-以太坊，2-EOS
     */
    private String type;

    /**
     * 数量
     */
    private String value;

    private String rmb;

    private String qimg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getQimg() {
        return qimg;
    }

    public void setQimg(String qimg) {
        this.qimg = qimg;
    }

    public EcWalletVo() {
    }

    public EcWalletVo(EcWallet wallet) {
        this.id = wallet.getId();
        this.wallet = wallet.getWallet();
        this.type = WalletType.getWalletType(wallet.getType()).getName();
        this.qimg = wallet.getWallet();
    }

    public EcWalletVo(String type, String value, String rmb) {
        this.type = type;
        this.value = value;
        this.rmb = rmb;
    }
}