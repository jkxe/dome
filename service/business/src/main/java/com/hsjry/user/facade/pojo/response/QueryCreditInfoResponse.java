/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryCustomerRelationPojo;
import com.hsjry.user.facade.pojo.UserContactStationResponsePojo;
import com.hsjry.user.facade.pojo.UserProfessionalInfoPojo;

/**
 * 
 * @author jiangjd12837
 * @version $Id: QueryCreditInfoResponse.java, v 1.0 2017年7月17日 下午4:30:10 jiangjd12837 Exp $
 */
public class QueryCreditInfoResponse implements Serializable {

    /**  */
    private static final long               serialVersionUID = 6070498611787100647L;
    //职业信息
    private List<UserProfessionalInfoPojo>  userProfessionalInfoPojoList;
    //客户关系人信息
    private List<QueryCustomerRelationPojo> queryCustomerRelationPojoList;
    //联系点
    private UserContactStationResponsePojo  userContactStationResponsePojo;

    /**
     * Getter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @return property value of userProfessionalInfoPojoList
     */
    public List<UserProfessionalInfoPojo> getUserProfessionalInfoPojoList() {
        return userProfessionalInfoPojoList;
    }

    /**
     * Setter method for property <tt>userProfessionalInfoPojoList</tt>.
     * 
     * @param userProfessionalInfoPojoList value to be assigned to property userProfessionalInfoPojoList
     */
    public void setUserProfessionalInfoPojoList(List<UserProfessionalInfoPojo> userProfessionalInfoPojoList) {
        this.userProfessionalInfoPojoList = userProfessionalInfoPojoList;
    }

    /**
     * Getter method for property <tt>queryCustomerRelationPojoList</tt>.
     * 
     * @return property value of queryCustomerRelationPojoList
     */
    public List<QueryCustomerRelationPojo> getQueryCustomerRelationPojoList() {
        return queryCustomerRelationPojoList;
    }

    /**
     * Setter method for property <tt>queryCustomerRelationPojoList</tt>.
     * 
     * @param queryCustomerRelationPojoList value to be assigned to property queryCustomerRelationPojoList
     */
    public void setQueryCustomerRelationPojoList(List<QueryCustomerRelationPojo> queryCustomerRelationPojoList) {
        this.queryCustomerRelationPojoList = queryCustomerRelationPojoList;
    }

    /**
     * Getter method for property <tt>userContactStationResponsePojo</tt>.
     * 
     * @return property value of userContactStationResponsePojo
     */
    public UserContactStationResponsePojo getUserContactStationResponsePojo() {
        return userContactStationResponsePojo;
    }

    /**
     * Setter method for property <tt>userContactStationResponsePojo</tt>.
     * 
     * @param userContactStationResponsePojo value to be assigned to property userContactStationResponsePojo
     */
    public void setUserContactStationResponsePojo(UserContactStationResponsePojo userContactStationResponsePojo) {
        this.userContactStationResponsePojo = userContactStationResponsePojo;
    }

}
