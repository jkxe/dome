/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.MatchPattern;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageUserListRequest.java, v 1.0 2017年6月15日 上午10:26:43 jiangjd12837 Exp $
 */
public class QueryManageUserListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -3729930656145680189L;
    //客户名称
    private String            clientName;
    //客户账号
    private String            clientAccount;
    //手机号
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String            mobileTel;
    //要排除的客户ID列表
    private List<String>      userIds;

    /**
    * Getter method for property <tt>userIds</tt>.
    * 
    * @return property value of userIds
    */
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Setter method for property <tt>userIds</tt>.
     * 
     * @param userIds value to be assigned to property userIds
     */
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
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
