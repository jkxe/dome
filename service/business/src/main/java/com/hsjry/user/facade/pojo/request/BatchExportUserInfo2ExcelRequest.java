package com.hsjry.user.facade.pojo.request;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: BatchExportUserInfo2ExcelRequest.java, v 0.1 2018/11/5 14:33 zhengqy15963 Exp $$
 */
public class BatchExportUserInfo2ExcelRequest implements Serializable {
    private static final long serialVersionUID = 2086278232922011792L;
    //组织ID
    @NotNull(errorCode = "000001", message = "组织ID")
    @NotBlank(errorCode = "000001", message = "组织ID")
    private String organId;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

}
