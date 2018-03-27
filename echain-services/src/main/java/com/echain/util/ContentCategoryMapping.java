package com.echain.util;

import java.util.HashMap;
import java.util.Map;

public class ContentCategoryMapping {

	public static Map<String, String> monitorMap = new HashMap<>();
	//public static Map<String, String> comparisonMap = new HashMap<>();
	public static Map<String, String> commonMap = new HashMap<>();
	
	static{
		monitorMap.put("101", "video");
		monitorMap.put("109", "video");
		monitorMap.put("120", "video");
		monitorMap.put("199", "video");
		monitorMap.put("131", "video");
		monitorMap.put("201", "music");
		monitorMap.put("220", "music");
		monitorMap.put("299", "music");
		monitorMap.put("301", "littlesite");
		monitorMap.put("303", "sns");
		monitorMap.put("304", "wangpan");
		monitorMap.put("305", "se");
		monitorMap.put("306", "ecommerce");
		monitorMap.put("307", "appmarket");
		monitorMap.put("402", "anime");
		monitorMap.put("4", "ott");
		monitorMap.put("309", "yun");
		monitorMap.put("310", "download");
		
		monitorMap.put("308", "novel");
		monitorMap.put("311", "news");
	}
	
	static{
		commonMap.put("101", "video");
		commonMap.put("109", "video");
		commonMap.put("120", "video");
		commonMap.put("199", "video");
		commonMap.put("131", "video");
		commonMap.put("201", "music");
		commonMap.put("220", "music");
		commonMap.put("299", "music");
		commonMap.put("301", "littlesite");
		commonMap.put("303", "sns");
		commonMap.put("304", "wangpan");
		commonMap.put("305", "se");
		commonMap.put("306", "ecommerce");
		commonMap.put("307", "appmarket");
		commonMap.put("402", "anime");
		commonMap.put("4", "ott");
		
		commonMap.put("308", "novel");
		commonMap.put("311", "news");
	}
}
