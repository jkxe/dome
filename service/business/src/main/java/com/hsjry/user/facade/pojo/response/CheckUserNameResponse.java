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
 * @version $Id: CheckUserNameResponse.java, v 1.0 2017年4月13日 上午11:04:32 jiangjd12837 Exp $
 */
public class CheckUserNameResponse implements Serializable {

    /**  */
    private static final long serialVersionUID = -5080299467109840949L;

    private EnumBool          existenceSign;

    /**
     * Getter method for property <tt>existenceSign</tt>.
     * 
     * @return property value of existenceSign
     */
    public EnumBool getExistenceSign() {
        return existenceSign;
    }

    /**
     * Setter method for property <tt>existenceSign</tt>.
     * 
     * @param existenceSign value to be assigned to property existenceSign
     */
    public void setExistenceSign(EnumBool existenceSign) {
        this.existenceSign = existenceSign;
    }

}
