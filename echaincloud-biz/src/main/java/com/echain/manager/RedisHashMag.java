package com.echain.manager;

import com.alibaba.fastjson.JSON;
import com.echain.redis.Redis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
public class RedisHashMag {

    @Autowired
    protected Redis redis;

    protected int expire = 12;
    protected TimeUnit timeUnit;
    protected String baseKey = "echain:";

    public RedisHashMag() {
        this.timeUnit = TimeUnit.HOURS;
    }

    public <T> T getValue(Object key, Object field, Class<T> cls) {
        if (redis.EXISTS(baseKey + key.toString())) {
            Object result = redis.HGET(baseKey + key.toString(), field.toString(), cls);
            T t = JSON.parseObject(JSON.toJSONString(result), cls);

            if (t == null || "".equals(t.toString())) {
                return null;
            }

            return t;
        }
        return null;
    }

    public <T> List<T> getArrayValue(Object key, Object field, Class<T> cls) {
        if (redis.EXISTS(baseKey + key.toString())) {
            String result = redis.HGETArry(baseKey + key.toString(), field.toString());
            if (StringUtils.isBlank(result)) {
                return null;
            }
            List<T> t = JSON.parseArray(result, cls);
            if (CollectionUtils.isEmpty(t)) {
                return null;
            }
            return t;
        }
        return null;
    }

    public void delKey(Object key) {
        redis.DEL(baseKey + key.toString());
    }

    public void delField(Object key, Object... fields) {
        redis.HDEL(baseKey + key.toString(), fields);
    }

    public void setValue(Object key, Object field, Object value) {
        setValue(key, field, value, this.expire, this.timeUnit);
    }

    public void setValue(Object key, Object field, Object value, int expire) {
        setValue(key, field, value, expire, this.timeUnit);
    }

    public void setValue(Object key, Object field, Object value, int expire, TimeUnit timeUnit) {
        setVal(key, field, value);
        redis.EXPIRE(baseKey + key.toString(), expire, timeUnit);
    }

    public void setValue(Object key, Object field, Object value, Date date) {
        setVal(key, field, value);
        redis.EXPIREAT(baseKey + key.toString(), date);
    }

    private void setVal(Object key, Object field, Object value) {
        if (value == null) {
            value = "";
        }

        redis.HSET(baseKey + key.toString(), field.toString(), value);
    }
}
