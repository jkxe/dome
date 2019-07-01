package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.QueryUserAndAddressPojo;

public class QueryUserAndAddressReponse implements Serializable {

	
    private static final long serialVersionUID = 3454577054097386785L;
    
    private List<QueryUserAndAddressPojo> quertUserAndAddressPojoList;

    /**
     * Getter method for property <tt>quertUserAndAddressPojoList</tt>.
     * 
     * @return property value of quertUserAndAddressPojoList
     */
    public List<QueryUserAndAddressPojo> getQuertUserAndAddressPojoList() {
        return quertUserAndAddressPojoList;
    }

    /**
     * Setter method for property <tt>quertUserAndAddressPojoList</tt>.
     * 
     * @param quertUserAndAddressPojoList value to be assigned to property quertUserAndAddressPojoList
     */
    public void setQuertUserAndAddressPojoList(List<QueryUserAndAddressPojo> quertUserAndAddressPojoList) {
        this.quertUserAndAddressPojoList = quertUserAndAddressPojoList;
    }

	

}
