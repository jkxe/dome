package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.*;
import cn.fintecher.pangolin.business.utils.ExcelExportHelper;
import cn.fintecher.pangolin.business.utils.ZWMathUtil;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.strategy.CaseStrategy;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import cn.fintecher.pangolin.model.AccCaseInfoDisModel;
import cn.fintecher.pangolin.model.ManualParams;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: PeiShouWen
 * @Description: 案件分配
 * @Date 15:50 2017/8/7
 */
@RestController
@RequestMapping(value = "/api/caseInfoDistributeController")
@Api(value = "案件分配", description = "案件分配")
public class CaseInfoDistributeController extends BaseController {

    private static final String ENTITY_NAME = "caseInfoDistributeController";
    Logger logger = LoggerFactory.getLogger(CaseInfoDistributeController.class);

    @Autowired
    CaseInfoService caseInfoService;
    @Inject
    CaseInfoDistributedRepository caseInfoDistributedRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    CaseInfoRepository caseInfoRepository;
    @Inject
    CaseInfoDistributedService caseInfoDistributedService;
    @Inject
    PersonalContactRepository personalContactRepository;
    @Inject
    RunCaseStrategyService runCaseStrategyService;
    @Inject
    OutsourcePoolRepository outsourcePoolRepository;
    @Inject
    EntityManageService entityManageService;
    @Inject
    StrategyDistributeService strategyDistributeService;
    @Inject
    TaskBoxRepository taskBoxRepository;
    @Inject
    ReminderService reminderService;
    @Inject
    RestTemplate restTemplate;

    @RequestMapping(value = "/distributeCeaseInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "案件分配(机构时传入机构的ID)", notes = "案件分配")
    public ResponseEntity distributeCeaseInfo(@RequestBody AccCaseInfoDisModel accCaseInfoDisModel,
                                              @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        try {
            User user = getUserByToken(token);
            try {
                caseInfoDistributedService.distributeCeaseInfo(accCaseInfoDisModel, user);
            } catch (final Exception e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String msg = Objects.isNull(e.getMessage()) ? "系统异常" : e.getMessage();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "error", msg)).body(null);
        }

    }

