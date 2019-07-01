package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ChenChang on 2017/7/12.
 */
@Entity
@Table(name = "personal_bank")
@Data
public class PersonalBank implements Serializable {
    @Id
    private String id;

    @ApiModelProperty(notes = "账户类型")
    private String accountType;

    @ApiModelProperty(notes = "开户银行")
    private String depositBank;

    @ApiModelProperty(notes = "开户支行")
    private String depositBranch;

    @ApiModelProperty(notes = "银行卡号")
    private String cardNumber;

    @ApiModelProperty(notes = "开户省份")
    private String depositProvince;

    @ApiModelProperty(notes = "开户城市")
    private String depositCity;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "客户ID")
    private String personalId;

    @ApiModelProperty(notes = "账户号")
    private String accountNumber;

    @ApiModelProperty(notes = "hy-客户id")
    private String customerId;

    @ApiModelProperty(notes = "hy-资源项id")
    private String resourceId;

    @ApiModelProperty(notes = "hy-账号类型")
    private String accountKind;

    @ApiModelProperty(notes = "hy-账户名")
    private String accountName;

    @ApiModelProperty(notes = "hy-账号创建日期")
    private Date buildDate;

    @ApiModelProperty(notes = "hy-创建时间")
    private Date createTime;

    @Transient
    private String certificatesNumber;

}
