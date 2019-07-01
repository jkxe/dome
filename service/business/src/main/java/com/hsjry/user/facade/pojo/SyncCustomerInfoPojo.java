/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumCertificateKind;
import com.hsjry.user.facade.pojo.enums.EnumEduLevel;
import com.hsjry.user.facade.pojo.enums.EnumGender;
import com.hsjry.user.facade.pojo.enums.EnumMarriageStatus;

/**
 * 
 * @author zhengqy15963
 * @version $Id: SyncCustomerInfoPojo.java, v 1.0 2017年11月27日 下午4:33:19 zhengqy15963 Exp $
 */
public class SyncCustomerInfoPojo implements Serializable {

    /** 对象 */
    private static final long   serialVersionUID = 2980587062938940692L;
    /** 客户编号   */
    private String              userId;
    /** 管理级别 */
    private String              userRole;
    /**是否集团员工*/
    private EnumBool            isGroup;
    /**会员等级*/
    private String                 code;
    /** 外部注册时间 */
    private Date                registerTime;
    /** 是否董事会成员 */
    private EnumBool            isBoardMember;
    /** HR管理级别 */
    private String              manageLevel;
    /** 是否正式工作 */
    private String              isFullTimeJob;
    /** 移动电话 */
    private String              telephone;
    /** 客户姓名 */
    private String              clientName;
    /** 证件类型 */
    private EnumCertificateKind cardType;
    /** 证件号码 */
    private String              idNo;
    /** 工作邮箱 */
    private String              email;
    /** 行业*/
    private String              industryType;
    /** 职位短描述*/
    private String              describeJob;
    /** 学历*/
    private EnumEduLevel        eduLevel;
    /** 年龄*/
    private String              age;
    /** 性别*/
    private EnumGender          sex;
    /** 婚姻状况*/
    private EnumMarriageStatus  marriageStatus;
    /** 单位固定电话*/
    private String              companyPhone;
    /** 户籍*/
    private String              censusRegister;
    /** 工龄*/
    private String              wages;
    /**工作单位*/
    private String              company;
    /**用户入职时间*/
    private Date                enterGroupTime;
    /**是否有惩罚记录*/
    private EnumBool            isPunishmentRecord;
    /**个人主卡*/
    private BankCardPojo        bankCardPojo;

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
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>clientName</tt>.
     * 
     * @return property value of clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Setter method for property <tt>clientName</tt>.
     * 
     * @param clientName value to be assigned to property clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Getter method for property <tt>cardType</tt>.
     * 
     * @return property value of cardType
     */
    public EnumCertificateKind getCardType() {
        return cardType;
    }

    /**
     * Setter method for property <tt>cardType</tt>.
     * 
     * @param cardType value to be assigned to property cardType
     */
    public void setCardType(EnumCertificateKind cardType) {
        this.cardType = cardType;
    }

    /**
     * Getter method for property <tt>idNo</tt>.
     * 
     * @return property value of idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * Setter method for property <tt>idNo</tt>.
     * 
     * @param idNo value to be assigned to property idNo
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

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
     * Getter method for property <tt>describeJob</tt>.
     * 
     * @return property value of describeJob
     */
    public String getDescribeJob() {
        return describeJob;
    }

    /**
     * Setter method for property <tt>describeJob</tt>.
     * 
     * @param describeJob value to be assigned to property describeJob
     */
    public void setDescribeJob(String describeJob) {
        this.describeJob = describeJob;
    }

    /**
     * Getter method for property <tt>eduLevel</tt>.
     * 
     * @return property value of eduLevel
     */
    public EnumEduLevel getEduLevel() {
        return eduLevel;
    }

    /**
     * Setter method for property <tt>eduLevel</tt>.
     * 
     * @param eduLevel value to be assigned to property eduLevel
     */
    public void setEduLevel(EnumEduLevel eduLevel) {
        this.eduLevel = eduLevel;
    }

    /**
     * Getter method for property <tt>age</tt>.
     * 
     * @return property value of age
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter method for property <tt>age</tt>.
     * 
     * @param age value to be assigned to property age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Getter method for property <tt>sex</tt>.
     * 
     * @return property value of sex
     */
    public EnumGender getSex() {
        return sex;
    }

    /**
     * Setter method for property <tt>sex</tt>.
     * 
     * @param sex value to be assigned to property sex
     */
    public void setSex(EnumGender sex) {
        this.sex = sex;
    }

    /**
     * Getter method for property <tt>marriageStatus</tt>.
     * 
     * @return property value of marriageStatus
     */
    public EnumMarriageStatus getMarriageStatus() {
        return marriageStatus;
    }

    /**
     * Setter method for property <tt>marriageStatus</tt>.
     * 
     * @param marriageStatus value to be assigned to property marriageStatus
     */
    public void setMarriageStatus(EnumMarriageStatus marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    /**
     * Getter method for property <tt>companyPhone</tt>.
     * 
     * @return property value of companyPhone
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * Setter method for property <tt>companyPhone</tt>.
     * 
     * @param companyPhone value to be assigned to property companyPhone
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    /**
     * Getter method for property <tt>censusRegister</tt>.
     * 
     * @return property value of censusRegister
     */
    public String getCensusRegister() {
        return censusRegister;
    }

    /**
     * Setter method for property <tt>censusRegister</tt>.
     * 
     * @param censusRegister value to be assigned to property censusRegister
     */
    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    /**
     * Getter method for property <tt>wages</tt>.
     * 
     * @return property value of wages
     */
    public String getWages() {
        return wages;
    }

    /**
     * Setter method for property <tt>wages</tt>.
     * 
     * @param wages value to be assigned to property wages
     */
    public void setWages(String wages) {
        this.wages = wages;
    }

    /**
     * Getter method for property <tt>company</tt>.
     * 
     * @return property value of company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Setter method for property <tt>company</tt>.
     * 
     * @param company value to be assigned to property company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
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
     * Getter method for property <tt>bankCardPojo</tt>.
     * 
     * @return property value of bankCardPojo
     */
    public BankCardPojo getBankCardPojo() {
        return bankCardPojo;
    }

    /**
     * Setter method for property <tt>bankCardPojo</tt>.
     * 
     * @param bankCardPojo value to be assigned to property bankCardPojo
     */
    public void setBankCardPojo(BankCardPojo bankCardPojo) {
        this.bankCardPojo = bankCardPojo;
    }

    /**
     * 构造器
     */
    public SyncCustomerInfoPojo() {
    }

}
