package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chenjing 2018-7-12
 */
@Entity
@Table(name = "case_repayment_record")
@Data
public class CaseRepaymentRecord extends BaseEntity {

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

    @ApiModelProperty(notes = "调取记录人")
    private String transferUser;

    @ApiModelProperty(notes = "调取时间")
    private Date transferTime;

}


