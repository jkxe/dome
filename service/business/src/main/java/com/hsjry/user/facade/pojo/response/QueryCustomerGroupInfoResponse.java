package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

import com.hsjry.user.facade.pojo.UserCustomerGroupInfoPojo;

/**
 * 客户群信息返回
 * 
 * @author jiangjd12837
 * @version $Id: QueryCustomerGroupInfoResponse.java, v 1.0 2017年3月13日 下午7:54:48 jiangjd12837 Exp $
 */
public class QueryCustomerGroupInfoResponse implements Serializable {

    /**  */
    private static final long         serialVersionUID = 6156824080486065627L;

    private UserCustomerGroupInfoPojo userCustomerGroupInfo;

    /**
     * Getter method for property <tt>userCustomerGroupInfo</tt>.
     * 
     * @return property value of userCustomerGroupInfo
     */
    public UserCustomerGroupInfoPojo getUserCustomergroupInfoPojo() {
        return userCustomerGroupInfo;
    }

    /**
     * Setter method for property <tt>userCustomerGroupInfo</tt>.
     * 
     * @param userCustomerGroupInfo value to be assigned to property userCustomerGroupInfo
     */
    public void setUserCustomergroupInfoPojo(UserCustomerGroupInfoPojo userCustomerGroupInfo) {
        this.userCustomerGroupInfo = userCustomerGroupInfo;
    }
}
