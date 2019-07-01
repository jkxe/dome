/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author zhengqy15963
 * @version $Id: IsBindCardResponse.java, v 1.0 2018年4月17日 上午10:37:42 zhengqy15963 Exp $
 */
public class IsBindCardResponse implements Serializable{

    
    private static final long serialVersionUID = -7743628298127051196L;

    private EnumBool          checkResult;

    /**
     * Getter method for property <tt>checkResult</tt>.
     * 
     * @return property value of checkResult
     */
    public EnumBool getCheckResult() {
        return checkResult;
    }

    /**
     * Setter method for property <tt>checkResult</tt>.
     * 
     * @param checkResult value to be assigned to property checkResult
     */
    public void setCheckResult(EnumBool checkResult) {
        this.checkResult = checkResult;
    }

}
