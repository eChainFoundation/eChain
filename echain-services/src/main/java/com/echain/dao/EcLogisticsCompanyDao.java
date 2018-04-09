package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcLogisticsCompany;

@Repository
public class EcLogisticsCompanyDao extends BaseDao<EcLogisticsCompany> {

    private final String className = "com.echain.dao.EcLogisticsCompanyDao.";

    @Override
    public String getClassName() {
        return className;
    }
}