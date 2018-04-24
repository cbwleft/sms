package com.cbwleft.sms.model.dto;

public class SendMessageResult {
	
	private boolean success;
	
	private String validateCode;
	
	private String failCode;
	
	private String bizId;

	public SendMessageResult(boolean success, String validateCode, String bizId) {
		this.success = success;
		this.validateCode = validateCode;
		this.bizId = bizId;
	}
	
	public SendMessageResult(String validateCode, String failCode, String bizId) {
		this.success = false;
		this.validateCode = validateCode;
		this.failCode = failCode;
		this.bizId = bizId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
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
