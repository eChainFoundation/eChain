package com.echain.task;

import com.alibaba.fastjson.JSON;
import com.echain.common.util.CommonUtil;
import com.echain.common.util.DateUtil;
import com.echain.common.util.ExceptionUtil;
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
import com.echain.util.Md5Util;
import com.echain.vo.ImportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时数据上链
 */
@Service
public class UpTransactionTask {

    private static final Logger logger = LoggerFactory.getLogger(UpTransactionTask.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserDappService userDappService;

    @Autowired
    EcOrderDao orderDao;

    @Autowired
    UpDownData upDownData;

    @Resource(name = "web3j")
    Web3j web3j;

    @Scheduled(cron = "${params.up-transaction-task-corn}")
    public void upTransaction() {
        Date date = new Date();
        handler(date, DateUtil.convert2String(date, "yyyyMMdd"), "all", web3j, null);
        handler(DateUtil.addDays(date, -1), DateUtil.convert2String(DateUtil.addDays(date, -1),
                "yyyyMMdd"), "day", web3j, null);
    }

    @Async
    public void testHandler(Date date, String frequency, Web3j web3j, BigInteger price) {
        String time = DateUtil.convert2String(date, "yyyyMMdd");
        handler(date, time, frequency, web3j, price);
    }

    public void handler(Date date, String time, String frequency, Web3j web3j, BigInteger price) {
        List<EcUserDapp> userDapps = userDappService.getBySingleUpload(frequency);

        try {
            String userAppIds = null;
            if (!CommonUtil.isEmpty(userDapps)) {
                for (EcUserDapp userDapp : userDapps) {
                    userAppIds = userAppIds == null ? userDapp.getId() + "" : userAppIds + "," + userDapp.getId();

                    List<EcTransaction> list = taskService
                            .selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()), date);

                    if (!CommonUtil.isEmpty(list)) {
                        List<Long> tids = new ArrayList<>();
                        Map<String, Object> map = new HashMap<>();
                        executor(list, map, tids);

                        upload(userDapp.getUserId(), userDapp.getDappId(), tids, map, time, web3j, price);
                    }
                }
            }

            List<EcUserDapp> notUserDapps = userDappService.getByNotSingleUpload(frequency);

            if (!CommonUtil.isEmpty(notUserDapps)) {
                // 不是单独上链的DappId集合
                Set<Long> dappIdSet = notUserDapps.stream().map(r -> r.getDappId()).collect(Collectors.toSet());

                for (Long dappId : dappIdSet) {
                    List<EcTransaction> transactions = taskService.selectListTransactionsNotSingleUploadDappId(userAppIds, dappId, date);

                    if (!CommonUtil.isEmpty(transactions)) {
                        List<Long> tids2 = new ArrayList<>();
                        Map<String, Object> map = new HashMap<>();
                        executor(transactions, map, tids2);

                        upload(0L, dappId, tids2, map, time, web3j, price);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("交易数据同步失败！");
            logger.error(ExceptionUtil.getExceptionMsg(e));
        }
    }

    private void executor(List<EcTransaction> list, Map<String, Object> map, List<Long> tids) {
        list.stream().forEach(r -> {
            List<String> records = new ArrayList<>();

            List<EcLogisticsRecord> recordList = r.getRecords();
            if (!CommonUtil.isEmpty(recordList)) {
                recordList.stream().forEach(m -> records.add(m.getLogisticsMd5()));
            }

            map.put(r.getDescribeMd5(), records);
            tids.add(r.getId());
        });
    }

    private void upload(Long userId, Long dappId, List<Long> tids, Map<String, Object> md5Map, String time, Web3j web3j,
                        BigInteger price) {
        if (!CommonUtil.isEmpty(tids)) {
            logger.info(new StringBuffer("同步以太坊交易数据").append(",userId=").append(userId)
                    .append(",dappId=").append(dappId).append(",条数=").append(tids.size()).toString());

            if (!CommonUtil.isEmpty(md5Map)) {
                String dataMd5 = Md5Util.encode(JSON.toJSONString(md5Map));

                // 可重试执行2次
                for (int i = 0; i <= 2; i++) {
                    try {
                        if (i > 0) {
                            logger.info(new StringBuffer("重试上链第").append(i).append("次")
                                    .append(",userId=").append(userId).append(",dappId=").append(dappId).toString());
                        }
                        processor(userId, dappId, tids, dataMd5, time, web3j, price);
                        logger.info(new StringBuffer("同步以太坊交易数据").append(",userId=").append(userId)
                                .append(",dappId=").append(dappId).append(",条数=").append(tids.size())
                                .append(", 成功").toString());
                        return;
                    } catch (Exception e) {
                        logger.error(new StringBuffer("同步以太坊交易数据").append(",userId=").append(userId)
                                .append(",dappId=").append(dappId).append(",条数=").append(tids.size())
                                .append(", 失败").toString());
                        logger.error(ExceptionUtil.getExceptionMsg(e));
                    }
                }
            }
        }
    }

    private void processor(Long userId, Long dappId, List<Long> tids, String dataMd5, String time, Web3j web3j, BigInteger price) throws Exception {
        // 数据上链
        Map<String, String> map = upDownData.uploadData(userId, dappId, dataMd5, time, web3j, price);

        if (!CommonUtil.isEmpty(map)) {
            String key = map.get("key");
            if (StringUtils.isEmpty(key)) {
                key = time + ":" + userId + ":" + dappId;
            }
            // 批量更新交易记录
            taskService.batchUpdateTransaction(tids, key, map.get("hash"), map.get("block_no"), dataMd5);
        }
    }

    @Async
    public void toChain(ImportVo vo) {
        String descText = JSON.toJSONString(vo);
        String descMd5 = Md5Util.encode(descText);

        EcOrder order = new EcOrder(vo.getOrderId(), descMd5, descText);

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
                    logger.info("key=" + ls.get(0).key);
                }
            }

            order.setTransactionKey(key);
            order.setTransactionHash(hash);
            order.setBlockNo(blockNo);

            orderDao.insert(order);
            logger.info("订单数据上链成功，orderId=" + vo.getOrderId());
        } catch (Exception e) {
            logger.error("订单数据上链失败，orderId=" + vo.getOrderId());
            logger.error(ExceptionUtil.getExceptionMsg(e));
        }
    }
}
