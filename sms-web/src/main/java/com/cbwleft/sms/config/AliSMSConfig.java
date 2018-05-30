package com.cbwleft.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云通讯配置
 * @author cbwleft
 */
@Configuration
@ConfigurationProperties(prefix="sms.channel.aliyun")
public class AliSMSConfig {

	private final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	private final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	
	/**
	 * 你的accessKeyId
	 */
	private String accessKeyId;
	
	/**
	 * 你的accessKeySecret
	 */
	private String accessKeySecret;

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	
	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	@Bean
	public IAcsClient acsClient() throws ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		return acsClient;
	}

}