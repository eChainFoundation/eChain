package com.echain.dao;

import org.springframework.stereotype.Repository;

import com.echain.entity.EcUserWallet;

@Repository
public class EcUserWalletDao extends BaseDao<EcUserWallet> {

    private final String className = "com.echain.dao.EcUserWalletDao.";

    @Override
    public String getClassName() {
        return className;
    }
}