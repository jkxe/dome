package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * 客户群信息
 * 
 * @author jiangjd12837
 * @version $Id: UserCustomergroupInfoPojo.java, v 1.0 2017年3月13日 下午7:54:06 jiangjd12837 Exp $
 */
public class UserCustomerGroupInfoPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = -5649462937417806092L;

    private String            clientGroupId;

    private String            groupName;

    /**
     * Getter method for property <tt>clientGroupId</tt>.
     * 
     * @return property value of clientGroupId
     */
    public String getClientGroupId() {
        return clientGroupId;
    }

    /**
     * Setter method for property <tt>clientGroupId</tt>.
     * 
     * @param clientGroupId value to be assigned to property clientGroupId
     */
    public void setClientGroupId(String clientGroupId) {
        this.clientGroupId = clientGroupId;
    }

    /**
     * Getter method for property <tt>groupName</tt>.
     * 
     * @return property value of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Setter method for property <tt>groupName</tt>.
     * 
     * @param groupName value to be assigned to property groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
