package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcOrderDao extends MyMapper<EcOrder> {

    EcOrder getByOrderId(@Param("orderId") String orderId);

    List<EcOrder> getByFlag();
}
