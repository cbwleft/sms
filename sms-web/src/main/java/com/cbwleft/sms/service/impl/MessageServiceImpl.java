package com.cbwleft.sms.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.cbwleft.sms.exception.ChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cbwleft.sms.constant.BaseResultEnum;
import com.cbwleft.sms.dao.constant.Columns;
import com.cbwleft.sms.dao.constant.RedisKeys;
import com.cbwleft.sms.dao.mapper.AppMapper;
import com.cbwleft.sms.dao.mapper.BatchMessageMapper;
import com.cbwleft.sms.dao.mapper.MessageMapper;
import com.cbwleft.sms.dao.mapper.TemplateMapper;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.BatchMessage;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.health.SMSHealthIndicator;
import com.cbwleft.sms.model.dto.BatchMessageDTO;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.model.dto.ValidateCodeDTO;
import com.cbwleft.sms.exception.BaseException;
import com.cbwleft.sms.service.IChannelSMSService;
import com.cbwleft.sms.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

@Service
public class MessageServiceImpl implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private AppMapper appMapper;
	
	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private BatchMessageMapper batchMessageMapper;

	@Autowired
	private IChannelSMSService channelSMSService;
	
	@Autowired
	private SMSHealthIndicator smsHealthIndicator;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public SendMessageResult send(MessageDTO messageDTO) {
		Template template = templateMapper.selectByPrimaryKey(Short.valueOf(messageDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		}
		App app = appMapper.selectByPrimaryKey(template.getAppId());
		if (app == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		
		Map<String, Object> params = messageDTO.getParams();
		String validateCodeKey = template.getValidateCodeKey();
		Short validateCodeExpire = template.getValidateCodeExpire();
		String validateCode = null;
		if (!StringUtils.isEmpty(validateCodeKey)) {
			if (validateCodeExpire == null || validateCodeExpire < 0) {
				throw new BaseException(BaseResultEnum.VALIDATE_CODE_EXPIRE_ILLEGAL);
			}
			logger.debug("需要发送验证码");
			validateCode = String.valueOf(params.get(validateCodeKey));
			if (StringUtils.isEmpty(validateCode)) {
				logger.info("参数中包含验证码{},系统不生成新的验证码", validateCode);
			} else {
				validateCode = getValidateCode(app.getValidateCodeLength());
				logger.debug("系统生成的验证码为{}", validateCode);
				Map<String, Object> paramsWithValidateCode = new HashMap<>(params);
				paramsWithValidateCode.put(validateCodeKey, validateCode);
				messageDTO.setParams(paramsWithValidateCode);
			}
		}
		
		logger.info("开始发送短信:{}", template);
		SendMessageResult result;
		try {
			result = channelSMSService.send(app, template, messageDTO);
		} catch (ChannelException e) {
			logger.error("渠道短信发送异常", e);
			result = new SendMessageResult(e.getMessage());
		}
		try {
			Message message = new Message();
			message.setMobile(messageDTO.getMobile());
			message.setParams(new ObjectMapper().writeValueAsString(params));
			message.setTemplateId(template.getId());
			if (result.isSuccess()) {
				message.setSendStatus(Columns.SendStatus.SENDING);
			} else {
				message.setSendStatus(Columns.SendStatus.FAILURE);
				message.setFailCode(result.getFailCode());
			}
			message.setBizId(result.getBizId());
			message.setValidateCode(validateCode);
			message.setValidateStatus(Columns.ValidateStatus.NO);
			Date now = new Date();
			message.setCreateDate(now);
			message.setUpdateDate(now);
			int rows = messageMapper.insertSelective(message);
			logger.info("{}插入结果:{},生成的自增id为:{}", message, rows, message.getId());
			if (!StringUtils.isEmpty(validateCodeKey)) {
				String redisKey = RedisKeys.VALIDATE_CODE_MESSAGE.format(messageDTO.getMobile(), template.getId());
				redisTemplate.opsForValue().set(redisKey, message, validateCodeExpire, TimeUnit.SECONDS);
			}
			if (!result.isSuccess()) {
				smsHealthIndicator.addSample(message);
			}
		} catch (Exception e) {
			logger.error("插入Message数据异常" + messageDTO, e);
		}
		return result;
	}

	@Override
	public String getValidateCode(byte length) {
		int base = (int) Math.pow(10, length - 1);
		int result = new Random().nextInt(9 * base) + base;
		return String.valueOf(result);
	}
	
	@Override
	public QuerySendResult queryAndUpdateSendStatus(Message message) {
		Template template = templateMapper.selectByPrimaryKey(message.getTemplateId());
		App app = appMapper.selectByPrimaryKey(template.getAppId());
		QuerySendResult querySendResult = channelSMSService.querySendStatus(app, message);
		if (querySendResult.isSuccess() && Columns.SendStatus.SENDING != querySendResult.getSendStatus()) {
			updateMessageSendStatus(message, querySendResult);
		}
		return querySendResult;
	}

	@Override
	public boolean check(ValidateCodeDTO validateDTO) {
		Template template = templateMapper.selectByPrimaryKey(Short.valueOf(validateDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		} else if (StringUtils.isEmpty(template.getValidateCodeKey())) {
			throw new BaseException(BaseResultEnum.NOT_VALIDATE_CODE_TEMPLATE);
		}
		App app = appMapper.selectByPrimaryKey(template.getAppId());
		if (app == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		String redisKey = RedisKeys.VALIDATE_CODE_MESSAGE.format(validateDTO.getMobile(), template.getId());
		Message message = (Message) redisTemplate.opsForValue().get(redisKey);
		//Message message = queryLatestMessage(validateDTO.getMobile(), template);
		if (message == null) {
			logger.debug("验证码已过期");
			return false;
		}
		if (!redisTemplate.delete(redisKey)) {
			logger.info("并发验证短信验证码或者验证码已过期");
			return false;
		}
		String validateCode = message.getValidateCode();
		if (!validateCode.equals(validateDTO.getValidateCode())) {
			logger.debug("验证码错误");
			return false;
		}
		try {
			int result = updateMessageValidateStatus(message);
			if (result > 0) {
				return true;
			} else {
				logger.warn("并发验证短信验证码");
				return false;
			}
		} catch (Exception e) {
			logger.error("更新Message数据异常", e);
			return false;
		}
	}

	@Override
	public int updateMessageValidateStatus(Message message) {
		Message updateMessage = new Message();
		updateMessage.setValidateStatus(Columns.ValidateStatus.YES);
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getId, message.getId())
						.andEqualTo(Message::getValidateStatus, message.getValidateStatus()))//CAS
				.build();
		int result = messageMapper.updateByExampleSelective(updateMessage, example);
		logger.info("{}更新验证码状态结果{}", message, result);
		return result;
	}

	@Override
	public Message queryLatestMessage(String mobile, Template template) {
		Instant expire = Instant.now().minusSeconds(template.getValidateCodeExpire());
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getMobile, mobile)
						.andEqualTo(Message::getTemplateId, template.getId())
						.andGreaterThan(Message::getSendStatus, Columns.SendStatus.FAILURE)
						.andGreaterThan(Message::getCreateDate, Date.from(expire)))
				.orderByDesc("id")//此处等同于create_date,但是具有更好的性能
				.build();
		PageHelper.startPage(1, 1, false);
		return messageMapper.selectOneByExample(example);
	}
	
	@Override
	public Message queryMessage(String mobile, String bizId) {
		Message message = new Message();
		message.setBizId(bizId);
		message.setMobile(mobile);
		return messageMapper.selectOne(message);
	}
	
	@Override
	public int updateMessageSendStatus(Message message, QuerySendResult querySendResult) {
		Message updateMessage = new Message();
		updateMessage.setId(message.getId());
		updateMessage.setSendStatus(querySendResult.getSendStatus());
		if (Columns.SendStatus.FAILURE == querySendResult.getSendStatus()) {
			updateMessage.setFailCode(querySendResult.getFailCode());
		} else if(Columns.SendStatus.SUCCESS == querySendResult.getSendStatus()) {
			updateMessage.setReceiveDate(querySendResult.getReceiveDate());
		}
		int result = messageMapper.updateByPrimaryKeySelective(updateMessage);
		logger.info("{}更新发送状态结果{}", updateMessage, result);
		smsHealthIndicator.addSample(updateMessage);
		return result;
	}

	@Override
	public List<Message> querySendingMessages(Date fromDate) {
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getSendStatus, Columns.SendStatus.SENDING)
						.andGreaterThan(Message::getCreateDate, fromDate))
				.orderByDesc("id")
				.build();
		PageHelper.startPage(1, 100, false);
		return messageMapper.selectByExample(example);
	}

	@Override
	public SendMessageResult batchSend(BatchMessageDTO batchMessageDTO) {
		App app = appMapper.selectByPrimaryKey(batchMessageDTO.getAppId());
		if (app == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		Set<String> mobile = batchMessageDTO.getMobile();
		logger.info("开始批量发送短信:{}", batchMessageDTO.getContent());
		SendMessageResult sendMessageResult = channelSMSService.batchSend(app, batchMessageDTO);
		try {
			BatchMessage batchMessage = new BatchMessage();
			batchMessage.setAppId(app.getId());
			batchMessage.setContent(batchMessageDTO.getContent());
			batchMessage.setTotal((short) mobile.size());

			if (sendMessageResult.isSuccess()) {
				batchMessage.setSendStatus(Columns.SendStatus.SENDING);
			} else {
				batchMessage.setSendStatus(Columns.SendStatus.FAILURE);
				batchMessage.setFailCode(sendMessageResult.getFailCode());
				batchMessage.setFailure(batchMessage.getTotal());
			}
			batchMessage.setBizId(sendMessageResult.getBizId());
			Date now = new Date();
			batchMessage.setCreateDate(now);
			batchMessage.setUpdateDate(now);
			int rows = batchMessageMapper.insertSelective(batchMessage);
			logger.info("{}插入结果:{},生成的自增id为:{}", batchMessage, rows, batchMessage.getId());
			String redisKey;
			if (sendMessageResult.isSuccess()) {
				redisKey = RedisKeys.BATCH_MESSAGE_SENDING.format(batchMessage.getId());
			} else {
				redisKey = RedisKeys.BATCH_MESSAGE_FAILURE.format(batchMessage.getId());
			}
			long add = stringRedisTemplate.opsForSet().add(redisKey, mobile.toArray(new String[0]));
			logger.info("{}集合新增:{}", redisKey, add) ;
		} catch (Exception e) {
			logger.error("插入BatchMessage数据异常" + batchMessageDTO, e);
		}
		return sendMessageResult;
	}
	
	@Override
	public BatchMessage queryBatchMessage(String bizId) {
		BatchMessage batchMessage = new BatchMessage();
		batchMessage.setBizId(bizId);
		return batchMessageMapper.selectOne(batchMessage);
	}
	
	@Override
	public int updateBatchMessageCount(int id, short sending, short success, short failure) {
		BatchMessage batchMessage = new BatchMessage();
		batchMessage.setId(id);
		batchMessage.setSuccess(success);
		batchMessage.setFailure(failure);
		if (sending == 0) {
			if (failure == 0) {
				batchMessage.setSendStatus(Columns.SendStatus.SUCCESS);
			} else {
				batchMessage.setSendStatus(Columns.SendStatus.COMPLETE);
			}
		}
		int result = batchMessageMapper.updateByPrimaryKeySelective(batchMessage);
		logger.info("{}更新批量短信发送数量结果{}", batchMessage, result);
		return result;
	}

}
