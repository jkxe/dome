package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

import java.util.List;

@Data
public class CtmInterfaceDataBean {

    private String userField8;//客户来源
    private String userField12;//推荐人
    private String userField13;//推荐支行
    private String userField11;//其他来源
    private String autopayBankAcctname;//约定还款扣款账户姓名
    private String autopayBankAcctno;//约定还款扣款账号
    private String bankName;//开户行名称
    private String applNo;//申请号
    private String fullname;//姓名
    private String sex;//性别
    private String mobileNo;//手机号
    private String age;//年龄
    private String email;//电子邮件地址
    private String maritalStatus;//婚姻状态
    private String noOfDependents;//子女人数
    private String idType;//证件类型
    private String idno;//证件号码
    private String nationality;//国籍
    private String qualification;//学历
    private String idIssuer;//发证机关所在地
    private String idExpiryDate;//身份证到期日
    private String idAdr;//身份证地址
    private String homeAdr;//家庭地址
    private String homeTelno;//家庭座机
    private String livedThereSince;//现居住迁入时间
    private String homeOwnership;//居住房屋的所有权
    private String housePurchasePrice;//房屋购置价
    private String houseAssAmt;//房屋评估价
    private String firstPayment;//首付金额
    private String mortgageBalance;//房贷余额
    private String totalMortgagePeriod;//房贷总期数
    private String mortgageRepaymentPeriod;//房贷已还款期数
    private String mortgageMonthlyReturn;//房贷月均还款额
    private String hoseAdr;//房产地址
    private String hoseAdr1;//其他房产1地址
    private String hoseAdr2;//其他房产2地址
    private String hoseAdr3;//其他房产3地址
    private String hoseAdr4;//其他房产4地址
    private String vehicleLicence;//车牌号
    private String driverLicence;//驾照号码
    private String compName;//单位名称
    private String compDepartment;//工作部门
    private String jobPosition;//岗位
    private String compDesgn;//在公司职务
    //	private String entryTime;//何时进入公司
    private String lengthSrv;//在该公司的服务时间(月)
    private String compStructure;//单位性质
    //	private String unitScale;//单位规模
//	private String unitEstshTime;//单位成立时间
    private String bussSic;//行业类别
    private String compTelno;//公司电话
    private String compAdr;//单位地址
    //	private String monthPayDay;//每月发薪日
    private String incomeType;//收入认定类型
    private String mthBasicSalary;//月均薪资（税后）
    //	private String otherMonthIncome;//每月其他收入
    private String fundCompName;//公积金缴存单位
    private String fundCount;//缴存期数
    private String fundAmt;//公积金个人缴存额度
    private String socialsecurityAmount;//个人社保月均缴存额度
    private String socialsecurityRatio;//社保缴存比例
    private String mthFixedAllow;//月固定补贴
    private String annualIncome;//年收入
    private String mthIncome;//家庭月收入
    //	private String monthExpenditure;//月均支出
//	private String sourceOfIncome;//收入来源说明
    private String mthRental;//每月租房/房贷费用
    private List<Contact> contacts;//联系人
    private String productCd;//产品类型
    private String productName;//产品名称
    private String promotionCd;//活动项目编号
    private String channelCd;//申请渠道代码
    private String merchandiseTotalPrice;//商品总价
    private String merchandiseCat1;//商品类型1
    private String merchandiseBrand1;//商品品牌(代银)1
    private String merchandiseModel1;//商品型号(账号)1
    private String merchandisePrice1;//商品价格(贷款）1
    private String merchandiseCat2;//商品类型2
    private String merchandiseBrand2;//商品品牌(代银)2
    private String merchandiseModel2;//商品型号(账号)2
    private String merchandisePrice2;//商品价格(贷款)2
    private String selfPayAmount;//自付金额
    private String loanAmount;//申请金额
    private String loanDate;//放款日期
    private String approvedLoanAmt;//批准贷款金额
    private String loanTenure;//贷款期限
    private String billCycle;//账单周期
    private String firstDueDate;//首次还款日
    private String mthlyPmt;//每月还款额
    private String amorzType;//还款计划类型
    private String analInterestRate;//年利率
    private String repayMethod;//还款方式
    //	private String comsiveInRate;//综合利率
    private String penaltyRate;//罚息费率
    //	private String conPenaltyRate;//合同违约金费率
    private String advRepPenRate;//提前还款违约金费率
    private String staSerFeeRate;//分期服务费费率
    private String repaySubloanWether;//是否还后续贷
    private String loanStatus;//贷款状态（核销）
    private String blacklistFlag;//黑名单标志
    private String storeNo;//销售门店代码
    private String saleRepName;//销售代表姓名
    private String saleRepPhone;//销售代表手机号
    private String userField9;//最高额抵押标识
    private String userField10;//主贷款标识
    private String userField4;//主贷款申请号
    private String salesRemark;//销售代表备注
    private String pbocScore;//人行征信评分
    private String decisionCd;//决定代码
    private String decisionReason;//决定原因
    private String residentDocMemo;//居住证明核查-备注
    private String residentApproveMemo;//居住证明-审批备注
    private String incomeDocMemo;//收入证明核查-备注
    private String incomeApproveMemo;//收入证明-审批备注
    private String coPhoneExistInd;//单位电话-调查结果
    private String coPhoneMemo;//单位电话-备注
    private String coPhoneApproveMemo;//单位电话-审批备注
    private String homePhoneMemo;//家庭电话-备注
    private String homePhoneApproveMemo;//家庭电话-审批备注
    private String mobileMemo;//本人手机-备注
    private String mobileApproveMemo;//本人手机-审批备注
    private String relativeMemo;//其他联系人-备注
    private String inetMemo;//系统校验-备注
    private String remarks;//备注
    private String overduePrincipal;//逾期本金
    private String dueNum;//逾期期数
    private String dpdDays;//逾期天数
    private String maxdelqdayCnt;//逾期最大天数
    private String latestDateReturn;//最近一次应还日期
    private String totalOverdue;//逾期总额
//    private String overdueInterest;//逾期利息
//    private String lateCharge;//逾期罚息
//    private String overdueGold;//逾期滞纳金
    private String lastedTerm;//已执行期数(7.18增)
    private String returnsNum;//已还期数
    private String remPeriodsNum;//剩余期数
    //	private String reimbAmount;//已还款金额
    private String unpaidPrincipal;//未偿还本金
    private String unpaidInterest;//未偿还利息
    private String unpaidPnltInt;//未偿还罚息
    private String unpaidOtInt;//未偿还其它利息
    private String unpaidMthFee;//未偿还管理费
    private String unpaidOtFee;//未偿还其它费用
    private String unpaidLpc;//未偿还滞纳金
//    private String currPnltInt;//当前未结罚息复利

    private String unbillInt;//未结利息
    private String totalAcru;//未结罚息
    private String lastPaymentDate;//最近一次还款日期
    private String lastPaymentAmt;//最近一次还款金额
    private String remainPrin;//剩余本金
    private String remainInt;//剩余利息
    private String remainFee;//剩余月服务费
    private String dueApplyCnt;//逾期账户数
    private String inColCnt;//内催次数
    private String outColCnt;//外包次数
    private List<RepayDetailed> repayDetaileds;//还款明细
    //	private List<RepayRecord> repayRecords;//还款明细
    private List<AttachmentInfo> attachmentInfoList;//附件信息

    private String settleAmt; //结清金额（7.25新增）
    private List<WriteOffRepDetail> woRepDetail;//核销还款明细
}
