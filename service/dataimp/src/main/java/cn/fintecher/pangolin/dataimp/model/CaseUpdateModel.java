package cn.fintecher.pangolin.dataimp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frank  braveheart1115@163.com
 * @Description:
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName: cn.fintecher.pangolin.dataimp.model.CaseUpdateModel
 * @date 2018年07月14日 09:19
 */
@Data
public class CaseUpdateModel {


    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "案件类型(0案件分配1电催小流转2电催强制流转3电催提前流转4电催保留流转外访小流转")
    private Integer caseType;

    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;

    @ApiModelProperty(notes = "部门id")
    private String departId;

    @ApiModelProperty("当前催员")
    private String currentCollector;

    @ApiModelProperty("案件类型：内催225、委外226、特殊801、停催802、贷后预警803")
    private Integer casePoolType;

    @ApiModelProperty("回收标志：0-未回收，1-已回收")
    private Integer recoverRemark;

}
