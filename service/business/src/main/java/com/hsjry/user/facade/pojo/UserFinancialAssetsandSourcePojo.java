package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumResourceSource;

/**金融资产
 * 
 * @author liaosq23298
 * @version $Id: UserFinancialAssetsandSourcePojo.java, v 0.1 Nov 22, 2017 3:24:19 PM liaosq23298 Exp $
 */
public class UserFinancialAssetsandSourcePojo implements Serializable{
    /**  */
    private static final long serialVersionUID = 7123700841590711545L;
    //资源项ID
    private String resourceId;
    //客户ID
    private String userId;
    //资产类型 
    private String assetType;
    //资产金额
    private String assetAmount;
    //资产到期日
    private String assetValidityPeriod;
    //资产状态
    private String assetStatus;
    //资源来源
    private EnumResourceSource resourceSource;
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
     * Getter method for property <tt>assetType</tt>.
     * 
     * @return property value of assetType
     */
    public String getAssetType() {
        return assetType;
    }
    /**
     * Setter method for property <tt>assetType</tt>.
     * 
     * @param assetType value to be assigned to property assetType
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }
    /**
     * Getter method for property <tt>assetAmount</tt>.
     * 
     * @return property value of assetAmount
     */
    public String getAssetAmount() {
        return assetAmount;
    }
    /**
     * Setter method for property <tt>assetAmount</tt>.
     * 
     * @param assetAmount value to be assigned to property assetAmount
     */
    public void setAssetAmount(String assetAmount) {
        this.assetAmount = assetAmount;
    }
    /**
     * Getter method for property <tt>assetValidityPeriod</tt>.
     * 
     * @return property value of assetValidityPeriod
     */
    public String getAssetValidityPeriod() {
        return assetValidityPeriod;
    }
    /**
     * Setter method for property <tt>assetValidityPeriod</tt>.
     * 
     * @param assetValidityPeriod value to be assigned to property assetValidityPeriod
     */
    public void setAssetValidityPeriod(String assetValidityPeriod) {
        this.assetValidityPeriod = assetValidityPeriod;
    }
    /**
     * Getter method for property <tt>assetStatus</tt>.
     * 
     * @return property value of assetStatus
     */
    public String getAssetStatus() {
        return assetStatus;
    }
    /**
     * Setter method for property <tt>assetStatus</tt>.
     * 
     * @param assetStatus value to be assigned to property assetStatus
     */
    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
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
