package com.cbwleft.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="sms.health")
public class SMSHealthConfig {
	
	/**
	 * 统计样本数量
	 */
	private int samples;
	
	/**
	 * 成功率临界值
	 */
	private float threshold;

	public int getSamples() {
		return samples;
	}

	public void setSamples(int samples) {
		this.samples = samples;
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

}