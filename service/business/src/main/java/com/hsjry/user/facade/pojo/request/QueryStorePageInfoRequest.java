/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;
import com.hsjry.user.facade.pojo.enums.EnumVerifyStatus;

/**
 * 分页查询门店信息请求入参
 * @author zhengqy15963
 * @version $Id: QueryStorePageInfoRequest.java, v 1.0 2018年5月7日 下午4:33:31 zhengqy15963 Exp $
 */
public class QueryStorePageInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1153973040882670382L;
    /**门店名称  */
    private String            organName;
    /**所属经销商  */
    private String            merchantName;
    /**所属经销商机构客户id 如提供此参数，查询门店不过滤商户未准入*/
    private String            merchantUserId;
    /**门店负责人  */
    private String            dutyName;
    /**启用状态  */
    private EnumObjectStatus  enableStatus;
    /**审核状态  */
    private EnumVerifyStatus  auditStatus;
    /**创建日期开始*/
    private Date              beginTime;
    /**创建日期结束*/
    private Date              endTime;

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
     * Getter method for property <tt>merchantName</tt>.
     * 
     * @return property value of merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Setter method for property <tt>merchantName</tt>.
     * 
     * @param merchantName value to be assigned to property merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * Getter method for property <tt>merchantUserId</tt>.
     * 
     * @return property value of merchantUserId
     */
    public String getMerchantUserId() {
        return merchantUserId;
    }

    /**
     * Setter method for property <tt>merchantUserId</tt>.
     * 
     * @param merchantUserId value to be assigned to property merchantUserId
     */
    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
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
     * Getter method for property <tt>enableStatus</tt>.
     * 
     * @return property value of enableStatus
     */
    public EnumObjectStatus getEnableStatus() {
        return enableStatus;
    }

    /**
     * Setter method for property <tt>enableStatus</tt>.
     * 
     * @param enableStatus value to be assigned to property enableStatus
     */
    public void setEnableStatus(EnumObjectStatus enableStatus) {
        this.enableStatus = enableStatus;
    }

    /**
     * Getter method for property <tt>auditStatus</tt>.
     * 
     * @return property value of auditStatus
     */
    public EnumVerifyStatus getAuditStatus() {
        return auditStatus;
    }

    /**
     * Setter method for property <tt>auditStatus</tt>.
     * 
     * @param auditStatus value to be assigned to property auditStatus
     */
    public void setAuditStatus(EnumVerifyStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * Getter method for property <tt>beginTime</tt>.
     * 
     * @return property value of beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * Setter method for property <tt>beginTime</tt>.
     * 
     * @param beginTime value to be assigned to property beginTime
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Getter method for property <tt>endTime</tt>.
     * 
     * @return property value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Setter method for property <tt>endTime</tt>.
     * 
     * @param endTime value to be assigned to property endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
