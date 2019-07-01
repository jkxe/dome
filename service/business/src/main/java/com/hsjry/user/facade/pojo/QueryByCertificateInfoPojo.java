/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryByCertificateInfoPojo.java, v 1.0 2018年6月6日 下午3:41:26 zhengqy15963 Exp $
 */
public class QueryByCertificateInfoPojo implements Serializable {

    /**  */
    private static final long   serialVersionUID = 8816882422695550564L;
    /**证件类型  */
    @NotNull(errorCode = "000001", message = "证件类型")
    private EnumCertificateKind certificateKind;
    /**证件号码  */
    @NotNull(errorCode = "000001", message = "证件号码")
    @NotBlank(errorCode = "000001", message = "证件号码")
    private String              certificateNo;

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

}
