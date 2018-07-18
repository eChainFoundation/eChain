package com.echain.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.echain.common.util.RestHttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public static final String withdraw_code = "QMHfX1";//提现验证码

    //EChain验证码
    public static final String verify_code = "k6Cqy";
    public static final String invitation_code = "FremO3";
    private static final String outside_appId = "60229";
    private static final String outside_signature = "d3148bcc3ac9563b61d427ab8a6609d6";
    private static final String china_appId = "21878";
    // 用户密钥
    private static final String china_signature = "2f3b38ab436ae0c6e8b543cbd9b50d8e";
    private static final String SEND_MESSAGE_URL = "http://api.mysubmail.com/message/xsend.json";
    private static final String SEND_INTERNATIONAL_URL = "http://api.mysubmail.com/internationalsms/xsend";

    @Autowired
    RestTemplate restTemplate;

    public void sendMessage(String phoneNo, String ticketNo, Integer time, String name, String country, String projectCode) throws Exception {
        Map<String, Object> vars = new HashMap<>();
        vars.put("code", ticketNo);
        vars.put("time", time);
        vars.put("name", name);

        sendMessage(phoneNo, country, projectCode, vars);
    }

    public void sendMessage(String phoneNo, String country, String projectCode, Map<String, Object> vars) throws Exception {
        String appId = china_appId;
        String signature = china_signature;
        String send_url = SEND_MESSAGE_URL;
        if (!"+86".equals(country)) {
            appId = outside_appId;
            signature = outside_signature;
            send_url = SEND_INTERNATIONAL_URL;
            phoneNo = country + phoneNo;
        }

        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("signature", signature);
        map.put("project", projectCode);
        map.put("to", phoneNo);
        map.put("vars", JSON.toJSONString(vars));

        String responseStr = RestHttpUtil.post(restTemplate, map, send_url, String.class);
        JSONObject result = JSON.parseObject(responseStr);

        logger.info(responseStr);
        String status = (String) result.get("status");
        logger.info(status);

        if (!"success".equals(status)) {
            throw new Exception("发送短信验证码失败！\t code: " + result.get("code") + "\t msg: " + result.get("msg"));
        }
    }

}
