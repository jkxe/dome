/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumSystemRoleType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除客户系统角色
 * @author hongsj
 * @version $Id: DelUserSystemRoleRequest.java, v 1.0 2017年3月20日 上午10:27:13 hongsj Exp $
 */
public class DelUserSystemRoleRequest implements Serializable {
    /**  */
    private static final long  serialVersionUID = -3647055029814265579L;
    /**
     * 用户Id
     */
    @NotNull(errorCode = "000001", message = "用户Id")
    @NotBlank(errorCode = "000001", message = "用户Id")
    private String             userId;
    /**
     * 系统角色类型
     */
    @NotNull(errorCode = "000001", message = "系统角色类型")
    @NotBlank(errorCode = "000001", message = "系统角色类型")
    private EnumSystemRoleType systemRoleType;

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
     * Getter method for property <tt>systemRoleType</tt>.
     * 
     * @return property value of systemRoleType
     */
    public EnumSystemRoleType getSystemRoleType() {
        return systemRoleType;
    }

    /**
     * Setter method for property <tt>systemRoleType</tt>.
     * 
     * @param systemRoleType value to be assigned to property systemRoleType
     */
    public void setSystemRoleType(EnumSystemRoleType systemRoleType) {
        this.systemRoleType = systemRoleType;
    }

}
