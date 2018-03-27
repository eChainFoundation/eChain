package com.echain.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanUtil implements ApplicationContextAware {

	/**
	 * 以静态变量保存ApplicationContext,可在任意代码中取出ApplicaitonContext.
	 */
	private static ApplicationContext context;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	
	@Override
	public void setApplicationContext(ApplicationContext context) {
		SpringBeanUtil.context = context;
	}

	public synchronized static ApplicationContext getApplicationContext() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
			
		}
		return context;
	}
	
 

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(String name) {
		T bean = (T) getApplicationContext().getBean(name); 
		return bean;
	}
	
	public static <T> T getBean(Class<T> className){
		return (T)(getApplicationContext().getAutowireCapableBeanFactory().getBean(className));
	}
	
}
