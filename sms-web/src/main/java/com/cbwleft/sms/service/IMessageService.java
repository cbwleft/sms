package com.cbwleft.sms.service;

import java.util.Date;
import java.util.List;

import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.BatchMessageDTO;
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
	 * @param message
	 * @return
	 */
	public QuerySendResult queryAndUpdateSendStatus(Message message);

	/**
	 * 查询发送中的短信
	 * @param fromDate 查询该时间点之后的数据
	 * @return
	 */
	public List<Message> querySendingMessages(Date fromDate);

	/**
	 * 根据手机号和第三方id查询短信
	 * @param mobile
	 * @param bizId
	 * @return
	 */
	public Message queryMessage(String mobile, String bizId);

	/**
	 * 更新发送状态
	 * @param message 更新前短信
	 * @param querySendResult 短信查询结果
	 * @return
	 */
	public int updateMessageSendStatus(Message message, QuerySendResult querySendResult);

	/**
	 * 批量发送短信
	 * @param batchMessage
	 * @return
	 */
	public SendMessageResult batchSend(BatchMessageDTO batchMessage);
}
