package cn.fintecher.pangolin.business.model.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class LoanRepaymentModel {

    @ApiModelProperty(notes = "贷款申请号")
    private String applNo;

    @ApiModelProperty(notes = "借款金额")
    private BigDecimal loanAmt = new BigDecimal(0);

    @ApiModelProperty(notes = "借款期数")
    private Integer loanNumber;

    @ApiModelProperty(notes = "当期金额")
    private BigDecimal currentAmt = new BigDecimal(0);

    @ApiModelProperty(notes = "是否结清 Y/N")
    private String terminationFlag;

    @ApiModelProperty(notes = "是否逾期 Y/N")
    private String delqFlag;
}
