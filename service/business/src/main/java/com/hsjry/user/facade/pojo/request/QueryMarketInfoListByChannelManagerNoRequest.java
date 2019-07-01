/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $Id: QueryMarketInfoListByChannelManagerNoRequest.java, v 1.0 2018年6月13日 上午11:34:10 zhengqy15963 Exp $
 */
public class QueryMarketInfoListByChannelManagerNoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 5853582329927443245L;
    /**
     * 渠道经理工号
     */
    private String channelManagerNo;
    /**
     * 渠道经理姓名
     */
    private String userName;
    /**
     * 启用停用状态
     */
    private String enableStatus;

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

    /**
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     *
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
