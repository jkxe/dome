package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.business.model.out.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.out.BatchDataCacheService;
import cn.fintecher.pangolin.business.service.out.DataSyncUtil;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import com.alibaba.fastjson.JSONObject;
import com.hsjry.lang.common.util.DateUtil;
import com.hsjry.user.facade.pojo.enums.EnumSocialType;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service("SyncDataService")
public class SyncDataService {
    Logger logger = LoggerFactory.getLogger(SyncDataService.class);


    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    ProductSeriesRepository productSeriesRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PayPlanRepository payPlanRepository;

    @Autowired
    PrincipalRepository principalRepository;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    OrderRepaymentPlanRepository orderRepaymentPlanRepository;

    @Autowired
    AreaCodeRepository areaCodeRepository;

    @Autowired
    AreaCodeService areaCodeService;

    @Autowired
    OverdueDetailRepository overdueDetailRepository;

    @Autowired
    PersonalBankRepository personalBankRepository;

    @Autowired
    PersonalImgFileRepository personalImgFileRepository;

    @Autowired
    PersonalSocialPlatRepository personalSocialPlatRepository;

    @Autowired
    PersonalContactRepository personalContactRepository;

    @Autowired
    PersonalAstOperCrdtRepository personalAstOperCrdtRepository;

    @Autowired
    PersonalJobRepository personalJobRepository;

    @Autowired
    PersonalAddressRepository personalAddressRepository;

    @Autowired
    CaseFileRepository caseFileRepository;

    @Autowired
    BatchDataCacheService batchDataCacheService;

    /**
     * 将字符串转Date
     *
     * @param str
     * @return
     */
    public Date hyStringToDate(String str, String format) {
        if (str == null) {
            return null;
        }
        if (format == null) {
            format = "yyyy/MM/dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        if (!str.trim().equals("")) {
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return date;
    }

    private BigDecimal hyInitBigDecimal(String value) {
        if (value == null || value.trim().equals("")) {
            value = "0";
        }
        return new BigDecimal(value);
    }

    //默认为0
    private Integer hyInitInteger(String value) {
        if (value == null || value.trim().equals("")) {
            value = "0";
        }
        return Integer.valueOf(value);
    }

    //默认为null
    private Integer hyInitInteger2(String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        }
        return Integer.valueOf(value);
    }

    private void addOverdueDetailReport(HYOverdueDetailBean hyInterfaceDataCaseInfoBean, String caseNumber, SyncDataModel syncDataModel) {
        //记录每天推送的逾期明细信息log
        OverdueDetail overdueDetail = new OverdueDetail();

        if (Objects.nonNull(hyInterfaceDataCaseInfoBean.getUserId())) {
            String updateTimeStr = batchDataCacheService.personalMap_get(hyInterfaceDataCaseInfoBean.getUserId());
            Personal personal = new Personal();
            if (updateTimeStr == null) {
                //如果还没有此客户明细信息
                personal.setCompanyCode("0001");
                personal.setId(hyInterfaceDataCaseInfoBean.getUserId());
                personal.setCustomerId(hyInterfaceDataCaseInfoBean.getUserId());
                personal.setName(hyInterfaceDataCaseInfoBean.getClientName());
                personal.setIdCard(hyInterfaceDataCaseInfoBean.getCertificateNo());
                personal.setCertificatesNumber(hyInterfaceDataCaseInfoBean.getCertificateNo());
                Date updateTime = new Date(0);
                personal.setUpdateTime(updateTime);
                overdueDetail.setPersonalInfo(personal);
                //同步该客户明细数据到内存personalMap
                batchDataCacheService.personalMap_put(personal.getId(), hyDateToString(updateTime));
            } else {
                personal.setId(hyInterfaceDataCaseInfoBean.getUserId());
            }
            overdueDetail.setPersonalInfo(personal);

        }
        BeanUtils.copyProperties(hyInterfaceDataCaseInfoBean, overdueDetail);
        overdueDetail.setStatTime(new Date());
        overdueDetail.setCaseNumber(caseNumber);
        syncDataModel.getOverdueDetailInsertMap().put(overdueDetail.getLoanInvoiceId(), overdueDetail);
    }

    private String generateCaseNumber(int caseNumberTemp) {
        String dateStr = DateUtil.getDate(new Date(), "yyyyMMddHHmmss");
        String code = getAutoIncreaceCode(caseNumberTemp, 6);
        return dateStr + code;
    }

    private String getAutoIncreaceCode(int id, int len) {
        String t = String.valueOf(id);
        while (t.length() < len)
            t = "0" + t;
        return t;
    }

    private String[] buildCaseInfoArray(CaseInfoDistributed caseInfoDistributed) {
        String[] tempCaseInfoDistributed = new String[11];
        if (caseInfoDistributed.getId() == null) {
            tempCaseInfoDistributed[0] = "temp";
        } else {
            tempCaseInfoDistributed[0] = caseInfoDistributed.getId();
        }
        tempCaseInfoDistributed[1] = caseInfoDistributed.getLoanInvoiceNumber();
        tempCaseInfoDistributed[2] = caseInfoDistributed.getCustomerId();
        tempCaseInfoDistributed[3] = caseInfoDistributed.getCaseNumber();
        tempCaseInfoDistributed[4] = hyDateToString(caseInfoDistributed.getUpdateTime());
        if (caseInfoDistributed.getCollectionStatus() != null) {
            tempCaseInfoDistributed[5] = caseInfoDistributed.getCollectionStatus().intValue() + "";
        }
        if (caseInfoDistributed.getCasePoolType() != null) {
            tempCaseInfoDistributed[6] = caseInfoDistributed.getCasePoolType().intValue() + "";
        }
        if (caseInfoDistributed.getCollectionType() != null) {
            tempCaseInfoDistributed[7] = caseInfoDistributed.getCollectionType().intValue() + "";
        }
        if (caseInfoDistributed.getDepartment() != null) {
            tempCaseInfoDistributed[8] = caseInfoDistributed.getDepartment().getId();
        }
        return tempCaseInfoDistributed;
    }

    private String[] buildCaseInfoArray(CaseInfo caseInfo) {
        String[] tempCaseInfoDistributed = new String[11];
        if (caseInfo.getId() == null) {
            tempCaseInfoDistributed[0] = "temp";
        } else {
            tempCaseInfoDistributed[0] = caseInfo.getId();
        }
        tempCaseInfoDistributed[1] = caseInfo.getLoanInvoiceNumber();
        tempCaseInfoDistributed[2] = caseInfo.getCustomerId();
        tempCaseInfoDistributed[3] = caseInfo.getCaseNumber();
        tempCaseInfoDistributed[4] = hyDateToString(caseInfo.getUpdateTime());
        if (caseInfo.getCollectionStatus() != null) {
            tempCaseInfoDistributed[5] = caseInfo.getCollectionStatus().intValue() + "";
        }
        if (caseInfo.getCasePoolType() != null) {
            tempCaseInfoDistributed[6] = caseInfo.getCasePoolType().intValue() + "";
        }
        if (caseInfo.getCollectionType() != null) {
            tempCaseInfoDistributed[7] = caseInfo.getCollectionType().intValue() + "";
        }
        if (caseInfo.getDepartment() != null) {
            tempCaseInfoDistributed[8] = caseInfo.getDepartment().getId();
        }
        if (caseInfo.getLatelyCollector() != null) {
            tempCaseInfoDistributed[9] = caseInfo.getLatelyCollector().getId();
        }
        if (caseInfo.getCurrentCollector() != null) {
            tempCaseInfoDistributed[10] = caseInfo.getCurrentCollector().getId();
        }
        return tempCaseInfoDistributed;
    }

    /**
     * 读取文件逾期明细入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYOverdueDetailTaskSyns(List<HYOverdueDetailBean> perDataList, String batch, SyncDataModel syncDataModel, Date closeDate, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        List<String> resultDataList = new ArrayList<>();
        int count = 0;
        for (HYOverdueDetailBean hyInterfaceDataCaseInfoBean : perDataList) {
            count++;
            logger.debug("逾期明细第" + handleIndex + "批-第" + count + "条-" + hyInterfaceDataCaseInfoBean.getLoanInvoiceId() + "......");
            try {
                String loanInvoiceNumber = hyInterfaceDataCaseInfoBean.getLoanInvoiceId();
                String customerId = hyInterfaceDataCaseInfoBean.getUserId();

                //从内存中获取分配案件池案件信息
                Object[] caseInfoDistributed = batchDataCacheService.caseInfoDistributedMap_get(loanInvoiceNumber);
                //判断分配案件池有没有该借据号案件，如果有就更新对应分配案件池案件
                if (Objects.nonNull(caseInfoDistributed)) {
                    //记录每天推送的逾期明细信息log
                    addOverdueDetailReport(hyInterfaceDataCaseInfoBean, caseInfoDistributed[3].toString(), syncDataModel);

                    //更新分配案件池案件
                    String dbUpdateTimeStr = caseInfoDistributed[4].toString();
                    Date dbUpdateTime = hyStringToDate(dbUpdateTimeStr, "yyyy-MM-dd hh:mm:ss");
                    Date updateDate = hyStringToDate(hyInterfaceDataCaseInfoBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
                    if (dbUpdateTime != null && updateDate.compareTo(dbUpdateTime) <= 0) {
                        taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + 1);
                        continue;
                    }
                    CaseInfoDistributed caseInfoDistributed1 = mergeHYCaseInfoDistributed(hyInterfaceDataCaseInfoBean, caseInfoDistributed, syncDataModel);
                    syncDataModel.getCaseInfoDistributedUdpateMap().put(caseInfoDistributed1.getLoanInvoiceNumber(), caseInfoDistributed1);
                    //同步分配池该案件同步至内存
                    batchDataCacheService.caseInfoDistributedMap_put(caseInfoDistributed1.getLoanInvoiceNumber(), buildCaseInfoArray(caseInfoDistributed1));
                } else {
                    //从内存中获取案件信息
                    Object[] caseInfo = batchDataCacheService.caseInfoMap_get(loanInvoiceNumber);
                    //判断催收中案件有没有该借据号案件，如果有就更新对应催收中案件
                    if (Objects.nonNull(caseInfo)) {
                        //记录每天推送的逾期明细信息log
                        addOverdueDetailReport(hyInterfaceDataCaseInfoBean, caseInfo[3].toString(), syncDataModel);

                        //更新案件信息
                        Date updateDate = hyStringToDate(hyInterfaceDataCaseInfoBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
                        String dbUpdateTimeStr = caseInfo[4].toString();
                        Date dbUpdateTime = hyStringToDate(dbUpdateTimeStr, "yyyy-MM-dd HH:mm:ss");
                        if (dbUpdateTime != null && updateDate.compareTo(dbUpdateTime) <= 0) {
                            taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + 1);
                            continue;
                        }
                        CaseInfo caseInfo1 = mergeHYCaseInfo(hyInterfaceDataCaseInfoBean, caseInfo, syncDataModel);
                        syncDataModel.getCaseInfoUpdateMap().put(caseInfo1.getLoanInvoiceNumber(), caseInfo1);
                        //同步该案件至内存
                        batchDataCacheService.caseInfoMap_put(caseInfo1.getLoanInvoiceNumber(), buildCaseInfoArray(caseInfo1));
                    } else {

                        //从内存中获取共债案件
                        Object[] caseInfo1 = batchDataCacheService.caseInfoMapByUser_get(customerId);
                        String caseNumber;
                        //判断催收中案件是否有该客户共债案件，d如果有就用催收中对应案件编号
                        if (Objects.nonNull(caseInfo1)) {
                            CaseInfo caseInfo2 = mergeHYCaseInfo(hyInterfaceDataCaseInfoBean, null, syncDataModel);

                            //案件表新增案件
                            //如果结清案件里有该借据号案件 逾期次数加1
                            //从内存中获取结清案件
                            List<Object[]> overdueCaseInfos = batchDataCacheService.overCaseInfoMap_get(loanInvoiceNumber);
                            int overdueCount = 0;
                            if (overdueCaseInfos != null) {
                                overdueCount = overdueCaseInfos.size();
                            }
                            overdueCount++;
                            caseInfo2.setOverdueCount(overdueCount);

                            caseInfo2.setBatchNumber(batch);
                            caseInfo2.setCaseNumber(caseInfo1[3].toString());
                            if (caseInfo1.length > 5 && caseInfo1[5] != null) {
                                caseInfo2.setCollectionStatus(Integer.valueOf(caseInfo1[5].toString()));
                            }
                            if (caseInfo1.length > 6 && caseInfo1[6] != null) {
                                caseInfo2.setCasePoolType(Integer.valueOf(caseInfo1[6].toString()));
                            }
                            if (caseInfo1.length > 7 && caseInfo1[7] != null) {
                                caseInfo2.setCollectionType(Integer.valueOf(caseInfo1[7].toString()));
                            }
                            if (caseInfo1.length > 8 && caseInfo1[8] != null) {
                                Department department = new Department();
                                department.setId(caseInfo1[8].toString());
                                caseInfo2.setDepartment(department);
                            }
                            if (caseInfo1.length > 9 && caseInfo1[9] != null) {
                                User user = new User();
                                user.setId(caseInfo1[9].toString());
                                caseInfo2.setLatelyCollector(user);
                            }
                            if (caseInfo1.length > 10 && caseInfo1[10] != null) {
                                User user = new User();
                                user.setId(caseInfo1[10].toString());
                                caseInfo2.setCurrentCollector(user);
                            }

                            syncDataModel.getCaseInfoInsertMap().put(caseInfo2.getLoanInvoiceNumber(), caseInfo2);
                            //同步数据到内存
                            batchDataCacheService.caseInfoMapByUser_put(caseInfo2.getCustomerId(), buildCaseInfoArray(caseInfo2));
                            //记录每天推送的逾期明细信息log
                            addOverdueDetailReport(hyInterfaceDataCaseInfoBean, caseInfo2.getCaseNumber(), syncDataModel);
                        } else {
                            //判断分配案件池中有没有该客户共债案件，如果有就用分配案件池对应案件编号
                            //从内存中获取共债案件
                            Object[] caseInfoDistributed1 = batchDataCacheService.caseInfoDistributedMapByUser_get(customerId);
                            if (Objects.nonNull(caseInfoDistributed1)) {
                                caseNumber = caseInfoDistributed1[3].toString();
                            } else {
                                //以上条件都不满足 重新生成案件号
                                SyncDataModel.caseNumberTemp++;
                                caseNumber = generateCaseNumber(SyncDataModel.caseNumberTemp);
                            }
                            hyInterfaceDataCaseInfoBean.setCaseNumber(caseNumber);
                            //分配案件池新增案件
                            //如果结清案件里有该借据号案件 逾期次数加1
                            //从内存中获取结清案件
                            List<Object[]> overdueCaseInfos = batchDataCacheService.overCaseInfoMap_get(loanInvoiceNumber);
                            int overdueCount = 0;
                            if (overdueCaseInfos != null) {
                                overdueCount = overdueCaseInfos.size();
                            }
                            overdueCount++;
                            hyInterfaceDataCaseInfoBean.setOverdueCount(overdueCount);

                            CaseInfoDistributed caseInfoDistributed2 = mergeHYCaseInfoDistributed(hyInterfaceDataCaseInfoBean, null, syncDataModel);
                            caseInfoDistributed2.setBatchNumber(batch);
                            syncDataModel.getCaseInfoDistributedInsertMap().put(caseInfoDistributed2.getLoanInvoiceNumber(), caseInfoDistributed2);
                            //同步案件数据到内催
                            batchDataCacheService.caseInfoDistributedMapByUser_put(caseInfoDistributed2.getCustomerId(), buildCaseInfoArray(caseInfoDistributed2));

                            //记录每天推送的逾期明细信息log
                            addOverdueDetailReport(hyInterfaceDataCaseInfoBean, caseNumber, syncDataModel);
                        }
                    }


                }


            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                        hyInterfaceDataCaseInfoBean.getLoanInvoiceId(),
                        JSONObject.toJSONString(hyInterfaceDataCaseInfoBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("逾期明细第" + handleIndex + "批-第" + count + "条-" + hyInterfaceDataCaseInfoBean.getLoanInvoiceId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "逾期明细数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户明细入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerDetailTaskSyns(List<HYOverdueCustomerDetailBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();
        int count = 0;
        for (HYOverdueCustomerDetailBean customerDetailBean : perDataList) {
            count++;
            try {
                logger.debug("客户明细第" + handleIndex + "批-第" + count + "条-" + customerDetailBean.getUserId() + "......");
                mergeHYCustomerDetail(customerDetailBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_DETAIL.getValue(),
                        customerDetailBean.getUserId(),
                        JSONObject.toJSONString(customerDetailBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户明细第" + handleIndex + "批-第" + count + "条-" + customerDetailBean.getUserId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "客户明细信息数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户开户信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerAccountTaskSyns(List<HYOverdueCustomerAccountBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerAccountBean customerAccountBean : perDataList) {
            try {
                count++;
                logger.debug("客户开户信息第" + handleIndex + "批-第" + count + "条：" + customerAccountBean.getResourceId() + "......");
                mergeHYCustomerAccount(customerAccountBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_ACCOUNT.getValue(),
                        customerAccountBean.getResourceId(),
                        JSONObject.toJSONString(customerAccountBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);
                logger.error("客户开户信息第" + handleIndex + "批-第" + count + "条：" + customerAccountBean.getResourceId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户开户信息数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户文本文件信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerFileAttachTaskSyns(List<HYOverdueCustomerFileAttachBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerFileAttachBean customerFileAttachBean : perDataList) {
            try {
                count++;
                logger.debug("客户文本文件第" + handleIndex + "批-第" + count + "条-" + customerFileAttachBean.getContractId() + "......");
                mergeHYCustomerFileAttach(customerFileAttachBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_FILE_ATTACH.getValue(),
                        customerFileAttachBean.getContractId(),
                        JSONObject.toJSONString(customerFileAttachBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户文本文件第" + handleIndex + "批-第" + count + "条-" + customerFileAttachBean.getContractId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户文本文件信息数据失败" : e.getMessage());
            }
        }
        return resultDataList;
    }

    /**
     * 读取文件客户影像文件信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerImgAttachTaskSyns(List<HYOverdueCustomerImgAttachBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerImgAttachBean customerImgAttachBean : perDataList) {
            try {
                count++;
                logger.debug("客户影像文件第" + handleIndex + "批-第" + count + "条-" + customerImgAttachBean.getResourceId() + "......");
                mergeHYCustomerImgAttach(customerImgAttachBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_IMG_ATTACH.getValue(),
                        customerImgAttachBean.getResourceId(),
                        JSONObject.toJSONString(customerImgAttachBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户影像文件第" + handleIndex + "批-第" + count + "条-" + customerImgAttachBean.getResourceId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户影像文件信息数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户联系人信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerRelationTaskSyns(List<HYOverdueCustomerRelationBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerRelationBean customerRelationBean : perDataList) {
            try {
                count++;
                logger.debug("客户联系人信息第" + handleIndex + "批-第" + count + "条-" + customerRelationBean.getUserId() + "......");
                mergeHYCustomerRelation(customerRelationBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_RELATION.getValue(),
                        customerRelationBean.getUserId() + "_" + customerRelationBean.getRelationUserId(),
                        JSONObject.toJSONString(customerRelationBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户联系人信息第" + handleIndex + "批-第" + count + "条-" + customerRelationBean.getUserId() + "_" + customerRelationBean.getRelationUserId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户联系人信息数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户社交平台信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerSocialPlatTaskSyns(List<HYOverdueCustomerSocialPlatBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerSocialPlatBean customerSocialPlatBean : perDataList) {
            try {
                count++;
                logger.debug("客户社交平台信息第" + handleIndex + "批-第" + count + "条-" + customerSocialPlatBean.getStationId() + "......");
                mergeHYCustomerSocialPlat(customerSocialPlatBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_SOCIAL_PLAT.getValue(),
                        customerSocialPlatBean.getStationId(),
                        JSONObject.toJSONString(customerSocialPlatBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户社交平台信息第" + handleIndex + "批-第" + count + "条-" + customerSocialPlatBean.getStationId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户社交平台信息数据失败" : e.getMessage());
            }
        }

        return resultDataList;
    }

    /**
     * 读取文件客户资产，征信，经营信息入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    public List<String> doHYCustomerAstOperCrdtSyns(List<HYOverdueCustomerAstOperCrdtBean> perDataList, SyncDataModel syncDataModel, int handleIndex) {
        List<String> resultDataList = new ArrayList<>();

        int count = 0;
        for (HYOverdueCustomerAstOperCrdtBean customerAstOperCrdtBean : perDataList) {
            try {
                count++;
                logger.debug("客户资产，征信，经营信息第" + handleIndex + "批-第" + count + "条-" + customerAstOperCrdtBean.getResourceId() + "......");
                mergeHYCustomerAstOperCrdt(customerAstOperCrdtBean, syncDataModel);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);

                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        ExceptionData.DataType.CUSTOMER_AST_OPER_CRDT.getValue(),
                        customerAstOperCrdtBean.getResourceId(),
                        JSONObject.toJSONString(customerAstOperCrdtBean),
                        ExceptionData.EXCEPTION_CODE_PARSE, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);

                logger.error("客户资产，征信，经营信息第" + handleIndex + "批-第" + count + "条-" + customerAstOperCrdtBean.getResourceId() + "解析数据失败");
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收客户资产，征信，经营信息数据失败" : e.getMessage());
            }

        }
        return resultDataList;
    }

    private String hyDateToString(Date date) {
        return DateUtil.getDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 更新案件信息
     *
     * @param ctmInterfaceDataBean
     * @param caseInfoObj
     * @return
     */
    public CaseInfoDistributed mergeHYCaseInfoDistributed(HYOverdueDetailBean ctmInterfaceDataBean, Object[] caseInfoObj, SyncDataModel syncDataModel) throws Exception {
        CaseInfoDistributed caseInfo = new CaseInfoDistributed();

        if (caseInfoObj == null) {
            caseInfo.setCompanyCode("0001");
            caseInfo.setCaseNumber(ctmInterfaceDataBean.getCaseNumber());
            caseInfo.setCustomerId(ctmInterfaceDataBean.getUserId());
            caseInfo.setCaseFollowInTime(new Date());
            if (Objects.nonNull(ctmInterfaceDataBean.getUserId())) {
                String updateTimeStr = batchDataCacheService.personalMap_get(ctmInterfaceDataBean.getUserId());
                Personal personal = new Personal();
                if (updateTimeStr == null) {
                    //如果还没有此客户明细信息
                    personal.setCompanyCode("0001");
                    personal.setId(ctmInterfaceDataBean.getUserId());
                    personal.setCustomerId(ctmInterfaceDataBean.getUserId());
                    personal.setName(ctmInterfaceDataBean.getClientName());
                    personal.setIdCard(ctmInterfaceDataBean.getCertificateNo());
                    personal.setCertificatesNumber(ctmInterfaceDataBean.getCertificateNo());
                    Date updateTime = new Date(0);
                    personal.setUpdateTime(updateTime);
                    syncDataModel.getPersonalInsertMap().put(personal.getId(), personal);
                    //同步该客户明细数据到内存personalMap
                    batchDataCacheService.personalMap_put(personal.getId(), hyDateToString(updateTime));
                } else {
                    personal.setId(ctmInterfaceDataBean.getUserId());
                }
                caseInfo.setPersonalInfo(personal);
            }
            if (Objects.nonNull(ctmInterfaceDataBean.getProductName())) {
                caseInfo.setProductName(ctmInterfaceDataBean.getProductName());//产品名称
                //从内存中获取产品
                Product product = batchDataCacheService.productMap_get(ctmInterfaceDataBean.getProductName());
                if (product != null) {
                    caseInfo.setProduct(product);
                    caseInfo.setProductType(product.getProductSeries().getId());
                    if (product.getProductSeries().getChannelType().equals("0")) {
                        caseInfo.setSourceChannel("线上");
                    } else if (product.getProductSeries().getChannelType().equals("1")) {
                        caseInfo.setSourceChannel("线下");
                    } else {
                        caseInfo.setSourceChannel("未知");
                    }
                    if (product.getProductSeries().getSeriesFlag() == 0) {
                        caseInfo.setCollectionMethod("自营");
                    } else if (product.getProductSeries().getSeriesFlag() == 1) {
                        caseInfo.setCollectionMethod("外包");
                    } else {
                        caseInfo.setCollectionMethod("未知");
                    }

                } else {
                    throw new Exception("未找到该产品: " + ctmInterfaceDataBean.getProductName());
                }
            }
            caseInfo.setLeaveCaseFlag(0);//留案标志
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件颜色标记
            caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()); //未回收
        } else {
            caseInfo.setId(caseInfoObj[0].toString());
        }

