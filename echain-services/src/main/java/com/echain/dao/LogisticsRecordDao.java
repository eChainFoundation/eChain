package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.LogisticsRecord;

@Repository
public class LogisticsRecordDao extends BaseDao<LogisticsRecord> {

    private final String className = "com.echain.dao.LogisticsRecordDao.";

    @Override
    public String getClassName() {
        return className;
    }
}