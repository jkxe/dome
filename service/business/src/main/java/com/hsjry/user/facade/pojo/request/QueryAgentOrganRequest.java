/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;

/**
 * 查询代理人组织请求类
 * @author hongsj
 * @version $Id: QueryAgentOrganRequest.java, v 1.0 2017年4月18日 下午2:30:23 hongsj Exp $
 */
public class QueryAgentOrganRequest implements Serializable {
    /**  */
    private static final long        serialVersionUID = 1L;
    /** 系统角色枚举列表 */
    @AssertValid(errorCode = "500010", message = "request,基础请求内部有空值!")
    private List<EnumSystemRoleType> systemRoleList;
    /** 客户id */
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String                   organCustomerId;

    /**
     * Getter method for property <tt>systemRoleList</tt>.
     * 
     * @return property value of systemRoleList
     */
    public List<EnumSystemRoleType> getSystemRoleList() {
        return systemRoleList;
    }

    /**
     * Setter method for property <tt>systemRoleList</tt>.
     * 
     * @param systemRoleList value to be assigned to property systemRoleList
     */
    public void setSystemRoleList(List<EnumSystemRoleType> systemRoleList) {
        this.systemRoleList = systemRoleList;
    }

    /**
     * Getter method for property <tt>organCustomerId</tt>.
     * 
     * @return property value of organCustomerId
     */
    public String getOrganCustomerId() {
        return organCustomerId;
    }

    /**
     * Setter method for property <tt>organCustomerId</tt>.
     * 
     * @param organCustomerId value to be assigned to property organCustomerId
     */
    public void setOrganCustomerId(String organCustomerId) {
        this.organCustomerId = organCustomerId;
    }

}
