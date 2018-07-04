package com.cbwleft.sms.constant;

/**
 * 接口错误码
 * @author cbwleft
 *
 */
public enum BaseResultEnum {

	SUCCESS(true, 1), 
	FAIL(false, 0),
	ILLEGAL_ARGUMENT(false, -1),
	TEMPLATE_NOT_EXIST(false, -1000),
	NOT_VALIDATE_CODE_TEMPLATE(false, -1001),
	VALIDATE_CODE_EXPIRE_ILLEGAL(false, -1002),
	APP_NOT_EXIST(false, -1100),
	SEND_TOO_FREQUENTLY(false, -2000),
	SERVER_ERROR(false, -9999);

	boolean success;
	int code;

	BaseResultEnum(boolean success, int code) {
		this.success = success;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getCode() {
		return code;
	}

}
