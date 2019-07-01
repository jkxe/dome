package com.hsjry.user.facade.pojo;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketBasicInfoPojo.java, v 0.1 2018/8/13 15:31 zhengqy15963 Exp $$
 */
public class QueryMarketBasicInfoPojo implements Serializable {
    private static final long serialVersionUID = -3250132545664550478L;
    /**
     * 渠道经理基本信息
     */
    private MarketBasicInfoPojo basicInfoPojo;
    /**
     * 所属机构名称
     */
    private String belongOrganName;
    /**
     * 所属角色，参见枚举EnumSystemRoleType
     */
    private String channalManagerRoleId;
    /**
     * 启用停用状态
     */
    private String objectStatus;

    public MarketBasicInfoPojo getBasicInfoPojo() {
        return basicInfoPojo;
    }

    public void setBasicInfoPojo(MarketBasicInfoPojo basicInfoPojo) {
        this.basicInfoPojo = basicInfoPojo;
    }

    public String getBelongOrganName() {
        return belongOrganName;
    }

    public void setBelongOrganName(String belongOrganName) {
        this.belongOrganName = belongOrganName;
    }

    public String getChannalManagerRoleId() {
        return channalManagerRoleId;
    }

    public void setChannalManagerRoleId(String channalManagerRoleId) {
        this.channalManagerRoleId = channalManagerRoleId;
    }

    public String getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(String objectStatus) {
        this.objectStatus = objectStatus;
    }
}
