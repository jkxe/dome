package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserEmailContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserSocialContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserTelContactStationInfoPojo;
import com.hsjry.user.facade.pojo.UserWebsiteContactStationInfoPojo;

/**
 * 查询单客户多联系点类型返回
 * 
 * @author jiangjd12837
 * @version $Id: QuerySingleUserContactStationResponse.java, v 1.0 2017年3月13日 下午7:55:50 jiangjd12837 Exp $
 */
public class QuerySingleUserContactStationResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = -8001787014729556513L;
    //地址联系点
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;
    //邮箱联系点
    private List<UserEmailContactStationInfoPojo>   userEmailContactStationInfoPojoList;
    //互联网社交联系点
    private List<UserSocialContactStationInfoPojo>  userSocialContactStationInfoPojoList;
    //电话联系点
    private List<UserTelContactStationInfoPojo>     userTelContactStationInfoPojoList;
    //网址联系点
    private List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList;

    /**
     * Getter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @return property value of userAddressContactStationInfoPojoList
     */
    public List<UserAddressContactStationInfoPojo> getUserAddressContactStationInfoPojoList() {
        return userAddressContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userAddressContactStationInfoPojoList</tt>.
     * 
     * @param userAddressContactStationInfoPojoList value to be assigned to property userAddressContactStationInfoPojoList
     */
    public void setUserAddressContactStationInfoPojoList(List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList) {
        this.userAddressContactStationInfoPojoList = userAddressContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @return property value of userEmailContactStationInfoPojoList
     */
    public List<UserEmailContactStationInfoPojo> getUserEmailContactStationInfoPojoList() {
        return userEmailContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userEmailContactStationInfoPojoList</tt>.
     * 
     * @param userEmailContactStationInfoPojoList value to be assigned to property userEmailContactStationInfoPojoList
     */
    public void setUserEmailContactStationInfoPojoList(List<UserEmailContactStationInfoPojo> userEmailContactStationInfoPojoList) {
        this.userEmailContactStationInfoPojoList = userEmailContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @return property value of userSocialContactStationInfoPojoList
     */
    public List<UserSocialContactStationInfoPojo> getUserSocialContactStationInfoPojoList() {
        return userSocialContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userSocialContactStationInfoPojoList</tt>.
     * 
     * @param userSocialContactStationInfoPojoList value to be assigned to property userSocialContactStationInfoPojoList
     */
    public void setUserSocialContactStationInfoPojoList(List<UserSocialContactStationInfoPojo> userSocialContactStationInfoPojoList) {
        this.userSocialContactStationInfoPojoList = userSocialContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @return property value of userTelContactStationInfoPojoList
     */
    public List<UserTelContactStationInfoPojo> getUserTelContactStationInfoPojoList() {
        return userTelContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userTelContactStationInfoPojoList</tt>.
     * 
     * @param userTelContactStationInfoPojoList value to be assigned to property userTelContactStationInfoPojoList
     */
    public void setUserTelContactStationInfoPojoList(List<UserTelContactStationInfoPojo> userTelContactStationInfoPojoList) {
        this.userTelContactStationInfoPojoList = userTelContactStationInfoPojoList;
    }

    /**
     * Getter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @return property value of userWebsiteContactStationInfoPojoList
     */
    public List<UserWebsiteContactStationInfoPojo> getUserWebsiteContactStationInfoPojoList() {
        return userWebsiteContactStationInfoPojoList;
    }

    /**
     * Setter method for property <tt>userWebsiteContactStationInfoPojoList</tt>.
     * 
     * @param userWebsiteContactStationInfoPojoList value to be assigned to property userWebsiteContactStationInfoPojoList
     */
    public void setUserWebsiteContactStationInfoPojoList(List<UserWebsiteContactStationInfoPojo> userWebsiteContactStationInfoPojoList) {
        this.userWebsiteContactStationInfoPojoList = userWebsiteContactStationInfoPojoList;
    }

}
