package com.cbwleft.sms.dao.model;

import java.io.Serializable;
import java.util.Date;

public class Template implements Serializable {
    private Short id;

    /**
     * 第三方渠道模板
     *
     * @mbg.generated
     */
    private String channelTemplateNo;

    /**
     * 模板名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 短信类型
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 短信模板
     *
     * @mbg.generated
     */
    private String template;

    /**
     * 短信验证码字段
     *
     * @mbg.generated
     */
    private String validateCodeKey;

    /**
     * 短信验证码有效时间（秒）
     *
     * @mbg.generated
     */
    private Short validateCodeExpire;

    /**
     * 应用id
     *
     * @mbg.generated
     */
    private Byte appId;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getChannelTemplateNo() {
        return channelTemplateNo;
    }

    public void setChannelTemplateNo(String channelTemplateNo) {
        this.channelTemplateNo = channelTemplateNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getValidateCodeKey() {
        return validateCodeKey;
    }

    public void setValidateCodeKey(String validateCodeKey) {
        this.validateCodeKey = validateCodeKey;
    }

    public Short getValidateCodeExpire() {
        return validateCodeExpire;
    }

    public void setValidateCodeExpire(Short validateCodeExpire) {
        this.validateCodeExpire = validateCodeExpire;
    }

    public Byte getAppId() {
        return appId;
    }

    public void setAppId(Byte appId) {
        this.appId = appId;
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
        sb.append(", channelTemplateNo=").append(channelTemplateNo);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", template=").append(template);
        sb.append(", validateCodeKey=").append(validateCodeKey);
        sb.append(", validateCodeExpire=").append(validateCodeExpire);
        sb.append(", appId=").append(appId);
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
        Template other = (Template) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChannelTemplateNo() == null ? other.getChannelTemplateNo() == null : this.getChannelTemplateNo().equals(other.getChannelTemplateNo()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getTemplate() == null ? other.getTemplate() == null : this.getTemplate().equals(other.getTemplate()))
            && (this.getValidateCodeKey() == null ? other.getValidateCodeKey() == null : this.getValidateCodeKey().equals(other.getValidateCodeKey()))
            && (this.getValidateCodeExpire() == null ? other.getValidateCodeExpire() == null : this.getValidateCodeExpire().equals(other.getValidateCodeExpire()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getChannelTemplateNo() == null) ? 0 : getChannelTemplateNo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTemplate() == null) ? 0 : getTemplate().hashCode());
        result = prime * result + ((getValidateCodeKey() == null) ? 0 : getValidateCodeKey().hashCode());
        result = prime * result + ((getValidateCodeExpire() == null) ? 0 : getValidateCodeExpire().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }
}