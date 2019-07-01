package com.hsjry.user.facade.pojo.response;

import com.hsjry.lang.common.base.enums.EnumBool;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 获取用户认证信息响应
 * @author zhengjl <zhengjl20204@hundsun.com>
 * @version V1.2.3
 * @date 2017年07月06日  21:17
 */
public class QueryUserRecognitionResponse implements Serializable {
    private static final long serialVersionUID = -1517582029742637357L;

    /**
     * 最新更新时间
     */
    private Date updateTime;

    /**
     * 认证是否有效
     */
    private EnumBool recongitionValid;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public EnumBool getRecongitionValid() {
        return recongitionValid;
    }

    public void setRecongitionValid(EnumBool recongitionValid) {
        this.recongitionValid = recongitionValid;
    }
}
