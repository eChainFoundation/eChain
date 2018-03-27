package com.echain.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ParamterUtil {
	
	/**
	 * 为url增加params
	 * @param url
	 * @param paramMap
	 * @return
	 */
	
	public static String appendParams(String url , Map<String,String> paramMap){
		if(StringUtils.isBlank(url) || (paramMap== null || paramMap.isEmpty())){
			return url;
		}
		
		StringBuilder builder = new StringBuilder();
		
		Iterator<String> keyIter = paramMap.keySet().iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			String value = paramMap.get(key);
			String keyShow= key+"=";
			if(url.indexOf(keyShow)==-1 && builder.indexOf(keyShow)==-1){
				builder.append(key+"="+value);
				if(keyIter.hasNext()){
					builder.append("&");
				}
			} 
		}
		if(StringUtils.isNotBlank(builder.toString())){
			if(url.indexOf("?")!=-1){
				return url+"&"+builder.toString();
			}else{
				return url+"?"+builder.toString();
			}
		}else{
			return url;
		} 
		
	}

	private static String truncateUrlPage(String strURL) {

		String strAllParam = null;
		String[] arrSplit = null; 
		if (strURL != null) {
			strURL = strURL.trim(); 
			arrSplit = strURL.split("[?]");
			if (strURL.length() > 1) {
				if (arrSplit.length > 1) {
					if (arrSplit[1] != null) {
						strAllParam = arrSplit[1];
					}
				}
			} 
		}

		return strAllParam;
	}
	
	public static String getPureUrlPage(String strURL) { 
 
		if (strURL != null) {
			strURL = strURL.trim(); 
			String[] arrSplit = strURL.split("[?]");
			if (arrSplit.length > 1) {
				strURL = arrSplit[0];
			} 
		}  
		return strURL;
	}
	/**
	 * 去掉带#号的url
	 * @param strURL
	 * @return
	 */
	public static String getSharpUrlPage(String url){
		if (url != null) {
			int idx = url.indexOf("#");
			if(idx > 0){
				url = url.substring(0, idx);
			}
		}
		return url;
	}
	
	
	/**
	 * 只保留传入的param
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static String getParameUrlPage(String url,String... params ) { 
	   String processingUrl = getSharpUrlPage(url); 
	   String pureUrl =  getPureUrlPage(processingUrl); 
	   Map<String, String> map =  getParamMap(processingUrl);
	   pureUrl += "?";
	   int loop = 0;
	   for(String paramKey : params){
		   String value = map.get(paramKey);
		   if(StringUtils.isNotEmpty(value)){
			   loop++;
			   if(loop > 1){
				   pureUrl +="&";  
			   }
			   pureUrl +=paramKey+"=" +value;  
		   }
		  
	   }
	   if(pureUrl.endsWith("?")){
		   pureUrl = pureUrl.substring(0, pureUrl.length()-1);
	   }
	   return pureUrl;
	}
	/**
	 * 去掉没用的parmas
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static String getExcludeParameUrlPage(String url,String... params ) { 
		   String processingUrl = getSharpUrlPage(url);
		   String pureUrl =  getPureUrlPage(processingUrl); 
		   Map<String, String> map =  getParamMap(processingUrl);
		   pureUrl += "?";
		   int loop = 0;
		   Set<String> keySet = map.keySet();
		   for(String key : keySet){
			   String value = map.get(key);
			   for(String param:params){
				   if(keySet.contains(param)){ 
					   continue;
			        }
			   }
			  
			   loop++;
			   if(loop > 1){
				   pureUrl +="&";  
			   }
			   pureUrl +=key+"=" +value;  
			  
			  
		   }
		   if(pureUrl.endsWith("?")){
			   pureUrl = pureUrl.substring(0, pureUrl.length()-1);
		   }
		   return pureUrl;
	 }
	
	/**
	 * 让param 有序的排列,不在参数里的 全部排在后面
	 * @param strURL
	 * @param params
	 * @return
	 */
	public  static String getParameOrderedUrlPage(String url,String... params ) {  
	 
	       String processingUrl = getSharpUrlPage(url);
		
		   String pureUrl =  getPureUrlPage(processingUrl); 
		   Map<String, String> map =  getParamMap(processingUrl);
		   pureUrl += "?";
		   int loop = 0;
		   if(params.length > 0){
			   for(String param:params){
				   if(map.containsKey(param)){ 
					   String value = map.get(param);
					   loop++;
					   if(loop > 1){
						   pureUrl +="&";  
					   }
					   pureUrl +=param+"=" +value;   
					   map.remove(param);
				   }   
			   } 
		   }else{
			   return url;
		   } 
		   
		   for(String key:map.keySet()){
			   String value = map.get(key);
			   loop++;
			   if(loop > 1){
				   pureUrl +="&";  
			   }
			   pureUrl +=key+"=" +value;   
			
		   }
	 
		   if(pureUrl.endsWith("?")){
			   pureUrl = pureUrl.substring(0, pureUrl.length()-1);
		   } 
		 
		   return pureUrl;
	 }
	
	/**
	 * 判断是否存在所有传入的参数
	 * @param url
	 * @param params
	 * @return
	 */
	public static boolean containsParams(String url ,String... params){
		boolean contains = true;
	    Map<String, String> map =  getParamMap(url);
	    for(String param:params){
		   if(map.containsKey(param)){ 
			   
		   }else{
			   contains = false;
		   }
	    }
	    
	    return contains;
	}
	
	public static String getParamMap(String url,String param) {
		 Map<String, String>  map = getParamMap(url);
		 if(map != null){
			 return map.get(param);
		 }
		 return null;
	}
	public static Map<String, String> getParamMap(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组 www.2cto.com
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
		     int idx = strSplit.indexOf("=");
		  
			// 解析出键值
			if (idx > 0) {
				String key = strSplit.substring(0,idx);
				String value = strSplit.substring(idx+1,strSplit.length());
				// 正确解析
				mapRequest.put(key, value); 

			} else { 
					mapRequest.put(strSplit, ""); 
			}
		}
		return mapRequest;
	}
	/**
	 * 得到url 全部host
	 * 比如 http://www.firstbrave.com/index.html 得到 http://www.firstbrave.com
	 * www.firstbrave.com/index.html 得到 www.firstbrave.com
	 * @param url
	 * @return
	 */
	public static String getUrlWholeHost(String url){
		if(StringUtils.isNotBlank(url)){
		   int idx = -1 ;
		   if(url.toLowerCase().startsWith("http")){
			   idx = url.indexOf("//"); 
		   }
		  
		   int secondIdx =  url.indexOf("/", idx+2);
		   if(secondIdx > -1){
			   return url.substring(0, secondIdx);
		   }else{
			   return url;
		   }
		} 
		return "";
	}
	
	/**
	 * 有些url 已/ 打头  需要补充其他信息
	 * @return
	 */
	public static String convert2WholeUrl(String parentUrl,String selfUrl){
		if(StringUtils.isNotBlank(selfUrl)){
			if(selfUrl.startsWith("/")){
				return getUrlWholeHost(parentUrl)+selfUrl;
			}
		}
		return selfUrl;
	}

	public static void main(String[] args) {
     String url = "http://www.firstbrave.com/index.html";
	 System.out.println(ParamterUtil.convert2WholeUrl(url,"/index2.html"));
	}

}