        //根据逾期天数计算逾期阶段 M1(1-30),M2(31-60)
        caseInfo.setOverdueDays(hyInitInteger(ctmInterfaceDataBean.getOverdueDays()));//逾期天数
        caseInfo.setOverduePeriods((caseInfo.getOverdueDays() - 1) / 30 + 1);
        String duePeriods = "M".concat(caseInfo.getOverduePeriods() + "");
        caseInfo.setPayStatus(duePeriods);
        caseInfo.setIntoApplyId(ctmInterfaceDataBean.getIntoApplyId());//进件申请编号
        caseInfo.setAccountBalance(hyInitBigDecimal(ctmInterfaceDataBean.getLoanAmount()));//账户余额
        caseInfo.setIntoTime(stringToDate(ctmInterfaceDataBean.getIntoTime(), "yyyy/MM/dd HH:mm:ss"));//进件时间
        caseInfo.setLoanPayTime(stringToDate(ctmInterfaceDataBean.getLoanPayTime(), "yyyy/MM/dd HH:mm:ss"));//放款时间

        if (ctmInterfaceDataBean.getFiveLevel().equals("")) {
            caseInfo.setFiveLevel("未分类");////银行信贷划分等级
        } else {
            if (ctmInterfaceDataBean.getFiveLevel().equals("1")) {
                caseInfo.setFiveLevel("正常");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("2")) {
                caseInfo.setFiveLevel("关注");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("3")) {
                caseInfo.setFiveLevel("次级");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("4")) {
                caseInfo.setFiveLevel("可疑");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("5")) {
                caseInfo.setFiveLevel("损失");////银行信贷划分等级
            } else {
                throw new Exception("未找到该银行信贷划分等级: " + ctmInterfaceDataBean.getFiveLevel());
            }
        }

