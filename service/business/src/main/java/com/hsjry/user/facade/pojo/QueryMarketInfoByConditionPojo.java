package com.hsjry.user.facade.pojo;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketInfoByConditionPojo.java, v 0.1 2018/8/10 19:47 zhengqy15963 Exp $$
 */
public class QueryMarketInfoByConditionPojo implements Serializable {
    private static final long serialVersionUID = -2720844472173654253L;
    /**
     * 渠道经理姓名
     */
    private String channelManagerName;
    /**
     * 渠道经理userId
     */
    private String marketUserId;
    /**
     * 渠道经理联系电话
     */
    private String channelManagerTelphone;
    /**
     * 渠道经理工号
     */
    private String channelManagerNo;
    /**
     * 渠道经理角色
     */
    private String channelManagerRole;
    /**
     * 所属机构名称
     */
    private String beLongToOrganName;
    /**
     * 启用停用状态
     */
    private EnumObjectStatus objectStatus;

    public String getChannelManagerName() {
        return channelManagerName;
    }

    public void setChannelManagerName(String channelManagerName) {
        this.channelManagerName = channelManagerName;
    }

    public String getMarketUserId() {
        return marketUserId;
    }

    public void setMarketUserId(String marketUserId) {
        this.marketUserId = marketUserId;
    }

    public String getChannelManagerTelphone() {
        return channelManagerTelphone;
    }

    public void setChannelManagerTelphone(String channelManagerTelphone) {
        this.channelManagerTelphone = channelManagerTelphone;
    }

    public String getChannelManagerNo() {
        return channelManagerNo;
    }

    public void setChannelManagerNo(String channelManagerNo) {
        this.channelManagerNo = channelManagerNo;
    }

    public String getChannelManagerRole() {
        return channelManagerRole;
    }

    public void setChannelManagerRole(String channelManagerRole) {
        this.channelManagerRole = channelManagerRole;
    }

    public String getBeLongToOrganName() {
        return beLongToOrganName;
    }

    public void setBeLongToOrganName(String beLongToOrganName) {
        this.beLongToOrganName = beLongToOrganName;
    }

    public EnumObjectStatus getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(EnumObjectStatus objectStatus) {
        this.objectStatus = objectStatus;
    }
}
