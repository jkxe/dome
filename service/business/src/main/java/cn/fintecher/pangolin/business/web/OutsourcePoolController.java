package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.AccFinanceEntryService;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.business.service.EntityManageService;
import cn.fintecher.pangolin.business.service.OutsourcePoolService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.*;
import cn.fintecher.pangolin.model.OutDistributeInfo;
import cn.fintecher.pangolin.model.OutsourcePoolBatchModel;
import cn.fintecher.pangolin.model.PreviewModel;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import freemarker.template.Configuration;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by  baizhangyu.
 * Description:
 * Date: 2017-07-26-10:14
 */
@RestController
@RequestMapping("/api/outsourcePoolController")
@Api(value = "委外管理", description = "委外管理")
public class OutsourcePoolController extends BaseController {
    //案件批次号最大99999（5位）
    public final static String CASE_SEQ = "caseSeq";
    private static final String ENTITY_NAME = "OutSource";
    private static final String ENTITY_NAME1 = "OutSourcePool";
    private static final String ENTITY_CASEINFO = "CaseInfo";
    private final Logger log = LoggerFactory.getLogger(OutsourcePoolController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AccFinanceEntryService accFinanceEntryService;
    @Autowired
    AccFinanceEntryRepository accFinanceEntryRepository;
    @Autowired
    SysParamRepository sysParamRepository;
    @Autowired
    CaseFollowupRecordRepository caseFollowupRecordRepository;
    @Inject
    OutsourcePoolService outsourcePoolService;
    @Inject
    OutBackSourceRepository outBackSourceRepository;
    @Inject
    CaseInfoService caseInfoService;
    @Autowired
    private OutsourceRepository outsourceRepository;
    @Autowired
    private CaseInfoRepository caseInfoRepository;
    @Autowired
    private OutsourcePoolRepository outsourcePoolRepository;
    @Autowired
    private OutsourceRecordRepository outsourceRecordRepository;
    @Autowired
    private  CaseTurnRecordRepository caseTurnRecordRepository;
    @Inject
    private Configuration freemarkerConfiguration;
    @Inject
    private PersonalRepository personalRepository;
    @Inject
    private CaseInfoReturnRepository caseInfoReturnRepository;
    @Inject
    private CaseInfoVerificationApplyRepository caseInfoVerificationApplyRepository;
    @Inject
    private EntityManageService entityManageService;

    @PostMapping(value = "/outsourceDistributePreview")
    @ApiOperation(value = "委外待分配按数量平均分配预览", notes = "委外待分配按数量平均分配预览")
    public ResponseEntity<PreviewModel> outsourceDistributePreview(@RequestBody OutsourceInfo outsourceInfo,
                                                                   @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to outsourceDistributePreview");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            PreviewModel list = outsourcePoolService.distributePreviewByNum(outsourceInfo, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", "按平均数分配预览失败")).body(null);
        }
    }

    @PostMapping(value = "/outsourceDistributeCeaseInfo")
    @ApiOperation(value = "委外待分配案件分配", notes = "委外待分配案件分配")
    public ResponseEntity<Object> outsourceDistributeCeaseInfo(@RequestBody OutsourceInfo outsourceInfo,
                                                               @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to outsourceDistributeCeaseInfo");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            String information = outsourcePoolService.distributeCeaseInfo(outsourceInfo, user);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(information)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("请重新选择案件", ENTITY_NAME, information)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", "分配失败")).body(null);
        }
    }

    /**
     * @Description : 查询委外分配信息
     */
    @GetMapping("/getOutDistributeInfo")
    @ApiOperation(value = "查询委外分配信息", notes = "查询委外分配信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OutDistributeInfo>> query(@RequestHeader(value = "X-UserToken") String token,
                                                         @ApiIgnore Pageable pageable) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            String companyCode;
            if (Objects.nonNull(user.getCompanyCode())) {
                companyCode = user.getCompanyCode();
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "该管理员无分案权限")).body(null);
            }
            List<OutDistributeInfo> outDistributeInfos = new ArrayList<>();
            Object[] object = outsourcePoolRepository.getAllOutSourceByCase(companyCode);
            for (int i = 0; i < object.length; i++) {
                Object[] object1 = (Object[]) object[i];
                if (Objects.nonNull(object1[1])) {
                    OutDistributeInfo outDistributeInfo = new OutDistributeInfo();
                    outDistributeInfo.setOutCode(Objects.isNull(object1[0]) ? null : object1[0].toString());
                    outDistributeInfo.setOutName(Objects.isNull(object1[1]) ? null : object1[1].toString());
                    outDistributeInfo.setCaseCount(Objects.isNull(object1[3]) ? null : Integer.valueOf(object1[3].toString()));
                    outDistributeInfo.setEndCount(Objects.isNull(object1[5]) ? null : Integer.valueOf(object1[5].toString()));
                    outDistributeInfo.setSuccessRate(Objects.isNull(object1[6]) ? null : BigDecimal.valueOf(Double.valueOf(object1[6].toString())));
                    outDistributeInfo.setEndAmt(Objects.isNull(object1[4]) ? null : BigDecimal.valueOf(Double.valueOf(object1[4].toString())));
                    outDistributeInfo.setOutId(Objects.isNull(object1[2]) ? null : object1[2].toString());
                    outDistributeInfos.add(outDistributeInfo);
                }
            }
            List<OutDistributeInfo> collect = outDistributeInfos.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<OutDistributeInfo> page = new PageImpl(collect, pageable, outDistributeInfos.size());
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @GetMapping("/outCaseScore")
    @ApiOperation(value = "待委外案件评分(手动)", notes = "待委外案件评分(手动)")
    public ResponseEntity outCaseScore(@RequestParam(required = false) String companyCode,
                                       @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = null;
            try {
                user = getUserByToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME1, "OutSourcePool", "请选择公司")).body(null);
                }
            } else {
                companyCode = user.getCompanyCode();
            }
            //获取案件评分策略
            List<ScoreRule> rules = null;
            try {
                ResponseEntity<ScoreRules> responseEntity = restTemplate.getForEntity(Constants.SCOREL_SERVICE_URL.concat("getScoreRules").concat("?comanyCode=").concat(companyCode).concat("&strategyType=").concat(CaseStrategy.StrategyType.OUTS.getValue().toString()), ScoreRules.class);
                if (Objects.isNull(responseEntity) || !responseEntity.hasBody() || responseEntity.getBody().getScoreRules().size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "请先设置评分策略")).body(null);
                } else {
                    ScoreRules scoreRules = responseEntity.getBody();
                    rules = scoreRules.getScoreRules();
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "获取策略失败")).body(null);
            }
            StopWatch watch1 = new StopWatch();
            watch1.start();
            KieSession kieSession = null;
            try {
                try {
                    //生成动态规则
                    kieSession = createSorceRule(user.getCompanyCode(), CaseStrategy.StrategyType.OUTS.getValue(), rules);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Iterable<OutsourcePool> outsourcePools = outsourcePoolRepository.findAll(QOutsourcePool.outsourcePool.outStatus.eq(OutsourcePool.OutStatus.TO_OUTSIDE.getCode())
                    .and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode())));
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            outsourcePools.forEach(single -> outsourcePoolList.add(single));
            List<OutsourcePool> outsourcePoolScoreList = new ArrayList<>();
            ScoreNumbersModel scoreNumbersModel = new ScoreNumbersModel();
            scoreNumbersModel.setTotal(outsourcePoolList.size());
            if (outsourcePoolList.size() > 0) {
                for (OutsourcePool outsourcePool : outsourcePoolList) {
                    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                    int age = IdcardUtils.getAgeByIdCard(outsourcePool.getCaseInfo().getPersonalInfo().getIdCard());
                    scoreRuleModel.setAge(age);
                    scoreRuleModel.setOverDueAmount(outsourcePool.getCaseInfo().getOverdueAmount().doubleValue());
                    scoreRuleModel.setOverDueDays(outsourcePool.getCaseInfo().getOverdueDays());
                    if (Objects.nonNull(outsourcePool.getCaseInfo().getArea())) {
                        if (Objects.nonNull(outsourcePool.getCaseInfo().getArea().getParent())) {
                            scoreRuleModel.setProId(outsourcePool.getCaseInfo().getArea().getId());//省份id
                        }
                    }
                    Personal personal = personalRepository.findOne(outsourcePool.getCaseInfo().getPersonalInfo().getId());
                    if (Objects.nonNull(personal) && Objects.nonNull(personal.getPersonalJobs())) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }
                    kieSession.insert(scoreRuleModel);//插入
                    kieSession.fireAllRules();//执行规则
                    if (scoreRuleModel.getCupoScore() != 0) {
                        outsourcePoolScoreList.add(outsourcePool);
                        outsourcePool.getCaseInfo().setScore(new BigDecimal(scoreRuleModel.getCupoScore()));
                    }
                }
                kieSession.dispose();
                outsourcePoolRepository.save(outsourcePoolList);
                watch1.stop();
                log.info("耗时：" + watch1.getTotalTimeMillis());
                if (outsourcePoolScoreList.size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "没有符合评分的案件")).body(null);
                }
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分完成", "success")).body(scoreNumbersModel);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "案件评分为空")).body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "exportCaseInfoFollowRecord", "评分失败")).body(null);
        }
    }

    @GetMapping("/outCurrentCaseScore")
    @ApiOperation(value = "委外中案件评分(手动)", notes = "委外中案件评分(手动)")
    public ResponseEntity outCurrentCaseScore(@RequestParam @ApiParam(value = "批次号", required = true) String batchNumber,
                                              @RequestParam @ApiParam(value = "委外方名称", required = true) String outsName,
                                              @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = null;
            try {
                user = getUserByToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Objects.isNull(user.getCompanyCode())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME1, "OutSourcePool", "请选择公司")).body(null);
            }
            List<ScoreRule> rules = null;
            try {
                ResponseEntity<ScoreRules> responseEntity = restTemplate.getForEntity(Constants.SCOREL_SERVICE_URL.concat("getScoreRules").concat("?comanyCode=").concat(user.getCompanyCode()).concat("&strategyType=").concat(CaseStrategy.StrategyType.OUTS.getValue().toString()), ScoreRules.class);
                if (Objects.isNull(responseEntity) || !responseEntity.hasBody() || responseEntity.getBody().getScoreRules().size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "请先设置评分策略")).body(null);
                } else {
                    ScoreRules scoreRules = responseEntity.getBody();
                    rules = scoreRules.getScoreRules();
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "获取策略失败")).body(null);
            }

            StopWatch watch1 = new StopWatch();
            watch1.start();
            KieSession kieSession = null;
            try {
                try {
                    //生成动态规则
                    kieSession = createSorceRule(user.getCompanyCode(), CaseStrategy.StrategyType.OUTS.getValue(), rules);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Outsource outsource = outsourceRepository.findOne(QOutsource.outsource.outsName.eq(outsName).and(QOutsource.outsource.companyCode.eq(user.getCompanyCode()).and(QOutsource.outsource.flag.eq(0))));
            Iterable<OutsourcePool> outsourcePools = outsourcePoolRepository.findAll(QOutsourcePool.outsourcePool.outStatus.eq(OutsourcePool.OutStatus.OUTSIDING.getCode())
                    .and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode())).and(QOutsourcePool.outsourcePool.outBatch.eq(batchNumber)).and(QOutsourcePool.outsourcePool.outsource.eq(outsource))
                    .and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())));
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            outsourcePools.forEach(single -> outsourcePoolList.add(single));
            List<OutsourcePool> outsourcePoolScoreList = new ArrayList<>();
            ScoreNumbersModel scoreNumbersModel = new ScoreNumbersModel();
            scoreNumbersModel.setTotal(outsourcePoolList.size());
            if (outsourcePoolList.size() > 0) {
                for (OutsourcePool outsourcePool : outsourcePoolList) {
                    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                    int age = IdcardUtils.getAgeByIdCard(outsourcePool.getCaseInfo().getPersonalInfo().getIdCard());
                    scoreRuleModel.setAge(age);
                    scoreRuleModel.setOverDueAmount(outsourcePool.getCaseInfo().getOverdueAmount().doubleValue());
                    scoreRuleModel.setOverDueDays(outsourcePool.getCaseInfo().getOverdueDays());
                    if (Objects.nonNull(outsourcePool.getCaseInfo().getArea())) {
                        if (Objects.nonNull(outsourcePool.getCaseInfo().getArea().getParent())) {
                            scoreRuleModel.setProId(outsourcePool.getCaseInfo().getArea().getId());//省份id
                        }
                    }
                    Personal personal = personalRepository.findOne(outsourcePool.getCaseInfo().getPersonalInfo().getId());
                    if (Objects.nonNull(personal) && Objects.nonNull(personal.getPersonalJobs())) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }

                    kieSession.insert(scoreRuleModel);//插入
                    kieSession.fireAllRules();//执行规则
                    if (scoreRuleModel.getCupoScore() != 0) {
                        outsourcePoolScoreList.add(outsourcePool);
                        outsourcePool.getCaseInfo().setScore(new BigDecimal(scoreRuleModel.getCupoScore()));
                    }
                }
                kieSession.dispose();
                outsourcePoolRepository.save(outsourcePoolList);
                watch1.stop();
                log.info("耗时：" + watch1.getTotalTimeMillis());
                if (outsourcePoolScoreList.size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "没有符合评分的案件")).body(null);
                }
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分完成", "success")).body(scoreNumbersModel);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "failure", "案件评分为空")).body(null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "exportCaseInfoFollowRecord", "评分失败")).body(null);
        }
    }

    /**
     * 动态生成规则
     *
     * @return
     * @throws IOException
     * @throws
     */
    private KieSession createSorceRule(String comanyCode, Integer strategyType, List<ScoreRule> rules) {

        try {
            freemarker.template.Template scoreFormulaTemplate = freemarkerConfiguration.getTemplate("scoreFormula.ftl", "UTF-8");
            freemarker.template.Template scoreRuleTemplate = freemarkerConfiguration.getTemplate("scoreRule.ftl", "UTF-8");
            StringBuilder sb = new StringBuilder();
            if (Objects.nonNull(rules)) {
                for (ScoreRule rule : rules) {
                    for (int i = 0; i < rule.getFormulas().size(); i++) {
                        ScoreFormula scoreFormula = rule.getFormulas().get(i);
                        Map<String, String> map = new HashMap<>();
                        map.put("id", rule.getId());
                        map.put("index", String.valueOf(i));
                        map.put("strategy", scoreFormula.getStrategy());
                        map.put("score", String.valueOf(scoreFormula.getScore()));
                        map.put("weight", String.valueOf(rule.getWeight()));
                        sb.append(FreeMarkerTemplateUtils.processTemplateIntoString(scoreFormulaTemplate, map));
                    }
                }
            }
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            Map<String, String> map = new HashMap<>();
            map.put("allRules", sb.toString());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(scoreRuleTemplate, map);
            kfs.write("src/main/resources/simple.drl",
                    kieServices.getResources().newReaderResource(new StringReader(text)));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            Results results = kieBuilder.getResults();
            if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
                log.error(results.getMessages().toString());
                throw new IllegalStateException("策略生成错误");
            }
            KieContainer kieContainer =
                    kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
            KieSession kieSession = kieContainer.newKieSession();
            return kieSession;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("策略生成失败");
        }
    }

    /**
     * @Description 多条件查询回收案件
     */
    @GetMapping("/getReturnCaseByConditions")
    @ApiOperation(value = "多条件查询回收案件", notes = "多条件查询回收案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfoReturn>> getReturnCaseByConditions(@QuerydslPredicate(root = CaseInfoReturn.class) Predicate predicate,
                                                                          @ApiIgnore Pageable pageable,
                                                                          @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                          @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            QCaseInfoReturn qCaseInfoReturn = QCaseInfoReturn.caseInfoReturn;
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfoReturn.companyCode.eq(companyCode));
                }
            } else {
                builder.and(qCaseInfoReturn.companyCode.eq(user.getCompanyCode()));
            }
            builder.and(qCaseInfoReturn.source.eq(CaseInfo.CasePoolType.OUTER.getValue())); //委外
            // 按照回收时间排序
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "operatorTime"));
            Page<CaseInfoReturn> page = caseInfoReturnRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoReturn", "", "查询失败")).body(null);
        }
    }

    @PostMapping("/verificationApply")
    @ApiOperation(value = "核销申请", notes = "核销申请")
    public ResponseEntity verificationApply(@RequestBody VerificationApplyModel verificationApplyModel, @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        try {
            List<String> ids = verificationApplyModel.getIds();
            List<CaseInfoVerificationApply> verificationApplies = new ArrayList<>();
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("User", "", "获取不到登录人信息")).body(null);
            }
            for (String id : ids) {
                CaseInfoReturn caseInfoReturn = caseInfoReturnRepository.findOne(id);
                if (Objects.isNull(caseInfoReturn)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoReturn", "", "获取回收案件信息失败")).body(null);
                }
                CaseInfoVerificationApply caseInfoVerificationApply = new CaseInfoVerificationApply();
                caseInfoVerificationApply.setOperator(user.getRealName()); // 操作人
                caseInfoVerificationApply.setOperatorTime(ZWDateUtil.getNowDateTime()); // 操作时间
                caseInfoVerificationApply.setApplicant(user.getRealName()); // 申请人
                caseInfoVerificationApply.setApplicationDate(ZWDateUtil.getNowDateTime()); // 申请日期
                caseInfoVerificationApply.setApplicationReason(verificationApplyModel.getReason()); // 申请理由
                caseInfoVerificationApply.setApprovalStatus(CaseInfoVerificationApply.ApprovalStatus.approval_pending.getValue()); // 申请状态：审批待通过
                CaseInfo caseInfo = caseInfoReturn.getCaseId();
                if (Objects.nonNull(caseInfo)) {
                    caseInfoVerificationApply.setCaseId(caseInfo.getId()); // 案件Id
                    caseInfoVerificationApply.setCaseNumber(caseInfo.getCaseNumber()); // 案件编号
                    caseInfoVerificationApply.setBatchNumber(caseInfo.getBatchNumber()); // 批次号
                    caseInfoVerificationApply.setOverdueAmount(caseInfo.getOverdueAmount()); // 逾期金额
                    caseInfoVerificationApply.setOverdueDays(caseInfo.getOverdueDays()); // 逾期天数
                    caseInfoVerificationApply.setPayStatus(caseInfo.getPayStatus()); // 还款状态
                    caseInfoVerificationApply.setContractNumber(caseInfo.getContractNumber()); // 合同编号
                    caseInfoVerificationApply.setContractAmount(caseInfo.getContractAmount()); // 合同金额
                    caseInfoVerificationApply.setOverdueCapital(caseInfo.getOverdueCapital()); // 逾期本金
                    caseInfoVerificationApply.setOverdueDelayFine(caseInfo.getOverdueDelayFine()); // 逾期滞纳金
                    caseInfoVerificationApply.setOverdueFine(caseInfo.getOverdueFine()); // 逾期罚息
                    caseInfoVerificationApply.setOverdueInterest(caseInfo.getOverdueInterest()); // 逾期利息
                    caseInfoVerificationApply.setHasPayAmount(caseInfo.getHasPayAmount()); // 已还款金额
                    caseInfoVerificationApply.setHasPayPeriods(caseInfo.getHasPayPeriods()); // 已还款期数
                    caseInfoVerificationApply.setLatelyPayAmount(caseInfo.getLatelyPayAmount()); // 最近还款金额
                    caseInfoVerificationApply.setLatelyPayDate(caseInfo.getLatelyPayDate()); // 最近还款日期
                    caseInfoVerificationApply.setPeriods(caseInfo.getPeriods()); // 还款期数
                    caseInfoVerificationApply.setCompanyCode(caseInfo.getCompanyCode());
                    caseInfoVerificationApply.setCommissionRate(caseInfo.getCommissionRate()); // 佣金比例
                    if (Objects.nonNull(caseInfo.getArea())) {
                        caseInfoVerificationApply.setCity(caseInfo.getArea().getId()); // 城市
                        if (Objects.nonNull(caseInfo.getArea().getParent())) {
                            caseInfoVerificationApply.setProvince(caseInfo.getArea().getParent().getId()); // 省份
                        }
                    }
                    if (Objects.nonNull(caseInfo.getPrincipalId())) {
                        caseInfoVerificationApply.setPrincipalName(caseInfo.getPrincipalId().getName()); // 委托方名称
                    }
                    if (Objects.nonNull(caseInfo.getPersonalInfo())) {
                        caseInfoVerificationApply.setPersonalName(caseInfo.getPersonalInfo().getName()); // 客户名称
                        caseInfoVerificationApply.setMobileNo(caseInfo.getPersonalInfo().getMobileNo()); // 电话号
                        caseInfoVerificationApply.setIdCard(caseInfo.getPersonalInfo().getIdCard()); // 身份证号
                    }
                }
                verificationApplies.add(caseInfoVerificationApply);
            }
            caseInfoVerificationApplyRepository.save(verificationApplies);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("核销申请失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @PostMapping("/changeToBeAssigned")
    @ApiOperation(value = "移入待分配", notes = "移入待分配")
    public ResponseEntity changeToBeAssigned(@RequestBody VerificationApplyModel verificationApplyModel, @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        try {
            List<String> ids = verificationApplyModel.getIds();
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("User", "", "获取不到登录人信息")).body(null);
            }
            for (String id : ids) {
                CaseInfoReturn caseInfoReturn = caseInfoReturnRepository.findOne(id);
                if (Objects.isNull(caseInfoReturn)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoReturn", "", "获取回收案件信息失败")).body(null);
                }
                CaseInfo caseInfo = caseInfoReturn.getCaseId();
                caseInfo.setRecoverWay(CaseInfo.RecoverWay.MANUAL.getValue());//默认手动回收
                caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue());//未回收
                caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime());//流入时间
                OutsourcePool outsourcePool = new OutsourcePool();
                outsourcePool.setCaseInfo(caseInfo);
                outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());
                outsourcePoolRepository.save(outsourcePool);
                caseInfoReturnRepository.delete(id);//删除原回收案件
            }
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("核销申请失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    /**
     * @Description : 查询委外案件
     */
    @GetMapping("/query")
    @ApiOperation(value = "查询待分配委外案件", notes = "查询待分配委外案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OutsourcePool>> query(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                     @QuerydslPredicate(root = OutsourcePool.class) Predicate predicate,
                                                     @ApiIgnore Pageable pageable,
                                                     @RequestHeader(value = "X-UserToken") String token,
                                                     @RequestParam @ApiParam(value = "tab页标识 1待分配;2已结案", required = true) Integer flag) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }

            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME1, "OutSourcePool", "请选择公司")).body(null);
                }
            } else {
                builder.and(qOutsourcePool.caseInfo.companyCode.eq(user.getCompanyCode())); //限制公司code码
            }
            builder.and(qOutsourcePool.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.OUTER.getValue()));//委外类型
            if (1 == flag) {
                builder.and(qOutsourcePool.outStatus.eq(OutsourcePool.OutStatus.TO_OUTSIDE.getCode())); //待分配
            } else if (2 == flag) {
                builder.and(qOutsourcePool.outStatus.eq(OutsourcePool.OutStatus.OUTSIDE_OVER.getCode())); //已结案
            }
            Page<OutsourcePool> page = outsourcePoolRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @PostMapping("/recallOutCase")
    @ApiOperation(value = "撤回", notes = "撤回")
    public ResponseEntity recallOutCase(@RequestBody VerificationApplyModel verificationApplyModel, @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        try {
            List<String> ids = verificationApplyModel.getIds();
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("User", "", "获取不到登录人信息")).body(null);
            }
            List<OutsourcePool> outsourcePools = new ArrayList<>();
            for (String id : ids) {
                OutsourcePool outsourcePool = outsourcePoolRepository.findOne(id);
                if (Objects.isNull(outsourcePool)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePool", "", "案件查询异常")).body(null);
                }
                Date outTime = outsourcePool.getOutTime();
                if (Objects.isNull(outTime)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePool", "", "获取委外时间异常")).body(null);
                }
                Integer days = ZWDateUtil.getBetween(outTime, ZWDateUtil.getNowDateTime(), ChronoUnit.DAYS);
                if (days >= 3) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePool", "", "委外已超过3天，不能撤回")).body(null);
                }
                if (Objects.nonNull(outsourcePool.getOutoperationStatus())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePool", "", "案件已操作，不能撤回")).body(null);
                }
                outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());
                outsourcePool.setOutsource(null);
                outsourcePool.setOperateTime(ZWDateUtil.getNowDateTime());
                outsourcePool.setOperator(user.getUserName());
                outsourcePool.setOutBatch(null);
                outsourcePool.setOutBackAmt(BigDecimal.ZERO);
                outsourcePool.setOutTime(null);
                outsourcePool.setOutoperationStatus(null);
                outsourcePools.add(outsourcePool);
            }
            outsourcePoolRepository.save(outsourcePools);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("核销申请失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @PostMapping("/closeOutsourcePool")
    @ApiOperation(value = "委外结案", notes = "委外结案")
    public ResponseEntity<List<OutsourcePool>> closeOutsourcePool(@RequestBody OutCaseIdList outCaseIdList, @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        try {
            List<String> outCaseIds = outCaseIdList.getOutCaseIds();
            List<OutsourcePool> outsourcePools = new ArrayList<>();
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            for (String outId : outCaseIds) {
                OutsourcePool outsourcePool = outsourcePoolRepository.findOne(outId);
                if (OutsourcePool.OutStatus.OUTSIDE_OVER.getCode().equals(outsourcePool.getOutStatus())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("已委外结案案件不能再结案", "", "已委外结案案件不能再结案")).body(null);
                }
                outsourcePool.setOutStatus(OutsourcePool.OutStatus.OUTSIDE_OVER.getCode());//状态改为委外结束
                outsourcePool.setOperator(user.getUserName());//委外结案人
                outsourcePool.setEndOutsourceTime(ZWDateUtil.getNowDateTime());//委外结案时间
                outsourcePools.add(outsourcePool);
            }
            outsourcePools = outsourcePoolRepository.save(outsourcePools);
            return ResponseEntity.ok().body(outsourcePools);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("委外结案失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @PostMapping("/backOutsourcePool")
    @ApiOperation(value = "退案", notes = "退案")
    public ResponseEntity<List<OutsourcePool>> backOutsourcePool(@RequestBody OutCaseIdList outCaseIdList, @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        try {
            List<String> outCaseIds = outCaseIdList.getOutCaseIds();
            List<OutsourcePool> outsourcePools = new ArrayList<>();
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            for (String outId : outCaseIds) {
                OutsourcePool outsourcePool = outsourcePoolRepository.findOne(outId);
                outsourcePool.setOutsource(null);
                outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());//状态改为待委外
                outsourcePool.setOperator(user.getUserName());//委外退案人
                outsourcePool.setOperateTime(ZWDateUtil.getNowDateTime());//委外退案时间
                outsourcePools.add(outsourcePool);
            }
            outsourcePools = outsourcePoolRepository.save(outsourcePools);
            return ResponseEntity.ok().body(outsourcePools);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("委外退案失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    @RequestMapping(value = "/exportAccOutsourcePool", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "委外案件导出", notes = "委外案件导出")
    //多条件查询领取案件
    public ResponseEntity<String> getAccOutsourcePoolByToken(@RequestBody OurBatchList ourBatchList,
                                                             @RequestHeader(value = "X-UserToken") String token) throws URISyntaxException {
        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(ourBatchList.getCompanyCode())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME1, "OutSourcePool", "请选择公司")).body(null);
                }
                builder.and(qOutsourcePool.caseInfo.companyCode.eq(ourBatchList.getCompanyCode()));
            } else {
                builder.and(qOutsourcePool.caseInfo.companyCode.eq(user.getCompanyCode())); //限制公司code码
            }
            List<String> list = ourBatchList.getOurBatchList();
            if (!list.isEmpty()) {
                builder.and(qOutsourcePool.outBatch.in(list));
            }
            List<OutsourcePool> outsourcePools = (List<OutsourcePool>) outsourcePoolRepository.findAll(builder);
            List<AccOutsourcePoolModel> accOutsourcePoolModels = new ArrayList<>();
            for (OutsourcePool outsourcePool : outsourcePools) {
                AccOutsourcePoolModel accOutsourcePoolModel = new AccOutsourcePoolModel();
                CaseInfo caseInfo = outsourcePool.getCaseInfo();
                accOutsourcePoolModel.setCaseCode(caseInfo.getCaseNumber());
                if (Objects.nonNull(caseInfo)) {
                    if (Objects.nonNull(caseInfo.getCommissionRate())) {
                        accOutsourcePoolModel.setCommissionRate(caseInfo.getCommissionRate().toString());
                    }
                    if (Objects.nonNull(caseInfo.getContractAmount())) {
                        accOutsourcePoolModel.setContractAmount(caseInfo.getContractAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    if (Objects.nonNull(caseInfo.getPerPayAmount())) {
                        accOutsourcePoolModel.setCurrentAmount(caseInfo.getPerPayAmount().toString());//每期还款金额
                    }
                    accOutsourcePoolModel.setCurrentPayDate(caseInfo.getPerDueDate());//每期还款日
                    if (Objects.nonNull(caseInfo.getPersonalInfo())) {
                        accOutsourcePoolModel.setCustName(caseInfo.getPersonalInfo().getName());
                    }
                    Set<PersonalJob> set = caseInfo.getPersonalInfo().getPersonalJobs();
                    PersonalJob personalJob = null;
                    if (!set.isEmpty()) {
                        personalJob = set.iterator().next();
                    }

                    if (Objects.nonNull(personalJob)) {
                        accOutsourcePoolModel.setEmployerAddress(personalJob.getAddress());
                        accOutsourcePoolModel.setEmployerName(personalJob.getCompanyName());
                        accOutsourcePoolModel.setEmployerPhone(personalJob.getPhone());
                    }
                    if (Objects.nonNull(caseInfo.getHasPayAmount())) {
                        accOutsourcePoolModel.setHasPayAmount(caseInfo.getHasPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    if (Objects.nonNull(caseInfo.getHasPayPeriods())) {
                        accOutsourcePoolModel.setHasPayPeriods(caseInfo.getHasPayPeriods().toString());
                    }
                    accOutsourcePoolModel.setHomeAddress(caseInfo.getPersonalInfo().getLocalHomeAddress());
                    accOutsourcePoolModel.setIdCardNumber(caseInfo.getPersonalInfo().getIdCard());
                    if (Objects.nonNull(caseInfo.getLatelyPayAmount())) {
                        accOutsourcePoolModel.setLastPayAmount(caseInfo.getLatelyPayAmount().toString());//最近还款金额
                    }
                    if (Objects.nonNull(caseInfo.getLatelyPayDate())) {
                        accOutsourcePoolModel.setLastPayDate(ZWDateUtil.fomratterDate(caseInfo.getLatelyPayDate(), "yyyy-MM-dd"));//最近还款日期
                    }
                    if (Objects.nonNull(caseInfo.getOverdueAmount())) {
                        accOutsourcePoolModel.setOverDueAmount(caseInfo.getOverdueAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    if (Objects.nonNull(caseInfo.getOverdueCapital())) {
                        accOutsourcePoolModel.setOverDueCapital(caseInfo.getOverdueCapital().toString());//逾期本金
                    }
                    if (Objects.nonNull(caseInfo.getOverdueDays())) {
                        accOutsourcePoolModel.setOverDueDays(caseInfo.getOverdueDays().toString());
                    }
                    if (Objects.nonNull(caseInfo.getOverdueFine())) {
                        accOutsourcePoolModel.setOverDueDisincentive(caseInfo.getOverdueFine().toString());//逾期罚息
                    }
                    if (Objects.nonNull(caseInfo.getOverdueDelayFine())) {
                        accOutsourcePoolModel.setOverDueFine(caseInfo.getOverdueDelayFine().toString());//逾期滞纳金
                    }
                    if (Objects.nonNull(caseInfo.getOverdueInterest())) {
                        accOutsourcePoolModel.setOverDueInterest(caseInfo.getOverdueInterest().toString());//逾期利息
                    }
                    if (Objects.nonNull(caseInfo.getOverduePeriods())) {
                        accOutsourcePoolModel.setOverDuePeriods(caseInfo.getOverduePeriods().toString());//逾期期数
                    }
                    if (Objects.nonNull(caseInfo.getPersonalInfo())) {
                        accOutsourcePoolModel.setPhoneNumber(caseInfo.getPersonalInfo().getMobileNo());
                    }
                    if (Objects.nonNull(caseInfo.getProduct())) {
                        if (Objects.nonNull(caseInfo.getProduct().getProductSeries())) {
                            accOutsourcePoolModel.setProductSeries(caseInfo.getProduct().getProductSeries().getSeriesName());
                        }
                    }
                    if (Objects.nonNull(caseInfo.getProduct())) {
                        accOutsourcePoolModel.setProductName(caseInfo.getProduct().getProductName());
                    }
                    if (Objects.nonNull(caseInfo.getPeriods())) {
                        accOutsourcePoolModel.setPayPeriods(caseInfo.getPeriods().toString());//还款总期数
                    }
                }
                accOutsourcePoolModels.add(accOutsourcePoolModel);
            }
            if (null == accOutsourcePoolModels || accOutsourcePoolModels.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("没有委外数据", "", "没有委外数据")).body(null);
            }

            String[] titleList = {"案件编号", "客户姓名", "身份证号", "手机号码", "产品系列", "产品名称", "合同金额（元）", "逾期总金额（元）", "逾期本金（元）", "逾期利息（元）", "逾期罚息（元）", "逾期滞纳金（元）", "还款期数", "逾期期数", "逾期天数", "已还款金额（元）", "已还款期数", "最近还款日期", "最近还款金额（元）", "家庭住址", "工作单位名称", "工作单位地址", "工作单位电话", "佣金比例（%）", "每期还款日", "每期还款金额（元）"};
            String[] proNames = {"caseCode", "custName", "idCardNumber", "phoneNumber", "productSeries", "productName", "contractAmount", "overDueAmount", "overDueCapital", "overDueInterest", "overDueDisincentive", "overDueFine", "payPeriods", "overDuePeriods", "overDueDays", "hasPayAmount", "hasPayPeriods", "lastPayDate", "lastPayAmount", "homeAddress", "employerName", "employerAddress", "employerPhone", "commissionRate", "currentPayDate", "currentAmount"};
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            ExcelUtil.createExcel(workbook, sheet, accOutsourcePoolModels, titleList, proNames, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "催收员业绩报表.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            String body = url.getBody();
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("上传服务器失败", "", "上传服务器失败")).body(null);
            } else {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "OutsourcePoolController")).body(body);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("导出失败", ENTITY_NAME1, e.getMessage())).body(null);
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 删除文件
            if (file != null) {
                file.deleteOnExit();
            }
        }
    }

    /**
     * @Description 查询可委外案件
     */
    @GetMapping("/getAllOutCase")
    @ApiOperation(value = "查询可委外案件", notes = "查询可委外案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllOutCase(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                        @RequestHeader(value = "X-UserToken") String token) throws Exception {
        log.debug("REST request to get all case");
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()); //待分配
        list.add(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue()); //待催收
        list.add(CaseInfo.CollectionStatus.COLLECTIONING.getValue()); //催收中
        list.add(CaseInfo.CollectionStatus.OVER_PAYING.getValue()); //逾期还款中
        list.add(CaseInfo.CollectionStatus.EARLY_PAYING.getValue()); //提前结清还款中
        list.add(CaseInfo.CollectionStatus.PART_REPAID.getValue()); //部分已还款
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "")).body(null);
                }
                builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            builder.and(QCaseInfo.caseInfo.collectionStatus.in(list)); //不查询已结案、已还款案件
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/outsourcePoolController/getAllOutCase");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", "caseInfo", e.getMessage())).body(null);
        }
    }

    @GetMapping("/loadTemplate")
    @ResponseBody
    @ApiOperation(value = "下载模板", notes = "下载模板")
    public ResponseEntity<String> loadTemplate(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                               @RequestParam @ApiParam(value = "下载模板的类型", required = true) Integer type,
                                               @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userBackcashPlan", "请选择公司")).body(null);
                }
            } else {
                companyCode = user.getCompanyCode();
            }
            QSysParam qSysParam = QSysParam.sysParam;
            SysParam sysParam = null;
            if (type == 0) {
                sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                        .and(qSysParam.code.eq(Constants.SMS_OUTCASE_ACCOUNT_URL_CODE))
                        .and(qSysParam.type.eq(Constants.SMS_OUTCASE_ACCOUNT_URL_TYPE)));
            } else {
                sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                        .and(qSysParam.code.eq(Constants.SMS_OUTCASE_FOLLOWUP_URL_CODE))
                        .and(qSysParam.type.eq(Constants.SMS_OUTCASE_FOLLOWUP_URL_TYPE)));
            }

            if (Objects.isNull(sysParam)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("该模板不存在", "", "该模板不存在")).body(null);
            }
            return ResponseEntity.ok().body(sysParam.getValue());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("下载失败", "", e.getMessage())).body(null);
        }
    }

    @GetMapping("/importFinancData")
    @ResponseBody
    @ApiOperation(value = "账目导入/委外跟进记录导入", notes = "账目导入/委外跟进记录导入")
    public ResponseEntity<List<CellError>> importExcelData(@RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                           @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                           @RequestParam(required = false) @ApiParam(value = "文件ID") String fileId,
                                                           @RequestParam(required = false) @ApiParam(value = "备注") String fienRemark,
                                                           @RequestParam @ApiParam(value = "导入类型", required = true) Integer type) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            List<CellError> errorList = accFinanceEntryService.importAccFinanceData(fileId, type, user, fienRemark);
            if (errorList.isEmpty()) {
                return ResponseEntity.ok().body(null);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(errorList.get(0).getErrorMsg(), "", errorList.get(0).getErrorMsg())).body(errorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("导入失败", "", e.getMessage())).body(null);
        }
    }


    @PostMapping("/affirmReconciliation")
    @ResponseBody
    @ApiOperation(value = "财务数据确认操作", notes = "财务数据确认操作")
    public ResponseEntity<List> affirmReconciliation(@RequestBody FienCasenums fienCasenums,
                                                     @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        try {
            User user = getUserByToken(token);
            if (fienCasenums.getIdList().isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("没有可确认的数据", "", "没有可确认的数据")).body(null);
            }
            List<AccFinanceEntry> accFinanceEntryList = new ArrayList<>();
            List<CaseInfo> caseInfoList = new ArrayList<>();  //在委外池中能匹配上的委外案件
            List<AccFinanceEntry> unableMatchList = new ArrayList<>();  //在委外池中没有匹配的财务数据
            List<OutsourcePool> outsourcePools = new ArrayList<>();  //在委外池中没有匹配的财务数据
            List<AccFinanceEntry> accFinanceEntrieAll = accFinanceEntryRepository.findAll(fienCasenums.getIdList());
            List<OutBackSource> outBackSourceList = new ArrayList<>();
            for (AccFinanceEntry financeEntryCase : accFinanceEntrieAll) {
                String caseNum = financeEntryCase.getFienCasenum();
                List<CaseInfo> caseInfos = caseInfoRepository.findByCaseNumber(caseNum);
                if (Objects.nonNull(caseInfos) && !caseInfos.isEmpty()) {
                    //对委外客户池已还款金额做累加
                    for (CaseInfo caseInfo : caseInfos) {
                        if (Objects.isNull(caseInfo.getRealPayAmount())) {
                            caseInfo.setRealPayAmount(BigDecimal.ZERO);
                        }
                        caseInfo.setRealPayAmount(caseInfo.getRealPayAmount().add(financeEntryCase.getFienPayback()));
                        caseInfoList.add(caseInfo);
                    }
                } else {
                    unableMatchList.add(financeEntryCase);   //未有匹配委外案件
                }
                //临时表中的数据状态为已确认。
                financeEntryCase.setFienStatus(Status.Disable.getValue());
                accFinanceEntryList.add(financeEntryCase);
            }
            //同步更新临时表中的数据状态为已确认
            accFinanceEntryRepository.save(accFinanceEntryList);
            //更新委外的案件池里的已还款金额
            outsourcePoolRepository.save(outsourcePools);
            outBackSourceRepository.save(outBackSourceList);
            return ResponseEntity.ok().body(unableMatchList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("确认失败", "", e.getMessage())).body(null);
        }
    }

    @GetMapping("/findFinanceData")
    @ResponseBody
    @ApiOperation(value = "查询未确认的数据", notes = "查询未确认的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<AccFinanceEntry>> findFinanceData(@ApiIgnore Pageable pageable,
                                                                 @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                 @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            AccFinanceEntry accFinanceEntry = new AccFinanceEntry();
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("AccFinanceEntry", "AccFinanceEntry", "")).body(null);
                }
                accFinanceEntry.setCompanyCode(companyCode);
            } else {
                accFinanceEntry.setCompanyCode(user.getCompanyCode()); //限制公司code码
            }
            accFinanceEntry.setFienStatus(Status.Enable.getValue());
            accFinanceEntry.setFienCount(null);
            accFinanceEntry.setFienPayback(null);
            ExampleMatcher matcher = ExampleMatcher.matching();
            org.springframework.data.domain.Example<AccFinanceEntry> example = org.springframework.data.domain.Example.of(accFinanceEntry, matcher);
            Page<AccFinanceEntry> page = accFinanceEntryRepository.findAll(example, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", "", e.getMessage())).body(null);
        }
    }

    @GetMapping("/findConfirmFinanceData")
    @ResponseBody
    @ApiOperation(value = "查询已确认的数据", notes = "查询已确认的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<AccFinanceEntry>> findConfirmFinanceData(@ApiIgnore Pageable pageable,
                                                                        @RequestParam(required = false) @ApiParam(value = "委外方") String outsName,
                                                                        @RequestParam(required = false) @ApiParam(value = "批次号") String outbatch,
                                                                        @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                        @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            AccFinanceEntry accFinanceEntry = new AccFinanceEntry();
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    accFinanceEntry.setCompanyCode(companyCode);
                }
            } else {
                accFinanceEntry.setCompanyCode(user.getCompanyCode()); //限制公司code码
            }
            accFinanceEntry.setFienStatus(Status.Disable.getValue());
            accFinanceEntry.setFienCount(null);
            accFinanceEntry.setFienPayback(null);
            if (Objects.nonNull(outsName)) {
                accFinanceEntry.setFienFgname(outsName);
            }
            if (Objects.nonNull(outbatch)) {
                accFinanceEntry.setFienBatchnum(outbatch);
            }
            ExampleMatcher matcher = ExampleMatcher.matching();
            org.springframework.data.domain.Example<AccFinanceEntry> example = org.springframework.data.domain.Example.of(accFinanceEntry, matcher);
            Page<AccFinanceEntry> page = accFinanceEntryRepository.findAll(example, pageable);
            // 共债案件合并
            List<AccFinanceEntry> list = accFinanceEntryRepository.findAll(example);



            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", "", e.getMessage())).body(null);
        }
    }


    @PostMapping("/deleteFinanceData")
    @ResponseBody
    @ApiOperation(value = "财务数据删除操作", notes = "财务数据删除操作")
    public ResponseEntity deleteFinanceData(@RequestBody FienCasenums fienCasenums) {
        try {
            if (fienCasenums.getIdList().isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("没有可删除的数据", "", "没有可删除的数据")).body(null);
            }
            for (String id : fienCasenums.getIdList()) {
                accFinanceEntryRepository.delete(id);
            }
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("删除失败", "", e.getMessage())).body(null);
        }
    }


    @RequestMapping(value = "/exportOutsideFinanceData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "导出委外财务对账数据", notes = "导出委外财务对账数据")
    public ResponseEntity<String> exportOutsideFinanceData(@RequestParam(value = "outbatch", required = false) @ApiParam("批次号") String outbatch,
                                                           @RequestParam(value = "outsName", required = false) @ApiParam("委外方") String outsName,
                                                           @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                           @RequestHeader(value = "X-UserToken") String token) {
        HSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        try {
            List<AccFinanceEntry> accOutsourcePoolList;
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            try {
                QAccFinanceEntry qAccFinanceEntry = QAccFinanceEntry.accFinanceEntry;
                BooleanBuilder builder = new BooleanBuilder();
                if (Objects.isNull(user.getCompanyCode())) {
                    if (Objects.isNull(companyCode)) {
                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME1, "OutsourcePool", "")).body(null);
                    }
                    builder.and(qAccFinanceEntry.companyCode.eq(companyCode));
                } else {
                    builder.and(qAccFinanceEntry.companyCode.eq(user.getCompanyCode())); //限制公司code码
                }
                if (Objects.nonNull(outbatch)) {
                    builder.and(qAccFinanceEntry.fienBatchnum.eq(outbatch));

                }
                if (Objects.nonNull(outsName)) {
                    builder.and(qAccFinanceEntry.fienFgname.like("%" + outsName + "%"));
                }
                builder.and(qAccFinanceEntry.fienStatus.eq(1));
                accOutsourcePoolList = (List<AccFinanceEntry>) accFinanceEntryRepository.findAll(builder);
            } catch (Exception e) {
                e.getStackTrace();
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询委外案件失败", "", e.getMessage())).body(null);
            }
            // 按照条件得到的财务数据为空时不允许导出
            if (accOutsourcePoolList.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("要导出的财务数据为空", "", "要导出的财务数据为空")).body(null);
            }
            // 将需要的数据获取到按照导出的模板存放在List中
            List<AccOutsideFinExportModel> accOutsideList = new ArrayList<>();
            for (int i = 0; i < accOutsourcePoolList.size(); i++) {
                AccFinanceEntry aop = accOutsourcePoolList.get(i);
                AccOutsideFinExportModel expm = new AccOutsideFinExportModel();
                expm.setOupoOutbatch(checkValueIsNull(aop.getFienBatchnum())); // 委外批次号
                expm.setOupoCasenum(checkValueIsNull(aop.getFienCasenum())); // 案件编号
                expm.setCustName(checkValueIsNull(aop.getFienCustname()));  // 客户名称
                expm.setOupoIdcard(checkValueIsNull(aop.getFienIdcard()));  // 身份证号
//                expm.setOupoStatus(checkOupoStatus(aop.getOutStatus())); // 委外状态
                expm.setOupoAmt(checkValueIsNull(aop.getFienCount()));  // 案件金额
                expm.setOupoPaynum(checkValueIsNull(aop.getFienPayback())); // 已还款金额
                expm.setOutsName(aop.getFienFgname());  // 委外方名称
                accOutsideList.add(expm);
            }

            // 将存放的数据写入Excel
            String[] titleList = {"案件编号", "客户姓名", "客户身份证号", "委外方", "案件金额", "已还款金额"};
            String[] proNames = {"oupoCasenum", "custName", "oupoIdcard", "outsName", "oupoAmt", "oupoPaynum"};
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sheet1");
            out = new ByteArrayOutputStream();
            ExcelUtil.createExcel(workbook, sheet, accOutsideList, titleList, proNames, 0, 0);
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "财务数据对账.xls");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("上传文件服务器失败", "", "上传文件服务器失败")).body(null);
            } else {
                return ResponseEntity.ok().body(url.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("上传文件服务器失败", "", e.getMessage())).body(null);
        } finally {
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 删除文件
            if (file != null) {
                file.delete();
            }
        }
    }

    /**
     * 检查值，为空时转为空字符串，不为空统一转为字符串
     */
    private String checkValueIsNull(Object obj) {
        if (Objects.nonNull(obj)) {
            return String.valueOf(obj.equals("null") ? "" : obj);
        } else {
            return null;
        }
    }

    /**
     * 将接受到的数字转换成相应的字符串
     */
    private String checkOupoStatus(Object obj) {
        if (Objects.nonNull(obj)) {
            if (Objects.equals(OutsourcePool.OutStatus.TO_OUTSIDE.getCode(), obj)) {
                return "待委外";
            } else if (Objects.equals(OutsourcePool.OutStatus.OUTSIDING.getCode(), obj)) {
                return "委外中";
            } else if (Objects.equals(OutsourcePool.OutStatus.OUTSIDE_EXPIRE.getCode(), obj)) {
                return "委外到期";
            } else {
                return "委外结束";
            }
        } else {
            return null;
        }
    }

    @GetMapping("/getOutRecored")
    @ApiOperation(value = "查询委外记录", notes = "查询委外记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OutsourceRecord>> getAccOutsourceOrder(@RequestParam(required = false) @ApiParam(value = "委外方") String outsName,
                                                                      @RequestParam(required = false) @ApiParam(value = "开始时间") String startDate,
                                                                      @RequestParam(required = false) @ApiParam(value = "结束时间") String endDate,
                                                                      @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                      @RequestHeader(value = "X-UserToken") String token,
                                                                      Pageable pageable) throws URISyntaxException {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            if (StringUtils.isNotBlank(startDate)) {
                startDate = startDate + " 00:00:00";
            }
            if (StringUtils.isNotBlank(endDate)) {
                endDate = endDate + " 23:59:59";
            }
            Date minTime = ZWDateUtil.getUtilDate(startDate, "yyyy-MM-dd HH:mm:ss");
            Date maxTime = ZWDateUtil.getUtilDate(endDate, "yyyy-MM-dd HH:mm:ss");
            QOutsourceRecord qOutsourceRecord = QOutsourceRecord.outsourceRecord;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(companyCode)) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourceRecord", "OutsourceRecord", "")).body(null);
                }
                builder.and(qOutsourceRecord.caseInfo.companyCode.eq(companyCode));
            } else {
                builder.and(qOutsourceRecord.caseInfo.companyCode.eq(user.getCompanyCode())); //限制公司code码
            }
            if (Objects.nonNull(startDate)) {
                builder.and(qOutsourceRecord.createTime.gt(minTime));
            }
            if (Objects.nonNull(endDate)) {
                builder.and(qOutsourceRecord.createTime.lt(maxTime));
            }
            if (Objects.nonNull(outsName)) {
                builder.and(qOutsourceRecord.outsource.outsName.eq(outsName));
            }
            Page<OutsourceRecord> page = outsourceRecordRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("查询失败", "", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 按批次号查看委外案件详情
     * <p>
     * Created by huyanmin at 2017/09/26
     */
    @GetMapping("/getOutSourceCaseByBatchnum")
    @ApiOperation(value = "按批次号查看委外案件详情", notes = "按批次号查看委外案件详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OutsourcePoolBatchModel>> getOutSourceCaseByBatchnum(@RequestParam(required = false) @ApiParam(value = "批次号") String batchNumber,
                                                                                    @RequestParam(required = false) @ApiParam(value = "委外方名称") String outsName,
                                                                                    @RequestParam(required = false) @ApiParam(value = "公司Code码") String companyCode,
                                                                                    @RequestParam(required = false) @ApiParam(value = "客户姓名") String personalName,
                                                                                    @RequestParam(required = false) @ApiParam(value = "客户手机号") String mobileNo,
                                                                                    @QuerydslPredicate(root = OutsourcePool.class) Predicate predicate,
                                                                                    @ApiIgnore Pageable pageable,
                                                                                    @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request get outsource case by batch number");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "user do not log in", e.getMessage())).body(null);
        }
        try {
            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.nonNull(user.getCompanyCode())) {
                builder.and(qOutsourcePool.companyCode.eq(user.getCompanyCode()));
            }
            if (Objects.nonNull(batchNumber)) {
                builder.and(qOutsourcePool.outBatch.eq(batchNumber));
            }
            if (Objects.nonNull(outsName)) {
                Outsource outsource = outsourceRepository.findOne(QOutsource.outsource.outsName.eq(outsName).and(QOutsource.outsource.companyCode.eq(user.getCompanyCode())).and(QOutsource.outsource.flag.eq(0)));
                builder.and(qOutsourcePool.outsource.id.eq(outsource.getId()));
            }
            if(Objects.nonNull(personalName)){
                builder.and(qOutsourcePool.caseInfo.personalInfo.name.eq(personalName));
            }
            if(Objects.nonNull(mobileNo)){
                builder.and(qOutsourcePool.caseInfo.personalInfo.mobileNo.eq(mobileNo));
            }
            builder.and(qOutsourcePool.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.OUTER.getValue()));
            builder.and(qOutsourcePool.outStatus.eq(OutsourcePool.OutStatus.OUTSIDING.getCode()));
            builder.and(qOutsourcePool.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            Iterator<OutsourcePool> pageIterator = outsourcePoolRepository.findAll(builder).iterator();
            List<OutsourcePoolBatchModel> list = new ArrayList<>();
            while (pageIterator.hasNext()){
                OutsourcePool outsourcePool = pageIterator.next();
                OutsourcePoolBatchModel outsourcePoolBatchModel = new OutsourcePoolBatchModel();
                outsourcePoolBatchModel.setCaseId(outsourcePool.getCaseInfo().getId());
                outsourcePoolBatchModel.setCaseNumber(outsourcePool.getCaseInfo().getCaseNumber());
                outsourcePoolBatchModel.setLeaveCaseFlag(outsourcePool.getCaseInfo().getLeaveCaseFlag());
                outsourcePoolBatchModel.setFollowupTime(outsourcePool.getCaseInfo().getFollowupTime());
                outsourcePoolBatchModel.setHoldDays(outsourcePool.getCaseInfo().getHoldDays());
                outsourcePoolBatchModel.setLeftDays(outsourcePool.getCaseInfo().getLeftDays());
                outsourcePoolBatchModel.setCompanyCode(outsourcePool.getCaseInfo().getCompanyCode());
                outsourcePoolBatchModel.setOverdueDays(outsourcePool.getCaseInfo().getOverdueDays());
                outsourcePoolBatchModel.setCollectionStatus(outsourcePool.getCaseInfo().getCollectionStatus());
                outsourcePoolBatchModel.setLoanInvoiceNumber(outsourcePool.getCaseInfo().getLoanInvoiceNumber());
                outsourcePoolBatchModel.setAccountBalance(outsourcePool.getCaseInfo().getAccountBalance());
                if (Objects.nonNull(outsourcePool.getCaseInfo().getProductName())){
                    outsourcePoolBatchModel.setProductName(outsourcePool.getCaseInfo().getProductName());
                }
                if (Objects.nonNull(outsourcePool.getCaseInfo().getSourceChannel())){
                    outsourcePoolBatchModel.setSourceChannel(outsourcePool.getCaseInfo().getSourceChannel());
                }
                if (Objects.nonNull(outsourcePool.getCaseInfo().getCollectionMethod())){
                    outsourcePoolBatchModel.setCollectionMethod(outsourcePool.getCaseInfo().getCollectionMethod());
                }
                outsourcePoolBatchModel.setOverDueDate(ZWDateUtil.getAfter(outsourcePool.getCaseInfo().getRepayDate(),1,null));
                if(Objects.nonNull(outsourcePool.getCaseInfo().getCurrentCollector())){
                    outsourcePoolBatchModel.setCollectorName(outsourcePool.getCaseInfo().getCurrentCollector().getRealName());
                }

                if(Objects.nonNull(outsourcePool.getCaseInfo().getPrincipalId())){
                    outsourcePoolBatchModel.setPrincipalName(outsourcePool.getCaseInfo().getPrincipalId().getName());
                }
                if(Objects.nonNull(outsourcePool.getCaseInfo().getPersonalInfo())){
                    if (Objects.nonNull(outsourcePool.getCaseInfo().getPersonalInfo().getName()))
                    outsourcePoolBatchModel.setPersonalName(outsourcePool.getCaseInfo().getPersonalInfo().getName());
                    outsourcePoolBatchModel.setIdCard(outsourcePool.getCaseInfo().getPersonalInfo().getCertificatesNumber());
                    outsourcePoolBatchModel.setPersonalId(outsourcePool.getCaseInfo().getPersonalInfo().getId());
                    outsourcePoolBatchModel.setMobileNo(outsourcePool.getCaseInfo().getPersonalInfo().getMobileNo());
                    if (Objects.nonNull(outsourcePool.getCaseInfo().getPersonalInfo().getIdCard()))
                    outsourcePoolBatchModel.setIdCard(outsourcePool.getCaseInfo().getPersonalInfo().getIdCard());
                }
                if(Objects.nonNull(outsourcePool.getCaseInfo().getArea())){
                    outsourcePoolBatchModel.setAreaName(outsourcePool.getCaseInfo().getArea().getAreaName());
                }
                if(Objects.nonNull(outsourcePool.getCaseInfo().getProduct())
                        && Objects.nonNull(outsourcePool.getCaseInfo().getProduct().getProductSeries())){
                    outsourcePoolBatchModel.setSeriesName(outsourcePool.getCaseInfo().getProduct().getProductSeries().getSeriesName());
                }
                if(Objects.nonNull(outsourcePool.getOutsource())){
                    outsourcePoolBatchModel.setOutsId(outsourcePool.getOutsource().getId());
                    outsourcePoolBatchModel.setOutsName(outsourcePool.getOutsource().getOutsName());
                }
                outsourcePoolBatchModel.setDelegationDate(outsourcePool.getOutTime());
                /**
                 * 如果没有contractAmt，就是在插入到outsources_pool的时候没有赋上，从case_info中取。
                 */
                BigDecimal contractAmt=outsourcePool.getContractAmt();
                if(null == contractAmt || contractAmt.compareTo(BigDecimal.ZERO) == 0){
                    outsourcePoolBatchModel.setContractAmt(outsourcePool.getCaseInfo().getOverdueAmount());
                }else{
                    outsourcePoolBatchModel.setContractAmt(outsourcePool.getContractAmt());
                }
                /**
                 * 如果没有逾期期数，就是在插入到outsources_pool的时候没有赋上，从case_info中取。
                 */
                String overduePeriod=outsourcePool.getOverduePeriods();
                if(StringUtils.isNotEmpty(overduePeriod)){
                    outsourcePoolBatchModel.setOverduePeriods(overduePeriod);
                }else{
                    String overduePeriods=outsourcePool.getCaseInfo().getOverduePeriods()+"";
                    if(StringUtils.isNotEmpty(overduePeriods)){
                        if(overduePeriods.contains("M")){
                            outsourcePoolBatchModel.setOverduePeriods(overduePeriods);
                        }else{
                            outsourcePoolBatchModel.setOverduePeriods("M"+overduePeriods);
                        }
                    }
                }
                outsourcePoolBatchModel.setOverOutsourceTime(outsourcePool.getOverOutsourceTime());
                outsourcePoolBatchModel.setOutBatch(outsourcePool.getOutBatch());
                outsourcePoolBatchModel.setId(outsourcePool.getId());
                list.add(outsourcePoolBatchModel);
            }
//            Page<OutsourcePoolBatchModel> page = new PageImpl<OutsourcePoolBatchModel>(list);
            Page<OutsourcePoolBatchModel> page = new PageImpl<>(
                    list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList()), pageable, list.size());
