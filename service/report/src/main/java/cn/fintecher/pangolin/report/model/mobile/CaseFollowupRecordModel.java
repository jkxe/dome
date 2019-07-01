package cn.fintecher.pangolin.report.model.mobile;

import cn.fintecher.pangolin.entity.BaseEntity;
import cn.fintecher.pangolin.report.entity.CaseFollowupAttachment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @Description: APP端查询案件协催记录 返回model
 * @Package cn.fintecher.pangolin.report.model.mobile
 * @ClassName: cn.fintecher.pangolin.report.model.mobile.CaseFollowupRecordModel
 * @date 2018年10月09日 11:36
 */
@Data
public class CaseFollowupRecordModel extends BaseEntity {

    @ApiModelProperty("当前催员ID")
    private String currentCollector;

    @ApiModelProperty("当前催员名称")
    private String currentCollectorName;

    @ApiModelProperty("部门Code")
    private String deptCode;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("客户ID")
    private String personalId;

    @ApiModelProperty("客户名称")
    private String personalName;

    @ApiModelProperty("案件信息ID")
    private String caseId;

    @ApiModelProperty("案件编号")
    private String caseNumber;

    @ApiModelProperty("跟进对象关系")
    private Integer targetRelation;

    @ApiModelProperty("跟进对象关系名称")
    private String targetRelationName;

    @ApiModelProperty("跟进对象姓名")
    private String targetName;

    @ApiModelProperty("详细地址")
    private String detail;

    @ApiModelProperty("跟进时间")
    private Date followTime;

    @ApiModelProperty(notes = "操作人")
    private String operator;

    @ApiModelProperty(notes = "操作人")
    private String operatorName;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty("催收反馈")
    private Integer collectionFeedback;

    @ApiModelProperty("催收反馈名称")
    private String collectionFeedbackName;

    @ApiModelProperty("反馈类型")
    private Integer resultType;

    @ApiModelProperty("反馈类型 有效联络 无效联络")
    private String resultTypeName;

    @ApiModelProperty("页数")
    private Integer page;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("电话状态")
    private Integer contactState;

    @ApiModelProperty("电话状态名称")
    private String contactStateName;

    @ApiModelProperty("跟进内容")
    private String content;

    @ApiModelProperty("下次跟进日期")
    private Date follnextDate;

    /**
     * 2018/10/23 0023 下午 6:35 zmm 添加。
     * 原型修改了，添加这几个属性
     */
    @ApiModelProperty("地址状态")
    private Integer addrStatus;

    @ApiModelProperty("地址类型")
    private Integer addrType;

    @ApiModelProperty("地址状态名称")
    private String addrStatusName;

    @ApiModelProperty("地址类型名称")
    private String addrTypeName;

    @ApiModelProperty("音频附件文件")
    List<CaseFollowupAttachment> audioAttachment;

    @ApiModelProperty("图片附件文件")
    List<CaseFollowupAttachment> photoAttachment;

    @ApiModelProperty(notes = "跟进方式")
    private Integer type;

    @ApiModelProperty(notes = "跟进方式名称")
    private String typeName;

    @ApiModelProperty(notes = "hy-同行人员")
    private String fellowWorkers;

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



}
