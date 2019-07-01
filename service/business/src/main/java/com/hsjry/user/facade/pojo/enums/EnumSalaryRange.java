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
public enum EnumSalaryRange {
    
    BELOW_THREE_THOUSAND("1", "3000人以下"),
    THREE_THOUSAND_TO_FOUR_THOUSAND_AND_FIVE_HUNDRED("2", "3001-4500"),
    FOUR_THOUSAND_AND_FIVE_HUNDRED_TO_SIX_THOUSAND("3", "4501-6000"),
    SIX_THOUSAND_TO_EIGHT_THOUSAND("4", "6001-8000"),
    EIGHT_THOUSAND_TO_TEN_THOUSAND("4", "8001-10000"),
    TEN_THOUSAND_TO_TWELVE_THOUSAND("4", "10001-12000"),
    TWELVE_THOUSAND_TO_TWENTY_THOUSAND("4", "12001-20000"),
    MORE_THAN_TWELVE_THOUSAND("5", "20000以上");
    
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
    private EnumSalaryRange(String code, String description) {
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
    public static EnumSalaryRange find(String code) {
        for (EnumSalaryRange frs : EnumSalaryRange.values()) {
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
