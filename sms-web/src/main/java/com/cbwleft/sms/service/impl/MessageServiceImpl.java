package com.cbwleft.sms.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cbwleft.sms.dao.Constants;
import com.cbwleft.sms.dao.mapper.AppMapper;
import com.cbwleft.sms.dao.mapper.MessageMapper;
import com.cbwleft.sms.dao.mapper.TemplateMapper;
import com.cbwleft.sms.dao.model.App;
import com.cbwleft.sms.dao.model.Message;
import com.cbwleft.sms.dao.model.Template;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.QuerySendResult;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.model.dto.ValidateCodeDTO;
import com.cbwleft.sms.model.vo.BaseException;
import com.cbwleft.sms.model.vo.BaseResultEnum;
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
	private TemplateMapper templateMapper;

	@Autowired
	private AppMapper appMapper;

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private IChannelSMSService channelSMSService;

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
		logger.info("开始发送短信:{}", template);
		SendMessageResult result = channelSMSService.send(app, template, messageDTO);
		try {
			Message message = new Message();
			message.setMobile(messageDTO.getMobile());
			message.setParams(new ObjectMapper().writeValueAsString(messageDTO.getParams()));
			message.setTemplateId(template.getId());
			if (result.isSuccess()) {
				message.setSendStatus(Constants.SendStatus.SENDING);
			} else {
				message.setSendStatus(Constants.SendStatus.FAILURE);
				message.setFailCode(result.getFailCode());
			}
			message.setBizId(result.getBizId());
			message.setValidateCode(result.getValidateCode());
			message.setValidateStatus(Constants.ValidateStatus.NO);
			int rows = messageMapper.insert(message);
			logger.info("{}插入结果:{}", message, rows);
		} catch (Exception e) {
			logger.error("插入Message数据异常" + messageDTO, e);
		}
		return result;
	}

	@Override
	public String getValidateCode(byte length) {
		int base = (int) Math.pow(10, length - 1);
		int result = new Random().nextInt((int) (9 * base)) + base;
		return String.valueOf(result);
	}
	
	@Override
	public QuerySendResult queryAndUpdateSendStatus(Message message) {
		Template template = templateMapper.selectByPrimaryKey(message.getTemplateId());
		App app = appMapper.selectByPrimaryKey(template.getAppId());
		QuerySendResult querySendResult = channelSMSService.querySendStatus(app, message);
		if (querySendResult.isSuccess() && Constants.SendStatus.SENDING != querySendResult.getSendStatus()) {
			Message updateMessage = new Message();
			updateMessage.setId(message.getId());
			updateMessage.setSendStatus(querySendResult.getSendStatus());
			if (Constants.SendStatus.FAILURE == querySendResult.getSendStatus()) {
				updateMessage.setFailCode(querySendResult.getFailCode());
			}else if(Constants.SendStatus.SUCCESS == querySendResult.getSendStatus()) {
				updateMessage.setReciveDate(querySendResult.getReceiveDate());
			}
			int result = messageMapper.updateByPrimaryKeySelective(updateMessage);
			logger.info("{}更新发送状态结果{}", updateMessage, result);
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
		Message message = queryLatestMessage(validateDTO.getMobile(), template);
		if (message == null) {
			logger.debug("验证码已过期");
			return false;
		}
		if (Constants.ValidateStatus.YES == message.getValidateStatus()) {
			logger.debug("该验证码已使用");
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
		updateMessage.setValidateStatus(Constants.ValidateStatus.YES);
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
						.andGreaterThan(Message::getSendStatus, Constants.SendStatus.FAILURE)
						.andGreaterThan(Message::getCreateDate, Date.from(expire)))
				.orderByDesc("id")//此处等同于create_date,但是具有更好的性能
				.build();
		PageHelper.startPage(1, 1, false);
		List<Message> list = messageMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<Message> querySendingMessages() {
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getSendStatus, Constants.SendStatus.SENDING))
				.orderByDesc("id")
				.build();
		PageHelper.startPage(1, 100, false);
		return messageMapper.selectByExample(example);
	}

}
