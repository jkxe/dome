package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 案件异常池
 */
@Entity
@Table(name = "case_info_exception")
@Data
public class CaseInfoException extends BaseEntity {
    @ApiModelProperty(notes = "客户姓名")
    private String personalName;

    @ApiModelProperty(notes = "身份证号")
    private String idCard;

    @ApiModelProperty(notes = "手机号码")
    private String mobileNo;

    @ApiModelProperty(notes = "产品系列名称")
    private String productSeriesName;

    @ApiModelProperty(notes = "产品名称")
    private String productName;

    @ApiModelProperty(notes = "合同编号")
    private String contractNumber;

    @ApiModelProperty(notes = "贷款日期")
    private Date loanDate;

    @ApiModelProperty(notes = "还款期数")
    private Integer periods;

    @ApiModelProperty(notes = "每期还款日")
    private String perDueDate;

    @ApiModelProperty(notes = "每期还款金额")
    private BigDecimal perPayAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "合同金额")
    private BigDecimal contractAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "剩余本金")
    private BigDecimal leftCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "剩余利息")
    private BigDecimal leftInterest = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期总金额")
    private BigDecimal overdueAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期本金")
    private BigDecimal overdueCapital = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期利息")
    private BigDecimal overDueInterest = new BigDecimal(0) ;

    @ApiModelProperty(notes = "逾期罚息")
    private BigDecimal overdueFine = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期滞纳金")
    private BigDecimal overdueDelayFine = new BigDecimal(0);

    @ApiModelProperty(notes = "其他金额")
    private BigDecimal otherAmt = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期日期")
    private Date overDueDate;

    @ApiModelProperty(notes = "逾期期数")
    private Integer overDuePeriods;

    @ApiModelProperty(notes = "逾期天数")
    private Integer overDueDays;

    @ApiModelProperty(notes = "已还款金额")
    private BigDecimal hasPayAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "已还款期数")
    private Integer hasPayPeriods;

    @ApiModelProperty(notes = "最近还款日期")
    private Date latelyPayDate;

    @ApiModelProperty(notes = "")
    private BigDecimal latelyPayAmount = new BigDecimal(0);

    @ApiModelProperty(notes = "最近还款金额")
    private String depositBank;

    @ApiModelProperty(notes = "银行卡号")
    private String cardNumber;

    @ApiModelProperty(notes = "省份")
    private String province;

    @ApiModelProperty(notes = "城市")
    private String city;

    @ApiModelProperty(notes = "家庭住址")
    private String homeAddress;

    @ApiModelProperty(notes = "家庭固话")
    private String homePhone;

    @ApiModelProperty(notes = "身份证户籍地址")
    private String idCardAddress;

    @ApiModelProperty(notes = "工作单位名称")
    private String companyName;

    @ApiModelProperty(notes = "工作单位地址")
    private String companyAddr;

    @ApiModelProperty(notes = "工作单位电话")
    private String companyPhone;

    @ApiModelProperty(notes = "联系人1姓名")
    private String contactName1;

    @ApiModelProperty(notes = "联系人1与客户关系")
    private String contactRelation1;

    @ApiModelProperty(notes = "联系人1工作单位")
    private String contactWorkUnit1;

    @ApiModelProperty(notes = "联系人1单位电话")
    private String contactUnitPhone1;

    @ApiModelProperty(notes = "联系人1手机号码")
    private String contactPhone1;

    @ApiModelProperty(notes = "联系人1住宅电话")
    private String contactHomePhone1;

    @ApiModelProperty(notes = "联系人1现居地址")
    private String contactCurrAddress1;

    @ApiModelProperty(notes = "联系人2姓名")
    private String contactName2;

    @ApiModelProperty(notes = "联系人2与客户关系")
    private String contactRelation2;

    @ApiModelProperty(notes = "联系人2工作单位")
    private String contactWorkUnit2;

    @ApiModelProperty(notes = "联系人2单位电话")
    private String contactUnitPhone2;

    @ApiModelProperty(notes = "联系人2手机号码")
    private String contactPhone2;

    @ApiModelProperty(notes = "联系人2住宅电话")
    private String contactHomePhone2;

    @ApiModelProperty(notes = "联系人2现居地址")
    private String contactCurrAddress2;

    @ApiModelProperty(notes = "联系人3姓名")
    private String contactName3;

    @ApiModelProperty(notes = "联系人3与客户关系")
    private String contactRelation3;

    @ApiModelProperty(notes = "联系人3工作单位")
    private String contactWorkUnit3;

    @ApiModelProperty(notes = "联系人3单位电话")
    private String contactUnitPhone3;

    @ApiModelProperty(notes = "联系人3手机号码")
    private String contactPhone3;

    @ApiModelProperty(notes = "联系人3住宅电话")
    private String contactHomePhone3;

    @ApiModelProperty(notes = "联系人3现居地址")
    private String contactCurrAddress3;

    @ApiModelProperty(notes = "联系人4姓名")
    private String contactName4;

    @ApiModelProperty(notes = "联系人4与客户关系")
    private String contactRelation4;

    @ApiModelProperty(notes = "联系人4工作单位")
    private String contactWorkUnit4;

    @ApiModelProperty(notes = "联系人4单位电话")
    private String contactUnitPhone4;

    @ApiModelProperty(notes = "联系人4手机号码")
    private String contactPhone4;

    @ApiModelProperty(notes = "联系人4住宅电话")
    private String contactHomePhone4;

    @ApiModelProperty(notes = "联系人4现居地址")
    private String contactCurrAddress4;

    @ApiModelProperty(notes = "联系人5姓名")
    private String contactName5;

    @ApiModelProperty(notes = "联系人5与客户关系")
    private String contactRelation5;

    @ApiModelProperty(notes = "联系人5工作单位")
    private String contactWorkUnit5;

    @ApiModelProperty(notes = "联系人5单位电话")
    private String contactUnitPhone5;

    @ApiModelProperty(notes = "联系人5手机号码")
    private String contactPhone5;

    @ApiModelProperty(notes = "联系人5住宅电话")
    private String contactHomePhone5;

    @ApiModelProperty(notes = "联系人5现居地址")
    private String contactCurrAddress5;

    @ApiModelProperty(notes = "联系人6姓名")
    private String contactName6;

    @ApiModelProperty(notes = "联系人6与客户关系")
    private String contactRelation6;

    @ApiModelProperty(notes = "联系人6工作单位")
    private String contactWorkUnit6;

    @ApiModelProperty(notes = "联系人6单位电话")
    private String contactUnitPhone6;

    @ApiModelProperty(notes = "联系人6手机号码")
    private String contactPhone6;

    @ApiModelProperty(notes = "联系人6住宅电话")
    private String contactHomePhone6;

    @ApiModelProperty(notes = "联系人6现居地址")
    private String contactCurrAddress6;

    @ApiModelProperty(notes = "联系人7姓名")
    private String contactName7;

    @ApiModelProperty(notes = "联系人7与客户关系")
    private String contactRelation7;

    @ApiModelProperty(notes = "联系人7工作单位")
    private String contactWorkUnit7;

    @ApiModelProperty(notes = "联系人7单位电话")
    private String contactUnitPhone7;

    @ApiModelProperty(notes = "联系人7手机号码")
    private String contactPhone7;

    @ApiModelProperty(notes = "联系人7住宅电话")
    private String contactHomePhone7;

    @ApiModelProperty(notes = "联系人7现居地址")
    private String contactCurrAddress7;

    @ApiModelProperty(notes = "联系人8姓名")
    private String contactName8;

    @ApiModelProperty(notes = "联系人8与客户关系")
    private String contactRelation8;

    @ApiModelProperty(notes = "联系人8工作单位")
    private String contactWorkUnit8;

    @ApiModelProperty(notes = "联系人8单位电话")
    private String contactUnitPhone8;

    @ApiModelProperty(notes = "联系人8手机号码")
    private String contactPhone8;

    @ApiModelProperty(notes = "联系人8住宅电话")
    private String contactHomePhone8;

    @ApiModelProperty(notes = "联系人8现居地址")
    private String contactCurrAddress8;

    @ApiModelProperty(notes = "联系人9姓名")
    private String contactName9;

    @ApiModelProperty(notes = "联系人9与客户关系")
    private String contactRelation9;

    @ApiModelProperty(notes = "联系人9工作单位")
    private String contactWorkUnit9;

    @ApiModelProperty(notes = "联系人9单位电话")
    private String contactUnitPhone9;

    @ApiModelProperty(notes = "联系人9手机号码")
    private String contactPhone9;

    @ApiModelProperty(notes = "联系人9住宅电话")
    private String contactHomePhone9;

    @ApiModelProperty(notes = "联系人9现居地址")
    private String contactCurrAddress9;

    @ApiModelProperty(notes = "联系人10姓名")
    private String contactName10;

    @ApiModelProperty(notes = "联系人10与客户关系")
    private String contactRelation10;

    @ApiModelProperty(notes = "联系人10工作单位")
    private String contactWorkUnit10;

    @ApiModelProperty(notes = "联系人10单位电话")
    private String contactUnitPhone10;

    @ApiModelProperty(notes = "联系人10手机号码")
    private String contactPhone10;

    @ApiModelProperty(notes = "联系人10住宅电话")
    private String contactHomePhone10;

    @ApiModelProperty(notes = "联系人10现居地址")
    private String contactCurrAddress10;

    @ApiModelProperty(notes = "联系人11姓名")
    private String contactName11;

    @ApiModelProperty(notes = "联系人11与客户关系")
    private String contactRelation11;

    @ApiModelProperty(notes = "联系人11工作单位")
    private String contactWorkUnit11;

    @ApiModelProperty(notes = "联系人11单位电话")
    private String contactUnitPhone11;

    @ApiModelProperty(notes = "联系人11手机号码")
    private String contactPhone11;

    @ApiModelProperty(notes = "联系人11住宅电话")
    private String contactHomePhone11;

    @ApiModelProperty(notes = "联系人11现居地址")
    private String contactCurrAddress11;

    @ApiModelProperty(notes = "联系人12姓名")
    private String contactName12;

    @ApiModelProperty(notes = "联系人12与客户关系")
    private String contactRelation12;

    @ApiModelProperty(notes = "联系人12工作单位")
    private String contactWorkUnit12;

    @ApiModelProperty(notes = "联系人12单位电话")
    private String contactUnitPhone12;

    @ApiModelProperty(notes = "联系人12手机号码")
    private String contactPhone12;

    @ApiModelProperty(notes = "联系人12住宅电话")
    private String contactHomePhone12;

    @ApiModelProperty(notes = "联系人12现居地址")
    private String contactCurrAddress12;

    @ApiModelProperty(notes = "联系人13姓名")
    private String contactName13;

    @ApiModelProperty(notes = "联系人13与客户关系")
    private String contactRelation13;

    @ApiModelProperty(notes = "联系人13工作单位")
    private String contactWorkUnit13;

    @ApiModelProperty(notes = "联系人13单位电话")
    private String contactUnitPhone13;

    @ApiModelProperty(notes = "联系人13手机号码")
    private String contactPhone13;

    @ApiModelProperty(notes = "联系人13住宅电话")
    private String contactHomePhone13;

    @ApiModelProperty(notes = "联系人13现居地址")
    private String contactCurrAddress13;

    @ApiModelProperty(notes = "联系人14姓名")
    private String contactName14;

    @ApiModelProperty(notes = "联系人14与客户关系")
    private String contactRelation14;

    @ApiModelProperty(notes = "联系人14工作单位")
    private String contactWorkUnit14;

    @ApiModelProperty(notes = "联系人14单位电话")
    private String contactUnitPhone14;

    @ApiModelProperty(notes = "联系人14手机号码")
    private String contactPhone14;

    @ApiModelProperty(notes = "联系人14住宅电话")
    private String contactHomePhone14;

    @ApiModelProperty(notes = "联系人14现居地址")
    private String contactCurrAddress14;

    @ApiModelProperty(notes = "联系人15姓名")
    private String contactName15;

    @ApiModelProperty(notes = "联系人15与客户关系")
    private String contactRelation15;

    @ApiModelProperty(notes = "联系人15工作单位")
    private String contactWorkUnit15;

    @ApiModelProperty(notes = "联系人15单位电话")
    private String contactUnitPhone15;

    @ApiModelProperty(notes = "联系人15手机号码")
    private String contactPhone15;

    @ApiModelProperty(notes = "联系人15住宅电话")
    private String contactHomePhone15;

    @ApiModelProperty(notes = "联系人15现居地址")
    private String contactCurrAddress15;

    @ApiModelProperty(notes = "备注")
    private String memo;

    @ApiModelProperty(notes = "佣金比例")
    private BigDecimal commissionRate = new BigDecimal(0);

    @ApiModelProperty(notes = "逾期管理费")
    private BigDecimal overdueManageFee = new BigDecimal(0);

    @ApiModelProperty(notes = "还款状态")
    private String paymentStatus;

    @ApiModelProperty(notes = "案件批次号")
    private String batchNumber;

    @ApiModelProperty(notes = "委托方编号")
    private String prinCode;

    @ApiModelProperty(notes = "委托方名称")
    private String prinName;

    @ApiModelProperty(notes = "委案日期")
    private Date delegationDate;


    @ApiModelProperty(notes = "结案日期")
    private Date closeDate;

    @ApiModelProperty(notes = "操作员")
    private String operator;

    @ApiModelProperty(notes = "操作时间")
    private Date operatorTime;

    @ApiModelProperty(notes = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(notes = "数据来源")
    private Integer dataSources;

    @ApiModelProperty(notes = "案件手数")
    private Integer caseHandNum;

    @ApiModelProperty(notes = "公司code码")
    private String companyCode;

    @ApiModelProperty(notes = "案件编号")
    private String caseNumber;

    @ApiModelProperty(notes = "待分配重复记录")
    private String distributeRepeat;

    @ApiModelProperty(notes = "已分配重复记录")
    private String assignedRepeat;

    @ApiModelProperty(notes = "数据处理标识")
    private Integer repeatStatus;

    @ApiModelProperty(notes = "客户号")
    private String personalNumber;

    @ApiModelProperty(notes = "账户号")
    private String accountNumber;

    @ApiModelProperty(notes = "首次还款日期")
    private Date firstPayDate;

    @ApiModelProperty(notes = "账龄")
    private String accountAge;

    @ApiModelProperty(notes = "案件到期回收方式：0-自动回收，1-手动回收")
    private Integer recoverWay;

    @ApiModelProperty(notes = "回收标志：0-未回收，1-已回收")
    private Integer recoverRemark;

    @ApiModelProperty("hy-借据号")
    private String loanInvoiceNumber;

    /**
     * 异常数据处理类型
     */
    public enum RepeatStatusEnum{
        PENDING(182,"待处理"),UPDATE(183,"更新"),DELETE(184,"删除"),ADD(185,"新增");
        private Integer value;
        private String remark;

        RepeatStatusEnum(Integer value, String remark) {
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
