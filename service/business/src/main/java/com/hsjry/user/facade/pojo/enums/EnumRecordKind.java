/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;
/**

 * 注册登记类型
 * 
 * 
 * @author jiangjd12837
 * @version $Id: EnumRecordKind.java, v 1.0 2017年4月11日 下午12:08:47 jiangjd12837 Exp $
 */
public enum EnumRecordKind {
    
    Party_and_government_organizations("A","党政机关"),
    ARMY("C","军队"),
    ENTERPRISE_WITH_DOMESTIC_FUNDING("E","内资企业"),
    STATE_OWNED_ENTERPRISE("F","国有企业"),
    PRIVATE_ENTERPRISE("L","私营企业"),
    FOREIGN_INVESTED_ENTERPRISES("M","外商投资企业(含港、澳、台)"),
    SINO_FOREIGN_JOINT_VENTURES("N","中外合资经营企业(含港、澳、台)"),
    SINO_FOREIGN_COOPERATIVE_ENTERPRISES("O","中外合作经营企业(含港、澳、台)"),
    FOREIGN_FUNDED_ENTERPRISES("P","外资企业(含港、澳、台)"),
    COMPANIES_LIMITED_BY_SHARES_WITH_FOREIGN_INVESTMENT("Q","外商投资股份有限公司(含港、澳、台)"),
    INDIVIDUAL_MANAGEMENT("R","个体经营"),
    COMPANY_WITH_LIMITED_LIABILITY("1", "有限责任公司"),//J
    LIMITED_COMPANY("2", "股份有限公司"),//K
    UNIVERSAL_SYSTEM("3", "全民制"),
    STOCK_COOPERATIVE_SYSTEM("4", "股份合作制"),//H
    PARTNERSHIP("5", "合伙企业"),
    SOLE_PROPRIETORSHIP("6", "个人独资企业"),
    JOINT_VENTURE("7", "联营企业"),//I
    COLLECTIVE_ENTERPRISE("8", "集体企业"),//G
    GOVERNMENT_AFFILIATED_INSTITUTIONS("9", "事业单位"),//B
    CORPORATION("10", "社团法人"),//D
    FARMER_SPECIALIZED_COOPERATIVE_ECONOMIC_ORGANIZATION("11", "农民专业合作经济组织"),
    FARMER_SPECIALIZED_COOPERATIVE_ECONOMIC_ORGANIZATION_BRANCH("12", "农民专业合作经济组织分支机构"),
    OTHER("x", "其他");//Z
    
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
    private EnumRecordKind(String code, String description) {
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
    public static EnumRecordKind find(String code) {
        for (EnumRecordKind frs : EnumRecordKind.values()) {
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
