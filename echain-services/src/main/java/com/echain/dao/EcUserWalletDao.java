package com.echain.dao;

import com.echain.entity.EcUserWallet;

public class EcUserWalletDao extends BaseDao<EcUserWallet> {

    private final String className = "com.echain.dao.EcUserWalletDao.";

    @Override
    public String getClassName() {
        return className;
    }
}