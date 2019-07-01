/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumTelephoneClassCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryOtherUserBySameTelTypeRequest.java, v 1.0 2017年6月30日 下午4:36:29 jiangjd12837 Exp $
 */
public class QueryOtherUserBySameTelTypeRequest implements Serializable {

    /**  */
    private static final long      serialVersionUID = -2608598298009707486L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                 userId;
    //电话联系点类型
    @NotNull(errorCode = "000001", message = "电话联系点类型")
    private EnumTelephoneClassCode telephoneClassCode;

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
     * Getter method for property <tt>telephoneClassCode</tt>.
     * 
     * @return property value of telephoneClassCode
     */
    public EnumTelephoneClassCode getTelephoneClassCode() {
        return telephoneClassCode;
    }

    /**
     * Setter method for property <tt>telephoneClassCode</tt>.
     * 
     * @param telephoneClassCode value to be assigned to property telephoneClassCode
     */
    public void setTelephoneClassCode(EnumTelephoneClassCode telephoneClassCode) {
        this.telephoneClassCode = telephoneClassCode;
    }

}
