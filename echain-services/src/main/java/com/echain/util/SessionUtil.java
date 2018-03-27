package com.echain.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class SessionUtil {
	
	public static final String LOGIN_DIRECT_URL_SESSION_NAME = "adminloginDirectUrl";
		
//	public static void loginSuccess(FbmUserBase user, FbmUserInfo userinfo, String falg, HttpServletRequest request){ 
//		HttpSession session = request.getSession(); 
//		session.setAttribute("adminuserid", user.getId());
//		session.setAttribute("adminusername", user.getUsername());
//		session.setAttribute("falg", falg);
////		session.setAttribute("headImg", userinfo.getAvatar());
//	} 
	

//	public static void setUser(FbmUserBase user, HttpServletRequest request){ 
//		
//		HttpSession session = request.getSession(); 
//		session.setAttribute("adminuserid", user.getId());
//		session.setAttribute("adminusergroupname", user.getFbmUserInfo().getFbmUserGroup().getGroupName());
//		session.setAttribute("adminusername", user.getUsername());
//	} 
	
	/**
	 * @author: yang_huan
	 * @date:2016.10.09
	 * @Description: 把我的（任务数、作品数、客户数）放入session
	 */
	public static void setSomeUserInfoCount(int jobCount, int worksCount, int customerCount, HttpServletRequest request){ 
		HttpSession sessionAdmin = request.getSession();
		sessionAdmin.setAttribute("jobCount", jobCount);
		sessionAdmin.setAttribute("worksCount", worksCount);
		sessionAdmin.setAttribute("customerCount", customerCount);
	}
	/**
	 * @author: yang_huan
	 * @date:2016.10.09
	 * @Description: 从session中取出     任务数
	 */
	public static int getJobCount(HttpServletRequest request){
		HttpSession sessionAdmin = request.getSession();
		int jobCount = (int)sessionAdmin.getAttribute("jobCount");
		return jobCount;
	}
	/**
	 * @author: yang_huan
	 * @date:2016.10.09
	 * @Description: 从session中取出     作品数
	 */
	public static int getWorksCount(HttpServletRequest request){
		HttpSession sessionAdmin = request.getSession();
		int worksCount = (int)sessionAdmin.getAttribute("worksCount");
		return worksCount;
	}
	/**
	 * @author: yang_huan
	 * @date:2016.10.09
	 * @Description: 从session中取出     客户数
	 */
	public static int getCustomerCount(HttpServletRequest request){
		HttpSession sessionAdmin = request.getSession();
		int customerCount = (int)sessionAdmin.getAttribute("customerCount");
		return customerCount;
	}
	
	
	public static void setPhoto(String photo ,HttpServletRequest request){ 
		HttpSession session = request.getSession(); 
		session.setAttribute("headphoto", photo);
	} 
	
	public static void setFlag(String flag ,HttpServletRequest request){ 
		HttpSession session = request.getSession(); 
		session.setAttribute("flag", flag);
	} 
	
	public static void setCustomerStatus(String status ,HttpServletRequest request){ 
		HttpSession session = request.getSession(); 
		session.setAttribute("status", status);
	} 
	
	public static void setCustomerType(String customType ,HttpServletRequest request){ 
		HttpSession session = request.getSession(); 
		session.setAttribute("customtype", customType);
	} 
	
	/**
	 * @author: yang_huan
	 * @date:2016.3.30
	 * @Description: 是否是平台管理员 平台管理员权限
	 */
	public static boolean isTerraceAdmin(HttpServletRequest request){
		Long adminId = 1L;
		if((SessionUtil.getUserId(request)!=null && adminId.equals(SessionUtil.getUserId(request))) 
				|| "admin".equals(SessionUtil.getUserName(request))){
			 return true;
		}
		return false;
	}

	public static void logoutSuccess(HttpServletRequest request){ 
		HttpSession session = request.getSession(); 
		Enumeration<String> enums =  session.getAttributeNames();
		while(enums.hasMoreElements()){
			String name = enums.nextElement();
			session.removeAttribute(name);
		}
	}
	
	/**
	 * session中   存入数据管理真实数据量
	 * @param counts
	 * @param request
	 */
	public static void setMonitoringCount(Integer counts ,HttpServletRequest request){ 
		HttpSession sessionAdmin = request.getSession(); 
		sessionAdmin.setAttribute("counts", counts);
	} 
	
	/**
	 * session中   取出数据管理真实数据量
	 */
	public static Integer getMonitoringCount(HttpServletRequest request){
		HttpSession sessionAdmin = request.getSession();
		Integer counts = (Integer)sessionAdmin.getAttribute("counts");
		return counts;
	}
	
	public static void setAfterLoginRedirect(String requestUrl, HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		session.setAttribute(LOGIN_DIRECT_URL_SESSION_NAME, requestUrl);
	}
	
	public static String getAfterLoginRedirect(HttpServletRequest request) {
		return getValue(LOGIN_DIRECT_URL_SESSION_NAME, request);
	}
	
	public static Long getUserId(HttpServletRequest request) {
		String sUserId = getValue("adminuserid", request);
		
		String cookieValue = CookieUtil.getCookieValueByName("cookieValue");
		if(!StringUtil.isEmpty(cookieValue)) {
//			FbmUserBase user =(FbmUserBase) RedisUtilII.get(cookieValue);
//			if(user!=null)
//				return user.getId();
		}
		
		if(sUserId != null)
			return Long.parseLong(sUserId);
		return 0l;
	}
	public static Long getUserId() {
		String sUserId = getValue("adminuserid", getRequest());
		
		String cookieValue = CookieUtil.getCookieValueByName("cookieValue");
		if(!StringUtil.isEmpty(cookieValue)) {
//			FbmUserBase user =(FbmUserBase) RedisUtilII.get(cookieValue);
//			if(user!=null)
//				return user.getId();
		}
		
		if(sUserId != null)
			return Long.parseLong(sUserId);
		return 0l;
	}
    public static String getLocalIp() {
    	HttpServletRequest request=getRequest();
    	
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        
        return ip;  
    }
	
	
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		return request;
	}
	
	public static String getUserGroupName() {
		String userGroupName = getValue("adminusergroupname", getRequest());
		
		String cookieValue = CookieUtil.getCookieValueByName("cookieValue");
		if(!StringUtil.isEmpty(cookieValue)) {
//			FbmUserBase user =(FbmUserBase) RedisUtilII.get(cookieValue);
//			if(user!=null)
//				return user.getFbmUserInfo().getFbmUserGroup().getGroupName();
		}
		
		if(userGroupName != null)
			return userGroupName;
		return "";
	}
	public static String getUserName(HttpServletRequest request) {
		String sUserName = getValue("adminusername", request);
		String cookieValue = CookieUtil.getCookieValueByName("cookieValue");
		if(!StringUtil.isEmpty(cookieValue)) {
//			FbmUserBase user =(FbmUserBase) RedisUtilII.get(cookieValue);
//			if(user!=null)
//				return user.getUsername();
		}
		if(sUserName != null)
			return sUserName;
		return "";
	}
	
	public static String getFalg(HttpServletRequest request) {
		String falg = getValue("falg", request);
		if(falg != null)
			return falg;
		return "0";
	}
	
	public static String getHeadImg(HttpServletRequest request) {
		String sHeadImg = getValue("headImg", request);
		if(sHeadImg != null)
			return sHeadImg;
		return "";
	}
	
	public static String getValue(String key, HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		Object o = session.getAttribute(key);
		
		if (o == null || "".equals(o))
			return null;
		else
			return String.valueOf(o);
	}
}
