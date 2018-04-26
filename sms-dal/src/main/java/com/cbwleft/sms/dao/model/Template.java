package com.cbwleft.sms.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_template")
public class Template {
    /**
     * 短信模板id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Short id;

    /**
     * 第三方渠道模板
     */
    @Column(name = "channel_template_no")
    private String channelTemplateNo;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 短信类型
     */
    private Byte type;

    /**
     * 短信模板
     */
    private String template;

    /**
     * 短信验证码字段
     */
    @Column(name = "validate_code_key")
    private String validateCodeKey;

    /**
     * 短信验证码有效时间（秒）
     */
    @Column(name = "validate_code_expire")
    private Short validateCodeExpire;

    /**
     * 应用id
     */
    @Column(name = "app_id")
    private Byte appId;

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
     * 获取短信模板id
     *
     * @return id - 短信模板id
     */
    public Short getId() {
        return id;
    }

    /**
     * 设置短信模板id
     *
     * @param id 短信模板id
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * 获取第三方渠道模板
     *
     * @return channel_template_no - 第三方渠道模板
     */
    public String getChannelTemplateNo() {
        return channelTemplateNo;
    }

    /**
     * 设置第三方渠道模板
     *
     * @param channelTemplateNo 第三方渠道模板
     */
    public void setChannelTemplateNo(String channelTemplateNo) {
        this.channelTemplateNo = channelTemplateNo;
    }

    /**
     * 获取模板名称
     *
     * @return name - 模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模板名称
     *
     * @param name 模板名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取短信类型
     *
     * @return type - 短信类型
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置短信类型
     *
     * @param type 短信类型
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取短信模板
     *
     * @return template - 短信模板
     */
    public String getTemplate() {
        return template;
    }

    /**
     * 设置短信模板
     *
     * @param template 短信模板
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * 获取短信验证码字段
     *
     * @return validate_code_key - 短信验证码字段
     */
    public String getValidateCodeKey() {
        return validateCodeKey;
    }

    /**
     * 设置短信验证码字段
     *
     * @param validateCodeKey 短信验证码字段
     */
    public void setValidateCodeKey(String validateCodeKey) {
        this.validateCodeKey = validateCodeKey;
    }

    /**
     * 获取短信验证码有效时间（秒）
     *
     * @return validate_code_expire - 短信验证码有效时间（秒）
     */
    public Short getValidateCodeExpire() {
        return validateCodeExpire;
    }

    /**
     * 设置短信验证码有效时间（秒）
     *
     * @param validateCodeExpire 短信验证码有效时间（秒）
     */
    public void setValidateCodeExpire(Short validateCodeExpire) {
        this.validateCodeExpire = validateCodeExpire;
    }

    /**
     * 获取应用id
     *
     * @return app_id - 应用id
     */
    public Byte getAppId() {
        return appId;
    }

    /**
     * 设置应用id
     *
     * @param appId 应用id
     */
    public void setAppId(Byte appId) {
        this.appId = appId;
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