//            Page<OutsourcePoolBatchModel> page = new PageImpl<>(list, pageable, list.size());
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "getAllClosedOutSourceCase", "系统异常!")).body(null);
        }

    }

    /**
     * @Description 按批次号和委外名称查看委外案件详情
     * <p>
     * Created by huyanmin at 2017/09/26
     */
    @RequestMapping(value = "/getOutSourceCaseByOutName", method = RequestMethod.POST)
    @ApiOperation(value = "按批次号查看委外案件详情", notes = "按批次号查看委外案件详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<OutsourcePool>> getOutSourceCaseByOutName(@RequestBody OurBatchList outsBatchlist,
                                                                         @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request get outsource case by batch number");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "user do not log in", e.getMessage())).body(null);
        }
        try {
            Pageable pageable = new PageRequest(outsBatchlist.getPage(), outsBatchlist.getSize(), new Sort(Sort.Direction.DESC, "id"));
            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.nonNull(user.getCompanyCode())) {
                builder.and(qOutsourcePool.companyCode.eq(user.getCompanyCode()));
            }
            if (Objects.nonNull(outsBatchlist.getOurBatchList()) && outsBatchlist.getOurBatchList().size() != 0) {
                builder.and(qOutsourcePool.outBatch.in(outsBatchlist.getOurBatchList()));
            }
            if (Objects.nonNull(outsBatchlist.getOutsNameList()) && outsBatchlist.getOutsNameList().size() != 0) {
                List<Outsource> outsourceList = new ArrayList<>();
                Iterable<Outsource> outsources = outsourceRepository.findAll(QOutsource.outsource.outsName.in(outsBatchlist.getOutsNameList()));
                outsources.forEach(single -> {
                    outsourceList.add(single);
                });
                builder.and(qOutsourcePool.outsource.in(outsourceList));
            }
            if (StringUtils.isNotBlank(outsBatchlist.getOutsName())) {
                Outsource outsource = outsourceRepository.findOne(QOutsource.outsource.outsName.eq(outsBatchlist.getOutsName()).and(QOutsource.outsource.companyCode.eq(user.getCompanyCode()).and(QOutsource.outsource.flag.eq(0))));
                builder.and(qOutsourcePool.outsource.eq(outsource));
            }
            if (Objects.nonNull(outsBatchlist.getOutTimeStart()) && !("").equals(outsBatchlist.getOutTimeStart())) {
                String outTimeStart = outsBatchlist.getOutTimeStart().substring(0, 10) + " 00:00:00";
                Date minTime = ZWDateUtil.getUtilDate(outTimeStart, "yyyy-MM-dd HH:mm:ss");
                builder.and(QOutsourcePool.outsourcePool.outTime.goe(minTime));
            }
            if (Objects.nonNull(outsBatchlist.getOutTimeEnd()) && !("").equals(outsBatchlist.getOutTimeEnd())) {
                String outTimeEnd = outsBatchlist.getOutTimeEnd().substring(0, 10) + " 23:59:59";
                Date minTime = ZWDateUtil.getUtilDate(outTimeEnd, "yyyy-MM-dd HH:mm:ss");
                builder.and(QOutsourcePool.outsourcePool.outTime.loe(minTime));
            }
            builder.and(qOutsourcePool.outStatus.eq(OutsourcePool.OutStatus.OUTSIDING.getCode()));
            builder.and(qOutsourcePool.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.OUTER.getValue()));
            builder.and(qOutsourcePool.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            Page<OutsourcePool> page = outsourcePoolRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "getAllClosedOutSourceCase", "系统异常!")).body(null);
        }
    }

    /**
     * @Description 查看委外案件跟进记录
     * <p>
     * Created by huyanmin at 2017/09/27
     */
    @GetMapping("/getOutSourceCaseFollowRecord")
    @ApiOperation(value = "查看委外案件跟进记录", notes = "查看委外案件跟进记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseFollowupRecord>> getOutSourceCaseFollowRecord(@RequestParam @ApiParam(value = "案件编号", required = true) String caseNumber,
                                                                                 @QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                                                                 @RequestParam(required = false) @ApiParam(value = "公司标识码") String companyCode,
                                                                                 @ApiIgnore Pageable pageable,
                                                                                 @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request get outsource case by case number");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "user do not log in", e.getMessage())).body(null);
        }
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.nonNull(caseNumber)) {
                builder.and(QCaseFollowupRecord.caseFollowupRecord.caseNumber.eq(caseNumber));
            }
            if (Objects.nonNull(user.getCompanyCode())) {
                builder.and(QCaseFollowupRecord.caseFollowupRecord.companyCode.eq(user.getCompanyCode()));
            }
            builder.and(QCaseFollowupRecord.caseFollowupRecord.caseFollowupType.eq(CaseFollowupRecord.CaseFollowupType.OUTER.getValue()));
            Page<CaseFollowupRecord> page = caseFollowupRecordRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "getAllClosedOutSourceCase", "系统异常!")).body(null);
        }
    }

    @PostMapping("/returnOutsourceCase")
    @ApiOperation(value = "回收委外案件", notes = "回收委外案件")
    public ResponseEntity returnOutsourceCase(@RequestBody OutCaseIdList outCaseIdList,
                                              @RequestHeader(value = "X-UserToken") String token) {
        try {
            List<String> outCaseIds = outCaseIdList.getOutCaseIds();
            if (Objects.isNull(outCaseIds) || outCaseIds.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择要回收的案件")).body(null);
            }
            User user = getUserByToken(token);
            List<OutsourcePool> all = new ArrayList<>();
            Iterable<OutsourcePool> pools = outsourcePoolRepository.findAll(QOutsourcePool.outsourcePool.caseNumber.in(outCaseIdList.getOutCaseIds()));
            if (pools.iterator().hasNext()){
                pools.forEach(obj->{all.add(obj);});
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择要回收的案件")).body(null);
            }
            Iterator<OutsourcePool> iterator = all.iterator();
            List<CaseInfoReturn> caseInfoReturnList = new ArrayList<>();
            List<OutsourcePool> outsourcePoolList = new ArrayList<>();
            List<CaseTurnRecord> list = new ArrayList<>();
            List<CaseInfo> caseInfoList = new ArrayList<>();
            while (iterator.hasNext()) {
                OutsourcePool outsourcePool = iterator.next();
                outsourcePoolList.add(outsourcePool);
                CaseInfo caseInfo = outsourcePool.getCaseInfo();
                //以案件为维度
                List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseInfo.getCaseNumber());
                for (CaseInfo info : byCaseNumber) {
                    info.setOperatorTime(new Date());
                    info.setOperator(user);
                    info.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
                    info.setRecoverRemark(CaseInfo.RecoverRemark.RECOVERED.getValue());
                    info.setCasePoolType(CaseInfo.CasePoolType.RETURN.getValue());
                    //回收案件不属于任何催收员和机构 祁吉贵
                    info.setDepartment(null);
                    info.setCurrentCollector(null);
                    caseInfoList.add(info);
                }
                CaseInfoReturn caseInfoReturn = new CaseInfoReturn();
                caseInfoReturn.setSource(CaseInfoReturn.Source.OUTSOURCE.getValue());
                caseInfoReturn.setOperatorTime(new Date());
                caseInfoReturn.setCaseId(caseInfo);
                caseInfoReturn.setCaseNumber(caseInfo.getCaseNumber());
                caseInfoReturn.setOperator(user.getId());
                caseInfoReturn.setReason(outCaseIdList.getReturnReason());
                caseInfoReturn.setOutBatch(outsourcePool.getOutBatch());
                caseInfoReturn.setOutsName(outsourcePool.getOutsource().getOutsName());
                caseInfoReturn.setOutTime(outsourcePool.getOutTime());
                caseInfoReturn.setOverOutsourceTime(outsourcePool.getOverOutsourceTime());
                caseInfoReturn.setCompanyCode(outsourcePool.getCompanyCode());
                caseInfoReturn.setReturnType(CaseInfoReturn.ReturnType.MANUAL.getValue());
                caseInfoReturnList.add(caseInfoReturn);

                CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                BeanUtils.copyProperties(caseInfo, caseTurnRecord);
                caseTurnRecord.setId(null); //主键置空
                caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                caseTurnRecord.setCaseNumber(caseInfo.getCaseNumber());
                caseInfoReturn.setCompanyCode(user.getCompanyCode());
                caseTurnRecord.setTurnApprovalStatus(CaseTurnRecord.TurnApprovalStatus.PASS.getValue());
                caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
                caseTurnRecord.setOperatorUserName(user.getUserName()); //操作员
                caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                caseTurnRecord.setTurnFromPool(CaseTurnRecord.TurnFromPool.OUTER.getValue());
                caseTurnRecord.setTurnToPool(CaseTurnRecord.TurnToPool.OUTER.getValue());
                caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.MANUAL_RECYCLING.getValue());
                list.add(caseTurnRecord);
            }
            caseTurnRecordRepository.save(list);
            caseInfoRepository.save(caseInfoList);
            caseInfoReturnRepository.save(caseInfoReturnList);
            outsourcePoolRepository.delete(outsourcePoolList);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("回收成功", "")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("案件回收失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

    /**
     * 委外案件 待分配案件 策略分配
     */
    @GetMapping("/outerStrategyDistribute")
    @ApiOperation(value = "委外案件 待分配案件 策略分配", notes = "委外案件 待分配案件 策略分配")
    public ResponseEntity<List<OutDistributeInfo>> outerStrategyDistribute(CaseInfoDistributeParams caseInfoDistributeParams,
                                                                           @RequestHeader(value = "X-UserToken") String token) {
        User user = null;
        List<OutDistributeInfo> list;
        List<CaseInfoDistributedModel> caseInfoDistributedModels = new ArrayList<>();
        try {
            user = getUserByToken(token);
            ParameterizedTypeReference<List<CaseStrategy>> responseType = new ParameterizedTypeReference<List<CaseStrategy>>() {
            };
            ResponseEntity<List<CaseStrategy>> caseStrategies = restTemplate.exchange(Constants.CASE_STRATEGY_URL
                    .concat("companyCode=").concat(user.getCompanyCode())
                    .concat("&strategyType=").concat(CaseStrategy.StrategyType.OUTS.getValue().toString()), HttpMethod.GET, null, responseType);
            if (Objects.isNull(caseStrategies) || !caseStrategies.hasBody() || caseStrategies.getBody().size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "", "没有分配策略信息")).body(null);
            }
            caseInfoDistributeParams.setCompanyCode(user.getCompanyCode());
            List<Object[]> objects = entityManageService.getOutSourcePool(caseInfoDistributeParams);
            caseInfoDistributedModels = caseInfoService.findModelList(objects, caseInfoDistributedModels);
            if (Objects.isNull(caseInfoDistributedModels) || caseInfoDistributedModels.size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "", "没有待分配的案件信息")).body(null);
            }
            list = outsourcePoolService.outerStrategyDistribute(caseStrategies.getBody(), caseInfoDistributedModels, user);
            //判断是否有符合策略的案件
            if (Objects.isNull(list) || list.size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "", "没有符合策略的案件")).body(null);
            } else {
                return ResponseEntity.ok().body(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("OutsourcePoolController", "", "分配失败")).body(null);
        }
    }

    @GetMapping("/revertOutCaseInfoDistribute")
    @ApiOperation(value = "根据批次撤销分配案件", notes = "根据批次撤销分配案件")
    public ResponseEntity revertOutCaseInfoDistribute(@RequestParam String batchNumber, @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("User", "", "获取不到登录人信息")).body(null);
            }
            outsourcePoolService.revertOutCaseInfoDistribute(batchNumber, user);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("核销申请失败", ENTITY_NAME1, e.getMessage())).body(null);
        }
    }

}
