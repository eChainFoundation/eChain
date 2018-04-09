package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcLogisticsRecord;

@Repository
public class EcLogisticsRecordDao extends BaseDao<EcLogisticsRecord> {

    private final String className = "com.echain.dao.EcLogisticsRecordDao.";

    @Override
    public String getClassName() {
        return className;
    }
}