package com.cbwleft.sms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IMessageServiceTest {
	
	@Autowired
	private IMessageService messageService;

	@Test
	public void testQueryAndUpdateSendStatus() {
		messageService.queryAndUpdateSendStatus(26);
	}
}
