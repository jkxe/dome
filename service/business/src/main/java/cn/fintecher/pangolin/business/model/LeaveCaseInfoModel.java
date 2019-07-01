package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class LeaveCaseInfoModel {

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty("案件编号")
    private String caseNumber;

    @ApiModelProperty("案件批次号")
    private String batchNumber;

    @ApiModelProperty(notes = "受托方名称")
    private String principalName;

    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;

    @ApiModelProperty(notes = "身份证号码")
    private String idCard;

    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期期数")
    private String overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays = 0;

    @ApiModelProperty(notes = "催收反馈")
    private Integer followupBack;

    @ApiModelProperty(notes = "持案天数")
    private Integer holdDays;

    @ApiModelProperty(notes = "剩余天数")
    private Integer leftDays;

    @ApiModelProperty(notes = "申请人")
    private String applyUser;

    @ApiModelProperty(notes = "申请时间")
    private Date applyTime;

    @ApiModelProperty(notes = "流程审批id")
    private String approvalId;

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    @ApiModelProperty(notes = "留案申请id")
    private String leaveId;

    @ApiModelProperty(notes = "流案原因")
    private String leaveReason;

    @ApiModelProperty(notes = "留案到期时间")
    private Date leaveCaseExpireTime;

    @ApiModelProperty(notes = "留案类型")
    private String leaveCaseType;
}
