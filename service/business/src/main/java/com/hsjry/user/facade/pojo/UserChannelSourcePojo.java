/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumChannelType;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 渠道来源返回pojo
 * @author hongsj
 * @version $Id: UserChannelSourcePojo.java, v 1.0 2017年3月21日 下午3:38:24 hongsj Exp $
 */
public class UserChannelSourcePojo implements Serializable {
    /**  */
    private static final long serialVersionUID = -448805262832933620L;
    /**
     * 渠道编号
     */
    private String            channelNo;
    /**
     * 租户Id
     */
    private String            tenantId;
    /**
     * 渠道名称
     */
    private String            channelName;
    /**
     * 渠道状态
     */
    private EnumObjectStatus  channelStatus;
    /**
     * 渠道类型
     */
    private EnumChannelType   channelType;

    /**
     * Getter method for property <tt>channelNo</tt>.
     * 
     * @return property value of channelNo
     */
    public String getChannelNo() {
        return channelNo;
    }

    /**
     * Setter method for property <tt>channelNo</tt>.
     * 
     * @param channelNo value to be assigned to property channelNo
     */
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    /**
     * Getter method for property <tt>tenantId</tt>.
     * 
     * @return property value of tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Setter method for property <tt>tenantId</tt>.
     * 
     * @param tenantId value to be assigned to property tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Getter method for property <tt>channelName</tt>.
     * 
     * @return property value of channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Setter method for property <tt>channelName</tt>.
     * 
     * @param channelName value to be assigned to property channelName
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * Getter method for property <tt>channelStatus</tt>.
     * 
     * @return property value of channelStatus
     */
    public EnumObjectStatus getChannelStatus() {
        return channelStatus;
    }

    /**
     * Setter method for property <tt>channelStatus</tt>.
     * 
     * @param channelStatus value to be assigned to property channelStatus
     */
    public void setChannelStatus(EnumObjectStatus channelStatus) {
        this.channelStatus = channelStatus;
    }

    /**
     * Getter method for property <tt>channelType</tt>.
     * 
     * @return property value of channelType
     */
    public EnumChannelType getChannelType() {
        return channelType;
    }

    /**
     * Setter method for property <tt>channelType</tt>.
     * 
     * @param channelType value to be assigned to property channelType
     */
    public void setChannelType(EnumChannelType channelType) {
        this.channelType = channelType;
    }

}
