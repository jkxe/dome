/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryRoleGroupingByuserIdListPojo.java, v 1.0 2017年7月7日 上午10:50:59 jiangjd12837 Exp $
 */
public class QueryRoleGroupingByuserIdListPojo implements Serializable {

    /**  */
    private static final long          serialVersionUID = 1345803899378156769L;
    //客户ID
    private String                     userId;
    //客户角色信息
    private List<UserCustomerRolePojo> userCustomerRolePojoList;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userCustomerRolePojoList</tt>.
     * 
     * @return property value of userCustomerRolePojoList
     */
    public List<UserCustomerRolePojo> getUserCustomerRolePojoList() {
        return userCustomerRolePojoList;
    }

    /**
     * Setter method for property <tt>userCustomerRolePojoList</tt>.
     * 
     * @param userCustomerRolePojoList value to be assigned to property userCustomerRolePojoList
     */
    public void setUserCustomerRolePojoList(List<UserCustomerRolePojo> userCustomerRolePojoList) {
        this.userCustomerRolePojoList = userCustomerRolePojoList;
    }

}
