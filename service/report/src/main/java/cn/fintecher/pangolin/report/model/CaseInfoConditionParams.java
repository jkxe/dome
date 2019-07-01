package cn.fintecher.pangolin.report.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : xiaqun
 * @Description : 查询案件条件
 * @Date : 9:52 2017/11/1
 */

@Data
public class CaseInfoConditionParams {
    private String collectionStatusList; //催收状态集合
    private String assistStatusList; //协催状态集合
    private String caseTypeList;//案件类型集合
    private String outStatusList;//委外状态集合
    private Integer collectionStatus; //催收状态
    private String personalName; //客户姓名
    private Integer personalType;//客户类型
    private String mobileNo; //客户手机号
    private String deptCode; //机构code码
    private String collectorName; //催收员姓名
    private String lastCollectorName;//上一个催收员姓名
    private BigDecimal overdueMaxAmt; //最大案件金额
    private BigDecimal overdueMinAmt; //最小案件金额
    private String payStatus; //还款状态
    private Integer overMaxDay; //最大逾期天数
    private Integer overMinDay; //最小逾期天数
    private String batchNumber; //批次号
    private String caseNumber;//案件编号
    private String principalId; //委托方ID
    private String principalName; //委托方名称
    private String outsName;// 委外方名称
    private String idCard; //客户身份证号
    private Integer followupBack; //催收反馈
    private Integer assistWay; //协催方式
    private Integer caseMark; //案件标记
    private String collectionType; //催收类型
    private Integer areaId; //所属城市ID
    private Integer parentAreaId; //省ID
    private String startFollowDate; //跟进时间开始
    private String endFollowDate; //跟进时间结束
    private String startOverDueDate; // 逾期开始时间
    private String endOverDueDate; // 逾期结束时间
    private String sourceChannel; // hy-来源渠道(线上线下)
    private String collectionMethod; // 催收方式
    private String queueName;//队列名称
    private Integer overdueCountMin;// 逾期次数起
    private Integer overdueCountMax;// 逾期次数止
    private String cardNumber; //银行卡号
    private BigDecimal realPayMaxAmt; //最大还款金额
    private BigDecimal realPayMinAmt; //最小还款金额
    private String userId; //用户ID
    private Integer isManager; //是否是管理员
    private String companyCode; //公司code
    private Integer feedBack; //催收反馈
    private String code; //催收反馈
    private Integer page; //页数
    private Integer size; //每页条数
    private String sort; //排序
    private String operatorMinTime;//最小回收时间
    private String operatorMaxTime;//最大回收时间
    private String delegationDate; //委案日期
    private String closeDate;//结案日期
    private String outTime; //委外日期
    private String overOutsourceTime;//委外到期日期
    private String overduePeriods;//逾期期数
    private BigDecimal commissionMaxRate;//佣金最大比例
    private BigDecimal commissionMinRate;//佣金最小比例
    private Integer circulationStatus; //流转审批状态
    private String endOutsourceTime; //委外结案日期
    private Integer handMaxNumber;//最大案件手数
    private Integer handMinNumber;//最小案件手数
    private String packagingTime;//打包时间
    private Integer repairStatus;//修复状态
    private String delegationDateStart; //最小委案日期
    private String delegationDateEnd; //最大委案日期
    private String closeDateStart;//最小结案日期
    private String closeDateEnd;//最大结案日期
    private String seriesName; //产品系列名称（类型）
    private String seriesId;//产品系列id
    private String productName;//产品名称
    private Integer turnFromPool;//流转来源
    private Integer turnToPool;//流转去向
    private Integer turnApprovalStatus;//流转状态
    private String caseFollowInTime;//案件流入时间
    private Integer lawsuitResult;//诉讼状态
    private Integer antiFraudResult;//反欺诈状态
    private Integer casePoolType;//案件池类型
    private Integer type;//类型
    private String  loanInvoiceNumber; // 借据号
   private Integer overdueCount; //  逾期次数
    private String  operatorTime; // 信修时间
    private String merchantName;//经销商
    private String startSettleDate;//开始出催时间(结清时间)
    private String endSettleDate;//结束出催时间(结清时间)

}