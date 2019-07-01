package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by duchao on 2018/6/19.
 */


@Entity
@Table(name = "missing_connection_info")
@Data
public class MissingConnectionInfo extends BaseEntity{
    /**
     * 案件编号（申请号）
     */
    @ApiModelProperty(name = "申请号")
    private String caseNumber;
    /**
     * 合同编号
     */
    @ApiModelProperty(name = "合同编号")
    private String contractNumber;
    /**
     * 客户姓名
     */
    @ApiModelProperty(name = "客户姓名")
    private String personalName;
    /**
     * 身份证号码
     */
    @ApiModelProperty(name = "身份证号码")
    private String idCard;
    /**
     * 手机号码
     */
    @ApiModelProperty(name = "手机号码")
    private String mobileNo;
    /**
     * 性别
     */
    @ApiModelProperty(name = "性别")
    private int sex;
    /**
     * 首次失联时间
     */
    @ApiModelProperty(name = "首次失联时间")
    private Date firstMissingTime;
    /**
     * 当前失联天数
     */
    @ApiModelProperty(name = "当前失联天数")
    private int currentMissingDays;
    /**
     * 最长失联天数
     */
    @ApiModelProperty(name = "最长失联天数")
    private int longestMissingDays;
    /**
     * 失联次数
     */
    @ApiModelProperty(name = "失联次数")
    private int missingTimes;
    /**
     * 当前是否失联
     */
    @ApiModelProperty(name = "当前是否失联")
    private boolean currentMissingFlag;
    /**
     * 公司code
     */
    @ApiModelProperty(name = "公司code", notes = "不导出Excel")
    private String companyCode;
}
