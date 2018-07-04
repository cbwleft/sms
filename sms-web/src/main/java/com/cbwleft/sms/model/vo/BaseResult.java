package com.cbwleft.sms.model.vo;

import com.cbwleft.sms.constant.BaseResultEnum;
import com.cbwleft.sms.exception.BaseException;

public class BaseResult {

	private BaseResultEnum baseResultEnum;

	private String msg;

	public BaseResult(BaseResultEnum baseResultEnum, String msg) {
		this.baseResultEnum = baseResultEnum;
		this.msg = msg;
	}

	public BaseResult(BaseException e) {
		this.baseResultEnum = e.getBaseResultEnum();
		this.msg = e.getMessage();
	}

	public String getMsg() {
		return msg == null ? baseResultEnum.name() : msg;
	}

	public boolean isSuccess() {
		return baseResultEnum.isSuccess();
	}

	public int getCode() {
		return baseResultEnum.getCode();
	}

}
