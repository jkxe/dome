/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

/**
 * 设置渠道状态请求类
 * @author hongsj
 * @version $Id: SetChannelStatusRequest.java, v 1.0 2017年3月15日 上午10:54:13 hongsj Exp $
 */
public class SetChannelStatusRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -5461058494001028383L;
    /**
     * 渠道编号
     */
    @NotNull(errorCode = "000001", message = "渠道编号")
    @NotBlank(errorCode = "000001", message = "渠道编号")
    private String            channelNo;
    /**
     * 渠道状态
     */
    @NotNull(errorCode = "000001", message = "渠道状态")
    private EnumObjectStatus  channelStatus;

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
