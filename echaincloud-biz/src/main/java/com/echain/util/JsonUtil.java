package com.echain.util;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static String convertToString(Map<String, Object> map) {
        return JSON.toJSONString(map);
    }

    public static String convertToString(List list) {
        return JSON.toJSONString(list);
    }


    /**
     * bean 转换为json 处理过时间类型了
     *
     * @param obj
     * @return
     */
    public static String convertBeanToJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * json 转换成 bean
     *
     * @param json
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertJsonToBean(String json, Class<T> beanClass) {
        return JSON.parseObject(json, beanClass);
    }
}
