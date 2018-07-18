package com.echain.entity;

public class EcOrder extends BaseEntity {

	private String orderId;

	private String describeMd5;

	private String transactionKey;

	private String transactionHash;

	private String blockNo;

	private String qrcodePath;

	private Integer flag;

	private String describeText;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getDescribeMd5() {
		return describeMd5;
	}

	public void setDescribeMd5(String describeMd5) {
		this.describeMd5 = describeMd5 == null ? null : describeMd5.trim();
	}

	public String getTransactionKey() {
		return transactionKey;
	}

	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey == null ? null : transactionKey.trim();
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash == null ? null : transactionHash.trim();
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo == null ? null : blockNo.trim();
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getDescribeText() {
		return describeText;
	}

	public void setDescribeText(String describeText) {
		this.describeText = describeText == null ? null : describeText.trim();
	}

	public EcOrder() {
	}

	public EcOrder(String orderId, String describeMd5, String describeText) {
		this.orderId = orderId;
		this.describeMd5 = describeMd5;
		this.describeText = describeText;
	}
}