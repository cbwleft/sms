package com.cbwleft.sms.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_message")
public class Message {
    /**
     * 短信id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信参数
     */
    private String params;

    /**
     * 短信系统生成的验证码
     */
    @Column(name = "validate_code")
    private String validateCode;

    /**
     * 模板id
     */
    @Column(name = "template_id")
    private Short templateId;

    /**
     * 发送状态（0：失败，1：接口调用成功，2：发送成功）
     */
    @Column(name = "send_status")
    private Byte sendStatus;

    /**
     * 验证码验证状态（0：未验证，1：已验证）
     */
    @Column(name = "validate_status")
    private Byte validateStatus;

    /**
     * 短信平台发送失败代码
     */
    @Column(name = "fail_code")
    private String failCode;

    /**
     * 短信平台id
     */
    @Column(name = "biz_id")
    private String bizId;

    /**
     * 短信发送渠道
     */
    private String channel;

    /**
     * 重试次数
     */
    private Byte retry;

    /**
     * 收到短信时间
     */
    @Column(name = "receive_date")
    private Date receiveDate;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 获取短信id
     *
     * @return id - 短信id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置短信id
     *
     * @param id 短信id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取短信参数
     *
     * @return params - 短信参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置短信参数
     *
     * @param params 短信参数
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取短信系统生成的验证码
     *
     * @return validate_code - 短信系统生成的验证码
     */
    public String getValidateCode() {
        return validateCode;
    }

    /**
     * 设置短信系统生成的验证码
     *
     * @param validateCode 短信系统生成的验证码
     */
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    /**
     * 获取模板id
     *
     * @return template_id - 模板id
     */
    public Short getTemplateId() {
        return templateId;
    }

    /**
     * 设置模板id
     *
     * @param templateId 模板id
     */
    public void setTemplateId(Short templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取发送状态（0：失败，1：接口调用成功，2：发送成功）
     *
     * @return send_status - 发送状态（0：失败，1：接口调用成功，2：发送成功）
     */
    public Byte getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置发送状态（0：失败，1：接口调用成功，2：发送成功）
     *
     * @param sendStatus 发送状态（0：失败，1：接口调用成功，2：发送成功）
     */
    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 获取验证码验证状态（0：未验证，1：已验证）
     *
     * @return validate_status - 验证码验证状态（0：未验证，1：已验证）
     */
    public Byte getValidateStatus() {
        return validateStatus;
    }

    /**
     * 设置验证码验证状态（0：未验证，1：已验证）
     *
     * @param validateStatus 验证码验证状态（0：未验证，1：已验证）
     */
    public void setValidateStatus(Byte validateStatus) {
        this.validateStatus = validateStatus;
    }

    /**
     * 获取短信平台发送失败代码
     *
     * @return fail_code - 短信平台发送失败代码
     */
    public String getFailCode() {
        return failCode;
    }

    /**
     * 设置短信平台发送失败代码
     *
     * @param failCode 短信平台发送失败代码
     */
    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    /**
     * 获取短信平台id
     *
     * @return biz_id - 短信平台id
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 设置短信平台id
     *
     * @param bizId 短信平台id
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 获取短信发送渠道
     *
     * @return channel - 短信发送渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置短信发送渠道
     *
     * @param channel 短信发送渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取重试次数
     *
     * @return retry - 重试次数
     */
    public Byte getRetry() {
        return retry;
    }

    /**
     * 设置重试次数
     *
     * @param retry 重试次数
     */
    public void setRetry(Byte retry) {
        this.retry = retry;
    }

    /**
     * 获取收到短信时间
     *
     * @return receive_date - 收到短信时间
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 设置收到短信时间
     *
     * @param receiveDate 收到短信时间
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mobile=").append(mobile);
        sb.append(", params=").append(params);
        sb.append(", validateCode=").append(validateCode);
        sb.append(", templateId=").append(templateId);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", validateStatus=").append(validateStatus);
        sb.append(", failCode=").append(failCode);
        sb.append(", bizId=").append(bizId);
        sb.append(", channel=").append(channel);
        sb.append(", retry=").append(retry);
        sb.append(", receiveDate=").append(receiveDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Message other = (Message) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getValidateCode() == null ? other.getValidateCode() == null : this.getValidateCode().equals(other.getValidateCode()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getSendStatus() == null ? other.getSendStatus() == null : this.getSendStatus().equals(other.getSendStatus()))
            && (this.getValidateStatus() == null ? other.getValidateStatus() == null : this.getValidateStatus().equals(other.getValidateStatus()))
            && (this.getFailCode() == null ? other.getFailCode() == null : this.getFailCode().equals(other.getFailCode()))
            && (this.getBizId() == null ? other.getBizId() == null : this.getBizId().equals(other.getBizId()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
            && (this.getRetry() == null ? other.getRetry() == null : this.getRetry().equals(other.getRetry()))
            && (this.getReceiveDate() == null ? other.getReceiveDate() == null : this.getReceiveDate().equals(other.getReceiveDate()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getValidateCode() == null) ? 0 : getValidateCode().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getSendStatus() == null) ? 0 : getSendStatus().hashCode());
        result = prime * result + ((getValidateStatus() == null) ? 0 : getValidateStatus().hashCode());
        result = prime * result + ((getFailCode() == null) ? 0 : getFailCode().hashCode());
        result = prime * result + ((getBizId() == null) ? 0 : getBizId().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getRetry() == null) ? 0 : getRetry().hashCode());
        result = prime * result + ((getReceiveDate() == null) ? 0 : getReceiveDate().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }
}