/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 特殊名单审核状态
 * @author hongsj
 * @version $Id: EnumListVerifyStatus.java, v 1.0 2017年3月24日 下午5:22:14 hongsj Exp $
 */
public enum EnumListVerifyStatus {
    NOT_AUDIT("0", "未审核"), 
    UNDER_REVIEW("1", "审核中"),
    AUDITED("2","已审核"),
    VETOED("3","拒绝名单加入"),
    CANCEL_NOT_AUDIT("4","撤销未审核"),
    CANCEL_AUDITED("5","已撤销");
    
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
    private EnumListVerifyStatus(String code, String description) {
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
    public static EnumListVerifyStatus find(String code) {
        for (EnumListVerifyStatus frs : EnumListVerifyStatus.values()) {
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
