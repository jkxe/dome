package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumImmovableType;
import com.hsjry.user.facade.pojo.enums.EnumResidenceType;

/**
 * 不动产资源
 * 
 * @author wanglg15468
 * @version $Id: UserRealEstateResourcesPojo.java, v 1.0 2017-3-15 下午7:19:27 wanglg15468 Exp $
 */
public class UserRealEstateResourcesPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 6355159366799752946L;
    //资源项ID
    private String            resourceId;
    //客户ID
    private String            userId;
    //拥有日期
    private String            getDate;
    //到期时间
    private String            expireDate;
    //付出价格
    private String            payCost;
    //地址
    private String            address;
    //占地面积
    private String            areaCovered;
    //实际经营面积
    private String            areaManage;
    //所有人
    private String            resourceOwner;
    //不动产类型
    private EnumImmovableType resourceType;
    //住宅类型
    private EnumResidenceType residenceType;

    /**
     * Getter method for property <tt>residenceType</tt>.
     * 
     * @return property value of residenceType
     */
    public EnumResidenceType getResidenceType() {
        return residenceType;
    }

    /**
     * Setter method for property <tt>residenceType</tt>.
     * 
     * @param residenceType value to be assigned to property residenceType
     */
    public void setResidenceType(EnumResidenceType residenceType) {
        this.residenceType = residenceType;
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
     * Getter method for property <tt>getDate</tt>.
     * 
     * @return property value of getDate
     */
    public String getGetDate() {
        return getDate;
    }

    /**
     * Setter method for property <tt>getDate</tt>.
     * 
     * @param getDate value to be assigned to property getDate
     */
    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    /**
     * Getter method for property <tt>expireDate</tt>.
     * 
     * @return property value of expireDate
     */
    public String getExpireDate() {
        return expireDate;
    }

    /**
     * Setter method for property <tt>expireDate</tt>.
     * 
     * @param expireDate value to be assigned to property expireDate
     */
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * Getter method for property <tt>payCost</tt>.
     * 
     * @return property value of payCost
     */
    public String getPayCost() {
        return payCost;
    }

    /**
     * Setter method for property <tt>payCost</tt>.
     * 
     * @param payCost value to be assigned to property payCost
     */
    public void setPayCost(String payCost) {
        this.payCost = payCost;
    }

    /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     * 
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>areaCovered</tt>.
     * 
     * @return property value of areaCovered
     */
    public String getAreaCovered() {
        return areaCovered;
    }

    /**
     * Setter method for property <tt>areaCovered</tt>.
     * 
     * @param areaCovered value to be assigned to property areaCovered
     */
    public void setAreaCovered(String areaCovered) {
        this.areaCovered = areaCovered;
    }

    /**
     * Getter method for property <tt>areaManage</tt>.
     * 
     * @return property value of areaManage
     */
    public String getAreaManage() {
        return areaManage;
    }

    /**
     * Setter method for property <tt>areaManage</tt>.
     * 
     * @param areaManage value to be assigned to property areaManage
     */
    public void setAreaManage(String areaManage) {
        this.areaManage = areaManage;
    }

    /**
     * Getter method for property <tt>resourceOwner</tt>.
     * 
     * @return property value of resourceOwner
     */
    public String getResourceOwner() {
        return resourceOwner;
    }

    /**
     * Setter method for property <tt>resourceOwner</tt>.
     * 
     * @param resourceOwner value to be assigned to property resourceOwner
     */
    public void setResourceOwner(String resourceOwner) {
        this.resourceOwner = resourceOwner;
    }

    /**
     * Getter method for property <tt>resourceType</tt>.
     * 
     * @return property value of resourceType
     */
    public EnumImmovableType getResourceType() {
        return resourceType;
    }

    /**
     * Setter method for property <tt>resourceType</tt>.
     * 
     * @param resourceType value to be assigned to property resourceType
     */
    public void setResourceType(EnumImmovableType resourceType) {
        this.resourceType = resourceType;
    }

}
