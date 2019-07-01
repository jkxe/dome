/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 
 * @author zhengqy15963
 * @version $Id: EnumMerchantUserType.java, v 1.0 2018年4月23日 上午11:26:14 zhengqy15963 Exp $
 */
public enum EnumMerchantUserType {
    ELECTRONICS("0","家电及消费电子"),
    DEPARTMENTSTORES("1","百货"),
    RENOVATION("2","装修/家饰"),
    EDUCATIONALINSTITUTION("3","教育机构"),
    TRAVELAGENCY("4","旅游机构"),
    WEDDINGINSTITUTION("5","婚庆机构"),
    RENTING("6","租房"),
    MEDICAL("7","健康医疗"),
    OTHER("8","其他"),
    FURNITURE("9","家具家居类");
    
    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    /**
    * 私有构造方法
    * 
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumMerchantUserType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumMerchantUserType} 实例
    **/
    public static EnumMerchantUserType find(String code) {
        for (EnumMerchantUserType frs : EnumMerchantUserType.values()) {
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
}
