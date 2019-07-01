package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class QueryCustomerGroupInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1239535356314183367L;
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
