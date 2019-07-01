package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : xiaqun
 * @Description : 案件流转记录实体
 * @Date : 15:48 2017/7/18
 */

@Entity
@Table(name = "case_turn_record")
@Data
public class CaseTurnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "主键ID")
    private Integer id;

    @ApiModelProperty(notes = "案件ID")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "催收状态")
    private Integer collectionStatus;

    @ApiModelProperty(notes = "实际还款金额")
    private BigDecimal realPayAmount;

    @ApiModelProperty(notes = "合同金额")
    private BigDecimal contractAmount;

    @ApiModelProperty(notes = "提前结清实际还款金额")
    private BigDecimal earlyRealsettleAmt;

    @ApiModelProperty(notes = "部门ID")
    private String departId;

    @ApiModelProperty(notes = "协催方式")
    private Integer assistWay;

    @ApiModelProperty(notes = "协催标识")
    private Integer assistFlag;

    @ApiModelProperty(notes = "持案天数")
    private Integer holdDays;

    @ApiModelProperty(notes = "剩余天数")
    private Integer leftDays;

    @ApiModelProperty(notes = "案件类型")
    private Integer caseType;

    @ApiModelProperty(notes = "当前催收员ID")
    private String currentCollector;

    @ApiModelProperty(notes = "接收人ID")
    private String receiveUserId;

    @ApiModelProperty(notes = "接受人名称")
    private String receiveUserRealName;

    @ApiModelProperty(notes = "案件流转次数")
    private Integer followUpNum;

    @ApiModelProperty(notes = "接受部门名称")
    private String receiveDeptName;

    @ApiModelProperty(notes = "操作员")
    private String operatorUserName;

    @ApiModelProperty(notes = "申请时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "公司code码")
    private String companyCode;

    @ApiModelProperty(notes = "流转类型 0-自动流转 1-手动流转 2-正常流转")
    private Integer circulationType;

    @ApiModelProperty(notes = "流转来源（电催817, 外访818, 委外819, 特殊820, 停催821, 核心系统825,Excel导入826,司法1069）")
    private Integer turnFromPool;

    @ApiModelProperty(notes = "流转去向（内催816,委外819, 特殊820, 停催821, 内诉823,外诉829，反欺诈824）")
    private Integer turnToPool;

    @ApiModelProperty(notes = "流转说明")
    private String turnDescribe;

    @ApiModelProperty(notes = "流转审核状态（待审批-222，通过-220，拒绝-221）")
    private Integer turnApprovalStatus;

    @ApiModelProperty(notes = "审批意见")
    private String approvalOpinion;

    @ApiModelProperty(notes = "审批流程id")
    private String approvalId;

    @ApiModelProperty(notes = "申请人")
    private String applyName;

    @ApiModelProperty(notes = "触发动作（手动分案1070，自动分案1071，手动流转1072，手动回收1073，自动回收1074）")
    private Integer triggerAction;


    /**
     * 触发动作
     */
    public enum triggerActionEnum {
        //手动分案
        AUTO_DIVISION(1070, "手动分案"),
        //自动分案
        MANUAL_DIVISION(1071, "自动分案"),
        //手动流转
        MANUAL(1072, "手动流转"),
        //手动回收
        MANUAL_RECYCLING(1073,"手动回收"),
        //自动回收
        AUTO_RECYCLING(1074,"自动回收");
        private Integer value;

        private String remark;

        triggerActionEnum(Integer value, String remark) {
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
    /**
     * 流转标识
     */
    public enum circulationTypeEnum {
        //自动
        AUTO(0, "自动流转"),
        //手动
        MANUAL(1, "手动流转"),
        //正常
        NOMAL(2, "正常流转"),
        //审批
        APPROVAL(3,"申请流转"),
        //核心和Excel
        CORE_EXCEL(4,"核心系统和Excel导入");

        private Integer value;

        private String remark;

        circulationTypeEnum(Integer value, String remark) {
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

    /**
     * 流转来源
     * 817-电催,818外访,1069司法,819-委外,820-特殊,821-停催,825-核心系统,826-Excel导入;
     */
    public enum TurnFromPool {
        //电催
        INNER(817, "电催"),
        //外访
        OUTBOUND(818, "外访"),
        //司法
        JUDICATURE(1069, "司法"),
        //委外
        OUTER(819, "委外"),
        //特殊
        SPECIAL(820, "特殊"),
        //停催
        STOP(821, "停催"),
        //核心系统
        CORE(825, "API"),
        //Excel导入
        EXCEL(826, "Excel导入");

        private Integer value;

        private String remark;

        TurnFromPool(Integer value, String remark) {
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

    /**
     * 流转去向
     * 854-内催,855-委外,856-特殊,857-停催;
     */
    public enum TurnToPool {
        //内催
        INNER(854, "内催"),
        //委外
        OUTER(855, "委外"),
        //特殊
        SPECIAL(856, "特殊"),
        //停催
        STOP(857, "停催");

        private Integer value;

        private String remark;

        TurnToPool(Integer value, String remark) {
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

    /**
     * 流转审核状态枚举
     */
    public enum TurnApprovalStatus {
        //待审批
        WAIT_APPROVAL(222, "待审批"),
        //通过
        PASS(220, "通过"),
        //拒绝
        REFUSE(221, "拒绝");
        private Integer value;

        private String remark;

        TurnApprovalStatus(Integer value, String remark) {
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