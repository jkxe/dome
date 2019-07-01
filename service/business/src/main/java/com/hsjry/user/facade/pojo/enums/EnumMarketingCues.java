/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.enums;

/**
 * 营销线索
 * 
 * @author wanglg15468
 * @version $Id: EnumMarketingCues.java, v 1.0 2017-3-14 下午8:10:58 wanglg15468 Exp $
 */
public enum EnumMarketingCues {
    
    BATCH_IMPORT("1", "批量导入"),  //@ignore_rpc_make_all
    MANAGE_MANUAL_ADD("2", "管理台手工添加"), //@ignore_rpc_make_all
    APP("3", "APP"),  //@ignore_rpc_make_pad
    PC("4", "PC"), //@ignore_rpc_make_all
    WECHAT("5", "微信"),
    MERCHANT_RECOMMEND("6", "商户推荐"), //@ignore_rpc_make_all
    PARTNERS_RECOMMENDED("7", "合作方推荐"),   //@ignore_rpc_make_all  
    OTHER_CHANNELS("8", "其他渠道"), //@ignore_rpc_make_all
    CUSTOMER_TRANSFER_BUSINESS("9", "已有客户转商户"), //@ignore_rpc_make_all
    CUSTOMER_TRANSFER_PARTNERS("10", "已有客户转合作方"), //@ignore_rpc_make_all
    MERCHANT_APPLICATION("11", "商户加盟申请"), //@ignore_rpc_make_all
    APIPAY("12","支付宝"), //@ignore_rpc_make_all
    PAD("13","PAD"); //@ //@ignore_rpc_make_app
    
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
    private EnumMarketingCues(String code, String description) {
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
    public static EnumMarketingCues find(String code) {
        for (EnumMarketingCues frs : EnumMarketingCues.values()) {
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
