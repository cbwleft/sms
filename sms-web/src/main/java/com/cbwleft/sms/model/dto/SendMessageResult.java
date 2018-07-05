package com.cbwleft.sms.model.dto;

public class SendMessageResult {
	
	private boolean success;
	
	private String failCode;
	
	private String bizId;

	public SendMessageResult(boolean success, String bizId) {
		this.success = success;
		this.bizId = bizId;
	}
	
	public SendMessageResult(String failCode) {
		this.success = false;
		this.failCode = failCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailCode() {
		return failCode;
	}

	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	

}
