package com.echain.util;


public class ServiceConstant {
 
	
	
	/** 上传文件的最大大小*/
	public static final Long HEAD_IMG_MAX_SIZE=20*1024*1024L;
	
	public static final Integer DEFAULT_PAGE_SIZE = 20;
	
	public static final String 	YES ="1";
	
	public static final String 	NO ="0";
	
	public static final String DEFAULT_LIST_IMG = "/resources/zounongjia/images/logo.png";
 
	/** 奖品类型,1为物品*/
	public static final String 	LOTTERY_PRIZE_TYPE_PRODUCT ="1";
	
	public static final String 	EXCEL_PATH = "";
	
	/** 奖品类型,2为微信红包*/
	public static final String 	LOTTERY_PRIZE_TYPE_HONGBAO ="2";
	
	
	/** 允许抽取次数,就一次*/
	public static final String 	LOTTERY_TYPE_ONLY_ONCE ="0";
	
	/** 允许抽取次数,一天多次*/
	public static final String 	LOTTERY_TYPE_TIMES_PER_DAY ="1";
	
	/** 允许抽取次数,一共多少次*/
	public static final String 	LOTTERY_TYPE_TIMES_TOTAL ="2";
	
	/** 奖品状态 未兑换*/
	public static final String 	LOTTERY_PRIZE_STATUS_UNCONSUME="1";
	
	/** 奖品状态 已兑换*/
	public static final String 	LOTTERY_PRIZE_STATUS_CONSUME="2";
	
	


	/** 头像的前缀地址*/
	public static final String UPLOAD_PIC_PATH =  "/opt/tomcat/tomcat-platform/webapps/fbimonitor-platform/";// ResourceConfig.getInstance().getUploadPicPath(); 
	
	/** 每日签到的积分数*/
	public static final Integer CHECK_PER_DAY_JIFEN=5;
	/**默认用户头像 */
	public static final String DEFAULT_HEAD_IMG="/static/app/images/user_face.png";
	
	public static final String NO_CHECK_VERIFY_CODE="347861";
	
	public static final String APPID = "wx5ee178af526421f2";

	/**
	 * 微信全网测试账号
	 */
	public final static String COMPONENT_APPID = "wx5b4cb9b5d0990d0f";  
	public final static String COMPONENT_APPSECRET = "3481b6f59e121fdd5a9092dae010ea20";  
	
	//test
//	public final static String COMPONENT_APPID = "wxf6a60a619bc82bab";  
//	public final static String COMPONENT_APPSECRET = "dbf9a08fd7fe195e06035a06e98a3ee8";  
	
	public final static String COMPONENT_ENCODINGAESKEY = "x8xNCaFoVxd4d4uF2NVDs8XNcyQVfFu2UqgmNxX8GnK";
	public final static String COMPONENT_TOKEN = "yzhCourse";
	
	//权限子模块id
	public final static String submoduleIds = "96,97,98,99,100,101,102,103,104,106";
 
	public final static String 	WEIXIN_TEXT_LINE_SEPARATOR	="\n";
	
	public static final String qrcodePath = "/opt/tomcat7/webapps/monitoring/static/qrcode";
	public static final String qrcodeUrl = "http://182.254.146.118/monitoring/JrjWeb/findUser?userId=";
	
	public static final String default_user_photo = "static/img/userphoto.jpg";
	
	/** 上传侵权截图的具体路径 */
	public static final String UPLOAD_TORT_PICTURE_PATH_PREFIX = UPLOAD_PIC_PATH+"static/upload/video_tort_url_picture";
	
}
