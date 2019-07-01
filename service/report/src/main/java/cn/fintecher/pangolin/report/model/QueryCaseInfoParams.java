package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : huyanmin
 * @Description : 委外催收中查询的Params
 * @Date : 2017/9/25
 */

@Data
public class QueryCaseInfoParams {
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;
    @ApiModelProperty(value = "客户姓名")
    private String personalName;
    @ApiModelProperty(value = "手机号")
    private String mobileNo;
    @ApiModelProperty(notes = "逾期总金额开始")
    private BigDecimal overdueAmountStart;
    @ApiModelProperty(notes = "逾期总金额结束")
    private BigDecimal overdueAmountEnd;
    @ApiModelProperty(notes = "逾期日期开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date overDueDateStart;
    @ApiModelProperty(notes = "逾期日期结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date overDueDateEnd;
    @ApiModelProperty(notes = "hy-来源渠道(线上线下)")
    private String sourceChannel;
    @ApiModelProperty(notes = "hy-催收方式（自催或者第三方）")
    private String collectionMethod;

    @ApiModelProperty(value = "公司code")
    private String companyCode;
    @ApiModelProperty(value = "批次号")
    private String batchNumber;
//    @ApiModelProperty(value = "委外方名称")
//    private String outsName;
    @ApiModelProperty(value = "委外方id")
    private String outsId;

    @ApiModelProperty(value = "页码数")
    private Integer page;
    @ApiModelProperty(value = "每页大小")
    private Integer size;

}
