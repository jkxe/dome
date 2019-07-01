package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.CaseInfoIntelligentModel;
import cn.fintecher.pangolin.business.model.ListResult;
import cn.fintecher.pangolin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yuanyanting
 * @version Id:IntelligentService.java,v 0.1 2017/12/21 15:51 yuanyanting Exp $$
 */
@Service("intelligentService")
public class IntelligentService {

    @Autowired
    private CaseInfoIntelligentService caseInfoIntelligentService;

    @Autowired
    private RestTemplate restTemplate;

    public void doAnalysis(User user, Object[] caseInfoToDistribute) {
        List<CaseInfoIntelligentModel> list = caseInfoIntelligentService.getCaseInfoDistribute(user, caseInfoToDistribute);
        ListResult result = new ListResult();
        result.setUser(user.getId());
        result.setResult(list);
        result.setStatus(ListResult.Status.SUCCESS.getVal());
        restTemplate.postForEntity("http://reminder-service/api/listResultMessageResource", result, Void.class);
    }
}
