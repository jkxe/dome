package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.entity.CaseFollowupAttachment;
import cn.fintecher.pangolin.report.entity.CaseInfo;
import cn.fintecher.pangolin.report.mapper.CaseInfoMapper;
import cn.fintecher.pangolin.report.mapper.FollowupRecord4MobileMapper;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.model.mobile.*;
import cn.fintecher.pangolin.report.util.AppUtils;
import cn.fintecher.pangolin.util.BeanUtils;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.hsjry.lang.common.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : xiaqun
 * @Description : 案件多条件查询
 * @Date : 9:38 2017/11/1
 */

@RestController
@RequestMapping("/api/CaseInfoInquiryController")
@Api(description = "案件多条件查询")
public class CaseInfoInquiryController extends BaseController {
    private final static String ENTITY_CASE_INFO = "CaseInfo";
    private final static String ENTITY_CASE_ASSIST = "CaseAssist";
    private final static String ENTITY_CASE_TURN_RECORD = "CaseTurnRecord";
    private final Logger log = LoggerFactory.getLogger(CaseInfoInquiryController.class);



    @Inject
    CaseInfoMapper caseInfoMapper;

    @Autowired
    private FollowupRecord4MobileMapper followupRecord4MobileMapper;



    /**
     * @Description 多条件查询电催，外访案件
     */
    @GetMapping("/getCaseInfoByCondition")
    @ApiOperation(value = "多条件查询电催，外访案件", notes = "多条件查询电催，外访案件")
    public ResponseEntity<Page<CaseInfoModel>> getCaseInfoByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            String code = StringUtils.isEmpty(caseInfoConditionParams.getCode()) ? tokenUser.getDepartment().getCode() : caseInfoConditionParams.getCode();
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndOverDueDate())){
                caseInfoConditionParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getStartOverDueDate()),1)));
                caseInfoConditionParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getEndOverDueDate()),1)));
            }
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getCaseInfoByCondition(
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),
                    StringUtils.trim(caseInfoConditionParams.getLoanInvoiceNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getFollowupBack(),
                    StringUtils.trim(caseInfoConditionParams.getCollectorName()),
                    StringUtils.trim(caseInfoConditionParams.getLastCollectorName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getOverdueCountMin(),
                    caseInfoConditionParams.getOverdueCountMax(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getPrincipalId(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getFollowupBack(),
                    caseInfoConditionParams.getAssistWay(),
                    caseInfoConditionParams.getCaseMark(),
                    caseInfoConditionParams.getCollectionType(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    StringUtils.trim(code),
                    caseInfoConditionParams.getCollectionStatusList(),
                    caseInfoConditionParams.getCollectionStatus(),
                    Objects.isNull(caseInfoConditionParams.getAreaId()) ? caseInfoConditionParams.getParentAreaId() : null,
                    caseInfoConditionParams.getAreaId(),
                    tokenUser.getType(),
                    tokenUser.getManager(),
                    tokenUser.getId(),
                    tokenUser.getCompanyCode(),
                    caseInfoConditionParams.getRealPayMaxAmt(),
                    caseInfoConditionParams.getRealPayMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getSeriesName()),
                    StringUtils.trim(caseInfoConditionParams.getProductName()),
                    caseInfoConditionParams.getTurnFromPool(),
                    caseInfoConditionParams.getTurnToPool(),
                    caseInfoConditionParams.getTurnApprovalStatus(),
                    caseInfoConditionParams.getLawsuitResult(),
                    caseInfoConditionParams.getAntiFraudResult(),
                    caseInfoConditionParams.getStartFollowDate(),
                    caseInfoConditionParams.getEndFollowDate(),
                    caseInfoConditionParams.getStartOverDueDate(),
                    caseInfoConditionParams.getEndOverDueDate(),
                    caseInfoConditionParams.getSourceChannel(),
                    caseInfoConditionParams.getCollectionMethod(),
                    caseInfoConditionParams.getQueueName(),
                    caseInfoConditionParams.getCaseFollowInTime(),
                    caseInfoConditionParams.getPersonalType(),
                    caseInfoConditionParams.getOverduePeriods(),
                    caseInfoConditionParams.getOverdueCount(),
                    caseInfoConditionParams.getMerchantName(),
                    caseInfoConditionParams.getStartSettleDate(),
                    caseInfoConditionParams.getEndSettleDate());

            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> pageResult = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());

//            List<CaseInfoModel> infoModels = totalInfoDebtCase(caseInfoModels);
//            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
//            List<CaseInfoModel> lister = infoModels.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
//            Page<CaseInfoModel> pageResult = new PageImpl<>(lister, pageable, infoModels.size());
//            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(infoModels);
//            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
//            Page<CaseInfoModel> page = new PageImpl<>(infoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(pageResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_INFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询协催案件
     */
    @GetMapping("/getCaseAssistByCondition")
    @ApiOperation(value = "多条件查询协催案件", notes = "多条件查询协催案件")
    public ResponseEntity<Page<CaseAssistModel>> getCaseAssistByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                          @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case assist by condition");
        try {
            User tokenUser = getUserByToken(token);
//            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseAssistModel> caseAssistModels = caseInfoMapper.getCaseAssistByCondition(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    caseInfoConditionParams.getAssistStatusList(),
                    tokenUser.getDepartment().getCode(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    tokenUser.getManager(),
                    tokenUser.getId(),
                    tokenUser.getCompanyCode(),
                    caseInfoConditionParams.getFollowupBack());
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            List<CaseAssistModel> lister = caseAssistModels.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<CaseAssistModel> pageResult = new PageImpl<>(lister, pageable, caseAssistModels.size());
//            PageInfo<CaseAssistModel> pageInfo = new PageInfo<>(assistModels);
//            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
//            Page<CaseAssistModel> page = new PageImpl<>(assistModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_ASSIST)).body(pageResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_ASSIST, "caseAssist", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询内催回收案件
     */
    @GetMapping("/getInnerAllCaseInfoReturn")
    @ApiOperation(value = "多条件查询内催回收案件", notes = "多条件查询内催回收案件")
    public ResponseEntity<Page<CaseInfoReturnModel>> getInnerAllCaseInfoReturn(CaseInfoConditionParams caseInfoConditionParams,
                                                                               @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get inner case return by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoReturnModel> caseInfoReturnModels = caseInfoMapper.getInnerAllCaseInfoReturn(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getOperatorMinTime()),
                    StringUtils.trim(caseInfoConditionParams.getOperatorMaxTime()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    StringUtils.trim(caseInfoConditionParams.getOverduePeriods()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoReturnModel> pageInfo = new PageInfo<>(caseInfoReturnModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoReturnModel> page = new PageImpl<>(caseInfoReturnModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "caseInfoReturn")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("caseInfoReturn", "caseInfoReturn", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询委外回收案件
     */
    @GetMapping("/getOutAllCaseInfoReturn")
    @ApiOperation(value = "多条件查询委外回收案件", notes = "多条件查询委外回收案件")
    public ResponseEntity<Page<CaseInfoReturnModel>> getOutAllCaseInfoReturn(CaseInfoConditionParams caseInfoConditionParams,
                                                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get out case return by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoReturnModel> caseInfoReturnModels = caseInfoMapper.getOutAllCaseInfoReturn(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    StringUtils.trim(caseInfoConditionParams.getOutsName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    StringUtils.trim(caseInfoConditionParams.getOverOutsourceTime()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoReturnModel> pageInfo = new PageInfo<>(caseInfoReturnModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoReturnModel> page = new PageImpl<>(caseInfoReturnModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "caseInfoReturn")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("caseInfoReturn", "caseInfoReturn", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询内催待分配和结案案件
     */
    @GetMapping("/getInnerWaitCollectCase")
    @ApiOperation(value = "多条件查询内催待分配和结案案件", notes = "多条件查询内催待分配和结案案件")
    public ResponseEntity<Page<CaseInfoModel>> getInnerWaitCollectCase(CaseInfoConditionParams caseInfoConditionParams,
                                                                       @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get inner wait case return by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            Integer type = 2;
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndOverDueDate())){
                caseInfoConditionParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getStartOverDueDate()),1)));
                caseInfoConditionParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getEndOverDueDate()),1)));
            }
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartSettleDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndSettleDate())){
                caseInfoConditionParams.setStartSettleDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(caseInfoConditionParams.getStartSettleDate()));
                caseInfoConditionParams.setEndSettleDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(caseInfoConditionParams.getEndSettleDate()));
            }
            if(caseInfoConditionParams.getCollectionStatusList().contains("25")){
                type = 0 ;//待分配
            }
            if(caseInfoConditionParams.getCollectionStatusList().contains("21")){
                type = 1; //催收中
            }
            if(caseInfoConditionParams.getCollectionStatusList().contains("24")){
                type = 3 ;//已结清
            }
            String deptCode;
            if(StringUtils.isEmpty(caseInfoConditionParams.getDeptCode())){
                deptCode = user.getDepartment().getCode();
            }else{
                deptCode = caseInfoConditionParams.getDeptCode();
            }
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getInnerWaitCollectCase(
                    StringUtils.trim(caseInfoConditionParams.getCollectionStatusList()),
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalId()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getParentAreaId(),
                    caseInfoConditionParams.getAreaId(),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getStartOverDueDate(),
                    caseInfoConditionParams.getEndOverDueDate(),
                    StringUtils.trim(caseInfoConditionParams.getDelegationDateStart()),
                    StringUtils.trim(caseInfoConditionParams.getDelegationDateEnd()),
                    StringUtils.trim(caseInfoConditionParams.getCloseDateStart()),
                    StringUtils.trim(caseInfoConditionParams.getCloseDateEnd()),
                    caseInfoConditionParams.getCommissionMaxRate(),
                    caseInfoConditionParams.getCommissionMinRate(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    StringUtils.trim(deptCode),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode(),
                    StringUtils.trim(caseInfoConditionParams.getCollectorName()),
                    StringUtils.trim(caseInfoConditionParams.getLastCollectorName()),
                    caseInfoConditionParams.getFollowupBack(),
                    caseInfoConditionParams.getTurnFromPool(),
                    caseInfoConditionParams.getTurnToPool(),
                    caseInfoConditionParams.getLawsuitResult(),
                    caseInfoConditionParams.getAntiFraudResult(),
                    StringUtils.trim(caseInfoConditionParams.getProductName()),
                    StringUtils.trim(caseInfoConditionParams.getSeriesId()),
                    StringUtils.trim(caseInfoConditionParams.getSeriesName()),//产品系列名称（类型）
                    StringUtils.trim(caseInfoConditionParams.getCaseFollowInTime()),
                    caseInfoConditionParams.getSourceChannel(),
                    caseInfoConditionParams.getCollectionMethod(),
                    caseInfoConditionParams.getQueueName(),
                    caseInfoConditionParams.getOverdueCount(),
                    caseInfoConditionParams.getMerchantName(),
                    caseInfoConditionParams.getStartFollowDate(),
                    caseInfoConditionParams.getEndFollowDate(),
                    caseInfoConditionParams.getStartSettleDate(),
                    caseInfoConditionParams.getEndSettleDate(),
                    caseInfoConditionParams.getPersonalType(),
                    type);
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "caseInfoReturn")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("caseInfoReturn", "caseInfoReturn", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询委外待分配和结案案件
     */
    @GetMapping("/getOutWaitCollectCase")
    @ApiOperation(value = "多条件查询委外待分配和结案案件", notes = "多条件查询委外待分配和结案案件")
    public ResponseEntity<Page<CaseInfoModel>> getOutWaitCollectCase(CaseInfoConditionParams caseInfoConditionParams,
                                                                     @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get out wait case return by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getoutWaitCollectCase(
                    StringUtils.trim(caseInfoConditionParams.getOutStatusList()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    StringUtils.trim(caseInfoConditionParams.getOutTime()),
                    StringUtils.trim(caseInfoConditionParams.getEndOutsourceTime()),
                    StringUtils.trim(caseInfoConditionParams.getOverduePeriods()),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "caseInfoReturn")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("caseInfoReturn", "caseInfoReturn", "查询失败")).body(null);
        }
    }


    /**
     * @Description 多条件查询司法案件
     */
    @GetMapping("/getCaseInfoJudicialByCondition")
    @ApiOperation(value = "多条件查询司法案件", notes = "多条件查询司法案件")
    public ResponseEntity<Page<CaseInfoJudicialModel>> getCaseInfoJudicialByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get Judicial case  by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoJudicialModel> caseInfoJudicialModels = caseInfoMapper.getCaseInfoJudicialByCondition(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    user.getDepartment().getCode(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoJudicialModel> pageInfo = new PageInfo<>(caseInfoJudicialModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoJudicialModel> page = new PageImpl<>(caseInfoJudicialModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "CaseInfoJudicialModel")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("CaseInfoJudicialModel", "CaseInfoJudicialModel", "查询失败")).body(null);
        }

    }

    /**
     * @Description 多条件查询流转案件
     */
    @GetMapping("/getTurnCaseByCondition")
    @ApiOperation(value = "多条件查询流转案件", notes = "多条件查询流转案件")
    public ResponseEntity<Page<CaseInfoModel>> getTurnCaseByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get turn case return by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getTurnCaseByCondition(
                    caseInfoConditionParams.getCaseTypeList(),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getCollectionStatus(),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    caseInfoConditionParams.getParentAreaId(),
                    caseInfoConditionParams.getAreaId(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getCirculationStatus(),
                    user.getDepartment().getCode(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "caseInfoReturn")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("caseInfoReturn", "caseInfoReturn", "查询失败")).body(null);
        }

    }

    /**
     * @Description 多条件查询导入的重复案件案件
     */
    @GetMapping("/getCaseInfoExceptionByCondition")
    @ApiOperation(value = "多条件查询导入的重复案件案件", notes = "多条件查询导入的重复案件案件")
    public ResponseEntity<Page<CaseInfoExceptionModel>> getCaseInfoExceptionByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                                        @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get Case Info Exception by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoExceptionModel> caseInfoExceptionModels = caseInfoMapper.getCaseInfoExceptionByCondition(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoExceptionModel> pageInfo = new PageInfo<>(caseInfoExceptionModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoExceptionModel> page = new PageImpl<>(caseInfoExceptionModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "CaseInfoException")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("CaseInfoException", "CaseInfoException", "查询失败")).body(null);
        }

    }

    /**
     * @Description 多条件查询核销管理案件
     */
    @GetMapping("/getCaseInfoVerificationByCondition")
    @ApiOperation(value = "多条件查询核销管理案件", notes = "多条件查询核销管理案件")
    public ResponseEntity<Page<CaseInfoModel>> getCaseInfoVerificationByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                                  @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get Case Info Verification by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getCaseInfoVerificationByCondition(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getDelegationDate()),
                    StringUtils.trim(caseInfoConditionParams.getCloseDate()),
                    caseInfoConditionParams.getCommissionMaxRate(),
                    caseInfoConditionParams.getCommissionMinRate(),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "CaseInfoException")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("CaseInfoException", "CaseInfoException", "查询失败")).body(null);
        }
    }

    /**
     * @Description 核销案件打包查询
     */
    @GetMapping("/getCaseInfoVerificationPackaging")
    @ApiOperation(value = "核销案件打包查询", notes = "核销案件打包查询")
    public ResponseEntity<Page<caseInfoVerificationPackagingModel>> getCaseInfoVerificationPackaging(CaseInfoConditionParams caseInfoConditionParams,
                                                                                                     @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get Case Info Verification by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<caseInfoVerificationPackagingModel> caseInfoVerificationPackagingModels = caseInfoMapper.getCaseInfoVerificationPackaging(
                    StringUtils.trim(caseInfoConditionParams.getPackagingTime()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<caseInfoVerificationPackagingModel> pageInfo = new PageInfo<>(caseInfoVerificationPackagingModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<caseInfoVerificationPackagingModel> page = new PageImpl<>(caseInfoVerificationPackagingModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "CaseInfoException")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("CaseInfoException", "CaseInfoException", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询案件修复信息
     */
    @GetMapping("/getAllRepairingCase")
    @ApiOperation(value = "多条件查询案件修复信息", notes = "多条件查询案件修复信息")
    public ResponseEntity<Page<CaseInfoModel>> getAllRepairingCase(CaseInfoConditionParams caseInfoConditionParams,
                                                                   @RequestHeader(value = "X-UserToken") String token) {
        log.debug("Rest request to get Case Info Repairing by condition");
        try {
            User user = getUserByToken(token);
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            if(null != caseInfoConditionParams.getOperatorTime()){
                Date date = DateUtil.getDate(caseInfoConditionParams.getOperatorTime(), "yyyy-MM-dd");
                String date1 = DateUtil.getDate(date, "yyyy-MM-dd");
                caseInfoConditionParams.setOperatorTime(date1);
            }else{
                caseInfoConditionParams.setOperatorTime(null);
            }
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getAllRepairingCase(
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),// 客户姓名
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),// 客户手机号
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),// 案件编号
                    StringUtils.trim(caseInfoConditionParams.getLoanInvoiceNumber()),// 借据号
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),//  身份证
                    caseInfoConditionParams.getOverduePeriods(),
                    caseInfoConditionParams.getOperatorTime(),
                    caseInfoConditionParams.getOverMaxDay(),//  预期最大天数
                    caseInfoConditionParams.getOverMinDay(),// 预期最小天数
                    caseInfoConditionParams.getRepairStatus(),
                    caseInfoConditionParams.getOverdueCount(),//  逾期次数
                    user.getDepartment().getCode(),
                    user.getManager(),
                    user.getId(),
                    user.getCompanyCode());
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", "CaseInfoException")).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().headers(HeaderUtil.createFailureAlert("CaseInfoException", "CaseInfoException", "查询失败")).body(null);
        }
    }



    /**
     * @Description 多条件查询 电子邮件，信函催收
     */
    @GetMapping("/getInnerCaseInfoByCondition")
    @ApiOperation(value = "多条件查询内催案件 催收中案件 查看 接口", notes = "多条件查询内催案件 催收中案件 查看 接口")
    public ResponseEntity<Page<CaseInfoModel>> getInnerCaseInfoByCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                           @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get inner case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            caseInfoConditionParams.setCode(tokenUser.getDepartment().getCode());
            caseInfoConditionParams.setUserId(tokenUser.getId());
            caseInfoConditionParams.setCompanyCode(tokenUser.getCompanyCode());
            caseInfoConditionParams.setIsManager(tokenUser.getManager());
            caseInfoConditionParams.setSort("followupBack,asc");
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getInnerCaseInfoByCondition(caseInfoConditionParams);
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_INFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询内催案件 催收中案件 查看 接口
     */
    @GetMapping("/getCaseInfoByNoPower")
    @ApiOperation(value = "多条件查询内催案件 催收中案件 查看 接口", notes = "多条件查询内催案件 催收中案件 查看 接口")
    public ResponseEntity<Page<CaseInfoModel>> getCaseInfoByNoPower(CaseInfoConditionParams caseInfoConditionParams,
                                                                    @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndOverDueDate())){
                caseInfoConditionParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getStartOverDueDate()),1)));
                caseInfoConditionParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getEndOverDueDate()),1)));
            }
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getCaseInfoByNoPower(
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),
                    StringUtils.trim(caseInfoConditionParams.getLoanInvoiceNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getFollowupBack(),
                    StringUtils.trim(caseInfoConditionParams.getCollectorName()),
                    StringUtils.trim(caseInfoConditionParams.getLastCollectorName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getOverdueCountMin(),
                    caseInfoConditionParams.getOverdueCountMax(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getPrincipalId(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getFollowupBack(),
                    caseInfoConditionParams.getAssistWay(),
                    caseInfoConditionParams.getCaseMark(),
                    caseInfoConditionParams.getCollectionType(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    StringUtils.trim(tokenUser.getDepartment().getCode()), //不做权限控制 祁吉贵
                    caseInfoConditionParams.getCollectionStatusList(),
                    caseInfoConditionParams.getCollectionStatus(),
                    Objects.isNull(caseInfoConditionParams.getAreaId()) ? caseInfoConditionParams.getParentAreaId() : null,
                    caseInfoConditionParams.getAreaId(),
                    tokenUser.getType(),
                    null,//不做权限控制 祁吉贵
                    tokenUser.getId(),
                    tokenUser.getCompanyCode(),
                    caseInfoConditionParams.getRealPayMaxAmt(),
                    caseInfoConditionParams.getRealPayMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getSeriesName()),
                    StringUtils.trim(caseInfoConditionParams.getProductName()),
                    caseInfoConditionParams.getTurnFromPool(),
                    caseInfoConditionParams.getTurnToPool(),
                    caseInfoConditionParams.getTurnApprovalStatus(),
                    caseInfoConditionParams.getLawsuitResult(),
                    caseInfoConditionParams.getAntiFraudResult(),
                    caseInfoConditionParams.getStartFollowDate(),
                    caseInfoConditionParams.getEndFollowDate(),
                    caseInfoConditionParams.getStartOverDueDate(),
                    caseInfoConditionParams.getEndOverDueDate(),
                    caseInfoConditionParams.getSourceChannel(),
                    caseInfoConditionParams.getCollectionMethod(),
                    caseInfoConditionParams.getQueueName(),
                    caseInfoConditionParams.getCaseFollowInTime(),
                    caseInfoConditionParams.getPersonalType(),
                    StringUtils.trim(caseInfoConditionParams.getOverduePeriods()),
                    caseInfoConditionParams.getOverdueCount(),
                    caseInfoConditionParams.getMerchantName(),
                    caseInfoConditionParams.getStartSettleDate(),
                    caseInfoConditionParams.getEndSettleDate());
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_INFO, "caseInfo", "查询失败")).body(null);
        }
    }


    /**
     * @param caseTurnParams
     * @Description 案件流转记录多条件查询
     * @Return
     */
    @GetMapping("/getCaseTurnRecord")
    @ApiOperation(value = "案件流转记录多条件查询", notes = "案件流转记录多条件查询")
    public ResponseEntity<Page<CaseTurnModel>> getCaseTurnRecord(CaseTurnParams caseTurnParams,
                                                                 @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            PageHelper.startPage(caseTurnParams.getPage() + 1, caseTurnParams.getSize());
            List<CaseTurnModel> caseTurnModels = caseInfoMapper.getCaseTurnRecord(
                    caseTurnParams.getCaseNumber(),
                    caseTurnParams.getBatchNumber(),
                    caseTurnParams.getPrincipalName(),
                    caseTurnParams.getPersonName(),
                    caseTurnParams.getMobileNo(),
                    caseTurnParams.getIdCard(),
                    caseTurnParams.getTurnFromPool(),
                    caseTurnParams.getTurnToPool(),
                    caseTurnParams.getCasePoolType(),
                    caseTurnParams.getTriggerAction(),
                    caseTurnParams.getOperatorStartTime(),
                    caseTurnParams.getOperatorEndTime(),
                    caseTurnParams.getApplyTime(),
                    caseTurnParams.getTurnApprovalStatus(),
                    tokenUser.getCompanyCode(),
                    caseTurnParams.getCirculationType());
//            List<CaseTurnModel> turnModels = totalTurnDebtCase(caseTurnModels);
            PageInfo<CaseTurnModel> pageInfo = new PageInfo<>(caseTurnModels);
            Pageable pageable = new PageRequest(caseTurnParams.getPage(), caseTurnParams.getSize());
            Page<CaseTurnModel> page = new PageImpl<>(caseTurnModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_TURN_RECORD)).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_TURN_RECORD, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询不同案件池已分配案件
     */
    @GetMapping("/getCaseByPoolTypeAndCondition")
    @ApiOperation(value = "多条件查询不同案件池已分配案件", notes = "多条件查询不同案件池已分配案件")
    public ResponseEntity<Page<CaseInfoModel>> getCaseByPoolTypeAndCondition(CaseInfoConditionParams caseInfoConditionParams,
                                                                      @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndOverDueDate())){
                caseInfoConditionParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getStartOverDueDate()),1)));
                caseInfoConditionParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getEndOverDueDate()),1)));
            }
            String code = StringUtils.isEmpty(caseInfoConditionParams.getCode()) ? tokenUser.getDepartment().getCode() : caseInfoConditionParams.getCode();
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getCaseByPoolTypeAndCondition(
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getFollowupBack(),
                    StringUtils.trim(caseInfoConditionParams.getCollectorName()),
                    StringUtils.trim(caseInfoConditionParams.getLastCollectorName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getStartOverDueDate(),
                    caseInfoConditionParams.getEndOverDueDate(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getPrincipalId(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getFeedBack(),
                    caseInfoConditionParams.getAssistWay(),
                    caseInfoConditionParams.getCaseMark(),
                    caseInfoConditionParams.getCollectionType(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    StringUtils.trim(code),
                    caseInfoConditionParams.getCollectionStatusList(),
                    caseInfoConditionParams.getCollectionStatus(),
                    Objects.isNull(caseInfoConditionParams.getAreaId()) ? caseInfoConditionParams.getParentAreaId() : null,
                    caseInfoConditionParams.getAreaId(),
                    tokenUser.getType(),
                    tokenUser.getManager(),
                    tokenUser.getId(),
                    tokenUser.getCompanyCode(),
                    caseInfoConditionParams.getRealPayMaxAmt(),
                    caseInfoConditionParams.getRealPayMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getSeriesName()),
                    StringUtils.trim(caseInfoConditionParams.getProductName()),
                    caseInfoConditionParams.getTurnFromPool(),
                    caseInfoConditionParams.getTurnToPool(),
                    caseInfoConditionParams.getTurnApprovalStatus(),
                    caseInfoConditionParams.getLawsuitResult(),
                    caseInfoConditionParams.getAntiFraudResult(),
                    caseInfoConditionParams.getCasePoolType(),
                    caseInfoConditionParams.getStartFollowDate(),
                    caseInfoConditionParams.getEndFollowDate(),
                    caseInfoConditionParams.getCaseFollowInTime(),
                    caseInfoConditionParams.getQueueName(),
                    caseInfoConditionParams.getSourceChannel(),
                    caseInfoConditionParams.getCollectionMethod(),
                    caseInfoConditionParams.getOverdueCount(),
                    caseInfoConditionParams.getMerchantName(),
                    caseInfoConditionParams.getPersonalType());
//            List<CaseInfoModel> infoModels = totalInfoDebtCase(caseInfoModels);
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_INFO, "caseInfo", "查询失败")).body(null);
        }
    }

    /**
     * @Description 多条件查询不同案件池待分配案件
     */
    @GetMapping("/getWaitCaseByPoolType")
    @ApiOperation(value = "多条件查询不同案件池待分配案件", notes = "多条件查询不同案件池已分配案件")
    public ResponseEntity<Page<CaseInfoModel>> getWaitCaseByPoolType(CaseInfoConditionParams caseInfoConditionParams,
                                                                             @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to get case info by condition");
        try {
            User tokenUser = getUserByToken(token);
            if (!Strings.isNullOrEmpty(caseInfoConditionParams.getStartOverDueDate()) && !Strings.isNullOrEmpty(caseInfoConditionParams.getEndOverDueDate())){
                caseInfoConditionParams.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getStartOverDueDate()),1)));
                caseInfoConditionParams.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(caseInfoConditionParams.getEndOverDueDate()),1)));
            }
            String code = StringUtils.isEmpty(caseInfoConditionParams.getCode()) ? tokenUser.getDepartment().getCode() : caseInfoConditionParams.getCode();
            PageHelper.startPage(caseInfoConditionParams.getPage() + 1, caseInfoConditionParams.getSize());
            List<CaseInfoModel> caseInfoModels = caseInfoMapper.getWaitCaseByPoolType(
                    StringUtils.trim(caseInfoConditionParams.getCaseNumber()),
                    StringUtils.trim(caseInfoConditionParams.getPersonalName()),
                    StringUtils.trim(caseInfoConditionParams.getMobileNo()),
                    caseInfoConditionParams.getFollowupBack(),
                    StringUtils.trim(caseInfoConditionParams.getCollectorName()),
                    StringUtils.trim(caseInfoConditionParams.getLastCollectorName()),
                    caseInfoConditionParams.getOverdueMaxAmt(),
                    caseInfoConditionParams.getOverdueMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getPrincipalName()),
                    StringUtils.trim(caseInfoConditionParams.getPayStatus()),
                    caseInfoConditionParams.getOverMaxDay(),
                    caseInfoConditionParams.getOverMinDay(),
                    caseInfoConditionParams.getStartOverDueDate(),
                    caseInfoConditionParams.getEndOverDueDate(),
                    StringUtils.trim(caseInfoConditionParams.getBatchNumber()),
                    caseInfoConditionParams.getPrincipalId(),
                    StringUtils.trim(caseInfoConditionParams.getIdCard()),
                    caseInfoConditionParams.getFeedBack(),
                    caseInfoConditionParams.getAssistWay(),
                    caseInfoConditionParams.getCaseMark(),
                    caseInfoConditionParams.getCollectionType(),
                    caseInfoConditionParams.getSort() == null ? null : caseInfoConditionParams.getSort(),
                    StringUtils.trim(code),
                    caseInfoConditionParams.getCollectionStatusList(),
                    caseInfoConditionParams.getCollectionStatus(),
                    Objects.isNull(caseInfoConditionParams.getAreaId()) ? caseInfoConditionParams.getParentAreaId() : null,
                    caseInfoConditionParams.getAreaId(),
                    tokenUser.getType(),
                    tokenUser.getManager(),
                    tokenUser.getId(),
                    tokenUser.getCompanyCode(),
                    caseInfoConditionParams.getRealPayMaxAmt(),
                    caseInfoConditionParams.getRealPayMinAmt(),
                    StringUtils.trim(caseInfoConditionParams.getSeriesName()),
                    StringUtils.trim(caseInfoConditionParams.getProductName()),
                    caseInfoConditionParams.getTurnFromPool(),
                    caseInfoConditionParams.getTurnToPool(),
                    caseInfoConditionParams.getTurnApprovalStatus(),
                    caseInfoConditionParams.getLawsuitResult(),
                    caseInfoConditionParams.getAntiFraudResult(),
                    caseInfoConditionParams.getCasePoolType(),
                    caseInfoConditionParams.getStartFollowDate(),
                    caseInfoConditionParams.getEndFollowDate(),
                    caseInfoConditionParams.getCaseFollowInTime(),
                    caseInfoConditionParams.getQueueName(),
                    caseInfoConditionParams.getSourceChannel(),
                    caseInfoConditionParams.getCollectionMethod(),
                    caseInfoConditionParams.getOverdueCount(),
                    caseInfoConditionParams.getMerchantName(),
                    caseInfoConditionParams.getPersonalType());
