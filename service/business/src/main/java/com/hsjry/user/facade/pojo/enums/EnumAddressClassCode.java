/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 地址分类代码
 * 
 * @author jiangjd12837
 * @version $Id: EnumAddressClassCode.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumAddressClassCode {
    
    REGISTERED_ADDRESS("11", "注册地址"),
    BUSINESS_ADDRESS("12", "经营地址"),
    ADMINISTRATIVE_DIVISION("13", "行政区域"),
    UNIT_ADDRESS("21", "单位地址"),
    RESIDENTIAL_ADDRESS("22", "住宅(居住)地址"),
    PERMANENT_ADDRESS("23", "户籍地址"),
    CERTIFICATE_ADDRESS("24", "证件地址"),
    SCHOOL_ADDRESS("25", "学校地址"),
    POST_BOX("31", "邮政信箱"),
    POSTAL_ADDRESS("32", "通信地址"),
    OTHER_ADDRESS("33", "其他地址"),
    OWN_HOUSE_TYPE("34", "自有房产地址"),
    BUY_HOUSE_ADDRESS("35","购房地址"),
    DECORATE_ADDRESS("36","装修地址");


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
    private EnumAddressClassCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumBearingMode} 实例
    **/
    public static EnumAddressClassCode find(String code) {
        for (EnumAddressClassCode frs : EnumAddressClassCode.values()) {
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
