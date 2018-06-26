package com.echain.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echain.entity.BaseEntity;
import com.echain.entity.EcLogisticsRecord;

import java.util.Date;

public class EcLogisticsRecordVo extends BaseEntity {

    private Long logisticsCompanyId;

    private String logisticsNo;

    private String logisticsMd5;

    private Long transactionKey;

    private String transactionHash;

    private String blockNo;

    private Date createTime;

    private LogisticsRecord logisticsText;

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

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getLogisticsMd5() {
        return logisticsMd5;
    }

    public void setLogisticsMd5(String logisticsMd5) {
        this.logisticsMd5 = logisticsMd5 == null ? null : logisticsMd5.trim();
    }

    public Long getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(Long transactionKey) {
        this.transactionKey = transactionKey;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public LogisticsRecord getLogisticsText() {
        return logisticsText;
    }

    public void setLogisticsText(LogisticsRecord logisticsText) {
        this.logisticsText = logisticsText;
    }

    public EcLogisticsRecordVo(EcLogisticsRecord record) {
        super();
        this.logisticsCompanyId = record.getLogisticsCompanyId();
        this.logisticsNo = record.getLogisticsNo();
        this.logisticsMd5 = record.getLogisticsMd5();
        this.transactionKey = record.getTransactionKey();
        this.transactionHash = record.getTransactionHash();
        this.blockNo = record.getBlockNo();
        this.createTime = record.getCreateTime();

        JSONObject jsonObject = JSON.parseObject(record.getLogisticsText());
        Object o = jsonObject.get("com.echain.entity.LogisticsRecord");

        LogisticsRecord r = JSON.parseObject(JSON.toJSONString(o), LogisticsRecord.class);
        this.logisticsText = r;
    }
}