/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.OrganizationPojo;

/**
 * 
 * @author hongsj
 * @version $Id: QueryOrganByParentIdResponse.java, v 1.0 2017年5月11日 下午2:26:12 hongsj Exp $
 */
public class QueryOrganByParentIdResponse implements Serializable {

    /**  */
    private static final long      serialVersionUID = 8421024735840803920L;

    private List<OrganizationPojo> pojoList;

    /**
     * Getter method for property <tt>pojoList</tt>.
     * 
     * @return property value of pojoList
     */
    public List<OrganizationPojo> getPojoList() {
        return pojoList;
    }

    /**
     * Setter method for property <tt>pojoList</tt>.
     * 
     * @param pojoList value to be assigned to property pojoList
     */
    public void setPojoList(List<OrganizationPojo> pojoList) {
        this.pojoList = pojoList;
    }

}
