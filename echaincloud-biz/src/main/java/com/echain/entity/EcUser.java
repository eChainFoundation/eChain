package com.echain.entity;

import java.util.Date;

public class EcUser extends BaseEntity {

    private String userState;

    private String country;

    private String phoneNumber;

    private String password;
    
    private String isUploadSingle;

    private Date createTime;

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState == null ? null : userState.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getPassword() {
        return password;
    }

    public String getIsUploadSingle() {
		return isUploadSingle;
	}

	public void setIsUploadSingle(String isUploadSingle) {
		this.isUploadSingle = isUploadSingle;
	}

	public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}