//            List<CaseInfoModel> infoModels = totalInfoDebtCase(caseInfoModels);
            if(caseInfoModels.size()>0){
                for(int i = 0; i<caseInfoModels.size();i++ ){
                    if(  null!=caseInfoModels.get(i).getStopTime()){
                        Date stopTime = caseInfoModels.get(i).getStopTime();
                         Date nowDateTime = ZWDateUtil.getNowDateTime();
                        Integer day = (int)(nowDateTime.getTime() - stopTime.getTime())/(24*60*60*1000);
                        caseInfoModels.get(i).setStopNum(day);//  计算天数
                    }else{
                        caseInfoModels.get(i).setStopNum(0);//  默认0天
                    }
                }
            }
            PageInfo<CaseInfoModel> pageInfo = new PageInfo<>(caseInfoModels);
            Pageable pageable = new PageRequest(caseInfoConditionParams.getPage(), caseInfoConditionParams.getSize());
            Page<CaseInfoModel> page = new PageImpl<>(caseInfoModels, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_CASE_INFO, "caseInfo", "查询失败")).body(null);
        }
    }



    public List<CaseInfoModel> totalInfoDebtCase(List<CaseInfoModel> list){
        // 共债案件合并的公共方法
        if (Objects.isNull(list) || list.size()==0){
            // 查询出来的数据是空的
            return list;
        }
        List<CaseInfoModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list.size() ; i ++ ) {
            // 判断停催天数
            if (list.get(i).getStopTime() != null) {
                Integer between = ZWDateUtil.getBetween(list.get(i).getStopTime(), ZWDateUtil.getNowDateTime(), ChronoUnit.DAYS);
                list.get(i).setStopNum(between);
            }

            if (caseNums.contains(list.get(i).getCaseNumber())) {
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            CaseInfoModel caseInfoModel = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                if (caseInfoModel.getCaseNumber().equals(list.get(j).getCaseNumber())) {
                    // 有共债案件需要合并(合并逻辑)
                    if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfoModel.getOverduePeriods())) {
                        // 判断逾期期数大小
                        if (new BigDecimal(caseInfoModel.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1) {
                            caseInfoModel.setOverduePeriods(list.get(j).getOverduePeriods());
                        }
                    }
                    if (Objects.nonNull(list.get(j).getOverdueDays()) && Objects.nonNull(caseInfoModel.getOverdueDays())) {
                        // 判断逾期天数
                        if (new BigDecimal(caseInfoModel.getOverdueDays()).compareTo(new BigDecimal(list.get(j).getOverdueDays())) < 1) {
                            caseInfoModel.setOverdueDays(list.get(j).getOverdueDays());
                        }
                    }
                    // 合并逾期总金额
                    if (Objects.nonNull(caseInfoModel.getOverdueAmount()) && Objects.nonNull(list.get(j).getOverdueAmount())) {
                        String overdueAmounts = new BigDecimal(caseInfoModel.getOverdueAmount()).add(new BigDecimal(list.get(j).getOverdueAmount())).toString();
                        caseInfoModel.setOverdueAmount(overdueAmounts);
                    }
                    // 合并到账金额
                    if (Objects.nonNull(caseInfoModel.getAccountBalance()) && Objects.nonNull(list.get(j).getAccountBalance())) {
                        caseInfoModel.setAccountBalance(caseInfoModel.getAccountBalance().add(list.get(j).getAccountBalance()));
                    }
                }
            }
            list1.add(caseInfoModel);
