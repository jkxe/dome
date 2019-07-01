/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMerchantAuthorizedInfoResponse.java, v 1.0 2018年7月4日 下午3:03:07 zhengqy15963 Exp $
 */
public class QueryMerchantAuthorizedInfoResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 4044007582677304651L;
    /**授权代理人联系方式  */
    private String            authorizedTel;
    /**授权代理人姓名  */
    private String            authorizedName;
    /**授权代理人userid  */
    private String             liableUserId;

    /**
     * Getter method for property <tt>authorizedTel</tt>.
     * 
     * @return property value of authorizedTel
     */
    public String getAuthorizedTel() {
        return authorizedTel;
    }

    /**
     * Setter method for property <tt>authorizedTel</tt>.
     * 
     * @param authorizedTel value to be assigned to property authorizedTel
     */
    public void setAuthorizedTel(String authorizedTel) {
        this.authorizedTel = authorizedTel;
    }

    /**
     * Getter method for property <tt>authorizedName</tt>.
     * 
     * @return property value of authorizedName
     */
    public String getAuthorizedName() {
        return authorizedName;
    }

    /**
     * Setter method for property <tt>authorizedName</tt>.
     * 
     * @param authorizedName value to be assigned to property authorizedName
     */
    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }

    /**
     * Getter method for property <tt>liableUserId</tt>.
     * 
     * @return property value of liableUserId
     */
    public String getLiableUserId() {
        return liableUserId;
    }

    /**
     * Setter method for property <tt>liableUserId</tt>.
     * 
     * @param liableUserId value to be assigned to property liableUserId
     */
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

}
