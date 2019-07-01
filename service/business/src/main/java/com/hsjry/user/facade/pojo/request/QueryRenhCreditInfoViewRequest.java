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
 * @author lilin22830
 * @version $Id: QueryRenhCreditInfoViewRequest.java, v 0.1 Aug 16, 2017 6:47:19 PM lilin22830 Exp $
 */
public class QueryRenhCreditInfoViewRequest implements Serializable {

    
    
    private static final long serialVersionUID = 3839748116206356490L;
    
    @NotNull(errorCode = "000001", message = "征信项ID")
    @NotBlank(errorCode = "000001", message = "征信项ID")
    private String reportId;

    /**
     * Getter method for property <tt>reportId</tt>.
     * 
     * @return property value of reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * Setter method for property <tt>reportId</tt>.
     * 
     * @param reportId value to be assigned to property reportId
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    
    

}
