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

import com.hsjry.user.facade.pojo.UserOrganizationPojo;

/**
 * 添加或修改组织请求类
 * @author hongsj
 * @version $Id: AddOrModifyOrganRequest.java, v 1.0 2017年3月18日 上午9:41:56 hongsj Exp $
 */
public class AddOrModifyOrganRequest implements Serializable {
    /**  */
    private static final long          serialVersionUID = 8459605984556127555L;
    /**
     * 门店信息列表
     */
    @AssertValid(errorCode = "500010", message = "request,基础请求内部有空值!")
    private List<UserOrganizationPojo> userOrganizationPojoList;
    /**
     * 机构客户Id
     */
    @NotNull(errorCode = "000001", message = "机构客户Id")
    @NotBlank(errorCode = "000001", message = "机构客户Id")
    private String                     userId;

    /**
     * Getter method for property <tt>userOrganizationPojoList</tt>.
     * 
     * @return property value of userOrganizationPojoList
     */
    public List<UserOrganizationPojo> getUserOrganizationPojoList() {
        return userOrganizationPojoList;
    }

    /**
     * Setter method for property <tt>userOrganizationPojoList</tt>.
     * 
     * @param userOrganizationPojoList value to be assigned to property userOrganizationPojoList
     */
    public void setUserOrganizationPojoList(List<UserOrganizationPojo> userOrganizationPojoList) {
        this.userOrganizationPojoList = userOrganizationPojoList;
    }

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

}
