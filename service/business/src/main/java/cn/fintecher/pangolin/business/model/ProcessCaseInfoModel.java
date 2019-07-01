package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProcessCaseInfoModel {

    @ApiModelProperty(notes = "任务id")
    private String taskId;

    @ApiModelProperty(notes = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "节点id")
    private String nodeId;

    @ApiModelProperty(notes = "角色id")
    private String roleId;

    @ApiModelProperty(notes = "角色名称")
    private String roleName;

    @ApiModelProperty(notes = "流程id")
    private String approvalId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "还款状态-逾期期数")
    private String payStatus;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "逾期总金额(元)")
    private BigDecimal overdueAmount;

    @ApiModelProperty(notes = "客户姓名")
    private String personName;

    @ApiModelProperty(notes = "客户手机号")
    private String mobileNo;

    @ApiModelProperty(notes = "身份证号码")
    private String idCard;

    @ApiModelProperty(notes = "委托方")
    private String principalName;

    @ApiModelProperty(notes = "减免罚息")
    private BigDecimal derateFine = new BigDecimal(0);

    @ApiModelProperty(notes = "减免催收服务费")
    private BigDecimal derateCollection = new BigDecimal(0);

    @ApiModelProperty(notes = "减免其他催收服务费")
    private BigDecimal derateOther = new BigDecimal(0);

    @ApiModelProperty(notes = "减免违约金")
    private BigDecimal deratePenalSum = new BigDecimal(0);

    @ApiModelProperty(notes = "申请减免金额")
    private BigDecimal applyDerateAmt = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期罚息")
    private BigDecimal overdueFine;

    @ApiModelProperty(notes = "逾期违约金")
    private BigDecimal overduePenalSum = new BigDecimal(0);

    @ApiModelProperty(notes = "催收服务费")
    private  BigDecimal collectionServiceCharge = new BigDecimal(0);

    @ApiModelProperty(notes = "其他金额")
    private BigDecimal otherAmt;

    @ApiModelProperty(notes = "还款审批ID")
    private String casePayApplyId;

    @ApiModelProperty(notes = "减免标识 0-没有 1-有")
    private Integer flag;

    @ApiModelProperty(notes = "还款类型")
    private Integer payType;

    @ApiModelProperty(notes = "申请还款金额")
    private BigDecimal applyPayAmt = new BigDecimal(0);

    @ApiModelProperty(notes = "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    @ApiModelProperty(notes = "案件id")
    private String caseId;
}
