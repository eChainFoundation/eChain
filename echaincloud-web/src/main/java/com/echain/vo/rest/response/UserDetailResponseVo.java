package com.echain.vo.rest.response;

import com.echain.entity.EcDapp;

import java.util.Map;

public class UserDetailResponseVo {

    private String userName;

    private Long userId;

    private Long points;

    private Map<EcDapp, Object> appPoints;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Map<EcDapp, Object> getAppPoints() {
        return appPoints;
    }

    public void setAppPoints(Map<EcDapp, Object> appPoints) {
        this.appPoints = appPoints;
    }

    public UserDetailResponseVo() {
    }

    public UserDetailResponseVo(String userName, Long userId, Long points, Map<EcDapp, Object> appPoints) {
        this.userName = userName;
        this.userId = userId;
        this.points = points;
        this.appPoints = appPoints;
    }
}
