package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class DelCustomerGroupRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 638650246743557166L;
    //客户群ID
    @NotNull(errorCode = "000001", message = "客户群ID")
    @NotBlank(errorCode = "000001", message = "客户群ID")
    private String            clientGroupId;

    public String getClientGroupId() {
        return clientGroupId;
    }

    public void setClientGroupId(String clientGroupId) {
        this.clientGroupId = clientGroupId;
    }
}
