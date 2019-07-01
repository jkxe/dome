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
 * @version $Id: YrhfQuerySignStatusResponse.java, v 1.0 2017年5月15日 下午4:20:00 jiangjd12837 Exp $
 */
public class YrhfQuerySignStatusResponse implements Serializable {
    /**  */
    private static final long     serialVersionUID = -1788998255089515826L;
    /**
     * C 状态
     */
    private EnumTransactionStatus status;
    /**
     * C 失败原因
     */
    private String                failCause;
    /**
     * C 合同编号
     */
    private String                signNo;

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
     * Getter method for property <tt>failCause</tt>.
     * 
     * @return property value of failCause
     */
    public String getFailCause() {
        return failCause;
    }

    /**
     * Setter method for property <tt>failCause</tt>.
     * 
     * @param failCause value to be assigned to property failCause
     */
    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    /**
     * Getter method for property <tt>signNo</tt>.
     * 
     * @return property value of signNo
     */
    public String getSignNo() {
        return signNo;
    }

    /**
     * Setter method for property <tt>signNo</tt>.
     * 
     * @param signNo value to be assigned to property signNo
     */
    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

}
