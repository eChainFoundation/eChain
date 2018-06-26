package com.echain.controller;

import com.echain.common.util.DateUtil;
import com.echain.entity.EcTransaction;
import com.echain.entity.EcUserDapp;
import com.echain.service.TaskService;
import com.echain.service.UserDappService;
import com.echain.solidity.UpDownData;
import com.echain.task.UpTransactionTask;
import com.echain.util.JsonUtil;
import com.echain.util.Md5Util;
import com.echain.vo.ImportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/***
 * 简介：测试类
 * <p>
 * code review 2018-03-09 16:16
 *
 * @author roc
 *
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserDappService userDappService;

    @Autowired
    UpDownData upDownData;

    @RequestMapping(value = "test.html", produces = "text/html;charset=UTF-8")
    public String process() {

        List<EcUserDapp> userDapps = this.userDappService.getBySingleUpload();

        try {
            String userAppIds = null;
            if (userDapps != null && userDapps.size() > 0) {
                for (EcUserDapp userDapp : userDapps) {
                    if (userAppIds == null) {
                        userAppIds = userDapp.getId() + "";
                    } else {
                        userAppIds += "," + userDapp.getId();
                    }

                    List<EcTransaction> list = taskService
                            .selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()));
                    List<Long> tids = new ArrayList<>();

                    Map<String, Object> map = new HashMap<>();
                    list.stream().forEach(r -> {
                        List<String> recoreds = r.getRecords().stream().map(m -> m.getLogisticsMd5())
                                .collect(Collectors.toList());

                        map.put(r.getDescribeMd5(), recoreds);
                        tids.add(r.getId());
                    });

                    upload(userDapp.getUserId(), userDapp.getDappId(), tids, map);
                }
            }

            List<EcUserDapp> notUserDapps = this.userDappService.getByNotSingleUpload();

            if (notUserDapps != null && notUserDapps.size() > 0) {
                // 不是单独上链的DappId集合
                Set<Long> dappIdSet = notUserDapps.stream().map(r -> r.getDappId()).collect(Collectors.toSet());

                for (Long dappId : dappIdSet) {
                    List<EcTransaction> transactions = this.taskService
                            .selectListTransactionsNotSingleUploadDappId(userAppIds, dappId);
                    List<Long> tids2 = new ArrayList<>();

                    Map<String, Object> map = new HashMap<>();
                    transactions.stream().forEach(r -> {
                        List<String> recoreds = r.getRecords().stream().map(m -> m.getLogisticsMd5())
                                .collect(Collectors.toList());

                        map.put(r.getDescribeMd5(), recoreds);
                        tids2.add(r.getId());
                    });

                    upload(0l, dappId, tids2, map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("交易数据同步失败！失败开始ID ==== ");
        }

        return "1";
    }

    private void upload(Long userId, Long dappId, List<Long> tids, Map<String, Object> md5Map) throws Exception {
        if (tids != null && tids.size() > 0) {
            System.out.println(new Date() + "同步以太坊交易数据条数  ==== " + tids.size());

            if (md5Map != null && md5Map.size() > 0) {
                String dataMd5 = Md5Util.encode(JsonUtil.convertToString(md5Map));
                // 数据上链
                Map<String, String> map = upDownData.uploadData(userId, dappId, dataMd5);
                if (map != null && map.size() > 0) {
                    System.out.println(new Date() + "批量更新交易数据条数  ==== " + tids.size());

                    String key = map.get("key");
                    if (key == null) {
                        String date = DateUtil.convert2String(new Date(), "yyyyMMdd");
                        key = date + ":" + userId + ":" + dappId;
                    }
                    // 批量更新交易记录
                    this.taskService.batchUpdateTransaction(tids, key, map.get("hash"), map.get("block_no"), dataMd5);
                    System.out.println(new Date() + "批量更新交易数据成功 ");
                }
            }
        }
    }

    @Autowired
    UpTransactionTask task;

    @RequestMapping(value = "task", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String task() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(
                "DSBB059201806058181141510281931,黄先生,13828849854,59,海淘正品险,0.399,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143563166,399,无,620354653830,意大利你好,直邮,2018/6/1 0:38,暂无,");
        list.add(
                "DSBB059201806058181142026886258,徐犹文,15980276753,59,海淘正品险,0.395,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143668337,395,无,889934862156597325,意大利你好,直邮,2018/6/1 0:39,暂无,");
        list.add(
                "DSBB059201806058181142431860769,李烨,13985070994,59,海淘正品险,1.088,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143685158,1088,无无,620354653867,意大利你好,直邮,2018/6/1 0:39,暂无暂无,");
        list.add(
                "DSBB059201806058181142835434200,付磊,18845034444,59,海淘正品险,1.999,2018/6/5 14:45,2018/6/5 14:45,LROAAH,SO97143994996,1999,无,620354654146,意大利你好,直邮,2018/6/1 0:40,暂无,");

        for (String str : list) {
            String[] sp = str.split(",");

            ImportVo vo = new ImportVo();

            vo.setOrderId(sp[0]);
            vo.setUserName(sp[1]);
            vo.setPhoneNumber(sp[2]);
            vo.setProductId(sp[3]);
            vo.setProductName(sp[4]);
            vo.setPremium(sp[5]);
            vo.setOrderTime(sp[6]);
            vo.setInsureTime(sp[7]);
            vo.setSource(sp[8]);
            vo.setPurchasOrderNo(sp[9]);
            vo.setPurchasOrderAmount(sp[10]);
            vo.setCategory(sp[11]);
            vo.setExpressNo(sp[12]);
            vo.setExpressCompanyName(sp[13]);
            vo.setExpressChannel(sp[14]);
            vo.setExpressTime(sp[15]);
            vo.setBrand(sp[16]);

            task.toChain(vo);
        }
        return "success";
    }
}
