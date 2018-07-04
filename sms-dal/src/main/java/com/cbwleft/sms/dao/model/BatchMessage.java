package com.cbwleft.sms.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_batch_message")
public class BatchMessage {
    /**
     * 批量消息id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 发送总量
     */
    private Short total;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     */
    @Column(name = "send_status")
    private Byte sendStatus;

    /**
     * 短信通道id
     */
    @Column(name = "biz_id")
    private String bizId;

    /**
     * 短信平台发送失败代码
     */
    @Column(name = "fail_code")
    private String failCode;

    /**
     * 发送成功数
     */
    private Short success;

    /**
     * 发送失败数
     */
    private Short failure;

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
     * 获取批量消息id
     *
     * @return id - 批量消息id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置批量消息id
     *
     * @param id 批量消息id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发送总量
     *
     * @return total - 发送总量
     */
    public Short getTotal() {
        return total;
    }

    /**
     * 设置发送总量
     *
     * @param total 发送总量
     */
    public void setTotal(Short total) {
        this.total = total;
    }

    /**
     * 获取发送内容
     *
     * @return content - 发送内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置发送内容
     *
     * @param content 发送内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     *
     * @return send_status - 发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     */
    public Byte getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     *
     * @param sendStatus 发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     */
    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 获取短信通道id
     *
     * @return biz_id - 短信通道id
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 设置短信通道id
     *
     * @param bizId 短信通道id
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
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
     * 获取发送成功数
     *
     * @return success - 发送成功数
     */
    public Short getSuccess() {
        return success;
    }

    /**
     * 设置发送成功数
     *
     * @param success 发送成功数
     */
    public void setSuccess(Short success) {
        this.success = success;
    }

    /**
     * 获取发送失败数
     *
     * @return failure - 发送失败数
     */
    public Short getFailure() {
        return failure;
    }

    /**
     * 设置发送失败数
     *
     * @param failure 发送失败数
     */
    public void setFailure(Short failure) {
        this.failure = failure;
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
        sb.append(", total=").append(total);
        sb.append(", content=").append(content);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", bizId=").append(bizId);
        sb.append(", failCode=").append(failCode);
        sb.append(", success=").append(success);
        sb.append(", failure=").append(failure);
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
        BatchMessage other = (BatchMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getSendStatus() == null ? other.getSendStatus() == null : this.getSendStatus().equals(other.getSendStatus()))
            && (this.getBizId() == null ? other.getBizId() == null : this.getBizId().equals(other.getBizId()))
            && (this.getFailCode() == null ? other.getFailCode() == null : this.getFailCode().equals(other.getFailCode()))
            && (this.getSuccess() == null ? other.getSuccess() == null : this.getSuccess().equals(other.getSuccess()))
            && (this.getFailure() == null ? other.getFailure() == null : this.getFailure().equals(other.getFailure()))
            && (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getSendStatus() == null) ? 0 : getSendStatus().hashCode());
        result = prime * result + ((getBizId() == null) ? 0 : getBizId().hashCode());
        result = prime * result + ((getFailCode() == null) ? 0 : getFailCode().hashCode());
        result = prime * result + ((getSuccess() == null) ? 0 : getSuccess().hashCode());
        result = prime * result + ((getFailure() == null) ? 0 : getFailure().hashCode());
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }
}