/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumTransactionStatus;

/**
 * 
 * @author jiangjd12837
 * @version $Id: YrhfQuerySignStatusResponse.java, v 1.0 2017年5月15日 下午4:20:00 jiangjd12837 Exp $
 */
public class YrhfQuerySignStatusResPojo implements Serializable {
    /**  */
    private static final long     serialVersionUID = -5650774578867517477L;
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
     *  C 返回码
     */
    private String                respCode;
    /**
     *  C 返回说明
     */
    private String                respMsg;

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

    /**
     * Getter method for property <tt>signNo</tt>.
     *
     * @return property value of respCode
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * Setter method for property <tt>signNo</tt>.
     *
     * @param respCode value to be assigned to property signNo
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    /**
     * Getter method for property <tt>signNo</tt>.
     *
     * @return property value of respMsg
     */
    public String getRespMsg() {
        return respMsg;
    }

    /**
     * Setter method for property <tt>signNo</tt>.
     *
     * @param respMsg value to be assigned to property signNo
     */
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
