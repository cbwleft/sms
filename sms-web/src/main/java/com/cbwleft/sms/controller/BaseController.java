package com.cbwleft.sms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cbwleft.sms.constant.BaseResultEnum;
import com.cbwleft.sms.exception.BaseException;
import com.cbwleft.sms.model.vo.BaseResult;

public class BaseController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler({ BaseException.class })
	public BaseResult exception(BaseException e) {
		logger.info("业务异常:{},message:{}", e.getBaseResultEnum(), e.getMessage());
		return new BaseResult(e);
	}
	
	@ExceptionHandler({ Exception.class })
	public BaseResult exception(Exception e) {
		logger.error("Controller未处理异常", e);
		return new BaseResult(BaseResultEnum.SERVER_ERROR, e.getMessage());
	}

}
