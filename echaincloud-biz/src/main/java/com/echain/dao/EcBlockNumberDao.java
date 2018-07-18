package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcBlockNumber;
import org.apache.ibatis.annotations.Param;

public interface EcBlockNumberDao extends MyMapper<EcBlockNumber> {
    int save(EcBlockNumber blockNumber);

    Long getNumberByWalletAndType(@Param("wallet") String wallet, @Param("type") Integer type);

    int updateNumberByType(@Param("wallet") String wallet, @Param("number") Long number, @Param("type") Integer type);
}