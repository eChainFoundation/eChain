package com.echain.util;

import java.util.Random;

/**
 * 获取随机数 
 * @author Administrator
 *
 */
public class RandomCountUtil {
	
	/**
	 * 产生1000000到2000000之间的随机数
	 * @return
	 */
	public static int getCount(){
		
		Random rand = new Random();  
		int count = rand.nextInt(20000) + 1000;
		return count; 
	}
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) { 
		System.out.println(getCount());
	}

}
