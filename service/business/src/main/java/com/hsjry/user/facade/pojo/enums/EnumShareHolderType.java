/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 
 * @author zhengqy15963
 * @version $Id: EnumShareHolderType.java, v 1.0 2018年4月23日 下午2:05:14 zhengqy15963 Exp $
 */
public enum EnumShareHolderType {
    
    STATEOWNED("0","国有"),
    LIST("1","上市"),
    NONGOVERNMENTAL("2","民营"),
    PERSONAL("3","个人"),
    OTHER("4","其他");

    
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
    private EnumShareHolderType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumMerchantUserType} 实例
    **/
    public static EnumShareHolderType find(String code) {
        for (EnumShareHolderType frs : EnumShareHolderType.values()) {
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
