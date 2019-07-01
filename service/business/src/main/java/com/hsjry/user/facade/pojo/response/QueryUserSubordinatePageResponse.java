package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.QueryUserSubordinatePagePojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:用户下属
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年07月28日  19:56
 */
public class QueryUserSubordinatePageResponse implements Serializable {

    private static final long serialVersionUID = 3840391598011479933L;

    private List<QueryUserSubordinatePagePojo>  queryUserSubordinatePagePojoList;

    public List<QueryUserSubordinatePagePojo> getQueryUserSubordinatePagePojoList() {
        return queryUserSubordinatePagePojoList;
    }

    public void setQueryUserSubordinatePagePojoList(List<QueryUserSubordinatePagePojo> queryUserSubordinatePagePojoList) {
        this.queryUserSubordinatePagePojoList = queryUserSubordinatePagePojoList;
    }
}
