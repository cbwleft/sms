package com.cbwleft.sms.model.vo;

import com.cbwleft.sms.constant.BaseResultEnum;

public class BaseException extends RuntimeException{

	private static final long serialVersionUID = 5195069121136547205L;

	private BaseResultEnum baseResultEnum;
	
	private String msg;
	
	public BaseException(BaseResultEnum baseResultEnum, String msg) {
		this.baseResultEnum = baseResultEnum;
		this.msg = msg;
	}

	public BaseException(BaseResultEnum baseResultEnum) {
		this.baseResultEnum = baseResultEnum;
	}

	public BaseResultEnum getBaseResultEnum() {
		return baseResultEnum;
	}

	public void setBaseResultEnum(BaseResultEnum baseResultEnum) {
		this.baseResultEnum = baseResultEnum;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
