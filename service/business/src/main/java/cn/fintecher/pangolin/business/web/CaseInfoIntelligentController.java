package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.CaseInfoIntelligentParams;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.service.CaseInfoIntelligentService;
import cn.fintecher.pangolin.business.service.IntelligentService;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author yuanyanting
 * @version Id:CaseInfoIntelligentController.java,v 0.1 2017/12/20 9:46 yuanyanting Exp $$
 */
@RestController
@RequestMapping("/api/caseInfoIntelligentController")
@Api(value = "CaseInfoIntelligentController", description = "云分析接口")
public class CaseInfoIntelligentController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CaseInfoIntelligentController.class);

    private static final String ENTITY_NAME = "CaseInfoIntelligentController";

    @Autowired
    private CaseInfoRepository caseInfoRepository;

    @Autowired
    private SysParamRepository sysParamRepository;

    @Autowired
    private CaseInfoIntelligentService caseInfoIntelligentService;

    @Autowired
    private IntelligentService intelligentService;

    /**
     * @Description 智能分案预览
     */
    @PostMapping("/caseInfoToDistributeIntelligentView")
    @ApiOperation(value = "智能分案预览", notes = "智能分案预览")
    public ResponseEntity caseInfoToDistributeIntelligentView(@RequestHeader(value = "X-UserToken") String token,
                                                              @RequestBody CaseInfoIntelligentParams caseInfoIntelligentParams) {
        try {
            User tokenUser = getUserByToken(token);

            List<String> caseNumbers = caseInfoIntelligentParams.getCaseNumber();
            if (Objects.isNull(caseNumbers) || caseNumbers.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "user", "请选择需要智能分案的案件！")).body(null);
            } else {

                SysParam param = sysParamRepository.findOne(QSysParam.sysParam.code.eq(Constants.ANALYSIS_CODE).and(QSysParam.sysParam.companyCode.eq(tokenUser.getCompanyCode())));
                if (Objects.nonNull(param)) {
                    if (Objects.equals(Integer.parseInt(param.getValue()), 1)) {
                        Object[] caseInfoToDistribute = caseInfoRepository.caseInfoToDistribute(tokenUser.getCompanyCode(), caseNumbers);
                        intelligentService.doAnalysis(tokenUser, caseInfoToDistribute);
                    }
                }
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("发送数据失败");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "user", "AI智能获取分配结果失败，请重试或手动分配。")).body(null);
        }
    }

    /**
     * @Description 智能分案
     */
    @PostMapping("/caseInfoToDistributeIntelligent")
    @ApiOperation(value = "智能分案", notes = "智能分案")
    public ResponseEntity caseInfoToDistributeIntelligent(@RequestBody CaseInfoIntelligentParams caseInfoIntelligentParams) {
        try {
            caseInfoIntelligentService.setCaseInfoDistribute(caseInfoIntelligentParams);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.debug("发送数据失败");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "user", e.getMessage())).body(null);
        }
    }
}
