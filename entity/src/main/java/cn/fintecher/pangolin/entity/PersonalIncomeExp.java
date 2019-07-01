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
 * @Description : 客户收支信息实体
 * @Date : 10:59 2017/7/26
 */

@Entity
@Table(name = "personal_income_exp")
@Data
public class PersonalIncomeExp extends BaseEntity{
    @ApiModelProperty(notes = "客户ID")
    private String personalId;

    @ApiModelProperty(notes = "年收入")
    private BigDecimal annualIncome;

    @ApiModelProperty(notes = "每月工作收入")
    private BigDecimal monthIncome;

    @ApiModelProperty(notes = "公积金账号")
    private String providentAccount;

    @ApiModelProperty(notes = "公积金密码")
    private String providentPwd;

    @ApiModelProperty(notes = "每月其他收入")
    private BigDecimal monthOtherIncome;

    @ApiModelProperty(notes = "月均支出")
    private BigDecimal monthExp;

    @ApiModelProperty(notes = "社保账号")
    private String socialSecurityAccount;

    @ApiModelProperty(notes = "社保密码")
    private String socialSecurityPwd;

    @ApiModelProperty(notes = "供养人数")
    private Integer dependentsNumber;

    @ApiModelProperty(notes = "收入来源说明")
    private String incomeMemo;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "收入类型")
    private Integer incomeType;

    @ApiModelProperty(notes = "公积金缴存单位")
    private String fundCompMame;

    @ApiModelProperty(notes = "缴存期数（月）")
    private Integer fundCount;

    @ApiModelProperty(notes = "个人公积金额度")
    private BigDecimal fundAmt;

    @ApiModelProperty(notes = "个人社保缴存额度")
    private BigDecimal socialSecurityAmt;

    @ApiModelProperty(notes = "社保缴存比例")
    private String  socialSecurityRato;

    @ApiModelProperty(notes = "月固定补贴")
    private BigDecimal subsidyMonth;

    @ApiModelProperty(notes = "家庭收入")
    private BigDecimal familyIncome;

    @ApiModelProperty(notes = "房贷/月租")
    private BigDecimal hosingLoan;

    @Transient
    private String certificatesNumber;

    /**
     * 收入类型
     */
    public enum IncomeType{
        Accumulation(939,"公积金"),
        social_security(940,"社保"),
        Taxbill(941,"税单"),
        Salary_flow(942,"薪资流水"),
        Mortgage(943,"房贷缴供流水"),
        certificate (944,"公司出具收入证明"),
        Running_water(945,"经营流水"),
        Other(946,"其他");
        private Integer value;
        private String remark;

        IncomeType(Integer value, String remark) {
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
