package cn.fintecher.pangolin.business.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yuanyanting
 * @version Id:CaseInfoIntelligentModel.java,v 0.1 2017/12/20 16:24 yuanyanting Exp $$
 */
@Data
public class CaseInfoIntelligentModel {
    private String collector;//催收员
    private String collectorId;//催收员Id
    private Integer count;//分配的案件数量
    private BigDecimal sum;//分配的案件金额
    private List<String> caseNumbers;//案件编号的List集合
}
