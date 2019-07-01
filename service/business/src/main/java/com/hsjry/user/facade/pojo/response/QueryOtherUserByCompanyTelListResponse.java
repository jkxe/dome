package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.QueryOtherUserByCompanyTelPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:查询当前单位电话列表被其他用户使用响应
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年08月05日  21:39
 */
public class QueryOtherUserByCompanyTelListResponse implements Serializable {

    private List<QueryOtherUserByCompanyTelPojo> queryOtherUserByCompanyTelPojoList;


    public List<QueryOtherUserByCompanyTelPojo> getQueryOtherUserByCompanyTelPojoList() {
        return queryOtherUserByCompanyTelPojoList;
    }

    public void setQueryOtherUserByCompanyTelPojoList(List<QueryOtherUserByCompanyTelPojo> queryOtherUserByCompanyTelPojoList) {
        this.queryOtherUserByCompanyTelPojoList = queryOtherUserByCompanyTelPojoList;
    }
}
