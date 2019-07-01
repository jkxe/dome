package com.hsjry.user.facade.pojo.request;

import lombok.Data;
import net.sf.oval.constraint.AssertValid;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2019/1/2
 */
@Data
public class ChannelBindCardRequest implements Serializable{
    private static final long serialVersionUID = 1520419595510989648L;

    /**
     * 绑卡所需字段
     */
    @AssertValid(errorCode = "000001", message = "绑卡信息")
    private BindCardParam bindCardParam;

    /**
     * 是否设置主卡
     */
    private Boolean isMainCard;

}
