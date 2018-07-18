package com.echain.constant;

import java.math.BigInteger;

public class EchainConstant {
	
	//平台用户手机号
	public static final String DEFALUT_USER_PHONE = "1";
	public static final BigInteger GAS_PRICE = BigInteger.valueOf(80000000000l);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(1000000);
	
	//loacl
//	public static final String ETHEREUM_RPC_IP = "http://101.132.124.123:8078/";
//	public static final String WALLET_FILE = "/Users/roc/Downloads/wallet/UTC--2018-03-27T07-57-53.477762418Z--cf8e15d01d5f932a6c880757854bcfd3fd4b90e5";
//	public static final String PASSWORD = "123456";
//	public static final String EPOINTS_ADDRESS = "0x39ed6181ed7b41f96a5995bf342d44acc8e2fe37";
//	public static final String SAVE_DATA_ADDRESS = "0xbad5ec35e643462d9125e242cf68888b8a800d1c";
	
	//product
//	public static final String ETHEREUM_RPC_IP = "http://47.100.36.250:8123/";

//	public static final String ETHEREUM_RPC_IP = "http://52.15.242.168:8123/";//	轻钱包
//	public static final String ETHEREUM_RPC_IP = "http://172.31.6.100:8123/";
//	public static final String WALLET_FILE = "/home/tomcat/walletfiles/UTC--2018-04-13T08-20-33.426Z--e1b7725ce3ef50f6d78e6202357bc80776e6dabe";
//	public static final String WALLET_FILE = "/Users/roc/Downloads/wallet/UTC--2018-04-13T08-20-33.426Z--e1b7725ce3ef50f6d78e6202357bc80776e6dabe";
//	public static final String PASSWORD = "wallet_2018@echain";
//	public static final String EPOINTS_ADDRESS = "0x08769a9b479a4b20e796194d960cc407fc66359a";
//	public static final String SAVE_DATA_ADDRESS = "0xf34cd2fd11233df8f90646ab658b03bfea98aa92";


	//test
//	public static final String ETHEREUM_RPC_IP = "http://101.132.124.123:8078/";
//	public static final String WALLET_FILE = "D:\\projects\\UTC--2018-06-21T06-42-48.524406003Z--2a0ac6022f3cf4ae45388420e3a428ea803f6308";
//	public static final String PASSWORD = "123456";
//	public static final String EPOINTS_ADDRESS = "0x5df64d0349679310d70d39a9cf3c9cd96867a653";

	//	0-保存，1-提交审核，2-审核中，3-审核通过，4-审核失败
	public static enum EvidenceStatus{
		WEIWANCHENG("0", "未完成", 0),		            // 创建
		WANCHENG("1", "创建成功", 1),		            // 创建
		TIJIAO("2", "提交审核", 2),			            // 提交审核
		SHENHEZHONG("3", "审核中", 3),                 // 审核中
		TONGGUO("4", "审核通过", 4),                     // 审核通过
		WEITONGGUO("5", "审核未通过", 5);                  // 审核未通过
	
		public String status;
		public String desc;
		public int index;
		
		public String getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}
		
		public int getIndex() {
			return index;
		}
		
		private EvidenceStatus(String status, String desc, int index) {
			this.status = status;
			this.desc = desc;
			this.index = index;
		}	
	}
	
	public static enum FileStatus{
		TUPIAN("1", "图片", 0),		            // 创建
		SHIPIN("2", "视频", 1),			            // 提交审核
		WENJIANJIA("3", "文件夹", 2);                 // 审核中
	
		public String status;
		public String desc;
		public int index;
		
		public String getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}
		
		public int getIndex() {
			return index;
		}
		
		private FileStatus(String status, String desc, int index) {
			this.status = status;
			this.desc = desc;
			this.index = index;
		}	
	}
	
//	1-创建， 2-选择人，3-上传照片，4-选择公证处，5-保存证据成功
	public static enum EvidenceStep{
		CHUANGJIAN("1", "创建证据", 1),		            // 创建
		XUANZECAOZUOREN("2", "选择操作人", 2),			            // 提交审核
		SHANGCHUANZHAOPIAN("3", "上传照片", 3),                 // 审核中
		XUANZEGONGZHENGCHU("4", "选择公证处", 4),                     // 审核通过
		BAOCUNCHENGGONG("5", "保存成功", 5);                  // 审核未通过
	
		public String status;
		public String desc;
		public int index;
		
		public String getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}
		
		public int getIndex() {
			return index;
		}
		
		private EvidenceStep(String status, String desc, int index) {
			this.status = status;
			this.desc = desc;
			this.index = index;
		}	
	}
	
	//远程文件夹名
	public static enum DirectoryName{
		SHOOT("shoot", "截屏", 0),		            // 创建
		VIDEO("video", "录像", 1);                 // 审核中
	
		public String status;
		public String desc;
		public int index;
		
		public String getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}
		
		public int getIndex() {
			return index;
		}
		
		private DirectoryName(String status, String desc, int index) {
			this.status = status;
			this.desc = desc;
			this.index = index;
		}	
	}
}
