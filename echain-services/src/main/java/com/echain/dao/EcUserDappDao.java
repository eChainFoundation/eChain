package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcUserDapp;

@Repository
public class EcUserDappDao extends BaseDao<EcUserDapp> {

    private final String className = "com.echain.dao.EcUserDappDao.";

    @Override
    public String getClassName() {
        return className;
    }
}