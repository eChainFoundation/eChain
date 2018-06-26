package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcLogisticsCompany;
import org.apache.ibatis.annotations.Param;

public interface EcLogisticsCompanyDao extends MyMapper<EcLogisticsCompany> {

    EcLogisticsCompany selectByCompanyname(@Param("logisticsCompanyName") String logisticsCompanyName);
}