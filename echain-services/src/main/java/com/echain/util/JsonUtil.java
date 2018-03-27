package com.echain.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

@SuppressWarnings("rawtypes")
public class JsonUtil
{

    public static String convertToString(Map<String, Object> map)
    {

        JSONObject jsonObject = JSONObject.fromObject(map);
        return jsonObject.toString();
    }

    public static String convertToString(List list)
    {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJSON2Map(String jsonStr)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet())
        {
            Object v = json.get(k);
            String value = String.valueOf(v).trim();
            if (value.startsWith("[") && value.endsWith("]"))
            {
                try
                {
                    JSONArray array = JSONArray.fromObject(v);
                    // JSONArray.toList()
                    // 如果内层还是数组的话，继续解析
                    if (array instanceof JSONArray)
                    {
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

                        Iterator<JSONObject> it = ((JSONArray) array)
                                .iterator();
                        while (it.hasNext())
                        {
                            JSONObject json2 = it.next();
                            list.add(parseJSON2Map(json2.toString()));
                        }
                        map.put(k.toString(), list);
                    }
                }
                catch (Throwable e)
                {
                    map.put(k.toString(), v);
                }
            }
            else
            {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * bean 转换为json 处理过时间类型了
     * @param obj
     * @return
     */
    public static String convertBeanToJson(Object obj)
    {

        JsonConfig jf = new JsonConfig();

        jf.registerJsonValueProcessor(java.util.Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss.SSS"));
        JSONObject jsonObject = JSONObject.fromObject(obj, jf);
        return jsonObject.toString();
    }

    /**
     * bean 转换为json 处理过时间类型了
     * @param obj
     * @return
     */
    public static String convertBeanToJson(List obj)
    {

        JsonConfig jf = new JsonConfig();

        jf.registerJsonValueProcessor(java.util.Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss.SSS"));
        JSONArray jsonObject = JSONArray.fromObject(obj, jf);
        return jsonObject.toString();
    }

    private static class DateJsonValueProcessor implements JsonValueProcessor
    {

        private String format;

        public DateJsonValueProcessor(String format)
        {
            this.format = format;
        }

        @Override
        public Object processArrayValue(Object value, JsonConfig jsonConfig)
        {
            return null;
        }

        @Override
        public Object processObjectValue(String key, Object value,
                JsonConfig jsonConfig)
        {
            if (value == null)
            {
                return "";
            }
            if (value instanceof java.sql.Timestamp)
            {
                String str = new SimpleDateFormat(format)
                        .format((java.sql.Timestamp) value);
                return str;
            }
            if (value instanceof java.util.Date)
            {
                String str = new SimpleDateFormat(format)
                        .format((java.util.Date) value);
                return str;
            }

            return value.toString();
        }
    }

    /**
     * json 转换成 bean
     * @param json
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertJsonToBean(String json, Class<T> beanClass)
    {
        System.out.println(beanClass.getGenericSuperclass());
        T b = (T) JSONObject.toBean(JSONObject.fromObject(json), beanClass);
        return b;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {

        // String monitorUrl = String.valueOf(articleEleMap.get("monitorUrl"));
        // Object monitorEndTime = articleEleMap.get("monitorEndTime");
        // Date monitorEndDate = null;
        // if(monitorEndTime != null && !"".equals(monitorEndTime)) {
        // monitorEndDate = DateUtil.parseDate(String.valueOf(monitorEndTime),
        // "yyyy-MM-dd");
        // }
        // String publishTime =
        // String.valueOf(articleEleMap.get("publishTime"));
        // String authorId = String.valueOf(articleEleMap.get("authorId"));
        // String authorName = String.valueOf(articleEleMap.get("authorName"));
        // String whiteList = String.valueOf(articleEleMap.get("whiteList"));

//        String json = "{'articles':[{'monitorUrl':'http://www.google.com','monitorEndTime':'2016-10-09','publishTime':'2016-10-08','authorId':'testid','authorName':'testname','whiteList':{'whiteList':[{'id':'testwiteid1','name':'testwitename1'},{'license_platform_name':'testwiteid2','license_author':'testwitename2'}]}},"
//                + "{'monitorUrl':'http://www.baidu.com','monitorEndTime':'2016-11-09','publishTime':'2016-11-08','authorId':'testid2','authorName':'testname2','whiteList':{'whiteList':[{'id':'testwiteid3','name':'testwitename3'},{'license_platform_name':'testwiteid4','license_author':'testwitename4'}]}}]}";
//
//        String json1 = "{'whiteList':[{'id':'testwiteid1','name':'testwitename1'},{'license_platform_name':'testwiteid2','license_author':'testwitename2'}]}}";
//        Map list = JsonUtil.parseJSON2Map(json1);
//        String js = "{'articles':{'media_id':'5054735','article_url':'kuaibao.qq.com','infringer_url':'kuaibao.qq.com','rights_id':'1'},'end_time':'2017'}";
//
//        String jsdata = "{'msg_type': 101,'msg_id': '4671808179543527426','msg_data': {'media_id': '5054735','article_url': 'kuaibao.qq.com','infringer_url': 'kuaibao.qq.com','rights_id': '1'}}";
//        // String ata = "{'msg_type': 101,'msg_id':
//        // '4671808179543527426','msg_data': {'articles':{'media_id':
//        // '5054735','article_url': 'kuaibao.qq.com','infringer_url':
//        // 'kuaibao.qq.com','rights_id': '1'},'end_time':'2017'}}";
//        String ata = "{'msg_type': 101,'msg_id': '4671808179543527426','msg_data': {'media_id': '5054735','article_url': 'kuaibao.qq.com','infringer_url': 'kuaibao.qq.com','rights_id': '1'}}";
//        String jString = "{'media_id':'5054735','article_url':'kuaibao.qq.com','infringer_url':'kuaibao.qq.com','rights_id':'1'}";
//        String json2 = "{'code':'0','msg':'success','data': {'openid':'OPENID','validity':true } }";
//        Map map = JsonUtil.parseJSON2Map(json2);
//        String object = (String) map.get("code");
//        Map object_data = (Map) map.get("data");
//        Boolean validity = Boolean
//                .valueOf(String.valueOf(object_data.get("validi")));
//        System.out.println(object + "=====");
//        String object2 = String.valueOf(map.get("data"));
//        Map<String, Object> map2 = JsonUtil.parseJSON2Map(object2);
//        Object object3 = map2.get("validity");
//        
//        System.out.println(validity + "-------------");
    	
    	
    	int j = 123;
		List<Integer> li = new ArrayList<Integer>();
		for(int i = 0; i <= j; i++) {
			if((i > 0 && i % 10 == 0) || (i == j)) {
				System.out.println(i + " ====  222222   " + li.size());
				li.clear();
				li.add(i);
			} else {
				System.out.println(i + " ====  111111   ");
				li.add(i);
			}
		}

    }

}
