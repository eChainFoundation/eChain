package com.echain.vo.rest.request;

import io.swagger.annotations.ApiModelProperty;

public class SaveLogisticsRequestVo {

    @ApiModelProperty(value = "物流公司名称", required = true, dataType = "String")
    private String logisticsCompanyName;

    @ApiModelProperty(value = "物流单号", required = true, dataType = "String")
    private String logisticsNo;

    @ApiModelProperty(value = "物流详情描述", required = true, dataType = "String")
    private String logisticsText;

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsText() {
        return logisticsText;
    }

    public void setLogisticsText(String logisticsText) {
        this.logisticsText = logisticsText;
    }
}
