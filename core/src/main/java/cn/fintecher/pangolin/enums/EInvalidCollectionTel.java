package cn.fintecher.pangolin.enums;
/**
 * @Description hy- 电催-无效联络枚举类 字典表type_code:0311
 */
public enum EInvalidCollectionTel {
    NOSE(1060, "不认识本人或无法转告"),
    QUIT(1061, "第三方表示无转告下QUIT，"),
    NOAS(1062, "电话无人接听"),
    BUSY(1063, "电话占线"),
    OFF(1064, "语音回复：关机"),
    CUT(1065, "拨号中对方挂机"),
    CUT_UNVERIFIED(1066, "电话接通后，未确认接电人身份，被挂机"),
    STOP(1067, "语音回复：停机、呼入限制等"),
    LOOO(1068,"语音回复：电话不存在、空号等");

    private Integer value;

    private String remark;

    EInvalidCollectionTel(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }


    public static EInvalidCollectionTel getEnumByCode(String code){
        for (EInvalidCollectionTel eInvalidCollectionTel : EInvalidCollectionTel.values()) {
            if(eInvalidCollectionTel.value.toString().equals(code)){
                return eInvalidCollectionTel;
            }
        }
        return null;
    }
    public static EInvalidCollectionTel getEnumByRemark(String remark){
        for (EInvalidCollectionTel eInvalidCollectionTel : EInvalidCollectionTel.values()) {
            if(eInvalidCollectionTel.remark.equals(remark)){
                return eInvalidCollectionTel;
            }
        }
        return null;
    }
}
