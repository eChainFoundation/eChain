package com.echain.dao;

import com.echain.entity.EcUser;

public class EcUserDao extends BaseDao<EcUser> {

    private final String className = "com.echain.dao.EcUserDao.";

    @Override
    public String getClassName() {
        return className;
    }
}