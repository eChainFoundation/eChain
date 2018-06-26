package com.echain.task;

import com.alibaba.fastjson.JSON;
import com.echain.common.util.DateUtil;
import com.echain.dao.EcOrderDao;
import com.echain.entity.EcLogisticsRecord;
import com.echain.entity.EcOrder;
import com.echain.entity.EcTransaction;
import com.echain.entity.EcUserDapp;
import com.echain.service.TaskService;
import com.echain.service.UserDappService;
import com.echain.solidity.SaveData_sol_SaveData;
import com.echain.solidity.SaveData_sol_SaveData.SetStringEventResponse;
import com.echain.solidity.UpDownData;
import com.echain.util.JsonUtil;
import com.echain.util.Md5Util;
import com.echain.vo.ImportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UpTransactionTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpTransactionTask.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserDappService userDappService;

    @Autowired
    EcOrderDao orderDao;

    @Autowired
    UpDownData upDownData;

    @Scheduled(cron = "0 0 23 * * ?")
    public void upTransaction() {
        List<EcUserDapp> userDapps = userDappService.getBySingleUpload();

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

                    if (list != null && list.size() > 0) {
                        List<Long> tids = new ArrayList<>();

                        Map<String, Object> map = new HashMap<>();
                        list.stream().forEach(r -> {
                            List<String> recoreds = new ArrayList<>();

                            List<EcLogisticsRecord> recordList = r.getRecords();
                            if (recordList != null && recordList.size() > 0) {
                                recordList.stream().forEach(m -> {
                                    recoreds.add(m.getLogisticsMd5());
                                });
                            }

                            map.put(r.getDescribeMd5(), recoreds);
                            tids.add(r.getId());
                        });

                        upload(userDapp.getUserId(), userDapp.getDappId(), tids, map);
                    }
                }
            }

            List<EcUserDapp> notUserDapps = userDappService.getByNotSingleUpload();

            if (notUserDapps != null && notUserDapps.size() > 0) {
                // 不是单独上链的DappId集合
                Set<Long> dappIdSet = notUserDapps.stream().map(r -> r.getDappId()).collect(Collectors.toSet());

                for (Long dappId : dappIdSet) {
                    List<EcTransaction> transactions = taskService
                            .selectListTransactionsNotSingleUploadDappId(userAppIds, dappId);

                    if (transactions != null && transactions.size() > 0) {
                        List<Long> tids2 = new ArrayList<>();

                        Map<String, Object> map = new HashMap<>();
                        transactions.stream().forEach(r -> {
                            List<String> recoreds = new ArrayList<>();

                            List<EcLogisticsRecord> recordList = r.getRecords();
                            if (recordList != null && recordList.size() > 0) {
                                recordList.stream().forEach(m -> {
                                    recoreds.add(m.getLogisticsMd5());
                                });
                            }

                            map.put(r.getDescribeMd5(), recoreds);
                            tids2.add(r.getId());
                        });

                        upload(0l, dappId, tids2, map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("交易数据同步失败！");
        }
    }

    private void upload(Long userId, Long dappId, List<Long> tids, Map<String, Object> md5Map) throws Exception {
        if (tids != null && tids.size() > 0) {
            System.out.println(
                    new Date() + " 同步以太坊交易数据" + ",userId=" + userId + ",dappId=" + dappId + ",条数=" + tids.size());

            if (md5Map != null && md5Map.size() > 0) {
                String dataMd5 = Md5Util.encode(JsonUtil.convertToString(md5Map));

                int tryNum = 2; // 执行次数

                for (int i = 0; i <= tryNum; i++) {
                    try {
                        if (i > 0) {
                            System.out.println(
                                    new Date() + " 重试上链第" + i + "次" + ",userId=" + userId + ",dappId=" + dappId);
                        }
                        processor(userId, dappId, tids, dataMd5);
                        System.out.println(new Date() + " 同步以太坊交易数据" + ",userId=" + userId + ",dappId=" + dappId
                                + ",条数=" + tids.size() + ", 成功");
                        return;
                    } catch (Exception e) {
                        System.out.println(new Date() + " 同步以太坊交易数据" + ",userId=" + userId + ",dappId=" + dappId
                                + ",条数=" + tids.size() + ", 失败");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void processor(Long userId, Long dappId, List<Long> tids, String dataMd5) throws Exception {
        // 数据上链
        Map<String, String> map = upDownData.uploadData(userId, dappId, dataMd5);

        if (map != null && map.size() > 0) {
            String key = map.get("key");
            if (key == null) {
                String date = DateUtil.convert2String(new Date(), "yyyyMMdd");
                key = date + ":" + userId + ":" + dappId;
            }
            // 批量更新交易记录
            taskService.batchUpdateTransaction(tids, key, map.get("hash"), map.get("block_no"), dataMd5);
            // System.out.println(new Date() + "批量更新交易数据成功， " + "条数：" + tids.size());
        }
    }

    @Async
    public void toChain(ImportVo vo) {
        String descText = JSON.toJSONString(vo);
        String descMd5 = Md5Util.encode(descText);

        EcOrder order = new EcOrder();

        order.setDescribeText(descText);
        order.setDescribeMd5(descMd5);
        order.setOrderId(vo.getOrderId());

        try {
            SaveData_sol_SaveData contract = upDownData.getContract();

            String key = vo.getOrderId();
            String hash = "";
            String blockNo = "";

            TransactionReceipt transaction = contract.setstring(key, descMd5).send();

            if (transaction.getTransactionHash() != null) {
                hash = transaction.getTransactionHash();
                blockNo = transaction.getBlockNumber().toString();
                List<SetStringEventResponse> ls = contract.getSetStringEvents(transaction);
                if (ls.size() == 1) {
                    System.out.println("key=" + ls.get(0).key);
                }
            }

            order.setTransactionKey(key);
            order.setTransactionHash(hash);
            order.setBlockNo(blockNo);

            orderDao.insert(order);
            LOGGER.info("订单数据上链成功，orderId=" + vo.getOrderId());
        } catch (Exception e) {
            LOGGER.error("订单数据上链失败，orderId=" + vo.getOrderId());
            e.printStackTrace();
        }
    }
}
