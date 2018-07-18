package com.echain.entity;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class EcWalletRecord {
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 发款方
     */
    private String from;

    /**
     * 收款方
     */
    private String to;

    /**
     * 钱包类型
     */
    private Integer type;

    /**
     * 代币数量
     */
    private BigDecimal points;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 获取代币数量
     *
     * @return points - 代币数量
     */
    public BigDecimal getPoints() {
        return points;
    }

    /**
     * 设置代币数量
     *
     * @param points 代币数量
     */
    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public EcWalletRecord() {
    }

    public EcWalletRecord(Long userId, String from, String to, Integer type, BigDecimal points, String remark) {
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.type = type;
        this.points = points;
        this.remark = remark;
    }
}