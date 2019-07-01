/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 行业分类
 * 
 * @author wanglg15468
 * @version $Id: EnumIndustryType.java, v 1.0 2017-3-15 上午9:01:06 wanglg15468 Exp $
 */
public enum EnumIndustryLabel {
                               
    DIGITAL_APPLIANCE("1", "数码电器"),
    AUTO_REPAIR_BEAUTY("2", "汽车维修美容"),
    BODY_BEAUTY("3", "美体美容"),
    HOME_BUILDING("4", "家居建材"),
    DECORATION_COMPANY("5", "装修公司"),
    EDUCATION_TRAINING("6", "教育培训"),
    THE_WEDDING("7", "婚庆"),
    AUTO_ELECTRIC_MOTORCYCLE_SALES("8", "汽车电动车摩托车销售"),
    RESTAURANT("9", "餐饮"),
    DISCOUNT_KTV("10", "量贩KTV"),
    SPORTS_GOODS("11", "体育用品"),
    GAME_CARD("12", "游戏点卡"),
    CINEMA("13", "电影院");
    
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
    private EnumIndustryLabel(String code, String description) {
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
    public static EnumIndustryLabel find(String code) {
        for (EnumIndustryLabel frs : EnumIndustryLabel.values()) {
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
