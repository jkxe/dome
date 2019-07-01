/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2018 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;
import java.util.HashMap;

import com.hsjry.user.facade.pojo.UserManageInfoPojo;

/**
 * 
 * @author zhengqy15963
 * @version $Id: QueryMerchantManagerInfoListResponse.java, v 1.0 2018年5月28日 下午3:27:21 zhengqy15963 Exp $
 */
public class QueryMerchantManagerInfoListResponse implements Serializable {

    /**  */
    private static final long                   serialVersionUID = -6493759631704941394L;
    /**
     * 返回对象为map，map的key为机构客户id，value为商户的经营信息
     *   */
    private HashMap<String, UserManageInfoPojo> merchantManagerInfoMap;

    /**
     * Getter method for property <tt>merchantManagerInfoMap</tt>.
     * 
     * @return property value of merchantManagerInfoMap
     */
    public HashMap<String, UserManageInfoPojo> getMerchantManagerInfoMap() {
        return merchantManagerInfoMap;
    }

    /**
     * Setter method for property <tt>merchantManagerInfoMap</tt>.
     * 
     * @param merchantManagerInfoMap value to be assigned to property merchantManagerInfoMap
     */
    public void setMerchantManagerInfoMap(HashMap<String, UserManageInfoPojo> merchantManagerInfoMap) {
        this.merchantManagerInfoMap = merchantManagerInfoMap;
    }

}
