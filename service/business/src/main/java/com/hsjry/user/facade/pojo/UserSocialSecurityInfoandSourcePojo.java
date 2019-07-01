package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 社保信息
 * @author liaosq23298
 * @version $Id: UserSocialSecurityInfoandSourcePojo.java, v 0.1 Nov 22, 2017 3:49:24 PM liaosq23298 Exp $
 */
public class UserSocialSecurityInfoandSourcePojo implements Serializable{

    /**  */
    private static final long serialVersionUID = 7807492557097204075L;
  //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //参保地
    private String            insuredAddress;
    //参保时间
    private String            insuredTime;
    //累计缴费月份
    private String            totalPayMonth;
    //参加工作月份
    private String            workingMonth;
    //缴费状态
    private String            paymentStatus;
    //个人缴费基数
    private String            basePayAmount;
    //月缴费金额
    private String            monthPayAmount;
    //信息更新日期
    private Date              updateTime;
    //缴费单位
    private String            payCompany;
    //中断或终止原因
    private String            interruptReason;
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
     * Getter method for property <tt>insuredAddress</tt>.
     * 
     * @return property value of insuredAddress
     */
    public String getInsuredAddress() {
        return insuredAddress;
    }

    /**
     * Setter method for property <tt>insuredAddress</tt>.
     * 
     * @param insuredAddress value to be assigned to property insuredAddress
     */
    public void setInsuredAddress(String insuredAddress) {
        this.insuredAddress = insuredAddress;
    }

    /**
     * Getter method for property <tt>insuredTime</tt>.
     * 
     * @return property value of insuredTime
     */
    public String getInsuredTime() {
        return insuredTime;
    }

    /**
     * Setter method for property <tt>insuredTime</tt>.
     * 
     * @param insuredTime value to be assigned to property insuredTime
     */
    public void setInsuredTime(String insuredTime) {
        this.insuredTime = insuredTime;
    }

    /**
     * Getter method for property <tt>totalPayMonth</tt>.
     * 
     * @return property value of totalPayMonth
     */
    public String getTotalPayMonth() {
        return totalPayMonth;
    }

    /**
     * Setter method for property <tt>totalPayMonth</tt>.
     * 
     * @param totalPayMonth value to be assigned to property totalPayMonth
     */
    public void setTotalPayMonth(String totalPayMonth) {
        this.totalPayMonth = totalPayMonth;
    }

    /**
     * Getter method for property <tt>workingMonth</tt>.
     * 
     * @return property value of workingMonth
     */
    public String getWorkingMonth() {
        return workingMonth;
    }

    /**
     * Setter method for property <tt>workingMonth</tt>.
     * 
     * @param workingMonth value to be assigned to property workingMonth
     */
    public void setWorkingMonth(String workingMonth) {
        this.workingMonth = workingMonth;
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
     * Getter method for property <tt>basePayAmount</tt>.
     * 
     * @return property value of basePayAmount
     */
    public String getBasePayAmount() {
        return basePayAmount;
    }

    /**
     * Setter method for property <tt>basePayAmount</tt>.
     * 
     * @param basePayAmount value to be assigned to property basePayAmount
     */
    public void setBasePayAmount(String basePayAmount) {
        this.basePayAmount = basePayAmount;
    }

    /**
     * Getter method for property <tt>monthPayAmount</tt>.
     * 
     * @return property value of monthPayAmount
     */
    public String getMonthPayAmount() {
        return monthPayAmount;
    }

    /**
     * Setter method for property <tt>monthPayAmount</tt>.
     * 
     * @param monthPayAmount value to be assigned to property monthPayAmount
     */
    public void setMonthPayAmount(String monthPayAmount) {
        this.monthPayAmount = monthPayAmount;
    }

    /**
     * Getter method for property <tt>payCompany</tt>.
     * 
     * @return property value of payCompany
     */
    public String getPayCompany() {
        return payCompany;
    }

    /**
     * Setter method for property <tt>payCompany</tt>.
     * 
     * @param payCompany value to be assigned to property payCompany
     */
    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }

    /**
     * Getter method for property <tt>interruptReason</tt>.
     * 
     * @return property value of interruptReason
     */
    public String getInterruptReason() {
        return interruptReason;
    }

    /**
     * Setter method for property <tt>interruptReason</tt>.
     * 
     * @param interruptReason value to be assigned to property interruptReason
     */
    public void setInterruptReason(String interruptReason) {
        this.interruptReason = interruptReason;
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
