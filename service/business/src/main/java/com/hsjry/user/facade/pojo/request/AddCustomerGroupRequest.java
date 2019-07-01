package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class AddCustomerGroupRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -8350826715933365854L;
    @NotNull(errorCode = "000001", message = "客户群名称")
    @NotBlank(errorCode = "000001", message = "客户群名称")
    private String            groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
