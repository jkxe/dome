/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyOrganNameRequest.java, v 1.0 2017年10月9日 上午11:50:56 jiangjd12837 Exp $
 */
public class ModifyOrganNameRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -1789823710783747000L;
    //组织Id
    @NotNull(errorCode = "000001", message = "组织Id")
    @NotBlank(errorCode = "000001", message = "组织Id")
    private String            organId;
    //组织名称
    @NotNull(errorCode = "000001", message = "组织名称")
    @NotBlank(errorCode = "000001", message = "组织名称")
    private String            organName;

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
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }

}
