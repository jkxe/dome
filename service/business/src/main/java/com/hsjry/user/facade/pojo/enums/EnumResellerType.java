package com.hsjry.user.facade.pojo.enums;

/**
 * Created by zhangxianli on 2018/6/9.
 */
public enum EnumResellerType {
    MERCHANT("1", "商户"),
    STORE("2", "门店");

    /**
     * 状态码
     **/
    private String code;
    /**
     * 状态描述
     **/
    private String description;

    /**
     * 私有构造方法
     *
     * @param code        编码
     * @param description 描述
     **/
    EnumResellerType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumResellerType} 实例
     **/
    public static EnumResellerType find(String code) {
        for (EnumResellerType frs : EnumResellerType.values()) {
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
