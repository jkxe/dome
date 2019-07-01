package com.hsjry.user.facade.pojo.modify;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 收货人信息
 * 
 * @author jiangjd12837
 * @version $Id: UserConsigneeInfoPojo.java, v 1.0 2017年3月13日 下午4:52:27 jiangjd12837 Exp $
 */
public class UserConsigneeInfoModifyPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 6045031914288880322L;
    //收货人信息ID
    @NotNull(errorCode = "000001", message = "收货人信息ID")
    @NotBlank(errorCode = "000001", message = "收货人信息ID")
    private String            consigneeInfoId;
    //收货人姓名
    private String            consigneeName;
    //收货人电话
    private String            consigneePhone;

    /**
     * Getter method for property <tt>consigneeInfoId</tt>.
     * 
     * @return property value of consigneeInfoId
     */
    public String getConsigneeInfoId() {
        return consigneeInfoId;
    }

    /**
     * Setter method for property <tt>consigneeInfoId</tt>.
     * 
     * @param consigneeInfoId value to be assigned to property consigneeInfoId
     */
    public void setConsigneeInfoId(String consigneeInfoId) {
        this.consigneeInfoId = consigneeInfoId;
    }

    /**
     * Getter method for property <tt>consigneeName</tt>.
     * 
     * @return property value of consigneeName
     */
    public String getConsigneeName() {
        return consigneeName;
    }

    /**
     * Setter method for property <tt>consigneeName</tt>.
     * 
     * @param consigneeName value to be assigned to property consigneeName
     */
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    /**
     * Getter method for property <tt>consigneePhone</tt>.
     * 
     * @return property value of consigneePhone
     */
    public String getConsigneePhone() {
        return consigneePhone;
    }

    /**
     * Setter method for property <tt>consigneePhone</tt>.
     * 
     * @param consigneePhone value to be assigned to property consigneePhone
     */
    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

}
