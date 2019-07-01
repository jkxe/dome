/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 资源类型
 * @author hongsj
 * @version $Id: EnumResourceTypeCode.java, v 1.0 2017年3月7日 下午1:44:52 hongsj Exp $
 */
public enum EnumResourceType {

    CERTIFICATES("0", "证件"),
    FINANCIAL_INSTRUMENTS("1", "金融工具"),
    IMAGE_DATA("2","影像资料"),
    VEHICLE("3","车辆"),
    IMMOVABLE_PROPERTY("4","不动产"),
    EDUCATION("5","教育"),
    PROFESSION("6","职业"),
    MANAGEMENT("7", "经营"),
    INCOME("8", "收入"),
    FINANCIAL_ASSETS("9", "金融资产"),
    PARTNER("10", "合作方"),
    FINANCIAL_STATEMENTS("11", "财务报表"),
    ACCUMULATION_FUND("12", "公积金"),
    PAY_TAXES("13", "缴税"),
    JUDICIAL_LITIGATION("14", "司法诉讼"),
    SOCIAL_SECURITY("15", "社保"),
    CREDIT_HISTORICAL("16", "历史信贷"),
    CREDIT_INVESTIGATION("17", "征信"),
    MAJOR_EVENT("18", "大事件"),
    CREDIT_INFO_APPLYINFO("19","授信申请记录");

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
    private EnumResourceType(String code, String description) {
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
    public static EnumResourceType find(String code) {
        for (EnumResourceType frs : EnumResourceType.values()) {
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
