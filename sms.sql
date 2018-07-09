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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 测试数据
INSERT INTO t_app (id, name, private_key, prefix, validate_code_length, channel_params) VALUES (1, '应用1', '1', '短信前缀', 4, '{\"accessKeyId\":\"你的accessKeyId\",\"accessKeySecret\":\"你的accessKeySecret\"}');
INSERT INTO t_template (id, channel_template_no, name, type, template, validate_code_key, validate_code_expire, app_id) VALUES (1, 'SMS_32505088', '用户注册验证码', 0, '验证码${code}，您正在注册成为${product}用户，感谢您的支持！', 'code', '300', 1);

-- V1.1.1新增
-- ----------------------------
-- Table structure for t_batch_message
-- ----------------------------
DROP TABLE IF EXISTS `t_batch_message`;
CREATE TABLE `t_batch_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '批量消息id',
  `total` smallint(6) NOT NULL COMMENT '发送总量',
  `content` varchar(255) NOT NULL COMMENT '发送内容',
  `send_status` tinyint(4) NOT NULL COMMENT '发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）',
  `biz_id` varchar(20) DEFAULT NULL COMMENT '短信通道id',
  `fail_code` varchar(40) DEFAULT NULL COMMENT '短信平台发送失败代码',
  `success` smallint(6) NOT NULL DEFAULT '0' COMMENT '发送成功数',
  `failure` smallint(6) NOT NULL DEFAULT '0' COMMENT '发送失败数',
  `appId` tinyint(4) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `t_message`
MODIFY COLUMN `fail_code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信平台发送失败代码' AFTER `validate_status`;

ALTER TABLE `t_batch_message`
MODIFY COLUMN `fail_code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信平台发送失败代码' AFTER `biz_id`;

-- V1.2.0新增
alter table t_batch_message change appId app_id tinyint not null COMMENT '应用id';
alter table t_message change recive_date receive_date timestamp null comment '收到短信时间';

ALTER TABLE `t_message`
ADD COLUMN `channel`  varchar(10) NOT NULL COMMENT '短信发送渠道' AFTER `biz_id`,
ADD COLUMN `retry`  tinyint NULL COMMENT '重试次数' AFTER `channel`;
UPDATE t_message set channel = 'aliyun' WHERE channel = '';