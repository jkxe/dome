package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.ModifyRelationPojo;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class ModifyRelationRequest implements Serializable {

    /**  */
    private static final long        serialVersionUID = -7325616968988806999L;
    //客户ID
    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String                   userId;

    //修改关系人信息
    private List<ModifyRelationPojo> modifyRelationPojoList;

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
     * Getter method for property <tt>modifyRelationPojoList</tt>.
     * 
     * @return property value of modifyRelationPojoList
     */
    public List<ModifyRelationPojo> getModifyRelationPojoList() {
        return modifyRelationPojoList;
    }

    /**
     * Setter method for property <tt>modifyRelationPojoList</tt>.
     * 
     * @param modifyRelationPojoList value to be assigned to property modifyRelationPojoList
     */
    public void setModifyRelationPojoList(List<ModifyRelationPojo> modifyRelationPojoList) {
        this.modifyRelationPojoList = modifyRelationPojoList;
    }

}
