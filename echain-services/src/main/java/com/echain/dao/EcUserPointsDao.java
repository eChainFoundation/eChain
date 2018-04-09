package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcUserPoints;

@Repository
public class EcUserPointsDao extends BaseDao<EcUserPoints> {

    private final String className = "com.echain.dao.EcUserPointsDao.";

    @Override
    public String getClassName() {
        return className;
    }
}