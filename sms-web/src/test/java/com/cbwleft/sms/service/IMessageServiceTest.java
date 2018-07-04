package com.cbwleft.sms.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cbwleft.sms.dao.mapper.MessageMapper;
import com.cbwleft.sms.dao.model.Message;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IMessageServiceTest {
	
	@Autowired
	private MessageMapper messageMapper;

	@Test
	public void testQueryAndUpdateSendStatus() {
		Message message =messageMapper.selectByPrimaryKey(244);
		//messageService.queryAndUpdateSendStatus(message);
		Assert.assertNotNull(message);
	}
}
