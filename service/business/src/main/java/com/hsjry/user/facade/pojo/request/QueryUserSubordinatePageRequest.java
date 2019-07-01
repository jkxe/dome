package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description:用户下属
 * @date 2017年07月28日  19:56
 */
public class QueryUserSubordinatePageRequest implements Serializable {

    private static final long serialVersionUID = -6847208993492446550L;
    /**
     * 客户Id
     */
    @NotNull(errorCode = "000001", message = "客户Id")
    @NotBlank(errorCode = "000001", message = "客户Id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
