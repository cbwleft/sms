package com.cbwleft.sms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cbwleft.sms.dao.Constants;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.model.vo.BaseException;
import com.cbwleft.sms.model.vo.BaseResultEnum;
import com.cbwleft.sms.service.IChannelSMSService;
import com.cbwleft.sms.service.IMessageService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AliSMSServiceImpl implements IChannelSMSService {

	private static final Logger logger = LoggerFactory.getLogger(AliSMSServiceImpl.class);

	@Autowired
	private IMessageService messageService;

	@Override
	public SendMessageResult send(App app, Template template, MessageDTO message) {
		IAcsClient acsClient = null;
		try {
			String channelParams = app.getChannelParams();
			AliSMSConfig config = new ObjectMapper().readValue(channelParams, AliSMSConfig.class);
			acsClient = config.acsClient();
		} catch (JsonParseException | JsonMappingException e) {
			throw new BaseException(BaseResultEnum.ILLEGAL_CHANNEL_PARAMS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String validateCode = null;
		try {
			SendSmsRequest request = new SendSmsRequest();
			request.setMethod(MethodType.POST);
			request.setPhoneNumbers(message.getMobile());
			request.setSignName(app.getPrefix());
			request.setTemplateCode(template.getChannelTemplateNo());
			String validateCodeKey = template.getValidateCodeKey();
			Map<String, Object> params = message.getParams();
			if (!StringUtils.isEmpty(validateCodeKey)) {
				logger.debug("需要发送验证码");
				validateCode = String.valueOf(params.get(validateCodeKey));
				if (StringUtils.isEmpty(validateCode)) {
					logger.info("参数中包含验证码{},系统不生成新的验证码", params.get(validateCodeKey));
				} else {
					validateCode = messageService.getValidateCode(app.getValidateCodeLength());
					logger.debug("系统生成的验证码为{}", validateCode);
					params = new HashMap<>(params);// clone参数,方法不应该对参数本身做修改
					params.put(validateCodeKey, validateCode);
				}
			}
			request.setTemplateParam(new ObjectMapper().writeValueAsString(params));
			SendSmsResponse sendSmsResponse = null;
			sendSmsResponse = acsClient.getAcsResponse(request);
			if ("OK".equals(sendSmsResponse.getCode())) {
				return new SendMessageResult(true, validateCode, sendSmsResponse.getBizId());
			} else {
				logger.warn("短信发送失败,原因:{},错误代码:{},请求Id:{}", sendSmsResponse.getMessage(), sendSmsResponse.getCode(),
						sendSmsResponse.getRequestId());
				return new SendMessageResult(validateCode, sendSmsResponse.getCode(), sendSmsResponse.getBizId());
			}
		} catch (Exception e) {
			logger.error("短信接口异常", e);
			return new SendMessageResult(validateCode, e.getMessage(), null);
		}
	}

	@Override
	public QuerySendResult querySendStatus(App app, Message message) {
		try {
			String channelParams = app.getChannelParams();
			AliSMSConfig config = new ObjectMapper().readValue(channelParams, AliSMSConfig.class);
			IAcsClient acsClient = config.acsClient();
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
					return new QuerySendResult(true, Constants.SendStatus.FAILURE, null, null, "no match send record");
				}
				SmsSendDetailDTO smsSendDetailDTO = list.get(0);
				logger.info("短信查询结果为:{}", new ObjectMapper().writeValueAsString(smsSendDetailDTO));
				int aliSendStatus = smsSendDetailDTO.getSendStatus().intValue();// 1：等待回执，2：发送失败，3：发送成功
				byte sendStatus = 0;
				Date reciveDate = null;
				String failCode = null;
				if (aliSendStatus == 1) {
					sendStatus = Constants.SendStatus.SENDING;
				} else if (aliSendStatus == 2) {
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
			logger.error("查询短信接口异常", e);
			return new QuerySendResult(false);
		}
	}

	private static class AliSMSConfig {

		private final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		private final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		private String accessKeyId;// 你的accessKeyId,参考本文档步骤2
		private String accessKeySecret;// 你的accessKeySecret，参考本文档步骤2

		@SuppressWarnings("unused")
		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		@SuppressWarnings("unused")
		public void setAccessKeySecret(String accessKeySecret) {
			this.accessKeySecret = accessKeySecret;
		}

		public IAcsClient acsClient() throws ClientException {
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			return acsClient;
		}

	}

}
