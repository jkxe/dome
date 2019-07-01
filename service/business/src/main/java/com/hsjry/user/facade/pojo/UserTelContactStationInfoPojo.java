package com.hsjry.user.facade.pojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumTelephoneClassCode;

/**
 * 电话联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserTelephoneContactStationPojo.java, v 1.0 2017年3月13日 下午4:53:04 jiangjd12837 Exp $
 */
public class UserTelContactStationInfoPojo extends UserContactStationInfoPojo {

    /**  */
    private static final long      serialVersionUID = 1613145127452386615L;
    //电话分类代码
    @NotNull(errorCode = "000001", message = "电话分类代码")
    private EnumTelephoneClassCode telephoneClassCode;
    //区号
    private String                 areaCode;
    //电话号
    @NotNull(errorCode = "000001", message = "电话号")
    @NotBlank(errorCode = "000001", message = "电话号")
    private String                 telephone;

    /**
     * Getter method for property <tt>areaCode</tt>.
     * 
     * @return property value of areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Setter method for property <tt>areaCode</tt>.
     * 
     * @param areaCode value to be assigned to property areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * Getter method for property <tt>telephone</tt>.
     * 
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     * 
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public EnumTelephoneClassCode getTelephoneClassCode() {
        return telephoneClassCode;
    }

    public void setTelephoneClassCode(EnumTelephoneClassCode telephoneClassCode) {
        this.telephoneClassCode = telephoneClassCode;
    }
}
