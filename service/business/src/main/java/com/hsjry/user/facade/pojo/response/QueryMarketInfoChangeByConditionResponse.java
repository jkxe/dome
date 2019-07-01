package com.hsjry.user.facade.pojo.response;

import com.hsjry.user.facade.pojo.QueryMarketInfoByConditionPojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketInfoChangeByConditionResponse.java, v 0.1 2018/8/10 19:45 zhengqy15963 Exp $$
 */
public class QueryMarketInfoChangeByConditionResponse implements Serializable {

    private static final long serialVersionUID = -2715553473404795477L;
    /**
     * 根据条件查询出来的渠道经理的信息列表
     */
    private List<QueryMarketInfoByConditionPojo> marketInfoByConditionPojos;

    public List<QueryMarketInfoByConditionPojo> getMarketInfoByConditionPojos() {
        return marketInfoByConditionPojos;
    }

    public void setMarketInfoByConditionPojos(List<QueryMarketInfoByConditionPojo> marketInfoByConditionPojos) {
        this.marketInfoByConditionPojos = marketInfoByConditionPojos;
    }
}
