package com.echain.entity;

import java.util.Date;
import java.util.List;

public class EcTransaction extends BaseEntity {

    private Long userId;

    private Long dappId;

    private Long userDappId;

    private String transactionPlatform;

    private String transactionNo;

    private String transactionPicture;

    private Long logisticsCompanyId;

    private String logisticsNo;

    private String describeMd5;

    private String status;

    private String errorMsg;

    private String transactionKey;

    private String transactionHash;

    private String blockNo;

    private String transactionValue;

    private Date createTime;

    private String describeText;

    private List<EcLogisticsRecord> records;

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

    public List<EcLogisticsRecord> getRecords() {
        return records;
    }

    public void setRecords(List<EcLogisticsRecord> records) {
        this.records = records;
    }

    public String getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    public EcTransaction() {
    }

    public EcTransaction(Long userId, Long dappId, Long userDappId, String describeMd5, String status,
                         Date createTime, String describeText, String transactionNo) {
        this.userId = userId;
        this.dappId = dappId;
        this.userDappId = userDappId;
        this.describeMd5 = describeMd5;
        this.status = status;
        this.transactionNo = transactionNo;
        this.createTime = createTime;
        this.describeText = describeText;
    }
}