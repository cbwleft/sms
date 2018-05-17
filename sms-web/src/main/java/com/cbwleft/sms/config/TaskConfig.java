package com.cbwleft.sms.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskConfig {

	@Bean
	public ScheduledExecutorService scheduledExecutor() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
		return executor;
	}
	
}
