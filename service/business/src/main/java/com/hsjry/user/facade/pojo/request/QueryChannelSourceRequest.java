/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author huangbb
 * @version $Id: QueryChannelSourceRequest.java, v 1.0 2017年6月24日 下午2:20:18 huangbb Exp $
 */
public class QueryChannelSourceRequest implements Serializable{

    private static final long serialVersionUID = -2827986010621507200L;
    
    /**渠道编号*/
    @NotNull(errorCode = "000001", message = "渠道编号")
    @NotBlank(errorCode = "000001", message = "渠道编号")
    String channelNo;

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
