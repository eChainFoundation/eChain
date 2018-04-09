package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcUser;

@Repository
public class EcUserDao extends BaseDao<EcUser> {

    private final String className = "com.echain.dao.EcUserDao.";

    @Override
    public String getClassName() {
        return className;
    }
}