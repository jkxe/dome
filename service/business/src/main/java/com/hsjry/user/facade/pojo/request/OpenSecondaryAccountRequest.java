package com.hsjry.user.facade.pojo.request;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/2
 */
@Data
public class OpenSecondaryAccountRequest implements Serializable {
    private static final long serialVersionUID = 346489157238721402L;

    /**
     * 主卡的resourceId
     */
    @NotEmpty
    private String resourceId;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String certificationNo;

    @NotEmpty
    private String userName;
}
