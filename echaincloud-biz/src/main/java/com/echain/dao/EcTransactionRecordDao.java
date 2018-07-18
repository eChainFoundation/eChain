package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcTransactionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcTransactionRecordDao extends MyMapper<EcTransactionRecord> {

    void batchInsert(List<EcTransactionRecord> list);

    Long save(EcTransactionRecord record);

    int getCountByAddress(@Param("address") String address);

    List<EcTransactionRecord> getRechargeRecord(@Param("address") String address, @Param("type") Integer type,
            @Param("contractAddress") String contractAddress);

    int updateFlagById(@Param("id") Long id);
}