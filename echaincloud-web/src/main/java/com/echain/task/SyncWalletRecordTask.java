package com.echain.task;

import com.echain.conf.ParamsProperties;
import com.echain.entity.EcTransactionRecord;
import com.echain.entity.EcWallet;
import com.echain.entity.EcWalletRecord;
import com.echain.enumaration.WalletType;
import com.echain.service.TransactionRecordService;
import com.echain.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SyncWalletRecordTask {

    private static final Logger logger = LoggerFactory.getLogger(SyncWalletRecordTask.class);

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionRecordService transactionRecordService;

    @Autowired
    ParamsProperties paramsProperties;

    /**
     * 同步比特币充值交易记录
     */
    //@Scheduled(cron = "${params.sync-transaction-record-task-corn}")
    public void syncRecord() {
        //查询所有已绑定用户的wallet
        List<EcWallet> walletList = walletService.getByUsed();

        for (EcWallet wallet : walletList) {
            String contractAddress = WalletType.ECP.getIndex() == wallet.getType() ? paramsProperties.getContractAddress() : null;

            //根据wallet地址及flag=0查询所有未同步的充值交易(from ec_transaction_record)
            List<EcTransactionRecord> rechargeRecordList = transactionRecordService.getRechargeRecord(wallet.getWallet(),
                    wallet.getType(), contractAddress);

            for (EcTransactionRecord transactionRecord : rechargeRecordList) {
                int d = WalletType.ECP.getIndex() == transactionRecord.getType() ? paramsProperties.getDecimals() : 18;
                BigDecimal value = transactionRecord.getValue().divide(BigDecimal.valueOf(Math.pow(10, d)));

                //保存交易记录到ec_wallet_record
                EcWalletRecord walletRecord = new EcWalletRecord(wallet.getUserId(), transactionRecord.getFrom(),
                        transactionRecord.getTo(), transactionRecord.getType(), value,
                        "转入" + value + WalletType.getWalletType(transactionRecord.getType()).getName());
                walletService.saveRecord(walletRecord);

                //更新 ec_transaction_record  flag=1
                transactionRecordService.updateFlagById(transactionRecord.getId());

                //更新 wallet's value
                int flag = 0;
                while (flag == 0) {
                    wallet = walletService.selectByUserIdType(wallet.getUserId(), wallet.getType());
                    flag = walletService.updateValue(wallet.getUserId(), wallet.getType(),
                            wallet.getValue().add(value), wallet.getValue());
                }
            }
        }
    }
}
