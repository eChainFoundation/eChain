package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class ReceiveTransactionsRequestVo {

    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String phoneNumber;

    @ApiModelProperty(value = "dappName", required = true, dataType = "String")
    private String dappName;

    @ApiModelProperty(value = "是否单独上链", required = false, dataType = "String")
    private String isUploadSingle;

    @ApiModelProperty(value = "交易平台", required = false, dataType = "String")
    private String transactionPlatform;

    @ApiModelProperty(value = "交易号", required = true, dataType = "String")
    private String transactionNo;

    @ApiModelProperty(value = "物流公司", required = false, dataType = "String")
    private String logisticsCompanyName;

    @ApiModelProperty(value = "物流单号", required = false, dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "描述信息", required = true, dataType = "String")
    private String describeText;

    @ApiModelProperty(value = "状态", required = true, dataType = "String")
    private String status;

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
}
