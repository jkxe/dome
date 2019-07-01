package com.hsjry.user.facade.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenmin
 * @since 2018/12/18
 */
@Data
public class ValidateCertificationPojo implements Serializable {
    private static final long serialVersionUID = 8112514508504450824L;

    /**
     * 身份证号
     */
    private String certificationNo;

    /**
     * 失效时间
     */
    private Date invalidDate;
}
