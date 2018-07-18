package com.echain.service;

import com.echain.dao.EcTransactionDao;
import com.echain.entity.EcTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private EcTransactionDao transactionDao;

    /*public Long selectMinIdNokeyAndChecked() {
        Long id = transactionDao.selectMinIdNokeyAndChecked();
        return id;
    }

    public List<EcTransaction> selectListTransactionMD5s(Long minId, Integer upDataSize) {
        List<EcTransaction> list = transactionDao.selectListTransactionMD5s(minId, upDataSize);
        return list;
    }

    public void updateTransactionByPrimaryKeySelective(EcTransaction tx) {
        this.transactionDao.updateByPrimaryKeySelective(tx);
    }

    public Long selectMinIdNokey() {
        return this.logisticsDao.selectMinIdNokey();
    }

    public List<EcLogisticsRecord> selectLogisticsRecords(Long minId, Integer upDataSize) {
        return this.logisticsDao.selectLogisticsRecords(minId, upDataSize);
    }

    public void updateLogisticsRecordByPrimaryKeySelective(EcLogisticsRecord tx) {
        this.logisticsDao.updateByPrimaryKeySelective(tx);
    }

    public List<EcTransaction> selectListTransactionMds5ByUserId(Long userId) {
        return this.transactionDao.selectListTransactionMD5sByUserId(userId);
    }

    public List<EcTransaction> selectListTransactionsNotSingleUpload(String userIds) {
        return this.transactionDao.selectListTransactionsNotSingleUpload(userIds);
    }*/

    public int batchUpdateTransaction(List<Long> list, String transactionKey, String transactionHash, String blockNo,
                                      String md5) {
        return this.transactionDao.batchUpdateTransaction(list, transactionKey, transactionHash, blockNo, md5);
    }

    public List<EcTransaction> selectListTransactionMds5ByUserDappIds(String userDappIds, Date date) {
        return transactionDao.selectListTransactionMds5ByUserDappIds(userDappIds, date);
    }

    public List<EcTransaction> selectListTransactionByUserDappId(String userDappId) {
        return transactionDao.selectListTransactionByUserDappId(userDappId);
    }

    public List<EcTransaction> selectListTransactionsNotSingleUploadDappId(String userAppIds, Long dappId, Date date) {
        return transactionDao.selectListTransactionsNotSingleUploadDappId(userAppIds, dappId, date);
    }

}
