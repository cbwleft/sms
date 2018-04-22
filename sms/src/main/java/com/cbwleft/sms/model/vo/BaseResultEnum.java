package com.cbwleft.sms.model.vo;

public enum BaseResultEnum {

	SUCCESS(true, 1), 
	FAIL(false, 0),
	TEMPLATE_NOT_EXIST(false, -1000),
	NOT_VALIDATE_CODE_TEMPLATE(false, -1001),
	APP_NOT_EXIST(false, -1100),
	ILLEGAL_CHANNEL_PARAMS(false, -1110),
	SERVER_ERROR(false, -9999);

	boolean success;
	int code;

	private BaseResultEnum(boolean success, int code) {
		this.success = success;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
