package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 缴税信息
 * 
 * @author wanglg15468
 * @version $Id: UserPayTaxesInfo.java, v 1.0 2017-3-15 下午7:09:48 wanglg15468 Exp $
 */
public class UserPayTaxesInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -878927507375597657L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //主管税务机关名称
    private String            taxAuthorityName;
    //主管税务机关代码
    private String            taxAuthorityCode;
    //税务登记代码
    private String            taxRegistrationCode;
    //纳税状态
    private String            taxStatus;
    //欠税总额
    private String            owingTaxesAmount;
    //欠税种类
    private String            owingTaxesType;
    //信息来源
    private String            infoSource;
    //信息更新日期
    private Date              updateTime;

    /**
     * Getter method for property <tt>resourceId</tt>.
     * 
     * @return property value of resourceId
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Setter method for property <tt>resourceId</tt>.
     * 
     * @param resourceId value to be assigned to property resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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
     * Getter method for property <tt>taxAuthorityName</tt>.
     * 
     * @return property value of taxAuthorityName
     */
    public String getTaxAuthorityName() {
        return taxAuthorityName;
    }

    /**
     * Setter method for property <tt>taxAuthorityName</tt>.
     * 
     * @param taxAuthorityName value to be assigned to property taxAuthorityName
     */
    public void setTaxAuthorityName(String taxAuthorityName) {
        this.taxAuthorityName = taxAuthorityName;
    }

    /**
     * Getter method for property <tt>taxAuthorityCode</tt>.
     * 
     * @return property value of taxAuthorityCode
     */
    public String getTaxAuthorityCode() {
        return taxAuthorityCode;
    }

    /**
     * Setter method for property <tt>taxAuthorityCode</tt>.
     * 
     * @param taxAuthorityCode value to be assigned to property taxAuthorityCode
     */
    public void setTaxAuthorityCode(String taxAuthorityCode) {
        this.taxAuthorityCode = taxAuthorityCode;
    }

    /**
     * Getter method for property <tt>taxRegistrationCode</tt>.
     * 
     * @return property value of taxRegistrationCode
     */
    public String getTaxRegistrationCode() {
        return taxRegistrationCode;
    }

    /**
     * Setter method for property <tt>taxRegistrationCode</tt>.
     * 
     * @param taxRegistrationCode value to be assigned to property taxRegistrationCode
     */
    public void setTaxRegistrationCode(String taxRegistrationCode) {
        this.taxRegistrationCode = taxRegistrationCode;
    }

    /**
     * Getter method for property <tt>taxStatus</tt>.
     * 
     * @return property value of taxStatus
     */
    public String getTaxStatus() {
        return taxStatus;
    }

    /**
     * Setter method for property <tt>taxStatus</tt>.
     * 
     * @param taxStatus value to be assigned to property taxStatus
     */
    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    /**
     * Getter method for property <tt>owingTaxesAmount</tt>.
     * 
     * @return property value of owingTaxesAmount
     */
    public String getOwingTaxesAmount() {
        return owingTaxesAmount;
    }

    /**
     * Setter method for property <tt>owingTaxesAmount</tt>.
     * 
     * @param owingTaxesAmount value to be assigned to property owingTaxesAmount
     */
    public void setOwingTaxesAmount(String owingTaxesAmount) {
        this.owingTaxesAmount = owingTaxesAmount;
    }

    /**
     * Getter method for property <tt>owingTaxesType</tt>.
     * 
     * @return property value of owingTaxesType
     */
    public String getOwingTaxesType() {
        return owingTaxesType;
    }

    /**
     * Setter method for property <tt>owingTaxesType</tt>.
     * 
     * @param owingTaxesType value to be assigned to property owingTaxesType
     */
    public void setOwingTaxesType(String owingTaxesType) {
        this.owingTaxesType = owingTaxesType;
    }

    /**
     * Getter method for property <tt>infoSource</tt>.
     * 
     * @return property value of infoSource
     */
    public String getInfoSource() {
        return infoSource;
    }

    /**
     * Setter method for property <tt>infoSource</tt>.
     * 
     * @param infoSource value to be assigned to property infoSource
     */
    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    /**
     * Getter method for property <tt>updateTime</tt>.
     * 
     * @return property value of updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Setter method for property <tt>updateTime</tt>.
     * 
     * @param updateTime value to be assigned to property updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
