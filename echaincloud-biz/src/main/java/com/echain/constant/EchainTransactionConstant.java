package com.echain.constant;

public class EchainTransactionConstant {
	
	public static final Integer UP_DATA_SIZE = 10000;

//	交易状态，0-未审核，1-审核通过，2-审核不通过
	public static enum TransactionStatus{
		WEISHENHE("0", "未完成", 0),		            // 未审核
		PASS("1", "创建成功", 1),		            // 审核通过
		UNPASS("2", "提交审核", 2);			            // 审核不通过
	
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
		
		private TransactionStatus(String status, String desc, int index) {
			this.status = status;
			this.desc = desc;
			this.index = index;
		}	
	} 
}