        caseInfo.setApplyPeriod(hyInitInteger(ctmInterfaceDataBean.getApplyPeriod()));//申请期数
        caseInfo.setCreditPeriod(hyInitInteger(ctmInterfaceDataBean.getCreditPeriod()));//授权期数
        caseInfo.setLoanPeriod(hyInitInteger(ctmInterfaceDataBean.getLoanPeriod()));//放款期数
        caseInfo.setApplyAmount(hyInitBigDecimal(ctmInterfaceDataBean.getApplyAmount()));//申请金额
        caseInfo.setCreditAmount(hyInitBigDecimal(ctmInterfaceDataBean.getCreditAmt()));//授信金额
        caseInfo.setLoanAmount(hyInitBigDecimal(ctmInterfaceDataBean.getLoanAmt()));//放款金额
        caseInfo.setOverdueAmount(hyInitBigDecimal(ctmInterfaceDataBean.getClearOverdueAmount()));//逾期金额
        caseInfo.setOverdueCapitalInterest(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentMonthDebtAmount()));//逾期本息
        caseInfo.setOverdueCapital(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentPreRepayPrincipal()));//逾期本金
        caseInfo.setOverdueInterestBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayInterest()));//当期以前未偿还利息
        caseInfo.setOverdueInterestCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayInterest()));//当期剩余应缴利息
        caseInfo.setOverdueInterest(caseInfo.getOverdueInterestBefore().add(caseInfo.getOverdueInterestCurrent()));//逾期利息
        caseInfo.setOverdueDelayFineBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayManagementFee()));//当期以前未偿还滞纳金
        caseInfo.setOverdueDelayFineCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayManagementFee()));//当期剩余应缴纳滞纳金
        caseInfo.setOverdueDelayFine(caseInfo.getOverdueDelayFineBefore().add(caseInfo.getOverdueDelayFineCurrent()));//逾期滞纳金
        caseInfo.setCurrentDebtAmount(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentDebtAmount()));//当期欠款总额
        caseInfo.setLoanPurpose(ctmInterfaceDataBean.getLoanPurposeName());//贷款用途
        caseInfo.setLeftPeriods(hyInitInteger(ctmInterfaceDataBean.getLeftNum()));//剩余期数
        caseInfo.setContractNumber(ctmInterfaceDataBean.getContractId());//合同编号
        caseInfo.setLoanInvoiceNumber(ctmInterfaceDataBean.getLoanInvoiceId());//借据号
        caseInfo.setPreRepayPrincipal(hyInitBigDecimal(ctmInterfaceDataBean.getPreRepayPrincipal()));//当期应扣本金

        if (ctmInterfaceDataBean.getFlag().equals("")) {
            caseInfo.setAdvancesFlag("非垫付");////垫付标志
        } else {
            if (ctmInterfaceDataBean.getFlag().equals("1")) {
                caseInfo.setAdvancesFlag("垫付");////垫付标志
            } else {
                caseInfo.setAdvancesFlag("非垫付");////垫付标志
            }
        }
        caseInfo.setOverdueManageFeeCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayFee()));//当期未偿还手续费
        caseInfo.setOverdueManageFeeBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayFee()));//当期以前未偿还手续费
        caseInfo.setOverdueManageFee(caseInfo.getOverdueManageFeeBefore().add(caseInfo.getOverdueManageFeeCurrent()));//逾期手续费
        caseInfo.setRepayDate(stringToDate(ctmInterfaceDataBean.getRepayDate(), "yyyy-MM-dd"));//应还日期
        if (ctmInterfaceDataBean.getMovingBackFlag().equals("")) {
            caseInfo.setMovingBackFlag("非回迁");//回迁标志
        } else {
            if (ctmInterfaceDataBean.getMovingBackFlag().equals("1")) {
                caseInfo.setMovingBackFlag("回迁");//回迁标志
            } else {
                caseInfo.setMovingBackFlag("非回迁");//回迁标志
            }
        }

        if (ctmInterfaceDataBean.getVerificationStatus().equals("")) {
            caseInfo.setMovingBackFlag("非核销");//核销标志
        } else {
            if (ctmInterfaceDataBean.getVerificationStatus().equals("1")) {
                caseInfo.setVerficationStatus("核销");//核销标志
            } else {
                caseInfo.setVerficationStatus("非核销");//核销标志
            }
        }

        caseInfo.setCreateTime(stringToDate(ctmInterfaceDataBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));//创建时间
        caseInfo.setUpdateTime(stringToDate(ctmInterfaceDataBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss"));//更新时间
        caseInfo.setPnltFine(hyInitBigDecimal(ctmInterfaceDataBean.getLeftOverdueFee()));//当期剩余应还罚息
        caseInfo.setUnpaidFine(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftOverdueFee()));//未偿还罚息
        caseInfo.setOverdueFine(caseInfo.getPnltFine().add(caseInfo.getUnpaidFine()));//逾期罚息
        caseInfo.setBusDate(stringToDate(ctmInterfaceDataBean.getBusDate(), "yyyy/MM/dd HH:mm:ss"));
        caseInfo.setOverdueCount(ctmInterfaceDataBean.getOverdueCount());
        caseInfo.setMerchantName(ctmInterfaceDataBean.getMerchantName());
        caseInfo.setStoreName(ctmInterfaceDataBean.getStoreName());
        caseInfo.setRepayAccountNo(ctmInterfaceDataBean.getUserAccount());
        caseInfo.setRepayBank(ctmInterfaceDataBean.getBranchName());
        caseInfo.setOverdueCount(ctmInterfaceDataBean.getOverdueCount());
        return caseInfo;
    }

    /**
     * 更新分配案件池案件信息
     *
     * @param ctmInterfaceDataBean
     * @param caseInfoObj
     * @return
     */
    public CaseInfo mergeHYCaseInfo(HYOverdueDetailBean ctmInterfaceDataBean, Object[] caseInfoObj, SyncDataModel syncDataModel) throws Exception {
        CaseInfo caseInfo = new CaseInfo();
        if (caseInfoObj == null) {
            caseInfo.setCaseFollowInTime(new Date());
            caseInfo.setCompanyCode("0001");
            caseInfo.setCaseNumber(ctmInterfaceDataBean.getCaseNumber());
            caseInfo.setCustomerId(ctmInterfaceDataBean.getUserId());
            caseInfo.setExceptionFlag(0);
            caseInfo.setAssistFlag(0);

            caseInfo.setLeaveCaseFlag(0);//留案标志
            caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
            caseInfo.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());
            caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件颜色标记
            caseInfo.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()); //未回收
            if (Objects.nonNull(ctmInterfaceDataBean.getUserId())) {
                String updateTimeStr = batchDataCacheService.personalMap_get(ctmInterfaceDataBean.getUserId());
                if (updateTimeStr != null) {
                    //如果还没有此客户明细信息
                    Personal personal = new Personal();
                    personal.setCompanyCode("0001");
                    personal.setId(ctmInterfaceDataBean.getUserId());
                    personal.setCustomerId(ctmInterfaceDataBean.getUserId());
                    personal.setName(ctmInterfaceDataBean.getClientName());
                    personal.setIdCard(ctmInterfaceDataBean.getCertificateNo());
                    personal.setCertificatesNumber(ctmInterfaceDataBean.getCertificateNo());
                    Date updateTime = new Date(0);
                    personal.setUpdateTime(updateTime);
                    caseInfo.setPersonalInfo(personal);
                    syncDataModel.getPersonalInsertMap().put(personal.getId(), personal);
                    //同步该客户明细数据到内存personalMap
                    batchDataCacheService.personalMap_put(personal.getId(), hyDateToString(updateTime));
                }
            }
            if (Objects.nonNull(ctmInterfaceDataBean.getProductName())) {
                caseInfo.setProductName(ctmInterfaceDataBean.getProductName());//产品名称
                //从内存中获取产品
                Product product = batchDataCacheService.productMap_get(ctmInterfaceDataBean.getProductName());
                if (product != null) {
                    caseInfo.setProduct(product);
                    caseInfo.setProductType(product.getProductSeries().getId());
                    if (product.getProductSeries().getChannelType().equals("0")) {
                        caseInfo.setSourceChannel("线上");
                    } else if (product.getProductSeries().getChannelType().equals("1")) {
                        caseInfo.setSourceChannel("线下");
                    } else {
                        caseInfo.setSourceChannel("未知");
                    }

                    if (product.getProductSeries().getSeriesFlag() == 0) {
                        caseInfo.setCollectionMethod("自营");
                    } else if (product.getProductSeries().getSeriesFlag() == 1) {
                        caseInfo.setCollectionMethod("外包");
                    } else {
                        caseInfo.setCollectionMethod("未知");
                    }
                } else {
                    throw new Exception("未找到该产品: " + ctmInterfaceDataBean.getProductName());
                }
            }
        } else {
            caseInfo.setId(caseInfoObj[0].toString());
        }

        //根据逾期天数计算逾期阶段 M1(1-30),M2(31-60)
        caseInfo.setOverdueDays(hyInitInteger(ctmInterfaceDataBean.getOverdueDays()));//逾期天数
        caseInfo.setOverduePeriods((caseInfo.getOverdueDays() - 1) / 30 + 1);
        String duePeriods = "M".concat(caseInfo.getOverduePeriods() + "");
        caseInfo.setPayStatus(duePeriods);
        caseInfo.setIntoApplyId(ctmInterfaceDataBean.getIntoApplyId());//进件申请编号
        caseInfo.setAccountBalance(hyInitBigDecimal(ctmInterfaceDataBean.getLoanAmount()));//账户余额
        caseInfo.setIntoTime(stringToDate(ctmInterfaceDataBean.getIntoTime(), "yyyy/MM/dd HH:mm:ss"));//进件时间
        caseInfo.setLoanPayTime(stringToDate(ctmInterfaceDataBean.getLoanPayTime(), "yyyy/MM/dd HH:mm:ss"));//放款时间

        if (ctmInterfaceDataBean.getFiveLevel().equals("")) {
            caseInfo.setFiveLevel("未分类");////银行信贷划分等级
        } else {
            if (ctmInterfaceDataBean.getFiveLevel().equals("1")) {
                caseInfo.setFiveLevel("正常");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("2")) {
                caseInfo.setFiveLevel("关注");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("3")) {
                caseInfo.setFiveLevel("次级");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("4")) {
                caseInfo.setFiveLevel("可疑");////银行信贷划分等级
            } else if (ctmInterfaceDataBean.getFiveLevel().equals("5")) {
                caseInfo.setFiveLevel("损失");////银行信贷划分等级
            } else {
                throw new Exception("未找到该银行信贷划分等级: " + ctmInterfaceDataBean.getFiveLevel());
            }
        }

        caseInfo.setApplyPeriod(hyInitInteger(ctmInterfaceDataBean.getApplyPeriod()));//申请期数
        caseInfo.setCreditPeriod(hyInitInteger(ctmInterfaceDataBean.getCreditPeriod()));//授权期数
        caseInfo.setLoanPeriod(hyInitInteger(ctmInterfaceDataBean.getLoanPeriod()));//放款期数
        caseInfo.setApplyAmount(hyInitBigDecimal(ctmInterfaceDataBean.getApplyAmount()));//申请金额
        caseInfo.setCreditAmount(hyInitBigDecimal(ctmInterfaceDataBean.getCreditAmt()));//授信金额
        caseInfo.setLoanAmount(hyInitBigDecimal(ctmInterfaceDataBean.getLoanAmt()));//放款金额
        caseInfo.setOverdueAmount(hyInitBigDecimal(ctmInterfaceDataBean.getClearOverdueAmount()));//逾期金额
        caseInfo.setOverdueCapitalInterest(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentMonthDebtAmount()));//逾期本息
        caseInfo.setOverdueCapital(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentPreRepayPrincipal()));//逾期本金
        caseInfo.setOverdueInterestBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayInterest()));//当期以前未偿还利息
        caseInfo.setOverdueInterestCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayInterest()));//当期剩余应缴利息
        caseInfo.setOverdueInterest(caseInfo.getOverdueInterestBefore().add(caseInfo.getOverdueInterestCurrent()));//逾期利息
        caseInfo.setOverdueDelayFineBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayManagementFee()));//当期以前未偿还滞纳金
        caseInfo.setOverdueDelayFineCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayManagementFee()));//当期剩余应缴纳滞纳金
        caseInfo.setOverdueDelayFine(caseInfo.getOverdueDelayFineBefore().add(caseInfo.getOverdueDelayFineCurrent()));//逾期滞纳金
        caseInfo.setCurrentDebtAmount(hyInitBigDecimal(ctmInterfaceDataBean.getCurrentDebtAmount()));//当期欠款总额
        caseInfo.setLoanPurpose(ctmInterfaceDataBean.getLoanPurposeName());//贷款用途
        caseInfo.setLeftPeriods(hyInitInteger(ctmInterfaceDataBean.getLeftNum()));//剩余期数
        caseInfo.setContractNumber(ctmInterfaceDataBean.getContractId());//合同编号
        caseInfo.setLoanInvoiceNumber(ctmInterfaceDataBean.getLoanInvoiceId());//借据号
        caseInfo.setPreRepayPrincipal(hyInitBigDecimal(ctmInterfaceDataBean.getPreRepayPrincipal()));//当期应扣本金

        if (ctmInterfaceDataBean.getFlag().equals("")) {
            caseInfo.setAdvancesFlag("非垫付");////垫付标志
        } else {
            if (ctmInterfaceDataBean.getFlag().equals("1")) {
                caseInfo.setAdvancesFlag("垫付");////垫付标志
            } else {
                caseInfo.setAdvancesFlag("非垫付");////垫付标志
            }
        }
        caseInfo.setOverdueManageFeeCurrent(hyInitBigDecimal(ctmInterfaceDataBean.getLeftRepayFee()));//当期未偿还手续费
        caseInfo.setOverdueManageFeeBefore(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftRepayFee()));//当期以前未偿还手续费
        caseInfo.setOverdueManageFee(caseInfo.getOverdueManageFeeBefore().add(caseInfo.getOverdueManageFeeCurrent()));//逾期手续费
        caseInfo.setRepayDate(stringToDate(ctmInterfaceDataBean.getRepayDate(), "yyyy-MM-dd"));//应还日期
        if (ctmInterfaceDataBean.getMovingBackFlag().equals("")) {
            caseInfo.setMovingBackFlag("非回迁");//回迁标志
        } else {
            if (ctmInterfaceDataBean.getMovingBackFlag().equals("1")) {
                caseInfo.setMovingBackFlag("回迁");//回迁标志
            } else {
                caseInfo.setMovingBackFlag("非回迁");//回迁标志
            }
        }

        if (ctmInterfaceDataBean.getVerificationStatus().equals("")) {
            caseInfo.setMovingBackFlag("非核销");//核销标志
        } else {
            if (ctmInterfaceDataBean.getVerificationStatus().equals("1")) {
                caseInfo.setVerficationStatus("核销");//核销标志
            } else {
                caseInfo.setVerficationStatus("非核销");//核销标志
            }
        }

        caseInfo.setCreateTime(stringToDate(ctmInterfaceDataBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));//创建时间
        caseInfo.setUpdateTime(stringToDate(ctmInterfaceDataBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss"));//更新时间
        caseInfo.setPnltFine(hyInitBigDecimal(ctmInterfaceDataBean.getLeftOverdueFee()));//当期剩余应还罚息
        caseInfo.setUnpaidFine(hyInitBigDecimal(ctmInterfaceDataBean.getBeforeCurrentLeftOverdueFee()));//未偿还罚息
        caseInfo.setOverdueFine(caseInfo.getPnltFine().add(caseInfo.getUnpaidFine()));//逾期罚息
        caseInfo.setBusDate(stringToDate(ctmInterfaceDataBean.getBusDate(), "yyyy/MM/dd HH:mm:ss"));
        caseInfo.setOverdueCount(ctmInterfaceDataBean.getOverdueCount());
        caseInfo.setMerchantName(ctmInterfaceDataBean.getMerchantName());
        caseInfo.setStoreName(ctmInterfaceDataBean.getStoreName());
        caseInfo.setRepayAccountNo(ctmInterfaceDataBean.getUserAccount());
        caseInfo.setRepayBank(ctmInterfaceDataBean.getBranchName());
        caseInfo.setOverdueCount(ctmInterfaceDataBean.getOverdueCount());
        return caseInfo;
    }

    /**
     * 解析客户明细数据加入到SyncDataModel
     *
     * @param overdueCustomerDetailBean
     * @return
     */
    public void mergeHYCustomerDetail(HYOverdueCustomerDetailBean overdueCustomerDetailBean, SyncDataModel syncDataModel) throws Exception {
        //从内存中获取客户明细信息
        String dbUpdateTimeStr = batchDataCacheService.personalMap_get(overdueCustomerDetailBean.getUserId());
        Date updateTime = hyStringToDate(overdueCustomerDetailBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(dbUpdateTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        Personal personal = new Personal();
        personal.setId(overdueCustomerDetailBean.getUserId());
        personal.setCustomerId(overdueCustomerDetailBean.getUserId());
        personal.setCompanyCode("0001");
        personal.setName(overdueCustomerDetailBean.getClientName());
        if (overdueCustomerDetailBean.getCertificateKind().equals("")) {
            personal.setCertificatesType("0");
        } else {
            String certificatesType = SyncDataModel.certificateTypeMap.get(overdueCustomerDetailBean.getCertificateKind());
            if (certificatesType == null) {
                throw new Exception("未找到该证件类型: " + overdueCustomerDetailBean.getCertificateKind());
            } else {
                personal.setCertificatesType(overdueCustomerDetailBean.getCertificateKind());
            }
        }

        personal.setCertificatesNumber(overdueCustomerDetailBean.getCertificateNo());
        personal.setIdCardExpirydate(hyStringToDate(overdueCustomerDetailBean.getInvalidDate(), null));
        personal.setMobileNo(overdueCustomerDetailBean.getTelephone());
        personal.setReferrer(overdueCustomerDetailBean.getBeRecommenderName());
        personal.setPermanentAddress(overdueCustomerDetailBean.getPermanentAddress());
        personal.setIdCardAddress(overdueCustomerDetailBean.getCompanyAddress());
        personal.setLocalHomeAddress(overdueCustomerDetailBean.getLivingAddress());
        personal.setAge(hyInitInteger2(overdueCustomerDetailBean.getAge()));
        if (overdueCustomerDetailBean.getSex().equals("")) {
            //未知
            personal.setSex(144);
        } else {
            int sexCode = hyInitInteger(overdueCustomerDetailBean.getSex());
            if (sexCode == 1) {
                //男
                personal.setSex(142);
            } else if (sexCode == 2) {
                //女
                personal.setSex(143);
            } else {
                //未知
                personal.setSex(144);
            }
        }
        if (overdueCustomerDetailBean.getEducation().equals("")) {
            //未知
            personal.setEducation(99);
        } else {
            String education = SyncDataModel.educationMap.get(overdueCustomerDetailBean.getEducation());
            if (education == null) {
                throw new Exception("未找到该教育程度: " + overdueCustomerDetailBean.getEducation());
            } else {
                int educationCode = hyInitInteger(overdueCustomerDetailBean.getEducation());
                personal.setEducation(educationCode);
            }

        }

        if (overdueCustomerDetailBean.getMarriage().equals("")) {
            //未知
            personal.setMarital(90);
        } else {
            String marrage = SyncDataModel.marrageMap.get(overdueCustomerDetailBean.getMarriage());
            if (marrage == null) {
                throw new Exception("未找到该婚姻状况: " + overdueCustomerDetailBean.getMarriage());
            } else {
                int marriageCode = hyInitInteger(overdueCustomerDetailBean.getMarriage());
                personal.setMarital(marriageCode);
            }
        }
        personal.setCreditLevel(overdueCustomerDetailBean.getCreditLevel());
        personal.setCreateTime(hyStringToDate(overdueCustomerDetailBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        personal.setUpdateTime(updateTime);

        //更新|新增客户列表
        syncDataModel.getPersonalInsertMap().put(personal.getId(), personal);
        //同步该客户明细数据到内存personalMap
        batchDataCacheService.personalMap_put(overdueCustomerDetailBean.getUserId(), hyDateToString(updateTime));

        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getCompanyName())) {
            //新增客户工作
            //从内存中获取客户工作信息
            String idUpdateTimeStr = batchDataCacheService.personalJobMap_get(overdueCustomerDetailBean.getUserId());
            PersonalJob personalJob = new PersonalJob();
            if (idUpdateTimeStr != null) {
                String[] idUpdateTimeArray = idUpdateTimeStr.split("|");
                if (!idUpdateTimeArray[0].equals("temp")) {
                    personalJob.setId(idUpdateTimeArray[0]);
                } else {
                    return;
                }
            }
            personalJob.setCompanyName(overdueCustomerDetailBean.getCompanyName());
            personalJob.setPhone(overdueCustomerDetailBean.getCompanyTelephone());
            personalJob.setPhoneAreaCode(overdueCustomerDetailBean.getCompanyTelAreaCode());
            personalJob.setPhoneExt(overdueCustomerDetailBean.getCompanyTelephoneExt());
            personalJob.setCareer(overdueCustomerDetailBean.getCareer());

            if (overdueCustomerDetailBean.getCareer().equals("")) {
                //未知
                personalJob.setCareer("Z");
            } else {
                String career = SyncDataModel.professionalMap.get(overdueCustomerDetailBean.getCareer());
                if (career == null) {
                    throw new Exception("未找到该职业: " + overdueCustomerDetailBean.getCareer());
                } else {
                    personalJob.setCareer(overdueCustomerDetailBean.getCareer());
                }
            }

            if (overdueCustomerDetailBean.getIndustry().equals("")) {
                //未知
                personalJob.setIndustry("Z");
            } else {
                String industry = SyncDataModel.industryMap.get(overdueCustomerDetailBean.getIndustry());
                if (industry == null) {
                    throw new Exception("未找到该行业: " + overdueCustomerDetailBean.getIndustry());
                } else {
                    personalJob.setIndustry(overdueCustomerDetailBean.getIndustry());
                }
            }

            if (overdueCustomerDetailBean.getDuty().equals("")) {
                //未知
                personalJob.setPosition("Z");
            } else {
                String position = SyncDataModel.positionMap.get(overdueCustomerDetailBean.getDuty());
                if (position == null) {
                    throw new Exception("未找到该职务: " + overdueCustomerDetailBean.getDuty());
                } else {
                    personalJob.setPosition(overdueCustomerDetailBean.getDuty());
                }
            }

            if (overdueCustomerDetailBean.getUnitProperty().equals("")) {
                //未知
                personalJob.setNature("Z");
            } else {
                String nature = SyncDataModel.unitPropertyMap.get(overdueCustomerDetailBean.getUnitProperty());
                if (nature == null) {
                    throw new Exception("未找到该工作单位性质: " + overdueCustomerDetailBean.getUnitProperty());
                } else {
                    personalJob.setNature(overdueCustomerDetailBean.getUnitProperty());
                }
            }

            personalJob.setWorkYear(overdueCustomerDetailBean.getWorkingYears());
            personalJob.setProvinceName(overdueCustomerDetailBean.getCompanyProvinceName());
            personalJob.setProvinceCode(overdueCustomerDetailBean.getCompanyProvince());
            personalJob.setCityName(overdueCustomerDetailBean.getCompanyCityName());
            personalJob.setCityCode(overdueCustomerDetailBean.getCompanyCity());
            personalJob.setAreaName(overdueCustomerDetailBean.getCompanyAreaName());
            personalJob.setAreaCode(overdueCustomerDetailBean.getCompanyArea());
            personalJob.setAddress(overdueCustomerDetailBean.getCompanyAddress());
            personalJob.setPersonalId(overdueCustomerDetailBean.getUserId());
            personalJob.setOperatorTime(updateTime);
            syncDataModel.getPersonalJobInsertMap().put(personalJob.getPersonalId(), personalJob);
            //同步该客户工作信息到内存
            batchDataCacheService.personalJobMap_put(personalJob.getPersonalId(),
                    "temp|" + hyDateToString(personalJob.getOperatorTime()));
        }
        //同步客户住宅地址
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getLivingProvince())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getLivingCity())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getLivingArea())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getLivingAddress())) {
            //新增客户地址
            //从内存中获取客户住宅地址信息
            String idUpdateTimeStr = batchDataCacheService.personalAddressMap_get(
                    overdueCustomerDetailBean.getUserId() +
                            "-0-" + PersonalAddress.AddressType.LIVING_ADDRESS.getValue());
            PersonalAddress personalAddress1 = new PersonalAddress();
            if (idUpdateTimeStr != null) {
                String[] idUpdateTimeArray = idUpdateTimeStr.split("|");
                if (!idUpdateTimeArray[0].equals("temp")) {
                    personalAddress1.setId(idUpdateTimeArray[0]);
                }
            }
            personalAddress1.setPersonalId(overdueCustomerDetailBean.getUserId());
            personalAddress1.setLivingAreaCode(overdueCustomerDetailBean.getLivingArea());
            personalAddress1.setLivingAreaName(overdueCustomerDetailBean.getLivingAreaName());
            personalAddress1.setLivingCityCode(overdueCustomerDetailBean.getLivingCity());
            personalAddress1.setLivingCityName(overdueCustomerDetailBean.getLivingCityName());
            personalAddress1.setLivingProvinceCode(overdueCustomerDetailBean.getLivingProvince());
            personalAddress1.setLivingProvinceName(overdueCustomerDetailBean.getLivingProvinceName());
            personalAddress1.setRelation(0);//本人地址
            personalAddress1.setName(overdueCustomerDetailBean.getClientName());
            personalAddress1.setDetail(overdueCustomerDetailBean.getLivingAddress());
            personalAddress1.setType(PersonalAddress.AddressType.LIVING_ADDRESS.getValue());
            personalAddress1.setOperator("administrator");
            personalAddress1.setOperatorTime(updateTime);
            personalAddress1.setSource(Constants.DataSource.IMPORT.getValue());
            syncDataModel.getPersonalAddressInsertMap().put(
                    personalAddress1.getPersonalId() + "-"
                            + personalAddress1.getRelation() + "-"
                            + personalAddress1.getType(), personalAddress1);

            //同步该客户住宅地址信息到内存
            batchDataCacheService.personalAddressMap_put(personalAddress1.getPersonalId()
                            + "-" + personalAddress1.getRelation() + "-"
                            + personalAddress1.getType(),
                    "temp|" + hyDateToString(personalAddress1.getOperatorTime()));
        }

        //同步客户公司地址
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getCompanyProvince())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getCompanyCity())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getCompanyArea())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getCompanyAddress())) {
            String idUpdateTimeStr = batchDataCacheService.personalAddressMap_get(
                    overdueCustomerDetailBean.getUserId() +
                            "-0-" + PersonalAddress.AddressType.COMPANY_ADDRESS.getValue());
            PersonalAddress personalAddress2 = new PersonalAddress();
            if (idUpdateTimeStr != null) {
                String[] idUpdateTimeArray = idUpdateTimeStr.split("|");
                if (!idUpdateTimeArray[0].equals("temp")) {
                    personalAddress2.setId(idUpdateTimeArray[0]);
                }
            }
            personalAddress2.setPersonalId(overdueCustomerDetailBean.getUserId());
            personalAddress2.setLivingAreaCode(overdueCustomerDetailBean.getCompanyArea());
            personalAddress2.setLivingAreaName(overdueCustomerDetailBean.getCompanyAreaName());
            personalAddress2.setLivingCityCode(overdueCustomerDetailBean.getCompanyCity());
            personalAddress2.setLivingCityName(overdueCustomerDetailBean.getCompanyCityName());
            personalAddress2.setLivingProvinceCode(overdueCustomerDetailBean.getCompanyProvince());
            personalAddress2.setLivingProvinceName(overdueCustomerDetailBean.getCompanyProvinceName());
            personalAddress2.setRelation(0);
            personalAddress2.setName(overdueCustomerDetailBean.getClientName());
            personalAddress2.setDetail(overdueCustomerDetailBean.getCompanyAddress());
            personalAddress2.setType(PersonalAddress.AddressType.COMPANY_ADDRESS.getValue());
            personalAddress2.setOperator("administrator");
            personalAddress2.setOperatorTime(updateTime);
            personalAddress2.setSource(Constants.DataSource.IMPORT.getValue());
            syncDataModel.getPersonalAddressInsertMap().put(personalAddress2.getPersonalId()
                    + "-" + personalAddress2.getRelation() + "-"
                    + personalAddress2.getType(), personalAddress2);

            //同步该客户公司地址信息到内存
            batchDataCacheService.personalAddressMap_put(personalAddress2.getPersonalId() + "-"
                            + personalAddress2.getRelation() + "-" + personalAddress2.getType(),
                    "temp|" + hyDateToString(personalAddress2.getOperatorTime()));
        }

        //同步客户户籍地址
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getPermanentAddress())) {
            //从内存中获取客户户籍地址信息
            String idUpdateTimeStr = batchDataCacheService.personalAddressMap_get(
                    overdueCustomerDetailBean.getUserId() +
                            "-0-" + PersonalAddress.AddressType.PERMANENT_ADDRESS.getValue());
            PersonalAddress personalAddress3 = new PersonalAddress();
            if (idUpdateTimeStr != null) {
                String[] idUpdateTimeArray = idUpdateTimeStr.split("|");
                if (!idUpdateTimeArray[0].equals("temp")) {
                    personalAddress3.setId(idUpdateTimeArray[0]);
                }
            }
            personalAddress3.setRelation(0);
            personalAddress3.setName(overdueCustomerDetailBean.getClientName());
            personalAddress3.setDetail(overdueCustomerDetailBean.getPermanentAddress());
            personalAddress3.setType(PersonalAddress.AddressType.PERMANENT_ADDRESS.getValue());
            personalAddress3.setOperator("administrator");
            personalAddress3.setOperatorTime(updateTime);
            personalAddress3.setSource(Constants.DataSource.IMPORT.getValue());
            personalAddress3.setPersonalId(overdueCustomerDetailBean.getUserId());
            syncDataModel.getPersonalAddressInsertMap().put(personalAddress3.getPersonalId()
                    + "-" + personalAddress3.getRelation() + "-"
                    + personalAddress3.getType(), personalAddress3);
            //同步该客户公司地址信息到内存
            batchDataCacheService.personalAddressMap_put(personalAddress3.getPersonalId() + "-"
                            + personalAddress3.getRelation() + "-" + personalAddress3.getType(),
                    "temp|" + hyDateToString(personalAddress3.getOperatorTime()));
        }

        //同步本人信息到联系人
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getTelephone())) {
            //从内存中获取关联人
            PersonalContact contactSelf = new PersonalContact();
            contactSelf.setId(ShortUUID.uuid());
            contactSelf.setSource(Constants.DataSource.IMPORT.getValue());
            contactSelf.setPersonalId(overdueCustomerDetailBean.getUserId());
            contactSelf.setCustomerId(overdueCustomerDetailBean.getUserId());
            contactSelf.setName(overdueCustomerDetailBean.getClientName());
            contactSelf.setPhone(overdueCustomerDetailBean.getTelephone());
            contactSelf.setMobile(overdueCustomerDetailBean.getTelephone());
            contactSelf.setRelation(0);
            contactSelf.setOperator("administrator");
            contactSelf.setOperatorTime(updateTime);
            contactSelf.setUpdateTime(updateTime);
            //同步该关联人数据到内存
            String identify = DataSyncUtil.personalContactUniqueIdentify(contactSelf.getPersonalId(),
                    String.valueOf(contactSelf.getRelation()), contactSelf.getName(), contactSelf.getPhone());
            if (!BatchDataCacheService.personalContactUniqueIdentifyCacheContains(identify)) {
                BatchDataCacheService.personalContactCachePut(identify, contactSelf);
            }
        }

        //保存配偶信息到联系人
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getSpouseName())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getSpouseTel())) {
            //从内存中获取关联人配偶信息
            PersonalContact contact1 = new PersonalContact();
            contact1.setId(ShortUUID.uuid());
            contact1.setSource(Constants.DataSource.IMPORT.getValue());
            contact1.setPersonalId(overdueCustomerDetailBean.getUserId());
            contact1.setCustomerId(overdueCustomerDetailBean.getUserId());
            contact1.setName(overdueCustomerDetailBean.getSpouseName());
            contact1.setPhone(overdueCustomerDetailBean.getSpouseTel());
            contact1.setMobile(overdueCustomerDetailBean.getSpouseTel());
            contact1.setRelation(1);
            contact1.setOperator("administrator");
            contact1.setOperatorTime(updateTime);
            contact1.setUpdateTime(updateTime);
            //同步该关联人配偶信息到内存
            String identify = DataSyncUtil.personalContactUniqueIdentify(contact1.getPersonalId(),
                    String.valueOf(contact1.getRelation()), contact1.getName(), contact1.getPhone());
            if (!BatchDataCacheService.personalContactUniqueIdentifyCacheContains(identify)) {
                BatchDataCacheService.personalContactCachePut(identify, contact1);
            }
        }

        //同步紧急联系人信息到联系人
        if (StringUtils.isNotBlank(overdueCustomerDetailBean.getContact())
                && StringUtils.isNotBlank(overdueCustomerDetailBean.getContactTel())) {
            //从内存中获取紧急联系人
            PersonalContact contact2 = new PersonalContact();
            contact2.setId(ShortUUID.uuid());
            contact2.setSource(Constants.DataSource.IMPORT.getValue());
            contact2.setPersonalId(overdueCustomerDetailBean.getUserId());
            contact2.setCustomerId(overdueCustomerDetailBean.getUserId());
            contact2.setName(overdueCustomerDetailBean.getContact());
            contact2.setPhone(overdueCustomerDetailBean.getContactTel());
            contact2.setMobile(overdueCustomerDetailBean.getContactTel());
            contact2.setRelation(20);
            contact2.setOperator("administrator");
            contact2.setOperatorTime(updateTime);
            contact2.setUpdateTime(updateTime);
            //同步联系人到内存
            String identify = DataSyncUtil.personalContactUniqueIdentify(contact2.getPersonalId(),
                    String.valueOf(contact2.getRelation()), contact2.getName(), contact2.getPhone());
            if (!BatchDataCacheService.personalContactUniqueIdentifyCacheContains(identify)) {
                BatchDataCacheService.personalContactCachePut(identify, contact2);
            }
        }
    }

    private String[] buildPersonContactArray(PersonalContact personalContact) {
        String[] tempPersonalContact = new String[6];
        if (personalContact.getId() == null) {
            tempPersonalContact[0] = "temp";
        } else {
            tempPersonalContact[0] = personalContact.getId();
        }
        tempPersonalContact[1] = personalContact.getPersonalId();
        tempPersonalContact[2] = personalContact.getRelation().toString();
        tempPersonalContact[3] = personalContact.getPhone();
        tempPersonalContact[4] = personalContact.getRelationUserId();
        tempPersonalContact[5] = hyDateToString(personalContact.getOperatorTime());
        return tempPersonalContact;
    }

    /**
     * 解析客户开户信息加入到SyncDataModel
     *
     * @param customerAccountBean
     * @return
     */
    public void mergeHYCustomerAccount(HYOverdueCustomerAccountBean customerAccountBean, SyncDataModel syncDataModel) {
        //从内存中获取客户开户信息
        String updateTimeStr = batchDataCacheService.personalBankMap_get(customerAccountBean.getResourceId());
        Date updateTime = hyStringToDate(customerAccountBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(updateTimeStr, "yyyy-MM-dd hh:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        PersonalBank bank = new PersonalBank();
        bank.setAccountKind(customerAccountBean.getAccountKind());
        bank.setAccountType(customerAccountBean.getAccountKind());
        bank.setDepositBank(customerAccountBean.getBranchName());
        bank.setDepositBranch(customerAccountBean.getBranchName());
        bank.setAccountName(customerAccountBean.getAccountName());
        bank.setAccountNumber(customerAccountBean.getAccount());
        bank.setCardNumber(customerAccountBean.getAccount());
        bank.setBuildDate(hyStringToDate(customerAccountBean.getBuildDate(), null));
        bank.setCreateTime(hyStringToDate(customerAccountBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        bank.setOperatorTime(updateTime);
        bank.setId(customerAccountBean.getResourceId());
        bank.setPersonalId(customerAccountBean.getUserId());
        bank.setCustomerId(customerAccountBean.getUserId());
        bank.setResourceId(customerAccountBean.getResourceId());
        syncDataModel.getPersonalBankInsertMap().put(bank.getId(), bank);
        //同步该客户开户信息到内存
        batchDataCacheService.personalBankMap_put(bank.getId(), hyDateToString(updateTime));
    }

    /**
     * 解析客户文本信息加入到SyncDataModel
     *
     * @param overdueCustomerFileAttachBean
     * @return
     */
    public void mergeHYCustomerFileAttach(HYOverdueCustomerFileAttachBean overdueCustomerFileAttachBean, SyncDataModel syncDataModel) {
        //从内存中获取客户文本文件信息
        String updateTimeStr = batchDataCacheService.personalFileAttachMap_get(overdueCustomerFileAttachBean.getContractId());
        Date updateTime = hyStringToDate(overdueCustomerFileAttachBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(updateTimeStr, "yyyy-MM-dd hh:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        CaseFile caseFile = new CaseFile();
        if (overdueCustomerFileAttachBean.getContractType().equals("")) {
            caseFile.setContractType(overdueCustomerFileAttachBean.getContractType());
        } else {
            String contractType = overdueCustomerFileAttachBean.getContractType();
            if (contractType.equals("1")) {
                caseFile.setContractType("授权合同");
            } else if (contractType.equals("2")) {
                caseFile.setContractType("借款合同");
            } else if (contractType.equals("3")) {
                caseFile.setContractType("征信授权查询委托书");
            } else if (contractType.equals("4")) {
                caseFile.setContractType("用户注册协议");
            } else if (contractType.equals("5")) {
                caseFile.setContractType("用户绑卡及代扣协议");
            } else if (contractType.equals("6")) {
                caseFile.setContractType("商户加盟协议");
            }
        }

        if (overdueCustomerFileAttachBean.getContractStatus().equals("")) {
            caseFile.setContractType(overdueCustomerFileAttachBean.getContractType());
        } else {
            String contractType = overdueCustomerFileAttachBean.getContractStatus();
            if (contractType.equals("1")) {
                caseFile.setContractStatus("待审核");
            } else if (contractType.equals("2")) {
                caseFile.setContractStatus("已审核");
            } else if (contractType.equals("3")) {
                caseFile.setContractStatus("已作废");
            } else if (contractType.equals("4")) {
                caseFile.setContractStatus("已终止");
            } else if (contractType.equals("5")) {
                caseFile.setContractStatus("已到期");
            }
        }

        caseFile.setContractName(overdueCustomerFileAttachBean.getContractName());
        caseFile.setCreditEnddate(hyStringToDate(overdueCustomerFileAttachBean.getCreditEndDate(), null));
        caseFile.setAuthorizedValid(overdueCustomerFileAttachBean.getAuthorizedValid());
        caseFile.setProductName(overdueCustomerFileAttachBean.getProductName());
        caseFile.setProductNo(overdueCustomerFileAttachBean.getProductNo());
        caseFile.setFileUid(overdueCustomerFileAttachBean.getBak1());
        caseFile.setCreateTime(hyStringToDate(overdueCustomerFileAttachBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        caseFile.setUpdateTime(updateTime);
        caseFile.setId(overdueCustomerFileAttachBean.getContractId());
        caseFile.setCustomerId(overdueCustomerFileAttachBean.getUserId());
        caseFile.setContractId(overdueCustomerFileAttachBean.getContractId());
        syncDataModel.getCaseFileInsertMap().put(caseFile.getId(), caseFile);
        //同步该客户文本文件信息到内存
        batchDataCacheService.personalFileAttachMap_put(caseFile.getId(), hyDateToString(updateTime));
    }

    /**
     * 解析客户影像信息加入到SyncDataModel
     *
     * @param overdueCustomerImgAttachBean
     * @return
     */
    public void mergeHYCustomerImgAttach(HYOverdueCustomerImgAttachBean overdueCustomerImgAttachBean, SyncDataModel syncDataModel) {
        //从内存中获取影像文件数据
        String updateTimeStr = batchDataCacheService.personalImgFileMap_get(overdueCustomerImgAttachBean.getResourceId());
        Date updateTime = hyStringToDate(overdueCustomerImgAttachBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(updateTimeStr, "yyyy-MM-dd hh:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        PersonalImgFile personalImgFile = new PersonalImgFile();
        personalImgFile.setImageKind(overdueCustomerImgAttachBean.getImageKind());
        personalImgFile.setImageType(overdueCustomerImgAttachBean.getImageType());
        personalImgFile.setImageName(overdueCustomerImgAttachBean.getImageName());
        personalImgFile.setImageUrl(overdueCustomerImgAttachBean.getImageUrl());
        personalImgFile.setCreateTime(hyStringToDate(overdueCustomerImgAttachBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));

        personalImgFile.setUpdateTime(updateTime);
        personalImgFile.setId(overdueCustomerImgAttachBean.getResourceId());
        personalImgFile.setCustomerId(overdueCustomerImgAttachBean.getUserId());
        personalImgFile.setResourceId(overdueCustomerImgAttachBean.getResourceId());
        syncDataModel.getPersonalImgFileInsertMap().put(personalImgFile.getId(), personalImgFile);

        batchDataCacheService.personalImgFileMap_put(personalImgFile.getId(), hyDateToString(updateTime));
    }

    /**
     * 解析客户社交资料加入到SyncDataModel
     *
     * @param overdueCustomerSocialPlatBean
     * @return
     */
    public void mergeHYCustomerSocialPlat(HYOverdueCustomerSocialPlatBean overdueCustomerSocialPlatBean, SyncDataModel syncDataModel) {
        //从内存中获取客户社交平台信息
        String updateTimeStr = batchDataCacheService.personalSocialPlatMap_get(overdueCustomerSocialPlatBean.getStationId());
        Date updateTime = hyStringToDate(overdueCustomerSocialPlatBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(updateTimeStr, "yyyy-MM-dd hh:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        PersonalSocialPlat personalSocialPlat = new PersonalSocialPlat();

        if (StringUtils.isBlank(overdueCustomerSocialPlatBean.getSocialType())) {
            personalSocialPlat.setSocialType("其他");
        } else {
            String socialType = overdueCustomerSocialPlatBean.getSocialType();
            if (socialType.equals(EnumSocialType.QQ.getCode())) {
                personalSocialPlat.setSocialType("QQ");
            } else if (socialType.equals(EnumSocialType.WECHAT.getCode())) {
                personalSocialPlat.setSocialType("微信");
            } else if (socialType.equals(EnumSocialType.SINA.getCode())) {
                personalSocialPlat.setSocialType("新浪微博");
            } else if (socialType.equals(EnumSocialType.WANGWANG.getCode())) {
                personalSocialPlat.setSocialType("旺旺");
            } else if (socialType.equals(EnumSocialType.TENDERNESS.getCode())) {
                personalSocialPlat.setSocialType("脉脉");
            } else if (socialType.equals(EnumSocialType.UNFAMILIAR_STREET.getCode())) {
                personalSocialPlat.setSocialType("陌陌");
            } else {
                personalSocialPlat.setSocialType("其他");
            }
        }
        personalSocialPlat.setAccount(overdueCustomerSocialPlatBean.getAccount());
        personalSocialPlat.setNickName(overdueCustomerSocialPlatBean.getNickName());
        personalSocialPlat.setCreateTime(hyStringToDate(overdueCustomerSocialPlatBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        personalSocialPlat.setUpdateTime(updateTime);

        personalSocialPlat.setId(overdueCustomerSocialPlatBean.getStationId());
        personalSocialPlat.setCustomerId(overdueCustomerSocialPlatBean.getUserId());
        personalSocialPlat.setStationId(overdueCustomerSocialPlatBean.getStationId());
        syncDataModel.getPersonalSocialPlatInsertMap().put(personalSocialPlat.getId(), personalSocialPlat);

        batchDataCacheService.personalSocialPlatMap_put(personalSocialPlat.getId(), hyDateToString(updateTime));
    }

    /**
     * 解析客户联系人加入到SyncDataModel
     *
     * @param overdueCustomerRelationBean
     * @return
     */
    public void mergeHYCustomerRelation(HYOverdueCustomerRelationBean overdueCustomerRelationBean, SyncDataModel syncDataModel) throws Exception {

        PersonalContact personalContact = new PersonalContact();
        personalContact.setId(ShortUUID.uuid());
        // 手机号和姓名为空时过滤
        if (StringUtils.isBlank(overdueCustomerRelationBean.getRelationTelephone())
                && StringUtils.isBlank(overdueCustomerRelationBean.getRelationClientName())) {
            return;
        }
        personalContact.setPersonalId(overdueCustomerRelationBean.getUserId());
        personalContact.setCustomerId(overdueCustomerRelationBean.getUserId());
        personalContact.setRelationUserId(overdueCustomerRelationBean.getRelationUserId());
        personalContact.setSource(Constants.DataSource.IMPORT.getValue());
        personalContact.setClientRelation(overdueCustomerRelationBean.getClientRelation());

        if (overdueCustomerRelationBean.getClientRelation().equals("")) {
            personalContact.setRelation(8);
        } else {
            String relation = SyncDataModel.personalRelationMap.get(overdueCustomerRelationBean.getClientRelation());
            if (relation == null) {
                throw new Exception("未找到该关联人关系: " + overdueCustomerRelationBean.getClientRelation());
            } else {
                int relationCode = hyInitInteger(overdueCustomerRelationBean.getClientRelation());
                personalContact.setRelation(relationCode);
            }
        }

        if (overdueCustomerRelationBean.getRelationCertificateKind().equals("")) {
            personalContact.setRelationCertificateKind("0");
        } else {
            String certificatesType = SyncDataModel.certificateTypeMap.get(overdueCustomerRelationBean.getRelationCertificateKind());
            if (certificatesType == null) {
                throw new Exception("未找到该证件类型: " + overdueCustomerRelationBean.getRelationCertificateKind());
            } else {
                personalContact.setRelationCertificateKind(overdueCustomerRelationBean.getRelationCertificateKind());
            }
        }

        personalContact.setName(overdueCustomerRelationBean.getRelationClientName());
        personalContact.setCertificatesNumber(overdueCustomerRelationBean.getRelationCertificateNo());
        personalContact.setMobile(overdueCustomerRelationBean.getRelationTelephone());
        personalContact.setPhone(overdueCustomerRelationBean.getRelationTelephone());
        personalContact.setCreateTime(hyStringToDate(overdueCustomerRelationBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        personalContact.setOperatorTime(new Date());
        personalContact.setUpdateTime(new Date());
        personalContact.setOperator("administrator");

        String identify = DataSyncUtil.personalContactUniqueIdentify(personalContact.getPersonalId(),
                String.valueOf(personalContact.getRelation()), personalContact.getName(), personalContact.getPhone());
        if (BatchDataCacheService.personalContactUniqueIdentifyCacheContains(identify)) {
            return;
        }
        BatchDataCacheService.personalContactCachePut(identify, personalContact);

    }

    /**
     * 解析客户资产，征信，经营信息加入到SyncDataModel
     *
     * @param overdueCustomerAstOperCrdtBean
     * @return
     */
    public void mergeHYCustomerAstOperCrdt(HYOverdueCustomerAstOperCrdtBean overdueCustomerAstOperCrdtBean, SyncDataModel syncDataModel) {
        //从内存里获取客户资产经营征信信息
        String updateTimeStr = batchDataCacheService.personalAstOperCrdtMap_get(overdueCustomerAstOperCrdtBean.getResourceId());
        Date updateTime = hyStringToDate(overdueCustomerAstOperCrdtBean.getUpdateTime(), "yyyy/MM/dd HH:mm:ss");
        Date dbUpdateTime = hyStringToDate(updateTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (dbUpdateTime != null && updateTime.compareTo(dbUpdateTime) <= 0) {
            return;
        }
        PersonalAstOperCrdt personalAstOperCrdt = new PersonalAstOperCrdt();
        personalAstOperCrdt.setOriginalData(overdueCustomerAstOperCrdtBean.getOriginalData());
        personalAstOperCrdt.setCreateTime(hyStringToDate(overdueCustomerAstOperCrdtBean.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
        personalAstOperCrdt.setUpdateTime(updateTime);

        personalAstOperCrdt.setId(overdueCustomerAstOperCrdtBean.getResourceId());
        personalAstOperCrdt.setResourceId(overdueCustomerAstOperCrdtBean.getResourceId());
        personalAstOperCrdt.setCustomerId(overdueCustomerAstOperCrdtBean.getUserId());
        personalAstOperCrdt.setResourceType(overdueCustomerAstOperCrdtBean.getResourceType());
        syncDataModel.getPersonalAstOperCrdtInsertMap().put(personalAstOperCrdt.getId(), personalAstOperCrdt);
        //同步该客户资产经营征信信息到内存
        batchDataCacheService.personalAstOperCrdtMap_put(personalAstOperCrdt.getId(), hyDateToString(updateTime));
    }

    /**
     * 读取文件案件入库操作
     *
     * @param perDataList
     * @param syncDataModel
     */
    @Async
    public CompletableFuture<List<String>> doTaskSyns(List<String> perDataList, SyncDataModel syncDataModel,
                                                      Date closeDate) {
        logger.info("子线解析案件数据开始......");
        //查詢消费金融的委托方
        List<AreaCode> areaCodeList = areaCodeService.getAllAreaCode();
        Principal principal = principalRepository.findByCode("P001");
        List<String> resultDataList = new ArrayList<>();
        for (String json : perDataList) {
            try {
                List<CtmInterfaceDataBean> ctmInterfaceDataBeanList = jsonToObject(json);
                if (ctmInterfaceDataBeanList != null && ctmInterfaceDataBeanList.size() > 0) {
                    for (CtmInterfaceDataBean ctmInterfaceDataBean : ctmInterfaceDataBeanList) {
                        String caseNumber = ctmInterfaceDataBean.getApplNo();
                        Iterator<CaseInfoDistributed> caseInfoDistributedIterator = caseInfoDistributedRepository.findAll(
                                QCaseInfoDistributed.caseInfoDistributed.caseNumber.eq(caseNumber)).iterator();
                        //待分配表中已有数据
                        if (caseInfoDistributedIterator.hasNext()) {
                            CaseInfoDistributed caseInfoDistributed = caseInfoDistributedIterator.next();
                            mergeCaseInfoDistributed(ctmInterfaceDataBean, caseInfoDistributed, syncDataModel, areaCodeList);
                        } else {
                            //caseInfo中是否有数据

                            Iterator<CaseInfo> iterator = caseInfoRepository.findAll(QCaseInfo.caseInfo.caseNumber.eq(caseNumber)).iterator();
                            if (iterator.hasNext()) {
                                CaseInfo caseInfo = iterator.next();
                                mergeCaseInfo(ctmInterfaceDataBean, caseInfo, syncDataModel, areaCodeList);
                            } else {
                                //产品系列
                                mergeSeries(ctmInterfaceDataBean, syncDataModel);
                                //产品信息
                                mergerProduct(ctmInterfaceDataBean, syncDataModel);
                                //客户信息
                                mergePersonal(ctmInterfaceDataBean, syncDataModel);
                                //新案件数据
                                mergerCaseInfoDistributed(ctmInterfaceDataBean, syncDataModel, principal, closeDate, areaCodeList);
                                //附件 createCaseFile
                                mergeCaseFile(ctmInterfaceDataBean, ctmInterfaceDataBean.getApplNo(), syncDataModel);
                                //商品信息
                                mergeCommodity(ctmInterfaceDataBean, syncDataModel);
                                //订单信息
                                mergeOrderInfo(ctmInterfaceDataBean, syncDataModel);
                                //创建还款订单计划
                                mergeorderRepaymentPlan(ctmInterfaceDataBean, syncDataModel);
                            }
                        }
                        //案件还款计划
                        mergePayPlan(ctmInterfaceDataBean, syncDataModel);
                        //核销案件还款明细
                        mergeWriteOffDetails(ctmInterfaceDataBean, syncDataModel);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                resultDataList.add(Objects.isNull(e.getMessage()) ? "接收数据失败" : e.getMessage());
            }


        }
        logger.info("子线解析案件数据结束......");
        return CompletableFuture.completedFuture(resultDataList);
    }


    /**
     * 证明材料（附件）
     *
     * @param ctmInterfaceDataBean
     * @param caseNumber
     * @return
     */
    public void mergeCaseFile(CtmInterfaceDataBean ctmInterfaceDataBean, String caseNumber, SyncDataModel syncDataModel) {
        List<AttachmentInfo> attachmentInfoList = ctmInterfaceDataBean.getAttachmentInfoList();
        if (attachmentInfoList != null && attachmentInfoList.size() > 0) {
            for (AttachmentInfo attachmentInfo : attachmentInfoList) {
                CaseFile caseFile = new CaseFile();
                caseFile.setCaseNumber(caseNumber);//案件编号
                caseFile.setFileType(attachmentInfo.getDocType());//证明材料类型
                caseFile.setFileCode(attachmentInfo.getDocCd());//证明材料代码
                caseFile.setOperatorTime(stringToDate(attachmentInfo.getMtnDate(), null));//上传时间
                caseFile.setOperator(attachmentInfo.getOprId());//上传人员
                caseFile.setOperatorName(attachmentInfo.getOprId());//上传人员
                caseFile.setFileUrl(attachmentInfo.getFileName());//文件路径
//                caseFile.setFileName(attachmentInfo.getFileName());//文件名称
                if (attachmentInfo.getDocType().trim().equals("身份证明") || attachmentInfo.getDocType().trim().equals("居住证明")
                        || attachmentInfo.getDocType().trim().equals("NCIIC") || attachmentInfo.getDocType().trim().equals("工作证明")
                        || attachmentInfo.getDocType().trim().equals("驻场代表意见") || attachmentInfo.getDocType().trim().equals("授权书")
                        || attachmentInfo.getDocType().trim().equals("现场照") || attachmentInfo.getDocType().trim().equals("申请表")
                        || attachmentInfo.getDocType().trim().equals("预评估") || attachmentInfo.getDocType().trim().equals("客户签名材料")) {
                    caseFile.setFileSource(CaseFile.FileSource.Input_data.getValues());//进件资料
                } else if (attachmentInfo.getDocType().trim().equals("运营审查资料") || attachmentInfo.getDocType().trim().equals("银行卡")
                        || attachmentInfo.getDocType().trim().equals("入账凭条")) {
                    caseFile.setFileSource(CaseFile.FileSource.Supplementary.getValues());//进件资料
                } else {
                    caseFile.setFileSource(CaseFile.FileSource.Other_Information.getValues());//进件资料
                }
                syncDataModel.getInsertCaseFileMap().put(caseNumber, caseFile);
            }
        }
    }

    /**
     * 创建还款计划表
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public void mergePayPlan(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        List<RepayDetailed> repayDetailedList = ctmInterfaceDataBean.getRepayDetaileds();
        if (repayDetailedList != null && repayDetailedList.size() > 0) {
            syncDataModel.getDeletePayPMap().put(ctmInterfaceDataBean.getApplNo(), ctmInterfaceDataBean.getApplNo());
            int index = 0;
            for (RepayDetailed repayDetailed : repayDetailedList) {
                PayPlan payPlan = new PayPlan();
                index++;
                payPlan.setCaseNumber(ctmInterfaceDataBean.getApplNo());//案件编号
                payPlan.setPayPeriod(Integer.valueOf(initBigDecimal(repayDetailed.getTermNo())));//还款期数
                payPlan.setPayDate(stringToDate(repayDetailed.getPmtDueDate(), null));//还款日期
                payPlan.setPayAmt(new BigDecimal(initBigDecimal(repayDetailed.getTotalCost())));//应还金额
                payPlan.setPayPrincipal(new BigDecimal(initBigDecimal(repayDetailed.getPrincipal())));//应还本金
                payPlan.setPayInterest(new BigDecimal(initBigDecimal(repayDetailed.getInterest())));//应还利息
                payPlan.setPayPlatformFee(new BigDecimal(initBigDecimal(repayDetailed.getMthFee())));//应还平台管理费
                payPlan.setPayFine(new BigDecimal(initBigDecimal(repayDetailed.getPenaltyInt())));//应还罚息
                payPlan.setPayLiquidated(new BigDecimal(initBigDecimal(repayDetailed.getDefGold())));//应还违约金
                payPlan.setSurplusPrincipal(new BigDecimal(initBigDecimal(repayDetailed.getRemainPrin())));//剩余本金
                payPlan.setSurplusInterest(new BigDecimal(initBigDecimal(repayDetailed.getRemainInt())));//剩余利息
                payPlan.setSurplusPlatformFee(new BigDecimal(initBigDecimal(repayDetailed.getResManFee())));//剩余管理费
                payPlan.setPayNoun(new BigDecimal(initBigDecimal(repayDetailed.getOtInt())));//应还复利
                payPlan.setHasPayPrincipal(new BigDecimal(initBigDecimal(repayDetailed.getPrincipal())));//已偿还本金
                payPlan.setHasPayInterest(new BigDecimal(initBigDecimal(repayDetailed.getIntPaid())));//已偿还利息
                payPlan.setHasPayNoun(new BigDecimal(initBigDecimal(repayDetailed.getOtIntPaid())));//已偿还复利
                payPlan.setHasPayLiquidated(new BigDecimal(initBigDecimal(repayDetailed.getLpcPaid())));//已偿还违约金
                payPlan.setHasPayFine(new BigDecimal(initBigDecimal(repayDetailed.getPnltIntPaid())));//以偿还罚息
                payPlan.setHasPayManagement(new BigDecimal(initBigDecimal(repayDetailed.getMthFeePaid())));//已偿还账户管理费
                payPlan.setPayAmount(new BigDecimal(initBigDecimal(repayDetailed.getRepayTotalCost())));//还款总额

                if (repayDetailed.getRepaymentType().trim().equals("0")) {//还款类型
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Amount_interest_Equal.getValue());//等额本息
                } else if (repayDetailed.getRepaymentType().trim().equals("2")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Interest_Equal.getValue());//等额利息
                } else if (repayDetailed.getRepaymentType().trim().equals("8")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Tailing_repayment.getValue());//尾款还款
                } else if (repayDetailed.getRepaymentType().trim().equals("A")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period1.getValue());//宽限期-1
                } else if (repayDetailed.getRepaymentType().trim().equals("B")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period2.getValue());//宽限期-2
                } else if (repayDetailed.getRepaymentType().trim().equals("C")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period3.getValue());//宽限期-3
                } else if (repayDetailed.getRepaymentType().trim().equals("D")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period4.getValue());//宽限期-4
                } else if (repayDetailed.getRepaymentType().trim().equals("E")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period5.getValue());//宽限期-5
                } else if (repayDetailed.getRepaymentType().trim().equals("F")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period6.getValue());//宽限期-6
                } else if (repayDetailed.getRepaymentType().trim().equals("G")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period7.getValue());//宽限期-7
                } else if (repayDetailed.getRepaymentType().trim().equals("H")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period8.getValue());//宽限期-8
                } else if (repayDetailed.getRepaymentType().trim().equals("I")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period9.getValue());//宽限期-9
                } else if (repayDetailed.getRepaymentType().trim().equals("J")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period10.getValue());//宽限期-10
                } else if (repayDetailed.getRepaymentType().trim().equals("K")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period11.getValue());//宽限期-11
                } else if (repayDetailed.getRepaymentType().trim().equals("L")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.Grace_period12.getValue());//宽限期-12
                } else if (repayDetailed.getRepaymentType().trim().equals("M")) {
                    payPlan.setPayType(OrderRepaymentPlan.AmorzType.First_interest.getValue());//首期免息还款
                }
                syncDataModel.getInsertPayPMap().put(ctmInterfaceDataBean.getApplNo().concat("-").concat(String.valueOf(index)), payPlan);
            }
        }
    }

    /**
     * 创建核销案件的还款计划
     *
     * @param ctmInterfaceDataBean
     * @param syncDataModel
     */
    public void mergeWriteOffDetails(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        if (ctmInterfaceDataBean.getLoanStatus().trim().equals("W")) { //核销案件
            String caseNumber = ctmInterfaceDataBean.getApplNo();
            int index = 0;
            List<WriteOffRepDetail> list = ctmInterfaceDataBean.getWoRepDetail();
            if (list != null && list.size() > 0) {
                logger.info("案件编号：" + caseNumber + "有" + list.size() + "条核销明细");
                syncDataModel.getDeleteWriteOffDetailsMap().put(caseNumber, caseNumber);
                for (WriteOffRepDetail writeOffRepDetail : list) {
                    index++;
                    WriteOffDetails writeOffDetails = new WriteOffDetails();
                    writeOffDetails.setCaseNumber(caseNumber);
                    writeOffDetails.setPersonalNo(writeOffRepDetail.getVqCustId());
                    writeOffDetails.setInaccountDate(stringToDate(writeOffRepDetail.getVqInAccountDate(), null));
                    writeOffDetails.setUnpaidPrincipal(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInUnpaidPrincipal())));
                    writeOffDetails.setUnpaidInterest(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInUnpaidInterest())));
                    writeOffDetails.setRemainPrincipal(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInRemainPrin())));
                    writeOffDetails.setVerifiNobillInterest(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInVerifiNobillInt())));
                    writeOffDetails.setOtherInterest(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInOtherInt())));
                    writeOffDetails.setPnltInterest(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInPnltInt())));
                    writeOffDetails.setInFine(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInLpc())));
                    writeOffDetails.setMonthFee(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInMthFee())));
                    writeOffDetails.setOtherFee(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqInOtherFee())));
                    if (writeOffRepDetail.getVqTerminationInd().trim().equals("Y")) {
                        writeOffDetails.setTerminationInd("已结清");
                    } else if (writeOffRepDetail.getVqTerminationInd().trim().equals("N")) {
                        writeOffDetails.setTerminationInd("未结清");
                    }
                    writeOffDetails.setHasTotal(new BigDecimal(initBigDecimal(writeOffRepDetail.getVqTotal())));
                    writeOffDetails.setSettleDate(stringToDate(writeOffRepDetail.getVqUserField2(), null));
                    writeOffDetails.setRequestDate(stringToDate(writeOffRepDetail.getVqRequestDate(), null));
                    syncDataModel.getInsertWriteOffDetailsMap().put(caseNumber.concat("-").concat(String.valueOf(index)), writeOffDetails);
                }
            }
        }
    }

    /**
     * 获取核销案件结清时间
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public Date getWriteCloseDate(CtmInterfaceDataBean ctmInterfaceDataBean) {
        if (ctmInterfaceDataBean.getLoanStatus().trim().equals("W")) { //核销案件
            List<WriteOffRepDetail> list = ctmInterfaceDataBean.getWoRepDetail();
            if (list != null && list.size() > 0) {
                for (WriteOffRepDetail writeOffRepDetail : list) {
                    if (writeOffRepDetail.getVqTerminationInd().trim().equals("Y")) { //已结清
                        return stringToDate(writeOffRepDetail.getVqUserField2(), null);
                    }
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 创建订单还款计划
     *
     * @param ctmInterfaceDataBean
     */
    public void mergeorderRepaymentPlan(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        OrderInfo orderInfo = orderInfoRepository.findOne(QOrderInfo.orderInfo.caseNumber.eq(ctmInterfaceDataBean.getApplNo()));
        if (Objects.nonNull(orderInfo)) {
            OrderRepaymentPlan orderRepaymentPlaner = orderRepaymentPlanRepository.findOne(orderInfo.getId());
            if (Objects.nonNull(orderRepaymentPlaner)) {
                syncDataModel.getDeleteOrderRepaymentPlanMap().put(ctmInterfaceDataBean.getApplNo(), orderRepaymentPlaner);
            }
        }

        OrderRepaymentPlan orderRepaymentPlan = new OrderRepaymentPlan();
//        orderRepaymentPlan.setOrderId(orderId);//订单id
        orderRepaymentPlan.setFirstDueDate(stringToDate(ctmInterfaceDataBean.getFirstDueDate(), null));//首次还款日
        orderRepaymentPlan.setMonthAmt(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMthlyPmt())));//月还金额
        if (ctmInterfaceDataBean.getAmorzType().trim().equals("0")) {//还款计划类型
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Amount_interest_Equal.getValue());//等额本息
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("2")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Interest_Equal.getValue());//等额利息
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("8")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Tailing_repayment.getValue());//尾款还款
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("A")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period1.getValue());//宽限期-1
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("B")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period2.getValue());//宽限期-2
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("C")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period3.getValue());//宽限期-3
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("D")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period4.getValue());//宽限期-4
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("E")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period5.getValue());//宽限期-5
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("F")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period6.getValue());//宽限期-6
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("G")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period7.getValue());//宽限期-7
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("H")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period8.getValue());//宽限期-8
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("I")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period9.getValue());//宽限期-9
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("J")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period10.getValue());//宽限期-10
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("K")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period11.getValue());//宽限期-11
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("L")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.Grace_period12.getValue());//宽限期-12
        } else if (ctmInterfaceDataBean.getAmorzType().trim().equals("M")) {
            orderRepaymentPlan.setAmorzType(OrderRepaymentPlan.AmorzType.First_interest.getValue());//首期免息还款
        }

        orderRepaymentPlan.setYearRate(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getAnalInterestRate())));//年利率
        if (ctmInterfaceDataBean.getRepayMethod().trim().equals("0")) {//还款方式
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Amount_interest_Equal.getValue().toString());//等额本息
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("2")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Interest_Equal.getValue().toString());//等额利息
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("8")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Tailing_repayment.getValue().toString());//尾款还款
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("A")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period1.getValue().toString());//宽限期-1
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("B")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period2.getValue().toString());//宽限期-2
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("C")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period3.getValue().toString());//宽限期-3
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("D")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period4.getValue().toString());//宽限期-4
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("E")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period5.getValue().toString());//宽限期-5
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("F")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period6.getValue().toString());//宽限期-6
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("G")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period7.getValue().toString());//宽限期-7
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("H")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period8.getValue().toString());//宽限期-8
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("I")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period9.getValue().toString());//宽限期-9
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("J")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period10.getValue().toString());//宽限期-10
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("K")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period11.getValue().toString());//宽限期-11
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("L")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.Grace_period12.getValue().toString());//宽限期-12
        } else if (ctmInterfaceDataBean.getRepayMethod().trim().equals("M")) {
            orderRepaymentPlan.setRepayMethod(OrderRepaymentPlan.AmorzType.First_interest.getValue().toString());//首期免息还款
        }
        orderRepaymentPlan.setPenaltyRate(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getPenaltyRate())));//罚息费率
        orderRepaymentPlan.setAdvancePaymentRate(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getAdvRepPenRate())));//提前还款违约金费率
        orderRepaymentPlan.setStagingFeeRate(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getStaSerFeeRate())));//分期服务费费率
        orderRepaymentPlan.setRepaySubloanWether(ctmInterfaceDataBean.getRepaySubloanWether());//是否还后续贷

        if (ctmInterfaceDataBean.getBlacklistFlag().trim().equals("Y")) {//最高额抵押标识
            orderRepaymentPlan.setBlackFlag(OrderRepaymentPlan.Flag.YES.getValue());//黑名单
        } else if (ctmInterfaceDataBean.getBlacklistFlag().trim().equals("N")) {
            orderRepaymentPlan.setBlackFlag(OrderRepaymentPlan.Flag.NO.getValue());//黑名单
        } else {
            orderRepaymentPlan.setBlackFlag(OrderRepaymentPlan.Flag.NO.getValue());//黑名单
        }

        if (ctmInterfaceDataBean.getUserField9().trim().equals("Y")) {//最高额抵押标识
            orderRepaymentPlan.setMaxMortgageMark(OrderRepaymentPlan.Flag.YES.getValue());
        } else if (ctmInterfaceDataBean.getUserField9().trim().equals("N")) {
            orderRepaymentPlan.setMaxMortgageMark(OrderRepaymentPlan.Flag.NO.getValue());
        }
        if (ctmInterfaceDataBean.getUserField10().trim().equals("Y")) {//主贷款标识
            orderRepaymentPlan.setMainLoanLogo(OrderRepaymentPlan.Flag.YES.getValue());
        } else if (ctmInterfaceDataBean.getUserField10().trim().equals("N")) {
            orderRepaymentPlan.setMainLoanLogo(OrderRepaymentPlan.Flag.NO.getValue());
        }
        orderRepaymentPlan.setMainApplyNumber(ctmInterfaceDataBean.getUserField4());//主贷款申请号
        orderRepaymentPlan.setSalesRemark(ctmInterfaceDataBean.getSalesRemark());//销售代表备注
        orderRepaymentPlan.setPbocScore(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getPbocScore())));//人行征信评分
        orderRepaymentPlan.setDecisionNo(ctmInterfaceDataBean.getDecisionCd());//决定代码
        orderRepaymentPlan.setDecisionReason(ctmInterfaceDataBean.getDecisionReason());//决定原因
        orderRepaymentPlan.setCaseNumber(ctmInterfaceDataBean.getApplNo());
        syncDataModel.getInsertOrderRepaymentPlan().put(ctmInterfaceDataBean.getApplNo(), orderRepaymentPlan);
    }

    /**
     *
     */
    public void mergeOrderInfo(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        Iterator<OrderInfo> iterator = orderInfoRepository.findAll(QOrderInfo.orderInfo.caseNumber.eq(ctmInterfaceDataBean.getApplNo())).iterator();
        while (iterator.hasNext()) {
            OrderInfo orderInfo = new OrderInfo();
            syncDataModel.getDeleteOrderInfoMap().put(ctmInterfaceDataBean.getApplNo(), orderInfo);
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCaseNumber(ctmInterfaceDataBean.getApplNo());//案件编号
        orderInfo.setPromotionNumber(ctmInterfaceDataBean.getPromotionCd());//活动项目编号
        orderInfo.setChannelNumber(ctmInterfaceDataBean.getChannelCd());//申请渠道代码
        orderInfo.setTotalPrice(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMerchandiseTotalPrice())));//商品总价
        orderInfo.setSelfPayAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getSelfPayAmount())));//自付金额
        orderInfo.setLoanAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getLoanAmount())));//申请金额
        orderInfo.setLoanDate(stringToDate(ctmInterfaceDataBean.getLoanDate(), null));//放款日期
        orderInfo.setApprovedLoanAmt(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getApprovedLoanAmt())));//批准贷款金额
        orderInfo.setLoanTenure(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getLoanTenure())));//贷款期限

        if (ctmInterfaceDataBean.getLoanStatus().trim().equals("N")) {
            orderInfo.setLoanStatus(OrderInfo.LoanStatus.normal.getValue());//正常
        } else if (ctmInterfaceDataBean.getLoanStatus().trim().equals("D")) {
            orderInfo.setLoanStatus(OrderInfo.LoanStatus.overdue.getValue());//逾期
        } else if (ctmInterfaceDataBean.getLoanStatus().trim().equals("I")) {
            orderInfo.setLoanStatus(OrderInfo.LoanStatus.sluggish.getValue());//呆滞
        } else if (ctmInterfaceDataBean.getLoanStatus().trim().equals("B")) {
            orderInfo.setLoanStatus(OrderInfo.LoanStatus.Baddeb.getValue());//呆账
        } else if (ctmInterfaceDataBean.getLoanStatus().trim().equals("W")) {
            orderInfo.setLoanStatus(OrderInfo.LoanStatus.verification.getValue());//核销
        }

        orderInfo.setBillCycle(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getBillCycle())));//账单周期
        orderInfo.setStoreNumber(ctmInterfaceDataBean.getStoreNo());//销售门店代码
        orderInfo.setSaleName(ctmInterfaceDataBean.getSaleRepName());//销售代表姓名
        orderInfo.setSaleNamePhone(ctmInterfaceDataBean.getSaleRepPhone());//销售代表手机号
        syncDataModel.getInsertOrderInfoMap().put(ctmInterfaceDataBean.getApplNo(), orderInfo);
    }

    /**
     * 创建对应的商品信息
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public void mergeCommodity(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        List<Commodity> list = new ArrayList<>();
        Commodity commodity1 = new Commodity();
        String mgs;
        if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("00")) {
            commodity1.setCommType(Commodity.CommType.Mobile.getValue()); //手机
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("10")) {
            commodity1.setCommType(Commodity.CommType.Black_electricity.getValue()); //黑电
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("11")) {
            commodity1.setCommType(Commodity.CommType.White_electricity.getValue()); //白电
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("20")) {
            commodity1.setCommType(Commodity.CommType.Notebook.getValue()); //笔记本
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("21")) {
            commodity1.setCommType(Commodity.CommType.Desktop.getValue()); //台式机及外围设备
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("30")) {
            commodity1.setCommType(Commodity.CommType.Digital.getValue()); //数码
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("40")) {
            commodity1.setCommType(Commodity.CommType.Electric_vehicle.getValue()); //电动车
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("41")) {
            commodity1.setCommType(Commodity.CommType.Motorcycle.getValue()); //摩托车
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("50")) {
            commodity1.setCommType(Commodity.CommType.furniture.getValue()); //家具
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("51")) {
            commodity1.setCommType(Commodity.CommType.Musical_Instruments.getValue()); //乐器
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("52")) {
            commodity1.setCommType(Commodity.CommType.consumer.getValue()); //汽车衍生消费品
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("53")) {
            commodity1.setCommType(Commodity.CommType.Medical_cosmetology.getValue()); //医疗美容
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("54")) {
            commodity1.setCommType(Commodity.CommType.Health.getValue()); //健康类
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("60")) {
            commodity1.setCommType(Commodity.CommType.Tourism_staging.getValue()); //旅游分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("61")) {
            commodity1.setCommType(Commodity.CommType.Decoration_staging.getValue()); //装修分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("62")) {
            commodity1.setCommType(Commodity.CommType.Educational_staging.getValue()); //教育分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("63")) {
            commodity1.setCommType(Commodity.CommType.Wedding_stage.getValue()); //婚庆分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("64")) {
            commodity1.setCommType(Commodity.CommType.General_use_cash.getValue()); //一般用途现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("65")) {
            commodity1.setCommType(Commodity.CommType.Other_cash.getValue()); //一般用途现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("66")) {
            commodity1.setCommType(Commodity.CommType.New_General_use_cash.getValue()); //一般用途现金(新定价）
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("67")) {
            commodity1.setCommType(Commodity.CommType.New_Other_cash.getValue()); //其他现金(新定价）
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("68")) {
            commodity1.setCommType(Commodity.CommType.Long_term_mortgage_loan.getValue()); //长期限抵押贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("69")) {
            commodity1.setCommType(Commodity.CommType.Credit_loan.getValue()); //联营业务-信用贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("70")) {
            commodity1.setCommType(Commodity.CommType.Circular_loan_durable_goods.getValue()); //循环贷耐用品
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("71")) {
            commodity1.setCommType(Commodity.CommType.Circular_loan_cash.getValue()); //循环贷现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("72")) {
            commodity1.setCommType(Commodity.CommType.Guarantee_loan.getValue()); //保证贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("80")) {
            commodity1.setCommType(Commodity.CommType.Circulation_loan_instalment.getValue()); //循环贷分期付款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("81")) {
            commodity1.setCommType(Commodity.CommType.Circular_loan_credit_loan.getValue()); //循环贷信用贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("82")) {
            commodity1.setCommType(Commodity.CommType.Joint_operation.getValue()); //联营业务-变更产品要素-宽限期（新增期限）
        }

        commodity1.setCommBrand(ctmInterfaceDataBean.getMerchandiseBrand1());//商品品牌(代银)1
        commodity1.setCommModel(ctmInterfaceDataBean.getMerchandiseModel1());//商品型号(账号)1
        commodity1.setCommPrice(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMerchandisePrice1())));//商品价格(贷款）1
        mgs = commodity1.getCommType() + "-" + commodity1.getCommBrand() + "-" + commodity1.getCommModel();
        commodity1.setCaseNumber(ctmInterfaceDataBean.getApplNo());
        syncDataModel.getInsertCommodity().put(mgs, commodity1);

        Commodity commodity2 = new Commodity();
        if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("00")) {
            commodity2.setCommType(Commodity.CommType.Mobile.getValue()); //手机
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("10")) {
            commodity2.setCommType(Commodity.CommType.Black_electricity.getValue()); //黑电
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("11")) {
            commodity2.setCommType(Commodity.CommType.White_electricity.getValue()); //白电
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("20")) {
            commodity2.setCommType(Commodity.CommType.Notebook.getValue()); //笔记本
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("21")) {
            commodity2.setCommType(Commodity.CommType.Desktop.getValue()); //台式机及外围设备
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("30")) {
            commodity2.setCommType(Commodity.CommType.Digital.getValue()); //数码
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("40")) {
            commodity2.setCommType(Commodity.CommType.Electric_vehicle.getValue()); //电动车
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("41")) {
            commodity2.setCommType(Commodity.CommType.Motorcycle.getValue()); //摩托车
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("50")) {
            commodity2.setCommType(Commodity.CommType.furniture.getValue()); //家具
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("51")) {
            commodity2.setCommType(Commodity.CommType.Musical_Instruments.getValue()); //乐器
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("52")) {
            commodity2.setCommType(Commodity.CommType.consumer.getValue()); //汽车衍生消费品
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("53")) {
            commodity2.setCommType(Commodity.CommType.Medical_cosmetology.getValue()); //医疗美容
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("54")) {
            commodity2.setCommType(Commodity.CommType.Health.getValue()); //健康类
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("60")) {
            commodity2.setCommType(Commodity.CommType.Tourism_staging.getValue()); //旅游分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("61")) {
            commodity2.setCommType(Commodity.CommType.Decoration_staging.getValue()); //装修分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("62")) {
            commodity2.setCommType(Commodity.CommType.Educational_staging.getValue()); //教育分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("63")) {
            commodity2.setCommType(Commodity.CommType.Wedding_stage.getValue()); //婚庆分期
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("64")) {
            commodity2.setCommType(Commodity.CommType.General_use_cash.getValue()); //一般用途现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("65")) {
            commodity2.setCommType(Commodity.CommType.Other_cash.getValue()); //一般用途现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("66")) {
            commodity2.setCommType(Commodity.CommType.New_General_use_cash.getValue()); //一般用途现金(新定价）
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("67")) {
            commodity2.setCommType(Commodity.CommType.New_Other_cash.getValue()); //其他现金(新定价）
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("68")) {
            commodity2.setCommType(Commodity.CommType.Long_term_mortgage_loan.getValue()); //长期限抵押贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("69")) {
            commodity2.setCommType(Commodity.CommType.Credit_loan.getValue()); //联营业务-信用贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("70")) {
            commodity2.setCommType(Commodity.CommType.Circular_loan_durable_goods.getValue()); //循环贷耐用品
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("71")) {
            commodity2.setCommType(Commodity.CommType.Circular_loan_cash.getValue()); //循环贷现金
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("72")) {
            commodity2.setCommType(Commodity.CommType.Guarantee_loan.getValue()); //保证贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("80")) {
            commodity2.setCommType(Commodity.CommType.Circulation_loan_instalment.getValue()); //循环贷分期付款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("81")) {
            commodity2.setCommType(Commodity.CommType.Circular_loan_credit_loan.getValue()); //循环贷信用贷款
        } else if (ctmInterfaceDataBean.getMerchandiseCat1().trim().equals("82")) {
            commodity2.setCommType(Commodity.CommType.Joint_operation.getValue()); //联营业务-变更产品要素-宽限期（新增期限）
        }
        commodity2.setCommBrand(ctmInterfaceDataBean.getMerchandiseBrand2());//商品品牌(代银)1
        commodity2.setCommModel(ctmInterfaceDataBean.getMerchandiseModel2());//商品型号(账号)1
        commodity2.setCommPrice(new BigDecimal(ctmInterfaceDataBean.getMerchandisePrice2()));//商品价格(贷款）1
        commodity2.setCaseNumber(ctmInterfaceDataBean.getApplNo());
        list.add(commodity2);
        mgs = commodity2.getCommType() + "-" + commodity2.getCommBrand() + "-" + commodity2.getCommModel();
        syncDataModel.getInsertCommodity().put(mgs, commodity2);
    }

    /**
     * 将json串转化为对象
     *
     * @param json
     * @return
     */
    public List<CtmInterfaceDataBean> jsonToObject(String json) {
        List<CtmInterfaceDataBean> ctmInterfaceDataBean = JSONObject.parseArray(json, CtmInterfaceDataBean.class);
        return ctmInterfaceDataBean;
    }

    /**
     * 创建案件待分配案件
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public void mergeCaseInfoDistributed(CtmInterfaceDataBean ctmInterfaceDataBean, CaseInfoDistributed caseInfoDistributed,
                                         SyncDataModel syncDataModel, List<AreaCode> areaCodeList) {
        caseInfoDistributed.setCaseNumber(ctmInterfaceDataBean.getApplNo());//借据号  申请号
        caseInfoDistributed.setOverdueAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalOverdue())));//逾期总金额
        caseInfoDistributed.setOverdueCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOverduePrincipal())));//逾期本金
        caseInfoDistributed.setOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDpdDays())));//逾期天数
        caseInfoDistributed.setLatelyPayDate(stringToDate(ctmInterfaceDataBean.getLastPaymentDate(), null));//最近还款日期
        caseInfoDistributed.setLatelyPayAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getLastPaymentAmt())));//最近一次还款金额1
        caseInfoDistributed.setLeftCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainPrin())));//剩余本金
        caseInfoDistributed.setLeftInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainInt())));//剩余利息
        String dueNum = initBigDecimal(ctmInterfaceDataBean.getDueNum());
        String duePeriods = "M".concat(dueNum);
        caseInfoDistributed.setOverduePeriods(Integer.valueOf(dueNum));//逾期期数
        caseInfoDistributed.setPayStatus(duePeriods);//逾期期数
        caseInfoDistributed.setLeaveCaseFlag(0);//留案标志
        caseInfoDistributed.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
        caseInfoDistributed.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());
        caseInfoDistributed.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件颜色标记
        caseInfoDistributed.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()); //未回收
        caseInfoDistributed.setCompanyCode("0001");
        caseInfoDistributed.setExecutedPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getLastedTerm())));
        AreaCode areaCode = mergeAreaCodeByAddress(ctmInterfaceDataBean, areaCodeList);
        if (Objects.nonNull(areaCode)) {
            caseInfoDistributed.setArea(areaCode);
        }
        caseInfoDistributed.setMaxOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getMaxdelqdayCnt())));//逾期最大天数
        caseInfoDistributed.setLatesDateReturn(stringToDate(ctmInterfaceDataBean.getLatestDateReturn(), null));//最近一次应还日期
        caseInfoDistributed.setLeftPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getRemPeriodsNum())));//剩余期数
        caseInfoDistributed.setUnpaidPrincipal(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPrincipal())));//未尝还本金
        caseInfoDistributed.setUnpaidInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidInterest())));//未偿还利息
        caseInfoDistributed.setUnpaidFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPnltInt())));//未尝还罚息
        caseInfoDistributed.setUnpaidOtherInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtInt())));//未偿还其它利息
        caseInfoDistributed.setUnpaidMthFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidMthFee())));//未尝还管理费
        caseInfoDistributed.setUnpaidOtherFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtFee())));//未尝还其他费用
        caseInfoDistributed.setUnpaidLpc(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidLpc())));//未尝还滞纳金
//        caseInfoDistributed.setCurrPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getCurrPnltInt())));//当前未结罚息复利
        caseInfoDistributed.setPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnbillInt())));
        caseInfoDistributed.setPnltFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalAcru())));
        caseInfoDistributed.setRemainFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainFee())));//剩余月服务费
        caseInfoDistributed.setOverdueAccountNumber(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDueApplyCnt())));//逾期账户数
        caseInfoDistributed.setInColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getInColCnt())));//內催次数
        caseInfoDistributed.setOutColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getOutColCnt())));//委外次数
        caseInfoDistributed.setAccountBalance(caseInfoDistributed.getUnpaidPrincipal().add(caseInfoDistributed.getLeftCapital()));//账户余额
        caseInfoDistributed.setSettleAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getSettleAmt())));//结清金额
        Date date = getWriteCloseDate(ctmInterfaceDataBean);
        if (Objects.nonNull(date)) {
            caseInfoDistributed.setSettleDate(date);
        }
        syncDataModel.getCaseInfoDistributedUdpateMap().put(ctmInterfaceDataBean.getApplNo(), caseInfoDistributed);
    }

    /**
     * 更新案件信息
     *
     * @param ctmInterfaceDataBean
     * @param caseInfo
     * @return
     */
    public void mergeCaseInfo(CtmInterfaceDataBean ctmInterfaceDataBean, CaseInfo caseInfo,
                              SyncDataModel syncDataModel, List<AreaCode> areaCodeList) {

        caseInfo.setCaseNumber(ctmInterfaceDataBean.getApplNo());//借据号  申请号
        caseInfo.setOverdueAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalOverdue())));//逾期总金额
        caseInfo.setOverdueCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOverduePrincipal())));//逾期本金
