/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMarketBasicInfoByUserIdResponse.java, v 1.0 2018年5月29日 下午8:08:25 zhengqy15963 Exp $
 */
public class QueryMarketBasicInfoByUserIdResponse implements Serializable {

    /**  */
    private static final long   serialVersionUID = 6603432689750139139L;
    /**渠道经理信息  */
    private MarketBasicInfoPojo marketBasicInfoPojo;

    /**
     * Getter method for property <tt>marketBasicInfoPojo</tt>.
     * 
     * @return property value of marketBasicInfoPojo
     */
    public MarketBasicInfoPojo getMarketBasicInfoPojo() {
        return marketBasicInfoPojo;
    }

    /**
     * Setter method for property <tt>marketBasicInfoPojo</tt>.
     * 
     * @param marketBasicInfoPojo value to be assigned to property marketBasicInfoPojo
     */
    public void setMarketBasicInfoPojo(MarketBasicInfoPojo marketBasicInfoPojo) {
        this.marketBasicInfoPojo = marketBasicInfoPojo;
    }

}
