package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

public class ResourceVerifyRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1855879792409806461L;
    @NotNull(errorCode = "000001", message = "登记点ID")
    @NotBlank(errorCode = "000001", message = "登记点ID")
    private String            registerId;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }
}
