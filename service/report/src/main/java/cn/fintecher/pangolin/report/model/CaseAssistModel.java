package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : xiaqun
 * @Description : 协催案件展示模型
 * @Date : 17:15 2017/11/2
 */

@Data
public class CaseAssistModel {
    private String caseId; //案件ID
    private String assistId; //协催案件ID
    private String caseNumber; //案件编号
    private String personalName; //客户姓名
    private String personalId; //客户ID
    private String mobileNo; //手机号
    private BigDecimal overdueAmount; //案件金额
    private Integer assistWay; //协催方催状态
    private Date caseFlowinTime; //案件式
    private Integer assistStatus; //协流入时间
    private Integer leaveCaseFlag; //留案标识
    private String latelyCollectorName; //上一个协催员姓名
    private String currentCollectorName; //当前催收员姓名
    private String assistCollectorName; //催收员
    private Integer markId; //案件标色
    private String operatorName; //操作员姓名
    private Integer followupBack; //催收反馈
    private Date followupTime; //跟进时间
    private BigDecimal promiseAmt; //承诺还款金额
    private Date promiseTime; //承诺还款时间
    private BigDecimal accountBalance;//账户余额
    private String loanInvoiceNumber;//hy-借据号
    private String overDuePeriods;//逾期期数
    private String overDueDays;//逾期天数
}