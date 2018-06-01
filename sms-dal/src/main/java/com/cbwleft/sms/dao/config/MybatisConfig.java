package com.cbwleft.sms.dao.config;

import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan("com.cbwleft.sms.dao.mapper")
public class MybatisConfig {

}
