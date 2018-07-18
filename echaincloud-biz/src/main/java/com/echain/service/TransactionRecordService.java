package com.echain.service;

import com.echain.common.util.CommonUtil;
import com.echain.dao.EcTransactionRecordDao;
import com.echain.entity.EcTransactionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionRecordService {
    @Autowired
    EcTransactionRecordDao recordDao;

    public void batchInsert(List<EcTransactionRecord> list) {
        recordDao.batchInsert(list);
    }

    public List<EcTransactionRecord> getRechargeRecord(String address, Integer type, String contractAddress) {
        List<EcTransactionRecord> list = recordDao.getRechargeRecord(address, type, contractAddress);
        return CommonUtil.isEmpty(list) ? new ArrayList<>() : list;
    }

    public int updateFlagById(Long id) {
        return recordDao.updateFlagById(id);
    }
}
