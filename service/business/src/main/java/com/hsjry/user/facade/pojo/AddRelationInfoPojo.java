/**
 * hsjry.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.hsjry.user.facade.pojo;

import com.hsjry.user.facade.pojo.enums.EnumRelationType;

import net.sf.oval.constraint.NotNull;


/**
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @Description:
 * @date 2017年10月22日  21:23
 */
public class AddRelationInfoPojo extends AddRelationPojo {

    /**关系类型*/
    @NotNull(errorCode = "000001", message = "关系类型")
    private EnumRelationType relationType;

    public EnumRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(EnumRelationType relationType) {
        this.relationType = relationType;
    }
}
