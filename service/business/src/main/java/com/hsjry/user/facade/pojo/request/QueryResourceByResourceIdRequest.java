package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

public class QueryResourceByResourceIdRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 2535613370268756424L;
    //资源项ID列表
    @NotNull(errorCode = "000001", message = "资源项ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "资源项ID列表")
    private List<String>      resourceId;

    public List<String> getResourceId() {
        return resourceId;
    }

    public void setResourceId(List<String> resourceId) {
        this.resourceId = resourceId;
    }
}
