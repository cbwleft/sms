package com.cbwleft.sms.dao.model;

import java.io.Serializable;
import java.util.Date;

public class App implements Serializable {
    private Byte id;

    /**
     * 应用名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 签名私钥
     *
     * @mbg.generated
     */
    private String privateKey;

    /**
     * 短信前缀
     *
     * @mbg.generated
     */
    private String prefix;

    /**
     * 系统生成的数字验证码长度
     *
     * @mbg.generated
     */
    private Byte validateCodeLength;

    /**
     * 第三方渠道SDK配置参数
     *
     * @mbg.generated
     */
    private String channelParams;

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

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Byte getValidateCodeLength() {
        return validateCodeLength;
    }

    public void setValidateCodeLength(Byte validateCodeLength) {
        this.validateCodeLength = validateCodeLength;
    }

    public String getChannelParams() {
        return channelParams;
    }

    public void setChannelParams(String channelParams) {
        this.channelParams = channelParams;
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