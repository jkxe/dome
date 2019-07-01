


package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.exception.GeneralException;
import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.*;
import cn.fintecher.pangolin.business.utils.ExcelExportHelper;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.model.AccCaseInfoDisModel;
import cn.fintecher.pangolin.model.PreviewModel;
import cn.fintecher.pangolin.util.ZWStringUtils;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import cn.fintecher.pangolin.web.ResponseUtil;
import com.google.common.collect.Lists;
import com.hsjry.lang.common.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ChenChang on 2017/5/23.
 */
@RestController
@RequestMapping("/api/caseInfoController")
@Api(value = "CaseInfoController", description = "案件操作")
public class CaseInfoController extends BaseController {

    private static final String ENTITY_NAME = "caseInfo";
    private static final String ENTITY_CASEINFO_RETURN = "CaseInfoReturn";
    private static final String ENTITY_CASEINFO_REMARK = "CaseInfoRemark";
    private static final String ENTITY_CASE_DISTRIBUTED_TEMPORARY = "CaseDistributedTemporary";
    private final Logger log = LoggerFactory.getLogger(CaseInfoController.class);
    private final CaseInfoRepository caseInfoRepository;

    @Inject
    RunCaseStrategyService runCaseStrategyService;
    @Inject
    private CaseInfoService caseInfoService;
    @Inject
    private CaseFollowupRecordRepository caseFollowupRecordRepository;
    @Inject
    private CaseTurnRecordRepository caseTurnRecordRepository;
    @Inject
    private RestTemplate restTemplate;
    @Inject
    private FollowRecordExportService followRecordExportService;
    @Inject
    private PersonalRepository personalRepository;
    @Inject
    private CaseInfoFileRepository caseInfoFileRepository;
    @Inject
    private CaseInfoHistoryRepository caseInfoHistoryRepository;
    @Inject
    private CaseInfoReturnRepository caseInfoReturnRepository;
    @Inject
    private CaseInfoVerificationService caseInfoVerificationService;
    @Inject
    private CaseInfoVerificationApplyRepository caseInfoVerificationApplyRepository;
    @Inject
    private CaseInfoRemarkRepository caseInfoRemarkRepository;
    @Inject
    private CaseInfoLearningRepository caseInfoLearningRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private EntityManageService entityManageService;
    @Inject
    StrategyDistributeService strategyDistributeService;
    @Inject
    CaseInfoDistributedService caseInfoDistributedService;
    @Inject
    private CaseFileRepository caseFileRepository;
    @Autowired
    private PrincipalRepository principalRepository;
    @Autowired
    private ProductRepository productRepository;

    public CaseInfoController(CaseInfoRepository caseInfoRepository) {
        this.caseInfoRepository = caseInfoRepository;
    }


    @PostMapping("/caseInfo")
    public ResponseEntity<CaseInfo> createCaseInfo(@RequestBody CaseInfo caseInfo) throws URISyntaxException {
        log.debug("REST request to save caseInfo : {}", caseInfo);
        if (caseInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "新增案件不应该含有ID")).body(null);
        }
        CaseInfo result = caseInfoRepository.save(caseInfo);
        return ResponseEntity.created(new URI("/api/caseInfo/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/caseInfo")
    public ResponseEntity<CaseInfo> updateCaseInfo(@RequestBody CaseInfo caseInfo) throws URISyntaxException {
        log.debug("REST request to update CaseInfo : {}", caseInfo);
        if (caseInfo.getId() == null) {
            return createCaseInfo(caseInfo);
        }
        CaseInfo result = caseInfoRepository.save(caseInfo);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caseInfo.getId().toString()))
                .body(result);
    }

    @GetMapping("/caseInfo")
    public List<CaseInfo> getAllCaseInfo() {
        log.debug("REST request to get all CaseInfo");
        List<CaseInfo> caseInfoList = caseInfoRepository.findAll();
        return caseInfoList;
    }

    @GetMapping("/queryCaseInfo")
    public ResponseEntity<Page<CaseInfo>> queryCaseInfo(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestHeader(value = "X-UserToken") String token) throws Exception {
        User user = getUserByToken(token);
        BooleanBuilder builder = new BooleanBuilder(predicate);
        builder.and(QCaseInfo.caseInfo.department.code.startsWith(""));
        Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queryCaseInfo");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/caseInfo/{id}")
    public ResponseEntity<CaseInfo> getCaseInfo(@PathVariable String id) {
        log.debug("REST request to get caseInfo : {}", id);
        CaseInfo caseInfo = caseInfoRepository.findOne(id);
        QCaseFile qCaseFile = QCaseFile.caseFile;
        List<CaseFile> caseFiles = Lists.newArrayList(caseFileRepository.findAll(qCaseFile.caseNumber.eq(caseInfo.getCaseNumber())));
        caseInfo.setCaseFileList(caseFiles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caseInfo));
    }

    @DeleteMapping("/caseInfo/{id}")
    public ResponseEntity<Void> deleteCaseInfo(@PathVariable String id) {
        log.debug("REST request to delete caseInfo : {}", id);
        caseInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    @GetMapping("/getAllBatchNumber")
    @ApiOperation(value = "获取所有批次号", notes = "获取所有批次号")
    public ResponseEntity<List<String>> getAllBatchNumber(@RequestHeader(value = "X-UserToken") String token,
                                                          @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode) {
        log.debug("REST request to getAllBatchNumber");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllBatchNumber", e.getMessage())).body(null);
        }
        try {
            List<String> distinctByBatchNumber;

            if (Objects.isNull(user.getCompanyCode())) {
                distinctByBatchNumber = caseInfoRepository.findAllDistinctByBatchNumber();
            } else {
                distinctByBatchNumber = caseInfoRepository.findDistinctByBatchNumber(user.getCompanyCode());
            }
            return ResponseEntity.ok().body(distinctByBatchNumber);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllBatchNumber", "系统异常!")).body(null);
        }
    }

    @GetMapping("/getAllCaseInfo")
    @ApiOperation(value = "分页查询案件管理案件", notes = "分页查询案件管理案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllCaseInfo(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                         @ApiIgnore Pageable pageable,
                                                         @RequestHeader(value = "X-UserToken") String token,
                                                         @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code码") String companyCode) {
        log.debug("REST request to getAllCaseInfo");
        Sort.Order personalName = new Sort.Order(Sort.Direction.ASC, "personalInfo.name"); //客户姓名正序
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllCaseInfo", e.getMessage())).body(null);
        }
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfo.companyCode.eq(companyCode)); //公司
                }
            } else {
                builder.and(qCaseInfo.companyCode.eq(user.getCompanyCode())); //公司
            }
//            builder.and(qCaseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue())); //以结案
//            builder.and(qCaseInfo.endType.notIn(CaseInfo.EndType.JUDGMENT_CLOSED.getValue(),CaseInfo.EndType.OUTSIDE_CLOSED.getValue())); //不查司法、委外的
//            builder.andAnyOf(qCaseInfo.endType.notIn(CaseInfo.EndType.JUDGMENT_CLOSED.getValue(),
//                    CaseInfo.EndType.OUTSIDE_CLOSED.getValue(),
//                    CaseInfo.EndType.CLOSE_CASE.getValue()),
//                    qCaseInfo.endType.isNull());
            List<String> allCaseInfoIds = caseInfoHistoryRepository.findAllCaseInfoIds();
            if (!allCaseInfoIds.isEmpty()) {
                builder.and(qCaseInfo.id.notIn(allCaseInfoIds));
            }
