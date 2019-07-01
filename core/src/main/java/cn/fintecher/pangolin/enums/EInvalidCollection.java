package cn.fintecher.pangolin.enums;
/**
 * @Description 外访-无效联络枚举类
 */
public enum EInvalidCollection {
    //他人转告
    OTHERTELL(94, "他人转告"),
    //查找
    FIND(95, "查找"),
    //无人应答
    NOANSWER(96, "无人应答"),
    //空坏号
    EMPTYORBAD(97, "空坏号"),
    //无法接通
    NOCONNECTION(98, "无法联通"),
    //失联
    LOST(99, "失联"),
    //待核实
    VERIFICATING(100, "待核实"),
    //客已离职
    CHQJ(844,"客已离职"),
    //电话无法接通
    WFJT(845,"电话无法接通"),
    //电话嘟嘟
    DUDU(846,"电话嘟嘟"),
    //稍后联系
    STAP(847,"稍后联系"),
    //无法联系，完全失联
    CHLC(848,"无法联系，完全失联"),
    //客户死亡、刑拘、丧失民事行为能力、失踪等特殊案件
    TSAJ(849,"客户死亡、刑拘、丧失民事行为能力、失踪等特殊案件"),
    //常年不在此
    KWJZ(880,"常年不在此"),
    //拒接接听
    NOPK(881,"拒接接听"),
    //查无此人
    NOCH(882,"查无此人"),
    //一接就断，类似信号不好
    YJJD(883,"一接就断，类似信号不好"),
    //电话空号
    NOEX(884,"电话空号"),
    //电话停机
    TNIS(885,"电话停机"),
    //电话关机
    OFF(886,"电话关机"),
    //秘书台
    MST(887,"秘书台"),
    //用户忙
    BUSY(888,"用户忙"),
    //类似讲方言无法沟通，跟第三人沟通时下的CODE
    OUGT(889,"类似讲方言无法沟通，跟第三人沟通时下的CODE"),
    //用户忙
    NOAS(890,"无人接听");

    private Integer value;

    private String remark;

    EInvalidCollection(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static EInvalidCollection getEnumByCode(String code){
        for (EInvalidCollection eInvalidCollection : EInvalidCollection.values()) {
            if(eInvalidCollection.value.toString().equals(code)){
                return eInvalidCollection;
            }
        }
        return null;
    }
    public static EInvalidCollection getEnumByRemark(String remark){
        for (EInvalidCollection eInvalidCollection : EInvalidCollection.values()) {
            if(eInvalidCollection.remark.equals(remark)){
                return eInvalidCollection;
            }
        }
        return null;
    }
}
