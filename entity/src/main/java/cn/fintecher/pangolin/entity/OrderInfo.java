package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "order_info")
@Data
public class OrderInfo extends BaseEntity {

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "活动项目编号")
    private String promotionNumber;

    @ApiModelProperty(notes = "申请渠道代码")
    private String channelNumber;

    @ApiModelProperty(notes = "商品总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(notes = "自付金额")
    private BigDecimal selfPayAmount;

    @ApiModelProperty(notes = "申请金额")
    private BigDecimal loanAmount;

    @ApiModelProperty(notes = "放款日期")
    private Date loanDate;

    @ApiModelProperty(notes = "批准贷款金额")
    private BigDecimal approvedLoanAmt;

    @ApiModelProperty(notes = "贷款期限")
    private Integer loanTenure;

    @ApiModelProperty(notes = "贷款状态")
    private String loanStatus;

    @ApiModelProperty(notes = "账单周期")
    private Integer billCycle;

    @ApiModelProperty(notes = "销售门店代码")
    private String storeNumber;

    @ApiModelProperty(notes = "销售代表姓名")
    private String saleName;

    @ApiModelProperty(notes = "销售代表手机号")
    private String saleNamePhone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_comm", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "comm_id"))
    @ApiModelProperty(notes = "用户所拥有的角色")
    private Set<Commodity> commodities;

    /**
     * 贷款状态
     */
    public enum LoanStatus{
        normal("992","正常"),
        overdue("993","逾期"),
        sluggish("994","呆滞"),
        Baddeb("995","呆账"),
        verification("996","核销");
        private String value;
        private String remark;
        LoanStatus(String value, String remark) {
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
