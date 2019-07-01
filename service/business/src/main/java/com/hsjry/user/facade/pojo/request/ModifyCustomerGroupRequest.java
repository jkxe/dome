package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class ModifyCustomerGroupRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -4138034069285439429L;
    //客户群ID
    @NotNull(errorCode = "000001", message = "客户群ID")
    @NotBlank(errorCode = "000001", message = "客户群ID")
    private String            clientGroupId;
    //客户群名称
    @NotNull(errorCode = "000001", message = "客户群名称")
    @NotBlank(errorCode = "000001", message = "客户群名称")
    private String            groupName;

    /**
     * Getter method for property <tt>clientGroupId</tt>.
     * 
     * @return property value of clientGroupId
     */
    public String getClientGroupId() {
        return clientGroupId;
    }

    /**
     * Setter method for property <tt>clientGroupId</tt>.
     * 
     * @param clientGroupId value to be assigned to property clientGroupId
     */
    public void setClientGroupId(String clientGroupId) {
        this.clientGroupId = clientGroupId;
    }

    /**
     * Getter method for property <tt>groupName</tt>.
     * 
     * @return property value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Setter method for property <tt>groupName</tt>.
     * 
     * @param groupName value to be assigned to property groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
