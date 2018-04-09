package com.echain.dao;

import com.echain.entity.EcLogisticsRecord;

public class EcLogisticsRecordDao extends BaseDao<EcLogisticsRecord> {

    private final String className = "com.echain.dao.EcLogisticsRecordDao.";

    @Override
    public String getClassName() {
        return className;
    }
}