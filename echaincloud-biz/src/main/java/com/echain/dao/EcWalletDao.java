package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcWallet;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EcWalletDao extends MyMapper<EcWallet> {

    Long save(@Param("wallet") String wallet, @Param("password") String password, @Param("type") Integer type);

    List<EcWallet> selectByWithoutFile();

    EcWallet selectByTypeWithoutUser(@Param("type") Integer type);

    int updateUserId(EcWallet wallet);

    int updateValue(@Param("userId") Long userId, @Param("type") Integer type, @Param("value") BigDecimal value,
                    @Param("oldValue") BigDecimal oldValue);

    int updateImg(@Param("wallet") String wallet, @Param("type") Integer type, @Param("qimgFile") String qimgFile);

    List<EcWallet> selectByUserId(@Param("userId") Long userId);

    EcWallet selectByUserIdType(@Param("userId") Long userId, @Param("type") Integer type);

    List<EcWallet> getByUsed();
}