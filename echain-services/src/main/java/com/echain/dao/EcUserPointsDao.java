package com.echain.dao;

import com.echain.entity.EcUserPoints;

public class EcUserPointsDao extends BaseDao<EcUserPoints> {

    private final String className = "com.echain.dao.EcUserPointsDao.";

    @Override
    public String getClassName() {
        return className;
    }
}