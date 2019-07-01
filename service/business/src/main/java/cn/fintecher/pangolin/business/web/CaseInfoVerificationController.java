package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.CaseInfoVerModel;
import cn.fintecher.pangolin.business.model.CaseInfoVerficationModel;
import cn.fintecher.pangolin.business.model.CaseInfoVerificationParams;
import cn.fintecher.pangolin.business.model.ListResult;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.CaseInfoVerificationService;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.ProgressMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import cn.fintecher.pangolin.web.PaginationUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yuanyanting
 * @version Id:CaseInfoVerificationController.java,v 0.1 2017/9/1 11:47 yuanyanting Exp $$
 */
@RestController
@RequestMapping("/api/caseInfoVerificationController")
@Api(value = "CaseInfoVerificationController", description = "核销案件操作")
public class CaseInfoVerificationController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(CaseInfoVerificationController.class);

    @Inject
    private CaseInfoVerificationRepository caseInfoVerificationRepository;

    @Inject
    private CaseInfoVerificationService caseInfoVerificationService;

    @Inject
    private CaseInfoRepository caseInfoRepository;

    @Inject
    private CaseInfoVerificationPackagingRepository caseInfoVerificationPackagingRepository;

    @Inject
    private CaseInfoVerificationApplyRepository caseInfoVerificationApplyRepository;

    @Inject
    private SysParamRepository sysParamRepository;

    @Inject
    private RestTemplate restTemplate;

    @Inject
    private RabbitTemplate rabbitTemplate;

    @Inject
    private ReminderService reminderService;

    private static final String VerApply = "SysParam.isVerApply";

    @GetMapping("/getSysparm")
    @ApiOperation(value = "查询是否需要申请", notes = "查询是否需要申请")
    public ResponseEntity<String> getSysparm(@RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
            SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.companyCode.eq(user.getCompanyCode()).and(QSysParam.sysParam.code.eq(VerApply)));
            String value = sysParam.getValue(); // 1--申请,0--不申请
            return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("查询成功", "caseInfoVerification")).body(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查询失败!")).body(null);
        }
    }

    @PostMapping("/saveCaseInfoVerification")
    @ApiOperation(value = "案件申请审批", notes = "案件申请审批")
    public ResponseEntity saveCaseInfoVerification(@RequestBody CaseInfoVerficationModel caseInfoVerficationModel,
                                                   @RequestHeader(value = "X-UserToken") String token,
                                                   @QuerydslPredicate(root = CaseInfoVerificationApply.class) Predicate predicate) {
        try {
            User user = getUserByToken(token);
            List<CaseInfo> caseInfoList = caseInfoRepository.findAll(caseInfoVerficationModel.getIds());
            for (int i = 0; i < caseInfoList.size(); i++) {
                if (caseInfoList.get(i).getCollectionStatus().equals(CaseInfo.CollectionStatus.CASE_OVER.getValue())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "结案案件不能核销!")).body(null);
                }
                if (caseInfoList.get(i).getCollectionStatus().equals(CaseInfo.CollectionStatus.CASE_OUT.getValue())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "已委外案件不能核销!")).body(null);
                }
            }
            SysParam sysParam = sysParamRepository.findOne(QSysParam.sysParam.companyCode.eq(user.getCompanyCode()).and(QSysParam.sysParam.code.eq("SysParam.isVerApply")));
            if (Integer.parseInt(sysParam.getValue()) == 1) { // 申请审批
                CaseInfoVerificationApply apply = new CaseInfoVerificationApply();
                List<CaseInfoVerificationApply> caseApplyList = new ArrayList();
                List<String> ids = caseInfoVerficationModel.getIds();
                BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
                booleanBuilder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.companyCode.eq(user.getCompanyCode()));
                for (String id : ids) {
                    booleanBuilder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.caseId.eq(id));
                }
                booleanBuilder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.approvalStatus.eq(222));
                Iterable<CaseInfoVerificationApply> Iterables = caseInfoVerificationApplyRepository.findAll(booleanBuilder);
                Iterables.forEach(single -> {
                    caseApplyList.add(single);
                });
                if (caseApplyList.size() != 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "不能提交重复申请！")).body(null);
                }
                for (CaseInfo caseInfo : caseInfoList) {
                    caseInfoVerificationService.setVerificationApply(apply, caseInfo, user, caseInfoVerficationModel.getApplicationReason());
                    caseInfoVerificationApplyRepository.save(apply);
//                    caseInfo.setCasePoolType(CaseInfo.CasePoolType.DESTORY.getValue()); //案件池类型：核销
                    caseInfoRepository.save(caseInfo);
                }
            } else {
                for (CaseInfo caseInfo : caseInfoList) {
                    caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.CASE_OVER.getValue()); // 催收类型：已结案
                    caseInfo.setEndType(CaseInfo.EndType.CLOSE_CASE.getValue()); // 结案方式：核销结案
//                    caseInfo.setCasePoolType(CaseInfo.CasePoolType.DESTORY.getValue()); // 案件池类型：核销
                    caseInfoRepository.save(caseInfo);
                    CaseInfoVerification caseInfoVerification = new CaseInfoVerification();
                    caseInfoVerification.setCaseInfo(caseInfo);// 核销的案件信息
                    caseInfoVerification.setState(caseInfoVerficationModel.getState()); // 核销说明
                    caseInfoVerification.setCompanyCode(caseInfo.getCompanyCode());// 公司code码
                    caseInfoVerification.setOperator(user.getRealName());// 操作人
                    caseInfoVerification.setOperatorTime(ZWDateUtil.getNowDateTime());// 操作时间
                    caseInfoVerification.setPackingStatus(CaseInfoVerification.PackingStatus.NO_PACKED.getValue());// 打包状态
                    caseInfoVerificationRepository.save(caseInfoVerification);
                }
            }
            return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("核销结案成功", "CaseInfoVerificationModel")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "操作失败!")).body(null);
        }
    }

    @RequestMapping(value = "/getCaseInfoVerificationApproval", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "审批待通过案件查询", notes = "审批待通过案件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity getCaseInfoVerificationApply(@QuerydslPredicate(root = CaseInfoVerificationApply.class) Predicate predicate,
                                                       @ApiIgnore Pageable pageable,
                                                       @RequestHeader(value = "X-UserToken") String token,
                                                       @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) {
            if (StringUtils.isNotBlank(companyCode)) {
                builder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.companyCode.eq(companyCode));
            }
        } else {
            builder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(QCaseInfoVerificationApply.caseInfoVerificationApply.approvalStatus.in(CaseInfoVerificationApply.ApprovalStatus.approval_pending.getValue(), CaseInfoVerificationApply.ApprovalStatus.approval_disapprove.getValue())); // 审批状态：待通过、审批拒绝
        List<CaseInfoVerificationApply> list = new ArrayList<>();
        List<CaseInfoVerificationApply> list1 = new ArrayList<>();
        Iterable<CaseInfoVerificationApply> all = caseInfoVerificationApplyRepository.findAll(builder);
        if (all.iterator().hasNext()){
            all.forEach(obj->{list.add(obj);});
            List<String> caseNums = new ArrayList<>();
            for  ( int  i  =   0 ; i  <  list.size() ; i ++ ) {
                if (caseNums.contains(list.get(i).getCaseNumber())) {
                    continue;
                }
                caseNums.add(list.get(i).getCaseNumber());
                CaseInfoVerificationApply caseInfoVerificationApply = list.get(i);
                for (int j = i + 1; j < list.size(); j++) {
                    if (caseInfoVerificationApply.getCaseNumber().equals(list.get(j).getCaseNumber())) {
                        // 有共债案件需要合并(合并逻辑)
//                        if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfoModel.getOverduePeriods())){
//                            // 判断逾期期数大小
//                            if (new BigDecimal(caseInfoModel.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1){
//                                caseInfoModel.setOverduePeriods(list.get(j).getOverduePeriods());
//                            }
//                        }
                        // 合并逾期金额
                        if (Objects.nonNull(caseInfoVerificationApply.getOverdueAmount()) && Objects.nonNull(list.get(j).getOverdueAmount())) {
                            caseInfoVerificationApply.setOverdueAmount(caseInfoVerificationApply.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                        }
                        // 合并到账金额


                    }
                }
                list1.add(caseInfoVerificationApply);
            }
        }
        Page<CaseInfoVerificationApply> page = new PageImpl<>(list1, pageable,list1.size() );
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "caseInfoVerification")).body(page);
    }

    @GetMapping("/caseInfoVerification")
    @ApiOperation(value = "核销单个案件查询", notes = "核销单个案件查询")
    public ResponseEntity<CaseInfoVerificationApply> caseInfoVerification(String id) {
        try {
            CaseInfoVerificationApply caseInfoVerificationApply = caseInfoVerificationApplyRepository.findOne(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "caseInfoVerification")).body(caseInfoVerificationApply);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查看失败")).body(null);
        }
    }

    @PostMapping("/caseInfoVerificationApply")
    @ApiOperation(value = "案件申请审批通过", notes = "案件申请审批通过")
    public ResponseEntity caseInfoVerificationApply(@RequestBody CaseInfoVerficationModel caseInfoVerficationModel,
                                                    @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
            caseInfoVerificationService.caseInfoVerificationApply(caseInfoVerficationModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("核销审批成功", "caseInfoVerification")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查看失败")).body(null);
        }
    }

    @RequestMapping(value = "/getCaseInfoVerification", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "核销审批通过案件查询", notes = "核销审批通过案件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity getCaseInfoVerification(@QuerydslPredicate(root = CaseInfoVerification.class) Predicate predicate,
                                                  @ApiIgnore Pageable pageable,
                                                  @RequestHeader(value = "X-UserToken") String token,
                                                  @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) { // 超级管理员
            if (StringUtils.isNotBlank(companyCode)) {
                builder.and(QCaseInfoVerification.caseInfoVerification.companyCode.eq(companyCode));
            }
        } else { // 普通管理员
            builder.and(QCaseInfoVerification.caseInfoVerification.companyCode.eq(user.getCompanyCode()));
        }
        builder.and(QCaseInfoVerification.caseInfoVerification.packingStatus.eq(CaseInfoVerification.PackingStatus.NO_PACKED.getValue()));
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "operatorTime"));
        Page<CaseInfoVerification> page = caseInfoVerificationRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "caseInfoVerification")).body(page);
    }

    @PostMapping("/exportVerification")
    @ApiOperation(value = "核销管理导出", notes = "核销管理导出")
    public ResponseEntity<String> exportVerification(@RequestHeader(value = "X-UserToken") String token,
                                                     @RequestBody CaseInfoVerficationModel caseInfoVerficationModel) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "登录异常")).body(null);
        }
        List<String> ids = caseInfoVerficationModel.getIds();
        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "请先选择案件")).body(null);
        }
        List<Object[]> caseInfoVerificationList = caseInfoVerificationService.getCastInfoList(caseInfoVerficationModel, user);
        if (caseInfoVerificationList.isEmpty()) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "要导出的数据为空")).body(null);
        }
        TaskBox taskBox = new TaskBox();
        taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
        taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
        taskBox.setCompanyCode(user.getCompanyCode());
        taskBox.setType(TaskBox.Type.EXPORT.getValue());
        taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
        taskBox.setOperator(user.getId());
        taskBox.setTaskDescribe("核销案件导出");
        ResponseEntity<TaskBox> responseEntity = restTemplate.postForEntity("http://business-service/api/taskBoxResource", taskBox, TaskBox.class);

        TaskBox finalTaskBox = responseEntity.getBody();
        String taskId = finalTaskBox.getId();
        //创建一个线程，执行导出任务
        Thread t = new Thread(() -> {
            try {
                ResponseEntity<String> url = caseInfoVerificationService.exportCaseInfoVerification(caseInfoVerificationList);
                if (Objects.nonNull(url) && Objects.nonNull(url.getBody()) && !url.getBody().isEmpty()) {
                    finalTaskBox.setTaskStatus(TaskBox.Status.FINISHED.getValue());
                    finalTaskBox.setRemark(url.getBody());
                } else {
                    finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
                }
                caseInfoVerificationService.setCaseInfoVerificationPackaging(user,caseInfoVerficationModel,url.getBody());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
            } finally {
                finalTaskBox.setEndDate(ZWDateUtil.getNowDateTime());
                restTemplate.postForEntity("http://business-service/api/taskBoxResource", finalTaskBox, TaskBox.class);
                try {
                    reminderService.sendTaskBoxMessage(finalTaskBox);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    log.error("客户信息导出发送消息失败");
                }
            }
        });
        t.start();
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("开始导出,完成后请前往消息列表查看下载。", "")).body(taskId);

    }

    @RequestMapping(value = "/getCaseInfoVerificationPackaging", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "核销案件打包的查询", notes = "核销案件打包的查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity getCaseInfoVerificationPackaging(@QuerydslPredicate(root = CaseInfoVerificationPackaging.class) Predicate predicate,
                                                           @ApiIgnore Pageable pageable,
                                                           @RequestHeader(value = "X-UserToken") String token,
                                                           @RequestParam(required = false) @ApiParam(value = "公司code码") String companyCode) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        if (Objects.isNull(user.getCompanyCode())) { // 超级管理员
            if (StringUtils.isNotBlank(companyCode)) {
                builder.and(QCaseInfoVerificationPackaging.caseInfoVerificationPackaging.companyCode.eq(companyCode));
            }
        } else { // 普通管理员
            builder.and(QCaseInfoVerificationPackaging.caseInfoVerificationPackaging.companyCode.eq(user.getCompanyCode()));
        }
        Page<CaseInfoVerificationPackaging> page = caseInfoVerificationPackagingRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", "caseInfoVerification")).body(page);
    }

    @PostMapping("/batchDownload")
    @ApiOperation(value = "立刻下载", notes = "立刻下载")
    public ResponseEntity<List<String>> batchDownload(@RequestHeader(value = "X-UserToken") String token,
                                                      @RequestBody CaseInfoVerficationModel caseInfoVerficationModel) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(null, "Userexists", e.getMessage())).body(null);
        }
        try{
            List<String> ids = caseInfoVerficationModel.getIds();
            ListResult<String> result = new ListResult();
            //创建一个线程，执行导出任务
            Thread t = new Thread(() -> {
                ProgressMessage progressMessage = new ProgressMessage();
                // 登录人ID
                progressMessage.setUserId(user.getId());
                //要解析的总数据
                progressMessage.setTotal(5);
                //当前解析的数据
                progressMessage.setCurrent(0);
                //正在处理数据
                progressMessage.setText("正在处理数据");
                rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, progressMessage);
                List<String> urlList = new ArrayList<>();
                if (ids.isEmpty()) {
                    List<String> urls = new ArrayList<>();
                    urls.add("请至少选择一个案件!");
                    result.setUser(user.getId());
                    result.setStatus(ListResult.Status.FAILURE.getVal());// 状态：失败
                    result.setResult(urls);
                    restTemplate.postForEntity("http://reminder-service/api/listResultMessageResource", result, Void.class);
                    progressMessage.setCurrent(1);
                    rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, progressMessage);
                }
                for (String id : ids) {
                    CaseInfoVerificationPackaging caseInfoVerificationPackaging = caseInfoVerificationPackagingRepository.findOne(id);
                    if (Objects.nonNull(caseInfoVerificationPackaging.getDownloadCount())) {
                        caseInfoVerificationPackaging.setDownloadCount(caseInfoVerificationPackaging.getDownloadCount() + 1); // 下载次数
                    }
                    caseInfoVerificationPackagingRepository.save(caseInfoVerificationPackaging);
                    String url = caseInfoVerificationPackaging.getDownloadAddress(); // 下载地址
                    urlList.add(url);
                }
                result.setUser(user.getId());
                result.setResult(urlList);
                result.setStatus(ListResult.Status.SUCCESS.getVal());
                restTemplate.postForEntity("http://reminder-service/api/listResultMessageResource", result, Void.class);
                progressMessage.setCurrent(3);
                rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, progressMessage);
            });
            t.start();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("开始导出,完成后请前往消息列表查看下载。", "")).body(null);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查看失败")).body(null);
        }
    }

    @PostMapping("/download")
    @ApiOperation(value = "单个立刻下载", notes = "单个立即下载")
    public ResponseEntity<String> download(@RequestBody CaseInfoVerficationModel caseInfoVerficationModel) {
        try {
            CaseInfoVerificationPackaging caseInfoVerificationPackaging = new CaseInfoVerificationPackaging();
            if (Objects.nonNull(caseInfoVerficationModel.getId())) {
                caseInfoVerificationPackaging = caseInfoVerificationPackagingRepository.findOne(caseInfoVerficationModel.getId());
            }
            if (Objects.nonNull(caseInfoVerificationPackaging.getDownloadCount())) {
                caseInfoVerificationPackaging.setDownloadCount(caseInfoVerificationPackaging.getDownloadCount() + 1); // 下载次数
            }
            caseInfoVerificationPackagingRepository.save(caseInfoVerificationPackaging);
            String url = caseInfoVerificationPackaging.getDownloadAddress();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", "caseInfoVerification")).body(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查看失败")).body(null);
        }
    }

    @GetMapping("/getVerificationReportBycondition")
    @ApiOperation(value = "多条件分页查询核销报表", notes = "多条件分页查询核销报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页大小."),
            @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "依据什么排序: 属性名(,asc|desc). ", allowMultiple = true)
    })
    public ResponseEntity<Page<CaseInfoVerModel>> getVerificationReportBycondition
            (@RequestHeader(value = "X-UserToken") String token,
             @ApiIgnore Pageable pageable,
             CaseInfoVerificationParams caseInfoVerificationParams) {
        User user;
        List<CaseInfoVerModel> caseInfoVerificationReport = null;
        try {
            user = getUserByToken(token);
            caseInfoVerificationReport = caseInfoVerificationService.getList(caseInfoVerificationParams, user);
            Integer totalCount = caseInfoVerificationRepository.getTotalCount();
            Page<CaseInfoVerModel> page = new PageImpl<>(caseInfoVerificationReport, pageable, totalCount);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caseInfoVerificationController/getVerificationReportBycondition");
            return new ResponseEntity<>(page, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "查询失败")).body(null);
        }
    }

    @GetMapping("/exportReport")
    @ApiOperation(value = "导出核销报表", notes = "导出核销报表")
    public ResponseEntity<String> exportReport(CaseInfoVerificationParams caseInfoVerificationParams,
                                               @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user.getCompanyCode())) {
                if (Objects.isNull(caseInfoVerificationParams.getCompanyCode())) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "请选择公司")).body(null);
                }
            }
            ListResult<String> result = new ListResult();
            //创建一个线程，执行导出任务
            Thread t = new Thread(() -> {
                ProgressMessage progressMessage = new ProgressMessage();
                // 登录人ID
                progressMessage.setUserId(user.getId());
                //要解析的总数据
                progressMessage.setTotal(5);
                //当前解析的数据
                progressMessage.setCurrent(0);
                //正在处理数据
                progressMessage.setText("正在处理数据");
                rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, progressMessage);
                String url = caseInfoVerificationService.exportReport(caseInfoVerificationParams, user);
                List<String> urls = new ArrayList<>();
                urls.add(url);
                result.setUser(user.getId());
                result.setResult(urls);
                result.setStatus(ListResult.Status.SUCCESS.getVal());
                restTemplate.postForEntity("http://reminder-service/api/listResultMessageResource", result, Void.class);
                progressMessage.setCurrent(3);
                rabbitTemplate.convertAndSend(Constants.FOLLOWUP_EXPORT_QE, progressMessage);
            });
            t.start();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("开始导出,完成后请前往消息列表查看下载。", "")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("caseInfoVerification", "caseInfoVerification", "导出失败")).body(null);
        }
    }
}

