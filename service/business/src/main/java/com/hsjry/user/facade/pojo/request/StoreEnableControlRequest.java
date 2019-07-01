/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author zhengqy15963
 * @version $Id: StoreEnableControlRequest.java, v 1.0 2018年5月11日 下午7:26:15 zhengqy15963 Exp $
 */
public class StoreEnableControlRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 6311987504521672255L;
    /**门店组织id  */
    @NotNull(errorCode = "000001", message = "门店组织id")
    @NotBlank(errorCode = "000001", message = "门店组织id")
    private String            organId;
    /**启用停用标志  */
    @NotNull(errorCode = "000001", message = "启用停用标志")
    private Boolean           flag;

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

    /**
     * Getter method for property <tt>flag</tt>.
     * 
     * @return property value of flag
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     * Setter method for property <tt>flag</tt>.
     * 
     * @param flag value to be assigned to property flag
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
