package com.cbwleft.sms.service;

import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.model.dto.ValidateCodeDTO;

public interface IMessageService {

	/**
	 * 发送短信
	 * @param message
	 * @return
	 */
	public SendMessageResult send(MessageDTO message);

	/**
	 * 校验验证码
	 * @param validateCode
	 * @return
	 */
	public boolean check(ValidateCodeDTO validateCode);
	
	/**
	 * 查询最近一条验证码短信
	 * @param mobile 手机号
	 * @param template 需要id,validateExpire字段
	 * @return
	 */
	public Message queryLatestMessage(String mobile, Template template);

	/**
	 * 将短信验证状态修改为已验证
	 * @param message
	 * @return
	 */
	public int updateMessageValidateStatus(Message message);

	/**
	 * 获取验证码
	 * @param length 验证码长度
	 * @return
	 */
	public String getValidateCode(byte length);

	/**
	 * 查询并更新发送状态
	 * @param messageId
	 * @return TODO
	 */
	public QuerySendResult queryAndUpdateSendStatus(int messageId);
}
