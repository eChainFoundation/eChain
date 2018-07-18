package com.echain;

import com.alibaba.fastjson.JSON;
import com.echain.conf.ParamsProperties;
import com.echain.dao.EcWalletDao;
import com.echain.entity.EcUserDapp;
import com.echain.entity.EcWallet;
import com.echain.manager.RedisKeyMag;
import com.echain.redis.Redis;
import com.echain.service.*;
import com.echain.util.QrcodeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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

    @Resource(name = "admin")
    Admin admin;

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

    @Test
    public void testAccount() throws IOException {
        EthGetTransactionCount transactionCount = admin.ethGetTransactionCount("0xe1b7725ce3ef50f6d78e6202357bc80776e6dabe",
                DefaultBlockParameter.valueOf("latest")).send();

        System.out.println(JSON.toJSONString(transactionCount));

    }

    @Autowired
    PointService pointService;

    @Test
    public void testUpdatePoints() {
        pointService.changePoints2(8L, 10L, "", '0');
    }

    @Autowired
    UserDappService userDappService;

    @Test
    public void testDao() {
//        List<EcUserDapp> userDapps = userDappService.getBySingleUpload();
        List<EcUserDapp> userDapps = userDappService.getByNotSingleUpload("all");
        System.out.println(JSON.toJSONString(userDapps));
    }

//    @Autowired
//    SyncTransactionRecordTask recordTask;
//
//    @Test
//    public void testSyncRecord() {
//        String address = "0xe1b7725ce3ef50f6d78e6202357bc80776e6dabe";
//        recordTask.processor(address, 1);
//    }

    @Autowired
    MessageService messageService;

    @Test
    public void testMessage() throws Exception {
        String phoneNo = "18721747590";
        // 验证码逻辑
        int rand = (int) ((Math.random() * 9 + 1) * 100000);

        messageService.sendMessage(phoneNo, rand + "", 60, "ETH", "+86", "k6Cqy");
    }

//    @Autowired
//    MQService mqService;
//
//    @Test
//    public void testMq() {
//        mqService.convertAndSend(MessageConstant.EXCHANGE, MessageConstant.TEST_QUEUE, "hello");
//    }

    @Autowired
    EcWalletDao ecWalletDao;

    @Test
    public void createImg() {
        List<EcWallet> list = ecWalletDao.selectAll();

        list.stream().forEach(r -> {
            try {
                QrcodeUtil.createQrImg(r.getWallet(), paramsProperties.getWalletQimgPath() + r.getWallet() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
