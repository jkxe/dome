/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumClientRelation;
import com.hsjry.user.facade.pojo.enums.EnumRelationType;

/**
 * 
 * @author jiangjd12837
 * @version $Id: UserCustomeRelationInfoPojo.java, v 1.0 2017年4月20日 下午5:32:26 jiangjd12837 Exp $
 */
public class UserCustomeRelationInfoPojo implements Serializable {

    /**  */
    private static final long  serialVersionUID = -3908852613834512972L;
    /**  */

    //客户ID
    private String             userId;
    //关系人客户ID   
    private String             relationUserId;
    //关系类型
    private EnumRelationType   relationType;
    private String             relationGetWay;
    //客户关系代码
    private EnumClientRelation clientRelation;

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
     * Getter method for property <tt>relationUserId</tt>.
     * 
     * @return property value of relationUserId
     */
    public String getRelationUserId() {
        return relationUserId;
    }

    /**
     * Setter method for property <tt>relationUserId</tt>.
     * 
     * @param relationUserId value to be assigned to property relationUserId
     */
    public void setRelationUserId(String relationUserId) {
        this.relationUserId = relationUserId;
    }

    /**
     * Getter method for property <tt>relationType</tt>.
     * 
     * @return property value of relationType
     */
    public EnumRelationType getRelationType() {
        return relationType;
    }

    /**
     * Setter method for property <tt>relationType</tt>.
     * 
     * @param relationType value to be assigned to property relationType
     */
    public void setRelationType(EnumRelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Getter method for property <tt>relationGetWay</tt>.
     * 
     * @return property value of relationGetWay
     */
    public String getRelationGetWay() {
        return relationGetWay;
    }

    /**
     * Setter method for property <tt>relationGetWay</tt>.
     * 
     * @param relationGetWay value to be assigned to property relationGetWay
     */
    public void setRelationGetWay(String relationGetWay) {
        this.relationGetWay = relationGetWay;
    }

    /**
     * Getter method for property <tt>clientRelation</tt>.
     * 
     * @return property value of clientRelation
     */
    public EnumClientRelation getClientRelation() {
        return clientRelation;
    }

    /**
     * Setter method for property <tt>clientRelation</tt>.
     * 
     * @param clientRelation value to be assigned to property clientRelation
     */
    public void setClientRelation(EnumClientRelation clientRelation) {
        this.clientRelation = clientRelation;
    }

}
