package cn.fintecher.pangolin.report.model.mobile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description: app 首页汇总结果model,所有的结果是通过1个SQL取的，返回的时候按照下面属性顺序返回的，注意对应关系。
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.IndexResult4MobileModel
 * @date 2018年10月11日 17:44
 */
@Data
public class IndexResult4MobileModel {


    @ApiModelProperty("待外访案件数量")
    private String waitForVisitNumber;

    @ApiModelProperty("外访中案件数量")
    private String visitingNumber;

    @ApiModelProperty("外访案件总数")
    private Integer visitingCount;

    @ApiModelProperty("待协催案件数量")
    private String waitForAssistNumber;

    @ApiModelProperty("协催中案件数量")
    private String assistingNumber;

    @ApiModelProperty("协催案件总数")
    private Integer assistCount;

    @ApiModelProperty("所有案件总数")
    private Integer totalCount;

    @ApiModelProperty("待催收金额")
    private String waitForAssistMoneryCount;

    @ApiModelProperty("欠款总额")
    private String accountBalanceCount;






}
