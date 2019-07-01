package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;


/**
 * 合作信息
 * @author liaosq23298
 * @version $Id: UserPartnerInfoandSourcePojo.java, v 0.1 Nov 22, 2017 2:50:35 PM liaosq23298 Exp $
 */

public class UserPartnerInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = -2444120424795043712L;
    //资源项ID
    private String             resourceId;
    //客户ID
    private String             userId;
    //合作方简介
    private String             partnerIntroduction;
    //担保信用等级
    private String             securedCreditLevel;
    //评级机构
    private String             ratingAgencies;
    //评级时间
    private String             ratingTime;
    //合作方代码
    private String             partnerCode;
    //资源来源
    private EnumResourceSource resourceSource; 

    /**
     * Getter method for property <tt>partnerCode</tt>.
     * 
     * @return property value of partnerCode
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     * Setter method for property <tt>partnerCode</tt>.
     * 
     * @param partnerCode value to be assigned to property partnerCode
     */
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

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
     * Getter method for property <tt>partnerIntroduction</tt>.
     * 
     * @return property value of partnerIntroduction
     */
    public String getPartnerIntroduction() {
        return partnerIntroduction;
    }

    /**
     * Setter method for property <tt>partnerIntroduction</tt>.
     * 
     * @param partnerIntroduction value to be assigned to property partnerIntroduction
     */
    public void setPartnerIntroduction(String partnerIntroduction) {
        this.partnerIntroduction = partnerIntroduction;
    }

    /**
     * Getter method for property <tt>securedCreditLevel</tt>.
     * 
     * @return property value of securedCreditLevel
     */
    public String getSecuredCreditLevel() {
        return securedCreditLevel;
    }

    /**
     * Setter method for property <tt>securedCreditLevel</tt>.
     * 
     * @param securedCreditLevel value to be assigned to property securedCreditLevel
     */
    public void setSecuredCreditLevel(String securedCreditLevel) {
        this.securedCreditLevel = securedCreditLevel;
    }

    /**
     * Getter method for property <tt>ratingAgencies</tt>.
     * 
     * @return property value of ratingAgencies
     */
    public String getRatingAgencies() {
        return ratingAgencies;
    }

    /**
     * Setter method for property <tt>ratingAgencies</tt>.
     * 
     * @param ratingAgencies value to be assigned to property ratingAgencies
     */
    public void setRatingAgencies(String ratingAgencies) {
        this.ratingAgencies = ratingAgencies;
    }

    /**
     * Getter method for property <tt>ratingTime</tt>.
     * 
     * @return property value of ratingTime
     */
    public String getRatingTime() {
        return ratingTime;
    }

    /**
     * Setter method for property <tt>ratingTime</tt>.
     * 
     * @param ratingTime value to be assigned to property ratingTime
     */
    public void setRatingTime(String ratingTime) {
        this.ratingTime = ratingTime;
    }
    /**
     * Getter method for property <tt>resourceSource</tt>.
     * 
     * @return property value of resourceSource
     */
    public EnumResourceSource getResourceSource() {
        return resourceSource;
    }

    /**
     * Setter method for property <tt>resourceSource</tt>.
     * 
     * @param resourceSource value to be assigned to property resourceSource
     */
    public void setResourceSource(EnumResourceSource resourceSource) {
        this.resourceSource = resourceSource;
    }
}
