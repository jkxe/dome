package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.business.model.out.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.util.ZWDateUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service("SynHYDataSaveService")
public class SyncHYDataSaveService {

    final Logger log = LoggerFactory.getLogger(SyncHYDataSaveService.class);

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    private SynDataSaveService synDataSaveService;

    @Autowired
    BatchDataCacheService batchDataCacheService;

    @Autowired
    CaseInfoBatchRepository caseInfoBatchRepository;

    @Autowired
    OverdueDetailBatchRepository overdueDetailBatchRepository;

    @Autowired
    CaseInfoDistributedBatchRepository caseInfoDistributedBatchRepository;

    @Autowired
    PersonalContactRepository personalContactRepository;

    @Autowired
    PersonalAddressRepository personalAddressRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    PersonalJobRepository personalJobRepository;
    @Autowired
    private EntityManager entityManager;

    //同步案件数据到内存
    private void initialCaseInfoData() {
        SyncDataModel.caseNumberTemp = 0;
        //循环数据库催收中数据 对比更新数据库中案件状态，并且同步案件到内存中
        //入催案件
        batchDataCacheService.caseInfoMap_delete();
        batchDataCacheService.caseInfoMapByUser_delete();
        List<Object[]> caseInfoList = caseInfoRepository.findAllKey();
        for (int i = 0; i < caseInfoList.size(); i++) {
            Object[] caseInfo = caseInfoList.get(i);
            batchDataCacheService.caseInfoMap_put(caseInfo[1].toString(), caseInfo);
            batchDataCacheService.caseInfoMapByUser_put(caseInfo[2].toString(), caseInfo);
        }

        //已结清案件
        batchDataCacheService.overCaseInfoMap_delete();
        List<Object[]> overCaseInfoList = caseInfoRepository.findAllKeyOver();
        for (int i = 0; i < overCaseInfoList.size(); i++) {
            Object[] caseInfo = overCaseInfoList.get(i);
            List<Object[]> caseInfoList1 = batchDataCacheService.overCaseInfoMap_get(caseInfo[1].toString());
            if (caseInfoList1 == null) {
                caseInfoList1 = new ArrayList<>();
                batchDataCacheService.overCaseInfoMap_put(caseInfo[1].toString(), caseInfoList1);
            }
            caseInfoList1.add(caseInfo);
        }

        //分配池案件
        batchDataCacheService.caseInfoDistributedMap_delete();
        batchDataCacheService.caseInfoDistributedMapByUser_delete();
        List<Object[]> caseInfoDistributedList = caseInfoDistributedRepository.findAllKey();
        for (int i = 0; i < caseInfoDistributedList.size(); i++) {
            Object[] caseInfoDistributed = caseInfoDistributedList.get(i);
            batchDataCacheService.caseInfoDistributedMap_put(caseInfoDistributed[1].toString(), caseInfoDistributed);
            batchDataCacheService.caseInfoDistributedMapByUser_put(caseInfoDistributed[2].toString(), caseInfoDistributed);
        }
    }

