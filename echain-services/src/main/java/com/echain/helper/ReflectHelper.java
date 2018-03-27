package com.echain.helper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: Page
 * @Description: TODO(分页模板)
 * @author wkq
 * @date 2016年9月1日 下午4:03:25
 */

public class ReflectHelper {
    /**
     * 获取obj对象fieldName的Field
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
            try{
                return superClass.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e){
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * 
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if(field != null){
            if(field.isAccessible()){
                value = field.get(obj);
            }else{
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * 
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if(field.isAccessible()){
            field.set(obj, value);
        }else{
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    public static Object getNewInstance(String clazz, String keyword) throws ClassNotFoundException, NoSuchMethodException,
            SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> c = Class.forName(clazz);
        Constructor<?> con = c.getConstructor(String.class);
        return con.newInstance(keyword);
    }

    public static Object getNewInstance(String clazz, String enumClass, String enumName, String keyword) throws ClassNotFoundException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> c;
        Constructor<?> con;
        Class<?> class1;

        class1 = Class.forName(clazz + "$" + enumClass);
        c = Class.forName(clazz);
        Object invokeTester = con = c.getConstructor(String.class, class1);
        // 获取类的valueOf()方法
        Method getStrMethod = class1.getMethod("valueOf", new Class[] {String.class });
        // 调用对象的valueOf()方法
        Object result = getStrMethod.invoke(invokeTester, new Object[] {enumName });
        return con.newInstance(keyword, result);

    }

    public static Object getNewInstance(String clazz) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> c = Class.forName(clazz);
        return c.newInstance();
    }

    /**
     * 将一条记录转成一个对象
     * 
     * @param cls 泛型类型
     * @param rs ResultSet对象
     * @return 泛型类型对象
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public static <T> T executeResultSet(Class<T> cls, ResultSet rs) throws InstantiationException, IllegalAccessException, SQLException {
        T obj = cls.newInstance();
        ResultSetMetaData rsm = rs.getMetaData();
        int columnCount = rsm.getColumnCount();
        // Field[] fields = cls.getFields();
        Field [] fields = cls.getDeclaredFields();
        for (Field field : fields){
            String fieldName = field.getName();
            for (int j = 1; j <= columnCount; j++){
                String columnName = rsm.getColumnLabel(j);
                if(fieldName.equalsIgnoreCase(columnName.replace("_", ""))){
                    Object value = rs.getObject(j);
                    field.setAccessible(true);
                    field.set(obj, value);
                    break;
                }
            }
        }
        return obj;
    }

    /**
     * @param t
     * @param name
     * @return
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> getMapByAttribute(T t, String name) throws IllegalArgumentException, IllegalAccessException {
        Field [] fields = t.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        Map<Integer, Integer> colorIndex = null;
        Map<Integer, Integer> colorKeywordsIndex = null;
        for (Field field : fields){
            String fieldName = field.getName();
            if(StringUtils.equals(fieldName, "colorIndex")){
                field.setAccessible(true);
                colorIndex = (Map<Integer, Integer>) field.get(t);
            }
            if(StringUtils.equals(fieldName, "colorKeywordsIndex")){
                field.setAccessible(true);
                colorKeywordsIndex = (Map<Integer, Integer>) field.get(t);
            }
        }
        for (Field field : fields){
            String fieldName = field.getName();
            if(fieldName.equalsIgnoreCase(name.replace("_", ""))){
                field.setAccessible(true);
                map.put("value", field.get(t));
                map.put("type", field.getType().getSimpleName());
                if(StringUtils.equals(name, "title")){
                    map.put("color", colorIndex);
                    map.put("colorKeywordsIndex", colorKeywordsIndex);
                }
                break;
            }

        }
        return map;
    }

    public static <T> Integer getColumnWidthByAttribute(T t, String name) throws IllegalArgumentException, IllegalAccessException {
        Field [] fields = t.getClass().getDeclaredFields();
        int columnWidth = 0;
        for (Field field : fields){
            String fieldName = field.getName();
            if(fieldName.equalsIgnoreCase(name.replace("_", ""))){
                field.setAccessible(true);
                break;
            }
        }
        return columnWidth;
    }

    public static <T> T executeResultSet(Class<T> cls, Map<String, Object> map) throws InstantiationException, IllegalAccessException {
        T obj = cls.newInstance();
        Field [] fields = cls.getDeclaredFields();
        for (Field field : fields){
            try{
                String fieldName = field.getName();
                String fieldType = field.getType().getSimpleName();
                if(map.containsKey(fieldName)){
                    Object value = map.get(fieldName);
                    if(value == null) continue;
                    field.setAccessible(true);
                    if("String".equals(fieldType)){
                        field.set(obj, value);
                    }else if("Integer".equals(fieldType) || "int".equals(fieldType)){
                        field.set(obj, Integer.parseInt(String.valueOf(value)));
                    }else if("Long".equalsIgnoreCase(fieldType)){
                        field.set(obj, Long.parseLong(String.valueOf(value)));
                    }else if("Double".equalsIgnoreCase(fieldType)){
                        field.set(obj, Double.parseDouble(String.valueOf(value)));
                    }else if("Boolean".equalsIgnoreCase(fieldType)){
                        field.set(obj, Boolean.parseBoolean(String.valueOf(value)));
                    }else{
                        field.set(obj, value);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static Map<String, Object> introspect(Object obj, boolean isFilterNullValue) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()){
            Method reader = pd.getReadMethod();
            if(reader != null){
                if((isFilterNullValue && null == reader.invoke(obj)) || pd.getName().equals("class")) continue;
                result.put(pd.getName(), reader.invoke(obj));
            }
        }
        return result;
    }

    /**
     * 利用反射实现对象之间属性复制
     * 
     * @param from
     * @param to
     */
    public static void copyProperties(Object from, Object to) throws Exception {
        copyPropertiesExclude(from, to, null);
    }

