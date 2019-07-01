package cn.fintecher.pangolin.enums;
/**
 * @Description 数据来源枚举类
 */
public enum ESource {
    //电话
    TEL(80, "电话"),
    //外访
    VISIT(81, "外访"),
    //信贷
    LOAN(82, "信贷"),
    ASSIST(203, "协催"),
    OUTSIDE(1020, "委外"),;
    private Integer value;

    private String remark;

    ESource(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static ESource getESourceByCode(String code){
        for (ESource eSource : ESource.values()) {
            if(eSource.value.toString().equals(code)){
                return eSource;
            }
        }
        return null;
    }
    public static ESource getESourceByRemark(String remark){
        for (ESource eSource : ESource.values()) {
            if(eSource.remark.equals(remark)){
                return eSource;
            }
        }
        return null;
    }
}
