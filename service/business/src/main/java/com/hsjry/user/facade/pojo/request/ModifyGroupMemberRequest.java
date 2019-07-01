/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangjd12837
 * @version $Id: ModifyGroupMemberRequest.java, v 1.0 2017年6月16日 下午3:59:54 jiangjd12837 Exp $
 */
public class ModifyGroupMemberRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -2638640429878844173L;
    //去除成员用户ID列表
    private List<String>      delUserIdList;
    //新增成员用户ID列表
    private List<String>      addUserIdList;
    //组织ID
    private String            organId;

    /**
     * Getter method for property <tt>delUserIdList</tt>.
     * 
     * @return property value of delUserIdList
     */
    public List<String> getDelUserIdList() {
        return delUserIdList;
    }

    /**
     * Setter method for property <tt>delUserIdList</tt>.
     * 
     * @param delUserIdList value to be assigned to property delUserIdList
     */
    public void setDelUserIdList(List<String> delUserIdList) {
        this.delUserIdList = delUserIdList;
    }

    /**
     * Getter method for property <tt>addUserIdList</tt>.
     * 
     * @return property value of addUserIdList
     */
    public List<String> getAddUserIdList() {
        return addUserIdList;
    }

    /**
     * Setter method for property <tt>addUserIdList</tt>.
     * 
     * @param addUserIdList value to be assigned to property addUserIdList
     */
    public void setAddUserIdList(List<String> addUserIdList) {
        this.addUserIdList = addUserIdList;
    }

    /**
     * Getter method for property <tt>organId</tt>.
     * 
     * @return property value of organId
     */
    public String getOrganId() {
        return organId;
    }

    /**
     * Setter method for property <tt>organId</tt>.
     * 
     * @param organId value to be assigned to property organId
     */
    public void setOrganId(String organId) {
        this.organId = organId;
    }

}
