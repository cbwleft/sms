package com.cbwleft.sms.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;


public class ValidateCodeDTO {

	/**
	 * 手机号码
	 */
	@Pattern(regexp = "[1][0-9]{10}", message = "手机号码格式不正确")
	private String mobile;

	/**
	 * 模板id
	 */
	@Min(value = 0, message = "模板id格式不正确")
	@Max(value = Short.MAX_VALUE, message = "模板id格式不正确")
	private String templateId;

	/**
	 * 验证码
	 */
	@Length(min = 4, max = 6, message = "验证码长度不正确")
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
