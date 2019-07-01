package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueDetailBean {
    //生成caseNumber
    private String caseNumber;
    //逾期信息
    private String intoApplyId;//进件申请编号(案件编号)
    private String userId;//客户id
    //还款信息
    private String loanAmt;//贷款余额(账户余额)
    private String sourceChannel;//来源渠道
    private String collectionMethod;//催收方式
    private String productName;//产品名称
    private String userAccount;//还款卡号(约定还款扣款账号)
    private String branchName;//还款银行(开户行名称)
    private String intoTime;//进件时间(借据逾期时间)
    private String loanPayTime;//放款时间
    private String overdueDays;//逾期天数
    private String fiveLevel;//五级分类(正常、关注、次级、可疑、损失)
    private String applyPeriod;//申请期数
    private String creditPeriod;//授信期数
    private String loanPeriod;//放款期数
    private String applyAmount;//申请金额
    private String creditAmt;//授信金额
    private String loanAmount;//放款金额
    private String clearOverdueAmount;//解除逾期状态所需金额
    private String currentMonthDebtAmount;//月结所欠金额
    private String currentPreRepayPrincipal;//当前应扣本金
    private String beforeCurrentLeftRepayInterest;//当期以前未偿还利息合计
    private String leftRepayInterest;//当期剩余应缴利息
    private String beforeCurrentLeftRepayManagementFee;//当期以前未偿还滞纳金
    private String leftRepayManagementFee;//当期剩余应缴滞纳金
    private String currentDebtAmount;//当前欠款总额
    private String loanPurposeName;//行业名称（贷款用途）
    private String leftNum;//剩余期数
    private String contractId;//合同编号
    private String busDate;//取数日期
    private String loanInvoiceId;//借据号
    private String preRepayPrincipal;//当期应扣本金
    private String flag;//垫付标记
    private String leftRepayFee;//当期剩余应缴分期手续费
    private String beforeCurrentLeftRepayFee;//当期以前未偿还分期手续费合计
    private String repayDate;//应还日期
    private String movingBackFlag;//回迁标记
    private String verificationStatus;//核销标记
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private String leftOverdueFee;//当期剩余应还罚金
    private String beforeCurrentLeftOverdueFee;//当期以前未偿还罚金

    //客户信息
    private String clientName;//客户名称(姓名)
    private String certificateType;//证件类型
    private String certificateNo;//证件号码(客户身份证号)
    private String telephone;//手机号码(手机号)
    private String beRecommenderName;//推荐人
    private String merchantName;//经销商名称
    private String storeName;//门店名称
    private String companyName;//公司名称
    private String companyTelAreaCode;//公司电话区号
    private String companyTelephone;//公司电话
    private String companyTelephoneExt;//公司电话分机号
    private String companyAddress;//公司地址

    private String overduePeriod;//逾期期数
    private String statTime;//统计时间
    private Integer overdueCount;

}
