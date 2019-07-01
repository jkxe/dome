package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.file.UploadFile;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import cn.fintecher.pangolin.model.AccCaseInfoDisModel;
import cn.fintecher.pangolin.model.BatchDistributeModel;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import com.google.common.base.Strings;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : xiaqun
 * @Description : 电催相关业务
 * @Date : 16:01 2017/7/17
 */

@RestController
@RequestMapping("/api/AccTelPoolController")
@Api(value = "AccTelPoolController", description = "电催页面接口")
public class AccTelPoolController extends BaseController {
    final Logger log = LoggerFactory.getLogger(AccTelPoolController.class);

    private static final String ENTITY_CASEINFO = "CaseInfo";

    private static final String ENTITY_CASE_PAY_APPLY = "CasePayApply";

    private static final String ENTITY_CASE_FOLLOWUP_RECORD = "CaseFollowupRecord";

    private static final String ENTITY_CASE_ASSIST_APPLY = "CaseAssistApply";

    private static final String ENTITY_PERSONAL_CONTACT = "PersonalContact";

    private static final String ENTITY_UPLOAD_FILE = "UploadFile";

    private static final String ENTITY_SEND_MESSAGE_RECORD = "SendMessageRecord";

    @Inject
    CaseInfoService caseInfoService;

    @Inject
    CaseInfoRepository caseInfoRepository;

    @Inject
    CasePayApplyRepository casePayApplyRepository;

    @Inject
    CaseFollowupRecordRepository caseFollowupRecordRepository;

    @Inject
    CaseAssistApplyRepository caseAssistApplyRepository;

    @Inject
    SendMessageRecordRepository sendMessageRecordRepository;

    @Inject
    PersonalContactRepository personalContactRepository;

    @Inject
    CaseAdvanceTurnApplayRepository caseAdvanceTurnApplayRepository;

    @Inject
    UserRepository userRepository;


    /**
     * @Description 电催案件重新分配
     */
    @PostMapping("/telCaseDistribution")
    @ApiOperation(value = "电催案件重新分配", notes = "电催案件重新分配")
    public ResponseEntity<Void> telCaseDistribution(@RequestBody ReDistributionParams reDistributionParams,
                                                    @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to reDistribute");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.reDistribution(reDistributionParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("分配成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "user", e.getMessage())).body(null);
        }
    }

