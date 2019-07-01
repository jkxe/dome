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
public enum EnumIndustryType {
                               
    AGRI_FORESTRY_ANIMAL_FISHERY("A", "农、林、牧、渔业"),
    MINING_INDUSTRY("B", "采矿业"),
    MANUFACTURING_INDUSTRY("C", "制造业"),
    ELECTRICITY_PRODUCTION_SUPPLY("D", "电力、热力、燃气及水生产和供应业"),
    CONSTRUCTION_INDUSTRY("E", "建筑业"),
    TRANSPORTATION_INDUSTRY("F", "交通运输、仓储和邮政业"),
    SOFTWARE_IT_SERVICES("G", "信息传输、软件和信息技术服务业"),
    WHOLESALE_RETAIL("H", "批发和零售业"),
    CATERING_INDUSTRY("I", "住宿和餐饮业"),
    FINANCE_INDUSTRY("J", "金融业"),
    REALTY_INDUSTRY("K", "房地产业"),
    BUSINESS_SERVICES("L", "租赁和商务服务业"),
    TECHNICAL_SERVICE_INDUSTRY("M", "科学研究和技术服务业"),
    PUBLIC_FACILITIES_MANAGEMENT("N", "水利、环境和公共设施管理业"),
    RESIDENT_SERVICES_INDUSTRY("O", "居民服务、修理和其他服务业"),
    EDUCATION_INDUSTRY("P", "教育"),
    SOCIAL_WORK("Q", "卫生和社会工作"),
    SPORTS_ENTERTAINMENT("R", "文化和体育娱乐业"),
    SOCIAL_ORGANIZATIONS("S", "公共管理、社会保障和社会组织"),
    INTERNATIONAL_ORGANIZATION("T", "国际组织"),
    OTHER("Z","未知");
    
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
    private EnumIndustryType(String code, String description) {
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
    public static EnumIndustryType find(String code) {
        for (EnumIndustryType frs : EnumIndustryType.values()) {
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
