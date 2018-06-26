package com.echain;

import com.alibaba.fastjson.JSON;
import com.echain.common.BaseResponse;
import com.echain.common.util.RestHttpUtil;
import com.echain.vo.rest.request.ReceiveLogisticsRequestVo;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class UtilTests {
    @Test
    public void testGet() {
        String url = "http://localhost:8090/rest/point/getPointByPhoneNumber?phoneNumber={1}";

        BaseResponse result = RestHttpUtil.get(new RestTemplate(), url, BaseResponse.class, "18721747590");

        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testPost() {
        String url = "http://localhost:8090/rest/transaction/receiveLogistics";

        ReceiveLogisticsRequestVo requestVo = new ReceiveLogisticsRequestVo();
        requestVo.setLogisticsCompanyName("test");
        requestVo.setLogisticsNo("132y9841");
        requestVo.setLogisticsText("hello");

        BaseResponse result = RestHttpUtil.post(new RestTemplate(), requestVo, url, BaseResponse.class);
        System.out.println(JSON.toJSONString(result));
    }
}
