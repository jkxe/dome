/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 
 * @author zhengqy15963
 * @version $Id: CustomerExtInfoPojo.java, v 1.0 2017年11月28日 上午9:46:32 zhengqy15963 Exp $
 */
public class CustomerExtInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -1602326712564221194L;
    /** 客户编号                    */
    private String            userId;
    /** 管理级别 */
    private String            userRole;
    /**是否集团员工*/
    private EnumBool          isGroup;
    /**会员等级*/
    private String            code;
    /** 外部注册时间 */
    private Date              registerTime;
    /** 是否董事会成员 */
    private EnumBool          isBoardMember;
    /** HR管理级别 */
    private String            manageLevel;
    /** 是否正式工作 */
    private String            isFullTimeJob;
    /** 是否有惩罚信息 */
    private EnumBool          isPunishmentRecord;
    /**用户入职时间*/
    private Date              enterGroupTime;
    /** 行业*/
    private String            industryType;
    /**更新之前的是否集团员工状态*/
    private EnumBool          isGroupBeforeChange;
    /**
     * Getter method for property <tt>industryType</tt>.
     * 
     * @return property value of industryType
     */
    public String getIndustryType() {
        return industryType;
    }

    /**
     * Setter method for property <tt>industryType</tt>.
     * 
     * @param industryType value to be assigned to property industryType
     */
    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    /**
     * 
     * Getter method for property <tt>enterGroupTime</tt>.
     * 
     * @return property value of enterGroupTime
     */
    public Date getEnterGroupTime() {
        return enterGroupTime;
    }

    /**
     * Setter method for property <tt>enterGroupTime</tt>.
     * 
     * @param enterGroupTime value to be assigned to property enterGroupTime
     */
    public void setEnterGroupTime(Date enterGroupTime) {
        this.enterGroupTime = enterGroupTime;
    }

    /**
     * Getter method for property <tt>isPunishmentRecord</tt>.
     * 
     * @return property value of isPunishmentRecord
     */
    public EnumBool getIsPunishmentRecord() {
        return isPunishmentRecord;
    }

    /**
     * Setter method for property <tt>isPunishmentRecord</tt>.
     * 
     * @param isPunishmentRecord value to be assigned to property isPunishmentRecord
     */
    public void setIsPunishmentRecord(EnumBool isPunishmentRecord) {
        this.isPunishmentRecord = isPunishmentRecord;
    }

    /**
     * Getter method for property <tt>userRole</tt>.
     * 
     * @return property value of userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Setter method for property <tt>userRole</tt>.
     * 
     * @param userRole value to be assigned to property userRole
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Getter method for property <tt>isGroup</tt>.
     * 
     * @return property value of isGroup
     */
    public EnumBool getIsGroup() {
        return isGroup;
    }

    /**
     * Setter method for property <tt>isGroup</tt>.
     * 
     * @param isGroup value to be assigned to property isGroup
     */
    public void setIsGroup(EnumBool isGroup) {
        this.isGroup = isGroup;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>registerTime</tt>.
     * 
     * @return property value of registerTime
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * Setter method for property <tt>registerTime</tt>.
     * 
     * @param registerTime value to be assigned to property registerTime
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * Getter method for property <tt>isBoardMember</tt>.
     * 
     * @return property value of isBoardMember
     */
    public EnumBool getIsBoardMember() {
        return isBoardMember;
    }

    /**
     * Setter method for property <tt>isBoardMember</tt>.
     * 
     * @param isBoardMember value to be assigned to property isBoardMember
     */
    public void setIsBoardMember(EnumBool isBoardMember) {
        this.isBoardMember = isBoardMember;
    }

    /**
     * Getter method for property <tt>manageLevel</tt>.
     * 
     * @return property value of manageLevel
     */
    public String getManageLevel() {
        return manageLevel;
    }

    /**
     * Setter method for property <tt>manageLevel</tt>.
     * 
     * @param manageLevel value to be assigned to property manageLevel
     */
    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
    }

    /**
     * Getter method for property <tt>isFullTimeJob</tt>.
     * 
     * @return property value of isFullTimeJob
     */
    public String getIsFullTimeJob() {
        return isFullTimeJob;
    }

    /**
     * Setter method for property <tt>isFullTimeJob</tt>.
     * 
     * @param isFullTimeJob value to be assigned to property isFullTimeJob
     */
    public void setIsFullTimeJob(String isFullTimeJob) {
        this.isFullTimeJob = isFullTimeJob;
    }

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
     * Getter method for property <tt>isGroupBeforeChange</tt>.
     * 
     * @return property value of isGroupBeforeChange
     */
    public EnumBool getIsGroupBeforeChange() {
        return isGroupBeforeChange;
    }

    /**
     * Setter method for property <tt>isGroupBeforeChange</tt>.
     * 
     * @param isGroupBeforeChange value to be assigned to property isGroupBeforeChange
     */
    public void setIsGroupBeforeChange(EnumBool isGroupBeforeChange) {
        this.isGroupBeforeChange = isGroupBeforeChange;
    }



    

}
