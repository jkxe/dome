/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 合作机构类型
 * 
 * @author wanglg15468
 * @version $Id: EnumPartnerType.java, v 1.0 2017-3-14 下午7:33:49 wanglg15468 Exp $
 */
public enum EnumPartnerType {
    
    FUND_SIDE("6","资金方"),
    ASSET_SIDE("7","资产方"),
    GUARANTEE_COMP("8","担保公司"),
    DATA_SERVICES_COMP("9","数据服务公司");

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
    private EnumPartnerType(String code, String description) {
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
    public static EnumPartnerType find(String code) {
        for (EnumPartnerType frs : EnumPartnerType.values()) {
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
