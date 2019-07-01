package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumReportKind;
import com.hsjry.user.facade.pojo.enums.EnumReportType;
import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**
 * 财务报表
 * 
 * @author wanglg15468
 * @version $Id: UserFinancialStatementsInfo.java, v 1.0 2017-3-15 下午6:15:46 wanglg15468 Exp $
 */
public class UserFinancialStatementsInfoPojo implements Serializable {

    /**  */
    private static final long  serialVersionUID = 4830767399719832295L;
    //资源项ID
    private String             resourceId;
    //客户ID
    private String             userId;
    //报表类型
    private EnumReportType     reportType;
    //报表类别
    private EnumReportKind     reportKind;
    //报表日期
    private String             reportDate;
    //报表url 
    private String             reportUrl;
    //资源标识
    private String             resourceIdType;
    //资源内容
    private String             resourceSubstance;
    //资源来源
    private EnumResourceSource resourceSource;
    //资源状态
    private String             resourceStatus;

    /**
     * Getter method for property <tt>reportType</tt>.
     * 
     * @return property value of reportType
     */
    public EnumReportType getReportType() {
        return reportType;
    }

    /**
     * Setter method for property <tt>reportType</tt>.
     * 
     * @param reportType value to be assigned to property reportType
     */
    public void setReportType(EnumReportType reportType) {
        this.reportType = reportType;
    }

    /**
     * Getter method for property <tt>reportKind</tt>.
     * 
     * @return property value of reportKind
     */
    public EnumReportKind getReportKind() {
        return reportKind;
    }

    /**
     * Setter method for property <tt>reportKind</tt>.
     * 
     * @param reportKind value to be assigned to property reportKind
     */
    public void setReportKind(EnumReportKind reportKind) {
        this.reportKind = reportKind;
    }

    /**
     * Getter method for property <tt>reportDate</tt>.
     * 
     * @return property value of reportDate
     */
    public String getReportDate() {
        return reportDate;
    }

    /**
     * Setter method for property <tt>reportDate</tt>.
     * 
     * @param reportDate value to be assigned to property reportDate
     */
    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Getter method for property <tt>reportUrl</tt>.
     * 
     * @return property value of reportUrl
     */
    public String getReportUrl() {
        return reportUrl;
    }

    /**
     * Setter method for property <tt>reportUrl</tt>.
     * 
     * @param reportUrl value to be assigned to property reportUrl
     */
    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
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
     * Getter method for property <tt>resourceIdType</tt>.
     * 
     * @return property value of resourceIdType
     */
    public String getResourceIdType() {
        return resourceIdType;
    }

    /**
     * Setter method for property <tt>resourceIdType</tt>.
     * 
     * @param resourceIdType value to be assigned to property resourceIdType
     */
    public void setResourceIdType(String resourceIdType) {
        this.resourceIdType = resourceIdType;
    }

    /**
     * Getter method for property <tt>resourceSubstance</tt>.
     * 
     * @return property value of resourceSubstance
     */
    public String getResourceSubstance() {
        return resourceSubstance;
    }

    /**
     * Setter method for property <tt>resourceSubstance</tt>.
     * 
     * @param resourceSubstance value to be assigned to property resourceSubstance
     */
    public void setResourceSubstance(String resourceSubstance) {
        this.resourceSubstance = resourceSubstance;
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

    /**
     * Getter method for property <tt>resourceStatus</tt>.
     * 
     * @return property value of resourceStatus
     */
    public String getResourceStatus() {
        return resourceStatus;
    }

    /**
     * Setter method for property <tt>resourceStatus</tt>.
     * 
     * @param resourceStatus value to be assigned to property resourceStatus
     */
    public void setResourceStatus(String resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

}
