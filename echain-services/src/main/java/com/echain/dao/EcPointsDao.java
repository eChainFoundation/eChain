package com.echain.dao;

import com.echain.entity.EcPoints;

public class EcPointsDao extends BaseDao<EcPoints> {

    private final String className = "com.echain.dao.EcPointsDao.";

    @Override
    public String getClassName() {
        return className;
    }
}