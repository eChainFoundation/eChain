package com.echain.util;


/**
 * 应用配置管理接口
 * 
 * @author jingruiguyue
 * 
 */
public interface SystemConfig {

	public int getInt(String key,int defaultValue);
	public String getString(String key,String defaultValue);
	public double getDouble(String key,double defaultValue);
	public String getString(String key);
	public long getLong(String key,long defaultValue);
	public boolean getBoolean(String key,boolean defaultValue);
	
	/**
	 * 将值保存在在内存中
	 * @param key
	 * @param value
	 */
	public void setStringInMemory(String key,String value);
}
