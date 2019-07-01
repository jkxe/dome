package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分案异常案件
 */
@Data
public class DivisionExceptionModel {
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

    @ApiModelProperty("分配时间")
    private Date caseFollowInTime;
}
