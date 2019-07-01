/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author huangbb
 * @version $Id: PasswordHasSetResponse.java, v 1.0 2017年7月4日 下午2:33:01 huangbb Exp $
 */
public class PasswordHasSetResponse implements Serializable {

    private static final long serialVersionUID = 3237011580851973811L;

    /**是否存在该密码*/
    private EnumBool bool;

    /**
     * Getter method for property <tt>bool</tt>.
     * 
     * @return property value of bool
     */
    public EnumBool getBool() {
        return bool;
    }

    /**
     * Setter method for property <tt>bool</tt>.
     * 
     * @param bool value to be assigned to property bool
     */
    public void setBool(EnumBool bool) {
        this.bool = bool;
    }
    

}
