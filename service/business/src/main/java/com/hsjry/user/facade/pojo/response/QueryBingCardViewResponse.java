package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

import com.hsjry.user.facade.pojo.UserFinancialInstrumentsPojo;

public class QueryBingCardViewResponse implements Serializable {

    /**  */
    private static final long                  serialVersionUID = 1663587379610776976L;
    //金融工具
    private List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList;

    /**
     * Getter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @return property value of userFinancialInstrumentsPojoList
     */
    public List<UserFinancialInstrumentsPojo> getUserFinancialInstrumentsPojoList() {
        return userFinancialInstrumentsPojoList;
    }

    /**
     * Setter method for property <tt>userFinancialInstrumentsPojoList</tt>.
     * 
     * @param userFinancialInstrumentsPojoList value to be assigned to property userFinancialInstrumentsPojoList
     */
    public void setUserFinancialInstrumentsPojoList(List<UserFinancialInstrumentsPojo> userFinancialInstrumentsPojoList) {
        this.userFinancialInstrumentsPojoList = userFinancialInstrumentsPojoList;
    }

}
