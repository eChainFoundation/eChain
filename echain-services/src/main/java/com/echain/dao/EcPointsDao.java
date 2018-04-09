package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcPoints;

@Repository
public class EcPointsDao extends BaseDao<EcPoints> {

    private final String className = "com.echain.dao.EcPointsDao.";

    @Override
    public String getClassName() {
        return className;
    }
}