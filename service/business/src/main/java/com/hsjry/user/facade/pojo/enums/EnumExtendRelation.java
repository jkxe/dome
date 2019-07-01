/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 关系扩展类型
 * 
 * @author jiangjd12837
 * @version $Id: RecordStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumExtendRelation {
    
    SHAREHOLDER_TYPE("1", "股东类型"),
    INVESTMENT_AMOUNT("2", "出资金额"),
    CURRENCY_TYPE("3", "币种"),
    EQUITY_PROPORTION("4", "股权比例"),
    DUTIES("5", "职务"),
    START_DATE("6", "任职开始日期"),
    END_DATE("7", "任职结束日期"),
    EMPLOYEE_NUMBER("8", "员工号"),
    Term_of_office("9", "任期");
    
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
    private EnumExtendRelation(String code, String description) {
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
    public static EnumExtendRelation find(String code) {
        for (EnumExtendRelation frs : EnumExtendRelation.values()) {
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
