package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcPoints;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcPointsDao extends MyMapper<EcPoints> {
    List<EcPoints> selectByUserIdAndAppId(@Param("userId") Long userId, @Param("dappId") Long dappId);
}