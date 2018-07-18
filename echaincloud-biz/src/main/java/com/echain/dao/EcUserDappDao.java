package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcUserDapp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcUserDappDao extends MyMapper<EcUserDapp> {

    EcUserDapp selectByUserIdAndDappId(@Param("userId") Long userId, @Param("dappId") Long dappId);

    //List<EcUserDapp> selectListByUserIdAndDappId(@Param("userId") Long userId, @Param("dappId") Long dappId);

    List<EcUserDapp> selectByUserId(@Param("userId") Long userId);

    List<EcUserDapp> getBySingleUpload(@Param("frequency") String frequency);

    List<EcUserDapp> getByNotSingleUpload(@Param("frequency") String frequency);

    int updatePoint(@Param("userId") Long userId, @Param("dappId") Long dappId,
                    @Param("consumePoints") Long consumePoints, @Param("oldConsumePoints") Long oldConsumePoints,
                    @Param("getPoints") Long getPoints, @Param("oldGetPoints") Long oldGetPoints);
}