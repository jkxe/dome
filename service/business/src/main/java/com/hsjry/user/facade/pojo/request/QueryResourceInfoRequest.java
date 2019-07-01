package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.enums.EnumResourceType;

public class QueryResourceInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -8923971552765745240L;
    
    //资源项类型
    @NotNull(errorCode = "000001", message = "资源项类型")
    private List<EnumResourceType> resourceTypeList;
    //客户Id
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String userId;
    
    /**
     * Getter method for property <tt>resourceTypeList</tt>.
     * 
     * @return property value of resourceTypeList
     */
    public List<EnumResourceType> getResourceTypeList() {
        return resourceTypeList;
    }

    /**
     * Setter method for property <tt>resourceTypeList</tt>.
     * 
     * @param resourceTypeList value to be assigned to property resourceTypeList
     */
    public void setResourceTypeList(List<EnumResourceType> resourceTypeList) {
        this.resourceTypeList = resourceTypeList;
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

}
