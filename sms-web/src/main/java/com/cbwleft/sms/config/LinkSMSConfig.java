package com.cbwleft.sms.config;

import java.nio.charset.Charset;
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

	private StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML));// 凌凯接口返回text/html;charset=gb2312
		return converter;
	}
	
	/**
	 * 此处SpringBoot2.0.X和1.5.X的处理方式有较大区别。<br>
	 * 在SpringBoot2.0.X中:<br>
	 * 默认Content-Type: application/x-www-form-urlencoded;charset=UTF-8<br>
	 * 并优先取Content-Type中的编码，再取setCharset中的编码，在未指定Content-Type头的情况下，永远使用UTF-8编码<br>
	 * 在SpringBoot1.5.X中:<br>
	 * 默认Content-Type: application/x-www-form-urlencoded<br>
	 * 并取setCharset中的编码进行URLEncode，http服务端会按照其默认编码进行URLDecode，所以此处必须指定默认编码<br>
	 * @see FormHttpMessageConverter#writeForm
	 * @return
	 */
	private FormHttpMessageConverter formHttpMessageConverter() {
		FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
		formHttpMessageConverter.setCharset(Charset.forName("gb2312"));//设置默认编码为gb2312
		return formHttpMessageConverter;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.rootUri(baseUrl)
				.messageConverters(formHttpMessageConverter(), stringHttpMessageConverter())
				.build();
	}

}
