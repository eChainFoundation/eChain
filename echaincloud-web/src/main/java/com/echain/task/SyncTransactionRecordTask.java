package com.echain.task;

import com.echain.common.util.CommonUtil;
import com.echain.common.util.ExceptionUtil;
import com.echain.conf.ParamsProperties;
import com.echain.entity.EcBlockNumber;
import com.echain.entity.EcTransactionRecord;
import com.echain.entity.EcWallet;
import com.echain.enumaration.WalletType;
import com.echain.service.TransactionRecordService;
import com.echain.service.WalletService;
import com.echain.vo.TokenTransferEvent;
import com.echain.vo.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Profile(value = "prod")
@Service
public class SyncTransactionRecordTask {

    private static final Logger logger = LoggerFactory.getLogger(SyncTransactionRecordTask.class);

    @Autowired
    TransactionRecordService recordService;

    @Autowired
    WalletService walletService;

    @Resource(name = "web3j")
    Web3j web3j;

    @Resource(name = "web3jTwo")
    Web3j web3jTwo;

    @Autowired
    ParamsProperties paramsProperties;

    /**
     * 同步比特币交易记录到数据库
     */
    @Scheduled(cron = "${params.sync-transaction-record-task-corn}")
    public void syncRecord() {
        //同步主账号的
        processor(paramsProperties.getMainAccount(), WalletType.ECP.getIndex());
        processor(paramsProperties.getMainAccount(), WalletType.ETH.getIndex());

        //查询所有已绑定用户的wallet
        List<EcWallet> walletList = walletService.getByUsed();

        for (EcWallet wallet : walletList) {
            if (WalletType.ECP.getIndex() == wallet.getType() || WalletType.ETH.getIndex() == wallet.getType()) {
                processor(wallet.getWallet(), wallet.getType());
            }
        }
    }

    public void processor(String address, int type) {
        BigInteger blockTotalNumber;

        try {
            blockTotalNumber = web3j.ethBlockNumber().send().getBlockNumber();
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMsg(e));
            try {
                blockTotalNumber = web3jTwo.ethBlockNumber().send().getBlockNumber();
            } catch (Exception e1) {
                logger.error("获取区块数失败:" + ExceptionUtil.getExceptionMsg(e1));
                return;
            }
        }

        handler(address, blockTotalNumber, type);
    }

    public void handler(String address, BigInteger blockTotalNumber, Integer type) {
        Long startNumber = walletService.getNumberByWallet(address, type);

        if (startNumber == null) {
            startNumber = 0L;
            walletService.saveBlockNumber(new EcBlockNumber(address, startNumber, type));
        }

        if (blockTotalNumber.longValue() <= startNumber) {
            return;
        }

        List<EcTransactionRecord> list = new ArrayList<>();
        int count = 0;

        if (WalletType.ETH.getIndex() == type) {
            List<TransferEvent> transferEvents = walletService.getTransferEvents(address,
                    BigInteger.valueOf(startNumber), blockTotalNumber, "asc");

            if (CommonUtil.isEmpty(transferEvents)) {
                walletService.updateNumber(address, blockTotalNumber.longValue(), type);
                return;
            }

            list = transferEvents.stream().map(m -> new EcTransactionRecord(m, address, type)).collect(Collectors.toList());
            count = transferEvents.size();
        } else if (WalletType.ECP.getIndex() == type) {
            List<TokenTransferEvent> events = walletService.getTokenTransferEvents(address,
                    BigInteger.valueOf(startNumber), blockTotalNumber, "asc");

            if (CommonUtil.isEmpty(events)) {
                walletService.updateNumber(address, blockTotalNumber.longValue(), type);
                return;
            }

            list = events.stream().map(m -> new EcTransactionRecord(m, address, type)).collect(Collectors.toList());
            count = events.size();
        }

        logger.info("同步比特币交易记录：address= " + address + ",条数=" + count + " start");
        recordService.batchInsert(list);
        logger.info("同步比特币交易记录：address= " + address + ",条数=" + count + " end");

        walletService.updateNumber(address, blockTotalNumber.longValue(), type);
    }
}
