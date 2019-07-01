package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 逾期详情
 */
@Entity
@Table(name = "hy_overdue_details")
@Data
public class OverdueDetail implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    @ApiModelProperty(notes = "主键ID")
    private Integer id;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "进件申请编号")
    private String intoApplyId;//进件申请编号(案件编号)

    @ApiModelProperty(notes = "客户信息")
    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id")
    private Personal personalInfo;

    @ApiModelProperty(notes = "来源渠道")
    private String sourceChannel;//来源渠道

    @ApiModelProperty(notes = "催收方式")
    private String collectionMethod;//催收方式

    @ApiModelProperty(notes = "产品名称")
    private String productName;//产品名称

    @ApiModelProperty(notes = "还款卡号")
    private String userAccount;//还款卡号(约定还款扣款账号)

    @ApiModelProperty(notes = "还款银行")
    private String branchName;//还款银行(开户行名称)

    @ApiModelProperty(notes = "进件时间")
    private String intoTime;//进件时间(借据逾期时间)

    @ApiModelProperty(notes = "放款时间")
    private String loanPayTime;//放款时间

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;//逾期天数

    @ApiModelProperty(notes = "五级分类")
    private String fiveLevel;//五级分类(正常、关注、次级、可疑、损失)

    @ApiModelProperty(notes = "申请期数")
    private String applyPeriod;//申请期数

    @ApiModelProperty(notes = "授信期数")
    private String creditPeriod;//授信期数

    @ApiModelProperty(notes = "放款期数")
    private String repayNum;//放款期数

    @ApiModelProperty(notes = "申请金额")
    private String applyAmount;//申请金额

    @ApiModelProperty(notes = "授信金额")
    private String creditAmt;//授信金额

    @ApiModelProperty(notes = "放款金额")
    private String loanAmount;//放款金额

    @ApiModelProperty(notes = "解除逾期状态所需金额")
    private String clearOverdueAmount;//解除逾期状态所需金额

    @ApiModelProperty(notes = "月结所欠金额")
    private String currentMonthDebtAmount;//月结所欠金额

    @ApiModelProperty(notes = "当前应扣本金")
    private String currentPreRepayPrincipal;//当前应扣本金

    @ApiModelProperty(notes = "当期以前未偿还利息合计")
    private String beforeCurrentLeftRepayInterest;//当期以前未偿还利息合计

    @ApiModelProperty(notes = "当期剩余应缴利息")
    private String leftRepayInterest;//当期剩余应缴利息

    @ApiModelProperty(notes = "当期以前未偿还滞纳金")
    private String beforeCurrentLeftRepayManagementFee;//当期以前未偿还滞纳金

    @ApiModelProperty(notes = "当期剩余应缴滞纳金")
    private String leftRepayManagementFee;//当期剩余应缴滞纳金

    @ApiModelProperty(notes = "当前欠款总额")
    private String currentDebtAmount;//当前欠款总额

    @ApiModelProperty(notes = "行业名称(贷款用途)")
    private String loanPurposeName;//行业名称（贷款用途）

    @ApiModelProperty(notes = "未偿还期限")
    private String leftNum;//剩余期数

    @ApiModelProperty(notes = "合同编号")
    private String contractId;//合同编号

    @ApiModelProperty(notes = "取数日期")
    private String busDate;//取数日期

    @ApiModelProperty(notes = "借据号")
    private String loanInvoiceId;//借据号

    @ApiModelProperty(notes = "当期应扣本金")
    private String preRepayPrincipal;//当期应扣本金

    @ApiModelProperty(notes = "代偿标记")
    private String flag;//垫付标记

    @ApiModelProperty(notes = "当期剩余应缴分期手续费")
    private String leftRepayFee;//当期剩余应缴分期手续费

    @ApiModelProperty(notes = "当期以前未偿还分期手续费合计")
    private String beforeCurrentLeftRepayFee;//当期以前未偿还分期手续费合计

    @ApiModelProperty(notes = "应还日期")
    private String repayDate;//应还日期

    @ApiModelProperty(notes = "回迁标记")
    private String movingBackFlag;//回迁标记

    @ApiModelProperty(notes = "核销标记")
    private String verificationStatus;//核销标记

    @ApiModelProperty(notes = "创建时间")
    private String createTime;//创建时间

    @ApiModelProperty(notes = "更新时间")
    private String updateTime;//更新时间

    @ApiModelProperty(notes = "当期剩余应缴罚息")
    private String leftOverdueFee;//当期剩余应缴罚息

    @ApiModelProperty(notes = "当期以前未偿还罚息")
    private String beforeCurrentLeftOverdueFee;//当期以前未偿还罚息

    @ApiModelProperty(notes = "客户名称")
    private String clientName;//客户名称(姓名)

    @ApiModelProperty(notes = "证件类型")
    private String certificateType;//证件类型

    @ApiModelProperty(notes = "证件号码")
    private String certificateNo;//证件号码(客户身份证号)

    @ApiModelProperty(notes = "经销商名称")
    private String merchantName;//经销商名称

    @ApiModelProperty(notes = "门店名称")
    private String storeName;//门店名称

    @ApiModelProperty(notes = "贷款余额")
    private String loanAmt;//贷款余额(账户余额)

    @ApiModelProperty(notes = "逾期期数")
    private String overduePeriods;//逾期期数

    @ApiModelProperty(notes = "统计时间")
    private Date statTime;
}
