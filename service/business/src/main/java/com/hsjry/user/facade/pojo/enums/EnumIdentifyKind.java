/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 标识类型
 * 
 * @author wanglg15468
 * @version $Id: EnumIdentifyContent.java, v 1.0 2017-3-14 下午6:53:02 wanglg15468 Exp $
 */
public enum EnumIdentifyKind {
                               
    USER_NAME("1", "用户名","0"),
    TELEPHONE("2", "手机号","0"),
    EMAIL("3", "邮箱","0"),
    CERTIFICATES("4", "证件","0"),
    FINANCIAL_CARD_NUMBER("5", "金融卡号","0"),
    BINDING_QQ("6", "联合登陆：QQ","1"),
    BINDING_SINA("7", "联合登陆：新浪微博","1"),
    BINDING_WECHAT("8", "联合登陆：微信","1"),
    BINDING_ALIPAY("9", "联合登陆：支付宝","1"),
    SAFETY_PROBLEM("10", "安全问题","0"),
    OTHER_BINDING("x", "其他联合登陆","1");

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;
    /** 联合标志 **/
    private String bindFlag;

    /**
    * 私有构造方法
    * 
    * @param code
    *            编码
    * @param description
    *            描述
    **/
    private EnumIdentifyKind(String code, String description,String bindFlag) {
        this.code = code;
        this.description = description;
        this.bindFlag = bindFlag;
    }

    /**
    * 根据编码查找枚举
    * 
    * @param code
    *            编码
    * @return {@link EnumBearingMode} 实例
    **/
    public static EnumIdentifyKind find(String code) {
        for (EnumIdentifyKind frs : EnumIdentifyKind.values()) {
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

    /**
     * Getter method for property <tt>bindFlag</tt>.
     * 
     * @return property value of bindFlag
     */
    public String getBindFlag() {
        return bindFlag;
    }
    
    
}
