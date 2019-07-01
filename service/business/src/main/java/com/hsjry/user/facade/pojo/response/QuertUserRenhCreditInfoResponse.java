package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserRenhCreditInfoPojo;


public class QuertUserRenhCreditInfoResponse implements Serializable {
	 private static final long                  serialVersionUID = 1663587379610776976L;
	 
	 private List<UserRenhCreditInfoPojo> userRenhCreditInfoPoJoList;

    /**
     * Getter method for property <tt>userRenhCreditInfoPoJoList</tt>.
     * 
     * @return property value of userRenhCreditInfoPoJoList
     */
    public List<UserRenhCreditInfoPojo> getUserRenhCreditInfoPoJoList() {
        return userRenhCreditInfoPoJoList;
    }

    /**
     * Setter method for property <tt>userRenhCreditInfoPoJoList</tt>.
     * 
     * @param userRenhCreditInfoPoJoList value to be assigned to property userRenhCreditInfoPoJoList
     */
    public void setUserRenhCreditInfoPoJoList(List<UserRenhCreditInfoPojo> userRenhCreditInfoPoJoList) {
        this.userRenhCreditInfoPoJoList = userRenhCreditInfoPoJoList;
    }

	 
	 
}
