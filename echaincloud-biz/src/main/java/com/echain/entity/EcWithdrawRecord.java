package com.echain.entity;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class EcWithdrawRecord {
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 代币类型
     */
    private Integer type;

    /**
     * 钱包地址
     */
    private String wallet;

    /**
     * 收款方
     */
    private String to;

    /**
     * 代币数量
     */
    private BigDecimal points;

    private Integer status;

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

    /**
     * 获取代币类型
     *
     * @return type - 代币类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置代币类型
     *
     * @param type 代币类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取钱包地址
     *
     * @return wallet - 钱包地址
     */
    public String getWallet() {
        return wallet;
    }

    /**
     * 设置钱包地址
     *
     * @param wallet 钱包地址
     */
    public void setWallet(String wallet) {
        this.wallet = wallet;
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
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
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

    public EcWithdrawRecord() {
    }

    public EcWithdrawRecord(Long id, Long userId, Integer type, String wallet, String to, BigDecimal points, Integer status, String remark) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.wallet = wallet;
        this.to = to;
        this.points = points;
        this.status = status;
        this.remark = remark;
    }
}