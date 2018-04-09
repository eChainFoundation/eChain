package com.echain.entity;

import java.util.Date;

public class EcPointsPool extends BaseEntity {

    private String nowPoints;

    private String freezePoints;

    private String consumePoints;

    private String allPoints;

    private Date createTime;

    public String getNowPoints() {
        return nowPoints;
    }

    public void setNowPoints(String nowPoints) {
        this.nowPoints = nowPoints == null ? null : nowPoints.trim();
    }

    public String getFreezePoints() {
        return freezePoints;
    }

    public void setFreezePoints(String freezePoints) {
        this.freezePoints = freezePoints == null ? null : freezePoints.trim();
    }

    public String getConsumePoints() {
        return consumePoints;
    }

    public void setConsumePoints(String consumePoints) {
        this.consumePoints = consumePoints == null ? null : consumePoints.trim();
    }

    public String getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(String allPoints) {
        this.allPoints = allPoints == null ? null : allPoints.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}