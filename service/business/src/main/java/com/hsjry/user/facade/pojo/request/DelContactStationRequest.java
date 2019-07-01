package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * 删除联系点
 * 
 * @author jiangjd12837
 * @version $Id: DelContactStationRequest.java, v 1.0 2017年3月13日 下午4:53:57 jiangjd12837 Exp $
 */

public class DelContactStationRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = 1082856450215922987L;
    //联系点ID
    @NotNull(errorCode = "000001", message = "联系点ID")
    @NotBlank(errorCode = "000001", message = "联系点ID")
    private String            stationId;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
