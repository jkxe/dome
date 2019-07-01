package com.hsjry.user.facade.pojo.request;

import lombok.Data;
import net.sf.oval.constraint.AssertValid;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/1
 */
@Data
public class BindCardRequest implements Serializable{
    private static final long serialVersionUID = 9048593149828252010L;

    @AssertValid(errorCode = "000001", message = "绑卡信息")
    private BindCardParam bindCardParam;

    @AssertValid(errorCode = "000001", message = "二类户信息")
    private SecondaryAccountParam secondaryAccountParam;

    private Boolean isOpenSecondary;
}
