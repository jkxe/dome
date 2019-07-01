package com.hsjry.user.facade.pojo.request;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketInfoListByProvinceAndCityAndAreaRequest.java, v 0.1 2018/8/2 19:01 zhengqy15963 Exp $$
 */
public class QueryMarketInfoListByProvinceAndCityAndAreaRequest implements Serializable {
    private static final long serialVersionUID = 7073335166422688786L;
    /**
     * 省编号
     */
    private String provinceCode;
    /**
     * 市编号
     */
    private String cityCode;
    /**
     * 区编号
     */
    private String areaCode;

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
