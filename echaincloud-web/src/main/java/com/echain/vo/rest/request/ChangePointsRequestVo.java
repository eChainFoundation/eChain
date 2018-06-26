package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class ChangePointsRequestVo {

    @ApiModelProperty(value = "积分发送方手机号", required = false, dataType = "String")
    private String sendPhoneNumber;

    @ApiModelProperty(value = "积分接受方手机号", required = false, dataType = "String")
    private String receivePhoneNumber;

    @ApiModelProperty(value = "dapp名称", required = false, dataType = "String")
    private String dappName;

    @ApiModelProperty(value = "是否单独上链", required = false, dataType = "String")
    private String isUploadSingle;

    @ApiModelProperty(value = "交易Id", required = false, dataType = "Long")
    private Long transactionId;

    @ApiModelProperty(value = "交易详情", required = false, dataType = "String")
    private String descText;

    @ApiModelProperty(value = "积分", required = false, dataType = "Long")
    private Long points;

    public String getSendPhoneNumber() {
        return sendPhoneNumber;
    }

    public void setSendPhoneNumber(String sendPhoneNumber) {
        this.sendPhoneNumber = sendPhoneNumber;
    }

    public String getReceivePhoneNumber() {
        return receivePhoneNumber;
    }

    public void setReceivePhoneNumber(String receivePhoneNumber) {
        this.receivePhoneNumber = receivePhoneNumber;
    }

    public String getDappName() {
        return dappName;
    }

    public void setDappName(String dappName) {
        this.dappName = dappName;
    }

    public String getIsUploadSingle() {
        return isUploadSingle;
    }

    public void setIsUploadSingle(String isUploadSingle) {
        this.isUploadSingle = isUploadSingle;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
