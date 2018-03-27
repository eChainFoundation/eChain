package com.echain.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StorePathUtil {
	
	
	public static String getStorePath(Long userId,String categoryAlias, String exportType){
		
		String storePath = ServiceConstant.EXCEL_PATH + userId + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator 
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + File.separator + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ File.separator + categoryAlias + "（"+exportType+"）" + new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xlsx";
		
		return storePath; 
	}
	
	
	public static String getReportPath(Long userId, Long worksId){
		String storePath = ServiceConstant.EXCEL_PATH + userId + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator 
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + File.separator + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ File.separator + worksId + new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xlsx";
		return storePath;
	}
	
	public static String getReportPathByWorksName(Long userId, String worksName){
		String storePath = ServiceConstant.EXCEL_PATH + userId + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator 
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + File.separator + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ File.separator + worksName +" "+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xlsx";
		return storePath;
	}
	public static String getReportPathByWorksName(Long userId, String worksName,Date date){
		String storePath = ServiceConstant.EXCEL_PATH + userId + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator 
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + File.separator + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ File.separator + worksName +" "+ new SimpleDateFormat("yyyy-MM-dd").format(date) + ".xlsx";
		return storePath;
	}
	public static String getReportPathByWorksNameEn(Long userId, String worksName,Date date){
		String storePath = ServiceConstant.EXCEL_PATH + userId + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator 
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + File.separator + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ File.separator +"en"+File.separator+ worksName +" "+ new SimpleDateFormat("yyyy-MM-dd").format(date) + ".xlsx";
		return storePath;
	}
	
	
	
	public static void main(String[] args) {
		String ss = getReportPathByWorksName(2L, "我的危险妻子");
		System.out.println(ss);
	}
	
	
}
