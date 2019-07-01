/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo;

import com.hsjry.user.facade.pojo.enums.EnumPadEnterItemType;
import com.hsjry.user.facade.pojo.enums.EnumPadEnterType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 16:42
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class UserPadItemPojo implements Serializable {

    private static final long serialVersionUID = -812768248408439349L;

    private String id;

    private String draftNo;

    private EnumPadEnterType type;

    private EnumPadEnterItemType itemType;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(String draftNo) {
        this.draftNo = draftNo;
    }

    public EnumPadEnterType getType() {
        return type;
    }

    public void setType(EnumPadEnterType type) {
        this.type = type;
    }

    public EnumPadEnterItemType getItemType() {
        return itemType;
    }

    public void setItemType(EnumPadEnterItemType itemType) {
        this.itemType = itemType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

