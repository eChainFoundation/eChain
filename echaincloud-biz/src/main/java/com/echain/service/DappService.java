package com.echain.service;

import com.echain.redis.Redis;
import com.echain.constant.RedisConstant;
import com.echain.dao.EcDappDao;
import com.echain.entity.EcDapp;
import com.echain.manager.RedisKeyMag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DappService {

    @Autowired
    private EcDappDao dappDao;

    @Autowired
    RedisKeyMag redisKeyMag;

    public EcDapp selectByPrimaryKey(Long id) {
        String key = Redis.mergeKey(RedisConstant.DAPP, "id", Long.toString(id));
        EcDapp o = redisKeyMag.getValue(key, EcDapp.class);
        if (o != null) {
            return o;
        }

        EcDapp result = dappDao.selectByPrimaryKey(id);
        redisKeyMag.setValue(key, result);

        return result;
    }

    public EcDapp selectByDappName(String dappName) {
        String key = Redis.mergeKey(RedisConstant.DAPP, "dappName", dappName);
        EcDapp o = redisKeyMag.getValue(key, EcDapp.class);
        if (o != null) {
            return o;
        }

        EcDapp result = dappDao.selectByDappName(dappName);
        redisKeyMag.setValue(key, result);

        return result;
    }

    public EcDapp insertSelective(EcDapp dapp) {
        dappDao.insertSelective(dapp);

        return dapp;
    }

}
