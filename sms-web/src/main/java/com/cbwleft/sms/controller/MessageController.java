package com.cbwleft.sms.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbwleft.sms.constant.BaseResultEnum;
import com.cbwleft.sms.model.dto.BatchMessageDTO;
import com.cbwleft.sms.model.dto.MessageDTO;
import com.cbwleft.sms.model.dto.SendMessageResult;
import com.cbwleft.sms.model.dto.ValidateCodeDTO;
import com.cbwleft.sms.model.vo.BaseResult;
import com.cbwleft.sms.service.IMessageService;

@RestController
public class MessageController extends BaseController {

	@Autowired
	private IMessageService messageService;

	/**
	 * 发送短信
	 * 
	 * @param message
	 * @return
	 */
	@PostMapping("/send")
	public BaseResult send(@RequestBody @Valid MessageDTO message, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		if (message.getParams() == null) {
			message.setParams(Collections.emptyMap());
		}
		SendMessageResult result = messageService.send(message);
		if (result.isSuccess()) {
			return new BaseResult(BaseResultEnum.SUCCESS, "发送成功");
		} else {
			return new BaseResult(BaseResultEnum.FAIL, result.getFailCode());
		}
	}

	/**
	 * 验证验证码
	 * 
	 * @param validateCode
	 * @return
	 */
	@PostMapping("/check")
	public BaseResult check(@RequestBody @Valid ValidateCodeDTO validateCode, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		boolean result = messageService.check(validateCode);
		if (result) {
			return new BaseResult(BaseResultEnum.SUCCESS, "验证通过");
		} else {
			return new BaseResult(BaseResultEnum.FAIL, "验证失败");
		}
	}
	
	/**
	 * 批量发送短信
	 * 
	 * @param batchMessage
	 * @return
	 */
	@PostMapping("/batchSend")
	public BaseResult batchSend(@RequestBody @Valid BatchMessageDTO batchMessage, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		SendMessageResult result = messageService.batchSend(batchMessage);
		if (result.isSuccess()) {
			return new BaseResult(BaseResultEnum.SUCCESS, "发送成功");
		} else {
			return new BaseResult(BaseResultEnum.FAIL, result.getFailCode());
		}
	}

}
