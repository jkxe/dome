/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumTransactionStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfCollectionSignResponse.java, v 1.0 2017年5月15日 下午4:01:41 jiangjd12837 Exp $
 */
public class YrhfCollectionSignResponse implements Serializable {
    /**  */
    private static final long     serialVersionUID = 2956486514537712461L;
    /**
     * C 交易状态
     */
    private EnumTransactionStatus status;
    /**
     * C 返回参数
     */
    private String                view;

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public EnumTransactionStatus getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(EnumTransactionStatus status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>view</tt>.
     * 
     * @return property value of view
     */
    public String getView() {
        return view;
    }

    /**
     * Setter method for property <tt>view</tt>.
     * 
     * @param view value to be assigned to property view
     */
    public void setView(String view) {
        this.view = view;
    }

}
