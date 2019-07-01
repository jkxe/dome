/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;
import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryMultipleContactStationByAuthIdListRequest.java, v 1.0 2017年4月19日 下午2:07:49 jiangjd12837 Exp $
 */
public class QueryMultipleContactStationByAuthIdListRequest implements Serializable {

    /**  */
    private static final long          serialVersionUID = -4657104118052826298L;
    @NotNull(errorCode = "000001", message = "通行证ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "通行证ID列表")
    private List<String>               authIdList;
    @NotNull(errorCode = "000001", message = "联系点类型")
    private EnumContactStationTypeCode enumContactStationTypeCode;

    /**
     * Getter method for property <tt>enumContactStationTypeCode</tt>.
     * 
     * @return property value of enumContactStationTypeCode
     */
    public EnumContactStationTypeCode getEnumContactStationTypeCode() {
        return enumContactStationTypeCode;
    }

    /**
     * Setter method for property <tt>enumContactStationTypeCode</tt>.
     * 
     * @param enumContactStationTypeCode value to be assigned to property enumContactStationTypeCode
     */
    public void setEnumContactStationTypeCode(EnumContactStationTypeCode enumContactStationTypeCode) {
        this.enumContactStationTypeCode = enumContactStationTypeCode;
    }

    /**
     * Getter method for property <tt>authIdList</tt>.
     * 
     * @return property value of authIdList
     */
    public List<String> getAuthIdList() {
        return authIdList;
    }

    /**
     * Setter method for property <tt>authIdList</tt>.
     * 
     * @param authIdList value to be assigned to property authIdList
     */
    public void setAuthIdList(List<String> authIdList) {
        this.authIdList = authIdList;
    }
}
