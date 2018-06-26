package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class MatcheRequestVo {

    @ApiModelProperty(value = "物流公司Id", required = true, dataType = "Long")
    private Long companyId;

    @ApiModelProperty( value = "物流编号", required = true, dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "交易Id", required = true, dataType = "Long")
    private Long transactionId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
}
