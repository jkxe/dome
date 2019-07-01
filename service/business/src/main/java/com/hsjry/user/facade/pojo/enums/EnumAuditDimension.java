/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 审核维度
 * @author hongsj
 * @version $Id: EnumAuditDimensionCode.java, v 1.0 2017年3月7日 下午4:48:52 hongsj Exp $
 */
public enum EnumAuditDimension {
    
    ROLE("0","角色"),
    CERTIFICATE("1","证件"),
    IMAGE_DATA("2","影像资料"),
    FINANCIAL_INSTRUMENTS("3","金融工具");
    
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
    private EnumAuditDimension(String code, String description) {
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
    public static EnumAuditDimension find(String code) {
        for (EnumAuditDimension frs : EnumAuditDimension.values()) {
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
