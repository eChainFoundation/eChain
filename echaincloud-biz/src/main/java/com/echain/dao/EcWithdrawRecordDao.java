package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcWithdrawRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcWithdrawRecordDao extends MyMapper<EcWithdrawRecord> {
    int save(EcWithdrawRecord record);

    List<EcWithdrawRecord> getListByUser(@Param("userId") Long userId, @Param("type") Integer type);
}