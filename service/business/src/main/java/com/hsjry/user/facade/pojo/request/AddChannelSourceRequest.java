/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumChannelType;

/**
 * 新增渠道状态请求类
 * @author hongsj
 * @version $Id: AddChannelSourceRequest.java, v 1.0 2017年3月15日 上午10:15:14 hongsj Exp $
 */
public class AddChannelSourceRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 4165053140277481052L;
    /**
     * 渠道名称
     */
    @NotNull(errorCode = "000001", message = "渠道名称")
    @Length(errorCode = "000002", max = 32, message = "渠道名称")
    @NotBlank(errorCode = "000001", message = "渠道名称")
    private String            channelName;
    /**
     * 渠道类型
     */
    @NotNull(errorCode = "000001", message = "渠道类型")
    private EnumChannelType   channelType;

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
