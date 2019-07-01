package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotNull;

import com.hsjry.user.facade.pojo.check.ListStringCheck;

public class DelResourceInfoRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -1446786338253059656L;
    @NotNull(errorCode = "000001", message = "资源ID列表")
    @CheckWith(value = ListStringCheck.class, errorCode = "000003", message = "资源ID列表")
    private List<String>      resourceIdList;

    public List<String> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<String> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

}
