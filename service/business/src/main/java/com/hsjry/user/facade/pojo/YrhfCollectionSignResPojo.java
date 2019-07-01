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
 * @version $Id: YrhfCollectionSignResponse.java, v 1.0 2017年5月15日 下午4:01:41 jiangjd12837 Exp $
 */
public class YrhfCollectionSignResPojo implements Serializable {
    /**  */
    private static final long     serialVersionUID = 2780631620327505389L;
    /**
     * C 交易状态
     */
    private EnumTransactionStatus status;
    /**
     * C 返回参数
     */
    private String                view;
    /**
     * C 返回码
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
    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of respCode
     */
    public String getRespCode() {
        return respCode;
    }
    /**
     * Setter method for property <tt>view</tt>.
     *
     * @param respCode value to be assigned to property view
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of respMsg
     */
    public String getRespMsg() {
        return respMsg;
    }
    /**
     * Setter method for property <tt>view</tt>.
     *
     * @param respMsg value to be assigned to property view
     */
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
