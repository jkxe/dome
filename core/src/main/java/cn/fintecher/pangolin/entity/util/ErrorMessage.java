package cn.fintecher.pangolin.entity.util;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 此类主要用来展示异步处理的时候 将所有的错误信息汇总，展示给前端
 * 目前支持的场景有 案件确认 ，导出跟进记录
 * Created by qijigui on 2018-01-29.
 */
public class ErrorMessage implements Serializable {

    @ApiModelProperty("错误来源")
    private String errorSource;
    @ApiModelProperty("错误内容")
    private String errorContent;
    @ApiModelProperty("错误时间")
    private Date errorDate;
    @ApiModelProperty("操作人真名")
    private String operatorName;

    public String getErrorSource() {
        return errorSource;
    }

    public void setErrorSource(String errorSource) {
        this.errorSource = errorSource;
    }

    public String getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorSource='" + errorSource + '\'' +
                ", errorContent='" + errorContent + '\'' +
                ", errorDate=" + errorDate +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
