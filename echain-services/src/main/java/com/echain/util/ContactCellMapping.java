package com.echain.util;

import java.util.HashMap;
import java.util.Map;

public class ContactCellMapping {

	private static Map<String, Integer> textMap =  new HashMap<>();
	private static Map<String, Integer> resolveMap =  new HashMap<>();
	
	public static Map<String, Integer> getTextMap(){
		textMap.put("域名", -1);
		resolveMap.put("平台名称", -1);
		textMap.put("电子邮箱", -1);

		return textMap;
	}
	public static Map<String, Integer> getResolveMap(){
		resolveMap.put("域名", -1);
		resolveMap.put("平台名称", -1);
		resolveMap.put("公司主体", -1);
		resolveMap.put("详细地址", -1);
		resolveMap.put("所在地区", -1);
		resolveMap.put("邮编", -1);
		resolveMap.put("姓名", -1);
		resolveMap.put("职位", -1);
		resolveMap.put("电话", -1);
		resolveMap.put("手机", -1);
		resolveMap.put("默认发函", -1);
		resolveMap.put("电子邮箱", -1);
		resolveMap.put("公司简写", -1);
		
		return resolveMap;
	}
}
