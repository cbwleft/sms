package com.cbwleft.sms.dao.constant;

import java.text.MessageFormat;

public enum RedisKeys {

	BATCH_MESSAGE_SENDING("sms:batch_message:{0}:sending"),
	BATCH_MESSAGE_SUCCESS("sms:batch_message:{0}:success"),
	BATCH_MESSAGE_FAILURE("sms:batch_message:{0}:failure"),
	
	VALIDATE_CODE_MESSAGE("sms:message:{0}:{1}:latest"),
	VALIDATE_RETRY("sms:message:{0}:{1}:retry");
	
	private String pattern;

	RedisKeys(String pattern) {
		this.pattern = pattern;
	}

	public String format(Object... args) {
		return MessageFormat.format(pattern, args);
	}
}
