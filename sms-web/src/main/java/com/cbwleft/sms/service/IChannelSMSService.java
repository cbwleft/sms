package com.cbwleft.sms.service;

import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.exception.ChannelException;
import com.cbwleft.sms.model.dto.BatchMessageDTO;
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
	SendMessageResult send(App app, Template template, MessageDTO message) throws ChannelException;

	/**
	 * 查询发送状态
	 * @param app 
	 * @param message
	 * @return
	 */
	QuerySendResult querySendStatus(App app, Message message);

	/**
	 * 调用具体的短信平台批量发送短信
	 * @param app
	 * @param batchMessage
	 * @return
	 */
	SendMessageResult batchSend(App app, BatchMessageDTO batchMessage);

}
