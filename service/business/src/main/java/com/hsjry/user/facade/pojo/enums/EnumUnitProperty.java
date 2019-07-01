/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 单位性质
 * 
 * @author jiangjd12837
 * @version $Id: EnumActiveStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumUnitProperty {
    
    PARTY_AND_GOVERNMENT_ORGANS("A", "党政机关"),
    INSTITUTIONS("B", "事业单位"),
    ARMY("C", "军队"),
    SOCIAL_GROUP("D", "社会团体"),
    DOMESTIC_ENTERPRISES("E", "内资企业"),
    STATE_OWNED_ENTERPRISES("F", "国有企业"),
    COLLECTIVE_ENTERPRISE("G", "集体企业"),
    STOCK_COOPERATIVE_ENTERPRISE("H", "股份合作企业"),
    ASSOCIATES("I", "联营企业"),
    LIMITED_LIABILITY_COMPANY("J", "有限责任公司"),
    LIMITED_BY_SHARE_LTD("K", "股份有限公司"),
    PRIVATE_ENTERPRISE("L", "私营企业"),
    FOREIGN_INVESTED_ENTERPRISES("M", "外商投资企业(含港、澳、台)"),
    SINO_FOREIGN_JOINT("N", "中外合资经营企业(含港、澳、台)"),
    SINO_FOREIGN_COOPERATIVE("O", "中外合作经营企业(含港、澳、台)"),
    FOREIGN_FUNDED_ENTERPRISES("P", "外资企业(含港、澳、台)"),
    COMPANIES_LIMITED("Q", "外商投资股份有限公司(含港、澳、台)"),
    SELF_EMPLOYED("R", "个体经营"),
    OTHER("Z", "其他"),



    PRIVATE_ENTREPRENEUR_INDIVIDUAL_BUSINESS("001", "私营企业主-个体工商户"),
    PRIVATE_ENTREPRENEUR_BUSINESS_ENTITY("002", "私营企业主-企业法人"),
    NETWORK_SELLERS_TAOBAO("003", "网点卖家-淘宝店主"),
    NETWORK_SELLERS_WEDIAN("004", "网点卖家-微店"),
    NETWORK_SELLERS_OTHER("005", "网点卖家-其他网点"),
    FREELANCER_JOBLESS("006", "自由职业-无业"),
    FREELANCER_FARMING("007", "自由职业-务农"),
    FREELANCER_HOUSEWIFE("008", "自由职业-家庭主妇/主夫"),
    FREELANCER_TIMESHEET("009", "自由职业-钟点工"),
    FREELANCER_TEMPORARY_WORKERS("010", "自由职业-临时工"),
    FREELANCER_PORTFOLIO_INVESTMENT("011", "自由职业-证券投资"),
    FREELANCER_INDIVIDUAL_TRANSPORTATION("012", "自由职业-个体运输"),
    FREELANCER_ITINERANT_VENDOR("013", "自由职业-流动摊贩"),
    FREELANCER_GAME_LEVELING("014", "自由职业-游戏代练"),
    FREELANCER_OTHER("015", "自由职业-其它"),
    SALARIED_GOVERNMENT("016", "工薪族-政府机关"),
    SALARIED_KINDERGARTEN("017", "工薪族-学校-幼儿园"),
    SALARIED_PRIMARY_SCHOOL("018", "工薪族-学校-小学"),
    SALARIED_MIDDLE_SCHOOL("019", "工薪族-学校-初中"),
    SALARIED_HIGH_SCHOOL("020", "工薪族-学校-高中"),
    SALARIED_TECHNICAL_SCHOOL("021", "工薪族-学校-技校"),
    SALARIED_SECONDARY_SCHOOL("022", "工薪族-学校-中专"),
    SALARIED_UNIVERSITY("023", "工薪族-学校-大学"),
    SALARIED_SCHOOL_OTHER("024", "工薪族-学校-其它"),
    SALARIED_PRIVATE_HOSPITAL("025", "工薪族-医院-民办"),
    SALARIED_HOSPITAL_ONE("026", "工薪族-医院-1级"),
    SALARIED_HOSPITAL_TWO("027", "工薪族-医院-2级"),
    SALARIED_HOSPITAL_THREE("028", "工薪族-医院-3级"),
    SALARIED_HOSPITAL_OTHER("029", "工薪族-医院-其它"),
    SALARIED_STATE_OWNED_ENTERPRISES("030", "工薪族-企业公司-国有"),
    SALARIED_LIMITED_BY_SHARE_LTD("031", "工薪族-企业公司-股份"),
    SALARIED_PRIVATE_ENTERPRISE("032", "工薪族-企业公司-私营"),
    SALARIED_FOREIGN_FUNDED_ENTERPRISES("033", "工薪族-企业公司-外资"),
    SALARIED_ASSOCIATES("034", "工薪族-企业公司-合资"),
    SALARIED_ENTERPRISES_OTHER("035", "工薪族-企业公司-其它"),
    SALARIED_PRIVATE_BUSINESSES("036", "工薪族-私营商铺"),
    FINANCIAL("037", "金融 "),
    TELECOM("038", "电信"),
    ELECTRIC_POWER("039", "电力"),
    TABOCCO("040", "烟草"),
    REFINING("041", "炼化"),
    PORT("042", "港务"),
    WATER_SERVICE("043", "水务"),
    AVIATION("044", "航空"),
    FIRM("045", "律师、会计师等")

    ;

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
    private EnumUnitProperty(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumUnitProperty} 实例
    **/
    public static EnumUnitProperty find(String code) {
        for (EnumUnitProperty frs : EnumUnitProperty.values()) {
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
