package com.hsjry.user.facade.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenmin
 * @since 2018/11/20
 */
@Data
public class MetaProfession implements Serializable{
    private static final long serialVersionUID = 2424346436603316377L;

    private Integer code;

    private String description;

    private Boolean isUsed;

    private MetaProfession metaProfession;
}
