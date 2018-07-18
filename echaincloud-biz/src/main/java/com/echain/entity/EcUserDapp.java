package com.echain.entity;

import java.util.Date;

public class EcUserDapp extends BaseEntity {

    private Long userId;

    private Long dappId;
    
    private String isUploadSingle;

    private Long consumePoints;

    private Long getPoints;

    private Date createTime;

    private String upEchainFrequency;

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

    public String getIsUploadSingle() {
		return isUploadSingle;
	}

	public void setIsUploadSingle(String isUploadSingle) {
		this.isUploadSingle = isUploadSingle;
	}

	public Long getConsumePoints() {
		return consumePoints;
	}

	public void setConsumePoints(Long consumePoints) {
		this.consumePoints = consumePoints;
	}

	public Long getGetPoints() {
		return getPoints;
	}

	public void setGetPoints(Long getPoints) {
		this.getPoints = getPoints;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpEchainFrequency() {
        return upEchainFrequency;
    }

    public void setUpEchainFrequency(String upEchainFrequency) {
        this.upEchainFrequency = upEchainFrequency;
    }
}