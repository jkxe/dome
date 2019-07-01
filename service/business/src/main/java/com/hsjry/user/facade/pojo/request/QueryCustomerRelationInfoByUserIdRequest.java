package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description: 查询联系人关系信息请求
 * @date 2017年10月23日  14:39
 */
public class QueryCustomerRelationInfoByUserIdRequest  implements Serializable {

    private static final long serialVersionUID = 6281712862823491150L;

    @NotNull(errorCode = "000001", message = "客户ID")
    @NotBlank(errorCode = "000001", message = "客户ID")
    private String     userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
