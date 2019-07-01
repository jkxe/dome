/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 激活禁用组件请求类
 * @author hongsj
 * @version $Id: SetSubassemblyStatusRequest.java, v 1.0 2017年3月28日 下午1:33:00 hongsj Exp $
 */
public class SetSubassemblyStatusRequest implements Serializable {
    /**  */
    private static final long serialVersionUID = -3325195092705962491L;
    /**组件id*/
    @NotNull(errorCode = "000001", message = "组件id")
    @NotBlank(errorCode = "000001", message = "组件id")
    private String            subassemblyId;
    /**组件状态*/
    @NotNull(errorCode = "000001", message = "组件状态")
    private EnumObjectStatus  subassemblyStatus;

    /**
     * Getter method for property <tt>subassemblyId</tt>.
     * 
     * @return property value of subassemblyId
     */
    public String getSubassemblyId() {
        return subassemblyId;
    }

    /**
     * Setter method for property <tt>subassemblyId</tt>.
     * 
     * @param subassemblyId value to be assigned to property subassemblyId
     */
    public void setSubassemblyId(String subassemblyId) {
        this.subassemblyId = subassemblyId;
    }

    /**
     * Getter method for property <tt>subassemblyStatus</tt>.
     * 
     * @return property value of subassemblyStatus
     */
    public EnumObjectStatus getSubassemblyStatus() {
        return subassemblyStatus;
    }

    /**
     * Setter method for property <tt>subassemblyStatus</tt>.
     * 
     * @param subassemblyStatus value to be assigned to property subassemblyStatus
     */
    public void setSubassemblyStatus(EnumObjectStatus subassemblyStatus) {
        this.subassemblyStatus = subassemblyStatus;
    }

}
