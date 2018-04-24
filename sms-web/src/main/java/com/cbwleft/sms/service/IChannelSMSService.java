package com.cbwleft.sms.service;

import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;

public interface IChannelSMSService {

	/**
	 * 调用具体的短信平台发送短信
	 * @param app
	 * @param template
	 * @param message
	 * @return
	 */
	public SendMessageResult send(App app, Template template, MessageDTO message);

	/**
	 * 查询发送状态
	 * @param app 
	 * @param message
	 * @return
	 */
	public QuerySendResult querySendStatus(App app, Message message);

	
}
