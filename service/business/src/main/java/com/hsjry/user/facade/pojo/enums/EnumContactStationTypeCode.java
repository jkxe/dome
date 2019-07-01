package com.hsjry.user.facade.pojo.enums;
/**
 * 联系点类型
 * 
 * @author jiangjd12837
 * @version $Id: EnumContactTtationTypeCode.java, v 1.0 2017年3月14日 上午11:08:39 jiangjd12837 Exp $
 */
public enum EnumContactStationTypeCode {
    TELEPHONE("1", "电话"),
    EMAIL("2", "邮箱"),
    ADDRESS("3", "地址"),
    SOCIAL("4", "社交"),
    WEBSITE("5", "网址");
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
    private EnumContactStationTypeCode(String code, String description) {
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
    public static EnumContactStationTypeCode find(String code) {
        for (EnumContactStationTypeCode frs : EnumContactStationTypeCode.values()) {
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
