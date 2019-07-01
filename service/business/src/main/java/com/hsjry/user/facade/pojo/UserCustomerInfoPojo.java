package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.lang.common.base.enums.user.EnumUserType;
import com.hsjry.user.facade.pojo.enums.EnumListStatus;
import com.hsjry.user.facade.pojo.enums.EnumManagerType;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

public class UserCustomerInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -5943712141134391317L;
    //客户ID
    private String            userId;
    //客户类型
    private EnumUserType      clientCategory;
    //激活状态
    private EnumBool          activeStatus;
    //记录状态
    private EnumObjectStatus  recordStatus;
    //是否关系人客户
    private EnumBool          custPartnerFlag;
    //名单状态
    private EnumListStatus    listStatus;
    //客户经理ID
    private String            custManagerId;
    //客户经理类型
    private EnumManagerType   managerType;
    //信用等级
    private String            creditLevel;
    //实名认证标志
    private EnumBool          realnameFlag;

    /**
     * Getter method for property <tt>custPartnerFlag</tt>.
     * 
     * @return property value of custPartnerFlag
     */
    public EnumBool getCustPartnerFlag() {
        return custPartnerFlag;
    }

    /**
     * Setter method for property <tt>custPartnerFlag</tt>.
     * 
     * @param custPartnerFlag value to be assigned to property custPartnerFlag
     */
    public void setCustPartnerFlag(EnumBool custPartnerFlag) {
        this.custPartnerFlag = custPartnerFlag;
    }

    /**
     * Getter method for property <tt>activeStatus</tt>.
     * 
     * @return property value of activeStatus
     */
    public EnumBool getActiveStatus() {
        return activeStatus;
    }

    /**
     * Setter method for property <tt>activeStatus</tt>.
     * 
     * @param activeStatus value to be assigned to property activeStatus
     */
    public void setActiveStatus(EnumBool activeStatus) {
        this.activeStatus = activeStatus;
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
     * Getter method for property <tt>clientCategory</tt>.
     * 
     * @return property value of clientCategory
     */
    public EnumUserType getClientCategory() {
        return clientCategory;
    }

    /**
     * Setter method for property <tt>clientCategory</tt>.
     * 
     * @param clientCategory value to be assigned to property clientCategory
     */
    public void setClientCategory(EnumUserType clientCategory) {
        this.clientCategory = clientCategory;
    }

    /**
     * Getter method for property <tt>listStatus</tt>.
     * 
     * @return property value of listStatus
     */
    public EnumListStatus getListStatus() {
        return listStatus;
    }

    /**
     * Setter method for property <tt>listStatus</tt>.
     * 
     * @param listStatus value to be assigned to property listStatus
     */
    public void setListStatus(EnumListStatus listStatus) {
        this.listStatus = listStatus;
    }

    /**
     * Getter method for property <tt>custManagerId</tt>.
     * 
     * @return property value of custManagerId
     */
    public String getCustManagerId() {
        return custManagerId;
    }

    /**
     * Setter method for property <tt>custManagerId</tt>.
     * 
     * @param custManagerId value to be assigned to property custManagerId
     */
    public void setCustManagerId(String custManagerId) {
        this.custManagerId = custManagerId;
    }

    /**
     * Getter method for property <tt>managerType</tt>.
     * 
     * @return property value of managerType
     */
    public EnumManagerType getManagerType() {
        return managerType;
    }

    /**
     * Setter method for property <tt>managerType</tt>.
     * 
     * @param managerType value to be assigned to property managerType
     */
    public void setManagerType(EnumManagerType managerType) {
        this.managerType = managerType;
    }

    /**
     * Getter method for property <tt>creditLevel</tt>.
     * 
     * @return property value of creditLevel
     */
    public String getCreditLevel() {
        return creditLevel;
    }

    /**
     * Setter method for property <tt>creditLevel</tt>.
     * 
     * @param creditLevel value to be assigned to property creditLevel
     */
    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    /**
     * Getter method for property <tt>realnameFlag</tt>.
     * 
     * @return property value of realnameFlag
     */
    public EnumBool getRealnameFlag() {
        return realnameFlag;
    }

    /**
     * Setter method for property <tt>realnameFlag</tt>.
     * 
     * @param realnameFlag value to be assigned to property realnameFlag
     */
    public void setRealnameFlag(EnumBool realnameFlag) {
        this.realnameFlag = realnameFlag;
    }

    public EnumObjectStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(EnumObjectStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

}
