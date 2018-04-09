package com.echain.dao;

import com.echain.entity.EcUserDapp;

public class EcUserDappDao extends BaseDao<EcUserDapp> {

    private final String className = "com.echain.dao.EcUserDappDao.";

    @Override
    public String getClassName() {
        return className;
    }
}