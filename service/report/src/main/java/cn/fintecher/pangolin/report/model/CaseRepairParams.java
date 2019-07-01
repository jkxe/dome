package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2017/8/22.
 */
@Data
public class CaseRepairParams {
    private String deptCode;
    private String companyCode;
    private String collector;
    private String name;
    private String address;

    @ApiModelProperty("客户姓名")
    private String personalName;
    @ApiModelProperty("客户手机号")
    private String mobileNo; //客户手机号
    @ApiModelProperty("案件编号")
    private String caseNumber;//案件编号（）
    @ApiModelProperty("客户身份证号")
    private String idCard; //客户身份证号
    @ApiModelProperty("借据号")
    private String  loanInvoiceNumber; // 借据号
    @ApiModelProperty("最大逾期天数")
    private Integer overMaxDay; //最大逾期天数
    @ApiModelProperty("最小逾期天数")
    private Integer overMinDay; //最小逾期天数
    @ApiModelProperty("信 修状态")
    private Integer repairStatus;
    @ApiModelProperty("逾期期数")
    private String overduePeriods;//逾期期数
    @ApiModelProperty("信修时间")
    private String operatorTime;//信修时间
    @ApiModelProperty("逾期次数")
    private Integer overdueCount; //  逾期次数
}
