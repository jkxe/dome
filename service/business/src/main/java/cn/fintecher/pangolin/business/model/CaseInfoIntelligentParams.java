package cn.fintecher.pangolin.business.model;

import lombok.Data;

import java.util.List;

/**
 * @author yuanyanting
 * @version Id:CaseInfoIntelligentParams.java,v 0.1 2017/12/20 16:09 yuanyanting Exp $$
 */
@Data
public class CaseInfoIntelligentParams {
    private List<String> caseNumber;//案件编号的List集合
    private List<CaseInfoIntelligentModel> intelligentModels;
}
