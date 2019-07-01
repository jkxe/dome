/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 获取客户角色请求类
 * @author hongsj
 * @version $Id: QueryCustomerRoleRequest.java, v 1.0 2017年3月27日 下午2:05:31 hongsj Exp $
 */
public class QueryCustomerRoleRequest implements Serializable {
    /**  */
    private static final long        serialVersionUID = 1305309389482219998L;
    /** 客户Id */
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String                   userId;
    /** 系统角色类型 */
    @NotNull(errorCode = "000001", message = "系统角色类型")
    private List<EnumSystemRoleType> systemRoleTypeList;

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
     * Getter method for property <tt>systemRoleTypeList</tt>.
     * 
     * @return property value of systemRoleTypeList
     */
    public List<EnumSystemRoleType> getSystemRoleTypeList() {
        return systemRoleTypeList;
    }

    /**
     * Setter method for property <tt>systemRoleTypeList</tt>.
     * 
     * @param systemRoleTypeList value to be assigned to property systemRoleTypeList
     */
    public void setSystemRoleTypeList(List<EnumSystemRoleType> systemRoleTypeList) {
        this.systemRoleTypeList = systemRoleTypeList;
    }

}
