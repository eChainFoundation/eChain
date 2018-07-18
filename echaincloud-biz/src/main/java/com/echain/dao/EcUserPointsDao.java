package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcUserPoints;
import org.apache.ibatis.annotations.Param;

public interface EcUserPointsDao extends MyMapper<EcUserPoints> {

    EcUserPoints selectByUserId(@Param("userId") Long userId);

    int updatePointByUserId(@Param("userId") Long userId, @Param("allPoints") Long allPoints,
                            @Param("nowPoints") Long nowPoints, @Param("consumePoints") Long consumePoints,
                            @Param("oldPoints") Long oldPoints);
}