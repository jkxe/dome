package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumInfoType;
/**
 * 征信信息
 * 
 * @author wanglg15468
 * @version $Id: UserCreditInfoPojo.java, v 1.0 2017-3-16 上午9:06:10 wanglg15468 Exp $
 */
public class UserCreditInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -7015000912055564730L;
  //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //信息类别
    private EnumInfoType infoType;
    //信息描述
    private String infoDescribe;
    //信息来源
    private String infoSource;
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
     * Getter method for property <tt>infoType</tt>.
     * 
     * @return property value of infoType
     */
    public EnumInfoType getInfoType() {
        return infoType;
    }
    /**
     * Setter method for property <tt>infoType</tt>.
     * 
     * @param infoType value to be assigned to property infoType
     */
    public void setInfoType(EnumInfoType infoType) {
        this.infoType = infoType;
    }
    /**
     * Getter method for property <tt>infoDescribe</tt>.
     * 
     * @return property value of infoDescribe
     */
    public String getInfoDescribe() {
        return infoDescribe;
    }
    /**
     * Setter method for property <tt>infoDescribe</tt>.
     * 
     * @param infoDescribe value to be assigned to property infoDescribe
     */
    public void setInfoDescribe(String infoDescribe) {
        this.infoDescribe = infoDescribe;
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

}
