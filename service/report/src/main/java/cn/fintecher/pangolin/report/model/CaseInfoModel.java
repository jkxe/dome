package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : xiaqun
 * @Description : 展示用案件模型
 * @Date : 10:19 2017/11/1
 */

@Data
public class CaseInfoModel {
    private String caseId; //案件ID
    private String caseNumber; //案件编号
    private String batchNumber; //批次号
    private String personalName; //客户姓名
    private String mobileNo; //手机号
    private String idCard; //身份证号
    private String overdueAmount; //案件金额
    private String payStatus; //还款状态
    private String overduePeriods;//逾期期数
    private Integer overdueDays; //逾期天数
    private String principalName; //委托方名称
    private String collectorName; //催收员姓名
    private String lastCollectorName;//上一个催收员姓名
    private Integer collectionStatus; //催收状态(状态)
    private Date followupTime; //跟进时间
    private Date caseFollowInTime; //案件流入日期
    private Integer circulationStatus; //流转审批状态
    private Integer approveResult; //流转审批结果
    private Integer followupBack; //催收反馈
    private Integer assistWay; //协催方式  30：单次协催，31：全程协催
    private Integer assistFlag;//协催标识：0-未协催，1-协催
    private Integer holdDays; //持案天数
    private Integer leftDays; //剩余天数
    private Integer leaveCaseFlag; //留案标识
    private String deptCode; //部门code码
    private String deptName; //部门名称
    //private String name; //机构
    private Integer collectionType; //催收类型
    private Integer caseMark; //案件标记
    private String personalId; //客户信息ID
    private BigDecimal promiseAmt; //承诺还款金额
    private Date promiseTime; //承诺还款日期
//    private Integer areaId; //归属城市ID
    private String cityName; //归属城市
//    private Integer parentAreaId; //省ID
    private String areaName; //归属省份
    private BigDecimal realPayAmount; //已还金额
    private BigDecimal outBackAmt;//回款金额
    private Date operatorTime; //操作时间
    private String endRemark;
    private Integer repairStatus;//修复状态
    private Date delegationDate; //委案日期
    private Date closeDate; //结案日期
    private BigDecimal score; //案件评分
    private BigDecimal commissionRate;//佣金比例
    private String outsName;//委外方名称
    private Date outTime;//委外时间
    private Date endOutsourceTime;// 委外已结案日期
    private Integer caseHandNum;//案件手数
    private String state;// 核销说明
    private String caseRepairId;//修复案件id
    private String seriesName; //产品系列名称（类型）
    private String productName;//产品名称
    private Integer turnFromPool;//流转来源
    private Integer turnToPool;//流转去向
    private Integer turnApprovalStatus;//流转审核状态（待审批-213，通过-214，拒绝-215）
    private Integer lawsuitResult;//诉讼状态
    private Integer antiFraudResult;//反欺诈状态
    private String perdueDate; //每期还款日
    private Integer personalType;//顾客类型
    // zmm 2018-07-25 添加
    private Integer caseType;   //案件类型
    private Integer assistStatus;   //协催审批状态
    private String certificatesNumber;//证件号码

    private BigDecimal accountBalance;//账户余额
    private String loanInvoiceNumber;//hy-借据号
    private Date stopTime;//hy-停催时间
    private Date overDueDate; // 逾期日期
    private String sourceChannel;// 来源渠道
    private String collectionMethod;// 催收方式
    private String queueName;// 队列名称
    private String merchantName; //  经销商
    private Integer overdueCount;//逾期次数
    private Integer stopNum;//停催天数
    private Integer sex;//性别
    private Date settleDate;//出催时间(结清时间)
    private Date repayDate;// 应还日期

}