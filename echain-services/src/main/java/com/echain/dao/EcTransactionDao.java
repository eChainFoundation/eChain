package com.echain.dao;

import com.echain.entity.EcTransaction;

public class EcTransactionDao extends BaseDao<EcTransaction> {

    private final String className = "com.echain.dao.EcTransactionDao.";

    @Override
    public String getClassName() {
        return className;
    }
}