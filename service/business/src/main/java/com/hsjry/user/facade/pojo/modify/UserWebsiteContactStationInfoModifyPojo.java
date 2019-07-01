package com.hsjry.user.facade.pojo.modify;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 网址联系点
 * 
 * @author jiangjd12837
 * @version $Id: UserWebsiteContactStationPojo.java, v 1.0 2017年3月13日 下午4:53:14 jiangjd12837 Exp $
 */
public class UserWebsiteContactStationInfoModifyPojo extends UserContactStationInfoModifyPojo {

    /**  */
    private static final long serialVersionUID = 1492896483986261126L;
    //网址URL
    @NotNull(errorCode = "000001", message = "网址URL")
    @NotBlank(errorCode = "000001", message = "网址URL")
    private String            website;

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
