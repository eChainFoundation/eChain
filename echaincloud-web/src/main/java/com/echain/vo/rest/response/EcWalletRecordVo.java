package com.echain.vo.rest.response;

import com.echain.common.util.DateUtil;
import com.echain.entity.EcWalletRecord;
import com.echain.enumaration.WalletType;
import com.echain.util.Utils;

import java.math.BigDecimal;

public class EcWalletRecordVo {
    private Long id;

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
    private String type;

    private String points;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public EcWalletRecordVo() {
    }

    public EcWalletRecordVo(EcWalletRecord record, boolean flag) {
        this.id = record.getId();
        this.from = flag ? Utils.transStr(record.getFrom()) : record.getFrom();
        this.to = flag ? Utils.transStr(record.getTo()) : record.getTo();
        this.type = WalletType.getWalletType(record.getType()).getName();
        this.points = record.getPoints().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        this.remark = record.getRemark();
        this.createTime = DateUtil.convert2String(record.getCreateTime(), DateUtil.FORMAT_YYYY_MM_DD_HHMMSS);
    }
}