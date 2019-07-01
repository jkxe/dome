/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 根节点枚举
 * @author hongsj
 * @version $Id: EnumRootId.java, v 1.0 2017年3月29日 下午2:49:26 hongsj Exp $
 */
public enum EnumRootId {

    MENU_ROOT_ID("1000", "菜单树根节点id"),
    MANAGE_ORGAN_ROOT_ID("2000","管理台组织根节点id"),
    CUSTOMER_MANAGER_ROOT_ID("3000","客户经理组织根节点id");
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
    private EnumRootId(String code, String description) {
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
    public static EnumRootId find(String code) {
        for (EnumRootId frs : EnumRootId.values()) {
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
