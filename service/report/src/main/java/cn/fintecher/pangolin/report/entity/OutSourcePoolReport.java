package cn.fintecher.pangolin.report.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

//@Entity
@Table(name = "outsource_pool")
@Data
public class OutSourcePoolReport extends BaseEntity{

    @ApiModelProperty(notes = "案件ID")
    private String caseId;

    @ApiModelProperty(notes = "委外方ID")
    private String outId;

    @ApiModelProperty(notes = "委外时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    @ApiModelProperty(notes = "操作时间")
    private Date operateTime;

    @ApiModelProperty(notes = "操作人")
    private String operator;

    @ApiModelProperty(notes = "委外状态")
    private Integer outStatus;

    @ApiModelProperty(notes = "委外批次号")
    private String outBatch;

    @ApiModelProperty(notes = "委外回款金额")
    private BigDecimal outBackAmt = BigDecimal.ZERO;

    @ApiModelProperty(notes = "逾期时段")
    private String overduePeriods;

    @ApiModelProperty(notes = "案件金额")
    private BigDecimal contractAmt = BigDecimal.ZERO;

    @ApiModelProperty(notes = "委外操作状态")
    private Integer outoperationStatus; //回款 204，回退 205，修复 206

    @ApiModelProperty(notes = "公司标识符")
    private String companyCode;

    @ApiModelProperty(notes = "委外到期时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date overOutsourceTime;

    @ApiModelProperty(notes = "委外结案日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endOutsourceTime;

    @ApiModelProperty(notes = "佣金比例")
    private BigDecimal commissionRate = BigDecimal.ZERO;

    @ApiModelProperty(notes = "佣金金额")
    private BigDecimal commission  = BigDecimal.ZERO;
}
