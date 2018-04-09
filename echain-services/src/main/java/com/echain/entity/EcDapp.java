package com.echain.entity;

import java.util.Date;

public class EcDapp extends BaseEntity {

    private String dappName;

    private String dappLogo;

    private String dappUrl;

    private String status;

    private Date createTime;

    private String describeText;

    public String getDappName() {
        return dappName;
    }

    public void setDappName(String dappName) {
        this.dappName = dappName == null ? null : dappName.trim();
    }

    public String getDappLogo() {
        return dappLogo;
    }

    public void setDappLogo(String dappLogo) {
        this.dappLogo = dappLogo == null ? null : dappLogo.trim();
    }

    public String getDappUrl() {
        return dappUrl;
    }

    public void setDappUrl(String dappUrl) {
        this.dappUrl = dappUrl == null ? null : dappUrl.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
}