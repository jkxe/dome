package cn.fintecher.pangolin.report.model.mobile;

import cn.fintecher.pangolin.report.entity.CaseFollowupAttachment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description:  APP添加跟进记录参数model
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.CaseFollowupRecordParam
 * @date 2018年10月09日 17:59
 */
@Data
public class CaseFollowupRecordParam {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("当前催员ID")
    private String currentCollector;

    @ApiModelProperty("客户ID")
    private String personalId;

    @ApiModelProperty("案件信息ID")
    private String caseId;

    @ApiModelProperty("案件编号")
    private String caseNumber;


    @ApiModelProperty("跟进对象关系")
    private Integer target;

    @ApiModelProperty("跟进对象姓名")
    private String targetName;

    @ApiModelProperty("催收反馈")
    private Integer collectionFeedback;

    @ApiModelProperty("催收类型(电催、外访、司法、委外、提醒)")
    private Integer collectionType;

    /**
     * @Description 催收类型枚举类
     */
    public enum CollectionType {
        //电催
        TEL(15, "电催"),
        //外访
        VISIT(16, "外访"),
        //司法
        JUDICIAL(17, "司法"),
        //委外
        outside(18, "委外"),
        //提醒
        remind(19, "提醒"),
        //综合
        COMPLEX(217, "综合"),
        //特殊
        SPECIAL(858, "特殊"),
        //停催
        STOP(859, "停催");

        private Integer value;
        private String remark;

        CollectionType(Integer value, String remark) {
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


    @ApiModelProperty("反馈类型: 有效联络88，无效联络99")
    private Integer resultType;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("电话状态")
    private Integer contactState;

    @ApiModelProperty("跟进内容")
    private String content;

    @ApiModelProperty("下次跟进日期")
    private Date follnextDate;

    @ApiModelProperty("操作员")
    private String operator;

    @ApiModelProperty("操作员")
    private String operatorName;

    @ApiModelProperty("操作时间")
    private Date operatorTime;

    @ApiModelProperty("编辑人")
    private String editor;

    @ApiModelProperty("编辑时间")
    private Date editorTime;

    @ApiModelProperty("音频附件文件")
    List<CaseFollowupAttachment> audioAttachment;

    @ApiModelProperty("图片附件文件")
    List<CaseFollowupAttachment> photoAttachment;

    /**
     * 2018/10/23 0023 下午 6:35 zmm 添加。
     * 原型修改了，添加这几个属性
     */

    @ApiModelProperty("地址状态")
    private Integer addrStatus;

    @ApiModelProperty("地址类型")
    private Integer addrType;

    @ApiModelProperty("详细地址")
    private String detail;

    @ApiModelProperty(notes = "跟进方式")
    private Integer type;

    @ApiModelProperty("跟进人员")
    private String followPerson;

    @ApiModelProperty(notes = "hy-同行人员")
    private String fellowWorkers;

    @ApiModelProperty("跟进时间")
    private Date followTime;



    /**
     * @Description 跟进方式枚举类
     */
    public enum Type {
        //电催
        TEL(78, "电催"),
        //外访
        VISIT(79, "外访"),
        //协催
        ASSIST(162, "协催"),
        //内催
        INNER(858, "内催"),
        //委外
        OUTER(859, "委外"),
        //特殊
        SPECIAL(860, "特殊"),
        //停催
        STOP(861, "停催");

        private Integer value;

        private String remark;

        Type(Integer value, String remark) {
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


    @ApiModelProperty("协催案件状态")
    private Integer assistStatus;

    /**
     * 案件协催状态
     */
    public enum AssistStatus {

        ASSIST_APPROVEING(26, "协催审批中"),
        ASSIST_REFUSED(27, "协催拒绝"),
        ASSIST_COLLECTING(28, "协催催收中"),
        ASSIST_COMPLATED(29, "协催完成"),
        ASSIST_WAIT_ASSIGN(117, "协催待分配"),
        ASSIST_WAIT_ACC(118, "协催待催收"),
        FAILURE(212, "协催审批失效");

        private Integer value;
        private String remark;

        AssistStatus(Integer value, String remark) {
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
