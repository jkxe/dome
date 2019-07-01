package cn.fintecher.pangolin.report.mapper;

import cn.fintecher.pangolin.entity.Personal;
import cn.fintecher.pangolin.report.entity.CaseFollowupAttachment;
import cn.fintecher.pangolin.report.entity.CaseInfo;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.model.mobile.CaseFollowupRecordModel;
import cn.fintecher.pangolin.report.model.mobile.CaseInfo4MobileVModel;
import cn.fintecher.pangolin.report.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: PeiShouWen
 * @Description:
 * @Date 17:50 2017/8/1
 */
public interface CaseInfoMapper extends MyMapper<CaseInfo> {
    //    List<CaseInfo> queryWaitCollectCase(@Param("deptCode") String deptCode,
//                                        @Param("companyCode") String companyCode);
    List<CaseInfo> queryWaitCollectCase(CaseInfoParams caseInfoParams);

    List<CaseInfo> queryWaitOwnCollectCase(CaseInfoParams caseInfoParams);

    List<CollectingCaseInfo> queryCollectingCase(CollectingCaseParams collectingCaseParams);

    void updateLngLat(Personal personal);

    /**
     * @Description 多条件查询案件信息
     */
    List<CaseInfoModel> getCaseInfoByCondition(
            @Param("caseNumber") String caseNumber,
           @Param("loanInvoiceNumber")String loanInvoiceNumber,
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("followupBack") Integer followupBack,
            @Param("collectorName") String collectorName,
            @Param("lastCollectorName") String lastCollectorName,
            @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
            @Param("overdueMinAmt") BigDecimal overdueMinAmt,
            @Param("principalName") String principalName,
            @Param("payStatus") String payStatus,
            @Param("overMaxDay") Integer overMaxDay,
            @Param("overMinDay") Integer overMinDay,
            @Param("overdueCountMin") Integer overdueCountMin,
            @Param("overdueCountMax") Integer overdueCountMax,
            @Param("batchNumber") String batchNumber,
            @Param("principalId") String principalId,
            @Param("idCard") String idCard,
            @Param("feedBack") Integer feedBack,
            @Param("assistWay") Integer assistWay,
            @Param("caseMark") Integer caseMark,
            @Param("collectionType") String collectionType,
            @Param("sort") String sort,
            @Param("code") String code,
            @Param("collectionStatusList") String collectionStatusList,
            @Param("collectionStatus") Integer collectionStatus,
            @Param("parentAreaId") Integer parentAreaId,
            @Param("areaId") Integer areaId,
            @Param("type") Integer type,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode,
            @Param("realPayMaxAmt") BigDecimal realPayMaxAmt,
            @Param("realPayMinAmt") BigDecimal realPayMinAmt,
            @Param("seriesName") String seriesName,
            @Param("productName") String productName,
            @Param("turnFromPool") Integer turnFromPool,
            @Param("turnToPool") Integer turnToPool,
            @Param("turnApprovalStatus") Integer turnApprovalStatus,
            @Param("lawsuitResult") Integer lawsuitResult,
            @Param("antiFraudResult") Integer antiFraudResult,
            @Param("startFollowDate") String startFollowDate,
            @Param("endFollowDate") String endFollowDate,
            @Param("startOverDueDate") String startOverDueDate,
            @Param("endOverDueDate") String endOverDueDate,
            @Param("sourceChannel") String sourceChannel,
            @Param("collectionMethod") String collectionMethod,
            @Param("queueName") String queueName,
            @Param("caseFollowInTime") String caseFollowInTime,
            @Param("personalType") Integer personalType,
            @Param("overduePeriods") String overduePeriods,
            @Param("overdueCount") Integer overdueCount,
            @Param("merchantName") String merchantName,
            @Param("startSettleDate") String startSettleDate,
            @Param("endSettleDate") String endSettleDate);
    /**
     * @Description 多条件查询案件信息 以借据为维度
     */
    List<CaseInfoModel> getCaseInfoByNoPower(
            @Param("caseNumber") String caseNumber,
           @Param("loanInvoiceNumber")String loanInvoiceNumber,
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("followupBack") Integer followupBack,
            @Param("collectorName") String collectorName,
            @Param("lastCollectorName") String lastCollectorName,
            @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
            @Param("overdueMinAmt") BigDecimal overdueMinAmt,
            @Param("principalName") String principalName,
            @Param("payStatus") String payStatus,
            @Param("overMaxDay") Integer overMaxDay,
            @Param("overMinDay") Integer overMinDay,
            @Param("overdueCountMin") Integer overdueCountMin,
            @Param("overdueCountMax") Integer overdueCountMax,
            @Param("batchNumber") String batchNumber,
            @Param("principalId") String principalId,
            @Param("idCard") String idCard,
            @Param("feedBack") Integer feedBack,
            @Param("assistWay") Integer assistWay,
            @Param("caseMark") Integer caseMark,
            @Param("collectionType") String collectionType,
            @Param("sort") String sort,
            @Param("code") String code,
            @Param("collectionStatusList") String collectionStatusList,
            @Param("collectionStatus") Integer collectionStatus,
            @Param("parentAreaId") Integer parentAreaId,
            @Param("areaId") Integer areaId,
            @Param("type") Integer type,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode,
            @Param("realPayMaxAmt") BigDecimal realPayMaxAmt,
            @Param("realPayMinAmt") BigDecimal realPayMinAmt,
            @Param("seriesName") String seriesName,
            @Param("productName") String productName,
            @Param("turnFromPool") Integer turnFromPool,
            @Param("turnToPool") Integer turnToPool,
            @Param("turnApprovalStatus") Integer turnApprovalStatus,
            @Param("lawsuitResult") Integer lawsuitResult,
            @Param("antiFraudResult") Integer antiFraudResult,
            @Param("startFollowDate") String startFollowDate,
            @Param("endFollowDate") String endFollowDate,
            @Param("startOverDueDate") String startOverDueDate,
            @Param("endOverDueDate") String endOverDueDate,
            @Param("sourceChannel") String sourceChannel,
            @Param("collectionMethod") String collectionMethod,
            @Param("queueName") String queueName,
            @Param("caseFollowInTime") String caseFollowInTime,
            @Param("personalType") Integer personalType,
            @Param("overduePeriods") String overduePeriods,
            @Param("overdueCount") Integer overdueCount,
            @Param("merchantName") String merchantName,
            @Param("startSettleDate") String startSettleDate,
            @Param("endSettleDate") String endSettleDate);

