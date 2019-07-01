package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;
import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import net.sf.oval.constraint.NotNull;

/**
 * 联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserContactStationPojo.java, v 1.0 2017年3月13日 下午4:52:34 jiangjd12837 Exp $
 */
public class UserContactStationInfoPojo implements Serializable {

    /**  */
    private static final long          serialVersionUID = -9122122017744771376L;
    //联系点ID
    private String                     stationId;
    //客户角色ID
    private String                     custRoleId;
    //客户ID
    private String                     userId;
    //联系点类型
    @NotNull(errorCode = "000001", message = "联系点类型")
    private EnumContactStationTypeCode contactStationType;
    //联系点状态
    @NotNull(errorCode = "000001", message = "联系点状态")
    private EnumObjectStatus           contactStationStatus;
    //是否默认联系点
    @NotNull(errorCode = "000001", message = "是否默认联系点")
    private EnumBool                   defaultStationFlag;
    //是否验证
    @NotNull(errorCode = "000001", message = "是否验证")
    private EnumBool                   validateFlag;

    /**
     * Getter method for property <tt>contactStationStatus</tt>.
     * 
     * @return property value of contactStationStatus
     */
    public EnumObjectStatus getContactStationStatus() {
        return contactStationStatus;
    }

    /**
     * Setter method for property <tt>contactStationStatus</tt>.
     * 
     * @param contactStationStatus value to be assigned to property contactStationStatus
     */
    public void setContactStationStatus(EnumObjectStatus contactStationStatus) {
        this.contactStationStatus = contactStationStatus;
    }

    /**
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
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
     * Getter method for property <tt>contactStationType</tt>.
     * 
     * @return property value of contactStationType
     */
    public EnumContactStationTypeCode getContactStationType() {
        return contactStationType;
    }

    /**
     * Setter method for property <tt>contactStationType</tt>.
     * 
     * @param contactStationType value to be assigned to property contactStationType
     */
    public void setContactStationType(EnumContactStationTypeCode contactStationType) {
        this.contactStationType = contactStationType;
    }

    /**
     * Getter method for property <tt>defaultStationFlag</tt>.
     * 
     * @return property value of defaultStationFlag
     */
    public EnumBool getDefaultStationFlag() {
        return defaultStationFlag;
    }

    /**
     * Setter method for property <tt>defaultStationFlag</tt>.
     * 
     * @param defaultStationFlag value to be assigned to property defaultStationFlag
     */
    public void setDefaultStationFlag(EnumBool defaultStationFlag) {
        this.defaultStationFlag = defaultStationFlag;
    }

    /**
     * Getter method for property <tt>validateFlag</tt>.
     * 
     * @return property value of validateFlag
     */
    public EnumBool getValidateFlag() {
        return validateFlag;
    }

    /**
     * Setter method for property <tt>validateFlag</tt>.
     * 
     * @param validateFlag value to be assigned to property validateFlag
     */
    public void setValidateFlag(EnumBool validateFlag) {
        this.validateFlag = validateFlag;
    }
}
