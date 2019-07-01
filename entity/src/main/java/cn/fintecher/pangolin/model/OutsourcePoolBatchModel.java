package cn.fintecher.pangolin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OutsourcePoolBatchModel {
    private String id;//委外池的id
    private String caseId;//案件id
    private String caseNumber;//案件编号
    private Integer leaveCaseFlag;//留案标志
    private Date followupTime;//跟进时间
    private Integer holdDays;//持案天数
    private Integer leftDays;//剩余天数
    private String companyCode;//公司编码
    private String collectorName;//催收员
    private Date delegationDate; //委案日期
    private String principalName;//委托方名称
    private String personalName;//客户姓名
    private String personalId;//客户id
    private String mobileNo;//手机号
    private String idCard;//客户身份证号
    private String areaName;//城市名称
    private Integer overdueDays;//逾期天数
    private String overduePeriods;//逾期阶段
    private Integer collectionStatus;//催收状态
    private String seriesName;//产品类型名称

    private BigDecimal accountBalance;//账户余额
    private Date overDueDate;//逾期开始时间 = 应还日期 + 1
    private String productName; // 产品名称
    private String sourceChannel; // 来源渠道
    private String collectionMethod; // 催收模式

    private BigDecimal contractAmt;//案件金额
    private String outsId;//受托方id
    private String outsName;//受托方名称
    private Date overOutsourceTime;//委外到期日期
    private Integer operationType;//类型
    private String outBatch;//批次号
    private String loanInvoiceNumber;//借据号
}
