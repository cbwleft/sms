package com.cbwleft.sms.exception;

import com.cbwleft.sms.constant.BaseResultEnum;

public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 5195069121136547205L;

	private BaseResultEnum baseResultEnum;
	
	private String message;

	public BaseException(BaseResultEnum baseResultEnum) {
		this.baseResultEnum = baseResultEnum;
	}

	public BaseException(BaseResultEnum baseResultEnum, String message) {
		this.baseResultEnum = baseResultEnum;
		this.message = message;
	}

	public BaseResultEnum getBaseResultEnum() {
		return baseResultEnum;
	}

	public String getMessage() {
		return message;
	}

}
