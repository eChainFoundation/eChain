package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcPointsPool;
import org.apache.ibatis.annotations.Param;

public interface EcPointsPoolDao extends MyMapper<EcPointsPool> {

    int updatePoint(@Param("id") Long id,
                    @Param("nowPoints") Long nowPoints, @Param("oldNowPoints") Long oldNowPoints,
                    @Param("consumePoints") Long consumePoints, @Param("oldConsumePoints") Long oldConsumePoints,
                    @Param("allPoints") Long allPoints, @Param("oldAllPoints") Long oldAllPoints);
}