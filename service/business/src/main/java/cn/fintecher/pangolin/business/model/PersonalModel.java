package cn.fintecher.pangolin.business.model;

import cn.fintecher.pangolin.entity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PersonalModel {
    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "hy-客户id")
    private String customerId;

    @ApiModelProperty(notes = "客户来源")
    private String personalSource;

    @ApiModelProperty(notes = "其他来源")
    private String otherSource;

    @ApiModelProperty(notes = "推介人")
    private String referrer;

    @ApiModelProperty(notes = "信用等级")
    private String creditLevel;

    @ApiModelProperty(notes = "推介支行")
    private String introductionBank;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "性别")
    private Integer sex;

    @ApiModelProperty(notes = "年龄")
    private Integer age;

    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;

    @ApiModelProperty(notes = "电子邮箱地址")
    private String email;

    @ApiModelProperty(notes = "教育程度")
    private Integer education;

    @ApiModelProperty(notes = "婚姻状态")
    private Integer marital;

    @ApiModelProperty(notes = "子女数量")
    private Integer childrenNumber;

    @ApiModelProperty(notes = "国籍")
    private String nationality;

    @ApiModelProperty(notes = "证件类型")
    private String certificatesType;

    @ApiModelProperty(notes = "证件号码")
    private String certificatesNumber;

    @ApiModelProperty(notes = "身份证发证机关")
    private String idCardIssuingAuthority;

    @ApiModelProperty(notes = "身份证到期时间")
    private Date idCardExpirydate;

    @ApiModelProperty(notes = "身份证地址")
    private String idCardAddress;

    @ApiModelProperty(notes = "现居住地址")
    private String localHomeAddress;

    @ApiModelProperty(notes = "居住地家庭座机")
    private String localPhoneNo;

    @ApiModelProperty(notes = "现居住迁入时间")
    private Date liveMoveTime;

    @ApiModelProperty(notes = "居住房屋所有权")
    private String homeOwnership;

    @ApiModelProperty(notes = "户籍地址")
    private String permanentAddress;

    @ApiModelProperty(notes = "客户对应的银行信息")
    private List<PersonalBank> personalBank;

    @ApiModelProperty(notes = "客户的车辆信息")
    private PersonalCar personalCar;

    @ApiModelProperty(notes = "工作信息")
    private PersonalJob personalJob;

    @ApiModelProperty(notes = "收入信息")
    private PersonalIncomeExp personalIncomeExp;

    @ApiModelProperty(notes = "联系人信息")
    private List<PersonalContact> personalContactList;

    @ApiModelProperty(notes = "房产信息")
    private PersonalPropertyModel personalPropertyModel;

    @ApiModelProperty(notes = "社交信息")
    private List<PersonalSocialPlat> personalSocialPlats;
}
