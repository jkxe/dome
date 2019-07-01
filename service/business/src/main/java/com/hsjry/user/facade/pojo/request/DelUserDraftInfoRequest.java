/**
 * @System: 消费金融
 * Copyright (c) 2017杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/5/18
 * Time: 14:01
 * @mail: xuxd14949@hundsun.com
 * @version: 2.0
 */

public class DelUserDraftInfoRequest implements Serializable{

    private static final long serialVersionUID = 749445174725107526L;

    @NotNull(errorCode = "000001", message = "申请编号")
    @NotBlank(errorCode = "000001", message = "申请编号")
    private String applyId;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}

