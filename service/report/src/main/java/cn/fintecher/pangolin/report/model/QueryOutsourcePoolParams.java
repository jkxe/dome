package cn.fintecher.pangolin.report.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : huyanmin
 * @Description : 委外催收中查询的Params
 * @Date : 2017/9/25
 */

@Data
public class  QueryOutsourcePoolParams {

    @ApiModelProperty(value = "公司code")
    private String companyCode;
    @ApiModelProperty(value = "案件编号")
    private String caseNumber;
    @ApiModelProperty(value = "批次号")
    private String batchNumber;
    @ApiModelProperty(value = "客户姓名")
    private String personalName;
    @ApiModelProperty(value = "手机号")
    private String mobileNo;
    @ApiModelProperty(value = "最大案件金额")
    private BigDecimal overdueMaxAmt;
    @ApiModelProperty(value = "最小案件金额")
    private BigDecimal overdueMinAmt;
    @ApiModelProperty(value = "最大逾期天数")
    private Integer overMaxDay;
    @ApiModelProperty(value = "最小逾期天数")
    private Integer overMinDay;
    @ApiModelProperty(value = "身份证号")
    private String idCard;
    @ApiModelProperty(value = "受托方名称")
    private String outsId;
    @ApiModelProperty(value = "受托方名称")
    private String outSourceName;
    @ApiModelProperty(value = "委外日期开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String outTimeStart;
    @ApiModelProperty(value = "委外日期结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String outTimeEnd;
/*    @ApiModelProperty(value = "委外状态")
    private Integer outStatus;*/
    @ApiModelProperty(notes = "催收状态 24已结清,25待分配")
    private Integer collectionStatus;
    @ApiModelProperty(value = "页码数")
    private Integer page;
    @ApiModelProperty(value = "每页大小")
    private Integer size;
    @ApiModelProperty(value = "排序")
    private String sort;
    /*选择委外方或批次号显示的类型*/
    private Integer type;
    @ApiModelProperty(value = "机构id")
    private String departId;
    @ApiModelProperty(value = "委托方id")
    private String principalId;
    @ApiModelProperty(value = "催收员姓名")
    private String realName;
    @ApiModelProperty(value = "案件流入时间")
    private String caseFollowInTime;
    @ApiModelProperty(value = "城市id")
    private String cityId;
    @ApiModelProperty(value = "省份id")
    private String areaId;
    @ApiModelProperty(value = "产品系列id")
    private String seriesId;
    @ApiModelProperty(value = "打标标记")
    private Integer caseMark;
    @ApiModelProperty(value = "逾期期数")
    private String overduePeriods;
    @ApiModelProperty(value = "催收反馈")
    private Integer followupBack;
    @ApiModelProperty(value = "流转来源")
    private Integer turnFromPool;
    @ApiModelProperty(value = "诉讼阶段")
    private Integer lawsuitResult;
    @ApiModelProperty(value = "反欺诈结果")
    private Integer antiFraudResult;
    @ApiModelProperty(value = "跟进时间")
    private String followupTime;
    @ApiModelProperty(value = "流转去向")
    private Integer turnToPool;
    @ApiModelProperty(value = "流转状态")
    private Integer turnStatus;
    @ApiModelProperty(value = "案件分配时间")
    private String distributeTime;
    @ApiModelProperty(value = "案件开始跟进时间")
    private String startFollowDate;
    @ApiModelProperty(value = "案件结束跟进时间")
    private String endFollowDate;
    @ApiModelProperty(value = "逾期状态")
    private String payStatus;
    @ApiModelProperty(value = "回收类型")
    private Integer returnType;
    @ApiModelProperty(notes = "hy-队列名称")
    private String queueName;
    @ApiModelProperty(notes = "hy-来源渠道")
    private String sourceChannel;// 来源渠道
    @ApiModelProperty(notes = "hy-催收方式")
    private String collectionMethod;// 催收方式
    @ApiModelProperty(notes = "hy-逾期次数")
    private Integer overdueCount;//逾期次数
    @ApiModelProperty(notes = "产品名称")
    private String productName;
    @ApiModelProperty(notes = "当前催收员")
    private String collector;

    @ApiModelProperty(value = "出催时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endOutsourceTimeStart;
    @ApiModelProperty(value = "出催时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endOutsourceTimeEnd;

    @ApiModelProperty(value = "到期日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date overOutSourceTimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "到期日期")
    private Date overOutSourceTimeEnd;

}
