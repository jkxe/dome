/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMarketInfoByMerchantUserIdOrChannelManagerNoRequest.java, v 1.0 2018年6月1日 上午10:40:45 zhengqy15963 Exp $
 */
public class QueryMarketInfoByMarketUserIdOrChannelManagerNoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 7831723175664207868L;
    /**渠道经理userId  */
    private String            marketUserId;
    /**渠道经理工号  */
    private String            channelManagerNo;

    /**
     * Getter method for property <tt>marketUserId</tt>.
     * 
     * @return property value of marketUserId
     */
    public String getMarketUserId() {
        return marketUserId;
    }

    /**
     * Setter method for property <tt>marketUserId</tt>.
     * 
     * @param marketUserId value to be assigned to property marketUserId
     */
    public void setMarketUserId(String marketUserId) {
        this.marketUserId = marketUserId;
    }

    /**
     * Getter method for property <tt>channelManagerNo</tt>.
     * 
     * @return property value of channelManagerNo
     */
    public String getChannelManagerNo() {
        return channelManagerNo;
    }

    /**
     * Setter method for property <tt>channelManagerNo</tt>.
     * 
     * @param channelManagerNo value to be assigned to property channelManagerNo
     */
    public void setChannelManagerNo(String channelManagerNo) {
        this.channelManagerNo = channelManagerNo;
    }

}
