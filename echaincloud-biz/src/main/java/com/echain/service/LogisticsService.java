package com.echain.service;

import com.echain.redis.Redis;
import com.echain.constant.RedisConstant;
import com.echain.dao.EcLogisticsRecordDao;
import com.echain.entity.EcLogisticsRecord;
import com.echain.manager.RedisHashMag;
import com.echain.manager.RedisKeyMag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogisticsService.class);

    @Autowired
    private EcLogisticsRecordDao recordDao;

    @Autowired
    RedisHashMag redisHashMag;

    @Autowired
    RedisKeyMag redisKeyMag;

    public EcLogisticsRecord saveLogistics(EcLogisticsRecord record) {
        try {
            recordDao.insertSelective(record);
            String filed = Redis.mergeKey("companyIdAndNo", Long.toString(record.getLogisticsCompanyId()),
                    record.getLogisticsNo());
            redisHashMag.delField(RedisConstant.LOGISTICSRECORD, filed);
            return record;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

        return null;
    }

    public List<EcLogisticsRecord> selectByCompanyIdAndNo(Long logisticsCompanyId, String logisticsNo) {
        String filed = Redis.mergeKey("companyIdAndNo", Long.toString(logisticsCompanyId), logisticsNo);
        List<EcLogisticsRecord> o = redisHashMag.getArrayValue(RedisConstant.LOGISTICSRECORD, filed, EcLogisticsRecord.class);
        if (o != null) {
            return o;
        }

        List<EcLogisticsRecord> result = recordDao.selectByCompanyIdAndNo(logisticsCompanyId, logisticsNo);
        redisHashMag.setValue(RedisConstant.LOGISTICSRECORD, filed, result);

        return result;
    }

    public EcLogisticsRecord selectLogisticById(Long logisticsId) {
        String key = Redis.mergeKey(RedisConstant.LOGISTICSRECORD, Long.toString(logisticsId));
        EcLogisticsRecord o = redisKeyMag.getValue(key, EcLogisticsRecord.class);
        if (o != null) {
            return o;
        }

        EcLogisticsRecord result = recordDao.selectByPrimaryKey(logisticsId);
        redisKeyMag.setValue(key, result);

        return result;
    }

}
