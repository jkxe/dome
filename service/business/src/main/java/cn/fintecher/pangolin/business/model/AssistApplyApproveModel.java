package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : sunyanping
 * @Description : 协催申请审批对象
 * @Date : 2017/7/17.
 */
@Data
@ApiModel("协催申请审批对象")
public class AssistApplyApproveModel {
    @ApiModelProperty("申请说明")
    private String applyRemark;
    @ApiModelProperty("审批结果")
    private Integer approveResult;
    @ApiModelProperty("审批意见")
    private String approveMemo;
}
