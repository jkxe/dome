package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ChenChang on 2017/7/10.
 */
@Entity
@Table(name = "Personal")
@Data
public class Personal implements Serializable {
    @Id
    private String id;

    @ApiModelProperty(notes = "姓名")
    private String name;

    @ApiModelProperty(notes = "性别")
    private Integer sex;

    @ApiModelProperty(notes = "客户类型")
    private Integer type;

    @ApiModelProperty(notes = "婚姻状态")
    private Integer marital;

    @ApiModelProperty(notes = "教育程度")
    private Integer education;

    @ApiModelProperty(notes = "毕业学校")
    private String school;

    @ApiModelProperty(notes = "年龄")
    private Integer age;

    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;

    @ApiModelProperty(notes = "手机号码状态")
    private Integer mobileStatus;

    @ApiModelProperty(notes = "身份证号码")
    private String idCard;

    @ApiModelProperty(notes = "微信号")
    private String wechat;

    @ApiModelProperty(notes = "QQ号")
    private String qq;

    @ApiModelProperty(notes = "电子邮箱地址")
    private String email;

    @ApiModelProperty(notes = "身份证有效期")
    private Integer idCardValidityPeriod;

    @ApiModelProperty(notes = "身份证发证机关")
    private String idCardIssuingAuthority;

    @ApiModelProperty(notes = "身份证地址")
    private String idCardAddress;

    @ApiModelProperty(notes = "本市生活时长")
    private String cityLiveTime;

    @ApiModelProperty(notes = "现居生活时长")
    private String localLiveTime;

    @ApiModelProperty(notes = "居住地家庭座机")
    private String localPhoneNo;

    @ApiModelProperty(notes = "现居住地址")
    private String localHomeAddress;

    @ApiModelProperty(notes = "电费户名")
    private String electricityAccount;

    @ApiModelProperty(notes = "电费密码")
    private String electricityPwd;

