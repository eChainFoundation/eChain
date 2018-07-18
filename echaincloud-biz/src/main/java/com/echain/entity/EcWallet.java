package com.echain.entity;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class EcWallet {
    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 钱包地址
     */
    private String wallet;

    /**
     * 钱包类型：1-以太坊，2-EOS
     */
    private Integer type;

    /**
     * 数量
     */
    private BigDecimal value;

    /**
     * 钱包密码
     */
    private String password;

    /**
     * 钱包文件
     */
    private String walletFile;

    /**
     * 钱包文件
     */
    private String qimgFile;

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
     * 获取用户Id
     *
     * @return user_id - 用户Id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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

    /**
     * 获取钱包类型：1-以太坊，2-EOS
     *
     * @return type - 钱包类型：1-以太坊，2-EOS
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置钱包类型：1-以太坊，2-EOS
     *
     * @param type 钱包类型：1-以太坊，2-EOS
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取数量
     *
     * @return value - 数量
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * 设置数量
     *
     * @param value 数量
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * 获取钱包密码
     *
     * @return password - 钱包密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置钱包密码
     *
     * @param password 钱包密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取钱包文件
     *
     * @return wallet_file - 钱包文件
     */
    public String getWalletFile() {
        return walletFile;
    }

    /**
     * 设置钱包文件
     *
     * @param walletFile 钱包文件
     */
    public void setWalletFile(String walletFile) {
        this.walletFile = walletFile;
    }

    public String getQimgFile() {
        return qimgFile;
    }

    public void setQimgFile(String qimgFile) {
        this.qimgFile = qimgFile;
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

    public EcWallet() {
    }

    public EcWallet(Long userId, String wallet, Integer type) {
        this.userId = userId;
        this.wallet = wallet;
        this.type = type;
    }
}