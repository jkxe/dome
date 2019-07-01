package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : xiaqun
 * @Description : 客户工作单位信息
 * @Date : 10:59 2017/7/26
 */
@Data
@Entity
@Table(name = "personal_job")
public class PersonalJob extends BaseEntity {
    @ApiModelProperty(notes = "客户信息ID")
    private String personalId;

    @ApiModelProperty(notes = "单位名称")
    private String companyName;

    @ApiModelProperty(notes = "部门")
    private String department;

    @ApiModelProperty(notes = "职务")
    private String position;

    @ApiModelProperty(notes = "职级")
    private String rank;

    @ApiModelProperty(notes = "单位性质")
    private String nature;

    @ApiModelProperty(notes = "单位规模")
    private String scale;

    @ApiModelProperty(notes = "单位固定电话")
    private String phone;

    @ApiModelProperty(notes = "单位成立时间")
    private Date createTime;

    @ApiModelProperty(notes = "单位地址")
    private String address;

    @ApiModelProperty(notes = "何时进入公司")
    private Date joinTime;

    @ApiModelProperty(notes = "基本月薪")
    private BigDecimal monthSalary;

    @ApiModelProperty(notes = "每月发薪日")
    private Integer payDay;

    @ApiModelProperty(notes = "所属行业")
    private String industry;

    @ApiModelProperty(notes = "发薪方式")
    private String payWay;

    @ApiModelProperty(notes = "现单位工作年限")
    private String workYear;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "工作时长（月）")
    private Integer workMother;

    @ApiModelProperty(notes = "hy-单位电话区号")
    private String phoneAreaCode;

    @ApiModelProperty(notes = "hy-单位电话分机号")
    private String phoneExt;

    @ApiModelProperty(notes = "hy-职业")
    private String career;

    @ApiModelProperty(notes = "hy-单位省")
    private String provinceName;

    @ApiModelProperty(notes = "hy-单位市")
    private String cityName;

    @ApiModelProperty(notes = "hy-单位区")
    private String areaName;

    @ApiModelProperty(notes = "hy-单位省编号")
    private String provinceCode;

    @ApiModelProperty(notes = "hy-单位市编号")
    private String cityCode;

    @ApiModelProperty(notes = "hy-单位区编号")
    private String areaCode;

    @Transient
    private String certificatesNumber;
    /**
     * 单位性质
     */
    public enum Nature{
        Government("919","事业单位"),
        Sociology("920","社会团体"),
        enterprise("921","企业"),
        individual("922","个体"),
        other("923","其他");
        private String value;
        private String remark;
        Nature(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }
        public String getValue() {
            return value;
        }
        public String getRemark() {
            return remark;
        }
    }

}