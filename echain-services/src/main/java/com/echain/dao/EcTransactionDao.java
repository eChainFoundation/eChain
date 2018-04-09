package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcTransaction;

@Repository
public class EcTransactionDao extends BaseDao<EcTransaction> {

    private final String className = "com.echain.dao.EcTransactionDao.";

    @Override
    public String getClassName() {
        return className;
    }
}