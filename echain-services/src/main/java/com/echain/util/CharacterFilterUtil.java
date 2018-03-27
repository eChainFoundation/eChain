package com.echain.util;

/**
 * 把字符串中的 ：    英文星号( * )、中英文问号( ? )、中英文冒号( : )、英文正反斜杠(/\)、英文方括号( [] ) 和英文单引号（ ' ）   
 * 替换  成下划线（  _ ）,  并截取替换后的字符串前30个字符
 * @author yanghuan
 *
 */
public class CharacterFilterUtil {
	
	public static final String BE_REPLACED_EN_1 = "*";
	
	public static final String BE_REPLACED_EN_2 = "?";
	
	public static final String BE_REPLACED_CN_2 = "？";
	
	public static final String BE_REPLACED_EN_3 = "/";
	
	public static final String BE_REPLACED_EN_4 = "\\";
	
	public static final String BE_REPLACED_EN_5 = "[";
	
	public static final String BE_REPLACED_EN_6 = "]";
	
	public static final String BE_REPLACED_EN_7 = "'";
	
	public static final String REPLACE_EN_UNDERLINE = "_";
	
	public static final int STR_MAX_LENGTH = 30;
	
	
	public static final String replaceStr(String str) {
		
		if(str==null || "".equals(str)) return "_";
		
		str = str.replace(BE_REPLACED_EN_1, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_2, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_CN_2, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_3, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_4, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_5, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_6, REPLACE_EN_UNDERLINE);
		str = str.replace(BE_REPLACED_EN_7, REPLACE_EN_UNDERLINE);
		
		if(str.length() > STR_MAX_LENGTH){
			str = str.substring(0, STR_MAX_LENGTH);
		}
		
		return str;
		
	}
	
	
	public static void main(String[] args) {
		
		String str = replaceStr("\\我*s?aq啊？阿里s/爱的，卡死啊啊[卡机]啊两面w三刀'qw'");
		System.out.println(str);
	}
	
}
