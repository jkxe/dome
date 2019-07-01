/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: DisableManagerUserRequest.java, v 1.0 2017年5月11日 下午6:36:18 jiangjd12837 Exp $
 */
public class DisableManagerUserRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -2543603332789232681L;
    /**
     * 用户Id列表
     */
    @NotNull(errorCode = "000001", message = "用户Id列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "用户Id列表")
    private List<String>      userIdList;
    //状态
    @NotNull(errorCode = "000001", message = "状态")
    private EnumObjectStatus  idStatus;

    /**
     * Getter method for property <tt>idStatus</tt>.
     * 
     * @return property value of idStatus
     */
    public EnumObjectStatus getIdStatus() {
        return idStatus;
    }

    /**
     * Setter method for property <tt>idStatus</tt>.
     * 
     * @param idStatus value to be assigned to property idStatus
     */
    public void setIdStatus(EnumObjectStatus idStatus) {
        this.idStatus = idStatus;
    }

    /**
     * Getter method for property <tt>userIdList</tt>.
     * 
     * @return property value of userIdList
     */
    public List<String> getUserIdList() {
        return userIdList;
    }

    /**
     * Setter method for property <tt>userIdList</tt>.
     * 
     * @param userIdList value to be assigned to property userIdList
     */
    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

}