    /**
     * 复制对象属性
     * 
     * @param from
     * @param to
     * @param excludsArray 排除属性列表
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void copyPropertiesExclude(Object from, Object to, String [] excludsArray) throws Exception {
        List<String> excludesList = null;
        if(excludsArray != null && excludsArray.length > 0){
            excludesList = Arrays.asList(excludsArray); // 构造列表对象
        }
        Method [] fromMethods = from.getClass().getDeclaredMethods();
        Method [] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++){
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if(!fromMethodName.contains("get")) continue;
            // 排除列表检测
            if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())){
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if(toMethod == null) continue;
            Object value = fromMethod.invoke(from, new Object[0]);
            if(value == null) continue;
            // 集合类判空处理
            if(value instanceof Collection){
                Collection newValue = (Collection) value;
                if(newValue.size() <= 0) continue;
            }
            toMethod.invoke(to, new Object[] {value });
        }
    }

    /**
     * 利用反射实现对象之间属性复制
     * 
     * @param from
     * @param to
     */
    public static void copyProperties1(Object from, Object to) throws Exception {
        copyPropertiesExclude1(from, to, null);
    }

    /**
     * 复制对象属性
     * 
     * @param from
     * @param to
     * @param excludsArray 排除属性列表
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void copyPropertiesExclude1(Object from, Object to, String [] excludsArray) throws Exception {
        List<String> excludesList = null;
        if(excludsArray != null && excludsArray.length > 0){
            excludesList = Arrays.asList(excludsArray); // 构造列表对象
        }
        Method [] fromMethods = from.getClass().getDeclaredMethods();
        Method [] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++){
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if(!fromMethodName.contains("get")) continue;
            // 排除列表检测
            if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())){
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if(toMethod == null) continue;
            Object value = fromMethod.invoke(from, new Object[0]);
            Object tovalue = fromMethod.invoke(to, new Object[0]);
            if(value == null) continue;

            if(value != null && tovalue != null) continue;

            // 集合类判空处理
            if(value instanceof Collection){
                Collection newValue = (Collection) value;
                if(newValue.size() <= 0) continue;
            }
            toMethod.invoke(to, new Object[] {value });
        }
    }

    /**
     * 对象属性值复制，仅复制指定名称的属性值
     * 
     * @param from
     * @param to
     * @param includsArray
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void copyPropertiesInclude(Object from, Object to, String [] includsArray) throws Exception {
        List<String> includesList = null;
        if(includsArray != null && includsArray.length > 0){
            includesList = Arrays.asList(includsArray); // 构造列表对象
        }else{
            return;
        }
        Method [] fromMethods = from.getClass().getDeclaredMethods();
        Method [] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++){
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if(!fromMethodName.contains("get")) continue;
            // 排除列表检测
            String str = fromMethodName.substring(3);
            if(!includesList.contains(str.substring(0, 1).toLowerCase() + str.substring(1))){
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if(toMethod == null) continue;
            Object value = fromMethod.invoke(from, new Object[0]);
            if(value == null) continue;
            // 集合类判空处理
            if(value instanceof Collection){
                Collection newValue = (Collection) value;
                if(newValue.size() <= 0) continue;
            }
            toMethod.invoke(to, new Object[] {value });
        }
    }

    /**
     * 从方法数组中获取指定名称的方法
     * 
     * @param methods
     * @param name
     * @return
     */
    public static Method findMethodByName(Method [] methods, String name) {
        for (int j = 0; j < methods.length; j++){
            if(methods[j].getName().equals(name)) return methods[j];
        }
        return null;
    }

