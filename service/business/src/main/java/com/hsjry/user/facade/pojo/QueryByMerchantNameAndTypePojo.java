package com.hsjry.user.facade.pojo;

import java.io.Serializable;
import java.util.Set;

/**
 * @author zhengqy15963
 * @version $$Id: QueryByMerchantNameAndTypePojo.java, v 0.1 2018/8/11 10:36 zhengqy15963 Exp $$
 */
public class QueryByMerchantNameAndTypePojo implements Serializable {
    private static final long serialVersionUID = 5536834423043536375L;
    /**
     * 查询出的结果集
     */
    private Set<String> merchantResourceIdSet;
    /**
     * 按名称查询的标识位
     */
    private boolean merchantNameQuery;
    /**
     * 按分类查询的标识位
     */
    private boolean merchantTypeQuery;

    public Set<String> getMerchantResourceIdSet() {
        return merchantResourceIdSet;
    }

    public void setMerchantResourceIdSet(Set<String> merchantResourceIdSet) {
        this.merchantResourceIdSet = merchantResourceIdSet;
    }

    public boolean isMerchantNameQuery() {
        return merchantNameQuery;
    }

    public void setMerchantNameQuery(boolean merchantNameQuery) {
        this.merchantNameQuery = merchantNameQuery;
    }

    public boolean isMerchantTypeQuery() {
        return merchantTypeQuery;
    }

    public void setMerchantTypeQuery(boolean merchantTypeQuery) {
        this.merchantTypeQuery = merchantTypeQuery;
    }
}