//        caseInfo.setOverdueInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOverdueInterest())));//逾期利息
//        caseInfo.setOverdueFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getLateCharge())));//逾期罚息
//        caseInfo.setOverdueDelayFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOverdueGold())));//逾期滞纳金
        caseInfo.setHasPayPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getReturnsNum())));//已还期数
//        caseInfoDistributed.setHasPayAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getReimbAmount())));//已还金额
        String dueNum = initBigDecimal(ctmInterfaceDataBean.getDueNum());
        String duePeriods = "M".concat(dueNum);
        caseInfo.setOverduePeriods(Integer.valueOf(dueNum));//逾期期数
        caseInfo.setPayStatus(duePeriods);//逾期期数
        caseInfo.setOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDpdDays())));//逾期天数
        caseInfo.setLatelyPayDate(stringToDate(ctmInterfaceDataBean.getLastPaymentDate(), null));//最近还款日期
        caseInfo.setLatelyPayAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getLastPaymentAmt())));//最近一次还款金额1
        caseInfo.setLeftCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainPrin())));//剩余本金
        caseInfo.setLeftInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainInt())));//剩余利息
        caseInfo.setExecutedPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getLastedTerm())));
        AreaCode areaCode = mergeAreaCodeByAddress(ctmInterfaceDataBean, areaCodeList);
        if (Objects.nonNull(areaCode)) {
            caseInfo.setArea(areaCode);
        }

        caseInfo.setMaxOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getMaxdelqdayCnt())));//逾期最大天数
        caseInfo.setLatesDateReturn(stringToDate(ctmInterfaceDataBean.getLatestDateReturn(), null));//最近一次应还日期
        caseInfo.setLeftPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getRemPeriodsNum())));//剩余期数
        caseInfo.setUnpaidPrincipal(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPrincipal())));//未尝还本金
        caseInfo.setUnpaidInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidInterest())));//未偿还利息
        caseInfo.setUnpaidFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPnltInt())));//未尝还罚息
        caseInfo.setUnpaidOtherInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtInt())));//未偿还其它利息
        caseInfo.setUnpaidMthFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidMthFee())));//未尝还管理费
        caseInfo.setUnpaidOtherFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtFee())));//未尝还其他费用
        caseInfo.setUnpaidLpc(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidLpc())));//未尝还滞纳金
