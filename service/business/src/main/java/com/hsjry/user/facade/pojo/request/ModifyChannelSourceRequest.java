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
 * 修改渠道状态请求类
 * @author hongsj
 * @version $Id: ModifyChannelSourceRequest.java, v 1.0 2017年3月15日 上午10:43:25 hongsj Exp $
 */
public class ModifyChannelSourceRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -1043321270563201662L;
    /**
     * 渠道编号
     */
    @NotNull(errorCode = "000001", message = "渠道编号")
    @NotBlank(errorCode = "000001", message = "渠道编号")
    private String            channelNo;
    /**
     * 渠道名称
     */
    @Length(errorCode = "000002", max = 50, message = "渠道名称")
    private String            channelName;

    /**
     * 渠道类型
     */
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

}