    private void setCaseInfo(CaseInfoDistributed caseInfoDistributed, CaseInfo caseInfo) {
        BeanUtils.copyProperties(caseInfoDistributed, caseInfo);
        caseInfo.setId(null);
        caseInfo.setCaseFollowInTime(ZWDateUtil.getNowDateTime()); //案件流入时间
        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue()); //催收状态-待分配
        caseInfo.setLeaveCaseFlag(CaseInfo.leaveCaseFlagEnum.NO_LEAVE.getValue()); //留案标识默认-非留案
        caseInfo.setAssistFlag(CaseInfo.AssistFlag.NO_ASSIST.getValue());
        caseInfo.setCaseMark(CaseInfo.Color.NO_COLOR.getValue());//打标标记
        caseInfo.setFollowUpNum(0);//流转次数
    }

    /**
     * HY导入案件
     *
     * @param hyInterfaceDataCaseInfoBeanList
     * @return
     */
    @Async
    public Future<String> importHyCaseInfo(List<HYOverdueDetailBean> hyInterfaceDataCaseInfoBeanList, String batch, Date closeDate, TaskBatchImportLog taskBatchImportLog) {

        //循环数据库催收中数据 对比更新数据库中案件状态，并且同步案件到内存中
        Set<String> overdueInvoiceIdList = hyInterfaceDataCaseInfoBeanList.stream()
                .map(HYOverdueDetailBean::getLoanInvoiceId)
                .collect(Collectors.toSet());

        List<String> caseInfoInvoiceIdList = caseInfoRepository.findAllLoanInvoiceNumber();
        List<String>  caseInfoDistributedInvoiceIdList = caseInfoDistributedRepository.findAllLoanInvoiceNumber();

        caseInfoInvoiceIdList.removeAll(overdueInvoiceIdList);
        caseInfoDistributedInvoiceIdList.removeAll(overdueInvoiceIdList);

        if (!caseInfoInvoiceIdList.isEmpty()) {
            TypedQuery<CaseInfo> query = entityManager.createQuery("select a from CaseInfo a where a.loanInvoiceNumber in ?1", CaseInfo.class);
            query.setParameter(1, caseInfoInvoiceIdList);
            List<CaseInfo> resultList = query.getResultList();

            List<CaseInfo> needSaveCaseOverCaseInfo = new ArrayList<>();
            for (CaseInfo caseInfo : resultList) {
                caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.CASE_OVER.getValue());
                caseInfo.setPayStatus("M0");//催收状态
                caseInfo.setOverduePeriods(0);//逾期期数
                caseInfo.setOverdueDays(0);//逾期天数
                caseInfo.setSettleDate(new Date());//结清时间
                needSaveCaseOverCaseInfo.add(caseInfo);
            }
            caseInfoRepository.save(needSaveCaseOverCaseInfo);
        }


        if (!caseInfoDistributedInvoiceIdList.isEmpty()) {
            List<CaseInfo> needSaveCaseOverCaseInfoDistributed = new ArrayList<>();
            List<CaseInfoDistributed> deleteDistributedList = new ArrayList<>();

            TypedQuery<CaseInfoDistributed> distributedTypedQuery = entityManager.createQuery("select a from CaseInfoDistributed a where a.loanInvoiceNumber in ?1", CaseInfoDistributed.class);
            distributedTypedQuery.setParameter(1, caseInfoDistributedInvoiceIdList);
            List<CaseInfoDistributed> queryResultList = distributedTypedQuery.getResultList();

            for (CaseInfoDistributed caseInfoDistributed : queryResultList) {
                //如果贷后分配池存在 进内存结清案件
                CaseInfo caseInfo = new CaseInfo();
                setCaseInfo(caseInfoDistributed, caseInfo);
                caseInfo.setCasePoolType(CaseInfo.CasePoolType.INNER.getValue());
                caseInfo.setExceptionFlag(0);// 修改异常状态
                caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.CASE_OVER.getValue());
                caseInfo.setSettleDate(new Date());//结清时间
                caseInfo.setPayStatus("M0");//催收状态
                caseInfo.setOverduePeriods(0);//逾期期数
                caseInfo.setOverdueDays(0);//逾期天数
                caseInfo.setId(null);
                needSaveCaseOverCaseInfoDistributed.add(caseInfo);
                deleteDistributedList.add(caseInfoDistributed);
            }

            caseInfoRepository.save(needSaveCaseOverCaseInfoDistributed);
            caseInfoDistributedRepository.delete(deleteDistributedList);
        }
        initialCaseInfoData();
        //循环核心推送数据 对比更新数据进库
        batchHandleOverdueDetail(hyInterfaceDataCaseInfoBeanList, batch, closeDate, 0, taskBatchImportLog);
        return new AsyncResult<>("客户逾期明细信息-执行完毕");
    }

    /**
     * HY导入客户明细信息
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerDetail(List<HYOverdueCustomerDetailBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerDetail(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户明细信息-执行完毕");
    }

    /**
     * HY导入客户开户信息
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerAccount(List<HYOverdueCustomerAccountBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerAccount(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户开户信息-执行完毕");
    }

    /**
     * HY导入客户文本附件
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerFileAttach(List<HYOverdueCustomerFileAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerFileAttach(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户文本附件-执行完毕");
    }

    /**
     * HY导入客户影像文件
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerImgFile(List<HYOverdueCustomerImgAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerImgFile(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户影像文件-执行完毕");
    }

    /**
     * HY导入客户社交资料
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerSocialPlat(List<HYOverdueCustomerSocialPlatBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerSocialPlat(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户社交资料-执行完毕");
    }

    /**
     * HY导入客户联系人
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHyCustomerRelation(List<HYOverdueCustomerRelationBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerRelation(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户联系人-执行完毕");
    }

    /**
     * HY导入客户资产经营征信
     *
     * @param dataList
     * @return
     */
    @Async
    public Future<String> importHYCustomerAstOperCrdt(List<HYOverdueCustomerAstOperCrdtBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        batchHandleCustomerAstOperCrdt(dataList, handleIndex, taskBatchImportLog);
        return new AsyncResult<>("客户资产经营征信-执行完毕");
    }


    /**
     * HY分批处理逾期明细
     *
     * @param dataList
     * @return
     */
    private void batchHandleOverdueDetail(List<HYOverdueDetailBean> dataList, String batch, Date closeDate, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        try {
            int perHandleCount = SyncDataModel.OVERDUE_COUNT;
            if (dataList.size() <= perHandleCount && dataList.size() > 0) {
                synDataSaveService.hyProcessSynDataCaseInfolimt(dataList, batch, closeDate, handleIndex, taskBatchImportLog);
            } else {
                List<HYOverdueDetailBean> perDataList = new ArrayList<>();
                perDataList.addAll(dataList.subList(0, perHandleCount));
                synDataSaveService.hyProcessSynDataCaseInfolimt(perDataList, batch, closeDate, handleIndex, taskBatchImportLog);
                dataList = dataList.subList(perHandleCount, dataList.size());
                handleIndex++;
                batchHandleOverdueDetail(dataList, batch, closeDate, handleIndex, taskBatchImportLog);
            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * HY分批处理客户明细
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerDetail(List<HYOverdueCustomerDetailBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            synDataSaveService.hyProcessSynDataCustomerDetaillimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerDetailBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            synDataSaveService.hyProcessSynDataCustomerDetaillimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerDetail(dataList, handleIndex, taskBatchImportLog);
        }
    }

    @Autowired
    SyncSingleSaveService syncSingleSaveService;

    /**
     * HY分批处理客户开户信息
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerAccount(List<HYOverdueCustomerAccountBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerAccountlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerAccountBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerAccountlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerAccount(dataList, handleIndex, taskBatchImportLog);
        }

    }

    /**
     * HY分批处理客户文本附近明细
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerFileAttach(List<HYOverdueCustomerFileAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerFileAttachlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerFileAttachBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerFileAttachlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerFileAttach(dataList, handleIndex, taskBatchImportLog);
        }

    }

    /**
     * HY分批处理客户影像文件
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerImgFile(List<HYOverdueCustomerImgAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerImgAttachlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerImgAttachBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerImgAttachlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerImgFile(dataList, handleIndex, taskBatchImportLog);
        }

    }

    /**
     * HY分批处理客户社交资料
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerSocialPlat(List<HYOverdueCustomerSocialPlatBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerSocialPlatlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerSocialPlatBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerSocialPlatlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerSocialPlat(dataList, handleIndex, taskBatchImportLog);
        }

    }

    /**
     * HY分批处理客户联系人
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerRelation(List<HYOverdueCustomerRelationBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerRelationlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerRelationBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerRelationlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerRelation(dataList, handleIndex, taskBatchImportLog);
        }
    }


    /**
     * HY分批处理客户资产经营征信
     *
     * @param dataList
     * @return
     */
    public void batchHandleCustomerAstOperCrdt(List<HYOverdueCustomerAstOperCrdtBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        //定义一批处理多少条案件数据
        int perHandleCount = SyncDataModel.perHandleCount;
        if (dataList.size() <= perHandleCount && !dataList.isEmpty()) {
            syncSingleSaveService.hyProcessSynDataCustomerAstOperCrdtlimt(dataList, handleIndex, taskBatchImportLog);
        } else {
            List<HYOverdueCustomerAstOperCrdtBean> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, perHandleCount));
            syncSingleSaveService.hyProcessSynDataCustomerAstOperCrdtlimt(perDataList, handleIndex, taskBatchImportLog);
            dataList = dataList.subList(perHandleCount, dataList.size());
            handleIndex++;
            batchHandleCustomerAstOperCrdt(dataList, handleIndex, taskBatchImportLog);
        }

    }


    /**
     * 异步执行逾期案件分任务
     */
    @Async
    public Future<String> doSyncSaveCaseInfo(SyncDataModel syncDataModel, TaskBatchImportLog taskBatchImportLog) {
        //案件信息(新增）
        log.info("逾期案件-逾期案件子任务开始");
        if (!syncDataModel.getCaseInfoInsertMap().isEmpty()) {
            List<CaseInfo> valueList = new ArrayList<>(syncDataModel.getCaseInfoInsertMap().values());
            try {
                caseInfoRepository.save(valueList);
                taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + valueList.size());
            } catch (Exception ex) {
                for (CaseInfo value : valueList) {
                    try {
                        caseInfoBatchRepository.insert(value);
                        taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + 1);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        //保存导入异常数据
                        ExceptionData exceptionData = ExceptionData.createInstance(
                                ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                                value.getLoanInvoiceNumber(),
                                JSONObject.toJSONString(value),
                                ExceptionData.EXCEPTION_CODE_INTODB, e);
                        syncDataModel.getExceptionDataInsertMap().put(
                                exceptionData.getExceptionCode() +
                                        exceptionData.getDataType() +
                                        exceptionData.getDataId(), exceptionData);
                    }
                }
            }
        }
        //案件信息(更新)
        if (!syncDataModel.getCaseInfoUpdateMap().isEmpty()) {
            List<CaseInfo> valueList = new ArrayList<>(syncDataModel.getCaseInfoUpdateMap().values());
            for(CaseInfo value:valueList){
                try {
                    caseInfoBatchRepository.update(value);
                    taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount()+1);
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                    //保存导入异常数据
                    ExceptionData exceptionData=ExceptionData.createInstance(
                            ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                            value.getLoanInvoiceNumber(),
                            JSONObject.toJSONString(value),
                            ExceptionData.EXCEPTION_CODE_INTODB, e);
                    syncDataModel.getExceptionDataInsertMap().put(
                            exceptionData.getExceptionCode()+
                                    exceptionData.getDataType()+
                                    exceptionData.getDataId(), exceptionData);
                }
            }
        }
        log.info("逾期案件-逾期案件子任务结束");
        return new AsyncResult<>("保存逾期案件-执行完毕");
    }

    /**
     * 异步执行分配池案件分任务
     */
    @Async
    public Future<String> doSyncSaveCaseInfoDistribute(SyncDataModel syncDataModel, TaskBatchImportLog taskBatchImportLog) {
        //待分配案件(新增)
        log.info("逾期案件-分配池案件子任务开始");
        if (!syncDataModel.getCaseInfoDistributedInsertMap().isEmpty()) {
            List<CaseInfoDistributed> valueList = new ArrayList<>(syncDataModel.getCaseInfoDistributedInsertMap().values());
            try {
                caseInfoDistributedRepository.save(valueList);
                taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + valueList.size());
            } catch (Exception ex) {
                for (CaseInfoDistributed value : valueList) {
                    try {
                        caseInfoDistributedBatchRepository.insert(value);
                        taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount() + 1);
                    } catch (Exception e) {
                        //保存导入异常数据
                        ExceptionData exceptionData = ExceptionData.createInstance(
                                ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                                value.getLoanInvoiceNumber(),
                                JSONObject.toJSONString(value),
                                ExceptionData.EXCEPTION_CODE_INTODB, e);
                        syncDataModel.getExceptionDataInsertMap().put(
                                exceptionData.getExceptionCode() +
                                        exceptionData.getDataType() +
                                        exceptionData.getDataId(), exceptionData);
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

        //待分配案件(更新)
        if (!syncDataModel.getCaseInfoDistributedUdpateMap().isEmpty()) {
            List<CaseInfoDistributed> valueList = new ArrayList<>(syncDataModel.getCaseInfoDistributedUdpateMap().values());
            for(CaseInfoDistributed value:valueList){
                try {
                    caseInfoDistributedBatchRepository.update(value);
                    taskBatchImportLog.setSuccessCount(taskBatchImportLog.getSuccessCount()+1);
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                    //保存导入异常数据
                    ExceptionData exceptionData=ExceptionData.createInstance(
                            ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                            value.getLoanInvoiceNumber(),
                            JSONObject.toJSONString(value),
                            ExceptionData.EXCEPTION_CODE_INTODB, e);
                    syncDataModel.getExceptionDataInsertMap().put(
                            exceptionData.getExceptionCode()+
                                    exceptionData.getDataType()+
                                    exceptionData.getDataId(), exceptionData);
                }
            }
        }
        log.info("逾期案件-分配池案件子任务结束");
        return new AsyncResult<>("保存分配池案件信息-执行完毕");
    }

    @Autowired
    private OverdueDetailRepository overdueDetailRepository;

    /**
     * 异步执行逾期明细分任务
     */
    @Async
    public Future<String> doSyncSaveOverdueDetail(SyncDataModel syncDataModel) {
        log.info("逾期案件-逾期明细子任务开始");
        List<OverdueDetail> valueList = new ArrayList<>(syncDataModel.getOverdueDetailInsertMap().values());
        try {
            overdueDetailRepository.save(valueList);
        } catch (Exception ex) {
            log.error(ex.toString());
            if (!valueList.isEmpty()) {
                for (OverdueDetail overdueDetail : valueList) {
                    overdueDetailBatchRepository.insert(overdueDetail);
                }
            }
            throw ex;
        }
        log.info("逾期案件-逾期明细子任务结束");
        return new AsyncResult<>("保存逾期明细-执行完毕");
    }

    /**
     * 异步执行客户明细分任务
     */
    @Async
    public Future<String> doSyncSaveCustomerDetail(SyncDataModel syncDataModel) {
        log.info("客户明细-客户明细子任务开始");
        //客户明细信息(新增)
        if (!syncDataModel.getPersonalInsertMap().isEmpty()) {
            List<Personal> valueList = new ArrayList<>(syncDataModel.getPersonalInsertMap().values());
            personalRepository.save(valueList);
        }
        //客户明细信息(更新)
        if (!syncDataModel.getPersonalUpdateMap().isEmpty()) {
            List<Personal> valueList = new ArrayList<>(syncDataModel.getPersonalUpdateMap().values());
            personalRepository.save(valueList);
        }
        log.info("客户明细-客户明细子任务结束");
        return new AsyncResult<>("保存客户明细-执行完毕");
    }

    /**
     * 异步执行客户工作分任务
     */
    @Async
    public Future<String> doSyncSaveCustomerJob(SyncDataModel syncDataModel) {
        //客户工作信息(新增)
        log.info("客户明细-客户工作信息子任务开始");
        if (!syncDataModel.getPersonalJobInsertMap().isEmpty()) {
            List<PersonalJob> valueList = new ArrayList<>(syncDataModel.getPersonalJobInsertMap().values());
            personalJobRepository.save(valueList);
        }
        //客户工作信息(更新)
        if (!syncDataModel.getPersonalJobUpdateMap().isEmpty()) {
            List<PersonalJob> valueList = new ArrayList<>(syncDataModel.getPersonalJobUpdateMap().values());
            personalJobRepository.save(valueList);
        }
        log.info("客户明细-客户工作信息子任务结束");
        return new AsyncResult<>("保存客户工作-执行完毕");
    }

    /**
     * 异步执行客户地址分任务
     */
    @Async
    public Future<String> doSyncSaveCustomerAddress(SyncDataModel syncDataModel) {
        //客户地址信息(新增)
        log.info("客户明细-客户地址信息子任务开始");
        if (!syncDataModel.getPersonalAddressInsertMap().isEmpty()) {
            List<PersonalAddress> valueList = new ArrayList<>(syncDataModel.getPersonalAddressInsertMap().values());
            personalAddressRepository.save(valueList);
        }
        //客户地址信息(更新)
        if (!syncDataModel.getPersonalAddressUpdateMap().isEmpty()) {
            List<PersonalAddress> valueList = new ArrayList<>(syncDataModel.getPersonalAddressUpdateMap().values());
            personalAddressRepository.save(valueList);
        }
        log.info("客户明细-客户地址信息子任务结束");
        return new AsyncResult<>("保存客户地址-执行完毕");
    }

    /**
     * 异步执行客户关系人分任务
     */
    @Async
    public Future<String> doSyncSaveCustomerRelation(SyncDataModel syncDataModel) {
        //客户联系人信息(新增)
        log.info("客户明细-客户联系人子任务开始");
        if (!syncDataModel.getPersonalContactInsertMap().isEmpty()) {
            List<PersonalContact> valueList = new ArrayList<>(syncDataModel.getPersonalContactInsertMap().values());
            personalContactRepository.save(valueList);
        }
        //客户联系人信息(更新)
        if (!syncDataModel.getPersonalContactUpdateMap().isEmpty()) {
            List<PersonalContact> valueList = new ArrayList<>(syncDataModel.getPersonalContactUpdateMap().values());
            personalContactRepository.save(valueList);
        }
        log.info("客户明细-客户联系人子任务结束");
        return new AsyncResult<>("保存客户联系人-执行完毕");
    }

    @Async
    public Future<Boolean> savePersonalContact() {
        Collection<PersonalContact> personalContacts = BatchDataCacheService.personalContactCacheGetAll().values();
        int totalBatch = DataSyncUtil.calculateTotalBatch(personalContacts.size(), SyncDataModel.perHandleCount);
        CompletableFuture[] futureList = new CompletableFuture[totalBatch];
        for (int batchIndex = 1; batchIndex <= totalBatch; batchIndex++) {
            List<PersonalContact> sublist = DataSyncUtil.sublist(new ArrayList<>(personalContacts), SyncDataModel.perHandleCount, batchIndex, totalBatch);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> personalContactRepository.save(sublist));
            futureList[batchIndex - 1] = future;
        }
        CompletableFuture<Void> future = CompletableFuture.allOf(futureList);
        try {
            future.get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("客户联系人-客户联系人保存子任务结束");
        return new AsyncResult<>(true);
    }
}
