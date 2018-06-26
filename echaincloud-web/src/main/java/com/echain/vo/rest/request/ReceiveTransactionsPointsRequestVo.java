package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class ReceiveTransactionsPointsRequestVo {
    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String phoneNumber;

    @ApiModelProperty(value = "dappName", required = true, dataType = "String")
    private String dappName;

    @ApiModelProperty(value = "是否单独上链", required = false, dataType = "String")
    private String isUploadSingle;

    @ApiModelProperty(value = "订单交易平台", required = false, dataType = "String")
    private String transactionPlatform;

    @ApiModelProperty(value = "订单编号", required = true, dataType = "String")
    private String transactionNo;

    @ApiModelProperty(value = "物流公司名称", required = false, dataType = "String")
    private String logisticsCompanyName;

    @ApiModelProperty(value = "物流编号", required = false, dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "交易详情", required = true, dataType = "String")
    private String describeText;

    @ApiModelProperty(value = "交易状态，0-未审核，1-审核通过，2-审核不通过", required = true, dataType = "String")
    private String status;

    @ApiModelProperty(value = "积分发送方手机号", required = false, dataType = "String")
    private String sendPhoneNumber;

    @ApiModelProperty(value = "积分接受方手机号", required = false, dataType = "String")
    private String receivePhoneNumber;

    @ApiModelProperty(value = "积分", required = false, dataType = "String")
    private Long points;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getTransactionPlatform() {
        return transactionPlatform;
    }

    public void setTransactionPlatform(String transactionPlatform) {
        this.transactionPlatform = transactionPlatform;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getDescribeText() {
        return describeText;
    }

    public void setDescribeText(String describeText) {
        this.describeText = describeText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
