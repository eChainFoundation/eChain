package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcLogisticsRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcLogisticsRecordDao extends MyMapper<EcLogisticsRecord> {

    Long selectMinIdNokey();

    List<EcLogisticsRecord> selectLogisticsRecords(@Param("minId") Long minId, @Param("upDataSize") Integer upDataSize);

    List<EcLogisticsRecord> selectByCompanyIdAndNo(@Param("logisticsCompanyId") Long logisticsCompanyId,
                                                   @Param("logisticsNo") String logisticsNo);
}