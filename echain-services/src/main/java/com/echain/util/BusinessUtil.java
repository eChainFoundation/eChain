package com.echain.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.echain.entity.BaseEntity;

public class BusinessUtil {
	
	/**
	 * 组合一个date
	 * @param day 日期中的 年月日
	 * @param time 日期中的 时分秒
	 * @return
	 */
	public static  Date unionDate(Date day,Date time){
		String formatDateStr = DateUtil.formatDate(day, "yyyy-MM-dd")+" "+ DateUtil.formatDate(time, "HH:mm:ss"); 
		Date rsDate =  DateUtil.parseDate(formatDateStr, "yyyy-MM-dd HH:mm:ss");
		return rsDate;
	}
	
	
	@SuppressWarnings("rawtypes") 
	public static String unionString(Map map ){
		StringBuilder builder = new StringBuilder(); 
		for(Object key: map.keySet()){
			String value = map.get(key).toString();
			builder.append("<"+key+">"+value+"</"+key+">"+FileUtil.LINE_SEPARATOR);
			builder.append(""+FileUtil.LINE_SEPARATOR);
		}
		return builder.toString();
	
	}
	
 
	
	public static String display(Object obj){
		if(obj == null){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getFirstNotNull(T... objs){
		
		if(objs == null){
			return null;
		}
		
		for(T t : objs){
			if(t!=null){
				return t;
			}
		}
		
		return null;
	}
	 
	public static <T extends BaseEntity> Map<Long ,T> convertListToMap(List<T> eleList){
		
		 Map<Long ,T> map = new HashMap<Long,T>();
		 
		 if(eleList == null || eleList.isEmpty()){
			 return map;
		 }
		 for(T t : eleList){
			 map.put(t.getId(), t);
		 }
		 
		return map;
	}
	
	
	
	
	public static String display(Object obj,Integer maxLength){
		if(obj == null){
			return "";
		}else{
			String str =  obj.toString();
			if(str.length() > maxLength){
				return str.substring(0, maxLength)+"...";
			}else{
				return str;
			}
		}
	}
	
	public static String display(Object obj,String defaultValue){
		if(obj == null || StringUtils.isEmpty(obj.toString())){
			return defaultValue;
		}else{
			return obj.toString();
		}
	}
	
	public static String getLimitFileSizeMessage(){
		return  "文件太大，不能超过"+ ServiceConstant.HEAD_IMG_MAX_SIZE/(1024*1024)+"M";
	}
	
	public static Double parseDouble(Object obj,Double defaultValue){
		if(obj == null){
			return defaultValue;
		} 
		try{
			return Double.parseDouble(obj.toString());
		}catch(Throwable e){
			return defaultValue;
		}
	}
	
	
	public static Long parseLong(Object obj){
		if(obj == null){
			return null;
		}
		
		try{
			return Long.parseLong(obj.toString());
		}catch(Throwable e){
			return null;
		}
	}
	
	public static Integer parseInt(Object obj){
		if(obj == null){
			return null;
		}
		
		try{
			return Integer.parseInt(obj.toString());
		}catch(Throwable e){
			return null;
		}
	}
	
	public static String getFileSizeMessage(){
		return   ServiceConstant.HEAD_IMG_MAX_SIZE/(1024*1024)+"M";
	}
	
	 /**
	  * 系统自动补零	 
	  * @param value 数字
	  * @param number 显示的字符串的长度
	  * @return
	  */
  public static String formatNumberWithZero(Object value , Integer number){
	   return String.format("%0"+number+"d",value);
  }	

}
