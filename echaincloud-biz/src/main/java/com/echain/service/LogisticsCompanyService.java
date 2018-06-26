package com.echain.service;

import com.echain.redis.Redis;
import com.echain.constant.RedisConstant;
import com.echain.dao.EcLogisticsCompanyDao;
import com.echain.entity.EcLogisticsCompany;
import com.echain.manager.RedisHashMag;
import com.echain.manager.RedisKeyMag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsCompanyService {

    @Autowired
    EcLogisticsCompanyDao logisticsCompanyDao;

    @Autowired
    RedisHashMag redisHashMag;

    @Autowired
    RedisKeyMag redisKeyMag;

    public List<EcLogisticsCompany> selectAll() {
        List<EcLogisticsCompany> o =
                redisHashMag.getArrayValue(RedisConstant.LOGISTICSCOMPANY, "all", EcLogisticsCompany.class);
        if (o != null) {
            return o;
        }

        List<EcLogisticsCompany> result = logisticsCompanyDao.selectAll();
        redisHashMag.setValue(RedisConstant.LOGISTICSCOMPANY, "all", result);

        return result;
    }

    public EcLogisticsCompany selectByCompanyName(String logisticsCompanyName) {
        String key = Redis.mergeKey(RedisConstant.LOGISTICSCOMPANY, "logisticsCompanyName", logisticsCompanyName);
        EcLogisticsCompany o = redisKeyMag.getValue(key, EcLogisticsCompany.class);
        if (o != null) {
            return o;
        }

        EcLogisticsCompany logisticsCompany = logisticsCompanyDao.selectByCompanyname(logisticsCompanyName);
        redisKeyMag.setValue(key, logisticsCompany);

        return logisticsCompany;
    }

    public void insertSelective(EcLogisticsCompany logisticsCompany) {
        logisticsCompanyDao.insertSelective(logisticsCompany);
        redisHashMag.delKey(RedisConstant.LOGISTICSCOMPANY);
    }
}
