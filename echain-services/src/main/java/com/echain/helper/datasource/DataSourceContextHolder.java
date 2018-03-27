package com.echain.helper.datasource;

import com.echain.util.StringUtil;

public class DataSourceContextHolder {  
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
 
    public static void setDbType(String dbType) {  
           contextHolder.set(dbType);  
    }  
 
    public static String getDbType() {  
    	String dataSource = contextHolder.get();
        if (StringUtil.isEmpty(dataSource)) {
            return DataSourceType.DATA_SOURCE_1;
        }else {
            return dataSource;
        }
    }  
    
    public static void clearDbType() {  
           contextHolder.remove();  
    }  
}  

