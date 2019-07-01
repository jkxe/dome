package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author : xiaqun
 * @Description : 跟进记录实体
 * @Date : 15:49 2017/7/19
 */

@Entity
@Table(name = "case_followup_record")
@Data
public class CaseFollowupRecord extends BaseEntity {
    @ApiModelProperty(notes = "客户信息ID")
    private String personalId;

    @ApiModelProperty(notes = "案件信息ID")
    private String caseId;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "跟进对象")
    private Integer target;

    @ApiModelProperty(notes = "跟进对象姓名")
    private String targetName;

    @ApiModelProperty(notes = "跟进方式")
    private Integer type;

    @ApiModelProperty(notes = "跟进内容")
    private String content;

    @ApiModelProperty(notes = "跟进时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date followTime;

    @ApiModelProperty(notes = "跟进人员")
    private String followPerson;

    @ApiModelProperty(notes = "电话联系状态")
    private Integer contactState;

    @ApiModelProperty(notes = "联系电话")
    private String contactPhone;

    @ApiModelProperty(notes = "催收类型")
    private Integer collectionType;

    @ApiModelProperty(notes = "催收反馈")
    private Integer collectionFeedback;

    @ApiModelProperty(notes = "催记来源：电催 ，外访 ，协催 ，委外，特殊，停催")
    private Integer source;

    @ApiModelProperty(notes = "操作人")
    private String operator;

    @ApiModelProperty(notes = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(notes = "操作人部门")
    private String operatorDeptName;

    @ApiModelProperty(notes = "操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;

    @ApiModelProperty(notes = "承诺还款标识 0-没有承诺 1-有承诺")
    private Integer promiseFlag;

    @ApiModelProperty(notes = "承诺还款金额")
    private BigDecimal promiseAmt;

    @ApiModelProperty(notes = "承诺还款日期")
    private Date promiseDate;

    @ApiModelProperty(notes = "下次跟进提醒标志 0-没有下次跟进 1-有下次跟进")
    private Integer follnextFlag;

    @ApiModelProperty(notes = "下次跟进提醒日期")
    private Date follnextDate;

    @ApiModelProperty(notes = "下次跟进提醒内容")
    private String follnextContent;

    @ApiModelProperty(notes = "地址状态")
    private Integer addrStatus;

    @ApiModelProperty(notes = "地址类型")
    private Integer addrType;

    @ApiModelProperty(notes = "通话ID")
    private String taskId;

    @ApiModelProperty(notes = "通话记录ID")
    private String recoderId;

    @ApiModelProperty(notes = "主叫id")
    private String taskcallerId;

    @ApiModelProperty(notes = "录音地址")
    private String opUrl;

    @ApiModelProperty(notes = "录音下载标识")
    private Integer loadFlag;

    @ApiModelProperty(notes = "催记方式 0-自动 1-手动")
    private Integer collectionWay;

    @ApiModelProperty(notes = "详细地址")
    private String detail;

    @ApiModelProperty(notes = "定位地址")
    private String collectionLocation;

    @ApiModelProperty(notes = "外访资料id集合")
    @Transient
    private List fileIds;

    @ApiModelProperty(notes = "公司code码")
    private String companyCode;

    @ApiModelProperty(notes = "呼叫类型(erpv3 163 中通天鸿 164")
    private Integer callType;

    @ApiModelProperty(notes = "呼叫开始时间")
    private Date startTime;

    @ApiModelProperty(notes = "呼叫结束时间")
    private Date endTime;

    @ApiModelProperty(notes = "通话时长默认为秒")
    private Integer connSecs;

    @ApiModelProperty("内催 225 委外 226 司法 227 核销 228")
    private Integer caseFollowupType;

    @ApiModelProperty(notes = "录音文件名称")
    private String fileName;

    @ApiModelProperty(notes = "录音文件目录")
    private String filePath;

    @ApiModelProperty(notes = "反馈类型 有效联络 无效联络")
    private Integer resultType;

    @ApiModelProperty(notes = "委外催收反馈")
    private String outFollowupBack;

    @ApiModelProperty(notes = "受托方")
    private String principalName;

    @ApiModelProperty(notes = "hy-通话方式")
    private String seatType;

    @ApiModelProperty(notes = "hy-通话结果")
    private String result;

    @ApiModelProperty(notes = "hy-通话类型")
    private String conversationType;

    @ApiModelProperty(notes = "hy-客服姓名")
    private String agentName;

    @ApiModelProperty(notes = "hy-响铃时间")
    private String ringingDuration;

    @ApiModelProperty(notes = "hy-拨号时间")
    private Date dialTime;

    @ApiModelProperty(notes = "hy-挂断时间")
    private Date hangUpTime;

    @ApiModelProperty(notes = "hy-同行人员")
    private String fellowWorkers;

    /**
     * @Description 电话状态枚举类
     */
    public enum ContactState {
        //正常
        NORMAL(64, "正常"),
        //空号
        UNN(65, "空号"),
        //停机
        HALT(66, "停机"),
        //关机
        POWEROFF(67, "关机"),
        //未知
        UNKNOWN(68, "未知");

        private Integer value;

        private String remark;

        ContactState(Integer value, String remark) {
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
     * @Description 跟进对象枚举类
     */
    public enum Target {
        //本人
        SELF(69, "本人"),
        //配偶
        SPOUSE(70, "配偶"),
        //父母
        PARENTS(71, "父母"),
        //子女
        CHILD(72, "子女"),
        //亲属
        RELATIVES(73, "亲属"),
        //同事
        COLLEAGUE(74, "同事"),
        //朋友
        FRIEND(75, "朋友"),
        //其他
        OTHER(76, "其他"),
        //单位
        UNIT(77, "单位"),
        //同学
        STUDENT(219, "同学");

        private Integer value;

        private String remark;

        Target(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }

        public static Target getEnumByCode(String code){
            for (Target eInvalidCollectionTel : Target.values()) {
                if(eInvalidCollectionTel.value.toString().equals(code)){
                    return eInvalidCollectionTel;
                }
            }
            return null;
        }
        public static Target getEnumByRemark(String remark){
            for (Target eInvalidCollectionTel : Target.values()) {
                if(eInvalidCollectionTel.remark.equals(remark)){
                    return eInvalidCollectionTel;
                }
            }
            return null;
        }
    }

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

    /**
     * @Description 地址类型枚举类
     */
    public enum AddrType {
        //现居住地址
        LIVING_ADDRESS(83, "现居住地址"),
        //单位地址
        COMPANY_ADDRESS(84, "单位地址"),
        //身份证地址
        IDCARD_ADDRESS(85, "身份证地址"),
        //房产地址
        ESTATE_ADDRESS(86, "房产地址"),
        //其他
        OTHER_ADDRESS(87, "其他");

        private Integer value;

        private String remark;

        AddrType(Integer value, String remark) {
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
     * @Description 系统备注
     */
    public enum SystemNote {

        //转诉讼
        FAWU(850,"转诉讼"),
        //转欺诈
        FRAU(851,"转欺诈"),
        //就是注记的意思，一般写长MEMO时所用的
        MEMO(852,"就是注记的意思，一般写长MEMO时所用的"),
        //记录征信查询结果
        ZXJG(853,"记录征信查询结果");

        private Integer value;

        private String remark;

        SystemNote(Integer value, String remark) {
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
     * @Description 打电话类型
     */
    public enum CallType {
        //erpv3
        ERPV3(163, "erpv3"),
        //中通天鸿
        TIANHONG(164, "中通天鸿"),
        //云羿
        YUNYI(165, "云羿"),
        //BeauPhone语音卡
        BPYUYIN(229, "BeauPhone语音卡"),
        //汉天
        HANTIAN(233,"汉天");
        private Integer value;

        private String remark;

        CallType(Integer value, String remark) {
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
     * @Description 跟记录类型
     */
    public enum CollectionWayEnum {
        AUTO(0, "自动"),
        MANUAL(1, "手动");
        private Integer value;

        private String remark;

        CollectionWayEnum(Integer value, String remark) {
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
     * @Description 跟踪记录类型
     */
    public enum CaseFollowupType {
        //电催
        INNER(225, "内催"),
        //外访
        OUTER(226, "委外"),
        //司法
        JUDICIAL(227, "司法"),
        //委外
        DESTORY(228, "核销");

        private Integer value;
        private String remark;

        CaseFollowupType(Integer value, String remark) {
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