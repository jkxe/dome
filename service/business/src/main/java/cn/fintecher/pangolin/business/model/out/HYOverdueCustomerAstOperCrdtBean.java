package cn.fintecher.pangolin.business.model.out;

import lombok.Data;

@Data
public class HYOverdueCustomerAstOperCrdtBean {
    private String userId;//客户id
    private String resourceId;//资源项id
    private String resourceType;//资源项类型
    private String originalData;//数据体
    private String createTime;//创建时间
    private String updateTime;//更新时间

    /**
     * 资源项类型
     */
    public enum ResourceType {
        VEHICLE("3","车辆"),
        IMMOVABLE_PROPERTY("4","不动产"),
        MANAGEMENT("7", "经营"),
        INCOME("8", "收入"),
        FINANCIAL_ASSETS("9", "金融资产"),
        JUDICIAL_LITIGATION("14", "司法诉讼"),
        SOCIAL_SECURITY("15", "社保"),
        CREDIT_HISTORICAL("16", "历史信贷"),
        ACCUMULATION_FUND("12", "公积金");

        private String value;
        private String remark;

        ResourceType(String value, String remark) {
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
