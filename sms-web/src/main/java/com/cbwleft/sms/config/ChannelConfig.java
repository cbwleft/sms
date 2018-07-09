package com.cbwleft.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 短信渠道配置
 */
@Configuration
@ConfigurationProperties(prefix="sms.channel")
public class ChannelConfig {

	private List<String> available;

	private String batchSend;

	public void setAvailable(List<String> available) {
		this.available = available;
	}

	public List<String> getAvailable() {
		return available;
	}

	public String getBatchSend() {
		return batchSend;
	}

	public void setBatchSend(String batchSend) {
		this.batchSend = batchSend;
	}
}
