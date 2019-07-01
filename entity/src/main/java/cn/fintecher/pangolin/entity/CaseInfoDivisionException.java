package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 分案异常案件
 * 
 * @author suyuchao
 * @email null
 * @date 2019-01-19 15:26:45
 */
@Entity
@Table(name = "case_info_division_exception")
@Data
public class CaseInfoDivisionException extends BaseEntity{
	private static final long serialVersionUID = 1L;

    @ApiModelProperty("案件ID")
	private String caseInfoId;

    @ApiModelProperty("案件待分配池ID")
	private String caseInfoDistributedId;

    @ApiModelProperty("hy-客户id")
	private String customerId;

    @ApiModelProperty("姓名")
	private String customerName;

    @ApiModelProperty("手机号码")
	private String customerMobileNo;

    @ApiModelProperty("身份证号码")
	private String customerIdCard;

    @ApiModelProperty("案件编号")
	private String caseNumber;

    @ApiModelProperty("逾期总金额")
	private BigDecimal overdueAmount;

    @ApiModelProperty("逾期期数")
	private Integer overduePeriods;

    @ApiModelProperty("异常类型 数据字典type_code:0403")
	private Integer exceptionType;

    @ApiModelProperty("异常检查时间")
	private Date exceptionCheckTime;

    @ApiModelProperty("状态 DEL删除，STP停用，NOL正常")
	private String status;

    @ApiModelProperty("备注")
	private String remark;

    @ApiModelProperty("")
	private String createBy;

    @ApiModelProperty("创建时间")
	private Date createTime;

    @ApiModelProperty("更新人")
	private String updateBy;

    @ApiModelProperty("更新时间")
	private Date updateTime;
}
