/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author jiangjd12837
 * @version $Id: VerifySmsResponse.java, v 1.0 2017年6月8日 下午5:05:46 jiangjd12837 Exp $
 */
public class VerifySmsResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = 8987168296071600211L;
    private EnumBool          VerifySms;

    /**
     * Getter method for property <tt>verifySms</tt>.
     * 
     * @return property value of VerifySms
     */
    public EnumBool getVerifySms() {
        return VerifySms;
    }

    /**
     * Setter method for property <tt>verifySms</tt>.
     * 
     * @param VerifySms value to be assigned to property verifySms
     */
    public void setVerifySms(EnumBool verifySms) {
        VerifySms = verifySms;
    }
}
