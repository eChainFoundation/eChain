package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class TakePointsRequestVo {
    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String phoneNumber;

    @ApiModelProperty(value = "钱包地址", required = true, dataType = "String")
    private String wallet;

    @ApiModelProperty(value = "费用", required = true, dataType = "String")
    private Long free;

    @ApiModelProperty(value = "所有积分", required = true, dataType = "String")
    private Long allPoints;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Long getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(Long allPoints) {
        this.allPoints = allPoints;
    }
}
