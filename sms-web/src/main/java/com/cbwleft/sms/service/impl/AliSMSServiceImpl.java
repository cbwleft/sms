package com.cbwleft.sms.service.impl;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.cbwleft.sms.constant.ConfigConstants;
import com.cbwleft.sms.dao.Constants;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.BatchMessageDTO;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.service.IChannelSMSService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Profile(ConfigConstants.ALI_SMS)
public class AliSMSServiceImpl implements IChannelSMSService {

	private static final Logger logger = LoggerFactory.getLogger(AliSMSServiceImpl.class);

	@Autowired
	private IAcsClient acsClient;

	@Override
	public SendMessageResult send(App app, Template template, MessageDTO message) {
		try {
			SendSmsRequest request = new SendSmsRequest();
			request.setMethod(MethodType.POST);
			request.setPhoneNumbers(message.getMobile());
			request.setSignName(app.getPrefix());
			request.setTemplateCode(template.getChannelTemplateNo());
			Map<String, Object> params = message.getParams();
			request.setTemplateParam(new ObjectMapper().writeValueAsString(params));
			SendSmsResponse sendSmsResponse = null;
			sendSmsResponse = acsClient.getAcsResponse(request);
			if ("OK".equals(sendSmsResponse.getCode())) {
				return new SendMessageResult(true, sendSmsResponse.getBizId());
			} else {
				logger.warn("阿里云通讯短信发送失败,原因:{},错误代码:{},请求Id:{}", sendSmsResponse.getMessage(), sendSmsResponse.getCode(),
						sendSmsResponse.getRequestId());
				return new SendMessageResult(sendSmsResponse.getCode(), sendSmsResponse.getBizId());
			}
		} catch (Exception e) {
			logger.error("阿里云通讯短信接口异常", e);
			return new SendMessageResult(e.getMessage(), null);
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
			if ("OK".equals(querySendDetailsResponse.getCode())) {
				List<SmsSendDetailDTO> list = querySendDetailsResponse.getSmsSendDetailDTOs();
				if (CollectionUtils.isEmpty(list)) {
					if (Duration.between(message.getCreateDate().toInstant(), Instant.now()).toMinutes() > 5) {
						return new QuerySendResult(true, Constants.SendStatus.FAILURE, null, null, "no match send record");
					} else {
						logger.info("未查询到对应的短信记录,可能是因为阿里读写库同步延迟");
						return new QuerySendResult(false);
					}
				}
				SmsSendDetailDTO smsSendDetailDTO = list.get(0);
				logger.info("阿里云通讯短信查询结果为:{}", new ObjectMapper().writeValueAsString(smsSendDetailDTO));
				int aliSendStatus = smsSendDetailDTO.getSendStatus().intValue();// 1：等待回执，2：发送失败，3：发送成功
				byte sendStatus = Constants.SendStatus.SENDING;
				Date reciveDate = null;
				String failCode = null;
				if (aliSendStatus == 2) {
					sendStatus = Constants.SendStatus.FAILURE;
					failCode = smsSendDetailDTO.getErrCode();
				} else if (aliSendStatus == 3) {
					reciveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")// 2017-05-25 00:00:00
							.parse(smsSendDetailDTO.getReceiveDate());
					sendStatus = Constants.SendStatus.SUCCESS;
				}
				return new QuerySendResult(true, sendStatus, smsSendDetailDTO.getContent(), reciveDate,
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
	public SendMessageResult batchSend(App app, BatchMessageDTO batchMessage) {
		throw new UnsupportedOperationException();
	}

}