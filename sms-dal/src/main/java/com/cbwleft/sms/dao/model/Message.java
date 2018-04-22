package com.cbwleft.sms.dao.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    /**
     * 短信id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     * 短信参数
     *
     * @mbg.generated
     */
    private String params;

    /**
     * 短信系统生成的验证码
     *
     * @mbg.generated
     */
    private String validateCode;

    /**
     * 模板id
     *
     * @mbg.generated
     */
    private Short templateId;

    /**
     * 发送状态（0：失败，1：接口调用成功，2：发送成功）
     *
     * @mbg.generated
     */
    private Byte sendStatus;

    /**
     * 验证码验证状态（0：未验证，1：已验证）
     *
     * @mbg.generated
     */
    private Byte validateStatus;

    /**
     * 短信平台发送失败代码
     *
     * @mbg.generated
     */
    private String failCode;

    /**
     * 第三方平台短信查询id
     *
     * @mbg.generated
     */
    private String bizId;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Short getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Short templateId) {
        this.templateId = templateId;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Byte getValidateStatus() {
        return validateStatus;
    }

    public void setValidateStatus(Byte validateStatus) {
        this.validateStatus = validateStatus;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

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
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }
}