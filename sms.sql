SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '应用id',
  `name` varchar(20) NOT NULL COMMENT '应用名称',
  `private_key` varchar(30) NOT NULL COMMENT '签名私钥',
  `prefix` varchar(10) NOT NULL COMMENT '短信前缀',
  `validate_code_length` tinyint(4) NOT NULL DEFAULT '4' COMMENT '系统生成的数字验证码长度',
  `channel_params` varchar(255) NOT NULL COMMENT '第三方渠道SDK配置参数',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_template
-- ----------------------------
DROP TABLE IF EXISTS `t_template`;
CREATE TABLE `t_template` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT COMMENT '短信模板id',
  `channel_template_no` varchar(20) NOT NULL COMMENT '第三方渠道模板',
  `name` varchar(30) NOT NULL COMMENT '模板名称',
  `type` tinyint(4) NOT NULL COMMENT '短信类型',
  `template` varchar(70) NOT NULL COMMENT '短信模板',
  `validate_code_key` varchar(10) DEFAULT NULL COMMENT '短信验证码字段',
  `validate_code_expire` smallint(6) DEFAULT NULL COMMENT '短信验证码有效时间（秒）',
  `app_id` tinyint(4) NOT NULL COMMENT '应用id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信id',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `params` varchar(200) NOT NULL COMMENT '短信参数',
  `validate_code` varchar(10) DEFAULT NULL COMMENT '短信系统生成的验证码',
  `template_id` smallint(6) NOT NULL COMMENT '模板id',
  `send_status` tinyint(4) NOT NULL COMMENT '发送状态（0：失败，1：接口调用成功，2：发送成功）',
  `validate_status` tinyint(4) NOT NULL COMMENT '验证码验证状态（0：未验证，1：已验证）',
  `fail_code` varchar(40) DEFAULT NULL COMMENT '短信平台发送失败代码',
  `biz_id` varchar(20) DEFAULT NULL COMMENT '短信平台id',
  `recive_date` timestamp NULL DEFAULT NULL COMMENT '收到短信时间',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;