//        caseInfo.setCurrPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getCurrPnltInt())));//当前未结罚息复利
        caseInfo.setPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnbillInt())));
        caseInfo.setPnltFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalAcru())));
        caseInfo.setRemainFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainFee())));//剩余月服务费
        caseInfo.setOverdueAccountNumber(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDueApplyCnt())));//逾期账户数
        caseInfo.setInColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getInColCnt())));//內催次数
        caseInfo.setOutColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getOutColCnt())));//委外次数
        caseInfo.setAccountBalance(caseInfo.getUnpaidPrincipal().add(caseInfo.getLeftCapital()));//账户余额
        caseInfo.setSettleAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getSettleAmt())));//结清金额
        Date date = getWriteCloseDate(ctmInterfaceDataBean);
        if (Objects.nonNull(date)) {
            caseInfo.setSettleDate(date);
        }
        syncDataModel.getCaseInfoUpdateMap().put(ctmInterfaceDataBean.getApplNo(), caseInfo);
    }


    /**
     * 产品系列
     *
     * @param ctmInterfaceDataBean
     * @param syncDataModel
     * @return
     */
    public void mergeSeries(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        String seriesName = ctmInterfaceDataBean.getProductCd().trim();//产品系列名称
        if (Objects.isNull(seriesName) || "".equals(seriesName.trim())) {
            seriesName = "未知";
        }
        Iterator<ProductSeries> productSeriesIterator = productSeriesRepository.findAll(QProductSeries.productSeries.seriesName
                .eq(seriesName)).iterator();
        if (Objects.nonNull(productSeriesIterator) && productSeriesIterator.hasNext()) {
            syncDataModel.getExistPSMap().put(seriesName, productSeriesIterator.next());
        } else {
            ProductSeries productSeries = new ProductSeries();
            productSeries.setSeriesName(seriesName);//产品系列名称 （产品类型）
            productSeries.setCompanyCode("0001");
            syncDataModel.getInsertPSMap().put(seriesName, productSeries);
        }
    }

    public void mergerProduct(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        String seriesName = ctmInterfaceDataBean.getProductCd().trim();//产品系列名称
        if (Objects.isNull(seriesName) || "".equals(seriesName.trim())) {
            seriesName = "未知";
        }
        //产品名称
        String productName = ctmInterfaceDataBean.getProductName();
        if (Objects.isNull(productName) || "".equals(productName.trim())) {
            productName = "未知";
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QProduct.product.productSeries.seriesName.eq(seriesName));
        booleanBuilder.and(QProduct.product.productName.eq(productName));
        Iterator<Product> productIterable = productRepository.findAll(booleanBuilder).iterator();
        if (Objects.nonNull(productIterable) && productIterable.hasNext()) {
            syncDataModel.getExistPCMap().put(productName, productIterable.next());
        } else {
            if (Objects.isNull(seriesName) || "".equals(seriesName.trim())) {
                seriesName = "未知";
            }
            Product product = new Product();
            product.setProductName(productName);//产品名称
            product.setProductSeries(syncDataModel.getExistPSMap().get(seriesName));
            product.setProductSerieName(seriesName);
            product.setCompanyCode("0001");
            syncDataModel.getInsertPCMap().put(seriesName.concat("-").concat(productName), product);
        }
    }

    /**
     * 对案件客户信息判断去重，并保存新的客户信息
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public void mergePersonal(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel) {
        //根据证件号查询该用户是否存在
        Iterator<Personal> personalIterator = personalRepository.findAll(QPersonal.personal.certificatesNumber
                .eq(ctmInterfaceDataBean.getIdno())).iterator();
        if (Objects.nonNull(personalIterator) && personalIterator.hasNext()) {
            syncDataModel.getExistPMap().put(ctmInterfaceDataBean.getIdno(), personalIterator.next());
        } else {
            Personal personalNew = createPersonal(ctmInterfaceDataBean);//保存客户信息
            syncDataModel.getInsertPMap().put(ctmInterfaceDataBean.getIdno(), personalNew);
            //客户的备注信息
            Material material = createMaterital(ctmInterfaceDataBean,
                    Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                            syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            syncDataModel.getInsertMaMap().put(ctmInterfaceDataBean.getIdno(), material);
            //客户收入信息
            PersonalIncomeExp personalIncomeExp = createPersonalIncomeExp(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            syncDataModel.getInsertPIMap().put(ctmInterfaceDataBean.getIdno(), personalIncomeExp);
            //客户工作信息
            PersonalJob personalJob = createPersonalJob(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            syncDataModel.getInsertPJMap().put(ctmInterfaceDataBean.getIdno(), personalJob);
            //客户的银行信息
            PersonalBank personalBank = createPersonalBank(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            syncDataModel.getInsertBMap().put(ctmInterfaceDataBean.getIdno(), personalBank);
            //客户车辆信息
            PersonalCar personalCar = createPersonalCar(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            syncDataModel.getInsertPCarMap().put(ctmInterfaceDataBean.getIdno(), personalCar);
            //客户房产信息
            List<PersonalProperty> personalPropertyList = createPersonalProperty(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            for (PersonalProperty personalProperty : personalPropertyList) {
                syncDataModel.getInsertPPMap().put(personalProperty.getCertificatesNumber(), personalProperty);
            }

            List<PersonalContact> personalContactList = createPersonalContact(ctmInterfaceDataBean, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            for (PersonalContact personalContact : personalContactList) {
                syncDataModel.getInsertPConMap().put(personalContact.getCertificatesNumber(), personalContact);
            }
            List<PersonalAddress> personalAddressList = createPersonalAddress(personalNew, personalContactList, Objects.nonNull(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno())) ?
                    syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()).getId() : null);
            for (PersonalAddress personalAddress : personalAddressList) {
                syncDataModel.getInsertAddressMap().put(personalAddress.getCertificatesNumber(), personalAddress);
            }
        }
    }

    public List<PersonalAddress> createPersonalAddress(Personal personal, List<PersonalContact> list, String personalId) {
        List<PersonalAddress> personalAddressList = new ArrayList<>();
        int index = 0;
        for (PersonalContact personalContact : list) {
            index++;
            PersonalAddress address = new PersonalAddress();
            address.setPersonalId(personalId);
            address.setName(personalContact.getName());//姓名
            address.setDetail(personalContact.getAddress());//现居住地址
            address.setType(Personal.AddrRelationEnum.CURRENTADDR.getValue());
            address.setStatus(Personal.AddrStatus.UNKNOWN.getValue());
            address.setSource(Constants.DataSource.IMPORT.getValue());//数据来源
            address.setCertificatesNumber(personal.getCertificatesNumber().concat("-").concat(String.valueOf(index)));
            address.setOperatorTime(new Date());
            personalAddressList.add(address);
        }
        return personalAddressList;
    }

    /**
     * 创建客户车辆信息对象
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public PersonalCar createPersonalCar(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        PersonalCar personalCar = new PersonalCar();
        personalCar.setPersonalId(personalId);//客户id
        personalCar.setNo(ctmInterfaceDataBean.getVehicleLicence());//车牌号码
        personalCar.setDriverNumber(ctmInterfaceDataBean.getDriverLicence());//驾照号码
        personalCar.setCertificatesNumber(ctmInterfaceDataBean.getIdno());
        return personalCar;
    }

    /**
     * 创建客户银行信息对象
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public PersonalBank createPersonalBank(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        PersonalBank personalBank = new PersonalBank();
        personalBank.setId(ShortUUID.uuid());
        personalBank.setPersonalId(personalId);//客户id
        personalBank.setDepositBank(ctmInterfaceDataBean.getBankName());//开户银行
        personalBank.setCardNumber(ctmInterfaceDataBean.getAutopayBankAcctno());//约定还款扣款账号
        personalBank.setCertificatesNumber(ctmInterfaceDataBean.getIdno());
        return personalBank;
    }

    /**
     * 创建客户工作信息对象
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public PersonalJob createPersonalJob(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        PersonalJob personalJob = new PersonalJob();
        personalJob.setPersonalId(personalId);
        personalJob.setCompanyName(ctmInterfaceDataBean.getCompName());//单位名称
        personalJob.setDepartment(ctmInterfaceDataBean.getCompDepartment());//部门
        personalJob.setRank(ctmInterfaceDataBean.getJobPosition());//职级 岗位
        personalJob.setPosition(ctmInterfaceDataBean.getCompDesgn().trim());
        if (ctmInterfaceDataBean.getCompStructure().trim().equals("1")) {//事业单位
            personalJob.setNature(PersonalJob.Nature.Government.getValue());//单位性质
        } else if (ctmInterfaceDataBean.getCompStructure().trim().equals("2")) {//社会团体
            personalJob.setNature(PersonalJob.Nature.Sociology.getValue());//单位性质
        } else if (ctmInterfaceDataBean.getCompStructure().trim().equals("3")) {//企业
            personalJob.setNature(PersonalJob.Nature.enterprise.getValue());//单位性质
        } else if (ctmInterfaceDataBean.getCompStructure().trim().equals("4")) {//个体
            personalJob.setNature(PersonalJob.Nature.individual.getValue());//单位性质
        } else if (ctmInterfaceDataBean.getCompStructure().trim().equals("5")) {//其他
            personalJob.setNature(PersonalJob.Nature.other.getValue());//单位性质
        }

        personalJob.setPhone(ctmInterfaceDataBean.getCompTelno());//单位固定电话
        String jobaddress = ctmInterfaceDataBean.getCompAdr();
        personalJob.setAddress(jobaddress.replace("|", "").trim());//单位地址
        personalJob.setMonthSalary(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMthBasicSalary())));//基本月薪（税后）
        personalJob.setIndustry(ctmInterfaceDataBean.getBussSic().trim());
        personalJob.setWorkMother(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getLengthSrv())));//工作时长(月)
        personalJob.setCertificatesNumber(ctmInterfaceDataBean.getIdno());
        return personalJob;
    }

    /**
     * 创建客户收入信息
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public PersonalIncomeExp createPersonalIncomeExp(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        PersonalIncomeExp personalIncomeExp = new PersonalIncomeExp();
        personalIncomeExp.setPersonalId(personalId);
        if (ctmInterfaceDataBean.getIncomeType().trim().equals("0")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Accumulation.getValue());//公积金
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("1")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.social_security.getValue());//社保
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("2")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Taxbill.getValue());//税单
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("3")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Salary_flow.getValue());//薪资流水
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("4")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Mortgage.getValue());//房贷缴供流水
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("5")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.certificate.getValue());//公司出具收入证明
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("6")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Running_water.getValue());//经营流水
        } else if (ctmInterfaceDataBean.getIncomeType().trim().equals("7")) {
            personalIncomeExp.setIncomeType(PersonalIncomeExp.IncomeType.Other.getValue());//其他
        }

        personalIncomeExp.setFundCompMame(ctmInterfaceDataBean.getFundCompName());//公积金缴存单位
        personalIncomeExp.setFundCount(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getFundCount())));//缴存期数
        personalIncomeExp.setFundAmt(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getFundAmt())));//个人公积金额度
        personalIncomeExp.setSocialSecurityAmt(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getSocialsecurityAmount())));//个人社保月均缴存额度
        personalIncomeExp.setSocialSecurityRato(ctmInterfaceDataBean.getSocialsecurityRatio());//社保缴存比例
        personalIncomeExp.setSubsidyMonth(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMthFixedAllow())));//月固定补贴
        personalIncomeExp.setFamilyIncome(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMthIncome())));//家庭月收入
        personalIncomeExp.setHosingLoan(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMthRental())));//每月租房/房贷费用
//        personalIncomeExp.setMonthOtherIncome(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOtherMonthIncome())));//每月的其他收入
        personalIncomeExp.setAnnualIncome(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getAnnualIncome())));//年收入
//        personalIncomeExp.setMonthExp(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMonthExpenditure())));//月支出
//        personalIncomeExp.setIncomeMemo(ctmInterfaceDataBean.getSourceOfIncome());//收入来源说明
        personalIncomeExp.setCertificatesNumber(ctmInterfaceDataBean.getIdno());

        return personalIncomeExp;
    }

    /**
     * 客户的备注信息
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public Material createMaterital(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        Material material = new Material();
        material.setPersonalId(personalId);
        material.setResidentDocMemo(ctmInterfaceDataBean.getResidentDocMemo());//居住证明核查
        material.setResidentApproveMemo(ctmInterfaceDataBean.getResidentApproveMemo());//居住证明
        material.setIncomeDocMemo(ctmInterfaceDataBean.getIncomeDocMemo());//收入证明核查
        material.setIncomeApproveMemo(ctmInterfaceDataBean.getIncomeApproveMemo());//收入证明-审批备注
        material.setCophoneExistind(ctmInterfaceDataBean.getCoPhoneExistInd());//单位电话-调查结果
        material.setCophoneMemo(ctmInterfaceDataBean.getCoPhoneMemo());//单位电话-备注
        material.setCophoneApproveMemo(ctmInterfaceDataBean.getCoPhoneApproveMemo());//单位电话-审批备注
        material.setHomePhoneMemo(ctmInterfaceDataBean.getHomePhoneMemo());//家庭电话-备注
        material.setHomePhoneApproveMemo(ctmInterfaceDataBean.getHomePhoneApproveMemo());//家庭电话-审批备注
        material.setMobileMemo(ctmInterfaceDataBean.getMobileMemo());//本人手机-备注
        material.setMobileApproveMemo(ctmInterfaceDataBean.getMobileApproveMemo());//本人手机-审批备注
        material.setRelativeMemo(ctmInterfaceDataBean.getRelativeMemo());//其他联系人-备注
        material.setInetMemo(ctmInterfaceDataBean.getInetMemo());//系统校验-备注
        material.setRemarks(ctmInterfaceDataBean.getRemarks());//备注
        material.setCertificatesNumber(ctmInterfaceDataBean.getIdno());
        return material;
    }

    /**
     * 创建客户信息对象
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public Personal createPersonal(CtmInterfaceDataBean ctmInterfaceDataBean) {
        Personal personal = new Personal();
        personal.setType(Personal.TYPE.A.getValue());
        personal.setName(ctmInterfaceDataBean.getFullname());//姓名
        //M 男 F女
        personal.setSex(ctmInterfaceDataBean.getSex().equals("M") ? Personal.SexEnum.MAN.getValue() : Personal.SexEnum.WOMEN.getValue());//性别.......M=0=男
        //要判断字段和贷后统一
        if (ctmInterfaceDataBean.getMaritalStatus().trim().equals("S")) {
            personal.setMarital(Personal.MARITAL.UNMARRIED.getValue());//未婚
        } else if (ctmInterfaceDataBean.getMaritalStatus().trim().equals("M")) {
            personal.setMarital(Personal.MARITAL.MARRIED.getValue());//已婚
        } else if (ctmInterfaceDataBean.getMaritalStatus().trim().equals("W")) {//丧偶
            personal.setMarital(Personal.MARITAL.WIDOWHOOD.getValue());//已婚
        } else if (ctmInterfaceDataBean.getMaritalStatus().trim().equals("D")) {//离异
            personal.setMarital(Personal.MARITAL.DIVORCE.getValue());//已婚
        }

        if (ctmInterfaceDataBean.getQualification().trim().equals("M")) {//硕士及以上
            personal.setEducation(Personal.EDUCATION.MASTER.getValue());//教育程度 学历  c.....
        } else if (ctmInterfaceDataBean.getQualification().trim().equals("T")) {//本科
            personal.setEducation(Personal.EDUCATION.UNDERGRADUATE.getValue());//教育程度 学历  c.....
        } else if (ctmInterfaceDataBean.getQualification().trim().equals("C")) {//大专
            personal.setEducation(Personal.EDUCATION.JUNIOR_COLLEGE.getValue());//教育程度 学历  c.....
        } else if (ctmInterfaceDataBean.getQualification().trim().equals("H")) {//高中和中专
            personal.setEducation(Personal.EDUCATION.SENIOR_SCHOOL.getValue());//教育程度 学历  c.....
        } else if (ctmInterfaceDataBean.getQualification().trim().equals("L")) {//初中及以下
            personal.setEducation(Personal.EDUCATION.SENIOR_MIDDLE.getValue());//教育程度 学历  c.....
        }

        if (ctmInterfaceDataBean.getIdType().trim().equals("1")) {//身份证
            personal.setCertificatesType(Personal.CertificatesType.IDCARD.getValue() + "");//证件类型

        } else if (ctmInterfaceDataBean.getIdType().trim().equals("2")) {//护照
            personal.setCertificatesType(Personal.CertificatesType.PASSPORT.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("3")) {//户口薄
            personal.setCertificatesType(Personal.CertificatesType.HOUSEHOLD.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("4")) {//军官证
            personal.setCertificatesType(Personal.CertificatesType.MILITARY.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("5")) {//士兵证
            personal.setCertificatesType(Personal.CertificatesType.SOLDIER_CARD.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("6")) {
            personal.setCertificatesType(Personal.CertificatesType.Civilian_cadres.getValue() + "");//文职干部证
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("7")) {//港澳居民来往内地通行证
            personal.setCertificatesType(Personal.CertificatesType.Hong_Kong.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("8")) {//台湾同胞来往内地通行证
            personal.setCertificatesType(Personal.CertificatesType.TaiWan.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("9")) {//临时身份证
            personal.setCertificatesType(Personal.CertificatesType.Temporary_card.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("A")) {//警官证
            personal.setCertificatesType(Personal.CertificatesType.Police_officer_card.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("B")) {//测试证件
            personal.setCertificatesType(Personal.CertificatesType.Test_certificate.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("C")) {//公司证件
            personal.setCertificatesType(Personal.CertificatesType.Company_documents.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("D")) {//其它证件
            personal.setCertificatesType(Personal.CertificatesType.Other_Card.getValue() + "");//证件类型
        } else if (ctmInterfaceDataBean.getIdType().trim().equals("V")) {//VIP证件
            personal.setCertificatesType(Personal.CertificatesType.VIP_certificate.getValue() + "");//证件类型
        }
        personal.setIdCard(ctmInterfaceDataBean.getIdno());//证件号码
        personal.setCertificatesNumber(ctmInterfaceDataBean.getIdno());//证件号码
        personal.setIdCardExpirydate(stringToDate(ctmInterfaceDataBean.getIdExpiryDate(), null));//身份证到期时间
        personal.setAge(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getAge())));//年龄
        personal.setMobileNo(ctmInterfaceDataBean.getMobileNo());//手机号码
        personal.setEmail(ctmInterfaceDataBean.getEmail());//电子邮箱地址
        personal.setIdCardIssuingAuthority(ctmInterfaceDataBean.getIdIssuer());//身份证发证机关
        String idCardAdddress = ctmInterfaceDataBean.getIdAdr();
        personal.setIdCardAddress(idCardAdddress.replace("|", ""));//身份证地址
        personal.setLocalPhoneNo(ctmInterfaceDataBean.getHomeTelno());//居住地址家庭座机
        personal.setLocalHomeAddress(ctmInterfaceDataBean.getHomeAdr().replace("|", "").trim());//现居住地址
        personal.setLiveMoveTime(stringToDate(ctmInterfaceDataBean.getLivedThereSince(), null));//现居住迁入时间
        personal.setHomeOwnership(ctmInterfaceDataBean.getHomeOwnership());//居住房屋所有权
        personal.setChildrenNumber(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getNoOfDependents())));//子女人数
        personal.setNationality(Objects.isNull(ctmInterfaceDataBean.getNationality().trim()) ? "中国" : ctmInterfaceDataBean.getNationality().trim());//国籍

        if (ctmInterfaceDataBean.getUserField8().trim().equals("01")) {
            personal.setPersonalSource(Personal.PersonalSource.Direct_guest.getValue());//直客
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("02")) {
            personal.setPersonalSource(Personal.PersonalSource.intermediary.getValue());//中介
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("03")) {
            personal.setPersonalSource(Personal.PersonalSource.Website_reservation.getValue());//网站预约
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("04")) {
            personal.setPersonalSource(Personal.PersonalSource.Electric_pin.getValue());//电销
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("05")) {
            personal.setPersonalSource(Personal.PersonalSource.Bank_recommends.getValue());//成都银行推荐
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("06")) {
            personal.setPersonalSource(Personal.PersonalSource.Advertisement.getValue());//广告
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("07")) {
            personal.setPersonalSource(Personal.PersonalSource.Other.getValue());//其他
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("08")) {
            personal.setPersonalSource(Personal.PersonalSource.Short_message.getValue());//成都银行短信
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("09")) {
            personal.setPersonalSource(Personal.PersonalSource.Old_customer.getValue());//老客户
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("10")) {
            personal.setPersonalSource(Personal.PersonalSource.Platform_merchant.getValue());//平台商户
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("11")) {
            personal.setPersonalSource(Personal.PersonalSource.Cooperation_Agency.getValue());//合作机构
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("12")) {
            personal.setPersonalSource(Personal.PersonalSource.commercial_bank.getValue());//商业银行
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("13")) {
            personal.setPersonalSource(Personal.PersonalSource.Transfer_agency.getValue());//转介绍机构
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("14")) {
            personal.setPersonalSource(Personal.PersonalSource.Introduce.getValue());//转介绍个人
        } else if (ctmInterfaceDataBean.getUserField8().trim().equals("15")) {
            personal.setPersonalSource(Personal.PersonalSource.commerce_platform.getValue());//电子商务平台
        }
        personal.setOtherSource(ctmInterfaceDataBean.getUserField11());//其他来源
        personal.setReferrer(ctmInterfaceDataBean.getUserField12());//推介人
        personal.setIntroductionBank(ctmInterfaceDataBean.getUserField13());//推荐支行
        personal.setCompanyCode("0001");
        return personal;
    }

    public AreaCode mergeAreaCodeByAddress(CtmInterfaceDataBean ctmInterfaceDataBean, List<AreaCode> areaCodeList) {
        String jobAddress = ctmInterfaceDataBean.getCompAdr().trim();
        String[] job = jobAddress.split("\\|");
        if (job.length > 2 && !job[1].trim().equals("")) {
            String address = job[1];
            AreaCode areaCode = getAreadCodeByAddress(address, areaCodeList);
            if (Objects.nonNull(areaCode)) {
                logger.info("案件编号：" + ctmInterfaceDataBean.getApplNo() + "城市是：" + areaCode.getAreaName());
                return areaCode;
            }
        }
        String idCardAddress = ctmInterfaceDataBean.getIdAdr();
        if (!idCardAddress.equals("")) {
            String[] idCard = idCardAddress.split("\\|");
            if (idCard.length > 2 && !idCard[1].trim().equals("")) {
                String address = idCard[1].trim();
                AreaCode areaCode = getAreadCodeByAddress(address, areaCodeList);
                return areaCode;
            }
        }
        return null;
    }

    /**
     * 创建案件待分配案件
     *
     * @param ctmInterfaceDataBean
     * @return
     */
    public void mergerCaseInfoDistributed(CtmInterfaceDataBean ctmInterfaceDataBean, SyncDataModel syncDataModel,
                                          Principal principal, Date closeDate, List<AreaCode> areaCodeList) {
        CaseInfoDistributed caseInfoDistributed = new CaseInfoDistributed();
        caseInfoDistributed.setExecutedPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getLastedTerm())));
        AreaCode areaCode = mergeAreaCodeByAddress(ctmInterfaceDataBean, areaCodeList);
        if (Objects.nonNull(areaCode)) {
            caseInfoDistributed.setArea(areaCode);
        }
        caseInfoDistributed.setCaseNumber(ctmInterfaceDataBean.getApplNo());//借据号  申请号
        caseInfoDistributed.setOverdueAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalOverdue())));//逾期总金额
        caseInfoDistributed.setOverdueCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getOverduePrincipal())));//逾期本金
        caseInfoDistributed.setHasPayPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getReturnsNum())));//已还期数
        caseInfoDistributed.setOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDpdDays())));//逾期天数
        caseInfoDistributed.setLatelyPayDate(stringToDate(ctmInterfaceDataBean.getLastPaymentDate(), null));//最近还款日期
        caseInfoDistributed.setLatelyPayAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getLastPaymentAmt())));//最近一次还款金额1
        caseInfoDistributed.setLeftCapital(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainPrin())));//剩余本金
        caseInfoDistributed.setLeftInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainInt())));//剩余利息
        String dueNum = initBigDecimal(ctmInterfaceDataBean.getDueNum());
        String duePeriods = "M".concat(dueNum);
        caseInfoDistributed.setOverduePeriods(Integer.valueOf(dueNum));//逾期期数
        caseInfoDistributed.setPayStatus(duePeriods);//逾期期数
        //产品名称
        String productName = ctmInterfaceDataBean.getProductName();
        if (Objects.isNull(productName) || "".equals(productName.trim())) {
            productName = "未知";
        }
        caseInfoDistributed.setProduct(syncDataModel.getExistPCMap().get(productName));//案件关联产品信息
        if (syncDataModel.getExistPCMap().containsKey(productName)) {
            caseInfoDistributed.setProductType(syncDataModel.getExistPCMap().get(productName).getProductSeries().getId());
        }
        caseInfoDistributed.setProductName(productName);
        //对客户信息的添加
        caseInfoDistributed.setPersonalInfo(syncDataModel.getExistPMap().get(ctmInterfaceDataBean.getIdno()));//案件对应的客户信息
        caseInfoDistributed.setCertificatesNumber(ctmInterfaceDataBean.getIdno());
        caseInfoDistributed.setCaseFollowInTime(new Date());//案件的流入时间
        caseInfoDistributed.setLeaveCaseFlag(0);//留案标志
        caseInfoDistributed.setCaseType(CaseInfo.CaseType.DISTRIBUTE.getValue());
        caseInfoDistributed.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
        caseInfoDistributed.setHandUpFlag(CaseInfo.HandUpFlag.NO_HANG.getValue());
        caseInfoDistributed.setCaseMark(CaseInfo.Color.NO_COLOR.getValue()); //案件颜色标记
        caseInfoDistributed.setRecoverRemark(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()); //未回收
        caseInfoDistributed.setAssistFlag(0);
        caseInfoDistributed.setCompanyCode("0001");
        caseInfoDistributed.setCloseDate(closeDate);
        if (Objects.nonNull(principal)) {
            caseInfoDistributed.setPrincipalId(principal);
        }

        caseInfoDistributed.setMaxOverdueDays(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getMaxdelqdayCnt())));//逾期最大天数
        caseInfoDistributed.setLatesDateReturn(stringToDate(ctmInterfaceDataBean.getLatestDateReturn(), null));//最近一次应还日期
        caseInfoDistributed.setLeftPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getRemPeriodsNum())));//剩余期数
        caseInfoDistributed.setUnpaidPrincipal(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPrincipal())));//未尝还本金
        caseInfoDistributed.setUnpaidInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidInterest())));//未偿还利息
        caseInfoDistributed.setUnpaidFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidPnltInt())));//未尝还罚息
        caseInfoDistributed.setUnpaidOtherInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtInt())));//未偿还其它利息
        caseInfoDistributed.setUnpaidMthFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidMthFee())));//未尝还管理费
        caseInfoDistributed.setUnpaidOtherFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidOtFee())));//未尝还其他费用
        caseInfoDistributed.setUnpaidLpc(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnpaidLpc())));//未尝还滞纳金
