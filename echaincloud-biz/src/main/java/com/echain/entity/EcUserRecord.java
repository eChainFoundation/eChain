package com.echain.entity;

import java.util.Date;
import javax.persistence.*;

public class EcUserRecord {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 比特币类型
     */
    private Byte type;

    /**
     * from比特币地址
     */
    private String from;

    /**
     * to比特币地址
     */
    private String to;

    /**
     * 比特币数量
     */
    private Double value;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
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
     * 获取比特币类型
     *
     * @return type - 比特币类型
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置比特币类型
     *
     * @param type 比特币类型
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取from比特币地址
     *
     * @return from - from比特币地址
     */
    public String getFrom() {
        return from;
    }

    /**
     * 设置from比特币地址
     *
     * @param from from比特币地址
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 获取to比特币地址
     *
     * @return to - to比特币地址
     */
    public String getTo() {
        return to;
    }

    /**
     * 设置to比特币地址
     *
     * @param to to比特币地址
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 获取比特币数量
     *
     * @return value - 比特币数量
     */
    public Double getValue() {
        return value;
    }

    /**
     * 设置比特币数量
     *
     * @param value 比特币数量
     */
    public void setValue(Double value) {
        this.value = value;
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
}