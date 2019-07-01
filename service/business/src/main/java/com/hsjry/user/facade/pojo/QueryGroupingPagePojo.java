/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryGroupingPagePojo.java, v 1.0 2017年6月16日 下午4:36:48 jiangjd12837 Exp $
 */
public class QueryGroupingPagePojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 1404828853185050162L;
    //组织ID
    private String            organId;
    //组织编号
    private String            organNo;
    //组织名称
    private String            organName;
    //组织负责人
    private String            dutyName;
    //组织负责人ID
    private String            liableUserId;
    //组织联系方式
    private String            contactWay;
    //通行证标识
    private String            identifiers;
    //小组人数
    private int               memberNumber;
    //组织创建时间
    private Date              organCreateTime;
    //组织修改时间
    private Date              organUpdateTime;

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

    /**
     * Getter method for property <tt>liableUserId</tt>.
     * 
     * @return property value of liableUserId
     */
    public String getLiableUserId() {
        return liableUserId;
    }

    /**
     * Setter method for property <tt>liableUserId</tt>.
     * 
     * @param liableUserId value to be assigned to property liableUserId
     */
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

    /**
     * Getter method for property <tt>contactWay</tt>.
     * 
     * @return property value of contactWay
     */
    public String getContactWay() {
        return contactWay;
    }

    /**
     * Setter method for property <tt>contactWay</tt>.
     * 
     * @param contactWay value to be assigned to property contactWay
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

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
     * Getter method for property <tt>organNo</tt>.
     * 
     * @return property value of organNo
     */
    public String getOrganNo() {
        return organNo;
    }

    /**
     * Setter method for property <tt>organNo</tt>.
     * 
     * @param organNo value to be assigned to property organNo
     */
    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    /**
     * Getter method for property <tt>dutyName</tt>.
     * 
     * @return property value of dutyName
     */
    public String getDutyName() {
        return dutyName;
    }

    /**
     * Setter method for property <tt>dutyName</tt>.
     * 
     * @param dutyName value to be assigned to property dutyName
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    /**
     * Getter method for property <tt>identifiers</tt>.
     * 
     * @return property value of identifiers
     */
    public String getIdentifiers() {
        return identifiers;
    }

    /**
     * Setter method for property <tt>identifiers</tt>.
     * 
     * @param identifiers value to be assigned to property identifiers
     */
    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Getter method for property <tt>memberNumber</tt>.
     * 
     * @return property value of memberNumber
     */
    public int getMemberNumber() {
        return memberNumber;
    }

    /**
     * Setter method for property <tt>memberNumber</tt>.
     * 
     * @param memberNumber value to be assigned to property memberNumber
     */
    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    /**
     * Getter method for property <tt>organCreateTime</tt>.
     * 
     * @return property value of organCreateTime
     */
    public Date getOrganCreateTime() {
        return organCreateTime;
    }

    /**
     * Setter method for property <tt>organCreateTime</tt>.
     * 
     * @param organCreateTime value to be assigned to property organCreateTime
     */
    public void setOrganCreateTime(Date organCreateTime) {
        this.organCreateTime = organCreateTime;
    }

    /**
     * Getter method for property <tt>organUpdateTime</tt>.
     * 
     * @return property value of organUpdateTime
     */
    public Date getOrganUpdateTime() {
        return organUpdateTime;
    }

    /**
     * Setter method for property <tt>organUpdateTime</tt>.
     * 
     * @param organUpdateTime value to be assigned to property organUpdateTime
     */
    public void setOrganUpdateTime(Date organUpdateTime) {
        this.organUpdateTime = organUpdateTime;
    }

}
