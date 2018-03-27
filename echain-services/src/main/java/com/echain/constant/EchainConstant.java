package com.echain.constant;

public class EchainConstant {
	
	public static final String EVIDENCE_FILE_HTTP = ":8080/guacd/"; 

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
