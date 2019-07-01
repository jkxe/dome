package cn.fintecher.pangolin.enums;

/**
 * @Description hy- 电催-有效联络枚举类 字典表type_code:0310
 */
public enum EffectiveCollectionTel {
    INSY(1030,"联系到本人，未达成还款协议"),
    ALPA(1031,"联系到本人或第三方，已还还款（电话接通）"),
    PTP(1032,"本人或第三方，达成还款协议"),
    MESS(1033,"联系到第三方，有效转告"),
    NOIN(1034,"联系到第三方，无有效转告或未明确无法转告"),
    PTPT(1035,"承诺明天还款"),
    KNOW(1036,"本人表示知道欠款情况，有还款诚意，无还款能力");

    private Integer value;

    private String remark;

    EffectiveCollectionTel(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static EffectiveCollectionTel getEnumByCode(String code){
        for (EffectiveCollectionTel effectiveCollectionTel : EffectiveCollectionTel.values()) {
            if(effectiveCollectionTel.value.toString().equals(code)){
                return effectiveCollectionTel;
            }
        }
        return null;
    }
    public static EffectiveCollectionTel getEnumByRemark(String remark){
        for (EffectiveCollectionTel effectiveCollectionTel : EffectiveCollectionTel.values()) {
            if(effectiveCollectionTel.remark.equals(remark)){
                return effectiveCollectionTel;
            }
        }
        return null;
    }
}