    @ApiModelProperty(notes = "产品内外部标识0-Excel,1-接口同步")
    private Integer dataSource;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "公司code码")
    private String companyCode;

    @ApiModelProperty(notes = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(notes = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(notes = "客户号")
    private String number;

    @ApiModelProperty(notes = "证件类型")
    private String certificatesType;

    @ApiModelProperty(notes = "证件号码")
    private String certificatesNumber;

    @ApiModelProperty(notes = "身份证到期时间")
    private Date idCardExpirydate;

    @ApiModelProperty(notes = "现居住迁入时间")
    private Date liveMoveTime;

    @ApiModelProperty(notes = "居住房屋所有权")
    private String homeOwnership;

    @ApiModelProperty(notes = "子女数量")
    private Integer childrenNumber;

    @ApiModelProperty(notes = "国籍")
    private String nationality;

    @ApiModelProperty(notes = "客户来源")
    private String personalSource;

    @ApiModelProperty(notes = "其他来源")
    private String otherSource;

    @ApiModelProperty(notes = "推介人")
    private String referrer;

    @ApiModelProperty(notes = "推介支行")
    private String introductionBank;

    @ApiModelProperty(notes = "hy-客户id")
    private String customerId;

    @ApiModelProperty(notes = "信用等级")
    private String creditLevel;

    @ApiModelProperty(notes = "户籍地址")
    private String permanentAddress;

    @ApiModelProperty(notes = "创建时间")
    private Date createTime;

    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    @OrderBy("relation asc")
    private List<PersonalContact> personalContacts; //客户联系人

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalBank> personalBankInfos; //客户开户信息

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalAddress> personalAddresses; //客户地址信息

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalCar> personalCars; //客户车产信息

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalIncomeExp> personalIncomeExps; //客户收支信息

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalJob> personalJobs; //客户工作信息

    @OneToMany(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "personalId", insertable = false, updatable = false)
    private Set<PersonalProperty> personalProperties; //客户房产信息

    /**
     * @Description 电话状态
     */
    public enum PhoneStatus {
        NORMAL(64, "正常"),
        VACANT_NUMBER(65, "空号"),
        HALT(66, "停机"),
        //提前结清还款中
        POWER_OFF(67, "关机"),
        UNKNOWN(68, "未知");
        private Integer value;
        private String remark;

        PhoneStatus(Integer value, String remark) {
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
     * 性别
     */
    public enum SexEnum {
        MAN(142, "男"), WOMEN(143, "女"), UNKNOWN(144, "未知");
        private Integer value;
        private String remark;

        SexEnum(Integer value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public static SexEnum getEnumByCode(Integer code){
            for (SexEnum sexEnum : SexEnum.values()) {
                if(sexEnum.value.equals(code)){
                    return sexEnum;
                }
            }
            return null;
        }


        public Integer getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }

    /**
     * 关系
     */
    public enum RelationEnum {
        SELF(69, "本人"), PARTNER(70, "配偶"), PARENTS(71, "父母"), CHILDREN(72, "子女"), FAMILY(73, "亲属"), COLLEAGUE(74, "同事"),
        FRIENDS(75, "朋友"), OTHERS(76, "其他"), UNIT(77, "单位"), STUDENT(219, "同学");
        private Integer value;
        private String remark;

        RelationEnum(Integer value, String remark) {
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

    public enum AddrRelationEnum {
        CURRENTADDR(83, "现居住地址"), UNITADDR(84, "单位地址"), IDCARDADDR(85, "身份证地址"),
        PROPERTYADDR(86, "房产地址"), OTHERS(87, "其他");
        private Integer value;
        private String remark;

        AddrRelationEnum(Integer value, String remark) {
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
     * @Description 地址状态
     */
    public enum AddrStatus {
        VALIDADDR(148, "有效地址"),
        NOADDR(149, "地址不存在"),
        UNRELATEDADDR(150, "无关地址"),
        SALEOFF(151, "已变卖"),
        RENTOUT(152, "出租"),
        VACANCY(153, "空置"),
        UNKNOWN(154, "未知");
        private Integer value;
        private String remark;

        AddrStatus(Integer value, String remark) {
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
     * 婚姻状况
     */
    public enum MARITAL {
        UNMARRIED(207, "未婚"),
        MARRIED(208, "已婚"),
        UNKNOW(209, "未知"),
        WIDOWHOOD(898,"丧偶"),
        DIVORCE(899,"离异");
        private Integer value;
        private String remark;

        MARITAL(Integer value, String remark) {
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
     * 学历
     */
    public enum EDUCATION {
        MASTER(900, "硕士及以上"),
        UNDERGRADUATE(901, "本科"),
        JUNIOR_COLLEGE(902, "大专"),
        SENIOR_SCHOOL(903,"高中/中专"),
        SENIOR_MIDDLE(904,"初中及以下"),
        UNKNOWN(1117,"未知");
        private Integer value;
        private String remark;

        EDUCATION(Integer value, String remark) {
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
     * 证件类型
     */
    public enum CertificatesType{
        IDCARD(905,"身份证"),
        HOUSEHOLD(906,"户口薄"),
        PASSPORT(907,"护照"),
        MILITARY(908,"军官证"),
        SOLDIER_CARD(909,"士兵证"),
        Civilian_cadres(910,"文职干部证"),
        Hong_Kong(911,"港澳居民来往内地通行证"),
        TaiWan(912,"台湾同胞来往内地通行证"),
        Temporary_card(913,"临时身份证"),
        Police_officer_card(914,"警官证"),
        Test_certificate(915,"测试证件"),
        Company_documents(916,"公司证件"),
        Other_Card(917,"其它证件"),
        VIP_certificate(918,"VIP证件");
        private Integer value;
        private String remark;

        CertificatesType(Integer value, String remark) {
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
    public enum TYPE {
        A(810, "普通"),
        B(811, "B类"),
        C(812, "C类"),
        D(813, "D类");
        private Integer value;
        private String remark;

        TYPE(Integer value, String remark) {
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
     * 客户来源
     *
     */
    public enum PersonalSource{
        Direct_guest("924","直客"),
        intermediary("925","中介"),
        Website_reservation("926","网站预约"),
        Electric_pin("927","电销"),
        Bank_recommends("928","成都银行推荐"),
        Advertisement("929","广告"),
        Other("930","其他"),
        Short_message("931","成都银行短信"),
        Old_customer("932","老客户"),
        Platform_merchant("933","平台商户"),
        Cooperation_Agency("934","合作机构"),
        commercial_bank("935","商业银行"),
        Transfer_agency("936","转介绍机构"),
        Introduce("937","转介绍个人"),
        commerce_platform("938","电子商务平台");
        private String value;
        private String remark;
        PersonalSource(String value, String remark) {
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
