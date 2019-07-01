package cn.fintecher.pangolin.report.model.distribution;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DistributionCaseParam {

    private Integer areaId;//省份id
    private Integer cityId;//城市id
    private BigDecimal overdueMaxAmt; //最大案件金额
    private BigDecimal overdueMinAmt; //最小案件金额
    private Integer overMaxDay; //最大逾期天数
    private Integer overMinDay; //最小逾期天数
    private String payStatus;//逾期状态
    private String caseNumber;//案件编号
    private String personalName;//客户姓名
    private String mobileNo;//客户手机号
    private String seriesId;//产品类型id
    private String outId;//受托方id
    private Integer page; //页数
    private Integer size; //每页条数
}
