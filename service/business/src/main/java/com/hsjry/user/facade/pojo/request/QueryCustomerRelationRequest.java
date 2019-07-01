/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumClientRelation;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCustomerRelationRequest.java, v 1.0 2017年3月30日 下午4:13:27 jiangjd12837 Exp $
 */
public class QueryCustomerRelationRequest implements Serializable {

    /**  */
    private static final long        serialVersionUID = -6519577004118852328L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                   userId;
    // 关系代码列表
    @NotNull(errorCode = "000001", message = "关系代码列表")
    private List<EnumClientRelation> clientRelationList;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>clientRelationList</tt>.
     * 
     * @return property value of clientRelationList
     */
    public List<EnumClientRelation> getClientRelationList() {
        return clientRelationList;
    }

    /**
     * Setter method for property <tt>clientRelationList</tt>.
     * 
     * @param clientRelationList value to be assigned to property clientRelationList
     */
    public void setClientRelationList(List<EnumClientRelation> clientRelationList) {
        this.clientRelationList = clientRelationList;
    }

}
