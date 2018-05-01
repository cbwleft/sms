# SMS微服务

### 项目介绍
基于Spring Boot 2.0.1.RELEASE，阿里云通讯实现的短信微服务。
除了支持发送普通短信以外，封装了短信验证码发送、验证码校验这种常见的应用场景。

### 为什么需要短信微服务
想象一下常规的开发流程：每个需要发送短信的应用需要单独接入短信SDK，上线以后用户反馈收不到短信验证码，此时你需要先去查应用日志短信接口有没有调用成功，然后再去第三方短信平台查询运营商短信发送反馈；或者是第三方短信平台提示你需要升级SDK；或者老板因为成本考虑需要切换更便宜更稳定的短信平台。<br>
微服务的存在就是为了解决此类问题，如果您正在尝试微服务拆分，短信这类业务无关的基础服务也是一个很好的起点。<br>
该服务支持多个应用接入，并提供了短信发送记录查询功能 。因为较好的抽象，可以方便二次开发进行类型（接入应用，短信类型、短信模板）、成功率、手机号、时间等维度的统计。

### 演示地址
[短信微服务演示地址](http://47.98.111.179)<br>
演示账号:sms<br>
演示密码:sms<br>

### 如何接入
如果您正在使用[Spring Cloud](https://projects.spring.io/spring-cloud/ "spring cloud")，只需要在sms工程的pom中增加spring-cloud-dependencies、spring-cloud-starter-eureka的依赖。<br>
如果您没有使用Spring Cloud，Spring Boot可以很方便地作为单体应用或者集群部署，配合nginx等反向代理进行远程调用使用。<br>
**如果您没有使用阿里云通讯**，该工程将对短信SDK的依赖限制在sms工程的AliSMSServiceImpl类中，您只需要通过实现IChannelSMSService接口就可以很方便地进行短信平台的替换。

### 技术选型
1. Spring Boot 2.0.1.RELEASE
2. Hibernate Validator 6.0.9.Final
3. Mybatis 3.4.5
4. MyBatis 通用 Mapper + 分页插件 PageHelper
5. (可选)Guns 2.5 fork版本（mybatis-plus替换为第4条）

### 项目结构
```
sms-parent
├── sms-web -- spring boot启动入口
├── sms-dal -- 数据持久层
├── sms-mgr -- 管理后台(开发中)
```
### 项目测试
1. 运行sms.sql（需要mysql 5.6以上）<br>
2. 修改application.yml中的数据库地址<br>
3. 运行sms工程，访问[http://localhost:8000/swagger-ui.html#/](http://localhost:8000/swagger-ui.html#/)进行接口测试<br>
4. (可选)目前管理后台基于guns开发，[源代码地址](https://github.com/cbwleft/guns/tree/sms/master)sms/master分支

### 开发计划
```
+ 管理后台业务与guns解耦
+ 接口鉴权
+ docker构建支持
+ 群发短信支持
+ 其它短信渠道对接
```