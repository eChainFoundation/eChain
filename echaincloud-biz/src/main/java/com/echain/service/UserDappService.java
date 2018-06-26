package com.echain.service;

import com.echain.redis.Redis;
import com.echain.constant.RedisConstant;
import com.echain.dao.EcUserDappDao;
import com.echain.entity.EcUserDapp;
import com.echain.manager.RedisHashMag;
import com.echain.manager.RedisKeyMag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDappService {

    @Autowired
    EcUserDappDao ecUserDappDao;

    @Autowired
    RedisKeyMag redisKeyMag;

    @Autowired
    RedisHashMag redisHashMag;

    public List<EcUserDapp> getBySingleUpload() {
        return ecUserDappDao.getBySingleUpload();
    }

    public List<EcUserDapp> getByNotSingleUpload() {
        return ecUserDappDao.getByNotSingleUpload();
    }

    public EcUserDapp selectByUserIdAndDappId(Long userId, Long dappId) {
        String key = Redis.mergeKey(RedisConstant.USERDAPP, "userId#dappId", Long.toString(userId), Long.toString(dappId));
        EcUserDapp o = redisKeyMag.getValue(key, EcUserDapp.class);
        if (o != null) {
            return o;
        }

        EcUserDapp result = ecUserDappDao.selectByUserIdAndDappId(userId, dappId);
        redisKeyMag.setValue(key, result);

        return result;
    }

    public void updateByPrimaryKeySelective(EcUserDapp ecUserDapp) {
        ecUserDappDao.updateByPrimaryKeySelective(ecUserDapp);

        String key = Redis.mergeKey(RedisConstant.USERDAPP, "userId#dappId", Long.toString(ecUserDapp.getUserId()),
                Long.toString(ecUserDapp.getDappId()));
        redisKeyMag.delKey(key);
    }

    public void insertSelective(EcUserDapp ecUserDapp) {
        ecUserDappDao.insertSelective(ecUserDapp);

        String field = Redis.mergeKey(RedisConstant.USERDAPP, "userId", Long.toString(ecUserDapp.getUserId()));
        redisHashMag.delField(RedisConstant.USERDAPP, field);
    }

    public List<EcUserDapp> selectByUserId(Long userId) {
        String field = Redis.mergeKey(RedisConstant.USERDAPP, "userId", Long.toString(userId));
        List<EcUserDapp> o = redisHashMag.getArrayValue(RedisConstant.USERDAPP, field, EcUserDapp.class);
        if (o != null) {
            return o;
        }

        List<EcUserDapp> result = ecUserDappDao.selectByUserId(userId);
        redisHashMag.setValue(RedisConstant.USERDAPP, field, result);
        return result;
    }

}
