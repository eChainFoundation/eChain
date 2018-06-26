package com.echain.manager;

import com.alibaba.fastjson.JSON;
import com.echain.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Title: RedisHashMag
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @author: lijun
 * @date: 2017/9/29 10:37
 */
@Component
public class RedisKeyMag {

    @Autowired
    Redis redis;

    private int expire = 12;
    private TimeUnit timeUnit;
    protected String baseKey = "echain:";

    public RedisKeyMag() {
        this.timeUnit = TimeUnit.HOURS;
    }

    public <T> T getValue(Object key, Class<T> cls) {
        if (redis.EXISTS(baseKey + key.toString())) {
            Object result = redis.GET(baseKey + key.toString(), cls);
            T t = JSON.parseObject(JSON.toJSONString(result), cls);

            if (t == null || "".equals(t.toString())) {
                return null;
            }

            return t;
        }
        return null;
    }

    public <T> List<T> getArrayValue(Object key, Class<T> cls) {
        if (redis.EXISTS(baseKey + key.toString())) {
            Object result = redis.GET(baseKey + key.toString(), cls);
            List<T> t = JSON.parseArray(JSON.toJSONString(result), cls);

            if (t == null || "".equals(t.toString())) {
                return null;
            }

            return t;
        }
        return null;
    }

    public void delKey(Object key) {
        redis.DEL(baseKey + key.toString());
    }

    public void setValue(Object key, Object value) {
        setValue(key, value, expire, timeUnit);
    }

    public void setValue(Object key, Object value, int time) {
        setValue(key, value, time, timeUnit);
    }

    public void setValue(Object key, Object value, int time, TimeUnit unit) {
        if (value == null) {
            value = "";
        }
        redis.SETEX(baseKey + key.toString(), value, time, unit);
    }
}
