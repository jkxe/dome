package cn.fintecher.pangolin.dataimp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OutSourcePoolModel {

    private String id;
    private String caseId;//案件案件
    private Date outTime;//委外时间
    private Integer outStatus;//委外状态
    private BigDecimal contractAmt = new BigDecimal(0);//案件金额
    private Integer outoperationStatus; //回款 204，回退 205，修复 206
    private String operator;//操作人
    private Date operateTime;//操作时间
    private String overduePeriods;//逾期阶段
    private String companyCode;//公司编号
}
