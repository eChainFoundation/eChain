package com.echain.entity;

import javax.persistence.Id;

public class EcBlockNumber {
    @Id
    private Long id;

    /**
     * 钱包地址
     */
    private String wallet;

    /**
     * 已记录的blockNumber
     */
    private Long number;

    private Integer type;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取钱包地址
     *
     * @return wallet - 钱包地址
     */
    public String getWallet() {
        return wallet;
    }

    /**
     * 设置钱包地址
     *
     * @param wallet 钱包地址
     */
    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    /**
     * 获取已记录的blockNumber
     *
     * @return number - 已记录的blockNumber
     */
    public Long getNumber() {
        return number;
    }

    /**
     * 设置已记录的blockNumber
     *
     * @param number 已记录的blockNumber
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public EcBlockNumber() {
    }

    public EcBlockNumber(String wallet, Long number, Integer type) {
        this.wallet = wallet;
        this.number = number;
        this.type = type;
    }
}