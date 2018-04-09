package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcPointsPool;

@Repository
public class EcPointsPoolDao extends BaseDao<EcPointsPool> {

    private final String className = "com.echain.dao.EcPointsPoolDao.";

    @Override
    public String getClassName() {
        return className;
    }
}