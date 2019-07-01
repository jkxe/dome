package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumEmailClassCode;

/**
 * 邮箱联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserEmailContactStationPojo.java, v 1.0 2017年3月13日 下午4:52:49 jiangjd12837 Exp $
 */
public class UserEmailMaintainPojo implements Serializable {

    /**  */
    private static final long  serialVersionUID = -6754797785416610782L;
    //联系点ID
    private String             stationId;
    //客户角色ID
    private String             custRoleId;
    //客户ID
    private String             userId;
    //邮箱分类代码
    @NotNull(errorCode = "000001", message = "邮箱分类代码")
    private EnumEmailClassCode emailClassCode;
    //邮箱地址
    @NotNull(errorCode = "000001", message = "邮箱地址")
    @NotBlank(errorCode = "000001", message = "邮箱地址")
    private String             email;

    /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return property value of email
     */
    public String getEmail() {
        return email;
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
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public EnumEmailClassCode getEmailClassCode() {
        return emailClassCode;
    }

    public void setEmailClassCode(EnumEmailClassCode emailClassCode) {
        this.emailClassCode = emailClassCode;
    }
}
