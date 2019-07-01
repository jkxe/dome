/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 职业    
 * 
 * @author jiangjd12837
 * @version $Id: EnumActiveStatus.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumVocationType {
    INSTITUTION("0", "国家机关、党群组织、企业、事业单位负责人"),
    TECHNICAL_PERSONNEL("1", "专业技术人员"),
    STAFF_PERSONNEL("3", "办事人员和有关人员"),
    SERVICE_PERSONNEL("4", "商业、服务业人员"),
    PRODUCTION_PERSONNEL("5", "农、林、牧、渔、水利业生产人员"),
    OPERATOR_PERSONNEL("6", "生产、运输设备操作人员及有关人员"),
    SOLDIER("X", "军人"),
    INCONVENIENCE_CLASSIFICATION("Y", "不便分类的其他从业人员"),
    UNKNOWN("Z", "未知"),
    //
    STUDENT("21", "学生"),
    SELF_EMPLOYED_PERSON("22", "个体户/企业负责人"),
    LIBERAL_PROFESSIONS("23", "无业/退休/自由职业"),
    SALE("24", "销售/中介/保险代理人"),
    WORKER("25", "工人"),
    CIVIL_SERVANT("28", "公务员/事业单位公职人员"),
    ENTERPRISE_GENERAL_STAFF("29", "企业一般员工"),
    MANAGEMENT_LAYER("20", "企业管理层"),


    MANAGER("30","经理"),
    VICE_GENERAL_MANAGER("31", "副总经理"),
    MAJORDOMO("32", "总监"),
    DEPUTY_DIRECTOR("33", "副总监"),
    SUPERVISOR("34", "主管"),
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
    private EnumVocationType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumVocationType} 实例
    **/
    public static EnumVocationType find(String code) {
        for (EnumVocationType frs : EnumVocationType.values()) {
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
