/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 惠付证件类型
 * @author huangbb
 * @version $Id: EnumIdKind.java, v 1.0 2017年5月17日 下午7:50:31 huangbb Exp $
 */
public enum EnumIdKind {

    RESIDENT_IDENTITY_CARD("0","居民身份证","0"),
    BUSINESS_LICENSE("01", "全国法人营业执照","2"),
    ORGANIZATION_CODE_CERT("07", "全国组织机构代码证书","P"),
    SOCIAL_CREDIT_CODE("18", "统一社会信用代码","z");
    
    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    private String yrhfCode;

    /**
    * 私有构造方法
    * 
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumIdKind(String code, String description, String yrhfCode) {
        this.code = code;
        this.description = description;
        this.yrhfCode = yrhfCode;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumBearingMode} 实例
    **/
    public static EnumIdKind find(String code) {
        for (EnumIdKind frs : EnumIdKind.values()) {
            if (frs.getCode().equals(code)) {
                return frs;
            }
        }
        return null;
    }

    /**
    * Getter method for property <tt>code</tt>.
    * 
    * @return property value of code
    **/
    public String getCode() {
        return code;
    }

    /**
    * Getter method for property <tt>description</tt>.
    * 
    * @return property value of description
    **/
    public String getDescription() {
        return description;
    }

    /**
     * Getter method for property <tt>yrhfCode</tt>.
     * 
     * @return property value of yrhfCode
     */
    public String getYrhfCode() {
        return yrhfCode;
    }

    /**
     * Setter method for property <tt>yrhfCode</tt>.
     * 
     * @param yrhfCode value to be assigned to property yrhfCode
     */
    public void setYrhfCode(String yrhfCode) {
        this.yrhfCode = yrhfCode;
    }


}