    public static void main(String [] args) throws Exception {
        // FbmDataExport fromfb = new FbmDataExport();
        // fromfb.setAuthor("sdfjs");
        // FbmDataExport toFb = new FbmDataExport();
        // toFb.setBookId("3333");
        // copyProperties(fromfb, toFb);
        // System.out.println(fromfb.getBookId());
        // System.out.println(toFb.getAuthor());

//        FbmTortDataNovel novel = new FbmTortDataNovel();
//        novel.setId(100L);
//        novel.setCreateTime(new Date());
//
//        Method method = novel.getClass().getSuperclass().getMethod("getId");
//        Method method2 = novel.getClass().getMethod("getCreateTime");
//        // String invoke2 =(String) method2.invoke(novel);
//        // System.out.println(invoke2);
//        String invoke = (String) method.invoke(novel);
//        System.out.println(invoke);
    }

    /**
     * 将属性存入某一对象中
     * 
     * @author ZakuStrike
     * @param methods
     * @param name
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object setProperty(Object o, Map<String, Object> map) throws Exception {
        Method [] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods){
            String name = method.getName();
            if(name.contains("setJobId")){
                method.invoke(o, map.get("jobId"));
            }else if(name.contains("setSiteId")){
                method.invoke(o, map.get("siteId"));
            }else if(name.contains("setDataId")){
                method.invoke(o, map.get("dataId"));
            }else if(name.contains("setWorksId")){
                method.invoke(o, map.get("workId"));
            }else if(name.contains("setUrl")){
                method.invoke(o, map.get("url"));
            }else if(name.contains("setUrlMd5")){
                method.invoke(o, map.get("urlmd5"));
            }else if(name.contains("setDataCreateTime")){
                method.invoke(o, map.get("dataCreateTime"));
            }else if(name.contains("setDataUpdateTime")){
                method.invoke(o, map.get("dataUpdateTime"));
            }else if(name.contains("setDataOfflineTime")){
                method.invoke(o, map.get("dataOfflineTime"));
            }else if(name.contains("setTitle")){
                method.invoke(o, map.get("title"));
            }else if(name.contains("setDataReleaseTime")){
                method.invoke(o, map.get("dataReleaseTime"));
            }else if(name.contains("setSimilarity")){
                method.invoke(o, map.get("similarity"));
            }else if(name.contains("setCreateTime")){
                method.invoke(o, map.get("createTime"));
            }else if(name.contains("setExtInfo")){
                method.invoke(o, map.get("extInfo"));
            }

        }

        return o;
    }
}