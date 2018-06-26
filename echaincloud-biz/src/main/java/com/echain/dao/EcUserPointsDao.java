package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcUserPoints;
import org.apache.ibatis.annotations.Param;

public interface EcUserPointsDao extends MyMapper<EcUserPoints> {

    EcUserPoints selectByUserId(@Param("userId") Long userId);
}