package com.echain.util;

 
import java.util.Date; 
public class VersionUtil {
	public static String getJsVersion(){ 
		return DateUtil.formatDate(new Date(), "MM-dd_HH:mm:ss.SSS");
		//return System.currentTimeMillis();
	}

}
