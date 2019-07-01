package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.List;

public class QueryUserAndAddressPojo implements Serializable {

	
    private static final long serialVersionUID = 3685538581849717551L;

    private String userId;
	
	private String userName;
	
	private List<UserAddressContactStationInfoPojo> addressList;


    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for property <tt>userName</tt>.
     * 
     * @return property value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter method for property <tt>userName</tt>.
     * 
     * @param userName value to be assigned to property userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter method for property <tt>addressList</tt>.
     * 
     * @return property value of addressList
     */
    public List<UserAddressContactStationInfoPojo> getAddressList() {
        return addressList;
    }

    /**
     * Setter method for property <tt>addressList</tt>.
     * 
     * @param addressList value to be assigned to property addressList
     */
    public void setAddressList(List<UserAddressContactStationInfoPojo> addressList) {
        this.addressList = addressList;
    }


 


}
