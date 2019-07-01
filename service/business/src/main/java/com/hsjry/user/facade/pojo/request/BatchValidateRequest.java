package com.hsjry.user.facade.pojo.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author chenmin
 * @since 2018/12/18
 */
@Data
public class BatchValidateRequest implements Serializable {

    private static final long serialVersionUID = 2424720901891217633L;

    /**
     * 身份证失效期延长请求列表
     */
    private Map<String, Date> validatingMap;
}
