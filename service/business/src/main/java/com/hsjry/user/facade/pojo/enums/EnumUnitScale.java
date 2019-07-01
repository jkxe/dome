/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 账户类型
 * 
 * @author jiangjd12837
 * @version $Id: EnumActiveStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumUnitScale {
    
    UNDER_TEN_PEOPLE("1", "10人以下"),
    TEN_TO_FIFTY("2", "10-50人"),
    FIFTY_TO_ONE_HUNDRED("3", "50-100人"),
    ONE_HUNDRED_TO_FIVE_HUNDRED("4", "100-500人"),
    MORE_THAN_FIVE_HUNDRED("5", "500人以上");
    
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
    private EnumUnitScale(String code, String description) {
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
    public static EnumUnitScale find(String code) {
        for (EnumUnitScale frs : EnumUnitScale.values()) {
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
