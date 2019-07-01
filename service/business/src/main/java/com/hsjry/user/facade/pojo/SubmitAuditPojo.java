/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

/**
 * 提交审核请求类
 * @author hongsj
 * @version $Id: SubmitAuditRequest.java, v 1.0 2017年3月20日 上午9:52:15 hongsj Exp $
 */
public class SubmitAuditPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -9179010729635370624L;
    /**
     * 客户角色Id
     */
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String            userId;
    /**
     * 客户角色Id
     */
    @NotNull(errorCode = "000001", message = "客户角色Id列表")
    @Size(min=1,errorCode="000002", message = "客户角色Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "客户角色ID列表错误")
    private List<String>      custRoleIds;

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
     * Getter method for property <tt>custRoleIds</tt>.
     * 
     * @return property value of custRoleIds
     */
    public List<String> getCustRoleIds() {
        return custRoleIds;
    }

    /**
     * Setter method for property <tt>custRoleIds</tt>.
     * 
     * @param custRoleIds value to be assigned to property custRoleIds
     */
    public void setCustRoleIds(List<String> custRoleIds) {
        this.custRoleIds = custRoleIds;
    }

}
