package com.echain.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echain.entity.BaseEntity;
import com.echain.entity.EcTransaction;

import java.util.Date;
import java.util.List;

public class EcTransactionVo extends BaseEntity {

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

    private Date createTime;

    private ProductTransaction describeText;

    private List<EcLogisticsRecordVo> records;


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

    public ProductTransaction getDescribeText() {
        return describeText;
    }

    public void setDescribeText(ProductTransaction describeText) {
        this.describeText = describeText;
    }

    public List<EcLogisticsRecordVo> getRecords() {
        return records;
    }

    public void setRecords(List<EcLogisticsRecordVo> records) {
        this.records = records;
    }

    public EcTransactionVo(EcTransaction transaction) {
        super();
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
        this.createTime = transaction.getCreateTime();

        JSONObject jsonObject = JSON.parseObject(transaction.getDescribeText());
        Object o = jsonObject.get("com.echain.entity.ProductTransaction");

        ProductTransaction p = JSON.parseObject(JSON.toJSONString(o), ProductTransaction.class);
        this.describeText = p;
    }
}