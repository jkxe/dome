/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import com.hsjry.user.facade.pojo.enums.EnumPadEnterItemType;
import com.hsjry.user.facade.pojo.enums.EnumPadEnterType;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 16:39
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class QueryUserPadEnterItemRequest implements Serializable {

    private static final long serialVersionUID = -6518935173684989283L;

    @NotNull(errorCode = "000001", message = "编号不能为空")
    @NotBlank(errorCode = "000001", message = "编号不能为空")
    private String draftNo;

    private EnumPadEnterType type;

    private EnumPadEnterItemType itemType;

    public String getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(String draftNo) {
        this.draftNo = draftNo;
    }

    public EnumPadEnterItemType getItemType() {
        return itemType;
    }

    public void setItemType(EnumPadEnterItemType itemType) {
        this.itemType = itemType;
    }

    public EnumPadEnterType getType() {
        return type;
    }

    public void setType(EnumPadEnterType type) {
        this.type = type;
    }
}

