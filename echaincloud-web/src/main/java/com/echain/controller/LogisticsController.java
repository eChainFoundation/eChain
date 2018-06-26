package com.echain.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.echain.entity.EcLogisticsCompany;
import com.echain.entity.EcLogisticsRecord;
import com.echain.service.LogisticsService;
import com.echain.service.UserService;
import com.echain.util.JsonUtil;
import com.echain.util.Md5Util;

@RequestMapping("/logistics")
@Controller
public class LogisticsController {

	@Autowired
	private LogisticsService logisticsService;

	@Autowired
	private UserService userService;

	/**
	 * 保存物流信息
	 * 
	 * @return http://localhost:8090/logistics/save_logistics.action?logisticsCompanyName=logisticsCompanyName&logisticsNo=logisticsNo&logisticsText=logisticsText
	 */
	@RequestMapping(value = "save_logistics.action", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllLogistics(@RequestParam(required = true) String logisticsCompanyName,
			@RequestParam(required = true) String logisticsNo, @RequestParam(required = true) String logisticsText,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out
				.println("save_logistics  ====  logisticsCompanyName : " + logisticsCompanyName + " ---- logisticsNo : "
						+ logisticsNo + " ---- logisticsText : " + logisticsText + "  -----  Date : " + new Date());

		EcLogisticsCompany logisticsCompany = null;
		if (logisticsCompanyName != null && !"".equals(logisticsCompanyName))
			logisticsCompany = this.userService.getLogisticsCompanyByCompanyname(logisticsCompanyName);

		EcLogisticsRecord record = new EcLogisticsRecord();
		record.setCreateTime(new Date());
		if (logisticsCompany != null) {
			record.setLogisticsCompanyId(logisticsCompany.getId());
		}
		record.setLogisticsNo(logisticsNo);
		record.setLogisticsText(logisticsText);
		record.setLogisticsMd5(Md5Util.encode(logisticsText));
		record = this.logisticsService.saveLogistics(record);

		Map<String, Object> map = new HashMap<String, Object>();
		if (record != null) {
			map.put("code", "200");
			Long transactionId = record.getId();
			map.put("recordId", transactionId);
		} else {
			map.put("code", "500");
			map.put("recordId", "0");
		}

		return JsonUtil.convertToString(map);
	}

}
