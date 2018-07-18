package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcWalletRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcWalletRecordDao extends MyMapper<EcWalletRecord> {

    int save(EcWalletRecord ecWalletRecord);

    List<EcWalletRecord> getRecordByType(@Param("userId") Long userId, @Param("type") Integer type);

    EcWalletRecord getById(@Param("id") Long id);
}
