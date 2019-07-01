/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 回收站数据类型枚举
 * @author hongsj
 * @version $Id: EnumRecycleTypeCode.java, v 1.0 2017年3月3日 下午4:28:32 hongsj Exp $
 */
public enum EnumRecycleType {

    USER_MENU("user_menu_info","用户菜单"), 
    USER_ORGANIZATION("user_organization_info", "用户组织");

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
    private EnumRecycleType(String code, String description) {
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
    public static EnumRecycleType find(String code) {
        for (EnumRecycleType frs : EnumRecycleType.values()) {
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
