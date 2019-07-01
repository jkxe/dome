package cn.fintecher.pangolin.dataimp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 实体。
 * @Package cn.fintecher.pangolin.dataimp.entity
 * @ClassName: cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy
 * @date 2018年06月23日 13:10
 */
@Data
@Document
@ApiModel(value = "ObtainTaticsStrategy", description = "案件数据获取分案策略")
public class ObtainTaticsStrategy implements Serializable {

    @Id
    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("策略名称")
    private String name;

    @ApiModelProperty("hy-策略状态[状态 504:开启,505:关闭]")
    private Integer status;

    @ApiModelProperty("hy-执行频率[分案频率 (每天,月初)]")
    private Integer divisionFrequency;

    @ApiModelProperty("hy-分案策略[催收员案件分配模式 (案件数量均分，案件金额均分，综合均分，人员配比分配....)]")
    private Integer userPattern;

    @ApiModelProperty("hy-催收队列")
    private List<String> collectionQueues;

    @ApiModelProperty(notes = "hy-分配方式 按催收员分配,按机构分配")
    private Integer allotType;

    /**
     * 案件分配到组的id,保存的是这个机构的上级节点ID与自己的ID.需要的ID为最后一个。
     */
    @ApiModelProperty("hy-分配的机构id[组别]") // department 表的ID 后台需要。
    private String departId;

    @ApiModelProperty("hy-分配的机构code(存储分配的机构路径)[组别]") // department 表的code,前端需要
    private List<String> departCode;

    /**
     * 以下属性是针对分案需要的参数，如果直接分配到组，该值为空；
     * 分到人，是人员的集合，根据 userPattern 再将人员与案件进行关联。
     */
    @ApiModelProperty("hy-分配到的催收员催收员[策略指定的催收员]")
    private List<String> users;

    @ApiModelProperty("hy-创建人")
    private String creator;

    @ApiModelProperty("hy-创建人ID")
    private String creatorId;

    @ApiModelProperty("hy-创建日期")
    private Date createTime;

    @ApiModelProperty("hy-更新人")
    private String updateBy;

    @ApiModelProperty("hy-更新人ID")
    private String updateId;

    @ApiModelProperty("hy-更新日期")
    private Date updateTime;

    @ApiModelProperty("案件类型：540:电催案件,541:委外案件,546:外访案件,542:特殊案件,543:停催案件,544:诉讼案件,545:反欺诈案件,546:外访案件")
    private Integer caseType;

    @ApiModelProperty("案件池类型：内催225、委外226、特殊801、停催802、贷后预警803")
    private Integer casePoolType;

    @ApiModelProperty("产品类型")
    private String productType;

    @ApiModelProperty("公司Code")
    private String companyCode;

    /**
     * 分案模式可用的只有一种，即有一个模式选择的是530，那其他的也只能是530，除非530的置为不可用。
     * 在每次添加策略之前需要检查先前可用策略的模式类型
     */
    @ApiModelProperty("分案模式 530:月初对月底,531:账单对账单")
    private Integer allocationPattern;

    @ApiModelProperty("策略JSON对象")  // 对应的对象为 ObtainStrategyJsonModel
    private String strategyJson;

    @ApiModelProperty("策略公式")
    private String strategyText;

    // 该属性是分配到人后，存放催收员的ID。
    @ApiModelProperty(notes = "当前催员")
    private String currentCollector;


    /**
     * 枚举参考  CaseInfo.CollectionType
     * 根据caseType 获取。
     */
    @ApiModelProperty(notes = "催收类型")
    private Integer collectionType;

    // 枚举参考  CaseInfo.CollectionStatus
    @ApiModelProperty(notes = "催收类型枚举类")
    private Integer collectionStatus;


    public enum UserPattern {
        MONEY(600, "按金额"),
        HOUSEHOLDS(601, "按户数"),
        SYNTHESIZE(602, "综合均分");
//        RATIO(603, "按催收员配比"),
//        MONEY_PIT(604, "按金额填坑"),
//        HOUSEHOLDS_PIT(605, "按户数填坑");
        private Integer value;
        private String remark;

        UserPattern(Integer value, String remark) {
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


    public enum AllocationPattern {
        MONTH_TO_MONTH(530, "月初对月底"),
        STATEMENT_OF_ACCOUNT(531, "账单对账单");

        private Integer value;
        private String remark;

        AllocationPattern(Integer value, String remark) {
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
    public enum AllotType {
        PERSON(1101, "按催收员分配"),
        DEPARTMENT(1102, "按机构分配");

        private Integer value;
        private String remark;

        AllotType(Integer value, String remark) {
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



    public enum Status {
        OPEN(504, "开启"),
        CLOSED(505, "关闭");

        private Integer value;
        private String remark;

        Status(Integer value, String remark) {
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


//    540:电催案件,541:委外案件,546:外访案件,542:特殊案件,543:停催案件,544:诉讼案件,545:反欺诈案件
    public enum CaseType {
//        TEL_CASE(540, "电催案件"),
//        OUTSIDE_CASE(541, "委外案件"),
//        VISIT_CASE(546, "外访案件"),
//        SPECIAL_CASE(542, "特殊案件"),
//        STOP_CASE(543, "停催案件"),
//        JUDICIAL_CASE(544, "诉讼案件"),
//        ANTI_FRAUD_CASE(545, "反欺诈案件");
        INNER_CASE(540, "内催案件"),
        OUTSIDE_CASE(541, "委外案件");

        private Integer value;
        private String remark;

        CaseType(Integer value, String remark) {
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


    public enum DivisionFrequency {
        EVERYDAY(610, "每天"),
        EARLY(611, "月初");

        private Integer value;
        private String remark;

        DivisionFrequency(Integer value, String remark) {
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
