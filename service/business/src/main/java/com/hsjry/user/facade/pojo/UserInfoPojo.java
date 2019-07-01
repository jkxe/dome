/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.lang.common.base.enums.user.EnumUserType;

/**
 * 客户信息
 * @author hongsj
 * @version $Id: UserInfoPojo.java, v 1.0 2017年3月23日 下午3:52:16 hongsj Exp $
 */
public class UserInfoPojo implements Serializable {

    private static final long   serialVersionUID = 1260189689140939047L;
    /**
     * 客户Id
     */
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String              userId;
    /**
     * 客户类型
     */
    @NotNull(errorCode = "000001", message = "客户类型")
    private EnumUserType        clientCategory;
    /**
     * 客户名称
     */
    @NotNull(errorCode = "000001", message = "客户名称")
    @NotBlank(errorCode = "000001", message = "客户名称")
    private String              clientName;
    /**
     * 证件号码
     */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              idNo;
    /**
     * 证件类型
     */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind idKind;

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
     * Getter method for property <tt>clientCategory</tt>.
     * 
     * @return property value of clientCategory
     */
    public EnumUserType getClientCategory() {
        return clientCategory;
    }

    /**
     * Setter method for property <tt>clientCategory</tt>.
     * 
     * @param clientCategory value to be assigned to property clientCategory
     */
    public void setClientCategory(EnumUserType clientCategory) {
        this.clientCategory = clientCategory;
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
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Getter method for property <tt>idKind</tt>.
     * 
     * @return property value of idKind
     */
    public EnumCertificateKind getIdKind() {
        return idKind;
    }

    /**
     * Setter method for property <tt>idKind</tt>.
     * 
     * @param idKind value to be assigned to property idKind
     */
    public void setIdKind(EnumCertificateKind idKind) {
        this.idKind = idKind;
    }

}
