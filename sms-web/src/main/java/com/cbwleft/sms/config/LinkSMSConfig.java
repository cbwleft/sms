package com.cbwleft.sms.config;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 凌凯短信配置
 * @author cbwleft
 */
@Configuration
@ConfigurationProperties(prefix = "sms.channel.link")
public class LinkSMSConfig {

	private final String baseUrl = "http://sdk2.028lk.com:9880/sdk2/";

	/**
	 * 企业号
	 */
	private String corpId;

	/**
	 * 密码
	 */
	private String password;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));// 凌凯接口返回text/html;charset=gb2312
		return converter;

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, StringHttpMessageConverter stringHttpMessageConverter) {
		return builder
				.rootUri(baseUrl)
				.messageConverters(new FormHttpMessageConverter(), stringHttpMessageConverter)
				.build();
	}

}
