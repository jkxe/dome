package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CaseTurnModel {

    @ApiModelProperty(notes = "案件编号（借据号）")
    private String caseNumber;

    @ApiModelProperty(notes = "接收人ID")
    private String receiveUserId;

    @ApiModelProperty(notes = "接受人名称")
    private String receiveUserRealName;

    @ApiModelProperty(notes = "接受部门名称")
    private String receiveDeptName;

    @ApiModelProperty(notes = "操作员")
    private String operatorUserName;

    @ApiModelProperty(notes = "公司code码")
    private String companyCode;

    @ApiModelProperty(notes = "流转类型 0-自动流转 1-手动流转 2-正常流转")
    private Integer circulationType;

    @ApiModelProperty(notes = "流转来源（内催-225、委外-226、特殊-229、停催-230、贷后预警-231）")
    private Integer turnFromPool;

    @ApiModelProperty(notes = "流转去向（内催-225、委外-226、特殊-229、停催-230、贷后预警-231、诉讼-232、反欺诈-233）")
    private Integer turnToPool;

    @ApiModelProperty(notes = "流转说明")
    private String turnDescribe;

    @ApiModelProperty(notes = "流转审核状态（待审批-213，通过-214，拒绝-215）")
    private Integer turnApprovalStatus;

    @ApiModelProperty(notes = "审批意见")
    private String approvalOpinion;

    @ApiModelProperty(notes = "审批流程id")
    private String approvalId;

    @ApiModelProperty(notes = "申请人")
    private String applyName;

}
