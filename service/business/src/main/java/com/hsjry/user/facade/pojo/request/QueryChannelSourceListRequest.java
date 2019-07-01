/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumChannelType;

/**
 * 查询渠道列表请求类
 * @author hongsj
 * @version $Id: QueryChannelSourceListRequest.java, v 1.0 2017年3月28日 下午1:42:36 hongsj Exp $
 */
public class QueryChannelSourceListRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = 228665613939409056L;
    /**渠道类型*/
    private EnumChannelType   channelType;

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
