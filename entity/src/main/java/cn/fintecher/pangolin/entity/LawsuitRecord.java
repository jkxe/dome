package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "Lawsuit_record")
public class LawsuitRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "主键ID")
    private Integer id;

    @ApiModelProperty(notes = "案件编号（借据号）")
    private String caseNumber;

    @ApiModelProperty(notes = "诉讼阶段（初始806、立案807、开庭808、公告809、审批827、执行828）")
    private Integer lawsuitResult;

    @ApiModelProperty(notes = "诉讼类型（内部诉讼814、外部诉讼815）")
    private Integer lawsuitType;

    @ApiModelProperty(notes = "流转来源（内催817，委外819, 特殊820, 停催821,核心系统825,Excel导入826）")
    private Integer turnFromPool;

    @ApiModelProperty(notes = "案件金额")
    private BigDecimal overdueAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期期数")
    private Integer overduePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overdueDays;

    @ApiModelProperty(notes = "迟案天数")
    private Integer holdDays;

    @ApiModelProperty(notes = "剩余天数")
    private Integer leftDays;

    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;

    @ApiModelProperty(notes = "催收员")
    private String collector;

    @ApiModelProperty(notes = "回收标识（0-未回收，1-回收）")
    private Integer recoverRemark;

    @ApiModelProperty(notes = "委托方ID(律师)")
    private String principalID;

    @ApiModelProperty(notes = "操作人")
    private String approver;

    @ApiModelProperty(notes = "创建时间")
    private Date creationTime;

    /**
     * 诉讼阶段枚举
     * 诉讼阶段（初始806、立案807、开庭808、公告809、审批827、执行828）
     */
    public enum LawsuitResult {
        //初始
        START(806, "初始"),
        //立案
        Register (807, "立案"),
        //开庭
        COURT(808,"开庭"),
        //公告
        NOTICE(809,"公告"),
        //审批
        APPROVAL(827,"审批"),
        //执行
        EXECUTE(828,"执行");
        private Integer value;

        private String remark;

        LawsuitResult(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }

    }

    public enum LawsuitType {
        //公告
        IN_LAWSUIT(814, "内部诉讼"),
        //立案
        OUT_LAWSUIT (815, "外部诉讼");
        private Integer value;

        private String remark;

        LawsuitType(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }

    }


}

