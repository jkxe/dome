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
public enum EnumResidenceType {
    
    AFFORDABLE_HOUSING("A", "经济适用住房"),
    LOW_RENT_HOUSING("B", "廉租房"),
    HOUSING_REFORM_HOUSE("C", "房改房"),
    ANJUFANG("D", "安居房"),
    FUND_RAISING_HOUSE("E", "集资房"),
    COMMODITY_HOUSE("F", "商品房"),
    CAPPED_PRICE_HOUSING("G", "限价房"),
    VILLA("H", "别墅"),
    SHOPS("I", "商铺"),
    OTHER("Z", "其他");
    
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
    private EnumResidenceType(String code, String description) {
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
    public static EnumResidenceType find(String code) {
        for (EnumResidenceType frs : EnumResidenceType.values()) {
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
