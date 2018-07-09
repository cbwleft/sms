package com.cbwleft.sms.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.cbwleft.sms.constant.BaseResultEnum;
import com.cbwleft.sms.constant.Channels;
import com.cbwleft.sms.dao.constant.Columns;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.exception.AmountNotEnoughException;
import com.cbwleft.sms.exception.BaseException;
import com.cbwleft.sms.exception.ChannelException;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.service.IChannelSMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(Channels.ALI_SMS)
public class AliSMSServiceImpl implements IChannelSMSService {


	private static final String SUCCESS = "OK";

	private static final String LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";

	private static final String OUT_OF_SERVICE = "isv.OUT_OF_SERVICE";

	private static final String AMOUNT_NOT_ENOUGH = "isv.AMOUNT_NOT_ENOUGH";

	private static final Logger logger = LoggerFactory.getLogger(AliSMSServiceImpl.class);

	@Autowired
	private IAcsClient acsClient;

	@Override
	public SendMessageResult send(App app, Template template, MessageDTO message) throws ChannelException {
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(message.getMobile());
		request.setSignName(app.getPrefix());
		request.setTemplateCode(template.getChannelTemplateNo());
		Map<String, Object> params = message.getParams();
		try {
			request.setTemplateParam(new ObjectMapper().writeValueAsString(params));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			throw new ChannelException(e);
		}
		checkChannelException(sendSmsResponse);
		if (SUCCESS.equals(sendSmsResponse.getCode())) {
			return new SendMessageResult(true, sendSmsResponse.getBizId());
		} else {
			logger.warn("阿里云通讯短信发送失败,原因:{},错误代码:{},请求Id:{}", sendSmsResponse.getMessage(), sendSmsResponse.getCode(),
					sendSmsResponse.getRequestId());
			return new SendMessageResult(sendSmsResponse.getCode());
		}
	}

	private void checkChannelException(SendSmsResponse sendSmsResponse) throws ChannelException {
		String code = sendSmsResponse.getCode();
		if (LIMIT_CONTROL.equals(code)) {
			throw new BaseException(BaseResultEnum.SEND_TOO_FREQUENTLY, "短信发送过于频繁," + sendSmsResponse.getMessage());
		} else if (OUT_OF_SERVICE.equals(code) || AMOUNT_NOT_ENOUGH.equals(code)) {
			throw new AmountNotEnoughException();
		}
	}

	@Override
	public QuerySendResult querySendStatus(App app, Message message) {
		try {
			QuerySendDetailsRequest request = new QuerySendDetailsRequest();
			// 必填-号码
			request.setPhoneNumber(message.getMobile());
			// 可选-调用发送短信接口时返回的BizId
			request.setBizId(message.getBizId());
			// 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
			request.setSendDate(new SimpleDateFormat("yyyyMMdd").format(message.getCreateDate()));
			// 必填-页大小
			request.setPageSize(1L);
			// 必填-当前页码从1开始计数
			request.setCurrentPage(1L);
			// hint 此处可能会抛出异常，注意catch
			QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
			// 获取返回结果
			if (SUCCESS.equals(querySendDetailsResponse.getCode())) {
				List<SmsSendDetailDTO> list = querySendDetailsResponse.getSmsSendDetailDTOs();
				if (CollectionUtils.isEmpty(list)) {
					if (Duration.between(message.getCreateDate().toInstant(), Instant.now()).toMinutes() > 5) {
						return new QuerySendResult(true, Columns.SendStatus.FAILURE, null, null, "noMatchSendRecord");
					} else {
						logger.info("未查询到对应的短信记录,可能是因为阿里读写库同步延迟");
						return new QuerySendResult(false);
					}
				}
				SmsSendDetailDTO smsSendDetailDTO = list.get(0);
				logger.info("阿里云通讯短信查询结果为:{}", new ObjectMapper().writeValueAsString(smsSendDetailDTO));
				int aliSendStatus = smsSendDetailDTO.getSendStatus().intValue();// 1：等待回执，2：发送失败，3：发送成功
				byte sendStatus = Columns.SendStatus.SENDING;
				Date receiveDate = null;
				String failCode = null;
				if (aliSendStatus == 2) {
					sendStatus = Columns.SendStatus.FAILURE;
					failCode = smsSendDetailDTO.getErrCode();
				} else if (aliSendStatus == 3) {
					receiveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")// 2017-05-25 00:00:00
							.parse(smsSendDetailDTO.getReceiveDate());
					sendStatus = Columns.SendStatus.SUCCESS;
				}
				return new QuerySendResult(true, sendStatus, smsSendDetailDTO.getContent(), receiveDate,
						failCode);
			} else {
				return new QuerySendResult(false);
			}
		} catch (Exception e) {
			logger.error("阿里云通讯查询短信接口异常", e);
			return new QuerySendResult(false);
		}
	}

	@Override
	public SendMessageResult batchSend(App app, String[] mobile, String content) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getChannel() {
		return Channels.ALI_SMS;
	}

}