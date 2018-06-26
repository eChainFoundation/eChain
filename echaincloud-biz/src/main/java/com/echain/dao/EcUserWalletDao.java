package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcUserWallet;
import org.apache.ibatis.annotations.Param;

public interface EcUserWalletDao extends MyMapper<EcUserWallet> {

    EcUserWallet selectByUserId(@Param("userId") Long userId);
}