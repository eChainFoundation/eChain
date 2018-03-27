package com.echain.util;

import java.util.Properties;

public class SystemConfigImpl implements SystemConfig{

	private final Properties pro;
	
	public SystemConfigImpl(Properties properties)
	{
		this.pro = properties; 
	}
	@Override
	public int getInt(String key, int defaultValue) {
		Object obj = getProperty(pro, key, defaultValue);
		return Integer.parseInt(obj.toString());
	}

	@Override
	public String getString(String key, String defaultValue) {
		Object obj = getProperty(pro, key, defaultValue);
		return (String)obj;
	}

	@Override
	public double getDouble(String key, double defaultValue) {
		Object obj = getProperty(pro, key, defaultValue);
		return (Double)obj;
	}
	private Object getProperty(Properties props, String key,
	            Object defaultValue) {
	        if (props == null) {
	            return defaultValue;
	        }
	        Object strValue = props.getProperty(key);
	        if (strValue == null) {
	            return defaultValue;
	        }
	        return strValue;
	    }
	private Object getProperty(Properties props, String key) {
        if (props == null) {
            return null;
        }
        Object strValue = props.getProperty(key);
        if (strValue == null) {
            return null;
        }
        return strValue;
    }
	@Override
	public String getString(String key) {
		Object obj = getProperty(pro,key);
		if(obj == null)
		{
			return null;
		}
		else
		{
			return (String)obj;
		}
	}
	@Override
	public long getLong(String key,long defaultValue) {
		Object obj = getProperty(pro,key);
		if(obj == null)
		{
			return defaultValue;
		}
		else
		{
			return Long.parseLong(obj.toString());
		}
	}
	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		Object obj = getProperty(pro,key);
		if(obj == null)
		{
			return defaultValue;
		}
		else
		{
			return Boolean.valueOf(obj.toString());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStringInMemory(String key, String value) {
		pro.setProperty(key, value);
	}
}
