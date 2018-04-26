package com.cbwleft.sms.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_app")
public class App {
    /**
     * 应用id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Byte id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 签名私钥
     */
    @Column(name = "private_key")
    private String privateKey;

    /**
     * 短信前缀
     */
    private String prefix;

    /**
     * 系统生成的数字验证码长度
     */
    @Column(name = "validate_code_length")
    private Byte validateCodeLength;

    /**
     * 第三方渠道SDK配置参数
     */
    @Column(name = "channel_params")
    private String channelParams;

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
     * 获取应用id
     *
     * @return id - 应用id
     */
    public Byte getId() {
        return id;
    }

    /**
     * 设置应用id
     *
     * @param id 应用id
     */
    public void setId(Byte id) {
        this.id = id;
    }

    /**
     * 获取应用名称
     *
     * @return name - 应用名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置应用名称
     *
     * @param name 应用名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取签名私钥
     *
     * @return private_key - 签名私钥
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * 设置签名私钥
     *
     * @param privateKey 签名私钥
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 获取短信前缀
     *
     * @return prefix - 短信前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置短信前缀
     *
     * @param prefix 短信前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取系统生成的数字验证码长度
     *
     * @return validate_code_length - 系统生成的数字验证码长度
     */
    public Byte getValidateCodeLength() {
        return validateCodeLength;
    }

    /**
     * 设置系统生成的数字验证码长度
     *
     * @param validateCodeLength 系统生成的数字验证码长度
     */
    public void setValidateCodeLength(Byte validateCodeLength) {
        this.validateCodeLength = validateCodeLength;
    }

    /**
     * 获取第三方渠道SDK配置参数
     *
     * @return channel_params - 第三方渠道SDK配置参数
     */
    public String getChannelParams() {
        return channelParams;
    }

    /**
     * 设置第三方渠道SDK配置参数
     *
     * @param channelParams 第三方渠道SDK配置参数
     */
    public void setChannelParams(String channelParams) {
        this.channelParams = channelParams;
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
        sb.append(", name=").append(name);
        sb.append(", privateKey=").append(privateKey);
        sb.append(", prefix=").append(prefix);
        sb.append(", validateCodeLength=").append(validateCodeLength);
        sb.append(", channelParams=").append(channelParams);
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
        App other = (App) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPrivateKey() == null ? other.getPrivateKey() == null : this.getPrivateKey().equals(other.getPrivateKey()))
            && (this.getPrefix() == null ? other.getPrefix() == null : this.getPrefix().equals(other.getPrefix()))
            && (this.getValidateCodeLength() == null ? other.getValidateCodeLength() == null : this.getValidateCodeLength().equals(other.getValidateCodeLength()))
            && (this.getChannelParams() == null ? other.getChannelParams() == null : this.getChannelParams().equals(other.getChannelParams()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPrivateKey() == null) ? 0 : getPrivateKey().hashCode());
        result = prime * result + ((getPrefix() == null) ? 0 : getPrefix().hashCode());
        result = prime * result + ((getValidateCodeLength() == null) ? 0 : getValidateCodeLength().hashCode());
        result = prime * result + ((getChannelParams() == null) ? 0 : getChannelParams().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }
}