/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryGroupMemberPage.java, v 1.0 2017年6月16日 下午4:52:44 jiangjd12837 Exp $
 */
public class QueryGroupMemberPageRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 9001512651095967525L;
    //组织ID
    private String            organId;
    //客户名称
    private String            clientName;
    //客户账号
    private String            clientAccount;
    //手机号
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String            mobileTel;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Getter method for property <tt>clientAccount</tt>.
     * 
     * @return property value of clientAccount
     */
    public String getClientAccount() {
        return clientAccount;
    }

    /**
     * Setter method for property <tt>clientAccount</tt>.
     * 
     * @param clientAccount value to be assigned to property clientAccount
     */
    public void setClientAccount(String clientAccount) {
        this.clientAccount = clientAccount;
    }

    /**
     * Getter method for property <tt>mobileTel</tt>.
     * 
     * @return property value of mobileTel
     */
    public String getMobileTel() {
        return mobileTel;
    }

    /**
     * Setter method for property <tt>mobileTel</tt>.
     * 
     * @param mobileTel value to be assigned to property mobileTel
     */
    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

}
