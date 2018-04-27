package com.cbwleft.sms.model.dto;

import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class MessageDTO {

	/**
	 * 手机号码
	 */
	@Pattern(regexp = "[1][0-9]{10}" , message = "手机号码格式不正确")
	private String mobile;

	/**
	 * 模板id
	 */
	@Min(value = 0 ,message = "模板id格式不正确")
	@Max(value = Short.MAX_VALUE ,message = "模板id格式不正确")
	private String templateId;

	/**
	 * 短信参数，不超过200个字符
	 */
	private Map<String, Object> params = Collections.emptyMap();

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "MessageDTO [mobile=" + mobile + ", templateId=" + templateId + ", params=" + params + "]";
	}
	
}
