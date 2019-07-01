package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ChenChang on 2017/7/12.
 */
@Entity
@Table(name = "personal_address")
@Data
public class PersonalAddress extends BaseEntity {
    @ApiModelProperty(notes = "关系：69-本人，70-配偶，71-父母，72-子女，73-亲属，74-同事，75-朋友，76-其他，77-单位")
    private Integer relation;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "地址类型")
    private Integer type;

    @ApiModelProperty(notes = "地址状态")
    private Integer status;

    @ApiModelProperty(notes = "数据来源")
    private Integer source;

    @ApiModelProperty(notes = "详细地址")
    private String detail;

    @ApiModelProperty(notes = "客户ID")
    private String personalId;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(notes = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(notes = "住宅省编号")
    private String livingProvinceCode;

    @ApiModelProperty(notes = "住宅省")
    private String livingProvinceName;

    @ApiModelProperty(notes = "住宅市")
    private String livingCityName;

    @ApiModelProperty(notes = "住宅市编号")
    private String livingCityCode;

    @ApiModelProperty(notes = "住宅区编号")
    private String livingAreaCode;

    @ApiModelProperty(notes = "住宅区")
    private String livingAreaName;

    @Transient
    private String certificatesNumber;

    public enum AddressType {

        LIVING_ADDRESS(83, "现居住地址"),

        COMPANY_ADDRESS(84, "单位地址"),

        PERMANENT_ADDRESS(85, "身份证地址"),

        HOUSE_ADDRESS(86, "房产地址"),

        OTHER(87, "其他");

        private Integer value;

        private String remark;

        AddressType(Integer value, String remark) {
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

    public enum AddressStatus {

        VALID(148, "有效地址"),

        NO_EXIST(149, "地址不存在"),

        NO_RELATION(150, "无关地址"),

        SELL(151, "已变卖"),

        RENT(152, "出租"),

        EMPTY(153, "空置"),

        UNKNOWN(154, "未知");

        private Integer value;

        private String remark;

        AddressStatus(Integer value, String remark) {
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

