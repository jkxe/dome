package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "write_off_details")
@Data
public class WriteOffDetails extends  BaseEntity{

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "入账日期")
    private Date inaccountDate;

    @ApiModelProperty(notes = "客户号")
    private String personalNo;

    @ApiModelProperty(notes = "录入未尝还本金")
    private BigDecimal unpaidPrincipal;

    @ApiModelProperty(notes = "录入剩余本金")
    private BigDecimal remainPrincipal;

    @ApiModelProperty(notes = "录入未尝还利息")
    private BigDecimal unpaidInterest;

    @ApiModelProperty(notes = "录入未出账单利息")
    private BigDecimal verifiNobillInterest;

    @ApiModelProperty(notes = "录入其他累计利息")
    private BigDecimal otherInterest;

    @ApiModelProperty(notes = "录入罚息")
    private BigDecimal pnltInterest;

    @ApiModelProperty(notes = "录入滞纳金")
    private BigDecimal inFine;

    @ApiModelProperty(notes = "录入月服务费")
    private BigDecimal monthFee;

    @ApiModelProperty(notes = "录入其他管理费")
    private BigDecimal otherFee;

    @ApiModelProperty(notes = "核销结清标识")
    private String terminationInd;

    @ApiModelProperty(notes = "还款总额")
    private BigDecimal hasTotal;

    @ApiModelProperty(notes = "结清日期")
    private Date settleDate;

    @ApiModelProperty(notes = "核销请求日期")
    private Date requestDate;
}