//    /**
//     * @Description 电催详情页面的客户信息
//     */
//    @GetMapping("/getTelCustInfo")
//    @ApiOperation(value = "电催详情页面的客户信息", notes = "电催详情页面的客户信息")
//    public ResponseEntity<Personal> getTelCustInfo(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId) {
//        log.debug("REST request to get customer information ");
//        try {
//            Personal personal = caseInfoService.getCustInfo(caseId);
//            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取客户信息成功", ENTITY_PERSONAL)).body(personal);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取客户信息失败", "user", e.getMessage())).body(null);
//        }
//    }


    /**
     * @Description 通过案件ID获得案件信息
     */
    @GetMapping("/getCaseInfoByCaseId")
    @ApiOperation(value = "通过案件ID获得案件信息", notes = "通过案件ID获得案件信息")
    public ResponseEntity<CaseInfo> getCaseInfoByCaseId(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId) {
        log.debug("REST request to get caseInfo by {caseId}", caseId);
        try {
            CaseInfo caseInfo = caseInfoRepository.findOne(caseId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取案件信息成功", ENTITY_CASEINFO)).body(caseInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "获取案件信息失败")).body(null);
        }
    }

    /**
     * @Description 通过案件号获得案件号下的所有案件信息
     */
    @GetMapping("/getAllCaseInfoByCaseNum")
    @ApiOperation(value = "通过案件号获得案件号下的所有案件信息", notes = "通过案件号获得案件号下的所有案件信息")
    public ResponseEntity<List<CaseInfo>> getAllCaseInfoByCaseNum(@RequestParam @ApiParam(value = "案件号", required = true) String caseNumber) {
        log.debug("REST request to get caseInfo by {}", caseNumber);
        try {

            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QCaseInfo.caseInfo.caseNumber.eq(caseNumber));
            Iterable<CaseInfo> all = caseInfoRepository.findAll(builder);
            List<CaseInfo> list = new ArrayList<>();
            all.forEach(obj->{list.add(obj);});
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取案件信息成功", ENTITY_CASEINFO)).body(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "通过案件号获得案件号下的所有案件信息")).body(null);
        }
    }


    /**
     * @Description 电催页面申请还款操作
     */
    @PostMapping("/doTelPay")
    @ApiOperation(value = "电催页面还款操作", notes = "电催页面还款操作")
    public ResponseEntity<Void> doTelPay(@RequestBody PayApplyParams payApplyParams,
                                         @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to apply payment");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.doPay(payApplyParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("还款操作成功", ENTITY_CASE_PAY_APPLY)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_PAY_APPLY, "casePayApply", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面还款撤回
     */
    @GetMapping("/telWithdraw")
    @ApiOperation(value = "电催页面还款撤回", notes = "电催页面还款撤回")
    public ResponseEntity<Void> telWithdraw(@RequestParam @ApiParam(value = "还款审批ID", required = true) String payApplyId,
                                            @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to withdraw by {payApplyId}", payApplyId);
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.payWithdraw(payApplyId, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("还款撤回成功", ENTITY_CASE_PAY_APPLY)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_PAY_APPLY, "casePayApply", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询还款记录
     */
    @GetMapping("/getPaymentRecord")
    @ApiOperation(value = "电催页面多条件查询还款记录", notes = "电催页面多条件查询还款记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CasePayApply>> getPaymentRecord(@RequestParam @ApiParam(value = "案件ID", required = true) String caseId,
                                                               @QuerydslPredicate(root = CasePayApply.class) Predicate predicate,
                                                               @ApiIgnore Pageable pageable) {
        log.debug("REST request to get payment records by {caseId}", caseId);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCasePayApply.casePayApply.caseId.eq(caseId)); //只查当前案件的还款记录
            Page<CasePayApply> page = casePayApplyRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getPaymentRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_PAY_APPLY, "casePayApply", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面添加跟进记录
     */
    @PostMapping("/saveFollowupRecord")
    @ApiOperation(value = "电催页面添加跟进记录", notes = "电催页面添加跟进记录")
    public ResponseEntity<CaseFollowupRecord> saveFollowupRecord(@RequestBody CaseFollowupParams caseFollowupParams,
                                                                 @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to save {caseFollowupRecord}", caseFollowupParams);
        try {
            User tokenUser = getUserByToken(token);
            CaseFollowupRecord result = caseInfoService.saveFollowupRecord(caseFollowupParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("跟进记录添加成功", ENTITY_CASE_FOLLOWUP_RECORD)).body(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_FOLLOWUP_RECORD, "caseFollowupRecord", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询跟进记录
     */
    @GetMapping("/getFollowupRecord/{caseNumber}")
    @ApiOperation(value = "电催页面多条件查询跟进记录", notes = "电催页面多条件查询跟进记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseFollowupRecord>> getFollowupRecord(@PathVariable @ApiParam(value = "案件编号", required = true) String caseNumber,
                                                                      @QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                                                      @ApiIgnore Pageable pageable) {
        log.debug("REST request to get case followup records by {caseNumber}", caseNumber);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseFollowupRecord.caseFollowupRecord.caseNumber.eq(caseNumber));
//          builder.and(QCaseFollowupRecord.caseFollowupRecord.collectionWay.eq(1)); //只查催记方式为手动的
            Page<CaseFollowupRecord> page = caseFollowupRecordRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getFollowupRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_FOLLOWUP_RECORD, "caseFollowupRecord", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询共债案件跟进记录
     */
    @GetMapping("/getAllFollowupRecord/{personalId}")
    @ApiOperation(value = "电催页面多条件查询共债案件跟进记录", notes = "电催页面多条件查询共债案件跟进记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseFollowupRecord>> getAllFollowupRecord(@PathVariable @ApiParam(value = "案件编号", required = true) String personalId,
                                                                         @QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                                                         @ApiIgnore Pageable pageable) {
        log.debug("REST request to get case followup records by {caseNumber}", personalId);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseFollowupRecord.caseFollowupRecord.personalId.eq(personalId));
//          builder.and(QCaseFollowupRecord.caseFollowupRecord.collectionWay.eq(1)); //只查催记方式为手动的
//            List<CaseFollowupRecord> list = new ArrayList<>();
//            List<CaseFollowupRecord> list1 = new ArrayList<>();
//            Iterable<CaseFollowupRecord> all = caseFollowupRecordRepository.findAll(builder);
//            if (all.iterator().hasNext()){
//                all.forEach(obj->{list.add(obj);});
//                // 合并共债案件
//                List<String> caseNums = new ArrayList<>();
//                for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
//                    if (caseNums.contains(list.get(i).getCaseNumber())){
//                        continue;
//                    }
//                    caseNums.add(list.get(i).getCaseNumber());
//                    CaseFollowupRecord caseFollowupRecord = list.get(i);
//                    list1.add(caseFollowupRecord);
//                }
//            }
//            Page<CaseFollowupRecord> page = new PageImpl<>(list1, pageable,list1.size() );
            Page<CaseFollowupRecord> page = caseFollowupRecordRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getFollowupRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_FOLLOWUP_RECORD, "caseFollowupRecord", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催主页面多条件查询电催案件
     */
    @GetMapping("/getAllTelCase")
    @ApiOperation(value = "电催主页面多条件查询电催案件", notes = "电催主页面多条件查询电催案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllTelCase(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                        @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                        @ApiIgnore Pageable pageable,
                                                        @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get all tel case");

        Sort.Order followupBackOrder1 = new Sort.Order(Sort.Direction.ASC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupBackOrder2 = new Sort.Order(Sort.Direction.DESC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupTime1 = new Sort.Order(Sort.Direction.ASC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间正序
        Sort.Order followupTime2 = new Sort.Order(Sort.Direction.DESC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间倒序
        Sort.Order color = new Sort.Order(Sort.Direction.DESC, "caseMark", Sort.NullHandling.NULLS_LAST); //案件打标
        Sort.Order personalName = new Sort.Order(Sort.Direction.ASC, "personalInfo.name"); //客户姓名正序
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            if (Objects.equals(tokenUser.getManager(), 1)) {
                builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            } else {
                builder.and(QCaseInfo.caseInfo.currentCollector.id.eq(tokenUser.getId()));
            }
//            builder.and(QCaseInfo.caseInfo.caseType.in(CaseInfo.CaseType.DISTRIBUTE.getValue(), CaseInfo.CaseType.PHNONELEAVETURN.getValue())); //只查案件类型为案件分配的
            builder.and(QCaseInfo.caseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())); //只查询电催案件
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())); //只查询没有回收的案件
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder1));
            }
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder2));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime1));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime2));
            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(color)).and(new Sort(personalName)));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllTelCase");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询电催待催收案件
     */
    @GetMapping("/getAllTelWaitCollection")
    @ApiOperation(value = "多条件查询电催待催收案件", notes = "多条件查询电催待催收案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllTelWaitCollection(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                  @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                                  @ApiIgnore Pageable pageable,
                                                                  @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get all wait collect tel case");
        Sort.Order followupBackOrder1 = new Sort.Order(Sort.Direction.ASC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupBackOrder2 = new Sort.Order(Sort.Direction.DESC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupTime1 = new Sort.Order(Sort.Direction.ASC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间正序
        Sort.Order followupTime2 = new Sort.Order(Sort.Direction.DESC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间倒序
        Sort.Order color = new Sort.Order(Sort.Direction.DESC, "caseMark", Sort.NullHandling.NULLS_LAST); //案件打标
        Sort.Order personalName = new Sort.Order(Sort.Direction.ASC, "personalInfo.name"); //客户姓名正序
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            if (Objects.equals(tokenUser.getManager(), 1)) {
                builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            } else {
                builder.and(QCaseInfo.caseInfo.currentCollector.id.eq(tokenUser.getId()));
            }
            builder.and(QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.WAITCOLLECTION.getValue())); //只查待催收的案件
            builder.and(QCaseInfo.caseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())); //只查询电催案件
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())); //只查询没有回收的案件
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder1));
            }
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder2));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime1));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime2));
            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(color)).and(new Sort(personalName)));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllTelWaitCollection");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询电催催收中和还款中案件
     */
    @GetMapping("/getAllTelCollecting")
    @ApiOperation(value = "多条件查询电催催收中案件", notes = "多条件查询电催催收中案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllTelCollecting(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                              @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                              @ApiIgnore Pageable pageable,
                                                              @RequestHeader(value = "X-UserToken") String token,
                                                              @RequestParam(required = false) @ApiParam(value = "案件状态") String status) {
        log.debug("REST request to get all collecting tel case");
        Sort.Order followupBackOrder1 = new Sort.Order(Sort.Direction.ASC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupBackOrder2 = new Sort.Order(Sort.Direction.DESC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupTime1 = new Sort.Order(Sort.Direction.ASC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间正序
        Sort.Order followupTime2 = new Sort.Order(Sort.Direction.DESC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间倒序
        Sort.Order color = new Sort.Order(Sort.Direction.DESC, "caseMark", Sort.NullHandling.NULLS_LAST); //案件打标
        Sort.Order personalName = new Sort.Order(Sort.Direction.ASC, "personalInfo.name"); //客户姓名正序
        try {
            if (Objects.isNull(status)) {
                Page<CaseInfo> page = new PageImpl<>(new ArrayList<>(), pageable, 0);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllTelCollecting");
                return new ResponseEntity<>(page, headers, HttpStatus.OK);
            }
            List<Integer> statusList = new ArrayList<>();
            String str[] = StringUtils.split(status, ",");
            for (String aStr : str) {
                if (!StringUtils.equals(StringUtils.trim(aStr), "")) {
                    statusList.add(Integer.parseInt(aStr));
                }
            }
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            if (Objects.equals(tokenUser.getManager(), 1)) {
                builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            } else {
                builder.and(QCaseInfo.caseInfo.currentCollector.id.eq(tokenUser.getId()));
            }
            builder.and(QCaseInfo.caseInfo.collectionStatus.in(statusList)); //只查传入的案件状态的案件
            builder.and(QCaseInfo.caseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())); //只查询电催案件
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())); //只查询没有回收的案件
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder1));
            }
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder2));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime1));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime2));
            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(color)).and(new Sort(personalName)));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllTelCollecting");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询电催待结案案件
     */
    @GetMapping("/getAllTelWaitEnd")
    @ApiOperation(value = "多条件查询电催待结案案件", notes = "多条件查询电催待结案案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllTelWaitEnd(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                           @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                           @ApiIgnore Pageable pageable,
                                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get all collecting tel case");
        Sort.Order followupBackOrder1 = new Sort.Order(Sort.Direction.ASC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupBackOrder2 = new Sort.Order(Sort.Direction.DESC, "followupBack", Sort.NullHandling.NULLS_LAST); //催收反馈默认排序
        Sort.Order followupTime1 = new Sort.Order(Sort.Direction.ASC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间正序
        Sort.Order followupTime2 = new Sort.Order(Sort.Direction.DESC, "followupTime", Sort.NullHandling.NULLS_LAST); //跟进时间倒序
        Sort.Order color = new Sort.Order(Sort.Direction.DESC, "caseMark", Sort.NullHandling.NULLS_LAST); //案件打标
        Sort.Order personalName = new Sort.Order(Sort.Direction.ASC, "personalInfo.name"); //客户姓名正序
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            if (Objects.equals(tokenUser.getManager(), 1)) {
                builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            } else {
                builder.and(QCaseInfo.caseInfo.currentCollector.id.eq(tokenUser.getId()));
            }
            builder.and(QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.REPAID.getValue())); //只查案件状态为已还款的案件
            builder.and(QCaseInfo.caseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())); //只查询电催案件
            builder.and(QCaseInfo.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue())); //只查询没有回收的案件
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder1));
            }
            if (pageable.getSort().toString().contains("followupBack") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupBackOrder2));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("ASC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime1));
            }
            if (pageable.getSort().toString().contains("followupTime") && pageable.getSort().toString().contains("DESC")) {
                pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(followupTime2));
            }
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(new Sort(color)).and(new Sort(personalName)));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllTelWaitEnd");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询电催已处理记录
     */
    @GetMapping("/getAllHandleTelCase")
    @ApiOperation(value = "多条件查询电催已处理记录", notes = "多条件查询电催已处理记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfo>> getAllHandleTelCase(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                              @QuerydslPredicate(root = CaseInfo.class) Predicate predicate,
                                                              @ApiIgnore Pageable pageable,
                                                              @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get all handle tel case");
        try {
            User tokenUser = getUserByToken(token);
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(QCaseInfo.caseInfo.companyCode.eq(companyCode));
                }
            } else {
                builder.and(QCaseInfo.caseInfo.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            if (Objects.equals(tokenUser.getManager(), 1)) {
                builder.and(QCaseInfo.caseInfo.currentCollector.department.code.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            } else {
                builder.and(QCaseInfo.caseInfo.currentCollector.id.eq(tokenUser.getId()));
            }
            builder.and(QCaseInfo.caseInfo.collectionStatus.eq(CaseInfo.CollectionStatus.CASE_OVER.getValue())); //只查询已结案案件
            builder.and(QCaseInfo.caseInfo.collectionType.eq(CaseInfo.CollectionType.TEL.getValue())); //只查询电催案件
            builder.and(QCaseInfo.caseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue()));
            Page<CaseInfo> page = caseInfoRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllHandleTelCase");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催案件结案
     */
    @GetMapping("/endTelCase")
    @ApiOperation(value = "电催案件结案", notes = "电催案件结案")
    public ResponseEntity<Void> endTelCase(EndCaseParams endCaseParams,
                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to end case by {endCaseParams}", endCaseParams);
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.endCase(endCaseParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("结案成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 协催申请
     */
    @PostMapping("/assistApply")
    @ApiOperation(value = "协催申请", notes = "协催申请")
    public ResponseEntity<Void> assistApply(@RequestBody AssistApplyParams assistApplyParams,
                                            @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to save assist apply");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.saveAssistApply(assistApplyParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("协催申请成功", ENTITY_CASE_ASSIST_APPLY)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_ASSIST_APPLY, "caseAssistApply", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询协催记录
     */
    @GetMapping("/getAllAssistApplyRecord")
    @ApiOperation(value = "电催页面多条件查询协催记录", notes = "电催页面多条件查询协催记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseAssistApply>> getAllAssistApplyRecord(@QuerydslPredicate(root = CaseAssistApply.class) Predicate predicate,
                                                                         @ApiIgnore Pageable pageable,
                                                                         @RequestParam @ApiParam(value = "案件ID", required = true) String caseId) {
        log.debug("REST request to get all assist apply record by {caseId}", caseId);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseAssistApply.caseAssistApply.caseId.eq(caseId)); //只查当前案件的协催申请记录
            Page<CaseAssistApply> page = caseAssistApplyRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllAssistApplyRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_ASSIST_APPLY, "caseAssistApply", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询共债协催记录
     */
    @GetMapping("/getAssistApplyRecord")
    @ApiOperation(value = "电催页面多条件查询共债协催记录", notes = "电催页面多条件查询共债协催记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseAssistApply>> getAssistApplyRecord(@QuerydslPredicate(root = CaseAssistApply.class) Predicate predicate,
                                                                      @ApiIgnore Pageable pageable,
                                                                      @RequestParam @ApiParam(value = "案件号", required = true) String caseNumber) {
        log.debug("REST request to get all assist apply record by {caseNumber}", caseNumber);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseAssistApply.caseAssistApply.caseNumber.eq(caseNumber)); //只查当前案件的协催申请记录
            // 根据caseNumber查询协催记录
            List<CaseAssistApply> list = new ArrayList<>();
            Iterable<CaseAssistApply> all = caseAssistApplyRepository.findAll(builder);
            if (all.iterator().hasNext()) {
                all.forEach(obj -> {
                    list.add(obj);
                });
                // 共债数据聚合
//                List<String> caseNums = new ArrayList<>();
//                for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
//                    if (caseNums.contains(list.get(i).getCaseNumber())){
//                        continue;
//                    }
//                    caseNums.add(list.get(i).getCaseNumber());
//                    CaseAssistApply caseAssistApply = list.get(i);
//                    list1.add(caseAssistApply);
//                }
            }
            Page<CaseAssistApply> page = new PageImpl<>(list, pageable, list.size());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllAssistApplyRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_ASSIST_APPLY, "caseAssistApply", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询发送信息记录
     */
    @GetMapping("/getSendMessageRecord")
    @ApiOperation(value = "电催页面多条件查询发送信息记录", notes = "电催页面多条件查询发送信息记录")
    public ResponseEntity<Page<SendMessageRecord>> getSendMessageRecord(@QuerydslPredicate(root = SendMessageRecord.class) Predicate predicate,
                                                                        @ApiIgnore Pageable pageable,
                                                                        @RequestParam @ApiParam(value = "案件借据号", required = true) String loanInvoiceNumber) {
        log.debug("REST request to get all send message record by {loanInvoiceNumber}", loanInvoiceNumber);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            CaseInfo one = caseInfoRepository.findOne(QCaseInfo.caseInfo.loanInvoiceNumber.eq(loanInvoiceNumber).and(QCaseInfo.caseInfo.collectionStatus.notIn(24)));
            if (Objects.isNull(one)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_SEND_MESSAGE_RECORD, "caseAssistApply", "数据异常")).body(null);
            }
            builder.and(QSendMessageRecord.sendMessageRecord.caseId.eq(one.getId())); //只查当前案件的信息发送记录
            Page<SendMessageRecord> page = sendMessageRecordRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getSendMessageRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_SEND_MESSAGE_RECORD, "caseAssistApply", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面多条件查询发送信息记录共债)
     */
    @GetMapping("/getAllSendMessageRecord")
    @ApiOperation(value = "电催页面多条件查询发送信息记录", notes = "电催页面多条件查询发送信息记录")
    public ResponseEntity<Page<SendMessageRecord>> getAllSendMessageRecord(@QuerydslPredicate(root = SendMessageRecord.class) Predicate predicate,
                                                                           @ApiIgnore Pageable pageable,
                                                                           @RequestParam @ApiParam(value = "案件编号", required = true) String caseNumber) {
        log.debug("REST request to get all send message record by {caseNumber}", caseNumber);
        try {
            List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseNumber);
            List<String> caseIds = new ArrayList<>();
            if (byCaseNumber.size() > 0) {
                byCaseNumber.forEach(obj -> {
                    caseIds.add(obj.getId());
                });
            }
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QSendMessageRecord.sendMessageRecord.caseId.in(caseIds)); //只查当前案件的信息发送记录
            Page<SendMessageRecord> page = sendMessageRecordRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getAllSendMessageRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_SEND_MESSAGE_RECORD, "caseAssistApply", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催页面新增联系人电话或邮箱地址
     */
    @PostMapping("/savePersonalContactPhone")
    @ApiOperation(value = "电催页面新增联系人电话或邮箱地址", notes = "电催页面新增联系人电话或邮箱地址")
    public ResponseEntity<PersonalContact> savePersonalContactPhone(@RequestBody AddPhoneOrMailParams addPhoneOrMailParams,
                                                                    @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to save new phone number");
        try {
            User tokenUser = getUserByToken(token);
            PersonalContact personalContact = personalContactRepository.findOne(addPhoneOrMailParams.getId());
            if (Objects.isNull(personalContact)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", "客户关系人未找到")).body(null);
            }
            PersonalContact result = new PersonalContact();
            BeanUtils.copyProperties(personalContact, result);
            result.setId(ShortUUID.uuid());
            result.setPhone(addPhoneOrMailParams.getPhone());
            result.setSource(Constants.DataSource.REPAIR.getValue()); //数据来源 147-修复
            result.setMail(addPhoneOrMailParams.getEmail());
            result.setOperator(tokenUser.getUserName()); //操作人
            result.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
            result.setRelation(addPhoneOrMailParams.getRelation()); //关系
            personalContactRepository.saveAndFlush(result);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("新增成功", ENTITY_PERSONAL_CONTACT)).body(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", "新增联系人信息失败")).body(null);
        }
    }

    /**
     * @Description 电催页面获取分配信息
     */
    @GetMapping("/getBatchInfo")
    @ApiOperation(value = "电催页面获取分配信息", notes = "电催页面获取分配信息")
    public ResponseEntity<BatchDistributeModel> getBatchInfo(@RequestParam(required = false) @ApiParam(value = "模块类型") List<Integer> modelType,
                                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get batch info");
        try {
            User tokenUser = getUserByToken(token);
            BatchDistributeModel batchDistributeModel = caseInfoService.getBatchDistribution(tokenUser,modelType);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取分配信息成功", ENTITY_CASEINFO)).body(batchDistributeModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取分配信息失败", "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催或外访页面批量分配
     */
    @PostMapping("/batchTelCase")
    @ApiOperation(value = "电催或外访页面批量分配", notes = "电催或外访页面批量分配")
    public ResponseEntity<Void> batchTelCase(@RequestBody BatchDistributeModel batchDistributeModel,
                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to batch case");
        try {
            User tokenUser = getUserByToken(token);
            String information = caseInfoService.batchCase(batchDistributeModel, tokenUser, false);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(information)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("请重新选择案件", ENTITY_CASEINFO, information)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("批量分配成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("批量分配失败", "caseInfo", e.getMessage())).body(null);
        }
    }
    /**
     * @Description 电催或外访页面批量分配-定时任务调用
     */
    @PostMapping("/batchTelCaseAuto")
    @ApiOperation(value = "电催或外访页面批量分配-定时任务调用", notes = "电催或外访页面批量分配-定时任务调用")
    public ResponseEntity<Void> batchTelCaseAuto(@RequestBody BatchDistributeModel batchDistributeModel) {
        log.debug("REST request to batch case");
        try {
            User tokenUser = userRepository.findOne(Constants.ADMINISTRATOR_ID);//定时任务,使用管理员用户
            caseInfoService.batchCase(batchDistributeModel, tokenUser,true);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("批量分配成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("批量分配失败", "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催案件颜色打标
     */
    @PutMapping("/telCaseMarkColor")
    @ApiOperation(value = "电催案件颜色打标", notes = "电催案件颜色打标")
    public ResponseEntity<Void> telCaseMarkColor(@RequestBody CaseMarkParams caseMarkParams,
                                                 @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to mark color");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.caseMarkColor(caseMarkParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件颜色打标成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("案件颜色打标失败", "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 修改联系人电话状态
     */
    @PutMapping("/modifyPhoneStatus")
    @ApiOperation(value = "修改联系人电话状态", notes = "修改联系人电话状态")
    public ResponseEntity<PersonalContact> modifyPhoneStatus(@RequestBody PhoneStatusParams phoneStatusParams,
                                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to modify phone status");
        try {
            User tokenUser = getUserByToken(token);
            PersonalContact personalContact = caseInfoService.modifyPhoneStatus(phoneStatusParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("联系人电话状态修改成功", ENTITY_PERSONAL_CONTACT)).body(personalContact);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("修改失败", "personalContact", e.getMessage())).body(null);
        }
    }


    /**
     * @Description 电催页面添加修复信息
     */
    @PostMapping("/saveRepairInfo")
    @ApiOperation(value = "电催页面添加修复信息", notes = "电催页面添加修复信息")
    public ResponseEntity<PersonalContact> saveRepairInfo(@RequestBody RepairInfoModel repairInfoModel,
                                                          @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to save repair information");
        try {
            User tokenUser = getUserByToken(token);
            PersonalContact personalContact = caseInfoService.saveRepairInfo(repairInfoModel, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("添加成功", ENTITY_PERSONAL_CONTACT)).body(personalContact);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 查看凭证
     */
    @GetMapping("/getPayProof")
    @ApiOperation(value = "查看凭证", notes = "查看凭证")
    public ResponseEntity<List<UploadFile>> getPayProof(@RequestParam @ApiParam(value = "还款审批ID") String casePayId) {
        log.debug("REST request to get payment proof");
        try {
            List<UploadFile> uploadFiles = caseInfoService.getRepaymentVoucher(casePayId);
            if (uploadFiles.isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("文件为空", ENTITY_UPLOAD_FILE)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("下载成功", ENTITY_UPLOAD_FILE)).body(uploadFiles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("下载失败", "uploadFile", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 分配前判断是否有协催案件或协催标识
     */
    @GetMapping("/checkCaseAssist")
    @ApiOperation(value = "分配前判断是否有协催案件或协催标识", notes = "分配前判断是否有协催案件或协催标识")
    public ResponseEntity<List<String>> checkCaseAssist(CheckAssistParams checkAssistParams) {
        log.debug("REST request to check assist");
        try {
            List<String> list = caseInfoService.checkCaseAssist(checkAssistParams);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("判断协催成功", ENTITY_CASEINFO)).body(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催案件留案操作
     */
    @PostMapping("/leaveTelCase")
    @ApiOperation(value = "电催案件留案操作", notes = "电催案件留案操作")
    public ResponseEntity<LeaveCaseModel> leaveTelCase(@RequestBody LeaveCaseParams leaveCaseParams,
                                                       @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to leave case");
        try {
            User tokenUser = getUserByToken(token);
            LeaveCaseModel leaveCaseModel = caseInfoService.leaveCase(leaveCaseParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("留案成功", ENTITY_CASEINFO)).body(leaveCaseModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 取消留案操作
     */
    @PostMapping("/cancelLeaveCase")
    @ApiOperation(value = "取消留案操作", notes = "取消留案操作")
    public ResponseEntity<LeaveCaseModel> cancelLeaveCase(@RequestBody LeaveCaseParams leaveCaseParams,
                                                          @RequestHeader(value = "x-UserToken") String token) {
        log.debug("REST request to cancel leave case");
        try {
            User tokenUser = getUserByToken(token);
            LeaveCaseModel leaveCaseModel = caseInfoService.cancelLeaveCase(leaveCaseParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("留案成功", ENTITY_CASEINFO)).body(leaveCaseModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催申请提前流转
     */
    @PostMapping("/telAdvanceCirculation")
    @ApiOperation(value = "电催申请提前流转", notes = "电催申请提前流转")
    public ResponseEntity<Void> telAdvanceCirculation(@RequestBody AdvanceCirculationParams advanceCirculationParams,
                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to advance circulation");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.advanceCirculation(advanceCirculationParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("申请成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 多条件查询电催小流转待审批案件
     */
    @GetMapping("/getTelPendingCase")
    @ApiOperation(value = "多条件查询电催小流转待审批案件", notes = "多条件查询电催小流转待审批案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseAdvanceTurnApplay>> getCaseAdvanceTurnApplay(@RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode,
                                                                                @RequestParam(required = false) @ApiParam(value = "客户名称") String custName,
                                                                                @RequestParam(required = false) @ApiParam(value = "审核状态") Integer approveState,
                                                                                @ApiIgnore Pageable pageable,
                                                                                @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to getCaseAdvanceTurnApplay");
        try {
            User tokenUser = getUserByToken(token);
            QCaseAdvanceTurnApplay qCaseAdvanceTurnApplay = QCaseAdvanceTurnApplay.caseAdvanceTurnApplay;
            BooleanBuilder builder = new BooleanBuilder();
            if (Objects.isNull(tokenUser.getCompanyCode())) {
                if (Objects.nonNull(companyCode)) {
                    builder.and(qCaseAdvanceTurnApplay.companyCode.eq(companyCode));
                }
            } else {
                builder.and(qCaseAdvanceTurnApplay.companyCode.eq(tokenUser.getCompanyCode())); //限制公司code码
            }
            builder.and(qCaseAdvanceTurnApplay.collectionType.eq(0)); //电催
            if (Objects.nonNull(custName)) {
                builder.and(qCaseAdvanceTurnApplay.personalName.like("%".concat(StringUtils.trim(custName)).concat("%")));
            }
            if (Objects.nonNull(approveState)) {
                builder.and(qCaseAdvanceTurnApplay.approveResult.eq(approveState));
            }
            builder.and(qCaseAdvanceTurnApplay.deptCode.startsWith(tokenUser.getDepartment().getCode())); //权限控制
            Page<CaseAdvanceTurnApplay> page = caseAdvanceTurnApplayRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getTelPendingCase");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseAdvanceTurnApplay", "CaseAdvanceTurnApplay", "查询失败")).body(null);
        }
    }

    /**
     * @Description 电催审批小流转案件
     */
    @PostMapping("/approvalTelCirculation")
    @ApiOperation(value = "电催审批小流转案件", notes = "电催审批小流转案件")
    public ResponseEntity<Void> approvalTelCirculation(@RequestBody CirculationApprovalParams circulationApprovalParams,
                                                       @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to approval tel circulation case");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.approvalCirculation(circulationApprovalParams, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("审批成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面查询客户联系人
     */
    @GetMapping("/getTelPersonalContact")
    @ApiOperation(value = "电催页面查询客户联系人", notes = "电催页面查询客户联系人")
    public ResponseEntity<List<PersonalContactModel>> getTelPersonalContact(@RequestParam @ApiParam(value = "客户信息ID", required = true) String personalId) {
        log.debug("REST request to get personal contact by {personalId}", personalId);
        try {
            List<PersonalContact> content = caseInfoService.getPersonalContact(personalId);
            List<PersonalContactModel> connectionRate = getConnectionRate(content, personalId);
//            PersonalContactModel personalContactModel = new PersonalContactModel();
//            personalContactModel.setContent(content);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_PERSONAL_CONTACT)).body(connectionRate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面查询客户联系人
     */
    @PostMapping("/updateTelPersonalContact")
    @ApiOperation(value = "电催页面查询客户联系人", notes = "电催页面查询客户联系人")
    public ResponseEntity<Void> updateTelPersonalContact(@RequestBody PersonalContactParams personalContactParams,
                                                         @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to advance circulation");
        try {
            User tokenUser = getUserByToken(token);
            if (Strings.isNullOrEmpty(personalContactParams.getId())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("数据异常!", "personalContactParams")).body(null);
            }
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(QPersonalContact.personalContact.id.eq(personalContactParams.getId()));
            PersonalContact personalContact = personalContactRepository.findOne(builder);
            if (personalContact == null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "要修改的联系人不存在")).build();
            }
            if (Objects.nonNull(personalContactParams.getPhoneStatus())) {
                personalContact.setPhoneStatus(personalContactParams.getPhoneStatus());
            }
            if (Objects.nonNull(personalContactParams.getAddressStatus())) {
                personalContact.setAddressStatus(personalContactParams.getAddressStatus());
            }
            if (Objects.nonNull(personalContactParams.getRelation())) {
                personalContact.setRelation(personalContactParams.getRelation());
            }
            personalContact.setOperator(tokenUser.getRealName());
            personalContact.setOperatorTime(new Date());
            personalContactRepository.save(personalContact);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改成功", "personalContactParams")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", e.getMessage())).body(null);
        }
    }

    /**
     * @Description 电催页面查询客户联系人共债合并信息
     */
    @GetMapping("/getTelAllPersonalContact")
    @ApiOperation(value = "电催页面查询客户联系人共债合并信息", notes = "电催页面查询客户联系人共债合并信息")
    public ResponseEntity<List<PersonalContactModel>> getTelAllPersonalContact(@RequestParam @ApiParam(value = "客户信息ID", required = true) String personalIds) {
        log.debug("REST request to get personal contact by {}", personalIds);
        try {
            List<PersonalContact> personalContact = caseInfoService.getPersonalContact(personalIds);
            List<PersonalContactModel> connectionRate = getConnectionRate(personalContact, personalIds);
            return ResponseEntity.ok(connectionRate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_PERSONAL_CONTACT, "personalContact", e.getMessage())).body(null);
        }
    }


    /**
     * @Description 电催修复信息校验
     */
    @PostMapping("/checkContactInfo")
    @ApiOperation(value = "电催修复信息校验", notes = "电催修复信息校验")
    public ResponseEntity checkContactInfo(@RequestBody CheckPersonalContactModel checkPersonalContactModel) {
        try {
            Iterable<PersonalContact> all = personalContactRepository.findAll(QPersonalContact.personalContact.personalId.eq(checkPersonalContactModel.getPersonalId()).and(QPersonalContact.personalContact.relation.eq(checkPersonalContactModel.getRelation())).and(QPersonalContact.personalContact.phone.eq(checkPersonalContactModel.getPersonalPhone())).and(QPersonalContact.personalContact.name.eq(checkPersonalContactModel.getPersonalName())));
            if (!all.iterator().hasNext()) {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("电催修复信息校验", null)).body(0);// 无数据
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("电催修复信息校验", null)).body(1);//有数据
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "checkContactInfo", e.getMessage())).body(null);
        }
    }


    /**
     * @Descritpion 电催页面查看电话录音列表
     */
    @GetMapping("/getPhoneRecord")
    @ApiOperation(value = "电催页面查看电话录音列表", notes = "电催页面查看电话录音列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseFollowupRecord>> getPhoneRecord(@RequestParam @ApiParam(value = "案件编号", required = true) String caseNumber,
                                                                   @QuerydslPredicate(root = CaseFollowupRecord.class) Predicate predicate,
                                                                   @ApiIgnore Pageable pageable) {
        log.debug("REST request to get phone record by {}", caseNumber);
        try {
            BooleanBuilder builder = new BooleanBuilder(predicate);
            builder.and(QCaseFollowupRecord.caseFollowupRecord.caseNumber.eq(caseNumber)); //案件编号
            builder.and(QCaseFollowupRecord.caseFollowupRecord.opUrl.isNotNull()); //录音地址不为空
            Page<CaseFollowupRecord> page = caseFollowupRecordRepository.findAll(builder, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/AccTelPoolController/getPhoneRecord");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_FOLLOWUP_RECORD, "caseFollowupRecord", e.getMessage())).body(null);
        }
    }

    @PostMapping("/turnCaseVisitConfirm")
    @ApiOperation(value = "流转案件确认", notes = "流转案件确认")
    public ResponseEntity toConfirm(@RequestBody CaseDistributeInfoModel caseDistributeInfoModel,
                                    @RequestHeader(value = "X-UserToken") String token) {
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.turnCaseConfirm(caseDistributeInfoModel.getCaseIdList(), tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("审批成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    @PostMapping(value = "/distributeTurnCeaseAgain")
    @ApiOperation(value = "案件重新分配", notes = "案件重新分配")
    public ResponseEntity distributeTurnCeaseAgain(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                                   @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        log.debug("REST request to distributeCeaseInfoAgain");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }
        try {
            caseInfoService.distributeCeaseInfoAgain(accCaseInfoDisModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "caseInfo")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseInfoController", "distributeCeaseInfoAgain", e.getMessage())).body(null);
        }

    }

    @PostMapping("/turnCaseVisitDistribution")
    @ApiOperation(value = "流转案件分配", notes = "流转案件分配")
    public ResponseEntity batchTurnCase(@RequestBody BatchDistributeModel batchDistributeModel,
                                        @RequestHeader(value = "X-UserToken") String token) {
        try {
            User tokenUser = getUserByToken(token);
            caseInfoService.turnCaseDistribution(batchDistributeModel, tokenUser);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("审批成功", ENTITY_CASEINFO)).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASEINFO, "caseInfo", e.getMessage())).body(null);
        }
    }

    private List<PersonalContactModel> getConnectionRate(List<PersonalContact> personalContact, String personalId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(QCaseFollowupRecord.caseFollowupRecord.personalId.eq(personalId));
        builder.and(QCaseFollowupRecord.caseFollowupRecord.taskId.isNotNull());
        Iterable<CaseFollowupRecord> all = caseFollowupRecordRepository.findAll(builder);
        List<CaseFollowupRecord> caseFollowupRecords = IterableUtils.toList(all);

        return personalContact.stream().map(contact -> {
            PersonalContactModel personalContactModel = new PersonalContactModel();
            BeanUtils.copyProperties(contact, personalContactModel);

            if (caseFollowupRecords.isEmpty()) {
                // 不存在跟进信息(默认接通率为100%)
                personalContactModel.setConnectionRate(100);
                return personalContactModel;
            }
            // 存在跟进信息
            long count = caseFollowupRecords.stream()
                    .filter(e -> StringUtils.equals(e.getResult(), "客户接听"))
                    .count();
            personalContactModel.setConnectionRate((int) ((double) count / (double) caseFollowupRecords.size() * 100));
            return personalContactModel;
        }).collect(Collectors.toList());
    }

}