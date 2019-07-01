/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryManageUserRequest.java, v 1.0 2017年4月28日 下午1:55:03 jiangjd12837 Exp $
 */
public class QueryManageUserRequest implements Serializable {

    /**  */
    private static final long          serialVersionUID = 1451481455427835032L;
    //客户名称
    private String                     clientName;
    //联系点类型
    private EnumContactStationTypeCode telephoneClassCode;
    //手机号
    @MatchPattern(matchAll = false, pattern = { "^0?(13|15|16|19|18|14|17)[0-9]{9}$" }, errorCode = "000003", message = "手机号")
    private String                     mobileTel;
    @NotNull(errorCode = "000001", message = "用户类型")
    private EnumUserType               userType;

    /**
     * Getter method for property <tt>userType</tt>.
     * 
     * @return property value of userType
     */
    public EnumUserType getUserType() {
        return userType;
    }

    /**
     * Setter method for property <tt>userType</tt>.
     * 
     * @param userType value to be assigned to property userType
     */
    public void setUserType(EnumUserType userType) {
        this.userType = userType;
    }

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Getter method for property <tt>telephoneClassCode</tt>.
     * 
     * @return property value of telephoneClassCode
     */
    public EnumContactStationTypeCode getTelephoneClassCode() {
        return telephoneClassCode;
    }

    /**
     * Setter method for property <tt>telephoneClassCode</tt>.
     * 
     * @param telephoneClassCode value to be assigned to property telephoneClassCode
     */
    public void setTelephoneClassCode(EnumContactStationTypeCode telephoneClassCode) {
        this.telephoneClassCode = telephoneClassCode;
    }

    /**
     * Getter method for property <tt>mobileTel</tt>.
     * 
     * @return property value of mobileTel
     */
    public String getMobileTel() {
        return mobileTel;
    }

    /**
     * Setter method for property <tt>mobileTel</tt>.
     * 
     * @param mobileTel value to be assigned to property mobileTel
     */
    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

}
