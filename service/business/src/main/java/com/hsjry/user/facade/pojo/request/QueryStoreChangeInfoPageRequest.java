/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotNull;

import com.hsjry.lang.common.base.enums.EnumBool;
import com.hsjry.user.facade.pojo.enums.EnumStoreRegisterKind;

/**
 * 查询门店信息变更请求
 * @author huangbb
 * @version $Id: QueryStoreChangeInfoPageRequest.java, v 1.0 2018年5月16日 下午3:02:54 huangbb Exp $
 */
public class QueryStoreChangeInfoPageRequest implements Serializable {

    private static final long serialVersionUID = -426001467475634089L;
    
    /**门店名称  */
    private String            organName;
    /**所属经销商  */
    private String            merchantName;
    /**门店负责人  */
    private String            dutyName;
    
    @NotNull(errorCode = "000001", message = "变更信息类型")
    private EnumStoreRegisterKind registerKind;
    
    @NotNull(errorCode = "000001", message = "是否查询变更记录")
    private EnumBool             bool;
    
    
    /**
     * Getter method for property <tt>organName</tt>.
     * 
     * @return property value of organName
     */
    public String getOrganName() {
        return organName;
    }
    /**
     * Setter method for property <tt>organName</tt>.
     * 
     * @param organName value to be assigned to property organName
     */
    public void setOrganName(String organName) {
        this.organName = organName;
    }
    /**
     * Getter method for property <tt>merchantName</tt>.
     * 
     * @return property value of merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }
    /**
     * Setter method for property <tt>merchantName</tt>.
     * 
     * @param merchantName value to be assigned to property merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    /**
     * Getter method for property <tt>dutyName</tt>.
     * 
     * @return property value of dutyName
     */
    public String getDutyName() {
        return dutyName;
    }
    /**
     * Setter method for property <tt>dutyName</tt>.
     * 
     * @param dutyName value to be assigned to property dutyName
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
    /**
     * Getter method for property <tt>registerKind</tt>.
     * 
     * @return property value of registerKind
     */
    public EnumStoreRegisterKind getRegisterKind() {
        return registerKind;
    }
    /**
     * Setter method for property <tt>registerKind</tt>.
     * 
     * @param registerKind value to be assigned to property registerKind
     */
    public void setRegisterKind(EnumStoreRegisterKind registerKind) {
        this.registerKind = registerKind;
    }
    /**
     * Getter method for property <tt>bool</tt>.
     * 
     * @return property value of bool
     */
    public EnumBool getBool() {
        return bool;
    }
    /**
     * Setter method for property <tt>bool</tt>.
     * 
     * @param bool value to be assigned to property bool
     */
    public void setBool(EnumBool bool) {
        this.bool = bool;
    }
    
    
}
