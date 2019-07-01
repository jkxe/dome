package com.hsjry.user.facade.pojo;

public class ConsigneeInfoViewPojo extends UserAddressContactStationInfoPojo {

    /**  */
    private static final long serialVersionUID = 1454759071992192991L;
    //收货人姓名
    private String            consigneeName;
    //收货人电话
    private String            consigneePhone;

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
