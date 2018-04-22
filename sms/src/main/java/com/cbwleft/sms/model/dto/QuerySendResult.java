package com.cbwleft.sms.model.dto;

public class QuerySendResult {
	
	private boolean success;
	
	private byte sendStatus;
	
	private String content;
	
	public QuerySendResult(boolean success, byte sendStatus, String content) {
		this.success = success;
		this.sendStatus = sendStatus;
		this.content = content;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(byte sendStatus) {
		this.sendStatus = sendStatus;
	}
	
}
