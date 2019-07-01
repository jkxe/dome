/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author huangbb
 * @version $Id: QueryUserAppInfoRequest.java, v 1.0 2017年4月20日 下午7:07:55 jiangjd12837 Exp $
 */
public class QueryUserAppInfoRequest implements Serializable {

    private static final long serialVersionUID = 4012239173079480949L;
    //通行证ID
    @NotNull(errorCode = "000001", message = "通行证ID")
    @NotBlank(errorCode = "000001", message = "通行证ID")
    private String            authId;
    //证件类型
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;


    /**
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
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
