package com.echain;

import com.echain.conf.ParamsProperties;
import com.echain.manager.RedisKeyMag;
import com.echain.redis.Redis;
import com.echain.service.TaskService;
import com.echain.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EchainApplicationTests {

    @Autowired
    ParamsProperties paramsProperties;

    @Autowired
    RedisKeyMag redisKeyMag;

    @Autowired
    TaskService taskService;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        System.out.println(paramsProperties.getUploadImgPath());
    }

    @Test
    public void testRedis() {
        redisKeyMag.setValue("test", "hello redis", 1, Redis.MINUTES);
        System.out.println(redisKeyMag.getValue("test", String.class));
    }

    @Autowired
    WalletService walletService;

    @Test
    public void testWallet() {
        String address = "0xA2A6f79aB139718C8e07062a4B16c517EE675f1c";
        int page = 1;
        int size = 100;
        String sort = "asc";

//        walletService.getTokenTransferEvents(address, page, size, sort);
        walletService.getTokenTransferEvents(page, size, sort);
    }
}
