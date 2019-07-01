package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserCustomerInfoPojo;

/**
 * 客户ID列表查询客户信息
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserInfoByuserIdlistResponse.java, v 1.0 2017年3月13日 下午4:55:54 jiangjd12837 Exp $
 */
public class QueryUserCustomerInfoByuserIdlistResponse implements Serializable {

    /**  */
    private static final long          serialVersionUID = -4962593955539080587L;
    private List<UserCustomerInfoPojo> userCustomerInfoPojoList;

    public List<UserCustomerInfoPojo> getUserCustomerInfoPojoList() {
        return userCustomerInfoPojoList;
    }

    public void setUserCustomerInfoPojoList(List<UserCustomerInfoPojo> userCustomerInfoPojoList) {
        this.userCustomerInfoPojoList = userCustomerInfoPojoList;
    }
}
