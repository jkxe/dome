/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumRegisterKind;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryChangeRecordListRequest.java, v 1.0 2018年5月17日 下午7:44:43 zhengqy15963 Exp $
 */
public class QueryChangeRecordListRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -5747531024786293624L;
    /**组织id  */
    private String            organId;
    /**客户角色id  */
    private String            custRoleId;
    /**登记点类型  */
    @NotNull(errorCode = "000001", message = "登记点类型")
    private EnumRegisterKind  registerKind;

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
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

    /**
     * Getter method for property <tt>registerKind</tt>.
     * 
     * @return property value of registerKind
     */
    public EnumRegisterKind getRegisterKind() {
        return registerKind;
    }

    /**
     * Setter method for property <tt>registerKind</tt>.
     * 
     * @param registerKind value to be assigned to property registerKind
     */
    public void setRegisterKind(EnumRegisterKind registerKind) {
        this.registerKind = registerKind;
    }

}
