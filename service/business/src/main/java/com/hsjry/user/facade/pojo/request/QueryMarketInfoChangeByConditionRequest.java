package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.enums.EnumObjectStatus;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: QueryMarketInfoChangeByConditionRequest.java, v 0.1 2018/8/10 19:26 zhengqy15963 Exp $$
 */
public class QueryMarketInfoChangeByConditionRequest implements Serializable {

    private static final long serialVersionUID = 6241017999142180812L;
    /**
     * 渠道经理名称
     */
    private String channelManagerName;
    /**
     * 渠道经理工号
     */
    private String channelManagerNo;
    /**
     * 启用停用状态
     */
    private EnumObjectStatus objectStatus;
    /**
     * 具体根节点
     */
    private String organRootId;

    public String getChannelManagerName() {
        return channelManagerName;
    }

    public void setChannelManagerName(String channelManagerName) {
        this.channelManagerName = channelManagerName;
    }

    public String getChannelManagerNo() {
        return channelManagerNo;
    }

    public void setChannelManagerNo(String channelManagerNo) {
        this.channelManagerNo = channelManagerNo;
    }

    public EnumObjectStatus getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(EnumObjectStatus objectStatus) {
        this.objectStatus = objectStatus;
    }

    public String getOrganRootId() {
        return organRootId;
    }

    public void setOrganRootId(String organRootId) {
        this.organRootId = organRootId;
    }
}
