package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcDapp;

@Repository
public class EcDappDao extends BaseDao<EcDapp> {

    private final String className = "com.echain.dao.EcDappDao.";

    @Override
    public String getClassName() {
        return className;
    }
}