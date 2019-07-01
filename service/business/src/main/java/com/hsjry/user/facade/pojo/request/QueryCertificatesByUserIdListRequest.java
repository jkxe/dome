/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCertificatesByUserIdList.java, v 1.0 2017年8月21日 下午7:54:01 jiangjd12837 Exp $
 */
public class QueryCertificatesByUserIdListRequest implements Serializable {

    /**  */
    private static final long   serialVersionUID = 5048739876781808563L;
    @NotNull(errorCode = "000001", message = "客户ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "客户ID列表")
    private List<String>        userIds;
    //证件号码
    private String              certificateNo;
    //证件项类型
    private EnumCertificateKind certificateKind;

    /**
     * Getter method for property <tt>userIds</tt>.
     * 
     * @return property value of userIds
     */
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Setter method for property <tt>userIds</tt>.
     * 
     * @param userIds value to be assigned to property userIds
     */
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * Getter method for property <tt>certificateNo</tt>.
     * 
     * @return property value of certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * Setter method for property <tt>certificateNo</tt>.
     * 
     * @param certificateNo value to be assigned to property certificateNo
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * Getter method for property <tt>certificateKind</tt>.
     * 
     * @return property value of certificateKind
     */
    public EnumCertificateKind getCertificateKind() {
        return certificateKind;
    }

    /**
     * Setter method for property <tt>certificateKind</tt>.
     * 
     * @param certificateKind value to be assigned to property certificateKind
     */
    public void setCertificateKind(EnumCertificateKind certificateKind) {
        this.certificateKind = certificateKind;
    }

}
