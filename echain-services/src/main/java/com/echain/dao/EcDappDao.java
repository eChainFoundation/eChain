package com.echain.dao;

import com.echain.entity.EcDapp;

public class EcDappDao extends BaseDao<EcDapp> {

    private final String className = "com.echain.dao.EcDappDao.";

    @Override
    public String getClassName() {
        return className;
    }
}