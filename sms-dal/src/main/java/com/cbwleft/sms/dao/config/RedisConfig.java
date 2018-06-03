package com.cbwleft.sms.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis存储配置
 * @author cbwleft
 *
 */
@Configuration
public class RedisConfig {
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate=new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		RedisSerializer<Object> objectSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setValueSerializer(objectSerializer);
		redisTemplate.setHashKeySerializer(objectSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
    }
	
}
