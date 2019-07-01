package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserAddressContactStationInfoPojo;

public class QueryConsignStationResponse implements Serializable {

    /**  */
    private static final long                       serialVersionUID = 6601996949227698790L;
    private List<UserAddressContactStationInfoPojo> userAddressContactStationInfoPojoList;

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

}