//        caseInfoDistributed.setCurrPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getCurrPnltInt())));//当前未结罚息复利
        caseInfoDistributed.setPnltInterest(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getUnbillInt())));
        caseInfoDistributed.setPnltFine(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getTotalAcru())));
        caseInfoDistributed.setRemainFee(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getRemainFee())));//剩余月服务费
        caseInfoDistributed.setOverdueAccountNumber(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getDueApplyCnt())));//逾期账户数
        caseInfoDistributed.setInColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getInColCnt())));//內催次数
        caseInfoDistributed.setOutColcnt(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getOutColCnt())));//委外次数
        caseInfoDistributed.setSettleAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getSettleAmt())));//结清金额
        Date date = getWriteCloseDate(ctmInterfaceDataBean);
        if (Objects.nonNull(date)) {
            caseInfoDistributed.setSettleDate(date);
        }
        caseInfoDistributed.setAccountBalance(caseInfoDistributed.getUnpaidPrincipal().add(caseInfoDistributed.getLeftCapital()));//账户余额
        syncDataModel.getCaseInfoDistributedInsertMap().put(ctmInterfaceDataBean.getApplNo(), caseInfoDistributed);
    }


    public AreaCode getAreadCodeByAddress(String address, List<AreaCode> areaCodeList) {
        if (address.trim().equals("")) {
            return null;
        } else {
            for (AreaCode areaCode : areaCodeList) {
                String areaName = areaCode.getAreaName();
                if (areaName.contains(address) && areaCode.getTreePath().split("/").length == 2) {
                    return areaCode;
                }
            }
        }
        return null;
    }

    /**
     * 做非空判断
     *
     * @param mgs
     * @return
     */
    public String initBigDecimal(String mgs) {
        if (mgs == null || mgs.trim().equals("")) {
            mgs = "0";
        }
        return mgs;
    }

    /**
     * 将字符串转Date
     *
     * @param str
     * @return
     */
    public Date stringToDate(String str, String format) {
        if (format == null) {
            format = "dd/MM/yyyy HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        if (!str.trim().equals("")) {
            try {
                date = formatter.parse(str);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return date;
    }

    /**
     * 创建客户联系人信息对象
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public List<PersonalContact> createPersonalContact(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        List<PersonalContact> list = new ArrayList<>();
        List<Contact> contactList = ctmInterfaceDataBean.getContacts();
        int index = 0;
        Personal personal = createPersonal(ctmInterfaceDataBean);
        PersonalContact pContact = new PersonalContact();
        pContact.setId(ShortUUID.uuid());
        pContact.setPersonalId(personalId);//客户id
        pContact.setName(personal.getName());
        pContact.setPhone(personal.getMobileNo());
        pContact.setAddress(personal.getLocalHomeAddress());//联系人的现居住地址
        pContact.setRelation(PersonalContact.relation.SELF.getValue());//本人
        pContact.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("-").concat(String.valueOf(index)));
        pContact.setSource(Constants.DataSource.IMPORT.getValue());
        list.add(pContact);
        for (Contact contact : contactList) {
            index++;
            PersonalContact personalContact = new PersonalContact();
            personalContact.setId(ShortUUID.uuid());
            personalContact.setPersonalId(personalId);//客户id
            //关系：145-本人，146-配偶，147-父母，148-子女，149-亲属，150-同事，151-朋友，152-其他，153-亲兄弟姐妹，154-单位
            if (contact.getRelRelationToCh().trim().equals("A")) {
                personalContact.setRelation(PersonalContact.relation.COLLEUAGUE.getValue());//同事
            } else if (contact.getRelRelationToCh().trim().equals("B") || contact.getRelRelationToCh().trim().equals("S")) {
                personalContact.setRelation(PersonalContact.relation.RELATIVES.getValue());//亲属
            } else if (contact.getRelRelationToCh().trim().equals("C")) {
                personalContact.setRelation(PersonalContact.relation.CHILD.getValue());//子女
            } else if (contact.getRelRelationToCh().trim().equals("F")) {
                personalContact.setRelation(PersonalContact.relation.FRIEND.getValue());//朋友
            } else if (contact.getRelRelationToCh().trim().equals("H") || contact.getRelRelationToCh().trim().equals("W")) {
                personalContact.setRelation(PersonalContact.relation.SPOUSE.getValue());//配偶
            } else if (contact.getRelRelationToCh().trim().equals("P")) {
                personalContact.setRelation(PersonalContact.relation.PARENT.getValue());//父母
            } else if (contact.getRelRelationToCh().trim().equals("R")) {
                personalContact.setRelation(PersonalContact.relation.OTHER.getValue());//其他
            } else if (contact.getRelRelationToCh().trim().equals("L")) {
                personalContact.setRelation(PersonalContact.relation.SELF.getValue());//本人
            }
            personalContact.setName(contact.getRelName());//联系人姓名
            personalContact.setIdCard(contact.getRelIdno());//身份证号码
            personalContact.setPhone(contact.getRelMobile());//手机号码
            personalContact.setAddress(contact.getRelhomeAdr().replace("|", "").trim());//联系人的现居住地址
            personalContact.setMobile(contact.getRelTelno());//联系人单位电话
            personalContact.setEmployer(contact.getRelCompName());//联系人工作单位
            personalContact.setWorkPhone(contact.getRelCompTelno());//联系人单位电话
            personalContact.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("-").concat(String.valueOf(index)));
            personalContact.setSource(Constants.DataSource.IMPORT.getValue());
            list.add(personalContact);
        }
        return list;
    }

    /**
     * 创建客户房产信息对象
     *
     * @param ctmInterfaceDataBean
     * @param personalId
     * @return
     */
    public List<PersonalProperty> createPersonalProperty(CtmInterfaceDataBean ctmInterfaceDataBean, String personalId) {
        List<PersonalProperty> list = new ArrayList<>();
        PersonalProperty personalProperty = new PersonalProperty();
        personalProperty.setPersonalId(personalId);
        personalProperty.setAddress(ctmInterfaceDataBean.getHoseAdr().replace("|", "").trim());
        personalProperty.setHousePurchasePrice(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getHousePurchasePrice())));//房屋购置价
        personalProperty.setHouseAssAmt(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getHouseAssAmt())));//房屋评估价
        personalProperty.setFirstPayment(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getFirstPayment())));//首付金额
        personalProperty.setRepaymentPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getMortgageRepaymentPeriod())));//房贷已还款期数
        personalProperty.setMonthPaymentAmount(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMortgageMonthlyReturn())));//房贷月均还款额
        personalProperty.setOver(new BigDecimal(initBigDecimal(ctmInterfaceDataBean.getMortgageBalance())));//房贷余额
        personalProperty.setTotalPeriods(Integer.valueOf(initBigDecimal(ctmInterfaceDataBean.getTotalMortgagePeriod())));//房贷总期数
        personalProperty.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("1"));
        list.add(personalProperty);
        PersonalProperty personalProperty1 = new PersonalProperty();
        personalProperty1.setAddress(ctmInterfaceDataBean.getHoseAdr1().replace("|", "").trim());
        personalProperty1.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("2"));
        list.add(personalProperty1);
        PersonalProperty personalProperty2 = new PersonalProperty();
        personalProperty2.setAddress(ctmInterfaceDataBean.getHoseAdr2().replace("|", "").trim());
        personalProperty2.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("3"));
        list.add(personalProperty2);
        PersonalProperty personalProperty3 = new PersonalProperty();
        personalProperty3.setAddress(ctmInterfaceDataBean.getHoseAdr3().replace("|", "").trim());
        personalProperty3.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("4"));
        list.add(personalProperty3);
        PersonalProperty personalProperty4 = new PersonalProperty();
        personalProperty4.setAddress(ctmInterfaceDataBean.getHoseAdr4().replace("|", "").trim());
        personalProperty4.setCertificatesNumber(ctmInterfaceDataBean.getIdno().concat("5"));
        list.add(personalProperty4);
        return list;
    }


}
