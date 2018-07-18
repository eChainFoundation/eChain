package com.echain.service;

import com.echain.constant.RedisConstant;
import com.echain.dao.EcUserDappDao;
import com.echain.entity.EcUserDapp;
import com.echain.manager.RedisHashMag;
import com.echain.manager.RedisKeyMag;
import com.echain.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserDappService {

    @Autowired
    EcUserDappDao ecUserDappDao;

    @Autowired
    RedisKeyMag redisKeyMag;

    @Autowired
    RedisHashMag redisHashMag;

    public List<EcUserDapp> getBySingleUpload(String frequency) {
        return ecUserDappDao.getBySingleUpload(frequency);
    }

    public List<EcUserDapp> getByNotSingleUpload(String frequency) {
        return ecUserDappDao.getByNotSingleUpload(frequency);
    }

    public EcUserDapp selectByUserIdAndDappId(Long userId, Long dappId) {
        EcUserDapp result = ecUserDappDao.selectByUserIdAndDappId(userId, dappId);

        if(result==null){
            EcUserDapp ecUserDapp = new EcUserDapp();
            ecUserDapp.setUserId(userId);
            ecUserDapp.setDappId(dappId);
            ecUserDapp.setConsumePoints(0L);
            ecUserDapp.setGetPoints(0L);
            ecUserDapp.setCreateTime(new Date());
            ecUserDapp.setIsUploadSingle("1");
            ecUserDappDao.insert(ecUserDapp);
        }

        return result;
    }

    public void updateByPrimaryKeySelective(EcUserDapp ecUserDapp) {
        ecUserDappDao.updateByPrimaryKeySelective(ecUserDapp);

        String key = Redis.mergeKey(RedisConstant.USERDAPP, "userId#dappId", Long.toString(ecUserDapp.getUserId()),
                Long.toString(ecUserDapp.getDappId()));
        redisKeyMag.delKey(key);
    }

    public int updatePoint(Long userId, Long dappId, Long consumePoints, Long oldConsumePoints, Long getPoints, Long oldGetPoints) {
        int res = ecUserDappDao.updatePoint(userId, dappId, consumePoints, oldConsumePoints, getPoints, oldGetPoints);

        String key = Redis.mergeKey(RedisConstant.USERDAPP, "userId#dappId", Long.toString(userId), Long.toString(dappId));
        redisKeyMag.delKey(key);

        return res;
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
