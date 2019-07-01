package cn.fintecher.pangolin.enums;
/**
 * @Description 催收类型枚举类
 */
public enum ECollectionType {
    //电催
    TEL(15, "电催"),
    //外访
    VISIT(16, "外访"),
    //司法
    JUDICIAL(17, "司法"),
    //委外
    outside(18, "委外"),
    //提醒
    remind(19, "提醒");

    private Integer value;
    private String remark;

    ECollectionType(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static ECollectionType getEnumByCode(String code){
        for (ECollectionType eCollectionType : ECollectionType.values()) {
            if(eCollectionType.value.toString().equals(code)){
                return eCollectionType;
            }
        }
        return null;
    }
    public static ECollectionType getEnumByRemark(String remark){
        for (ECollectionType eCollectionType : ECollectionType.values()) {
            if(eCollectionType.remark.equals(remark)){
                return eCollectionType;
            }
        }
        return null;
    }

}
