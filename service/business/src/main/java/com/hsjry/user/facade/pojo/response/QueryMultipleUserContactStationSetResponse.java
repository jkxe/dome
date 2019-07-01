package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserContactSetPojo;

/**
 * 多客户单联系点类型查询联系点返回
 * 
 * @author jiangjd12837
 * @version $Id: QueryMultipleUserContactStationResponse.java, v 1.0 2017年3月13日 下午7:55:31 jiangjd12837 Exp $
 */
public class QueryMultipleUserContactStationSetResponse implements Serializable {

    private static final long        serialVersionUID = 5348258317181999358L;

    /**联系点集合*/
    private List<UserContactSetPojo> userContactSetPojoList;

    /**
     * Getter method for property <tt>userContactSetPojoList</tt>.
     * 
     * @return property value of userContactSetPojoList
     */
    public List<UserContactSetPojo> getUserContactSetPojoList() {
        return userContactSetPojoList;
    }

    /**
     * Setter method for property <tt>userContactSetPojoList</tt>.
     * 
     * @param userContactSetPojoList value to be assigned to property userContactSetPojoList
     */
    public void setUserContactSetPojoList(List<UserContactSetPojo> userContactSetPojoList) {
        this.userContactSetPojoList = userContactSetPojoList;
    }

    
}
