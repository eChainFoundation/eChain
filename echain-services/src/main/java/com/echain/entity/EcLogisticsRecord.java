package com.echain.entity;

import java.util.Date;

public class EcLogisticsRecord extends BaseEntity {

    private String logisticsCompany;

    private String logisticsNo;

    private String logisticsMd5;

    private Date createTime;

    private String logisticsText;

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public String getLogisticsMd5() {
        return logisticsMd5;
    }

    public void setLogisticsMd5(String logisticsMd5) {
        this.logisticsMd5 = logisticsMd5 == null ? null : logisticsMd5.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLogisticsText() {
        return logisticsText;
    }

    public void setLogisticsText(String logisticsText) {
        this.logisticsText = logisticsText == null ? null : logisticsText.trim();
    }
}