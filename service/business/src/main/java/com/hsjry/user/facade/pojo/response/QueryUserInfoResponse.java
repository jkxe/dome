package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserInfoByuserIdlistPojo;

/**
 * 
 * 
 * @author jiangjd12837
 * @version $Id: QueryUserInfoResponse.java, v 1.0 2017年3月20日 下午12:13:41 jiangjd12837 Exp $
 */
public class QueryUserInfoResponse implements Serializable {

    /**  */
    private static final long                   serialVersionUID = 8136073656240195448L;
    private List<QueryUserInfoByuserIdlistPojo> queryUserInfoByuserIdlistPojo;

    /**
     * Getter method for property <tt>queryUserInfoByuserIdlistPojo</tt>.
     * 
     * @return property value of queryUserInfoByuserIdlistPojo
     */
    public List<QueryUserInfoByuserIdlistPojo> getQueryUserInfoByuserIdlistPojo() {
        return queryUserInfoByuserIdlistPojo;
    }

    /**
     * Setter method for property <tt>queryUserInfoByuserIdlistPojo</tt>.
     * 
     * @param queryUserInfoByuserIdlistPojo value to be assigned to property queryUserInfoByuserIdlistPojo
     */
    public void setQueryUserInfoByuserIdlistPojo(List<QueryUserInfoByuserIdlistPojo> queryUserInfoByuserIdlistPojo) {
        this.queryUserInfoByuserIdlistPojo = queryUserInfoByuserIdlistPojo;
    }

}
