/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;



/**
 * 查询特殊名单视图请求类
 * @author hongsj
 * @version $Id: QuerySpecialViewByAuditNoSerialRequest.java, v 1.0 2017年3月28日 下午2:09:29 hongsj Exp $
 */
public class QuerySpecialViewByAuditNoSerialRequest implements Serializable {

    private static final long serialVersionUID = 8306519903760745151L;

    /**审核编号*/
    @NotNull(errorCode = "000001", message = "审核编号")
    @NotBlank(errorCode = "000001", message = "审核编号")
    private String auditSerialNo;

    /**
     * Getter method for property <tt>auditSerialNo</tt>.
     * 
     * @return property value of auditSerialNo
     */
    public String getAuditSerialNo() {
        return auditSerialNo;
    }

    /**
     * Setter method for property <tt>auditSerialNo</tt>.
     * 
     * @param auditSerialNo value to be assigned to property auditSerialNo
     */
    public void setAuditSerialNo(String auditSerialNo) {
        this.auditSerialNo = auditSerialNo;
    }
    
    
}
