package com.echain.rest;

import com.alibaba.fastjson.JSON;
import com.echain.common.BaseResponse;
import com.echain.entity.EcLogisticsCompany;
import com.echain.entity.EcLogisticsRecord;
import com.echain.service.LogisticsService;
import com.echain.service.UserService;
import com.echain.util.Md5Util;
import com.echain.vo.rest.request.SaveLogisticsRequestVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping(value = "/rest/logistics", produces = "application/json")
@RestController
public class LogisticsRest {

    private static final Logger logger = LoggerFactory.getLogger(LogisticsRest.class);

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "保存物流信息")
    @ApiImplicitParam(name = "requestVo", value = "请求体", required = true, dataType = "ReceiveTransactionsRequestVo")
    @PostMapping(value = "save_logistics")
    public BaseResponse<Long> getAllLogistics(@RequestBody SaveLogisticsRequestVo requestVo) {
        logger.info("save_logistics  ====  " + JSON.toJSONString(requestVo));

        EcLogisticsCompany logisticsCompany = null;
        if (StringUtils.isEmpty(requestVo.getLogisticsCompanyName())) {
            logisticsCompany = this.userService.getLogisticsCompanyByCompanyname(requestVo.getLogisticsCompanyName());
        }

        EcLogisticsRecord record = new EcLogisticsRecord();
        record.setCreateTime(new Date());
        if (logisticsCompany != null) {
            record.setLogisticsCompanyId(logisticsCompany.getId());
        }
        record.setLogisticsNo(requestVo.getLogisticsNo());
        record.setLogisticsText(requestVo.getLogisticsText());
        record.setLogisticsMd5(Md5Util.encode(requestVo.getLogisticsText()));
        record = this.logisticsService.saveLogistics(record);

        return record != null ? new BaseResponse<>(200, "成功", record.getId())
                : new BaseResponse<>(500, "失败", null);
    }
}
