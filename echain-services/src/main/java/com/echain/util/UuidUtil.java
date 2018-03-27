package com.echain.util;

import java.util.UUID;

public class UuidUtil {
	
	public static String generateUuid(){
		return UUID.randomUUID().toString(); 
	}
	
	public static String generateOutTradeNo(){
		String value = generateUuid().replace("-", "").toLowerCase();
		return value;
	}
	
	public static String generateRandomNo(){
		String value = generateUuid().replace("-", "").toLowerCase();
		return value;
	}


}
