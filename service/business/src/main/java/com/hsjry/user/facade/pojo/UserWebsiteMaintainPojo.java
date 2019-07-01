package com.hsjry.user.facade.pojo;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 网址联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserWebsiteContactStationPojo.java, v 1.0 2017年3月13日 下午4:53:14 jiangjd12837 Exp $
 */
public class UserWebsiteMaintainPojo implements Serializable {

    /**  */
    private static final long serialVersionUID = 7955398870821403641L;
    //联系点ID
    private String            stationId;
    //客户角色ID
    private String            custRoleId;
    //客户ID
    private String            userId;

    //网址URL
    @NotNull(errorCode = "000001", message = "网址URL")
    @NotBlank(errorCode = "000001", message = "网址URL")
    private String            website;

    /**
     * Getter method for property <tt>stationId</tt>.
     * 
     * @return property value of stationId
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * Setter method for property <tt>stationId</tt>.
     * 
     * @param stationId value to be assigned to property stationId
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    /**
     * Getter method for property <tt>custRoleId</tt>.
     * 
     * @return property value of custRoleId
     */
    public String getCustRoleId() {
        return custRoleId;
    }

    /**
     * Setter method for property <tt>custRoleId</tt>.
     * 
     * @param custRoleId value to be assigned to property custRoleId
     */
    public void setCustRoleId(String custRoleId) {
        this.custRoleId = custRoleId;
    }

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
     * Getter method for property <tt>website</tt>.
     * 
     * @return property value of website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Setter method for property <tt>website</tt>.
     * 
     * @param website value to be assigned to property website
     */
    public void setWebsite(String website) {
        this.website = website;
    }
}
