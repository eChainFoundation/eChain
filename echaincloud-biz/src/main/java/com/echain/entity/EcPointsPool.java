package com.echain.entity;

import java.util.Date;

public class EcPointsPool extends BaseEntity {

    private Long nowPoints;

    private Long freezePoints;

    private Long consumePoints;

    private Long allPoints;

    private Date createTime;

    public Long getNowPoints() {
        return nowPoints;
    }

    public void setNowPoints(Long nowPoints) {
        this.nowPoints = nowPoints;;
    }

    public Long getFreezePoints() {
        return freezePoints;
    }

    public void setFreezePoints(Long freezePoints) {
        this.freezePoints = freezePoints;
    }

    public Long getConsumePoints() {
        return consumePoints;
    }

    public void setConsumePoints(Long consumePoints) {
        this.consumePoints = consumePoints;
    }

    public Long getAllPoints() {
        return allPoints;
    }

    public void setAllPoints(Long allPoints) {
        this.allPoints = allPoints;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}