package com.cbwleft.sms.model.dto;

public class ValidateCodeDTO {
	
	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 模板id
	 */
	private String templateId;
	
	/**
	 * 验证码
	 */
	private String validateCode;

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

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	@Override
	public String toString() {
		return "ValidateCodeDTO [mobile=" + mobile + ", templateId=" + templateId + ", validateCode=" + validateCode
				+ "]";
	}
	
}
