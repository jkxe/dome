package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserCustomerGroupInfoPojo;

public class QueryCustomerGroupByUserIdlistResponse implements Serializable {

    /**  */
    private static final long               serialVersionUID = -6039020355183080009L;
    private List<UserCustomerGroupInfoPojo> userCustomerGroupInfoPojoList;

    public List<UserCustomerGroupInfoPojo> getUserCustomerGroupInfoPojoList() {
        return userCustomerGroupInfoPojoList;
    }

    public void setUserCustomerGroupInfoPojoList(List<UserCustomerGroupInfoPojo> userCustomerGroupInfoPojoList) {
        this.userCustomerGroupInfoPojoList = userCustomerGroupInfoPojoList;
    }
}
