package cn.fintecher.pangolin.enums;

/**
 * @Description 外访-有效联络枚举类
 */
public enum EffectiveCollection {
    //承诺还款
    PROMISE(90, "承诺还款"),
    //协商跟进
    CONSULT(91, "协商跟进"),
    //拒绝还款
    REFUSEPAY(92, "拒绝还款"),
    //客户提示已还款
    HAVEREPAYMENT(93, "客户提示已还款"),
    //承诺缴款,时效为2两天内能还款
    PTP(836,"承诺缴款，时效为2天内能还款"),
    //有困难，2、3天内可能会还款
    HPTP(837,"有困难，2、3天内可能会还款"),
    //已经还款，资金在途
    CAPT(838,"已经还款，资金在途"),
    //第三人代缴（父母\朋友\联系代缴）
    OPTP(839,"第三人代缴（父母\\朋友\\联系代缴）"),
    //资金有困难，需要时间周转，承诺时间长，超过3天，但要还款
    CDLT(840,"资金有困难，需要时间周转，承诺时间长，超过3天，但要还款"),
    //恶意欠款
    EYQK(841,"恶意欠款"),
    //本人拒绝沟通，不愿还款
    NOST(842,"本人拒绝沟通，不愿还款"),
    //第三人留言
    LMS(843,"第三人留言");

    private Integer value;

    private String remark;

    EffectiveCollection(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static EffectiveCollection getEnumByCode(String code){
        for (EffectiveCollection effectiveCollection : EffectiveCollection.values()) {
            if(effectiveCollection.value.toString().equals(code)){
                return effectiveCollection;
            }
        }
        return null;
    }
    public static EffectiveCollection getEnumByRemark(String remark){
        for (EffectiveCollection effectiveCollection : EffectiveCollection.values()) {
            if(effectiveCollection.remark.equals(remark)){
                return effectiveCollection;
            }
        }
        return null;
    }
}
