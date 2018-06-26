package com.echain.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.echain.entity.EcDapp;
import com.echain.entity.EcUser;
import com.echain.entity.EcUserDapp;
import com.echain.entity.EcUserPoints;
import com.echain.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user_detail.do", produces = "text/html;charset=UTF-8")
	public String personlPage(@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "type", required = false) String type, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws IOException {
		EcUser user = userService.getUserByPhoneNumberNoInsert(phoneNumber);
		if (user == null || !type.equals("emall")) {
			response.sendError(404);
			return null;
		}
		modelMap.put("userName", user.getPhoneNumber());
		modelMap.put("userId", user.getId());
		// 剩余总积分
		EcUserPoints userPoint = userService.getUserPointByUserIdNoInsert(user.getId());
		modelMap.put("points", userPoint.getNowPoints());
		// 每个app获取积分总数
		LinkedHashMap<EcDapp, Object> appPoints = new LinkedHashMap<>();
		List<EcUserDapp> userDapps = userService.getUserDappPointsByUserId(user.getId());
		userDapps.stream().sorted(Comparator.comparing(EcUserDapp::getId));
		for (EcUserDapp ecUserDapp : userDapps) {
			Long dappId = ecUserDapp.getDappId();
			if (dappId == 1)
				continue;
			EcDapp dapp = userService.getDappByDappId(dappId);
			appPoints.put(dapp, ecUserDapp.getGetPoints());
		}
		modelMap.put("appPoints", appPoints);
		return "echain/personal";
	}

	@RequestMapping(value = "{userId}/upload.do", produces = "text/html;charset=UTF-8")
	public String upload(@PathVariable("userId") String userId, ModelMap modelMap) {
		modelMap.put("userId", userId);
		return "echain/upload";
	}

}
