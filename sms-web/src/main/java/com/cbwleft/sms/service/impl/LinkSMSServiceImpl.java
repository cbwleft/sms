package com.cbwleft.sms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cbwleft.sms.exception.AmountNotEnoughException;
import com.cbwleft.sms.exception.ChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.cbwleft.sms.config.LinkSMSConfig;
import com.cbwleft.sms.constant.Channels;
import com.cbwleft.sms.dao.constant.Columns;
import com.cbwleft.sms.dao.constant.RedisKeys;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.BatchMessage;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.service.IBatchQueryable;
import com.cbwleft.sms.service.IMessageService;

@Service(Channels.LINK_SMS)
public class LinkSMSServiceImpl implements IBatchQueryable {

	private static final Logger logger = LoggerFactory.getLogger(LinkSMSServiceImpl.class);

	private static final String REPORT_STATUS_SUCCESS = "1";

	private static final String REPORT_STATUS_FAILURE = "2";

	public static final int AMOUNT_NOT_ENOUGH = -5;

	@Autowired
	private LinkSMSConfig linkSMSConfig;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public SendMessageResult send(App app, Template template, MessageDTO message) throws ChannelException {
		String content = renderString(template.getTemplate(), message.getParams());
		content += "【" + app.getPrefix() + "】";
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("CorpID", linkSMSConfig.getCorpId());
		params.add("Pwd", linkSMSConfig.getPassword());
		params.add("Mobile", message.getMobile());
		params.add("Content", content);
		String body;
		try {
			ResponseEntity<String> result = restTemplate.postForEntity("/BatchSend2.aspx", params, String.class);
			body = result.getBody();
			logger.info("凌凯短信发送接口返回{}", body);
		} catch (Exception e) {
			throw new ChannelException(e);
		}
		int intBody = Integer.parseInt(body);
		checkChannelException(intBody);
		if (intBody > 0) {
			return new SendMessageResult(true, body);
		} else {
			logger.warn("凌凯短信发送失败,错误代码:{}", body);
			return new SendMessageResult(body);
		}
	}

	private void checkChannelException(int intBody) throws ChannelException {
		 if (intBody == AMOUNT_NOT_ENOUGH) {
		 	throw new AmountNotEnoughException();
		}
	}

	private String renderString(String content, Map<String, Object> map) {
		Set<Entry<String, Object>> sets = map.entrySet();
		for (Entry<String, Object> entry : sets) {
			String regex = "\\$\\{" + entry.getKey() + "}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(content);
			content = matcher.replaceAll(String.valueOf(entry.getValue()));
		}
		return content;
	}

	@Override
	public QuerySendResult querySendStatus(App app, Message message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchQueryAndUpdateSendStatus() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("CorpID", linkSMSConfig.getCorpId());
		params.add("Pwd", linkSMSConfig.getPassword());
		ResponseEntity<String> result = restTemplate.postForEntity("/GetReportSMS.aspx", params, String.class);
		String body = result.getBody();
		if (StringUtils.isEmpty(body)) {
			logger.debug("没有新的报告数据");
		} else if (body.endsWith("|||")) {// ID+'$$$$$'+号码+''$$$$$'+时间+'$$$$$'+报告标志+'$$$$$'+报告+'$$$$$'+报告日期+'|||'
			logger.info("凌凯接收短信发送状态报告接口返回{}", body);
			String[] reports = body.split("\\|\\|\\|");
			Map<String, BatchMessage> batchMessages = new HashMap<>();// 自己实现一级缓存
			for (String report : reports) {
				try {
					String[] args = report.split("\\$\\$\\$\\$\\$");
					String bizId = args[0];
					String mobile = args[1];
					String status = args[3];// 报告标志：0，无状态；1，成功；2，失败；3，其他
					String failCode = args[4];// 报告：各运营商直接返回的状态报告值
					Date receiveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(args[5]);// 2018-05-29 10:36:48
					Message message = messageService.queryMessage(mobile, bizId);
					if (message != null) {
						byte sendStatus = Columns.SendStatus.SENDING;
						if (REPORT_STATUS_SUCCESS.equals(status)) {
							sendStatus = Columns.SendStatus.SUCCESS;
						} else if (REPORT_STATUS_FAILURE.equals(status)) {
							sendStatus = Columns.SendStatus.FAILURE;
						}
						QuerySendResult querySendResult = new QuerySendResult(true, sendStatus, null, receiveDate,
								failCode);
						if (sendStatus != Columns.SendStatus.SENDING) {
							messageService.updateMessageSendStatus(message, querySendResult);
						}
					} else {
						logger.debug("检查是否是批量发送短信");
						BatchMessage batchMessage = batchMessages.get(bizId);
						if (batchMessage == null) {
							batchMessage = messageService.queryBatchMessage(bizId);
						}
						if (batchMessage != null) {
							batchMessages.putIfAbsent(bizId, batchMessage);
							int id = batchMessage.getId();
							stringRedisTemplate.opsForSet().remove(RedisKeys.BATCH_MESSAGE_SENDING.format(id), mobile);
							if (REPORT_STATUS_SUCCESS.equals(status)) {
								stringRedisTemplate.opsForSet().add(RedisKeys.BATCH_MESSAGE_SUCCESS.format(id), mobile);
							} else if (REPORT_STATUS_FAILURE.equals(status)) {
								stringRedisTemplate.opsForSet().add(RedisKeys.BATCH_MESSAGE_FAILURE.format(id), mobile);
							}
						} else {
							logger.info("未找到对应的短信记录,可能未通过短信微服务发送或者不同环境账户未分离");
						}
					}
				} catch (Exception e) {
					logger.error("凌凯接收短信发送状态格式异常:{}", e.getMessage());
				}
			}
			for (BatchMessage batchMessage : batchMessages.values()) {
				try {
					int id = batchMessage.getId();
					short sending = stringRedisTemplate.opsForSet().size(RedisKeys.BATCH_MESSAGE_SENDING.format(id)).shortValue();
					short success = stringRedisTemplate.opsForSet().size(RedisKeys.BATCH_MESSAGE_SUCCESS.format(id)).shortValue();
					short failure = stringRedisTemplate.opsForSet().size(RedisKeys.BATCH_MESSAGE_FAILURE.format(id)).shortValue();
					messageService.updateBatchMessageCount(id, sending, success, failure);
				} catch (Exception e) {
					logger.error("更新批量发送短信异常:{}", e);
				}
			}
		} else {
			logger.warn("凌凯接收短信发送状态报告失败,错误代码:{}", body);
		}
	}

	@Override
	public SendMessageResult batchSend(App app, String[] mobile, String content) throws ChannelException {
		try {
			content += "【" + app.getPrefix() + "】";
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
			params.add("CorpID", linkSMSConfig.getCorpId());
			params.add("Pwd", linkSMSConfig.getPassword());
			params.add("Mobile", String.join(",", mobile));
			params.add("Content", content);
			ResponseEntity<String> result = restTemplate.postForEntity("/BatchSend2.aspx", params, String.class);
			String body = result.getBody();
			logger.info("凌凯短信发送接口返回{}", body);
			int intBody = Integer.parseInt(body);
			if (intBody > 0) {
				return new SendMessageResult(true, body);
			} else {
				logger.warn("凌凯短信发送失败,错误代码:{}", body);
				return new SendMessageResult(body);
			}
		} catch (Exception e) {
			throw new ChannelException(e);
		}
	}

	@Override
	public String getChannel() {
		return Channels.LINK_SMS;
	}

}