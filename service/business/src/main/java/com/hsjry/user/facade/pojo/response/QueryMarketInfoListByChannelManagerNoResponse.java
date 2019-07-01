/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.MarketBasicInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMarketInfoListByChannelManagerNoResponse.java, v 1.0 2018年6月13日 上午11:36:02 zhengqy15963 Exp $
 */
public class QueryMarketInfoListByChannelManagerNoResponse implements Serializable {

    /**  */
    private static final long         serialVersionUID = 2179319193791166910L;
    /**渠道经理信息列表  */
    private List<MarketBasicInfoPojo> marketManagers;

    /**
     * Getter method for property <tt>marketManagers</tt>.
     * 
     * @return property value of marketManagers
     */
    public List<MarketBasicInfoPojo> getMarketManagers() {
        return marketManagers;
    }

    /**
     * Setter method for property <tt>marketManagers</tt>.
     * 
     * @param marketManagers value to be assigned to property marketManagers
     */
    public void setMarketManagers(List<MarketBasicInfoPojo> marketManagers) {
        this.marketManagers = marketManagers;
    }

}
