package com.echain.vo.rest.response;

import com.echain.entity.EcTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class EcTransactionResponseVo {

    @ApiModelProperty(value = "ID", dataType = "Long")
    private Long id;

    @ApiModelProperty(value = "用户ID", dataType = "Long")
    private Long userId;

    @ApiModelProperty(value = "dapp ID", dataType = "Long")
    private Long dappId;

    @ApiModelProperty(value = "该用户在这个dapp中的交易,0-从echain中上传的交易", dataType = "Long")
    private Long userDappId;

    @ApiModelProperty(value = "订单交易平台，例如：淘宝，京东等", dataType = "String")
    private String transactionPlatform;

    @ApiModelProperty(value = "订单编号", dataType = "String")
    private String transactionNo;

    @ApiModelProperty(value = "订单照片路径", dataType = "String")
    private String transactionPicture;

    @ApiModelProperty(value = "物流公司ID", dataType = "Long")
    private Long logisticsCompanyId;

    @ApiModelProperty(value = "物流编号", dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "交易详情MD5", dataType = "String")
    private String describeMd5;

    @ApiModelProperty(value = "交易状态，0-未审核，1-审核通过，2-审核不通过", dataType = "String")
    private String status;

    @ApiModelProperty(value = "交易不通过的原因描述", dataType = "String")
    private String errorMsg;

    @ApiModelProperty(value = "交易上链的key，通过此key值获取上传数据的具体hash值", dataType = "String")
    private String transactionKey;

    @ApiModelProperty(value = "数据上链时，返回的交易hash值，通过此hash在以太坊浏览器上查找到相应的数据信息", dataType = "String")
    private String transactionHash;

    @ApiModelProperty(value = "数据上链时，数据存储在以太坊上的块的编号", dataType = "String")
    private String blockNo;

    @ApiModelProperty(value = "md5值", dataType = "String")
    private String transactionValue;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createTime;

    @ApiModelProperty(value = "交易详情", dataType = "String")
    private String describeText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDappId() {
        return dappId;
    }

    public void setDappId(Long dappId) {
        this.dappId = dappId;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public Long getUserDappId() {
        return userDappId;
    }

    public void setUserDappId(Long userDappId) {
        this.userDappId = userDappId;
    }

    public String getTransactionPlatform() {
        return transactionPlatform;
    }

    public void setTransactionPlatform(String transactionPlatform) {
        this.transactionPlatform = transactionPlatform == null ? null : transactionPlatform.trim();
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public String getTransactionPicture() {
        return transactionPicture;
    }

    public void setTransactionPicture(String transactionPicture) {
        this.transactionPicture = transactionPicture == null ? null : transactionPicture.trim();
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Long getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    public void setLogisticsCompanyId(Long logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getDescribeMd5() {
        return describeMd5;
    }

    public void setDescribeMd5(String describeMd5) {
        this.describeMd5 = describeMd5 == null ? null : describeMd5.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescribeText() {
        return describeText;
    }

    public void setDescribeText(String describeText) {
        this.describeText = describeText == null ? null : describeText.trim();
    }

    public String getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    public EcTransactionResponseVo() {
    }

    public EcTransactionResponseVo(EcTransaction transaction) {
        this.id = transaction.getId();
        this.userId = transaction.getUserId();
        this.dappId = transaction.getDappId();
        this.userDappId = transaction.getUserDappId();
        this.transactionPlatform = transaction.getTransactionPlatform();
        this.transactionNo = transaction.getTransactionNo();
        this.transactionPicture = transaction.getTransactionPicture();
        this.logisticsCompanyId = transaction.getLogisticsCompanyId();
        this.logisticsNo = transaction.getLogisticsNo();
        this.describeMd5 = transaction.getDescribeMd5();
        this.status = transaction.getStatus();
        this.errorMsg = transaction.getErrorMsg();
        this.transactionKey = transaction.getTransactionKey();
        this.transactionHash = transaction.getTransactionHash();
        this.blockNo = transaction.getBlockNo();
        this.transactionValue = transaction.getTransactionValue();
        this.createTime = transaction.getCreateTime();
        this.describeText = transaction.getDescribeText();
    }
}