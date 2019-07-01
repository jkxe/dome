package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketInfoListByProvinceAndCityAndAreaResponse.java, v 0.1 2018/8/2 19:04 zhengqy15963 Exp $$
 */
public class QueryMarketInfoListByProvinceAndCityAndAreaResponse implements Serializable {
    private static final long serialVersionUID = -6661900385440369303L;
    /**
     * 渠道经理所在的组织的组织id列表
     */
    List<String> organIdList;

    public List<String> getOrganIdList() {
        return organIdList;
    }

    public void setOrganIdList(List<String> organIdList) {
        this.organIdList = organIdList;
    }
}