//            if (Objects.equals(user.getManager(), User.MANAGER_TYPE.DATA_AUTH.getValue())) { //管理者
//                builder.and(qCaseInfo.department.code.startsWith(user.getDepartment().getCode()));
//            }
//            if (Objects.equals(user.getManager(), User.MANAGER_TYPE.NO_DATA_AUTH.getValue())) { //不是管理者
//                builder.and(qCaseInfo.currentCollector.eq(user).or(qCaseInfo.assistCollector.eq(user)));
//            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(personalName)));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllCaseInfo", "系统异常!")).body(null);
        }
    }

    @PostMapping(value = "/distributeCeaseInfoAgain")
    @ApiOperation(value = "案件重新分配", notes = "案件重新分配")
    public ResponseEntity distributeCeaseInfoAgain(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                                   @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to distributeCeaseInfoAgain");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            String information = caseInfoService.distributeCeaseInfoAgain(accCaseInfoDisModel, user);
            if (StringUtils.isNotBlank(information)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("请重新选择案件", ENTITY_NAME, information)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/distributePreview")
    @ApiOperation(value = "内催待分配预览", notes = "内催待分配预览")
    public ResponseEntity<PreviewModel> distributePreview(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                                          @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to distributeCeaseInfo");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            PreviewModel previewModel = caseInfoService.distributePreview(accCaseInfoDisModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(previewModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/distributePreviewScheduled")
    @ApiOperation(value = "内催待分配预览-供定时任务调用", notes = "内催待分配预览-供定时任务调用")
    public ResponseEntity<PreviewModel> distributePreviewScheduled(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel) {
        log.debug("REST request to distributeCeaseInfo");
        User user = null;
        try {
            PreviewModel previewModel = caseInfoService.distributePreview(accCaseInfoDisModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(previewModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/distributeCaseInfo")
    @ApiOperation(value = "内催待分配案件分配", notes = "内催待分配案件分配")
    public ResponseEntity distributeCaseInfo(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                             @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to distributeCeaseInfo");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            String information = caseInfoService.distributeCeaseInfo(accCaseInfoDisModel, user, true);
            if (StringUtils.isNotBlank(information)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("请重新选择案件", ENTITY_NAME, information)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/distributeCaseInfoScheduled")
    @ApiOperation(value = "内催待分配案件分配-定时任务不需要token", notes = "内催待分配案件分配-定时任务不需要token")
    public ResponseEntity distributeCaseInfoScheduled(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel) {
        log.debug("REST request to distributeCeaseInfo");
        User user = null;
        try {
            user = userRepository.findOne(Constants.ADMINISTRATOR_ID);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            caseInfoService.distributeCeaseInfo(accCaseInfoDisModel, user, false);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "errorMessage", e.getMessage())).body(null);
        }
    }

    @GetMapping("/getCaseInfoDetails")
    @ApiOperation(value = "案件详情查询操作", notes = "案件详情查询操作")
    public ResponseEntity<CaseInfo> getCaseInfoDetails(@RequestParam("id") String id) {
        CaseInfo caseInfo = caseInfoRepository.findOne(id);
        return ResponseEntity.ok().body(caseInfo);
    }

    @GetMapping("/getOverdueInfo")
    @ApiOperation(value = "查询案件逾期信息", notes = "查询案件逾期信息")
    public ResponseEntity<OverdueInfoModel> getOverdueInfo(@RequestParam("id") String id) {
        OverdueInfoModel overdueInfoModel = new OverdueInfoModel();
        Object newObject = caseInfoRepository.getOverdueInfo(id);
        Object[] object = (Object[]) newObject;
        overdueInfoModel.setPayPeriod((Integer) object[0]);
        overdueInfoModel.setHasPayPeriods((Integer) object[0] - (Integer) object[1]);
        overdueInfoModel.setOverduePeriods((Integer) object[2]);
        overdueInfoModel.setOverdueDays((Integer) object[3]);
        overdueInfoModel.setApprovedLoanAmt((BigDecimal) object[4]);
        overdueInfoModel.setOverdueAmount((BigDecimal) object[5]);
        overdueInfoModel.setOverdueCapital((BigDecimal) object[6]);
        overdueInfoModel.setOverdueInterest((BigDecimal) object[7]);
        overdueInfoModel.setUnpaidOtherFee((BigDecimal) object[8]);
        overdueInfoModel.setCardAddress((String) object[9]);
        overdueInfoModel.setUnpaidFine((BigDecimal) object[10]);
        overdueInfoModel.setUnpaidInterest((BigDecimal) object[11]);
        overdueInfoModel.setOperatorTime((Date) object[12]);
        overdueInfoModel.setVerficationStatus((String) object[13]);
        overdueInfoModel.setOverdueCapitalInterest((BigDecimal) object[14]);
        overdueInfoModel.setOverdueDelayFine((BigDecimal) object[15]);
        overdueInfoModel.setOverdueFine((BigDecimal) object[16]);
        overdueInfoModel.setLoanPeriod((Integer) object[17]);
        overdueInfoModel.setAccountBalance((BigDecimal) object[18]);
        overdueInfoModel.setOverdueManageFee((BigDecimal) object[19]);
        return ResponseEntity.ok().body(overdueInfoModel);
    }


    @GetMapping("/getAllOverdueInfo")
    @ApiOperation(value = "查询共债案件逾期信息", notes = "查询共债案件逾期信息")
    public ResponseEntity<OverdueInfoModel> getAllOverdueInfo(@RequestParam("caseNumber") String caseNumber,
                                                              @RequestParam("typeOfcase") String typeOfcase) {
        OverdueInfoModel overdueInfoModel = new OverdueInfoModel();
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseNumber));
        if(StringUtils.equals("0", typeOfcase)){
            builder.and(QCaseInfo.caseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
        } else if (StringUtils.equals("1", typeOfcase)){
            builder.and(QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
        }
        Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
        List<CaseInfo> list = Lists.newArrayList(all);
        for (CaseInfo caseInfo : list) {
            // 判断OverdueInfoModel是否为空
            if (overdueInfoModel.getOverduePeriods() == 0
                    && overdueInfoModel.getHasPayPeriods() == 0
                    && StringUtils.isBlank(overdueInfoModel.getCardAddress())) {
                overdueInfoModel.setPayPeriod(caseInfo.getLoanPeriod());
                overdueInfoModel.setHasPayPeriods(caseInfo.getHasPayPeriods());
                overdueInfoModel.setOverduePeriods(caseInfo.getOverduePeriods());
                overdueInfoModel.setOverdueDays(caseInfo.getOverdueDays());
                overdueInfoModel.setApprovedLoanAmt(Optional.ofNullable(caseInfo.getAccountBalance()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setOverdueAmount(caseInfo.getOverdueAmount());
                overdueInfoModel.setOverdueCapital(caseInfo.getOverdueCapital());
                overdueInfoModel.setOverdueInterest(caseInfo.getOverdueInterest());
                overdueInfoModel.setUnpaidOtherFee(Optional.ofNullable(caseInfo.getUnpaidOtherFee()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setCardAddress(caseInfo.getPersonalInfo().getIdCardAddress());
                overdueInfoModel.setUnpaidFine(Optional.ofNullable(caseInfo.getUnpaidFine()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setUnpaidInterest(Optional.ofNullable(caseInfo.getUnpaidInterest()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setOperatorTime(caseInfo.getPersonalInfo().getOperatorTime());
                overdueInfoModel.setVerficationStatus(caseInfo.getVerficationStatus());
                overdueInfoModel.setOverdueCapitalInterest(Optional.ofNullable(caseInfo.getOverdueCapitalInterest()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setOverdueDelayFine(Optional.ofNullable(caseInfo.getOverdueDelayFine()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setOverdueFine(Optional.ofNullable(caseInfo.getOverdueFine()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setLoanPeriod(caseInfo.getLoanPeriod());
                overdueInfoModel.setAccountBalance(Optional.ofNullable(caseInfo.getAccountBalance()).orElse(BigDecimal.ZERO));
                overdueInfoModel.setOverdueManageFee(Optional.ofNullable(caseInfo.getOverdueManageFee()).orElse(BigDecimal.ZERO));
            } else {
                // 不为空  需要合并共债案件数据
                if (overdueInfoModel.getOverduePeriods() <= caseInfo.getOverduePeriods()) {
                    overdueInfoModel.setOverduePeriods(caseInfo.getOverduePeriods());
                }
                // 不为空  需要合并共债案件数据
                if (overdueInfoModel.getOverdueDays() <= caseInfo.getOverdueDays()) {
                    overdueInfoModel.setOverdueDays(caseInfo.getOverdueDays());
                }
                // 合并贷款金额
                if (Objects.nonNull(caseInfo.getAccountBalance())) {
                    overdueInfoModel.setApprovedLoanAmt(overdueInfoModel.getApprovedLoanAmt().add(caseInfo.getAccountBalance()));
                }
                // 合并逾期金额
                if (Objects.nonNull(caseInfo.getOverdueAmount())) {
                    overdueInfoModel.setOverdueAmount(overdueInfoModel.getOverdueAmount().add(caseInfo.getOverdueAmount()));
                }
                // 合并逾期本金
                if (Objects.nonNull(caseInfo.getOverdueCapital())) {
                    overdueInfoModel.setOverdueCapital(overdueInfoModel.getOverdueCapital().add(caseInfo.getOverdueCapital()));
                }
                // 合并逾期利息
                if (Objects.nonNull(caseInfo.getOverdueInterest())) {
                    overdueInfoModel.setOverdueInterest(overdueInfoModel.getOverdueInterest().add(caseInfo.getOverdueInterest()));
                }
                // 合并逾期其他费用
                if (Objects.nonNull(caseInfo.getUnpaidOtherFee())) {
                    overdueInfoModel.setUnpaidOtherFee(overdueInfoModel.getUnpaidOtherFee().add(caseInfo.getUnpaidOtherFee()));
                }
                // 合并未结罚息
                if (Objects.nonNull(caseInfo.getUnpaidFine())) {
                    overdueInfoModel.setUnpaidFine(overdueInfoModel.getUnpaidFine().add(caseInfo.getUnpaidFine()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getUnpaidInterest())) {
                    overdueInfoModel.setUnpaidInterest(overdueInfoModel.getUnpaidInterest().add(caseInfo.getUnpaidInterest()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getOverdueCapitalInterest())) {
                    overdueInfoModel.setOverdueCapitalInterest(overdueInfoModel.getOverdueCapitalInterest().add(caseInfo.getOverdueCapitalInterest()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getOverdueDelayFine())) {
                    overdueInfoModel.setOverdueDelayFine(overdueInfoModel.getOverdueDelayFine().add(caseInfo.getOverdueDelayFine()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getOverdueFine())) {
                    overdueInfoModel.setOverdueFine(overdueInfoModel.getOverdueFine().add(caseInfo.getOverdueFine()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getLoanPeriod())) {
                    overdueInfoModel.setLoanPeriod(overdueInfoModel.getLoanPeriod() + caseInfo.getLoanPeriod());
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getAccountBalance())) {
                    overdueInfoModel.setAccountBalance(overdueInfoModel.getAccountBalance().add(caseInfo.getAccountBalance()));
                }
                // 合并未结利息
                if (Objects.nonNull(caseInfo.getOverdueManageFee())) {
                    overdueInfoModel.setOverdueManageFee(overdueInfoModel.getOverdueManageFee().add(caseInfo.getOverdueManageFee()));
                }
            }
        }
        return ResponseEntity.ok().body(overdueInfoModel);
    }

    @GetMapping("/getTelCaseInfo")
    @ApiOperation(value = "分页查询电催案件", notes = "分页查询电催案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getTelCaseInfo(
            @ApiIgnore Pageable pageable,
            @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to getAllCaseInfo");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllCaseInfo", e.getMessage())).body(null);
        }
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "getAllCaseInfo", "系统异常!")).body(null);
        }
    }

    @GetMapping("/getCaseInfoFollowRecord")
    @ApiOperation(value = "案件跟进记录", notes = "案件跟进记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseFollowupRecord>> getCaseInfoFollowRecord(@QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                                                            @ApiIgnore Pageable pageable,
                                                                            @RequestParam("caseNumber") @ApiParam("案件ID") String caseNumber) {
        QCaseFollowupRecord qCaseFollowupRecord = QCaseFollowupRecord.caseFollowupRecord;
        BooleanBuilder builder = new BooleanBuilder(predicate);
        builder.and(qCaseFollowupRecord.caseNumber.eq(caseNumber));
        builder.and(QCaseFollowupRecord.caseFollowupRecord.collectionWay.eq(1)); //只查催记方式为手动的
        Page<CaseFollowupRecord> page = caseFollowupRecordRepository.findAll(builder, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/getCaseInfoTurnRecord")
    @ApiOperation(value = "案件流转记录", notes = "案件流转记录")
    public ResponseEntity<List<CaseTurnRecord>> getCaseInfoTurnRecord(@QuerydslPredicate(root = CaseTurnRecord.class) Predicate predicate,
                                                                      @RequestParam("caseId") @ApiParam("案件ID") String caseId) {
        QCaseTurnRecord qCaseTurnRecord = QCaseTurnRecord.caseTurnRecord;
        BooleanBuilder builder = new BooleanBuilder(predicate);
        builder.and(qCaseTurnRecord.caseId.eq(caseId));
        Iterable<CaseTurnRecord> all = caseTurnRecordRepository.findAll(builder);
        List<CaseTurnRecord> caseTurnRecords = IterableUtils.toList(all);
        return ResponseEntity.ok().body(caseTurnRecords);
    }

    @GetMapping("/electricSmallCirculation")
    @ApiOperation(value = "电催小流转池", notes = "电催小流转池")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity electricSmallCirculation(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                   @ApiIgnore Pageable pageable,
                                                   @RequestHeader(value = "X-UserToken") String token,
                                                   @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "User not exists", e.getMessage())).body(null);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            }
        } else {
            booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode()));
        }
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CaseType.PHNONESMALLTURN.getValue());
        list.add(CaseInfo.CaseType.PHNONEFAHEADTURN.getValue());
        booleanBuilder.and(QCaseInfo.caseInfo.caseType.in(list));
        Page<CaseInfo> page = caseInfoRepository.findAll(booleanBuilder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "electricSmallCirculation")).body(page);
    }

    @GetMapping("/electricForceCirculation")
    @ApiOperation(value = "电催强制流转池", notes = "电催强制流转池")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity electricForceCirculation(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                   @ApiIgnore Pageable pageable,
                                                   @RequestHeader(value = "X-UserToken") String token,
                                                   @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "User not exists", e.getMessage())).body(null);
        }
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            }
        } else {
            booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode()));
        }
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CaseType.PHNONEFORCETURN.getValue());
        list.add(CaseInfo.CaseType.PHNONELEAVETURN.getValue());
        booleanBuilder.and(QCaseInfo.caseInfo.caseType.in(list));
        Page<CaseInfo> page = caseInfoRepository.findAll(booleanBuilder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "electricSmallCirculation")).body(page);
    }

    @GetMapping("/outSmallCirculation")
    @ApiOperation(value = "外访小流转池", notes = "外访小流转池")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity outSmallCirculation(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                              @ApiIgnore Pageable pageable,
                                              @RequestHeader(value = "X-UserToken") String token,
                                              @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "User not exists", e.getMessage())).body(null);
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            }
        } else {
            booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode()));
        }
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CaseType.OUTSMALLTURN.getValue());
        list.add(CaseInfo.CaseType.OUTFAHEADTURN.getValue());
        booleanBuilder.and(QCaseInfo.caseInfo.caseType.in(list));
        Page<CaseInfo> page = caseInfoRepository.findAll(booleanBuilder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "outSmallCirculation")).body(page);
    }

    @GetMapping("/outForceCirculation")
    @ApiOperation(value = "外访强制流转池", notes = "外访强制流转池")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity outForceCirculation(@QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                              @ApiIgnore Pageable pageable,
                                              @RequestHeader(value = "X-UserToken") String token,
                                              @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "User not exists", e.getMessage())).body(null);
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (Objects.nonNull(companyCode)) {
                booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
            }
        } else {
            booleanBuilder.and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode()));
        }
        List<Integer> list = new ArrayList<>();
        list.add(CaseInfo.CaseType.OUTFORCETURN.getValue());
        list.add(CaseInfo.CaseType.OUTLEAVETURN.getValue());
        booleanBuilder.and(QCaseInfo.caseInfo.caseType.in(list));
        Page<CaseInfo> page = caseInfoRepository.findAll(booleanBuilder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "outForceCirculation")).body(page);
    }

    @PostMapping("/exportCaseInfoFollowRecord")
    @ApiOperation(value = "导出跟进记录", notes = "导出跟进记录")
    public ResponseEntity exportCaseInfoFollowRecord(@RequestBody ExportCaseNum exportCaseNum,
                                                     @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        XSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
        try {
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(exportCaseNum.getCompanyCode())) {
                    user.setCompanyCode(exportCaseNum.getCompanyCode());
                }
            }
            List<String> caseNumberList = exportCaseNum.getCaseNumberList();
            if (caseNumberList.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请选择案件!")).body(null);
            }
            List<Object[]> caseFollowupRecords = caseFollowupRecordRepository.findFollowup(caseNumberList, user.getCompanyCode());
            if (caseFollowupRecords.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "要导出的跟进记录数据为空!")).body(null);
            }
            if (caseFollowupRecords.size() > 10000) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "不支持导出数据超过10000条!")).body(null);
            }
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            out = new ByteArrayOutputStream();
            Map<String, String> head = followRecordExportService.createHead();
            List<Map<String, Object>> data = followRecordExportService.createData(caseFollowupRecords);
            ExcelExportHelper.createExcel(workbook, sheet, head, data, 0, 0);
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "跟进记录.xlsx");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "系统错误!")).body(null);
            } else {
                return ResponseEntity.ok().body(url.getBody());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "上传文件服务器失败")).body(null);
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

    @GetMapping("/exportFollowRecord")
    @ApiOperation(value = "导出跟进记录(单案件)", notes = "导出跟进记录(单案件)")
    public ResponseEntity exportFollowRecord(@QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                             @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode,
                                             @RequestParam("caseNumber") @ApiParam("案件编号") String caseNumber,
                                             @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        XSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }

        try {
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    user.setCompanyCode(companyCode);
                }
            }
            List<Object[]> caseFollowupRecords = caseFollowupRecordRepository.findFollowupSingl(caseNumber, user.getCompanyCode());
            if (caseFollowupRecords.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "跟进记录数据为空!")).body(null);
            }
            if (caseFollowupRecords.size() > 10000) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "不支持导出数据超过10000条!")).body(null);
            }
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("sheet1");
            out = new ByteArrayOutputStream();
            Map<String, String> head = followRecordExportService.createHead();
            List<Map<String, Object>> data = followRecordExportService.createData(caseFollowupRecords);
            ExcelExportHelper.createExcel(workbook, sheet, head, data, 0, 0);
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "跟进记录.xlsx");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            if (url == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "系统错误!")).body(null);
            } else {
                return ResponseEntity.ok().body(url.getBody());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "caseinfo", ex.getMessage())).body(null);
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
     * @Description : 查询共债案件
     */

    @GetMapping("/queryCaseInfoList")
    @ApiOperation(value = "查询共债案件", notes = "查询共债案件")
    public ResponseEntity<Page<CaseInfo>> queryCaseInfoList(@RequestParam String companyCode,
                                                            @RequestParam(required = false) String id,
                                                            @ApiIgnore Pageable pageable,
                                                            @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }

        CaseInfo caseInfo = caseInfoRepository.findOne(id);
        if (Objects.equals(caseInfo.getCollectionStatus(), CaseInfo.CollectionStatus.CASE_OVER.getValue())) { //如果案件为已结案状态
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "已结案案件不存在共债案件")).body(null);
        }
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        BooleanBuilder builder = new BooleanBuilder();
        if (Objects.nonNull(companyCode)) {
            builder.and(qCaseInfo.companyCode.eq(companyCode));
        }
        if (Objects.nonNull(caseInfo.getPersonalInfo())) {
            builder.and(qCaseInfo.personalInfo.idCard.eq(caseInfo.getPersonalInfo().getIdCard())).
                    and(qCaseInfo.personalInfo.name.eq(caseInfo.getPersonalInfo().getName())).
                    and(qCaseInfo.id.ne(caseInfo.getId())).
                    and(qCaseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue(), CaseInfo.CollectionStatus.CASE_OUT.getValue()));
        }
        Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(page);
    }

    /**
     * @Description : 分页查询核销案件
     */
    @GetMapping("/queryChargeOffList")
    @ApiOperation(value = "分页查询核销案件", notes = "分页查询核销案件")
    public ResponseEntity<Page<CaseInfo>> queryChargeOffList(
            @RequestParam(required = false) @ApiParam("案件编号") String caseNumber,
            @RequestParam(required = false) @ApiParam("借据号") String loanInvoiceNumber,
            @RequestParam(required = false) @ApiParam("客户姓名") String name,
            @RequestParam(required = false) @ApiParam("手机号") String mobileNo,
            @RequestParam(required = false) @ApiParam("身份证号码") String idCard,
            @RequestParam(required = false) @ApiParam("逾期期数") Integer overduePeriods,
            @RequestParam(required = false) @ApiParam("逾期天数左区间") Integer overdueDaysLeft,
            @RequestParam(required = false) @ApiParam("逾期天数右区间") Integer overdueDaysRight,
            //@RequestParam(required = false) @ApiParam("核销时间") String verificationDate,
            @RequestParam(required = false) @ApiParam("逾期次数") Integer overdueCount,
            @ApiIgnore Pageable pageable,
            @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCaseInfo.verficationStatus.eq("核销"));
        if (Objects.nonNull(caseNumber)) builder.and(qCaseInfo.caseNumber.eq(caseNumber));
        if (Objects.nonNull(loanInvoiceNumber)) builder.and(qCaseInfo.loanInvoiceNumber.eq(loanInvoiceNumber));
        if (Objects.nonNull(name)) builder.and(qCaseInfo.personalInfo.name.like("%" + name + "%"));
        if (Objects.nonNull(mobileNo)) builder.and(qCaseInfo.personalInfo.mobileNo.like("%" + mobileNo + "%"));
        if (Objects.nonNull(idCard)) builder.and(qCaseInfo.personalInfo.certificatesNumber.eq(idCard));
        if (Objects.nonNull(overduePeriods)) builder.and(qCaseInfo.overduePeriods.eq(overduePeriods));
        if (Objects.nonNull(overdueCount)) builder.and(qCaseInfo.overdueCount.eq(overdueCount));
        if (Objects.nonNull(overdueDaysLeft)) builder.and(qCaseInfo.overdueDays.goe(overdueDaysLeft));
        if (Objects.nonNull(overdueDaysRight)) builder.and(qCaseInfo.overdueDays.loe(overdueDaysRight));

        Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(page);
    }

    /**
     * @Description : 查询所有核销案件
     */
    @GetMapping("/queryAllChargeOff")
    @ApiOperation(value = "查询所有核销案件", notes = "查询所有核销案件")
    public ResponseEntity<Iterable<CaseInfo>> queryAllChargeOff(
            @RequestParam(required = false) @ApiParam("案件编号") String caseNumber,
            @RequestParam(required = false) @ApiParam("客户姓名") String name,
            @RequestParam(required = false) @ApiParam("手机号") String mobileNo,
            @RequestParam(required = false) @ApiParam("身份证号码") String idCard,
            @RequestParam(required = false) @ApiParam("逾期期数") Integer overduePeriods,
            @RequestParam(required = false) @ApiParam("逾期天数左区间") Integer overdueDaysLeft,
            @RequestParam(required = false) @ApiParam("逾期天数右区间") Integer overdueDaysRight
    ) {
        User user;
        try {
            //user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCaseInfo.verficationStatus.eq("核销"));
        if (Objects.nonNull(name)) builder.and(qCaseInfo.caseNumber.eq(caseNumber));
        if (Objects.nonNull(name)) builder.and(qCaseInfo.personalInfo.name.eq(name));
        if (Objects.nonNull(mobileNo)) builder.and(qCaseInfo.personalInfo.mobileNo.eq(mobileNo));
        if (Objects.nonNull(idCard)) builder.and(qCaseInfo.personalInfo.certificatesNumber.eq(idCard));
        if (Objects.nonNull(overduePeriods)) builder.and(qCaseInfo.overduePeriods.eq(overduePeriods));
        if (Objects.nonNull(overdueDaysLeft) && Objects.nonNull(overdueDaysRight))
            builder.and(qCaseInfo.overdueDays.between(overdueDaysLeft, overdueDaysRight));
        Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(all);
    }


    @GetMapping("/updateAllScoreStrategyManual")
    @ApiOperation(value = "更新案件评分(手动)", notes = "更新案件评分(手动)")
    public ResponseEntity updateAllScoreStrategyManual(@RequestParam @ApiParam(required = true) Integer strategyType,
                                                       @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            String companyCode = user.getCompanyCode();
            StopWatch watch1 = new StopWatch();
            watch1.start();
            KieSession kieSession = null;
            try {
                kieSession = runCaseStrategyService.createSorceRule(companyCode, strategyType);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            Iterable<CaseInfo> caseInfoList = caseInfoRepository.findAll(QCaseInfo.caseInfo.collectionStatus.in(CaseInfo.CollectionStatus.COLLECTIONING.getValue(),
                    CaseInfo.CollectionStatus.EARLY_PAYING.getValue(),
                    CaseInfo.CollectionStatus.OVER_PAYING.getValue(),
                    CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue(),
                    CaseInfo.CollectionStatus.PART_REPAID.getValue(),
                    CaseInfo.CollectionStatus.WAITCOLLECTION.getValue())
                    .and(QCaseInfo.caseInfo.companyCode.eq(user.getCompanyCode())));
            List<CaseInfo> accCaseInfoList = new ArrayList<>();
            List<CaseInfo> caseInfoList1 = new ArrayList<>();
            caseInfoList.forEach(single -> accCaseInfoList.add(single));
            ScoreNumbersModel scoreNumbersModel = new ScoreNumbersModel();
            scoreNumbersModel.setTotal(accCaseInfoList.size());
            if (accCaseInfoList.size() > 0) {
                for (CaseInfo caseInfo : accCaseInfoList) {
                    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                    int age = caseInfo.getPersonalInfo().getAge();
                    scoreRuleModel.setAge(age);
                    scoreRuleModel.setOverDueAmount(caseInfo.getOverdueAmount().doubleValue());
                    scoreRuleModel.setOverDueDays(caseInfo.getOverdueDays());
                    if (Objects.nonNull(caseInfo.getArea())) {
                        if (Objects.nonNull(caseInfo.getArea().getParent())) {
                            scoreRuleModel.setProId(caseInfo.getArea().getParent().getId());//省份id
                        }
                    }
                    Personal personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
                    if (Objects.nonNull(personal) && Objects.nonNull(personal.getPersonalJobs())) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }
                    kieSession.insert(scoreRuleModel);//插入
                    kieSession.fireAllRules();//执行规则
                    caseInfo.setScore(new BigDecimal(scoreRuleModel.getCupoScore()));
                    caseInfoList1.add(caseInfo);
                }
                kieSession.dispose();
                caseInfoRepository.save(caseInfoList1);
                watch1.stop();
                log.info("耗时：" + watch1.getTotalTimeMillis());
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分完成", "success")).body(scoreNumbersModel);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseinfo", "failure", "案件为空")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", "上传文件服务器失败")).body(null);
        }
    }


    @GetMapping("/updateInnerWaitCollScore")
    @ApiOperation(value = "内催待分配案件评分", notes = "内催待分配案件评分")
    public ResponseEntity updateInnerWaitCollScore(@RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            String companyCode = user.getCompanyCode();
            StopWatch watch1 = new StopWatch();
            watch1.start();
            KieSession kieSession = null;
            try {
                kieSession = runCaseStrategyService.createSorceRule(companyCode, CaseStrategy.StrategyType.INNER.getValue());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfo.companyCode.eq(companyCode)); //公司
                }
            } else {
                builder.and(qCaseInfo.companyCode.eq(user.getCompanyCode())); //公司
            }
            builder.and(qCaseInfo.department.isNull());
            builder.and(qCaseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()));
            builder.and(qCaseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            builder.and(qCaseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            Iterable<CaseInfo> caseInfoList = caseInfoRepository.findAll(builder);
            List<CaseInfo> accCaseInfoList = new ArrayList<>();
            List<CaseInfo> caseInfoList1 = new ArrayList<>();
            caseInfoList.forEach(single -> accCaseInfoList.add(single));
            ScoreNumbersModel scoreNumbersModel = new ScoreNumbersModel();
            scoreNumbersModel.setTotal(accCaseInfoList.size());
            if (accCaseInfoList.size() > 0) {
                for (CaseInfo caseInfo : accCaseInfoList) {
                    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                    int age = caseInfo.getPersonalInfo().getAge();
                    scoreRuleModel.setAge(age);
                    scoreRuleModel.setOverDueAmount(caseInfo.getOverdueAmount().doubleValue());
                    scoreRuleModel.setOverDueDays(caseInfo.getOverdueDays());
                    if (Objects.nonNull(caseInfo.getArea())) {
                        if (Objects.nonNull(caseInfo.getArea().getParent())) {
                            scoreRuleModel.setProId(caseInfo.getArea().getParent().getId());//省份id
                        }
                    }
                    Personal personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
                    if (Objects.nonNull(personal) && Objects.nonNull(personal.getPersonalJobs())) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }
                    kieSession.insert(scoreRuleModel);//插入
                    kieSession.fireAllRules();//执行规则
                    if (scoreRuleModel.getCupoScore() != 0) {
                        caseInfo.setScore(new BigDecimal(scoreRuleModel.getCupoScore()));
                        caseInfoList1.add(caseInfo);
                    }
                }
                kieSession.dispose();
                caseInfoRepository.save(caseInfoList1);
                watch1.stop();
                log.info("耗时：" + watch1.getTotalTimeMillis());
                if (caseInfoList1.size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseinfo", "failure", "没有符合策略的案件")).body(null);
                }
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分完成", "success")).body(scoreNumbersModel);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseinfo", "failure", "案件为空")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
    }

    @GetMapping("/updateInnerCollectIngScore")
    @ApiOperation(value = "内催催收中案件评分", notes = "内催催收中案件评分")
    public ResponseEntity updateInnerCollectIngScore(@RequestHeader(value = "X-UserToken") String token,
                                                     @RequestParam @ApiParam(value = "批次号", required = true) String batchNumber) {
        try {
            User user = getUserByToken(token);
            String companyCode = user.getCompanyCode();
            StopWatch watch1 = new StopWatch();
            watch1.start();
            KieSession kieSession = null;
            try {
                kieSession = runCaseStrategyService.createSorceRule(companyCode, CaseStrategy.StrategyType.INNER.getValue());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            User tokenUser = getUserByToken(token);
            List<Integer> status = new ArrayList<>();
            status.add(CaseInfo.CollectionStatus.COLLECTIONING.getValue());
            status.add(CaseInfo.CollectionStatus.OVER_PAYING.getValue());
            status.add(CaseInfo.CollectionStatus.EARLY_PAYING.getValue());
            status.add(CaseInfo.CollectionStatus.PART_REPAID.getValue());
            status.add(CaseInfo.CollectionStatus.REPAID.getValue());
            status.add(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue());
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QCaseInfo.caseInfo.batchNumber.eq(batchNumber));
            if (Objects.nonNull(tokenUser.getCompanyCode())) {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode()));
            }
            builder.and(QCaseInfo.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            builder.and(QCaseInfo.caseInfo.department.code.startsWith(tokenUser.getDepartment().getCode()));
            builder.andAnyOf(QCaseInfo.caseInfo.collectionStatus.in(status), QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())
                    .and(QCaseInfo.caseInfo.department.isNotNull()));
            Iterable<CaseInfo> caseInfoList = caseInfoRepository.findAll(builder);
            List<CaseInfo> accCaseInfoList = new ArrayList<>();
            List<CaseInfo> caseInfoList1 = new ArrayList<>();
            caseInfoList.forEach(single -> accCaseInfoList.add(single));
            ScoreNumbersModel scoreNumbersModel = new ScoreNumbersModel();
            scoreNumbersModel.setTotal(accCaseInfoList.size());
            if (accCaseInfoList.size() > 0) {
                for (CaseInfo caseInfo : accCaseInfoList) {
                    ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                    int age = caseInfo.getPersonalInfo().getAge();
                    scoreRuleModel.setAge(age);
                    scoreRuleModel.setOverDueAmount(caseInfo.getOverdueAmount().doubleValue());
                    scoreRuleModel.setOverDueDays(caseInfo.getOverdueDays());
                    if (Objects.nonNull(caseInfo.getArea())) {
                        if (Objects.nonNull(caseInfo.getArea().getParent())) {
                            scoreRuleModel.setProId(caseInfo.getArea().getParent().getId());//省份id
                        }
                    }
                    Personal personal = personalRepository.findOne(caseInfo.getPersonalInfo().getId());
                    if (Objects.nonNull(personal) && Objects.nonNull(personal.getPersonalJobs())) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }
                    kieSession.insert(scoreRuleModel);//插入
                    kieSession.fireAllRules();//执行规则

                    if (scoreRuleModel.getCupoScore() != 0) {
                        caseInfo.setScore(new BigDecimal(scoreRuleModel.getCupoScore()));
                        caseInfoList1.add(caseInfo);
                    }
                }
                kieSession.dispose();
                caseInfoRepository.save(caseInfoList1);
                watch1.stop();
                log.info("耗时：" + watch1.getTotalTimeMillis());
                if (caseInfoList1.size() == 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseinfo", "failure", "没有符合策略的案件")).body(null);
                }
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分完成", "success")).body(scoreNumbersModel);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseinfo", "failure", "案件为空")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "exportCaseInfoFollowRecord", e.getMessage())).body(null);
        }
    }

    @GetMapping("/findUpload")
    @ApiOperation(value = "查看附件", notes = "查看附件")
    public ResponseEntity<List<CaseInfoFile>> findUpload(@RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                         @RequestParam(value = "caseNumber", required = true) @ApiParam("案件编号") String caseNumber,
                                                         @RequestParam(value = "companyCode", required = false) String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "User is not login", "用户未登录")).body(null);
        }
        Iterable<CaseInfoFile> all = caseInfoFileRepository.findAll(QCaseInfoFile.caseInfoFile.caseNumber.eq(caseNumber));
        List<CaseInfoFile> caseInfoFiles = IterableUtils.toList(all);
        return ResponseEntity.ok().body(caseInfoFiles);
    }

    /**
     * @Description 查看凭证
     */
    @GetMapping("/getFollowupFile")
    @ApiOperation(value = "下载凭证", notes = "下载凭证")
    public ResponseEntity<List<UploadFile>> getFollowupFile(@RequestParam @ApiParam(value = "跟进记录ID") String followId) {
        log.debug("REST request to get flowup file");
        try {
            List<UploadFile> caseFlowupFiles = caseInfoService.getFollowupFile(followId);
            if (caseFlowupFiles.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("文件为空", "")).body(caseFlowupFiles);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("下载成功", "")).body(caseFlowupFiles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("下载失败", "uploadFile", "下载失败")).body(null);
        }
    }

    /**
     * @Description 案件查找
     */
    @GetMapping("/findCaseInfo")
    @ApiOperation(value = "案件查找", notes = "案件查找")
    public ResponseEntity<Page<CaseInfo>> findCaseInfo(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                       @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                       @RequestParam(required = false) @ApiParam(value = "逾期天数开始时间") String startOverDueDate,
                                                       @RequestParam(required = false) @ApiParam(value = "逾期天数结束时间") String endOverDueDate,
                                                       @RequestParam(required = false) @ApiParam(value = "出催开始时间") String startSettleDate,
                                                       @RequestParam(required = false) @ApiParam(value = "出催结束时间") String endSettleDate,
                                                       @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                       @ApiIgnore Pageable pageable,
                                                       @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to find case");
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else { //不是超级管理员
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode()));
            }
            if (StringUtils.isNotEmpty(startOverDueDate)) {
                Date date = DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(startOverDueDate), 2);
                builder.and(QCaseInfo.caseInfo.repayDate.after(DateUtil.getEndTimeOfDay(date)));
            }
            if (StringUtils.isNotEmpty(endOverDueDate)) {
                Date date = DateUtil.getDate(endOverDueDate, "yyyy-MM-dd");
                builder.and(QCaseInfo.caseInfo.repayDate.before(DateUtil.getStartTimeOfDay(date)));
            }
            if (StringUtils.isNotEmpty(startSettleDate)) {
                Date date = DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(startSettleDate), 1);
                builder.and(QCaseInfo.caseInfo.settleDate.after(DateUtil.getEndTimeOfDay(date)));
            }
            if (StringUtils.isNotEmpty(endSettleDate)) {
                Date date = DateUtil.addDate(new SimpleDateFormat("yyyy-MM-dd").parse(endSettleDate), 1);
                builder.and(QCaseInfo.caseInfo.settleDate.before(DateUtil.getStartTimeOfDay(date)));
            }
            if (StringUtils.isNotEmpty(idCard)) {
                builder.and(QCaseInfo.caseInfo.personalInfo.certificatesNumber.eq(idCard));
            }

            Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
            List<CaseInfo> list = new ArrayList<>();
            all.forEach(single -> {
                CaseInfo caseInfoObj = new CaseInfo();
                BeanUtils.copyProperties(single, caseInfoObj);
                list.add(caseInfoObj);
            });
            // 合并共债案件
            List<CaseInfo> list1 = new ArrayList<>();
            List<String> caseNums = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (caseNums.contains(list.get(i).getCaseNumber())) {
                    continue;
                }
                caseNums.add(list.get(i).getCaseNumber());
                CaseInfo caseInfo = list.get(i);
                // 设置身份证和地区
                for (int j = i + 1; j < list.size(); j++) {
                    if (caseInfo.getCaseNumber().equals(list.get(j).getCaseNumber())) {
                        if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfo.getOverduePeriods())) {
                            // 判断逾期期数大小
                            if (new BigDecimal(caseInfo.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1) {
                                caseInfo.setOverduePeriods(list.get(j).getOverduePeriods());
                            }
                        }

                        // 合并逾期总金额
                        caseInfo.setOverdueAmount(caseInfo.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                        // 合并到账金额
                        caseInfo.setAccountBalance(caseInfo.getAccountBalance().add(list.get(j).getAccountBalance()));
                    }
                }
                list1.add(caseInfo);
            }
            List<CaseInfo> modeList = list1.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<CaseInfo> page = new PageImpl<>(modeList, pageable, list1.size());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/CaseInfoController/findCaseInfo");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 修改备注
     */
    @PostMapping("/modifyCaseMemo")
    @ApiOperation(value = "修改备注", notes = "修改备注")
    public ResponseEntity<Void> modifyCaseMemo(@RequestBody ModifyMemoParams modifyMemoParams,
                                               @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to modify case memo");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.modifyCaseMemo(modifyMemoParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 案件退案
     */
    @PostMapping("/returnCase")
    @ApiOperation(value = "案件退案", notes = "案件退案")
    public ResponseEntity<Void> returnCase(@RequestBody ReturnCaseParams returnCaseParams,
                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to return case");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.returnCase(returnCaseParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("退案成功", ENTITY_CASEINFO_RETURN)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO_RETURN, "caseInfoReturn", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 多条件查询退案案件
     */
    @GetMapping("/getAllCaseInfoReturn")
    @ApiOperation(value = "多条件查询退案案件", notes = "多条件查询退案案件")
    public ResponseEntity<Page<CaseInfoReturn>> getAllCaseInfoReturn(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                     @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                                     @ApiIgnore Pageable pageable,
                                                                     @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get all case info return");
        Sort.Order operatorTimeSort = new Sort.Order(Sort.Direction.DESC, "operatorTime", Sort.NullHandling.NULLS_LAST); //操作时间倒序
        Sort.Order personalNameSort = new Sort.Order(Sort.Direction.ASC, "caseId.personalInfo.name", Sort.NullHandling.NULLS_LAST); //客户姓名正序
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) { //超级管理员
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfoReturn.caseInfoReturn.caseId.companyCode.eq(companyCode));
                }
            } else { //不是超级管理员
                builder.and(QCaseInfoReturn.caseInfoReturn.caseId.companyCode.eq(tokenUser.getCompanyCode()));
            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(operatorTimeSort)).and(new Sort(personalNameSort)));
            Page<CaseInfoReturn> page = caseInfoReturnRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/CaseInfoController/getAllCaseInfoReturn");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO_RETURN, "caseInfoReturn", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询备注信息
     */
    @GetMapping("/getCaseInfoRemark")
    @ApiOperation(value = "查询备注信息", notes = "查询备注信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfoRemark>> getCaseInfoRemark(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId,
                                                                  @QuerydslPredicate(root = CaseInfoRemark.class) Predicate predicate,
                                                                  @ApiIgnore Pageable pageable) {
        log.debug("REST request to get case info remark");
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            QCaseInfoRemark qCaseInfoRemark = QCaseInfoRemark.caseInfoRemark;
            builder.and(qCaseInfoRemark.caseId.eq(caseId)); //案件ID
            builder.and(qCaseInfoRemark.remark.isNotEmpty());
            builder.and(qCaseInfoRemark.remark.isNotNull());
            builder.and(qCaseInfoRemark.remark.trim().ne(""));
            Page<CaseInfoRemark> page = caseInfoRemarkRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caseInfoController/getCaseInfoRemark");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO_REMARK, "caseInfoRemark", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询共债备注信息
     */
    @GetMapping("/getAllCaseInfoRemark")
    @ApiOperation(value = "查询共债案件备注信息", notes = "查询共债案件备注信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfoRemark>> getAllCaseInfoRemark(@RequestParam @ApiParam(value = "案件ids", required = true) List<String> ids,
                                                                     @QuerydslPredicate(root = CaseInfoRemark.class) Predicate predicate,
                                                                     @ApiIgnore Pageable pageable) {
        log.debug("REST request to get case info remark");
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            QCaseInfoRemark qCaseInfoRemark = QCaseInfoRemark.caseInfoRemark;
            builder.and(qCaseInfoRemark.caseId.in(ids)); //案件ID
            builder.and(qCaseInfoRemark.remark.isNotEmpty());
            builder.and(qCaseInfoRemark.remark.isNotNull());
            builder.and(qCaseInfoRemark.remark.trim().ne(""));
            // 共债案件合并
            Iterable<CaseInfoRemark> all = caseInfoRemarkRepository.findAll(builder);
            List<CaseInfoRemark> list = new ArrayList<>();
            if (all.iterator().hasNext()) {
                all.forEach(obj -> {
                    list.add(obj);
                });
            }
            Page<CaseInfoRemark> page = new PageImpl<>(list, pageable, list.size());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caseInfoController/getCaseInfoRemark");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO_REMARK, "caseInfoRemark", "查询失败")).body(null);
        }
    }

    @PostMapping("/moveToDistribution")
    @ApiOperation(value = "移入待分配案件池", notes = "移入待分配案件池")
    public ResponseEntity moveToDistribution(@RequestHeader(value = "X-UserToken") String token,
                                             @RequestBody @ApiParam(value = "案件ID集合", required = true) CaseInfoIdList caseIds) {
        log.debug("REST request to moveToDistribution");
        try {
            User user = getUserByToken(token);
            caseInfoService.moveToDistribution(caseIds, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功!", "")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 内催按批次号查询催收中案件
     */
    @GetMapping("/getCollectingCase")
    @ApiOperation(value = "内催按批次号查询催收中案件", notes = "内催按批次号查询催收中案件")
    public ResponseEntity<Page<CaseInfo>> getCollectingCase(@RequestHeader(value = "X-UserToken") String token,
                                                            @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                            @ApiIgnore Pageable pageable,
                                                            @RequestParam @ApiParam(value = "批次号", required = true) String batchNumber) {
        log.debug("REST request to get case info remark");
        try {
            User tokenUser = getUserByToken(token);
            List<Integer> status = new ArrayList<>();
            status.add(CaseInfo.CollectionStatus.COLLECTIONING.getValue());
            status.add(CaseInfo.CollectionStatus.OVER_PAYING.getValue());
            status.add(CaseInfo.CollectionStatus.EARLY_PAYING.getValue());
            status.add(CaseInfo.CollectionStatus.PART_REPAID.getValue());
            status.add(CaseInfo.CollectionStatus.REPAID.getValue());
            status.add(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue());
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseInfo.caseInfo.batchNumber.eq(batchNumber));
            if (Objects.nonNull(tokenUser.getCompanyCode())) {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode()));
            }
            builder.and(QCaseInfo.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            builder.and(QCaseInfo.caseInfo.department.code.startsWith(tokenUser.getDepartment().getCode()));
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            builder.andAnyOf(QCaseInfo.caseInfo.collectionStatus.in(status), QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue())
                    .and(QCaseInfo.caseInfo.department.isNotNull()));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    @GetMapping("/findAllCaseInfoReturn")
    @ApiOperation(notes = "内催案件管理查询回收案件", value = "内催案件管理查询回收案件")
    public ResponseEntity<Page<CaseInfoReturn>> findAllCaseInfoReturn(@RequestHeader(value = "X-UserToken") String token,
                                                                      @ApiIgnore Pageable pageable,
                                                                      @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode,
                                                                      @QuerydslPredicate(root = CaseInfoReturn.class) Predicate predicate) {
        try {
            User user = getUserByToken(token);
            QCaseInfoReturn qCaseInfoReturn = QCaseInfoReturn.caseInfoReturn;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfoReturn.caseId.companyCode.eq(companyCode));//公司
                }
            } else {
                builder.and(qCaseInfoReturn.caseId.companyCode.eq(user.getCompanyCode()));//公司
            }
//            builder.and(qCaseInfoReturn.caseId.department.code.startsWith(user.getDepartment().getCode())); //huyanmin-部门code去过滤不同部门之间的回收案件 回收案件先不做权限控制。
            builder.and(qCaseInfoReturn.caseId.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));//内催
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "operatorTime"));
            Page<CaseInfoReturn> all = caseInfoReturnRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(all);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    @PostMapping("/verifyApply")
    @ApiOperation(notes = "内催案件管理案件回收核销申请", value = "内催案件管理案件回收核销申请")
    public ResponseEntity verifyApply(@RequestHeader(value = "X-UserToken") String token,
                                      @RequestBody @ApiParam(value = "回收案件ID集合", required = true) VerificationApplyModel model) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(model.getIds()) || model.getIds().isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择要核销的案件!")).body(null);
            }
            BooleanBuilder builder = new BooleanBuilder();
            QCaseInfoReturn qCaseInfoReturn = QCaseInfoReturn.caseInfoReturn;
            builder.and(qCaseInfoReturn.id.in(model.getIds()));
            Iterable<CaseInfoReturn> all = caseInfoReturnRepository.findAll(builder);
            Iterator<CaseInfoReturn> iterator = all.iterator();
            while (iterator.hasNext()) {
                CaseInfoReturn next = iterator.next();
                CaseInfoVerificationApply apply = new CaseInfoVerificationApply();
                caseInfoVerificationService.setVerificationApply(apply, next.getCaseId(), user, model.getReason());
                caseInfoVerificationApplyRepository.save(apply);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("申请成功!", "")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "申请失败!")).body(null);
        }
    }

    @GetMapping("/getInnerWaitCollectCase")
    @ApiOperation(value = "分页查询内催待分配案件", notes = "分页查询内催待分配案件")
    public ResponseEntity<Page<CaseInfo>> getInnerWaitCollectCase(
            @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
            @ApiIgnore Pageable pageable,
            @RequestHeader(value = "X-UserToken") String token,
            @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode) {
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", e.getMessage())).body(null);
        }
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfo.companyCode.eq(companyCode)); //公司
                }
            } else {
                builder.and(qCaseInfo.companyCode.eq(user.getCompanyCode())); //公司
            }
            builder.and(qCaseInfo.department.isNull());
            builder.and(qCaseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()));
            builder.and(qCaseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            builder.and(qCaseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "查询失败")).body(null);
        }
    }

    @GetMapping("/getInnerOverCase")
    @ApiOperation(value = "分页查询内催结案案件", notes = "分页查询内催结案案件")
    public ResponseEntity<Page<CaseInfo>> getInnerOverCase(
            @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
            @ApiIgnore Pageable pageable,
            @RequestHeader(value = "X-UserToken") String token,
            @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode) {
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", e.getMessage())).body(null);
        }
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qCaseInfo.companyCode.eq(companyCode)); //公司
                }
            } else {
                builder.and(qCaseInfo.companyCode.eq(user.getCompanyCode())); //公司
            }
            builder.and(qCaseInfo.department.code.startsWith(user.getDepartment().getCode()));
            builder.and(qCaseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
            builder.and(qCaseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            builder.and(qCaseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "查询失败")).body(null);
        }
    }

    /**
     * 内催案件 待分配案件 策略分配
     */
    @GetMapping("/innerStrategyDistribute")
    @ApiOperation(value = "内催案件 待分配案件 策略分配", notes = "内催案件 待分配案件 策略分配")

    public ResponseEntity innerStrategyDistribute(@RequestHeader(value = "X-UserToken") String token,
                                                  CaseInfoDistributeParams caseInfoDistributeParams) {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("开始分配。。。");
        CaseInfoInnerStrategyResultModel caseInfoInnerStrategyResultModel = null;
        User user = null;
        try {
            user = getUserByToken(token);
            ParameterizedTypeReference<List<CaseStrategy>> responseType = new ParameterizedTypeReference<List<CaseStrategy>>() {
            };
            ResponseEntity<List<CaseStrategy>> caseStrategies = restTemplate.exchange(Constants.CASE_STRATEGY_URL
                    .concat("companyCode=").concat(user.getCompanyCode())
                    .concat("&strategyType=").concat(CaseStrategy.StrategyType.INNER.getValue().toString()), HttpMethod.GET, null, responseType);
            if (Objects.isNull(caseStrategies) || !caseStrategies.hasBody() || caseStrategies.getBody().size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "没有分配策略信息")).body(null);
            }
            List<CaseInfoDistributedModel> caseInfos = new ArrayList<>();
            caseInfoDistributeParams.setCompanyCode(user.getCompanyCode());
            List<Object[]> objects = entityManageService.getCaseInfoDistribute(caseInfoDistributeParams, "case_info");
            caseInfos = caseInfoService.findModelList(objects, caseInfos);
            if (Objects.isNull(caseInfos) || caseInfos.size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "没有待分配的案件信息")).body(null);
            }
            strategyDistributeService.innerCountStrategyAllocation(caseStrategies.getBody(), IterableUtils.toList(caseInfos), user);
            caseInfoDistributedService.reminderMessage(user.getId(), "策略分配成功", "策略分配成功提醒");
            watch.stop();
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "", "分配失败")).body(null);
        }
    }

    /**
     * @Description 查询共债案件数量
     */
    @GetMapping("/getCommonCaseCount")
    @ApiOperation(value = "查询共债案件数量", notes = "查询共债案件数量")
    public ResponseEntity<CommonCaseCountModel> getCommonCaseCount(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId,
                                                                   @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get common case count");
        try {
            User user = getUserByToken(token);
            CommonCaseCountModel commonCaseCountModel = caseInfoService.getCommonCaseCount(caseId, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "")).body(commonCaseCountModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 案件到期提醒
     */
    @GetMapping("/maturityRemind")
    @ApiOperation(value = "案件到期提醒", notes = "案件到期提醒")
    public ResponseEntity<CommonCaseCountModel> maturityRemind(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId,
                                                               @RequestHeader(value = "X-UserToken") String token) {
        try {
            User tokenUser = getUserByToken(token);
            CommonCaseCountModel commonCaseCountModel = caseInfoService.maturityRemind(caseId, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("提醒成功", ENTITY_NAME)).body(commonCaseCountModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("提醒成功", ENTITY_NAME)).body(null);
        }
    }

    /**
     * @Description 一键审批提前流转
     */
    @PostMapping("/approveAllAdvanceTurn")
    @ApiOperation(value = "一键审批提前流转", notes = "一键审批提前流转")
    public ResponseEntity<Void> approvedAllAdvanceTurn(@RequestBody PaymentParams paymentParams, @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to approve all advance turn");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.approveAllAdvanceTurn(paymentParams.getResult(), paymentParams.getFlag(), paymentParams.getOpinion(), tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("审批成功", ENTITY_NAME)).body(null);
        } catch (GeneralException ge) {
            log.error(ge.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "caseInfo", ge.getMessage())).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "caseInfo", "审批失败")).body(null);
        }
    }

    /**
     * @Description 撤销分案 根据批次号 整批撤销
     */
    @GetMapping("/revertCaseInfoDistribute")
    @ApiOperation(value = "撤销分案", notes = "撤销分案")
    public ResponseEntity<Void> revertCaseInfoDistribute(@RequestParam String batchNumber, @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to revoke case distribute {}", batchNumber);
        if (ZWStringUtils.isEmpty(batchNumber)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", "请选择批次信息")).body(null);
        }
        try {
            User user = getUserByToken(token);
            caseInfoService.revokeCaseDistribute(batchNumber, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("", ENTITY_CASE_DISTRIBUTED_TEMPORARY)).body(null);
        } catch (GeneralException ge) {
            log.error(ge.getMessage(), ge);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", ge.getMessage())).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", "撤销失败")).body(null);
        }
    }

    /**
     * @Description 根据案件ID撤销分案
     */
    @PostMapping("/revertCaseInfoDistributeByCaseId")
    @ApiOperation(value = "撤销分案", notes = "撤销分案")
    public ResponseEntity<Void> revertCaseInfoDistributeByCaseId(@RequestBody CaseInfoIdList caseInfoIdList, @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to revoke case distribute {}", caseInfoIdList);
        if (Objects.isNull(caseInfoIdList) || caseInfoIdList.getIds().isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", "请选择批次信息")).body(null);
        }
        try {
            User user = getUserByToken(token);
            caseInfoService.revertCaseInfoDistributeByCaseId(caseInfoIdList, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("", ENTITY_CASE_DISTRIBUTED_TEMPORARY)).body(null);
        } catch (GeneralException ge) {
            log.error(ge.getMessage(), ge);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", ge.getMessage())).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_DISTRIBUTED_TEMPORARY, "", "撤销失败")).body(null);
        }
    }

    /**
     * 案件分析返回值
     */
    @GetMapping("/analyzeCaseinfoReturn")
    @ApiOperation(value = "案件分析返回值", notes = "案件分析返回值")
    public ResponseEntity<JSONObject> analyzeCaseinfoReturn(@RequestParam(value = "caseNumber", required = false) String caseNumber,
                                                            @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            QCaseInfoLearning qCaseInfoLearning = QCaseInfoLearning.caseInfoLearning;
            CaseInfoLearning caseInfoLearning = caseInfoLearningRepository.findOne(qCaseInfoLearning.caseNumber.eq(caseNumber).and(qCaseInfoLearning.companyCode.eq(user.getCompanyCode())));
            if (Objects.isNull(caseInfoLearning.getMachineLearningResult())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "此案件无案件分析结果!")).body(null);
            }
            JSONObject jsonObjectReturn1 = JSONObject.fromObject(caseInfoLearning.getMachineLearningResult());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(jsonObjectReturn1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfo", "caseInfo", "此案件无分析结果!")).body(null);
        }
    }

    /**
     * 案件分析返回催收员和匹配率
     */
    @GetMapping("/analyzeCaseinfoReturnUser")
    @ApiOperation(value = "案件分析返回催收员和匹配率", notes = "案件分析返回催收员和匹配率")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页大小."),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "依据什么排序: 属性名(,asc|desc). ", allowMultiple = true)
    })
    public ResponseEntity<Page> analyzeCaseinfoReturnUser(@RequestParam(value = "caseNumber", required = false) String caseNumber,
                                                          @ApiIgnore Pageable pageable,
                                                          @RequestHeader(value = "X-UserToken") String token) {
        try {
            User userToken = getUserByToken(token);
            QCaseInfoLearning qCaseInfoLearning = QCaseInfoLearning.caseInfoLearning;
            CaseInfoLearning caseInfoLearning = caseInfoLearningRepository.findOne(qCaseInfoLearning.caseNumber.eq(caseNumber).and(qCaseInfoLearning.companyCode.eq(userToken.getCompanyCode())));
            if (Objects.isNull(caseInfoLearning.getMachineLearningResult())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "此案件无分析结果!")).body(null);
            }
            JSONObject jsonObjectReturn1 = JSONObject.fromObject(caseInfoLearning.getMachineLearningResult());
            JSONArray collectorProb = jsonObjectReturn1.getJSONArray("collectorProb");
            Object[] objects = collectorProb.toArray();
            List<CaseAnalysisReturn> list = new ArrayList<>();
            for (int i = 0; i < objects.length; i++) {
                String collectorId = JSONObject.fromObject(objects[i].toString()).get("collectorId").toString();
                User user = userRepository.findOne(collectorId);
                String realName = user.getRealName();
                String probability = JSONObject.fromObject(objects[i].toString()).get("probability").toString();
                CaseAnalysisReturn caseAnalysisReturn = new CaseAnalysisReturn();
                caseAnalysisReturn.setName(realName);
                caseAnalysisReturn.setProbability(probability);
                list.add(caseAnalysisReturn);
            }
            list.sort((o1, o2) -> o2.getProbability().compareTo(o1.getProbability()));
            Page page = new PageImpl<>(list, pageable, list.size());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caseInfoController/analyzeCaseinfoReturnUser");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoController", "caseInfoController", "此案件无分析结果!")).body(null);
        }
    }
}


