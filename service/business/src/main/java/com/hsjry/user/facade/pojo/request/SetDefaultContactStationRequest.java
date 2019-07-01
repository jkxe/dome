package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 修改已绑定手机号请求报文
 * 
 * @author jiangjd12837
 * @version $Id: SetDefaultContactStationRequest.java, v 1.0 2017年3月13日 下午4:54:39 jiangjd12837 Exp $
 */
public class SetDefaultContactStationRequest implements Serializable {

    /**  */
    private static final long          serialVersionUID = -1287297826052805959L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                     userId;
    //联系点ID
    @NotNull(errorCode = "000001", message = "联系点ID")
    @NotBlank(errorCode = "000001", message = "联系点ID")
    private String                     stationId;
    //联系点类型
    @NotNull(errorCode = "000001", message = "联系点类型")
    private EnumContactStationTypeCode contactStationType;

    /**
     * Getter method for property <tt>contactStationType</tt>.
     * 
     * @return property value of contactStationType
     */
    public EnumContactStationTypeCode getContactStationType() {
        return contactStationType;
    }

    /**
     * Setter method for property <tt>contactStationType</tt>.
     * 
     * @param contactStationType value to be assigned to property contactStationType
     */
    public void setContactStationType(EnumContactStationTypeCode contactStationType) {
        this.contactStationType = contactStationType;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
