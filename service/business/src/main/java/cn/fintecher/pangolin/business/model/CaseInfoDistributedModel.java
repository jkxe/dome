package cn.fintecher.pangolin.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : huyanmin
 * @Description : 待分配策略分配model
 * @Date : 2017/12/27
 */
@Data
public class CaseInfoDistributedModel {
    @ApiModelProperty("案件ID")
    private String caseId;
    @ApiModelProperty("产品系列名称")
    private String seriesName;
    @ApiModelProperty("逾期期数")
    private Integer overduePeriods;
    @ApiModelProperty("案件编号")
    private String caseNumber;
    @ApiModelProperty("批次号")
    private String batchNumber;
    @ApiModelProperty("委托方")
    private String principalName;
    @ApiModelProperty("客户姓名")
    private String personalName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("身份证号")
    private String idCard;
    @ApiModelProperty("省份")
    private Integer province;
    @ApiModelProperty("城市")
    private Integer city;
    @ApiModelProperty("还款状态")
    private String payStatus;
    @ApiModelProperty("逾期天数")
    private Integer overdueDays;
    @ApiModelProperty("案件金额")
    private BigDecimal overdueAmount;
    @ApiModelProperty("佣金比例")
    private BigDecimal commissionRate;
    @ApiModelProperty("案件评分")
    private BigDecimal score;
    @ApiModelProperty("委案日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date delegationDate;
    @ApiModelProperty("结案日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeDate;
    @ApiModelProperty("委托方")
    private String outSourceName;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("合同金额")
    private BigDecimal contractAmount;
}