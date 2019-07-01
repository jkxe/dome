/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.OrganizationPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManagementRoleInfoResponse.java, v 1.0 2017年8月17日 下午2:32:25 jiangjd12837 Exp $
 */
public class QueryManagementRoleInfoResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 8215086740183643422L;
    //组织信息
    private OrganizationPojo  organizationPojo;
    //角色名称列表
    private List<String>      roleNameList;

    /**
     * Getter method for property <tt>organizationPojo</tt>.
     * 
     * @return property value of organizationPojo
     */
    public OrganizationPojo getOrganizationPojo() {
        return organizationPojo;
    }

    /**
     * Setter method for property <tt>organizationPojo</tt>.
     * 
     * @param organizationPojo value to be assigned to property organizationPojo
     */
    public void setOrganizationPojo(OrganizationPojo organizationPojo) {
        this.organizationPojo = organizationPojo;
    }

    /**
     * Getter method for property <tt>roleNameList</tt>.
     * 
     * @return property value of roleNameList
     */
    public List<String> getRoleNameList() {
        return roleNameList;
    }

    /**
     * Setter method for property <tt>roleNameList</tt>.
     * 
     * @param roleNameList value to be assigned to property roleNameList
     */
    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

}
