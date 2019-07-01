package cn.fintecher.pangolin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Table(name = "commodity")
@Data
public class Commodity extends BaseEntity{

    @ApiModelProperty(notes = "商品类型")
    private String commType;

    @ApiModelProperty(notes = "商品品牌")
    private String commBrand;

    @ApiModelProperty(notes = "商品型号")
    private String commModel;

    @ApiModelProperty(notes = "商品价格")
    private BigDecimal commPrice;

    @Transient
    private String caseNumber;

    public enum CommType{
        Mobile("947","手机"),
        Black_electricity("948","黑电"),
        White_electricity("949","白电"),
        Notebook("950","笔记本"),
        Desktop("951","台式机及外围设备"),
        Digital("952","数码"),
        Electric_vehicle("953","电动车"),
        Motorcycle("954","摩托车"),
        furniture("955","家具"),
        Musical_Instruments("956","乐器"),
        consumer("957","汽车衍生消费品"),
        Medical_cosmetology("958","医疗美容"),
        Health ("959","健康类"),
        Tourism_staging("960","旅游分期"),
        Decoration_staging("961","装修分期"),
        Educational_staging("962","教育分期"),
        Wedding_stage("963","婚庆分期"),
        General_use_cash("964","一般用途现金"),
        Other_cash("965","其他现金"),
        New_General_use_cash("966","一般用途现金(新定价)"),
        New_Other_cash("967","其他现金(新定价)"),
        Long_term_mortgage_loan("968","长期限抵押贷款"),
        Credit_loan("969","联营业务-信用贷款"),
        Circular_loan_durable_goods("970","循环贷耐用品"),
        Circular_loan_cash("971","循环贷现金"),
        Guarantee_loan("972","保证贷款"),
        Circulation_loan_instalment("973","循环贷分期付款"),
        Circular_loan_credit_loan("974","循环贷信用贷款"),
        Joint_operation("975","联营业务-变更产品要素-宽限期（新增期限）");
        private String value;
        private String remark;
        CommType(String value, String remark) {
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
