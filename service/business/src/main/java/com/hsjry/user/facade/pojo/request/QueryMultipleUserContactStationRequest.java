package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.check.ListStringCheck;
import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

/**
 * 查询多客户单联系点类型请求
 * 
 * @author jiangjd12837
 * @version $Id: QueryMultipleUserContactStationRequest.java, v 1.0 2017年3月13日 下午7:47:26 jiangjd12837 Exp $
 */
public class QueryMultipleUserContactStationRequest implements Serializable {

    /**  */
    private static final long          serialVersionUID = 6814922585168650724L;
    @NotNull(errorCode = "000001", message = "客户ID")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "客户ID列表")
    private List<String>               userId;
    @NotNull(errorCode = "000001", message = "联系点类型")
    private EnumContactStationTypeCode enumContactStationTypeCode;

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public List<String> getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

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

}
