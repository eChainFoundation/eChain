package com.echain.entity;

import java.util.Date;

public class EcTakePoints extends BaseEntity {

    private Long userWalletId;

    private Long takePoints;

    private String type;

    private String status;

    private Date createTime;

    public Long getUserWalletId() {
        return userWalletId;
    }

    public void setUserWalletId(Long userWalletId) {
        this.userWalletId = userWalletId;
    }

    public Long getTakePoints() {
        return takePoints;
    }

    public void setTakePoints(Long takePoints) {
        this.takePoints = takePoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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

    public EcTakePoints() {
    }

    public EcTakePoints(Long userWalletId, Long takePoints, String type, String status, Date createTime) {
        this.userWalletId = userWalletId;
        this.takePoints = takePoints;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
    }
}