package com.hsjry.user.facade.pojo.request;

import lombok.Data;
import net.sf.oval.constraint.NotEmpty;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/12
 */
@Data
public class ChangeSecondaryAccountRequest implements Serializable {
    private static final long serialVersionUID = 3272401699032454130L;

    @NotEmpty
    private String secondAccount;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String certificationNo;

    @NotEmpty
    private String mobile;

    /**
     * 新的一类卡的resourceId
     */
    @NotEmpty
    private String resourceId;

}
