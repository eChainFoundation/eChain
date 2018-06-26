package com.echain.manager;

import com.echain.redis.Redis;
import com.echain.constant.RedisConstant;
import com.echain.dao.EcPointsDao;
import com.echain.entity.EcPoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PointMag {

    @Autowired
    EcPointsDao pointsDao;

    @Autowired
    RedisHashMag redisHashMag;

    public void insertSelective(EcPoints points) {
        pointsDao.insertSelective(points);
        String field = Redis.mergeKey("userId#appId", Long.toString(points.getUserId()),
                Long.toString(points.getDappId()));
        redisHashMag.delField(RedisConstant.POINTS, field);
    }

    public List<EcPoints> selectByUserIdAndAppId(Long userId, Long appId) {
        String field = Redis.mergeKey("userId#appId", Long.toString(userId), Long.toString(appId));
        List<EcPoints> o = redisHashMag.getArrayValue(RedisConstant.POINTS, field, EcPoints.class);
        if (o != null) {
            return o;
        }

        List<EcPoints> result = pointsDao.selectByUserIdAndAppId(userId, appId);
        redisHashMag.setValue(RedisConstant.POINTS, field, result);

        return result;
    }
}
