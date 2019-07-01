/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserInfoPojo;
import com.hsjry.user.facade.pojo.enums.EnumListStatus;
import com.hsjry.user.facade.pojo.enums.EnumListType;

import net.sf.oval.constraint.AssertValid;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 添加特殊名单请求类
 * @author hongsj
 * @version $Id: AddSpecialListRequest.java, v 1.0 2017年3月28日 上午9:59:37 hongsj Exp $
 */
public class AddSpecialListRequest implements Serializable {
    /**  */
    private static final long  serialVersionUID = 4389488099578635573L;
    /**名单状态*/
    @NotNull(errorCode = "000001", message = "名单状态")
    private EnumListStatus     listStatus;
    /**名单类型*/
    @NotNull(errorCode = "000001", message = "名单类型")
    private EnumListType       listType;
    /**客户信息列表*/
    @AssertValid(errorCode = "500010", message = "客户信息")
    private List<UserInfoPojo> userInfoPojoList;
    /**操作人客户Id*/
    @NotNull(errorCode = "000001", message = "操作人客户Id")
    @NotBlank(errorCode = "000001", message = "操作人客户Id")
    private String             acuserId;
    /**加入理由*/
    @NotNull(errorCode = "000001", message = "加入理由")
    @NotBlank(errorCode = "000001", message = "加入理由")
    private String             reason;
    /**信息来源*/
    @NotNull(errorCode = "000001", message = "信息来源")
    @NotBlank(errorCode = "000001", message = "信息来源")
    private String             listSource;

    /**
     * Getter method for property <tt>listStatus</tt>.
     * 
     * @return property value of listStatus
     */
    public EnumListStatus getListStatus() {
        return listStatus;
    }

    /**
     * Setter method for property <tt>listStatus</tt>.
     * 
     * @param listStatus value to be assigned to property listStatus
     */
    public void setListStatus(EnumListStatus listStatus) {
        this.listStatus = listStatus;
    }

    /**
     * Getter method for property <tt>listType</tt>.
     * 
     * @return property value of listType
     */
    public EnumListType getListType() {
        return listType;
    }

    /**
     * Setter method for property <tt>listType</tt>.
     * 
     * @param listType value to be assigned to property listType
     */
    public void setListType(EnumListType listType) {
        this.listType = listType;
    }

    /**
     * Getter method for property <tt>userInfoPojoList</tt>.
     * 
     * @return property value of userInfoPojoList
     */
    public List<UserInfoPojo> getUserInfoPojoList() {
        return userInfoPojoList;
    }

    /**
     * Setter method for property <tt>userInfoPojoList</tt>.
     * 
     * @param userInfoPojoList value to be assigned to property userInfoPojoList
     */
    public void setUserInfoPojoList(List<UserInfoPojo> userInfoPojoList) {
        this.userInfoPojoList = userInfoPojoList;
    }

    /**
     * Getter method for property <tt>acuserId</tt>.
     * 
     * @return property value of acuserId
     */
    public String getAcuserId() {
        return acuserId;
    }

    /**
     * Setter method for property <tt>acuserId</tt>.
     * 
     * @param acuserId value to be assigned to property acuserId
     */
    public void setAcuserId(String acuserId) {
        this.acuserId = acuserId;
    }

    /**
     * Getter method for property <tt>reason</tt>.
     * 
     * @return property value of reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter method for property <tt>reason</tt>.
     * 
     * @param reason value to be assigned to property reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Getter method for property <tt>listSource</tt>.
     * 
     * @return property value of listSource
     */
    public String getListSource() {
        return listSource;
    }

    /**
     * Setter method for property <tt>listSource</tt>.
     * 
     * @param listSource value to be assigned to property listSource
     */
    public void setListSource(String listSource) {
        this.listSource = listSource;
    }

}