    @GetMapping("/findCaseInfoDistribute")
    @ApiOperation(value = "案件分配页面（多条件查询）", notes = "案件分配页面（多条件查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfoDistributed>> findCaseInfoDistribute(@QuerydslPredicate(root = CaseInfoDistributed.class) Predicate predicate,
                                                                            @ApiIgnore Pageable pageable,
                                                                            @RequestHeader(value = "X-UserToken") String token,
                                                                            @RequestParam(value = "companyCode", required = false) String companyCode) {
        logger.debug("REST request to findCaseInfoDistribute");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("CaseInfoDistributeController", "findCaseInfoDistribute", e.getMessage()))
                    .body(null);
        }
        try {
            QCaseInfoDistributed qd = QCaseInfoDistributed.caseInfoDistributed;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qd.companyCode.eq(companyCode));
                }
            } else {
                builder.and(qd.companyCode.eq(user.getCompanyCode()));
            }
            builder.and(qd.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
            Iterable<CaseInfoDistributed> all = caseInfoDistributedRepository.findAll(builder);
            List<CaseInfoDistributed> list = new ArrayList<>();
            all.forEach(obj->{list.add(obj);});
            // 共债案件聚合
            List<CaseInfoDistributed> list1 = new ArrayList<>();
            List<String> caseNums = new ArrayList<>();
            for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
                if (caseNums.contains(list.get(i).getCaseNumber())){
                    continue;
                }
                caseNums.add(list.get(i).getCaseNumber());
                CaseInfoDistributed caseInfoDistributed = list.get(i);
                for (int  j  =   i+1 ; j  <  list.size() ; j ++ ){
                    if  (caseInfoDistributed.getCaseNumber().equals(list.get(j).getCaseNumber()))  {
                        if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfoDistributed.getOverduePeriods())){
                            // 判断逾期期数大小
                            if (new BigDecimal(caseInfoDistributed.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1){
                                caseInfoDistributed.setOverduePeriods(list.get(j).getOverduePeriods());
                            }
                        }
                        // 合并逾期总金额
                        caseInfoDistributed.setOverdueAmount(caseInfoDistributed.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                        // 合并到账金额
                        caseInfoDistributed.setAccountBalance(caseInfoDistributed.getAccountBalance().add(list.get(j).getAccountBalance()));
                    }
                }
                list1.add(caseInfoDistributed);
            }
            Page<CaseInfoDistributed> page = new PageImpl<>(list1, pageable,list1.size() );
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("CaseInfoDistributeController", "findCaseInfoDistribute", "系统异常!"))
                    .body(null);
        }
    }

    @PostMapping("/getUserInfoByUserId")
    @ApiOperation(value = "查找用户的案件数", notes = "查找用户的案件数")
    public ResponseEntity getAccReceivePoolByUserId(@ApiParam(value = "用户userId组", required = true) @RequestBody UserInfoModel userIds) {
        try {
            List<UserInfoModel> list = new ArrayList<>();
            List<String> userIds1 = userIds.getUserIds();
            for (String userId : userIds1) {
                UserInfoModel userInfo = new UserInfoModel();
                User user = userRepository.findOne(userId);
                userInfo.setUserId(user.getId());
                userInfo.setUserName(user.getUserName());
                userInfo.setCollector(user.getRealName());
                Integer caseCountOnUser = caseInfoDistributedRepository.getCaseCountOnUser(user.getId());
                userInfo.setCaseCount(caseCountOnUser);
                list.add(userInfo);
            }
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("CaseInfoDistributeController", "getAccReceivePoolByUserId", "系统异常!"))
                    .body(null);
        }
    }

    @GetMapping("/getCaseCountOnDept")
    @ApiOperation(value = "查找部门下的案件数", notes = "查找部门下的案件数")
    public ResponseEntity getCaseCountOnDept(@RequestParam("deptCode") @ApiParam("部门Code") String deptCode,
                                             @RequestHeader(value = "X-UserToken") String token) {
        logger.debug("REST request to getCaseCountOnDept");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("CaseInfoDistributeController", "getCaseCountOnDept", e.getMessage()))
                    .body(null);
        }
        try {
            QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(qCaseInfo.companyCode.eq(user.getCompanyCode()));
            builder.and(qCaseInfo.department.code.like(deptCode.concat("%")));
            builder.and(qCaseInfo.collectionStatus.notIn(CaseInfo.CollectionStatus.CASE_OVER.getValue()));
            Long count = caseInfoRepository.count(builder);
            return ResponseEntity.ok().body(count);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert("CaseInfoDistributeController", "getCaseCountOnDept", "系统异常!"))
                    .body(null);
        }
    }

    @GetMapping("/getCaseInfoDistributedDetails")
    @ApiOperation(value = "案件详情查询操作", notes = "案件详情查询操作")
    public ResponseEntity<CaseInfoDistributed> getCaseInfoDistributedDetails(@RequestParam("id") String id) {
        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(id);
        return ResponseEntity.ok().body(caseInfoDistributed);
    }

    @GetMapping("/batchAddPersonContacts")
    @ApiOperation(value = "根据备注解析联系人信息", notes = "根据备注解析联系人信息")
    public ResponseEntity<Void> batchAddPersonContacts(@RequestHeader(value = "X-UserToken") String token) {
        User user = null;
        try {
            user = getUserByToken(token);
            List<CaseInfoDistributed> caseInfoDistributeds = caseInfoDistributedRepository.findAll();
            if (Objects.isNull(caseInfoDistributeds) || caseInfoDistributeds.size() == 0) {
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createAlert("", "")).body(null);
            }
            for (CaseInfoDistributed caseInfoDistributed : caseInfoDistributeds) {
                if (Objects.nonNull(caseInfoDistributed.getMemo())) {
                    char[] charArray = caseInfoDistributed.getMemo().toCharArray();
                    String phoneNumber = "";
                    for (char temp : charArray) {
                        if (((int) temp >= 48 && (int) temp <= 57) || (int) temp == 45) {
                            phoneNumber += temp;
                        } else {
                            if (!Objects.equals(phoneNumber, "")) {
                                setPersonalContacts(caseInfoDistributed.getPersonalInfo().getId(), phoneNumber, user);
                            }
                            phoneNumber = "";
                        }
                    }
                    if (!Objects.equals(phoneNumber, "")) {
                        setPersonalContacts(caseInfoDistributed.getPersonalInfo().getId(), phoneNumber, user);
                    }
                }
                //解析完了将memo 置为空。
                caseInfoDistributed.setMemo(null);
            }
            caseInfoDistributedRepository.save(caseInfoDistributeds);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("RepairCaseDistributeController", "error", e.getMessage())).body(null);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("", null)).body(null);
    }

    /**
     * 增加联系人信息
     *
     * @param custId
     * @param phoneNumber
     */
    private void setPersonalContacts(String custId, String phoneNumber, User user) {
        PersonalContact personalContact = new PersonalContact();
        personalContact.setId(ShortUUID.uuid());
        personalContact.setPersonalId(custId);
        personalContact.setPhone(phoneNumber);
        personalContact.setInformed(0);
        personalContact.setPhoneStatus(Personal.PhoneStatus.NORMAL.getValue());
        personalContact.setSource(Constants.DataSource.IMPORT.getValue());
        personalContact.setOperator(user.getUserName());
        personalContact.setOperatorTime(ZWDateUtil.getNowDate());
        personalContact.setRelation(PersonalContact.relation.OTHER.getValue());
        personalContactRepository.save(personalContact);
    }

    @PostMapping("/manualAllocation")
    @ApiOperation(notes = "案件分配手动分案", value = "案件分配手动分案")
    public ResponseEntity manualAllocation(@RequestHeader(value = "X-UserToken") String token,
                                           @RequestBody ManualParams manualParams) {
        logger.debug("REST request to getCaseCountOnDept");
        try {
            User user = getUserByToken(token);
            caseInfoDistributedService.manualAllocation(manualParams, user,true);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("分配成功", "")).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "分配失败")).body(null);
        }
    }
    @PostMapping("/manualAllocationScheduled")
    @ApiOperation(notes = "案件分配手动分案-定时任务调用", value = "案件分配手动分案-定时任务调用")
    public ResponseEntity<List<String>> manualAllocationScheduled(@RequestBody ManualParams manualParams) {
        logger.debug("REST request to getCaseCountOnDept");
        try {
            User user = userRepository.findOne(Constants.ADMINISTRATOR_ID);//定时任务,使用管理员用户
            List<String> caseIdList = caseInfoDistributedService.manualAllocation(manualParams, user,false);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("分配成功", "")).body(caseIdList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "分配失败")).body(null);
        }
    }

    @PostMapping("/allocationCount")
    @ApiOperation(notes = "案件分配手动分案统计", value = "案件分配手动分案统计")
    public ResponseEntity<AllocationCountModel> allocationCount(@RequestBody ManualParams manualParams) {
        try {
            AllocationCountModel model = caseInfoDistributedService.allocationCount(manualParams);
            return ResponseEntity.ok().body(model);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "手动分案统计失败")).body(null);
        }
    }


    @PostMapping("/countStrategyAllocation")
    @ApiOperation(notes = "策略分配情况统计", value = "策略分配情况统计")
    public ResponseEntity countStrategyAllocation(@RequestBody CaseInfoDistributeParams caseInfoDistributeParams,
                                                  @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            strategyDistributeService.accessCountStrategyAllocation(caseInfoDistributeParams, user);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
        }
    }

    @PostMapping("/previewResult")
    @ApiOperation(value = "策略预览结果", notes = "策略预览结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity previewResult(@RequestBody PreviewParams previewParams,
                                        @RequestHeader(value = "X-UserToken") String token) {

        try {
            User user = getUserByToken(token);

            if (StringUtils.isBlank(previewParams.getJsonString())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请先配置策略")).body(null);
            }
            if (Objects.isNull(previewParams.getType())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择策略类型")).body(null);
            }
            CaseStrategy caseStrategy = null;
            try {
                caseStrategy = caseInfoDistributedService.previewResult(previewParams.getJsonString());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            boolean areaIdFlag = false;
            boolean ppsFlag = false;
            if (StringUtils.isNotBlank(caseStrategy.getStrategyText())) {
                if (caseStrategy.getStrategyText().contains(Constants.STRATEGY_AREA_ID)) {
                    areaIdFlag = true;
                }
                if (caseStrategy.getStrategyText().contains(Constants.STRATEGY_PRODUCT_SERIES)) {
                    ppsFlag = true;
                }
            }
            List<CaseInfoDistributedModel> checkedList = new ArrayList<>();
            CaseInfoDistributeParams caseInfoDistributeParams = new CaseInfoDistributeParams();
            caseInfoDistributeParams.setCompanyCode(user.getCompanyCode());
            caseInfoDistributeParams.setPersonalName(previewParams.getPersonalName());
            caseInfoDistributeParams.setMobileNo(previewParams.getPhone());
            caseInfoDistributeParams.setIdCard(previewParams.getIdCard());
            caseInfoDistributeParams.setBatchNumber(previewParams.getBatchNumber());
            caseInfoDistributeParams.setOverDueAmountStart(previewParams.getStartAmount());
            caseInfoDistributeParams.setOverDueAmountEnd(previewParams.getEndAmount());
            List<CaseInfoDistributedModel> caseInfoDistributedModelList = new ArrayList<>();
            List<Object[]> objects = null;
            if (Objects.equals(previewParams.getType(), CaseStrategy.StrategyType.IMPORT.getValue())) {// 案件导入策略分配
                try {
                    objects = entityManageService.getCaseInfoDistribute(caseInfoDistributeParams, "case_info_distributed");
                    caseInfoDistributedModelList = caseInfoService.findModelList(objects, caseInfoDistributedModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "获取预览数据失败")).body(null);
                }
                KieSession kieSession = null;
                try {
                    kieSession = runCaseStrategyService.runCaseRule(checkedList, caseStrategy, Constants.CASE_INFO_DISTRIBUTE_RULE);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
                }
                for (CaseInfoDistributedModel caseInfoDistributedModel : caseInfoDistributedModelList) {
                    if (checkedList.size() == 50) {
                        break;
                    }
                    kieSession.insert(caseInfoDistributedModel);//插入
                    kieSession.fireAllRules();//执行规则
                }
                kieSession.dispose();
            } else if (Objects.equals(previewParams.getType(), CaseStrategy.StrategyType.INNER.getValue())) {
                // 内催策略分配
                try {
                    objects = entityManageService.getCaseInfoDistribute(caseInfoDistributeParams, "case_info");
                    caseInfoDistributedModelList = caseInfoService.findModelList(objects, caseInfoDistributedModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "获取预览数据失败")).body(null);
                }
                KieSession kieSession = null;
                try {
                    kieSession = runCaseStrategyService.runCaseRule(checkedList, caseStrategy, Constants.CASE_INFO_RULE);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
                }
                for (CaseInfoDistributedModel caseInfoDistributedModel : caseInfoDistributedModelList) {
                    if (checkedList.size() == 50) {
                        break;
                    }
                    kieSession.insert(caseInfoDistributedModel);//插入
                    kieSession.fireAllRules();//执行规则
                }
                kieSession.dispose();
            } else if (Objects.equals(previewParams.getType(), CaseStrategy.StrategyType.OUTS.getValue())) {
                // 委外策略分配
                try {
                    objects = entityManageService.getOutSourcePool(caseInfoDistributeParams);
                    caseInfoDistributedModelList = caseInfoService.findModelList(objects, caseInfoDistributedModelList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "获取预览数据失败")).body(null);
                }
                KieSession kieSession = null;
                try {
                    kieSession = runCaseStrategyService.runCaseRule(checkedList, caseStrategy, Constants.OUTSOURCE);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
                }
                for (CaseInfoDistributedModel caseInfoDistributedModel : caseInfoDistributedModelList) {
                    if (checkedList.size() == 50) {
                        break;
                    }
                    kieSession.insert(caseInfoDistributedModel);//插入
                    kieSession.fireAllRules();//执行规则
                }
                kieSession.dispose();

            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择策略类型再预览")).body(null);
            }
            Content<CaseInfoDistributedModel> content = new Content<>();
            content.setContent(checkedList);
            return ResponseEntity.ok().body(content);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "预览结果失败")).body(null);
        }

    }

    @GetMapping("/findRecoverDistribute")
    @ApiOperation(value = "查询待分配案件的回收案件", notes = "查询待分配案件的回收案件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CaseInfoDistributed>> findRecoverDistribute(@QuerydslPredicate(root = CaseInfoDistributed.class) Predicate predicate,
                                                                           @ApiIgnore Pageable pageable,
                                                                           @RequestHeader(value = "X-UserToken") String token,
                                                                           @RequestParam(value = "companyCode", required = false) String companyCode) {
        logger.debug("REST request to findRecoverDistribute");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage()))
                    .body(null);
        }
        try {
            QCaseInfoDistributed qd = QCaseInfoDistributed.caseInfoDistributed;
            BooleanBuilder builder = new BooleanBuilder(predicate);
            if (Objects.isNull(user.getCompanyCode())) {
                if (StringUtils.isNotBlank(companyCode)) {
                    builder.and(qd.companyCode.eq(companyCode));
                }
            } else {
                builder.and(qd.companyCode.eq(user.getCompanyCode()));
            }
            builder.and(qd.recoverRemark.eq(CaseInfo.RecoverRemark.RECOVERED.getValue()));
            Page<CaseInfoDistributed> page = caseInfoDistributedRepository.findAll(builder, pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "系统异常!"))
                    .body(null);
        }
    }

    @DeleteMapping("/deleteRecoverDistribute")
    @ApiOperation(value = "删除待分配案件回收案件", notes = "删除待分配案件回收案件")
    public ResponseEntity deleteRecoverDistribute(@RequestBody CaseInfoIdList ids) {
        logger.debug("REST request to deleteRecoverDistribute");
        try {
            if (Objects.isNull(ids.getIds()) || ids.getIds().isEmpty()) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择要删除的案件")).body(null);
            }
            List<CaseInfoDistributed> all = caseInfoDistributedRepository.findAll(ids.getIds());
            caseInfoDistributedRepository.delete(all);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除成功", "")).body(null);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "删除错误")).body(null);
        }
    }

    @PostMapping("/importCaseScore")
    @ApiOperation(value = "案件导入待分配案件评分", notes = "案件导入待分配案件评分")
    public ResponseEntity importCaseScore(@RequestBody CaseInfoIdList params,
                                          @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            if (Objects.isNull(user.getCompanyCode())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "超级管理员不允许此项操作")).body(null);
            }
            KieSession kieSession = null;
            try {
                kieSession = runCaseStrategyService.createSorceRule(user.getCompanyCode(), CaseStrategy.StrategyType.IMPORT.getValue());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", e.getMessage())).body(null);
            }
            Iterator<CaseInfoDistributed> iterator;
            if (Objects.nonNull(params.getIds()) && !params.getIds().isEmpty()) {
                List<CaseInfoDistributed> all = caseInfoDistributedRepository.findAll(params.getIds());
                iterator = all.iterator();
            } else {
                QCaseInfoDistributed qCaseInfoDistributed = QCaseInfoDistributed.caseInfoDistributed;
                BooleanBuilder builder = new BooleanBuilder();
                // 未回收
                builder.and(qCaseInfoDistributed.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()));
                builder.and(qCaseInfoDistributed.companyCode.eq(user.getCompanyCode()));
                Iterable<CaseInfoDistributed> all = caseInfoDistributedRepository.findAll(builder);
                iterator = all.iterator();
            }

            List<CaseInfoDistributed> caseInfoDistributedList = new ArrayList<>();
            while (iterator.hasNext()) {
                CaseInfoDistributed next = iterator.next();
                ScoreRuleModel scoreRuleModel = new ScoreRuleModel();
                if (Objects.nonNull(next.getPersonalInfo())) {
                    scoreRuleModel.setAge(Objects.isNull(next.getPersonalInfo().getAge()) ? 0 : next.getPersonalInfo().getAge());
                    if (Objects.nonNull(next.getPersonalInfo().getPersonalJobs()) && !next.getPersonalInfo().getPersonalJobs().isEmpty()) {
                        scoreRuleModel.setIsWork(1);
                    } else {
                        scoreRuleModel.setIsWork(0);
                    }
                }
                scoreRuleModel.setOverDueAmount(next.getOverdueAmount().doubleValue());
                scoreRuleModel.setOverDueDays(next.getOverdueDays());
                if (Objects.nonNull(next.getArea())) {
                    if (Objects.nonNull(next.getArea().getParent())) {
                        scoreRuleModel.setProId(next.getArea().getParent().getId());
                    }
                } else {
                    scoreRuleModel.setProId(null);
                }
                kieSession.insert(scoreRuleModel);
                kieSession.fireAllRules();
                if (scoreRuleModel.getCupoScore() != 0) {
                    next.setScore(ZWMathUtil.DoubleToBigDecimal(scoreRuleModel.getCupoScore(), null, null));
                    caseInfoDistributedList.add(next);
                }
            }
            kieSession.dispose();
            caseInfoDistributedRepository.save(caseInfoDistributedList);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("评分成功", "")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "评分失败")).body(null);
        }
    }

    @GetMapping("/caseInfoDistributedExport")
    @ApiOperation(value = "待分配案件池案件导出", notes = "待分配案件池案件导出")
    public ResponseEntity caseInfoDistributedExport(@RequestHeader(value = "X-UserToken") String token,
                                                    DistributedExportParams distributedExportParams) {
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (final Exception e) {
            logger.debug(e.getMessage());
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", e.getMessage())).body(null);
        }

        // 保存任务盒子
        TaskBox taskBox = new TaskBox();
        taskBox.setOperatorTime(ZWDateUtil.getNowDateTime());
        taskBox.setOperator(user.getId());
        taskBox.setTaskStatus(TaskBox.Status.UN_FINISH.getValue());
        taskBox.setTaskDescribe("待分配案件池案件导出");
        taskBox.setCompanyCode(user.getCompanyCode());
        taskBox.setBeginDate(ZWDateUtil.getNowDateTime());
        taskBox.setType(TaskBox.Type.EXPORT.getValue());
        TaskBox finalTaskBox = taskBoxRepository.save(taskBox);

        XSSFWorkbook workbook = null;
        File file = null;
        ByteArrayOutputStream out = null;
        FileOutputStream fileOutputStream = null;

        //存储数据信息
        try {
            Map<String, String> headMap = caseInfoDistributedService.creatHeader();
            List<Map<String, Object>> dataList = caseInfoDistributedService.createExcelData(distributedExportParams);
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("待分配案件列表");
            ExcelExportHelper.createExcel(workbook, sheet, headMap, dataList, 0, 0);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            String filePath = FileUtils.getTempDirectoryPath().concat(File.separator).concat(DateTime.now().toString("yyyyMMddhhmmss") + "待分配案件表.xlsx");
            file = new File(filePath);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(out.toByteArray());
            FileSystemResource resource = new FileSystemResource(file);
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("file", resource);
            ResponseEntity<String> url = restTemplate.postForEntity("http://file-service/api/uploadFile/addUploadFileUrl", param, String.class);
            logger.debug(url.getBody());
            if (url != null && url.getBody() != null && !url.getBody().trim().isEmpty()) {
                finalTaskBox.setTaskStatus(TaskBox.Status.FINISHED.getValue());
                finalTaskBox.setRemark(url.getBody());
            } else {
                finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
            }
        } catch (Exception e) {
            finalTaskBox.setTaskStatus(TaskBox.Status.FAILURE.getValue());
            logger.error(e.getMessage(), e);
        } finally {
            finalTaskBox.setEndDate(ZWDateUtil.getNowDateTime());
            taskBoxRepository.save(finalTaskBox);
            try {
                reminderService.sendTaskBoxMessage(finalTaskBox);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                logger.error("待分配案件导出发送消息失败");
            }
            // 关闭流
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            // 删除文件
            if (file != null) {
                file.delete();
            }
        }
        return ResponseEntity.ok().body(finalTaskBox.getId());
    }


}
