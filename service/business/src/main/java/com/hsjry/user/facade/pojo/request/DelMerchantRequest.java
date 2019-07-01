/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumMerchanRoleTypeList;

/**
 * 
 * @author zhengqy15963
 * @version $Id: DelMerchantRequest.java, v 1.0 2018年5月24日 下午6:30:11 zhengqy15963 Exp $
 */
public class DelMerchantRequest implements Serializable {

    /**  */
    private static final long       serialVersionUID = -1880960591963186660L;
    /**
     * 机构客户Id
     */
    @NotNull(errorCode = "000001", message = "机构客户Id")
    @NotBlank(errorCode = "000001", message = "机构客户Id")
    private String                  userId;
    /**
     * 系统角色类型
     */
    @NotNull(errorCode = "000001", message = "系统角色类型")
    @NotBlank(errorCode = "000001", message = "系统角色类型")
    private EnumMerchanRoleTypeList merchanRoleTypeList;

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
     * Getter method for property <tt>merchanRoleTypeList</tt>.
     * 
     * @return property value of merchanRoleTypeList
     */
    public EnumMerchanRoleTypeList getMerchanRoleTypeList() {
        return merchanRoleTypeList;
    }

    /**
     * Setter method for property <tt>merchanRoleTypeList</tt>.
     * 
     * @param merchanRoleTypeList value to be assigned to property merchanRoleTypeList
     */
    public void setMerchanRoleTypeList(EnumMerchanRoleTypeList merchanRoleTypeList) {
        this.merchanRoleTypeList = merchanRoleTypeList;
    }

}
