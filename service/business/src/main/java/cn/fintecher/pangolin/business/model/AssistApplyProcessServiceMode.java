package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("协催案件展示对象")
public class AssistApplyProcessServiceMode {

    @ApiModelProperty(notes = "申请协催案件id")
    private String assistApplyId;

    @ApiModelProperty(notes = "案件id")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty("客户姓名")
    private String personalName;

    @ApiModelProperty("客户手机号")
    private String personalPhone;

    @ApiModelProperty("客户信息ID")
    private String personalId;

    @ApiModelProperty("催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;

    @ApiModelProperty("部门Code")
    private String deptCode;

    @ApiModelProperty("委托方ID")
    private String principalId;

    @ApiModelProperty("委托方名称")
    private String principalName;

    @ApiModelProperty("逾期总金额")
    private BigDecimal overdueAmount;

    @ApiModelProperty("逾期天数")
    private Integer overdueDays;

    @ApiModelProperty("逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty("持案天数")
    private Integer holdDays;

    @ApiModelProperty("剩余天数")
    private Integer leftDays;

    @ApiModelProperty("省份编号")
    private Integer areaId;

    @ApiModelProperty("城市名称")
    private String areaName;

    @ApiModelProperty("申请人")
    private String applyUserName;

    @ApiModelProperty("申请人姓名")
    private String applyRealName;

    @ApiModelProperty("申请人部门名称")
    private String applyDeptName;

    @ApiModelProperty("申请原因")
    private String applyReason;

    @ApiModelProperty("申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    @ApiModelProperty("申请失效日期")
    private Date applyInvalidTime;

    @ApiModelProperty("协催方式")
    private Integer assistWay;

    @ApiModelProperty("产品系列ID")
    private String productSeries;

    @ApiModelProperty("产品ID")
    private String productId;

    @ApiModelProperty("产品系列名称")
    private String productSeriesName;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("公司Code码")
    private String companyCode;

    @ApiModelProperty(notes = "申请流程id")
    private String approvalId;

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "账户余额")
    private BigDecimal accountBalance;
}
