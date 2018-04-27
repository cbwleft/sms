package com.cbwleft.sms.model.dto;

import org.hibernate.validator.constraints.Length;

import com.cbwleft.sms.model.validation.Mobile;
import com.cbwleft.sms.model.validation.TemplateId;


public class ValidateCodeDTO {

	/**
	 * 手机号码
	 */
	@Mobile
	private String mobile;

	/**
	 * 模板id
	 */
	@TemplateId
	private String templateId;

	/**
	 * 验证码
	 */
	@Length(min = 4, max = 6, message = "{com.cbwleft.sms.constraints.ValidateCode.messsage}")
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
