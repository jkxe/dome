package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.enums.EnumContactStationTypeCode;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class QuerySingleUserContactStationRequest implements Serializable {

    /**  */
    private static final long                serialVersionUID = -4824047232027580378L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                           userId;
    //联系点类型
    @NotNull(errorCode = "000001", message = "联系点类型")
    private List<EnumContactStationTypeCode> contactStationTypeList;

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

    public List<EnumContactStationTypeCode> getContactStationTypeList() {
        return contactStationTypeList;
    }

    public void setContactStationTypeList(List<EnumContactStationTypeCode> contactStationTypeList) {
        this.contactStationTypeList = contactStationTypeList;
    }

}
