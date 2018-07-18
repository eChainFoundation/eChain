package com.echain.vo.rest.response;

import com.echain.common.util.DateUtil;
import com.echain.entity.EcWithdrawRecord;
import com.echain.enumaration.WalletType;
import com.echain.enumaration.WithdrawType;
import com.echain.util.Utils;

import java.math.BigDecimal;

public class EcWithdrawRecordVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 代币类型
     */
    private String type;

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
    private String points;

    private String status;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public EcWithdrawRecordVo() {
    }

    public EcWithdrawRecordVo(EcWithdrawRecord record) {
        //this.id = record.getId();
        //this.userId = record.getUserId();
        this.type = WalletType.getWalletType(record.getType()).getName();
        this.wallet = record.getWallet();
        this.to = Utils.transStr(record.getTo());
        this.points = record.getPoints().setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();
        this.status = WithdrawType.getWalletType(record.getStatus()).getName();
        this.remark = record.getRemark();
        this.createTime = DateUtil.convert2String(record.getCreateTime(), DateUtil.FORMAT_YYYY_MM_DD_HHMMSS);
    }
}