package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 公积金信息
 * @author liaosq23298
 * @version $Id: UserAccumulationFundInfoandSourcePojo.java, v 0.1 Nov 22, 2017 3:04:26 PM liaosq23298 Exp $
 */
public class UserAccumulationFundInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = -6050350354597420189L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //参缴地
    private String            payPalce;
    //参缴日期
    private String            payDate;
    //初缴月份
    private String            firstPayMonth;
    //缴至月份
    private String            lastPayMonth;
    //缴费状态
    private String            paymentStatus;
    //月缴存额
    private String            payQuotaMouth;
    //个人缴存比例
    private String            personalProportion;
    //单位缴存比例
    private String            companyProportion;
    //信息来源
    private String            infoSource;
    //资源来源
    private EnumResourceSource resourceSource;

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
     * Getter method for property <tt>payPalce</tt>.
     * 
     * @return property value of payPalce
     */
    public String getPayPalce() {
        return payPalce;
    }

    /**
     * Setter method for property <tt>payPalce</tt>.
     * 
     * @param payPalce value to be assigned to property payPalce
     */
    public void setPayPalce(String payPalce) {
        this.payPalce = payPalce;
    }

    /**
     * Getter method for property <tt>payDate</tt>.
     * 
     * @return property value of payDate
     */
    public String getPayDate() {
        return payDate;
    }

    /**
     * Setter method for property <tt>payDate</tt>.
     * 
     * @param payDate value to be assigned to property payDate
     */
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    /**
     * Getter method for property <tt>firstPayMonth</tt>.
     * 
     * @return property value of firstPayMonth
     */
    public String getFirstPayMonth() {
        return firstPayMonth;
    }

    /**
     * Setter method for property <tt>firstPayMonth</tt>.
     * 
     * @param firstPayMonth value to be assigned to property firstPayMonth
     */
    public void setFirstPayMonth(String firstPayMonth) {
        this.firstPayMonth = firstPayMonth;
    }

    /**
     * Getter method for property <tt>lastPayMonth</tt>.
     * 
     * @return property value of lastPayMonth
     */
    public String getLastPayMonth() {
        return lastPayMonth;
    }

    /**
     * Setter method for property <tt>lastPayMonth</tt>.
     * 
     * @param lastPayMonth value to be assigned to property lastPayMonth
     */
    public void setLastPayMonth(String lastPayMonth) {
        this.lastPayMonth = lastPayMonth;
    }

    /**
     * Getter method for property <tt>paymentStatus</tt>.
     * 
     * @return property value of paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Setter method for property <tt>paymentStatus</tt>.
     * 
     * @param paymentStatus value to be assigned to property paymentStatus
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Getter method for property <tt>payQuotaMouth</tt>.
     * 
     * @return property value of payQuotaMouth
     */
    public String getPayQuotaMouth() {
        return payQuotaMouth;
    }

    /**
     * Setter method for property <tt>payQuotaMouth</tt>.
     * 
     * @param payQuotaMouth value to be assigned to property payQuotaMouth
     */
    public void setPayQuotaMouth(String payQuotaMouth) {
        this.payQuotaMouth = payQuotaMouth;
    }

    /**
     * Getter method for property <tt>personalProportion</tt>.
     * 
     * @return property value of personalProportion
     */
    public String getPersonalProportion() {
        return personalProportion;
    }

    /**
     * Setter method for property <tt>personalProportion</tt>.
     * 
     * @param personalProportion value to be assigned to property personalProportion
     */
    public void setPersonalProportion(String personalProportion) {
        this.personalProportion = personalProportion;
    }

    /**
     * Getter method for property <tt>companyProportion</tt>.
     * 
     * @return property value of companyProportion
     */
    public String getCompanyProportion() {
        return companyProportion;
    }

    /**
     * Setter method for property <tt>companyProportion</tt>.
     * 
     * @param companyProportion value to be assigned to property companyProportion
     */
    public void setCompanyProportion(String companyProportion) {
        this.companyProportion = companyProportion;
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