    /**
     * @Description 多条件查询协催案件信息
     */
    List<CaseAssistModel> getCaseAssistByCondition(@Param("personalName") String personalName,
                                                   @Param("mobileNo") String mobileNo,
                                                   @Param("overdueMaxAmount") BigDecimal overdueMaxAmt,
                                                   @Param("overdueMinAmount") BigDecimal overdueMinAmt,
                                                   @Param("assistStatusList") String assistStatusList,
                                                   @Param("deptCode") String deptCode,
                                                   @Param("sort") String sort,
                                                   @Param("isManager") Integer isManager,
                                                   @Param("userId") String userId,
                                                   @Param("companyCode") String companyCode,
                                                   @Param("followupBack") Integer followupBack);

    /**
     * @Description 多条件查询协催案件信息
     */
    List<CaseInfoModel> getInnerCaseInfoByCondition(CaseInfoConditionParams caseInfoConditionParams);

    List<CaseInfoModel> getExportCaseInfoBycaseInfo(@Param("caseInfo") CaseInfoParams caseInfo);

    /**
     * @Description 多条件查询内催回收案件信息
     */
    List<CaseInfoReturnModel> getInnerAllCaseInfoReturn(@Param("personalName") String personalName,
                                                        @Param("mobileNo") String mobileNo,
                                                        @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                        @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                        @Param("operatorMinTime") String operatorMinTime,
                                                        @Param("operatorMaxTime") String operatorMaxTime,
                                                        @Param("overMaxDay") Integer overMaxDay,
                                                        @Param("overMinDay") Integer overMinDay,
                                                        @Param("overduePeriods") String overduePeriods,
                                                        @Param("batchNumber") String batchNumber,
                                                        @Param("deptCode") String deptCode,
                                                        @Param("isManager") Integer isManager,
                                                        @Param("userId") String userId,
                                                        @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询委外回收案件信息
     */
    List<CaseInfoReturnModel> getOutAllCaseInfoReturn(@Param("personalName") String personalName,
                                                      @Param("mobileNo") String mobileNo,
                                                      @Param("idCard") String idCard,
                                                      @Param("batchNumber") String batchNumber,
                                                      @Param("outsName") String outsName,
                                                      @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                      @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                      @Param("overMaxDay") Integer overMaxDay,
                                                      @Param("overMinDay") Integer overMinDay,
                                                      @Param("overOutsourceTime") String overOutsourceTime,
                                                      @Param("deptCode") String deptCode,
                                                      @Param("isManager") Integer isManager,
                                                      @Param("userId") String userId,
                                                      @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询内催待分配和结案案件信息
     */
    List<CaseInfoModel> getInnerWaitCollectCase(@Param("collectionStatusList") String collectionStatusList,
                                                @Param("caseNumber") String caseNumber,
                                                @Param("personalName") String personalName,
                                                @Param("mobileNo") String mobileNo,
                                                @Param("principalId") String principalId,
                                                @Param("batchNumber") String batchNumber,
                                                @Param("parentAreaId") Integer parentAreaId,
                                                @Param("areaId") Integer areaId,
                                                @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                @Param("payStatus") String payStatus,
                                                @Param("overMaxDay") Integer overMaxDay,
                                                @Param("overMinDay") Integer overMinDay,
                                                @Param("startOverDueDate") String startOverDueDate,
                                                @Param("endOverDueDate") String endOverDueDate,
                                                @Param("delegationDateStart") String delegationDateStart,
                                                @Param("delegationDateEnd") String delegationDateEnd,
                                                @Param("closeDateStart") String closeDateStart,
                                                @Param("closeDateEnd") String closeDateEnd,
                                                @Param("commissionMaxRate") BigDecimal commissionMaxRate,
                                                @Param("commissionMinRate") BigDecimal commissionMinRate,
                                                @Param("idCard") String idCard,
                                                @Param("sort") String sort,
                                                @Param("deptCode") String deptCode,
                                                @Param("isManager") Integer isManager,
                                                @Param("userId") String userId,
                                                @Param("companyCode") String companyCode,
                                                @Param("collectorName") String collectorName,
                                                @Param("lastCollectorName") String lastCollectorName,
                                                @Param("followupBack") Integer followupBack,
                                                @Param("turnFromPool") Integer turnFromPool,
                                                @Param("turnToPool") Integer turnToPool,
                                                @Param("lawsuitStatus") Integer lawsuitStatus,
                                                @Param("antiFraudStatus") Integer antiFraudStatus,
                                                @Param("productName") String productName,
                                                @Param("seriesId") String seriesId,
                                                @Param("seriesName") String seriesName,
                                                @Param("caseFollowInTime") String caseFollowInTime,
                                                @Param("sourceChannel") String sourceChannel,
                                                @Param("collectionMethod") String collectionMethod,
                                                @Param("queueName") String queueName,
                                                @Param("overdueCount") Integer overdueCount,
                                                @Param("merchantName") String merchantName,
                                                @Param("startFollowDate") String startFollowDate,
                                                @Param("endFollowDate") String endFollowDate,
                                                @Param("startSettleDate") String startSettleDate,
                                                @Param("endSettleDate") String endSettleDate,
                                                @Param("personalType") Integer personalType,
                                                @Param("type") Integer type);

    /**
     * @Description 多条件查询委外待分配和结案案件信息
     */
    List<CaseInfoModel> getoutWaitCollectCase(@Param("outStatusList") String outStatusList,
                                              @Param("personalName") String personalName,
                                              @Param("mobileNo") String mobileNo,
                                              @Param("idCard") String idCard,
                                              @Param("batchNumber") String batchNumber,
                                              @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                              @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                              @Param("overMaxDay") Integer overMaxDay,
                                              @Param("overMinDay") Integer overMinDay,
                                              @Param("outTime") String outTime,
                                              @Param("endOutsourceTime") String endOutsourceTime,
                                              @Param("overduePeriods") String overduePeriods,
                                              @Param("deptCode") String deptCode,
                                              @Param("isManager") Integer isManager,
                                              @Param("userId") String userId,
                                              @Param("companyCode") String companyCode);


    /**
     * @Description 多条件查询司法案件信息
     */
    List<CaseInfoJudicialModel> getCaseInfoJudicialByCondition(@Param("personalName") String personalName,
                                                               @Param("mobileNo") String mobileNo,
                                                               @Param("batchNumber") String batchNumber,
                                                               @Param("principalName") String principalName,
                                                               @Param("idCard") String idCard,
                                                               @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                               @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                               @Param("overduePeriods") String overduePeriods,
                                                               @Param("overMaxDay") Integer overMaxDay,
                                                               @Param("overMinDay") Integer overMinDay,
                                                               @Param("deptCode") String deptCode,
                                                               @Param("sort") String sort,
                                                               @Param("isManager") Integer isManager,
                                                               @Param("userId") String userId,
                                                               @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询流转案件信息
     */
    List<CaseInfoModel> getTurnCaseByCondition(@Param("caseTypeList") String caseTypeList,
                                               @Param("personalName") String personalName,
                                               @Param("mobileNo") String mobileNo,
                                               @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                               @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                               @Param("batchNumber") String batchNumber,
                                               @Param("overMaxDay") Integer overMaxDay,
                                               @Param("overMinDay") Integer overMinDay,
                                               @Param("collectionStatus") Integer collectionStatus,
                                               @Param("principalName") String principalName,
                                               @Param("parentAreaId") Integer parentAreaId,
                                               @Param("areaId") Integer areaId,
                                               @Param("idCard") String idCard,
                                               @Param("circulationStatus") Integer circulationStatus,
                                               @Param("deptCode") String deptCode,
                                               @Param("sort") String sort,
                                               @Param("isManager") Integer isManager,
                                               @Param("userId") String userId,
                                               @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询案件导入重复案件信息
     */
    List<CaseInfoExceptionModel> getCaseInfoExceptionByCondition(@Param("personalName") String personalName,
                                                                 @Param("mobileNo") String mobileNo,
                                                                 @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                                 @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                                 @Param("batchNumber") String batchNumber,
                                                                 @Param("principalName") String principalName,
                                                                 @Param("overMaxDay") Integer overMaxDay,
                                                                 @Param("overMinDay") Integer overMinDay,
                                                                 @Param("deptCode") String deptCode,
                                                                 @Param("isManager") Integer isManager,
                                                                 @Param("userId") String userId,
                                                                 @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询核销案件信息
     */
    List<CaseInfoModel> getCaseInfoVerificationByCondition(@Param("personalName") String personalName,
                                                           @Param("mobileNo") String mobileNo,
                                                           @Param("batchNumber") String batchNumber,
                                                           @Param("principalName") String principalName,
                                                           @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                           @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                           @Param("delegationDate") String delegationDate,
                                                           @Param("closeDate") String closeDate,
                                                           @Param("commissionMaxRate") BigDecimal commissionMaxRate,
                                                           @Param("commissionMinRate") BigDecimal commissionMinRate,
                                                           @Param("deptCode") String deptCode,
                                                           @Param("isManager") Integer isManager,
                                                           @Param("userId") String userId,
                                                           @Param("companyCode") String companyCode);

    /**
     * @Deacription 核销案件打包查询
     */
    List<caseInfoVerificationPackagingModel> getCaseInfoVerificationPackaging(@Param("packagingTime") String packagingTime,
                                                                              @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
                                                                              @Param("overdueMinAmt") BigDecimal overdueMinAmt,
                                                                              @Param("deptCode") String deptCode,
                                                                              @Param("isManager") Integer isManager,
                                                                              @Param("userId") String userId,
                                                                              @Param("companyCode") String companyCode);

    /**
     * @Description 多条件查询修复案件
     */
    List<CaseInfoModel> getAllRepairingCase(@Param("personalName") String personalName,
                                            @Param("mobileNo")String mobileNo,
                                            @Param("caseNumber")String caseNumber,
                                            @Param("loanInvoiceNumber")String loanInvoiceNumber,
                                            @Param("idCard")String    idCard,
                                           @Param("overduePeriods") String  overduePeriods,
                                            @Param("operatorTime") String operatorTime,
                                            @Param("overMaxDay") Integer overMaxDay,
                                            @Param("overMinDay") Integer overMinDay,
                                            @Param("repairStatus") Integer repairStatus,
                                            @Param("overdueCount") Integer overdueCount,
                                            @Param("deptCode") String deptCode,
                                            @Param("isManager") Integer isManager,
                                            @Param("userId") String userId,
                                            @Param("companyCode") String companyCode);

    /**]
     * @Description 根据id查询数据字典
     */
    String getDataDict(@Param("followupBack") Integer followupBack);


    /**
     * @Description 多多条件查询案件流转记录
     */
    List<CaseTurnModel> getCaseTurnRecord(@Param("caseNumber") String caseNumber,
                                          @Param("batchNumber") String batchNumber,
                                          @Param("principalName") String principalName,
                                          @Param("personName") String personName,
                                          @Param("mobileNo") String mobileNo,
                                          @Param("idCard") String idCard,
                                          @Param("turnFromPool") Integer turnFromPool,
                                          @Param("turnToPool") Integer turnToPool,
                                          @Param("casePoolType") Integer casePoolType,
                                          @Param("triggerAction") Integer triggerAction,
                                          @Param("operatorStartTime") String operatorStartTime,
                                          @Param("operatorEndTime") String operatorEndTime,
                                          @Param("applyTime") String applyTime,
                                          @Param("turnApprovalStatus") Integer turnApprovalStatus,
                                          @Param("companyCode") String companyCode,
                                          @Param("circulationType") String circulationType);

    /**
     * @Description 多条件查询不同案件池已分配案件
     */
    List<CaseInfoModel> getCaseByPoolTypeAndCondition(
            @Param("caseNumber") String caseNumber,
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("followupBack") Integer followupBack,
            @Param("collectorName") String collectorName,
            @Param("lastCollectorName") String lastCollectorName,
            @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
            @Param("overdueMinAmt") BigDecimal overdueMinAmt,
            @Param("principalName") String principalName,
            @Param("payStatus") String payStatus,
            @Param("overMaxDay") Integer overMaxDay,
            @Param("overMinDay") Integer overMinDay,
            @Param("startOverDueDate") String startOverDueDate,
            @Param("endOverDueDate") String endOverDueDate,
            @Param("batchNumber") String batchNumber,
            @Param("principalId") String principalId,
            @Param("idCard") String idCard,
            @Param("feedBack") Integer feedBack,
            @Param("assistWay") Integer assistWay,
            @Param("caseMark") Integer caseMark,
            @Param("collectionType") String collectionType,
            @Param("sort") String sort,
            @Param("code") String code,
            @Param("collectionStatusList") String collectionStatusList,
            @Param("collectionStatus") Integer collectionStatus,
            @Param("parentAreaId") Integer parentAreaId,
            @Param("areaId") Integer areaId,
            @Param("type") Integer type,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode,
            @Param("realPayMaxAmt") BigDecimal realPayMaxAmt,
            @Param("realPayMinAmt") BigDecimal realPayMinAmt,
            @Param("seriesName") String seriesName,
            @Param("productName") String productName,
            @Param("turnFromPool") Integer turnFromPool,
            @Param("turnToPool") Integer turnToPool,
            @Param("turnApprovalStatus") Integer turnApprovalStatus,
            @Param("lawsuitResult") Integer lawsuitResult,
            @Param("antiFraudResult") Integer antiFraudResult,
            @Param("casePoolType") Integer casePoolType,
            @Param("startFollowDate") String startFollowDate,
            @Param("endFollowDate") String endFollowDate,
            @Param("caseFollowInTime") String caseFollowInTime,
            @Param("queueName") String queueName,
            @Param("sourceChannel") String sourceChannel,
            @Param("collectionMethod") String collectionMethod,
            @Param("overdueCount") Integer overdueCount,
            @Param("merchantName") String merchantName,
            @Param("personalType") Integer personalType);

    /**
     * @Description 多条件查询不同案件池待分配案件
     */
    List<CaseInfoModel> getWaitCaseByPoolType(
            @Param("caseNumber") String caseNumber,
            @Param("personalName") String personalName,
            @Param("mobileNo") String mobileNo,
            @Param("followupBack") Integer followupBack,
            @Param("collectorName") String collectorName,
            @Param("lastCollectorName") String lastCollectorName,
            @Param("overdueMaxAmt") BigDecimal overdueMaxAmt,
            @Param("overdueMinAmt") BigDecimal overdueMinAmt,
            @Param("principalName") String principalName,
            @Param("payStatus") String payStatus,
            @Param("overMaxDay") Integer overMaxDay,
            @Param("overMinDay") Integer overMinDay,
            @Param("startOverDueDate") String startOverDueDate,
            @Param("endOverDueDate") String endOverDueDate,
            @Param("batchNumber") String batchNumber,
            @Param("principalId") String principalId,
            @Param("idCard") String idCard,
            @Param("feedBack") Integer feedBack,
            @Param("assistWay") Integer assistWay,
            @Param("caseMark") Integer caseMark,
            @Param("collectionType") String collectionType,
            @Param("sort") String sort,
            @Param("code") String code,
            @Param("collectionStatusList") String collectionStatusList,
            @Param("collectionStatus") Integer collectionStatus,
            @Param("parentAreaId") Integer parentAreaId,
            @Param("areaId") Integer areaId,
            @Param("type") Integer type,
            @Param("isManager") Integer isManager,
            @Param("userId") String userId,
            @Param("companyCode") String companyCode,
            @Param("realPayMaxAmt") BigDecimal realPayMaxAmt,
            @Param("realPayMinAmt") BigDecimal realPayMinAmt,
            @Param("seriesName") String seriesName,
            @Param("productName") String productName,
            @Param("turnFromPool") Integer turnFromPool,
            @Param("turnToPool") Integer turnToPool,
            @Param("turnApprovalStatus") Integer turnApprovalStatus,
            @Param("lawsuitResult") Integer lawsuitResult,
            @Param("antiFraudResult") Integer antiFraudResult,
            @Param("casePoolType") Integer casePoolType,
            @Param("startFollowDate") String startFollowDate,
            @Param("endFollowDate") String endFollowDate,
            @Param("caseFollowInTime") String caseFollowInTime,
            @Param("queueName") String queueName,
            @Param("sourceChannel") String sourceChannel,
            @Param("collectionMethod") String collectionMethod,
            @Param("overdueCount") Integer overdueCount,
            @Param("merchantName") String merchantName,
            @Param("personalType") Integer personalType);

    cn.fintecher.pangolin.report.entity.CaseInfo findCaseInfoById(@Param("caseId")String caseId);


    /**
     * @param
     * @Description: 获取 客户信息视图
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/30 0030 下午 3:18
     */
    PersonalVModel getPersonalVModel(@Param("personId") String personId);


    /**
     * @param
     * @Description: 获取 客户地址信息 视图
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/30 0030 下午 3:18
     */
    List<PersonalAddressVModel> getPersonalAddress(@Param("personId") String personId);


    /**
     * @param
     * @Description: 获取 客户联系人信息 视图
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/30 0030 下午 3:18
     */
    List<ContactVModel> getContactInfo(@Param("personId") String personId);


    /**
     * @param
     * @Description: 获取案件基本信息
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/9/30 0030 下午 4:55
     */
    List<CaseInfo4MobileVModel> getCaseInfo4Mobile(@Param("caseNumber") String caseNumber);

    /**
     * @Description: 修改用户图像
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 3:19
     */
    void updateUserPhoto4Mobile(@Param("userId") String userId, @Param("photoUrl") String photoUrl);

    /**
     * @param
     * @Description: APP端查询案件协催记录
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/9 0009 下午 3:57
     */
    List<CaseFollowupRecordModel> getFollowupRecord4Mobile(@Param("caseId") String caseId, @Param("recordId") String recordId);

    /**
     * @Description: 根据跟进记录id与文件类型获取该跟进记录对应的附件。
     * @author zhangmingming  braveheart1115@163.com
     * @date 2018/10/10 0010 下午 4:05
     */
    List<CaseFollowupAttachment> getCaseFollowupAttachment4Mobile(@Param("caseFollowupRecordId") String caseFollowupRecordId, @Param("type") String type);


    List<CaseInfo> findCaseInfoByCaseNumber(@Param("caseNumber") String caseNumber);

    List<QueryChargeOffResponse> queryChargeOffList(QueryChargeOffParams queryChargeOffParams);
}
