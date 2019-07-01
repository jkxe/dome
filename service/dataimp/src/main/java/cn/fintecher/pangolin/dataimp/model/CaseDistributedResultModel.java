package cn.fintecher.pangolin.dataimp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  存放案件唯一标识与案件类型
 * @Package cn.fintecher.pangolin.dataimp.model
 * @ClassName: cn.fintecher.pangolin.dataimp.model.CaseDistributedResultModel
 * @date 2018年06月20日 14:22
 */
@Data
public class CaseDistributedResultModel implements Comparable<CaseDistributedResultModel>{

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    // 枚举参考 ObtainTaticsStrategy.CaseType
    @ApiModelProperty(notes = "案件类型(540:内催案件,541:委外案件,542:特殊案件,543:停催案件,544:诉讼案件,545:反欺诈案件)")
    private Integer caseType;

    /**
     * 在进行案件分配的时候，当前催员为空，因此催收状态设置为待催收；
     * 当进行案件流转的时候，当前催员不为空，这时候将原来的催收状态(催收中)就要改为待催收。
     * 该属性是分配到人后，存放催收员的ID。
     */
    @ApiModelProperty(notes = "当前催员")
    private String currentCollector;

    // 枚举参考  CaseInfo.CollectionStatus
    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;

    @ApiModelProperty(notes = "案件池类型")
    private Integer casePoolType;

    @ApiModelProperty(notes = "案件回收标识（0-未回收，1-已回收）")
    private Integer recoverRemark;

    // 枚举参考  CaseInfo.CollectionType
    @ApiModelProperty(notes = "催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;

    @ApiModelProperty("组别") // department 表的ID
    private String departId;

    @ApiModelProperty("产品类型(耐消，抵押，保证，信用)")
    private String productType;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty(notes = "剩余本金") // 贷款余额
    private BigDecimal leftCapital = new BigDecimal(0);

    @ApiModelProperty("逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty("逾期天数")
    private Integer overdueDays;





    @Override
    public int compareTo(CaseDistributedResultModel o) {
        return this.leftCapital.compareTo(o.leftCapital);
    }
}
