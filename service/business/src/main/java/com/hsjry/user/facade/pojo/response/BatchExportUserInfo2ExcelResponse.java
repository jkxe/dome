package com.hsjry.user.facade.pojo.response;

import java.io.Serializable;

/**
 * @author zhengqy15963
 * @version $$Id: BatchExportUserInfo2ExcelResponse.java, v 0.1 2018/11/5 14:35 zhengqy15963 Exp $$
 */
public class BatchExportUserInfo2ExcelResponse implements Serializable {
    private static final long serialVersionUID = 8937500104643564909L;

    /**
     * 先行返回的返回值
     */
    private String tempResult;

    public String getTempResult() {
        return tempResult;
    }

    public void setTempResult(String tempResult) {
        this.tempResult = tempResult;
    }
}
