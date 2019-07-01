package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:查询当前单位电话列表被其他用户使用请求
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年08月05日  21:39
 */
public class QueryOtherUserByCompanyTelListRequest implements Serializable {


    private static final long serialVersionUID = -551523430672753813L;
    private List<String> telList;


    public List<String> getTelList() {
        return telList;
    }

    public void setTelList(List<String> telList) {
        this.telList = telList;
    }
}
