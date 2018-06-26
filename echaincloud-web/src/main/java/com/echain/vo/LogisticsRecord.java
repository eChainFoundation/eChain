package com.echain.vo;

import java.util.Date;

import com.echain.entity.BaseEntity;

public class LogisticsRecord extends BaseEntity {

    private Long logisticsCompanyId;
    
    private String logisticsNo;

    private Long transactionId;
    
    private Long ecLogisticsId;

    private String productName;

    private String optionerName;

    private String optionContent;

    private String createTime;

    private String describeText;

    public Long getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    public void setLogisticsCompanyId(Long logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }

    public Long getEcLogisticsId() {
		return ecLogisticsId;
	}

	public void setEcLogisticsId(Long ecLogisticsId) {
		this.ecLogisticsId = ecLogisticsId;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getOptionerName() {
        return optionerName;
    }

    public void setOptionerName(String optionerName) {
        this.optionerName = optionerName == null ? null : optionerName.trim();
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent == null ? null : optionContent.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescribeText() {
        return describeText;
    }

    public void setDescribeText(String describeText) {
        this.describeText = describeText == null ? null : describeText.trim();
    }
}