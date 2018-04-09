package com.echain.dao;

import com.echain.entity.EcPointsPool;

public class EcPointsPoolDao extends BaseDao<EcPointsPool> {

    private final String className = "com.echain.dao.EcPointsPoolDao.";

    @Override
    public String getClassName() {
        return className;
    }
}