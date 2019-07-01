package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_repayment_plan")
@Data
public class OrderRepaymentPlan extends  BaseEntity{

    @ApiModelProperty(notes = "订单id")
    private String orderId;

    @ApiModelProperty(notes = "首次还款日")
    private Date firstDueDate;

    @ApiModelProperty(notes = "每月还款额")
    private BigDecimal monthAmt;

    @ApiModelProperty(notes = "还款计划类型")
    private Integer amorzType;

    @ApiModelProperty(notes = "年利率")
    private BigDecimal yearRate;

    @ApiModelProperty(notes = "还款方式")
    private String repayMethod;

    @ApiModelProperty(notes = "综合利率")
    private BigDecimal comsiveRate;

    @ApiModelProperty(notes = "罚息费率")
    private BigDecimal penaltyRate;

    @ApiModelProperty(notes = "合同违约金费率")
    private BigDecimal contractPenaltyRate;

    @ApiModelProperty(notes = "提前还款违约金费率")
    private BigDecimal advancePaymentRate;

    @ApiModelProperty(notes = "分期服务费率")
    private BigDecimal stagingFeeRate;

    @ApiModelProperty(notes = "是否还后续贷款")
    private String repaySubloanWether;

    @ApiModelProperty(notes = "黑名单标志")
    private String blackFlag;

    @ApiModelProperty(notes = "最高额抵押标识")
    private String maxMortgageMark;

    @ApiModelProperty(notes = "主贷款标识")
    private String mainLoanLogo;

    @ApiModelProperty(notes = "主贷款申请号")
    private String mainApplyNumber;

    @ApiModelProperty(notes = "销售代表备注")
    private String salesRemark;

    @ApiModelProperty(notes = "人行征信评分")
    private BigDecimal pbocScore;

    @ApiModelProperty(notes = "决定代码")
    private String decisionNo;

    @ApiModelProperty(notes = "决定原因")
    private String decisionReason;

    @Transient
    private String caseNumber;

    /**
     * 还款计划类型 、还款方式、还款类型
     */
    public enum AmorzType{

        Amount_interest_Equal(976,"等额本息"),
        Interest_Equal (977,"等额利息"),
        Tailing_repayment(978,"尾款还款"),
        Grace_period1(979,"宽限期-1"),
        Grace_period2(980,"宽限期-2"),
        Grace_period3(981,"宽限期-3"),
        Grace_period4(982,"宽限期-4"),
        Grace_period5(983,"宽限期-5"),
        Grace_period6(984,"宽限期-6"),
        Grace_period7(985,"宽限期-7"),
        Grace_period8(986,"宽限期-8"),
        Grace_period9(987,"宽限期-9"),
        Grace_period10(988,"宽限期-10"),
        Grace_period11(989,"宽限期-11"),
        Grace_period12(990,"宽限期-12"),
        First_interest(991,"首期免息还款");
        private Integer value;
        private String remark;
        AmorzType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }
        public Integer getValue() {
            return value;
        }
        public String getRemark() {
            return remark;
        }
    }

    /**
     * 最高额抵押和主贷款标识
     */
    public enum Flag{
        YES("997","是"),
        NO("998","否");
        private String value;
        private String remark;
        Flag(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }
        public String getValue() {
            return value;
        }
        public String getRemark() {
            return remark;
        }
    }
}
