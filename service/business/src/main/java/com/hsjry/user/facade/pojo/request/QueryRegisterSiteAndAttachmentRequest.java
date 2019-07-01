/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * 查询登记点表和登记附件接口入参
 * @author zhengqy15963
 * @version $Id: QueryRegisterSiteAndAttachmentRequest.java, v 1.0 2018年5月17日 下午4:17:53 zhengqy15963 Exp $
 */
public class QueryRegisterSiteAndAttachmentRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -2800770107460071666L;
    /**登记点表ID  */
    private String            registerId;

    /**
     * Getter method for property <tt>registerId</tt>.
     * 
     * @return property value of registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * Setter method for property <tt>registerId</tt>.
     * 
     * @param registerId value to be assigned to property registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

}
