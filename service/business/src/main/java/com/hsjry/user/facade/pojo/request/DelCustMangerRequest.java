/**
 * @System: 消费金融
 * Copyright (c) 2018杭州恒生云融网络有限公司-版权所有
 */
package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author: xuxd14949
 * Date: 2018/8/1
 * Time: 20:58
 * @mail: xuxd14949@hsjry.com
 * @version: 2.0
 */

public class DelCustMangerRequest implements Serializable {

    private static final long serialVersionUID = 6099275221568212702L;

    //联系点ID
    @NotNull(errorCode = "000001", message = "编号id")
    @NotBlank(errorCode = "000001", message = "编号id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

