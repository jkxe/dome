package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.QueryUserCustomerRelationPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @Description:查询联系人关系信息响应
 * @date 2017年10月23日  14:40
 */
public class QueryCustomerRelationInfoByUserIdResponse implements Serializable {

    private static final long serialVersionUID = 165880685028618096L;

    private List<QueryUserCustomerRelationPojo> queryUserCustomerRelationPojos;

    public List<QueryUserCustomerRelationPojo> getQueryUserCustomerRelationPojos() {
        return queryUserCustomerRelationPojos;
    }

    public void setQueryUserCustomerRelationPojos(List<QueryUserCustomerRelationPojo> queryUserCustomerRelationPojos) {
        this.queryUserCustomerRelationPojos = queryUserCustomerRelationPojos;
    }
}
