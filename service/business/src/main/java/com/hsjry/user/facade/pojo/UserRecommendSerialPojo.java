/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Date;

import com.hsjry.lang.common.base.enums.EnumBool;

/**
 * 推荐流水
 * @author hongsj
 * @version $Id: UserRecommendSerialPojo.java, v 1.0 2017年3月31日 上午10:21:44 hongsj Exp $
 */
public class UserRecommendSerialPojo implements Serializable {
    /**  */
    private static final long serialVersionUID = 2073399746067708085L;
    /**流水ID*/
    private String            serialId;
    /**租户ID*/
    private String            tenantId;
    /**通行证ID*/
    private String            authId;
    /**被推荐人通行证ID*/
    private String            beRecommenderAuthId;
    /**被推荐人姓名*/
    private String            beRecommenderName;
    /**被推荐人手机*/
    private String            beRecommenderPhone;
    /**被推荐人信息*/
    private String            beRecommenderInfo;
    /**是否推荐成功*/
    private EnumBool          recommendSucceFlag;
    /**创建时间*/
    private Date              createTime;

    /**
     * Getter method for property <tt>serialId</tt>.
     * 
     * @return property value of serialId
     */
    public String getSerialId() {
        return serialId;
    }

    /**
     * Setter method for property <tt>serialId</tt>.
     * 
     * @param serialId value to be assigned to property serialId
     */
    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    /**
     * Getter method for property <tt>tenantId</tt>.
     * 
     * @return property value of tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Setter method for property <tt>tenantId</tt>.
     * 
     * @param tenantId value to be assigned to property tenantId
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Getter method for property <tt>authId</tt>.
     * 
     * @return property value of authId
     */
    public String getAuthId() {
        return authId;
    }

    /**
     * Setter method for property <tt>authId</tt>.
     * 
     * @param authId value to be assigned to property authId
     */
    public void setAuthId(String authId) {
        this.authId = authId;
    }

    /**
     * Getter method for property <tt>beRecommenderAuthId</tt>.
     * 
     * @return property value of beRecommenderAuthId
     */
    public String getBeRecommenderAuthId() {
        return beRecommenderAuthId;
    }

    /**
     * Setter method for property <tt>beRecommenderAuthId</tt>.
     * 
     * @param beRecommenderAuthId value to be assigned to property beRecommenderAuthId
     */
    public void setBeRecommenderAuthId(String beRecommenderAuthId) {
        this.beRecommenderAuthId = beRecommenderAuthId;
    }

    /**
     * Getter method for property <tt>beRecommenderName</tt>.
     * 
     * @return property value of beRecommenderName
     */
    public String getBeRecommenderName() {
        return beRecommenderName;
    }

    /**
     * Setter method for property <tt>beRecommenderName</tt>.
     * 
     * @param beRecommenderName value to be assigned to property beRecommenderName
     */
    public void setBeRecommenderName(String beRecommenderName) {
        this.beRecommenderName = beRecommenderName;
    }

    /**
     * Getter method for property <tt>beRecommenderPhone</tt>.
     * 
     * @return property value of beRecommenderPhone
     */
    public String getBeRecommenderPhone() {
        return beRecommenderPhone;
    }

    /**
     * Setter method for property <tt>beRecommenderPhone</tt>.
     * 
     * @param beRecommenderPhone value to be assigned to property beRecommenderPhone
     */
    public void setBeRecommenderPhone(String beRecommenderPhone) {
        this.beRecommenderPhone = beRecommenderPhone;
    }

    /**
     * Getter method for property <tt>beRecommenderInfo</tt>.
     * 
     * @return property value of beRecommenderInfo
     */
    public String getBeRecommenderInfo() {
        return beRecommenderInfo;
    }

    /**
     * Setter method for property <tt>beRecommenderInfo</tt>.
     * 
     * @param beRecommenderInfo value to be assigned to property beRecommenderInfo
     */
    public void setBeRecommenderInfo(String beRecommenderInfo) {
        this.beRecommenderInfo = beRecommenderInfo;
    }

    /**
     * Getter method for property <tt>recommendSucceFlag</tt>.
     * 
     * @return property value of recommendSucceFlag
     */
    public EnumBool getRecommendSucceFlag() {
        return recommendSucceFlag;
    }

    /**
     * Setter method for property <tt>recommendSucceFlag</tt>.
     * 
     * @param recommendSucceFlag value to be assigned to property recommendSucceFlag
     */
    public void setRecommendSucceFlag(EnumBool recommendSucceFlag) {
        this.recommendSucceFlag = recommendSucceFlag;
    }

        /**
         * Getter method for property <tt>createTime</tt>.
         * 
         * @return property value of createTime
         */
    public Date getCreateTime() {
        return createTime;
    }

        /**
         * Setter method for property <tt>createTime</tt>.
         * 
         * @param createTime value to be assigned to property createTime
         */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
