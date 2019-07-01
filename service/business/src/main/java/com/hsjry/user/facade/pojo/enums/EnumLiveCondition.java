/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 居住情况
 * 
 * @author jiangjd12837
 * @version $Id: EnumLiveCondition.java, v 1.0 2017年3月14日 上午11:21:59 jiangjd12837 Exp $
 */
public enum EnumLiveCondition {
    
    HAVE("1", "自有"),
    LEASE("2", "租赁"),
    LIVE_WITH_PARENTS("3", "与父母同住"),
    FAMILY_HOUSE("4", "亲属住房"),
    ZYFSP("5", "自有非商品"),
    ZYWAJ_HOUSE("6", "自有无按揭商品房"),
    ZYAJ_HOUSE("7", "自有有按揭商品房"),
    OTHER("8", "其他");
    
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
    private EnumLiveCondition(String code, String description) {
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
    public static EnumLiveCondition find(String code) {
        for (EnumLiveCondition frs : EnumLiveCondition.values()) {
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
