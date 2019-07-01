package cn.fintecher.pangolin.report.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


/**
 * 委外案件池
 * 
 * @author suyuchao
 * @email null
 * @date 2019-03-02 18:56:40
 */
@Data
@ApiModel("委外案件池")
public class OutsourcePoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	

    @ApiModelProperty("主键")
	private String id;

    @ApiModelProperty("案件ID")
	private String caseId;

    @ApiModelProperty("案件编号")
	private String caseNumber;

    @ApiModelProperty("委外方ID")
	private String outId;

    @ApiModelProperty("操作时间")
	private Date operateTime;

    @ApiModelProperty("委外时间")
	private Date outTime;

    @ApiModelProperty("操作人")
	private String operator;

    @ApiModelProperty("委外状态")
	private Integer outStatus;

    @ApiModelProperty("委外批次号")
	private String outBatch;

    @ApiModelProperty("佣金比例")
	private String commissionRate;

    @ApiModelProperty("佣金")
	private Long commission;

    @ApiModelProperty("委外回款金额")
	private BigDecimal outBackAmt;

    @ApiModelProperty("逾期时段")
	private String overduePeriods;

    @ApiModelProperty("案件总金额")
	private BigDecimal contractAmt;

    @ApiModelProperty("委外回款操作状态")
	private Integer outoperationStatus;

    @ApiModelProperty("公司特定标识")
	private String companyCode;

    @ApiModelProperty("委外到期时间")
	private Date overOutsourceTime;

    @ApiModelProperty("已结案日期")
	private Date endOutsourceTime;

}
