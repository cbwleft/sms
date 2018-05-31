package com.cbwleft.sms.model.dto;

import java.util.Set;

import com.cbwleft.sms.model.validation.AppId;
import com.cbwleft.sms.model.validation.Content;
import com.cbwleft.sms.model.validation.Mobile;

public class BatchMessageDTO {

	/**
	 * 手机号码
	 */
	private Set<@Mobile String> mobile;

	/**
	 * 应用id
	 */
	@AppId
	private String appId;

	/**
	 * 短信内容
	 */
	@Content
	private String content;

	public Set<String> getMobile() {
		return mobile;
	}

	public void setMobile(Set<String> mobile) {
		this.mobile = mobile;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