//
//            Collections.sort(list1, new Comparator<CaseInfoModel>() {
//                @Override
//                public int compare(CaseInfoModel o1, CaseInfoModel o2) {
//                    int diff = o1.getOverdueDays() - o1.getOverdueDays();
//                    if (diff > 0){
//                        return 1;
//                    }else if(diff < 0){
//                        return -1;
//                    }
//                    return 0;
//                }
//            }); // 按年龄排序

        }
        return list1;
    }

    public List<CaseAssistModel> totalAssistDebtCase(List<CaseAssistModel> list){
        // 共债案件合并的公共方法
        if (Objects.isNull(list)){
            // 查询出来的数据是空的
            return list;
        }
        List<CaseAssistModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
            if (caseNums.contains(list.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            CaseAssistModel caseAssistModel = list.get(i);
            for (int  j  =   i+1 ; j  <  list.size() ; j ++ ){
                if  (caseAssistModel.getCaseNumber().equals(list.get(j).getCaseNumber()))  {
                    // 合并逾期总金额
                    caseAssistModel.setOverdueAmount(caseAssistModel.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                    // 合并到账金额
                    caseAssistModel.setAccountBalance(caseAssistModel.getAccountBalance().add(list.get(j).getAccountBalance()));
                }
            }
            list1.add(caseAssistModel);
        }
        return list1;
    }

    public List<CaseTurnModel> totalTurnDebtCase(List<CaseTurnModel> list){
        // 共债案件合并的公共方法
        if (Objects.isNull(list)){
            // 查询出来的数据是空的
            return list;
        }
        List<CaseTurnModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
            if (caseNums.contains(list.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            CaseTurnModel caseTurnModel = list.get(i);
            for (int  j  =   i+1 ; j  <  list.size() ; j ++ ){
                if  (caseTurnModel.getCaseNumber().equals(list.get(j).getCaseNumber()))  {

                    if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseTurnModel.getOverduePeriods())){
                        // 判断逾期期数大小
                        if (new BigDecimal(caseTurnModel.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1){
                            caseTurnModel.setOverduePeriods(list.get(j).getOverduePeriods());
                        }
                    }
                    if (Objects.nonNull(list.get(j).getOverdueDays()) && Objects.nonNull(caseTurnModel.getOverdueDays())){
                        // 判断逾期天数
                        if (new BigDecimal(caseTurnModel.getOverdueDays()).compareTo(new BigDecimal(list.get(j).getOverdueDays())) < 1){
                            caseTurnModel.setOverdueDays(list.get(j).getOverdueDays());
                        }
                    }
                    // 合并逾期总金额
                    if (Objects.nonNull(caseTurnModel.getOverdueAmount()) && Objects.nonNull(list.get(j).getOverdueAmount())){
                        caseTurnModel.setOverdueAmount(caseTurnModel.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                    }
                }
            }
            list1.add(caseTurnModel);
        }
        return list1;
    }




    //**************************************************  以下接口是APP需要的 **********************************************************//

    /**
     * @param
     * @Description: 多条件查询电催，外访案件，针对移动版的接口。
     * ------------------   任务列表 对应接口
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/29 0029 下午 3:19
     */
    @GetMapping("/getCaseInfoByCondition4Mobile")
    @ApiOperation(value = "多条件查询电催，外访案件", notes = "多条件查询电催，外访案件")
    public ResponseEntity<Page<CaseInfo4MobileModel>> getCaseInfoByCondition4Mobile(
            @RequestParam(required = false) @ApiParam("客户姓名") String personalName,
            @RequestParam(required = false) @ApiParam("地址") String address,
            @RequestParam(required = false) @ApiParam("催收状态") String collectionStatus,
            @RequestParam(required = false) @ApiParam("催收类型") String collectionType,
            @RequestParam @ApiParam("页数") Integer page,
            @RequestParam @ApiParam("每页条数") Integer pageSize,
            @RequestHeader(value = "X-UserToken") String token) {
        User user;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }

        try {
            List<CaseInfo4MobileModel> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(collectionType)) {
                if (CaseInfo.CollectionType.TEL.getValue().intValue() == Integer.parseInt(collectionType)) { // 协催
                    resultList = getAssistCaseInfo(collectionStatus, user, personalName, address);
                } else if (CaseInfo.CollectionType.VISIT.getValue().intValue() == Integer.parseInt(collectionType)) { // 协催
                    resultList = getVisitCaseInfo(collectionStatus, user, personalName, address);
                }
            } else {
                List<CaseInfo4MobileModel> assistList = getAssistCaseInfo(collectionStatus, user, personalName, address);
                List<CaseInfo4MobileModel> visitList = getVisitCaseInfo(collectionStatus, user, personalName, address);
                resultList.addAll(assistList);
                resultList.addAll(visitList);
            }
            // 共债案件合并
            List<CaseInfo4MobileModel> caseInfo4MobileModels = totalCaseInfo4MobileModel(resultList);
            Pageable pageable = new PageRequest(page, pageSize);
            List<CaseInfo4MobileModel> lister = caseInfo4MobileModels.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<CaseInfo4MobileModel> pageResult = new PageImpl<>(lister, pageable, caseInfo4MobileModels.size());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("分页查询协催案件成功", ENTITY_CASE_INFO)).body(pageResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "分页查询协催案件失败")).body(null);
        }
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 下午 3:51
     * @Description: 查询外访案件
     */
    public List<CaseInfo4MobileModel> getVisitCaseInfo(String assistStatus, User user, String personalName, String address) {
        Map<String, String> queryparam = AppUtils.getQueryparam4Visit(user, assistStatus, personalName, address, followupRecord4MobileMapper);
        List<VisitModel4Mobile> list = followupRecord4MobileMapper.getVisitCaseInfo(queryparam);
        List<CaseInfo4MobileModel> resultList = new ArrayList<>();
        VisitModel4Mobile visit = null;
        CaseInfo4MobileModel result = null;
        for (int i = 0; i < list.size(); i++) {
            visit = list.get(i);
            result = new CaseInfo4MobileModel();
            BeanUtils.copyProperties(visit, result);
            resultList.add(result);
        }
        AppUtils.setLatitudeAndLongitude4Visit(list, restTemplate, followupRecord4MobileMapper, user.getId());
        return resultList;
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/26 0026 下午 3:51
     * @Description: 查询协催案件
     */
    public List<CaseInfo4MobileModel> getAssistCaseInfo(String assistStatus, User user, String personalName, String address) {
        Map<String, String> queryparam = AppUtils.getQueryparam4Assist(user, assistStatus, personalName, address, followupRecord4MobileMapper);
        List<AssistModel4Mobile> list = followupRecord4MobileMapper.getAssistCaseInfo(queryparam);
        List<CaseInfo4MobileModel> resultList = new ArrayList<>();
        AssistModel4Mobile assist = null;
        CaseInfo4MobileModel result = null;
        for (int i = 0; i < list.size(); i++) {
            assist = list.get(i);
            result = new CaseInfo4MobileModel();
            if (StringUtils.isNotEmpty(assist.getCollectionTypeName())) {
                if (assist.getCollectionTypeName().equals("电催")) {
                    assist.setCollectionTypeName("协催");
                }
            }
            BeanUtils.copyProperties(assist, result);
            resultList.add(result);
        }
        AppUtils.setLatitudeAndLongitude4Assist(list, restTemplate, followupRecord4MobileMapper, user.getId());
        return resultList;
    }

    /**
     * @Description: 基本信息 的接口。
     * ------------------  基本信息 页面接口
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/29 0029 下午 3:19
     */
    @GetMapping("/getBaseInfo4Mobile")
    @ApiOperation(value = "获取外访案件基本信息", notes = "获取外访案件基本信息")
    public ResponseEntity<BaseInfoModel> getBaseInfo4Mobile(
            @RequestParam(required = false) @ApiParam("客户id") String personId,
            @RequestHeader(value = "X-UserToken") String token) {
        try {
            getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        BaseInfoModel baseInfoModel = new BaseInfoModel();
        try {
            PersonalVModel personalVModel = caseInfoMapper.getPersonalVModel(personId);
            List<PersonalAddressVModel> personalAddress = caseInfoMapper.getPersonalAddress(personId);
            List<ContactVModel> contactInfo = caseInfoMapper.getContactInfo(personId);
            baseInfoModel.setPersonalAddress(personalAddress);
            baseInfoModel.setPersonalVModel(personalVModel);
            baseInfoModel.setContactInfo(contactInfo);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(baseInfoModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    /**
     * @param
     * @Description: 获取案件基本信息
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/30 0030 下午 4:55
     */
    @GetMapping("/getCaseInfo4Mobile")
    @ApiOperation(value = "获取案件基本信息", notes = "获取案件基本信息")
    public ResponseEntity<CaseInfo4MobileVModel> getCaseInfo4Mobile(
            @RequestParam(required = false) @ApiParam("案件编号") String caseNumber,
            @RequestParam(required = false) @ApiParam("借据号") String loanInvoiceNumber,
            @RequestHeader(value = "X-UserToken") String token) {
        try {
            getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            List<CaseInfo4MobileVModel> caseInfo4MobileVModel = caseInfoMapper.getCaseInfo4Mobile(caseNumber);
            // 合并公债案件
            CaseInfo4MobileVModel mobileVModel = new CaseInfo4MobileVModel();
            List<String> list = new ArrayList<>();
            if (!Strings.isNullOrEmpty(loanInvoiceNumber)) {
                // 这个是传借据号    以借据号维度展示数据
                for (CaseInfo4MobileVModel info4MobileVModel : caseInfo4MobileVModel) {
                    info4MobileVModel.setHasPayPeriods(info4MobileVModel.getLoanTenure() - info4MobileVModel.getHasPayPeriods());
                    if (info4MobileVModel.getLoanInvoiceNumber().equals(loanInvoiceNumber)){
                        mobileVModel = info4MobileVModel;
                        list.add(info4MobileVModel.getLoanInvoiceNumber());
                        continue;
                    }
                }
            }else {

                // 合并公债案件
                for (int i = 0; i < caseInfo4MobileVModel.size(); i++) {
                    if (i == 0){
                        mobileVModel = caseInfo4MobileVModel.get(0);
                        list.add(mobileVModel.getLoanInvoiceNumber());
                        continue;
                    }
                    // 合并贷款金额
                    if (mobileVModel.getApprovedLoanAmt() != null && caseInfo4MobileVModel.get(i).getApprovedLoanAmt() != null){
                        mobileVModel.setApprovedLoanAmt(mobileVModel.getApprovedLoanAmt().add(caseInfo4MobileVModel.get(i).getApprovedLoanAmt()));
                    }
                    // 合并逾期金额
                    if (mobileVModel.getOverdueAmount() != null && caseInfo4MobileVModel.get(i).getOverdueAmount() != null){
                        mobileVModel.setOverdueAmount(mobileVModel.getOverdueAmount().add(caseInfo4MobileVModel.get(i).getOverdueAmount()));
                    }
                    // 合并逾期期数
                    if (mobileVModel.getOverduePeriods() <= caseInfo4MobileVModel.get(i).getOverduePeriods()){
                        mobileVModel.setOverduePeriods(caseInfo4MobileVModel.get(i).getOverduePeriods());
                    }
                    // 合并最大逾期天数
                    if(mobileVModel.getOverdueDays() <= caseInfo4MobileVModel.get(i).getOverdueDays()){
                        mobileVModel.setOverdueDays(caseInfo4MobileVModel.get(i).getOverdueDays());
                    }
                    // 合并逾期本金
                    if (mobileVModel.getOverdueCapital() != null && caseInfo4MobileVModel.get(i).getOverdueCapital() != null){
                        mobileVModel.setOverdueCapital(mobileVModel.getOverdueCapital().add(caseInfo4MobileVModel.get(i).getOverdueCapital()));
                    }
                    // 合并逾期利息
                    if (mobileVModel.getUnpaidInterest() != null && caseInfo4MobileVModel.get(i).getUnpaidInterest() != null){
                        mobileVModel.setUnpaidInterest(mobileVModel.getUnpaidInterest().add(caseInfo4MobileVModel.get(i).getUnpaidInterest()));
                    }
                    // 合并未结罚息
                    if (mobileVModel.getPnltFine() != null && caseInfo4MobileVModel.get(i).getPnltFine() != null){
                        mobileVModel.setPnltFine(mobileVModel.getPnltFine().add(caseInfo4MobileVModel.get(i).getPnltFine()));
                    }
                    // 合并未结利息
                    if (mobileVModel.getPnltInterest() != null && caseInfo4MobileVModel.get(i).getPnltInterest() != null){
                        mobileVModel.setPnltInterest(mobileVModel.getPnltInterest().add(caseInfo4MobileVModel.get(i).getPnltInterest()));
                    }
                    list.add(caseInfo4MobileVModel.get(i).getLoanInvoiceNumber());
                }
            }
            mobileVModel.setLoanInvoiceNumbers(list);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(mobileVModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
    }


    /**
     * @Description: 修改用户图像
     * --------------------  首页 修改用户图像接口。
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 3:19
     */
    @GetMapping("/updateUserPhoto4Mobile")
    @ApiOperation(value = "修改用户图像", notes = "修改用户图像")
    public ResponseEntity updateUserPhoto4Mobile(@RequestParam String userId, @RequestParam String photoUrl,
                                                 @RequestHeader(value = "X-UserToken") String token) {
        try {
            getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            caseInfoMapper.updateUserPhoto4Mobile(userId, photoUrl);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改用户图像成功", "")).body("修改用户图像成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "修改用户图像失败")).body(null);
        }
    }

    /**
     * @Description: APP端查询案件跟进记录详情
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 4:25
     */
    @GetMapping("/getFollowupRecordDetail4Mobile")
    @ApiOperation(value = "APP端查询案件跟进记录详情", notes = "APP端查询案件跟进记录详情")
    public ResponseEntity<CaseFollowupRecordModel> getFollowupRecordDetail4Mobile(@RequestParam @ApiParam("跟进记录ID") String recordId,
                                                                                  @RequestHeader(value = "X-UserToken") String token) {
        try {
            getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            List<CaseFollowupRecordModel> record = caseInfoMapper.getFollowupRecord4Mobile(null, recordId);
            CaseFollowupRecordModel model = null;
            if (null != record && record.size() > 0) {
                model = record.get(0);
                model.setPhotoAttachment(caseInfoMapper.getCaseFollowupAttachment4Mobile(model.getId(), "photo"));
                model.setAudioAttachment(caseInfoMapper.getCaseFollowupAttachment4Mobile(model.getId(), "audio"));
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("APP端查询案件跟进记录详情成功", ENTITY_CASE_INFO)).body(model);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "APP端查询案件协跟进录详情失败")).body(null);
        }
    }

    /**
     * @Description: APP端查询案件跟进记录列表
     * ----------------- 跟进记录列表界面/详情界面 接口
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/9 0009 上午 11:34
     */
    @GetMapping("/getFollowupRecord4Mobile")
    @ApiOperation(value = "APP端查询案件跟进记录列表", notes = "APP端查询案件跟进记录列表")
    public ResponseEntity<Page<CaseFollowupRecordModel>> getFollowupRecord4Mobile(@RequestParam @ApiParam("案件ID") String caseId,
                                                                                  @RequestParam @ApiParam("页数") Integer page,
                                                                                  @RequestParam @ApiParam("每页条数") Integer pageSize,
                                                                                  @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to getFollowupRecord4Mobile");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            List<CaseFollowupRecordModel> list = caseInfoMapper.getFollowupRecord4Mobile(caseId, null);
            CaseFollowupRecordModel model = null;
            for (int i = 0; i < list.size(); i++) {
                model = list.get(i);
                model.setCurrentCollector(user.getId());
                model.setCurrentCollectorName(user.getRealName());
                model.setDeptCode(user.getDepartment().getCode());
                model.setDeptName(user.getDepartment().getName());
                model.setPhotoAttachment(caseInfoMapper.getCaseFollowupAttachment4Mobile(model.getId(), "photo"));
                model.setAudioAttachment(caseInfoMapper.getCaseFollowupAttachment4Mobile(model.getId(), "audio"));
            }
            Pageable pageable = new PageRequest(page, pageSize);
            List<CaseFollowupRecordModel> lister = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<CaseFollowupRecordModel> pageResult = new PageImpl<>(lister, pageable, list.size());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_CASE_INFO)).body(pageResult);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }

    /**
     * @Description: APP端添加案件跟进记录
     * ---------------------  新增跟进记录界面 接口
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 5:09
     */
    @PostMapping("/addFollowupRecord4Mobile")
    @ApiOperation(value = "APP端添加案件跟进记录", notes = "APP端添加案件跟进记录")
    public ResponseEntity addFollowupRecord4Mobile(@RequestBody CaseFollowupRecordParam caseFollowupRecordParam,
                                                   @RequestHeader(value = "X-UserToken") String token) {
        log.debug("REST request to addFollowupRecord4Mobile");
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            caseFollowupRecordParam.setId(id);
            caseFollowupRecordParam.setOperator(user.getUserName());
            caseFollowupRecordParam.setOperatorName(user.getRealName());
            caseFollowupRecordParam.setOperatorTime(new Date());
            caseFollowupRecordParam.setFollowPerson(user.getRealName());
            caseFollowupRecordParam.setFollowTime(new Date());
            // 在案件跟进记录中既有caseId又有caseNumber，这样可以去掉一个
            String caseNumberTemp = followupRecord4MobileMapper.getCaseNumberByCaseId(caseFollowupRecordParam.getCaseId());
            caseFollowupRecordParam.setCaseNumber(caseNumberTemp);
            int collectionType = caseFollowupRecordParam.getCollectionType();
            if (collectionType == CaseFollowupRecordParam.CollectionType.TEL.getValue().intValue()) { // 协催案件
                caseFollowupRecordParam.setType(CaseFollowupRecordParam.Type.ASSIST.getValue());
            } else if (collectionType == CaseFollowupRecordParam.CollectionType.VISIT.getValue().intValue()) { // 外访案件
                caseFollowupRecordParam.setType(CaseFollowupRecordParam.Type.VISIT.getValue());
            }
            followupRecord4MobileMapper.addFollowupRecord4Mobile(caseFollowupRecordParam);
            /**
             *  添加跟进记录后，将案件的状态由 待催收 改为催收中。
             *  修改 协催记录 case_assist表中的 assist_status = 28
             *  case_info 中 collectionStatus=21
             */
            List<CaseInfo> list = caseInfoMapper.findCaseInfoByCaseNumber(caseNumberTemp);
            for (int i = 0; i < list.size(); i++) {
                followupRecord4MobileMapper.updateAssistStatusIsCollecting(list.get(i).getId());
                Map<String, String> param = new HashMap<>();
                param.put("caseId", list.get(i).getId());
                param.put("followupBack", caseFollowupRecordParam.getCollectionFeedback() + "");
                followupRecord4MobileMapper.updateCollectionStatusIsCollecting(param);
            }
            // 添加音频附件
            List<CaseFollowupAttachment> audioAttachmentList = caseFollowupRecordParam.getAudioAttachment();
            if (null != audioAttachmentList && audioAttachmentList.size() > 0) {
                CaseFollowupAttachment audio = null;
                for (int i = 0; i < audioAttachmentList.size(); i++) {
                    audio = audioAttachmentList.get(i);
                    audio.setId(UUID.randomUUID().toString().replace("-", ""));
                    audio.setType("audio");
                    audio.setOperator(user.getId());
                    audio.setOperatorTime(new Date());
                    audio.setCaseFollowupRecordId(id);
                    followupRecord4MobileMapper.addCaseFollowupAttachment4Mobile(audio);
                }
            }
            // 添加图片附件
            List<CaseFollowupAttachment> photoAttachmentList = caseFollowupRecordParam.getPhotoAttachment();
            if (null != photoAttachmentList && photoAttachmentList.size() > 0) {
                CaseFollowupAttachment photo = null;
                for (int i = 0; i < photoAttachmentList.size(); i++) {
                    photo = photoAttachmentList.get(i);
                    photo.setId(UUID.randomUUID().toString().replace("-", ""));
                    photo.setType("photo");
                    photo.setOperator(user.getId());
                    photo.setOperatorTime(new Date());
                    photo.setCaseFollowupRecordId(id);
                    followupRecord4MobileMapper.addCaseFollowupAttachment4Mobile(photo);
                }
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("APP端添加案件协催记录成功", ENTITY_CASE_INFO)).body("APP端添加案件协催记录成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "APP端添加案件协催记录失败")).body("APP端添加案件协催记录失败");
        }
    }


    /**
     * @Description: 首页获取的有外访和协催的，因此在查询金额的时候，判断条件是当前催员或协催人员的id是传入的id。
     * --------------------------  首页汇总数据接口。
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/12 0012 下午 3:50
     */
    @GetMapping("/getIndexResult4Mobile")
    @ApiOperation(value = "获取外访人员首页汇总结果", notes = "获取外访人员首页汇总结果")
    public ResponseEntity getIndexResult4Mobile(@RequestHeader(value = "X-UserToken") String token) {
        User user = null;
        try {
            user = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "用户没有登录")).body(null);
        }
        try {
            String personalId = null;
            int isManager = user.getManager();
            if (1 == isManager) {
                String departCode = user.getDepartment().getCode();
                String assistCollector = null;
                if (StringUtils.isNotEmpty(departCode)) {
                    List<String> list = followupRecord4MobileMapper.getAllUserIdByDeptCode(departCode);
                    if (null != list && list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            sb.append("'").append(list.get(i)).append("',");
                        }
                        assistCollector = sb.toString();
                        assistCollector = assistCollector.substring(0, assistCollector.length() - 1);
                    }
                }
                personalId = assistCollector;
            } else {
                personalId = "'" + user.getId() + "'";
            }
            List<String> result = followupRecord4MobileMapper.getIndexResult4Mobile(personalId);
            IndexResult4MobileModel model = new IndexResult4MobileModel();
            // 待外访案件数量
            String waitForVisitNumber = result.get(0).substring(0, result.get(0).indexOf("."));
            model.setWaitForVisitNumber(waitForVisitNumber);
            // 外访中案件数量
            String visitingNumber = result.get(1).substring(0, result.get(1).indexOf("."));
            model.setVisitingNumber(visitingNumber);
            // 外访案件总数
            int visitingCount = Integer.parseInt(waitForVisitNumber) + Integer.parseInt(visitingNumber);
            model.setVisitingCount(visitingCount);


            // 待协催案件数量
            String waitForAssistNumber = result.get(2).substring(0, result.get(2).indexOf("."));
            model.setWaitForAssistNumber(waitForAssistNumber);
            // 协催中案件数量
            String assistingNumber = result.get(3).substring(0, result.get(3).indexOf("."));
            model.setAssistingNumber(result.get(3).substring(0, result.get(3).indexOf(".")));
            int assistCount = Integer.parseInt(waitForAssistNumber) + Integer.parseInt(assistingNumber);
            model.setAssistCount(assistCount);
            int totalCount = visitingCount + assistCount;
            model.setTotalCount(totalCount);
            // 待催收金额
            String waitForAssistMoneryCount = result.get(4).substring(0, result.get(4).indexOf(".") + 3);
            model.setWaitForAssistMoneryCount(waitForAssistMoneryCount);
            // 欠款总额
            String accountBalanceCount = result.get(5).substring(0, result.get(5).indexOf(".") + 3);
            model.setAccountBalanceCount(accountBalanceCount);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取外访人员首页汇总结果成功", "")).body(model);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "获取外访人员首页汇总结果失败")).body(null);
        }
    }



    public List<CaseInfo4MobileModel> totalCaseInfo4MobileModel(List<CaseInfo4MobileModel> list){
        // 共债案件合并的公共方法
        if (Objects.isNull(list)){
            // 查询出来的数据是空的
            return list;
        }
        List<CaseInfo4MobileModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
            if (caseNums.contains(list.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            CaseInfo4MobileModel caseInfo4MobileModel = list.get(i);
            for (int  j  =   i+1 ; j  <  list.size() ; j ++ ){
                if  (caseInfo4MobileModel.getCaseNumber().equals(list.get(j).getCaseNumber()))  {

                    if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(caseInfo4MobileModel.getOverduePeriods())){
                        // 判断逾期期数大小
                        if (new BigDecimal(caseInfo4MobileModel.getOverduePeriods()).compareTo(new BigDecimal(list.get(j).getOverduePeriods())) < 1){
                            caseInfo4MobileModel.setOverduePeriods(list.get(j).getOverduePeriods());
                        }
                    }
                    // 合并逾期总金额
                    caseInfo4MobileModel.setOverdueAmount(caseInfo4MobileModel.getOverdueAmount() + (list.get(j).getOverdueAmount()));
                    // 合并账户余额
                    caseInfo4MobileModel.setAccountBalance(caseInfo4MobileModel.getAccountBalance() + list.get(i).getAccountBalance());
                }
            }
            list1.add(caseInfo4MobileModel);
        }
        return list1;
    }

}