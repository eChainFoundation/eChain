package com.echain.util;

import org.apache.commons.lang.StringUtils;

public class UserUtil {
	
//	public static String getUserName(UserBase user){
//		/*if(user!=null){if(StringUtils.isNotEmpty(user.getUserRealName())){
//			return user.getUserRealName();
//		}else */if(StringUtils.isNotEmpty(user.getUserName())){
//				return user.getUserName();
//		 }else if(StringUtils.isNotEmpty(user.getPhoneNumber())){
//				return  getHiddenPhoneNumber(user.getPhoneNumber()); 
//	 	}
//		
//		return "";
//		
//	}
//	
	
	public static String getHiddenPhoneNumber(String phoneNumber){
		if(StringUtils.isEmpty(phoneNumber)){
			return "";
		}else if(!StringUtil.isMobile(phoneNumber)){
			return "";
		}else{
			return  phoneNumber.substring(0,3)+ "**"+phoneNumber.substring(7);
		}
		
	
	}	
  
	 public static String processPassword(String password){
		   if(StringUtils.isEmpty(password)){//设置密码是原始密码为空
			   return password;
		   }
		   String md5Pwd = Md5Util.encode(password);
		   return md5Pwd;
	   }	
   
//   public static void main(String[] args) throws Exception{
//	   System.out.println(Md5Util.encode(RSAUtil.encryptString("123456")));
//   }
	

}
