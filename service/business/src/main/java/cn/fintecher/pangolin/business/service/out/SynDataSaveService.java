package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.business.model.out.HYOverdueCustomerDetailBean;
import cn.fintecher.pangolin.business.model.out.HYOverdueDetailBean;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.SyncDataService;
import cn.fintecher.pangolin.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service("SynDataSaveService")
public class SynDataSaveService {

    final Logger log = LoggerFactory.getLogger(SynDataSaveService.class);

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private SyncHYDataSaveService syncHYDataSaveService;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    PersonalJobRepository personalJobRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    PersonalIncomeExpRepository personalIncomeExpRepository;

    @Autowired
    PersonalBankRepository personalBankRepository;

    @Autowired
    PersonalCarRepository personalCarRepository;

    @Autowired
    PersonalContactRepository personalContactRepository;

    @Autowired
    PersonalAddressRepository personalAddressRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSeriesRepository productSeriesRepository;

    @Autowired
    PersonalPropertyRepository personalPropertyRepository;

    @Autowired
    PayPlanRepository payPlanRepository;

    @Autowired
    OrderRepaymentPlanRepository orderRepaymentPlanRepository;

    @Autowired
    WriteOffDetailsRepository writeOffDetailsRepository;

    @Autowired
    CommodityRepository commodityRepository;

    @Autowired
    CaseFileRepository caseFileRepository;

    @Autowired
    PersonalImgFileRepository personalImgFileRepository;

    @Autowired
    PersonalSocialPlatRepository personalSocialPlatRepository;

    @Autowired
    PersonalAstOperCrdtRepository personalAstOperCrdtRepository;

    @Autowired
    OverdueDetailRepository overdueDetailRepository;

    @Autowired
    ExceptionDataRepository exceptionDataRepository;

    /**
     * 逾期明细数据批量数据处理
     * @param dataList
     */
    public void hyProcessSynDataCaseInfolimt(List<HYOverdueDetailBean> dataList, String batch, Date closeDate, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("逾期明细信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYOverdueDetailTaskSyns(dataList, batch, syncDataModel,closeDate, handleIndex,taskBatchImportLog);
        log.info("逾期明细信息第"+handleIndex+"批有"+dataList.size()+"条数据解析合并共债处理完成..........");

        try {
            //开始保存数据
            //客户明细信息(新增)
            if (!syncDataModel.getPersonalInsertMap().isEmpty()) {
                List<Personal> valueList = new ArrayList<>(syncDataModel.getPersonalInsertMap().values());
                personalRepository.save(valueList);
            }

            syncHYDataSaveService.doSyncSaveOverdueDetail(syncDataModel);
            Future<String> stringFuture = syncHYDataSaveService.doSyncSaveCaseInfoDistribute(syncDataModel, taskBatchImportLog);
            Future<String> stringFuture1 = syncHYDataSaveService.doSyncSaveCaseInfo(syncDataModel, taskBatchImportLog);

            do {
                Thread.sleep(1000);
            } while (!stringFuture.isDone() || !stringFuture1.isDone());

            log.info("逾期明细信息第" + handleIndex + "批有" + dataList.size() + "条数据保存数据库成功..........");
        } catch (Exception e) {
            //保存导入异常数据
            ExceptionData exceptionData = ExceptionData.createInstance(
                    ExceptionData.DataType.OVERDUE_DETAIL.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode() +
                            exceptionData.getDataType() +
                            exceptionData.getDataId(), exceptionData);
            log.error("逾期明细信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }

        //异常数据信息(新增）
        if (!syncDataModel.getExceptionDataInsertMap().isEmpty()) {
            List<ExceptionData> valueList = new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }

    /**
     * 客户明细信息批量数据处理
     *
     * @param dataList
     */
    public void hyProcessSynDataCustomerDetaillimt(List<HYOverdueCustomerDetailBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog) {
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户明细信息第" + handleIndex + "批有" + dataList.size() + "条数据等待处理完成..........");
        syncDataService.doHYCustomerDetailTaskSyns(dataList, syncDataModel, handleIndex);
        log.info("客户明细信息第" + handleIndex + "批有" + dataList.size() + "条数据解析完成..........");
        try {
            Future<String> result1=null,result2=null,result3=null;

            //开始保存数据
            result1=syncHYDataSaveService.doSyncSaveCustomerDetail(syncDataModel);
            result2=syncHYDataSaveService.doSyncSaveCustomerJob(syncDataModel);
            result3=syncHYDataSaveService.doSyncSaveCustomerAddress(syncDataModel);
            while (true){
                if(result1.isDone()){
                    break;
                }
                Thread.sleep(500);
            }

            log.info("客户明细信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData = ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_DETAIL.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode() +
                            exceptionData.getDataType() +
                            exceptionData.getDataId(), exceptionData);

            log.error("客户明细信息第" + handleIndex + "批有" + dataList.size() + "条数据保存数据库失败.........." + ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if (!syncDataModel.getExceptionDataInsertMap().isEmpty()) {
            List<ExceptionData> valueList = new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }


    /**
     * 1000条数据处理一次数据库
     *
     * @param dataList
     * @param batch
     */
    public void processSynDatalimt(List<String> dataList, String batch, Date closeDate) {
        SyncDataModel syncDataModel = new SyncDataModel();
        CompletableFuture<List<String>> subTask = syncDataService.doTaskSyns(dataList, syncDataModel, closeDate);
        log.info("1000条数据等待其子线程处理完成..........");
        //收集子线程返回结果
        List<String> resultList = new ArrayList<>();
        try {
            resultList.addAll(subTask.get());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (resultList.isEmpty()) {
            //开始保存数据
            //产品系列
            if (!syncDataModel.getInsertPSMap().isEmpty()) {
                List<ProductSeries> valueList = new ArrayList<>(syncDataModel.getInsertPSMap().values());
                productSeriesRepository.save(valueList);
                for (ProductSeries productSeries : valueList) {
                    syncDataModel.getExistPSMap().put(productSeries.getSeriesName(), productSeries);
                }
            }
            //产品名称
            if (!syncDataModel.getInsertPCMap().isEmpty()) {
                List<Product> valueList = new ArrayList<>(syncDataModel.getInsertPCMap().values());
                for (Product product : valueList) {
                    if (Objects.isNull(product.getProductSeries())) {
                        product.setProductSeries(syncDataModel.getExistPSMap().get(product.getProductSerieName()));
                    }
                    syncDataModel.getExistPCMap().put(product.getProductName(), product);
                }
                productRepository.save(valueList);
            }
            //客户信息
            if (!syncDataModel.getInsertPMap().isEmpty()) {
                List<Personal> valueList = new ArrayList<>(syncDataModel.getInsertPMap().values());
                personalRepository.save(valueList);
                for (Personal personal : valueList) {
                    syncDataModel.getExistPMap().put(personal.getCertificatesNumber(), personal);
                }
            }
            //客户备注信息
            if (!syncDataModel.getInsertMaMap().isEmpty()) {
                List<Material> valueList = new ArrayList<>(syncDataModel.getInsertMaMap().values());
                for (Material material : valueList) {
                    if (Objects.isNull(material.getPersonalId())) {
                        material.setPersonalId(syncDataModel.getExistPMap().get(material.getCertificatesNumber()).getId());
                    }
                }
                materialRepository.save(valueList);
            }
            //客户收入信息
            if (!syncDataModel.getInsertPIMap().isEmpty()) {
                List<PersonalIncomeExp> valueList = new ArrayList<>(syncDataModel.getInsertPIMap().values());
                for (PersonalIncomeExp personalIncomeExp : valueList) {
                    if (Objects.isNull(personalIncomeExp.getPersonalId())) {
                        personalIncomeExp.setPersonalId(syncDataModel.getExistPMap().get(personalIncomeExp.getCertificatesNumber()).getId());
                    }
                }
                personalIncomeExpRepository.save(valueList);
            }
            ///客户工作信息
            if (!syncDataModel.getInsertPJMap().isEmpty()) {
                List<PersonalJob> valueList = new ArrayList<>(syncDataModel.getInsertPJMap().values());
                for (PersonalJob personalJob : valueList) {
                    if (Objects.isNull(personalJob.getPersonalId())) {
                        personalJob.setPersonalId(syncDataModel.getExistPMap().get(personalJob.getCertificatesNumber()).getId());
                    }
                }
                personalJobRepository.save(valueList);
            }
            if (!syncDataModel.getInsertAddressMap().isEmpty()) {
                List<PersonalAddress> valueList = new ArrayList<>(syncDataModel.getInsertAddressMap().values());
                for (PersonalAddress personalAddress : valueList) {
                    if (Objects.isNull(personalAddress.getPersonalId())) {
                        String number = personalAddress.getCertificatesNumber().substring(0, personalAddress.getCertificatesNumber().indexOf("-"));
                        personalAddress.setPersonalId(syncDataModel.getExistPMap().get(number).getId());
                    }
                }
                personalAddressRepository.save(valueList);
            }
            //开户信息
            if (!syncDataModel.getInsertBMap().isEmpty()) {
                List<PersonalBank> valueList = new ArrayList<>(syncDataModel.getInsertBMap().values());
                for (PersonalBank personalBank : valueList) {
                    if (Objects.isNull(personalBank.getPersonalId())) {
                        personalBank.setPersonalId(syncDataModel.getExistPMap().get(personalBank.getCertificatesNumber()).getId());
                    }
                }
                personalBankRepository.save(valueList);
            }
            //客户车辆信息
            if (!syncDataModel.getInsertPCarMap().isEmpty()) {
                List<PersonalCar> valueList = new ArrayList<>(syncDataModel.getInsertPCarMap().values());
                for (PersonalCar personalCar : valueList) {
                    if (Objects.isNull(personalCar.getPersonalId())) {
                        personalCar.setPersonalId(syncDataModel.getExistPMap().get(personalCar.getCertificatesNumber()).getId());
                    }
                }
            }
            //客户工作信息
            if (!syncDataModel.getInsertPPMap().isEmpty()) {
                List<PersonalProperty> valueList = new ArrayList<>(syncDataModel.getInsertPPMap().values());
                for (PersonalProperty personalProperty : valueList) {
                    if (Objects.isNull(personalProperty.getPersonalId())) {
                        String number = personalProperty.getCertificatesNumber().substring(0, personalProperty.getCertificatesNumber().length() - 1);
                        personalProperty.setPersonalId(syncDataModel.getExistPMap().get(number).getId());
                    }
                }
                personalPropertyRepository.save(valueList);
            }
            //客户联系人信息
            if (!syncDataModel.getInsertPConMap().isEmpty()) {
                List<PersonalContact> valueList = new ArrayList<>(syncDataModel.getInsertPConMap().values());
                for (PersonalContact personalContact : valueList) {
                    if (Objects.isNull(personalContact.getPersonalId())) {
                        String number = personalContact.getCertificatesNumber().substring(0, personalContact.getCertificatesNumber().indexOf("-"));
                        personalContact.setPersonalId(syncDataModel.getExistPMap().get(number).getId());
                    }
                }
                personalContactRepository.save(valueList);
            }
            //待分配案件(新增)
            if (!syncDataModel.getCaseInfoDistributedInsertMap().isEmpty()) {
                List<CaseInfoDistributed> valueList = new ArrayList<>(syncDataModel.getCaseInfoDistributedInsertMap().values());
                for (CaseInfoDistributed caseInfoDistributed : valueList) {
                    caseInfoDistributed.setBatchNumber(batch);
                    if (Objects.isNull(caseInfoDistributed.getProduct())) {
                        caseInfoDistributed.setProduct(syncDataModel.getExistPCMap().get(caseInfoDistributed.getProductName()));
                        caseInfoDistributed.setProductType(syncDataModel.getExistPCMap().get(caseInfoDistributed.getProductName()).getProductSeries().getId());
                    }
                    if (Objects.isNull(caseInfoDistributed.getPersonalInfo())) {
                        caseInfoDistributed.setPersonalInfo(syncDataModel.getExistPMap().get(caseInfoDistributed.getCertificatesNumber()));
                    }
                }
                caseInfoDistributedRepository.save(valueList);
            }
            //还款计划
            if (!syncDataModel.getInsertPayPMap().isEmpty()) {
                List<String> deleteList = new ArrayList<>(syncDataModel.getDeletePayPMap().values());
                payPlanRepository.deleteByCaseNumber(deleteList);
                List<PayPlan> valueList = new ArrayList<>(syncDataModel.getInsertPayPMap().values());
                payPlanRepository.save(valueList);
            }
            if (!syncDataModel.getInsertWriteOffDetailsMap().isEmpty()) {
                List<String> deleteList = new ArrayList<>(syncDataModel.getDeleteWriteOffDetailsMap().values());
                writeOffDetailsRepository.deleteByCaseNumber(deleteList);
                List<WriteOffDetails> valueList = new ArrayList<>(syncDataModel.getInsertWriteOffDetailsMap().values());
                writeOffDetailsRepository.save(valueList);
            }
            //商品信息
            if (!syncDataModel.getInsertCommodity().isEmpty()) {
                List<Commodity> valueList = new ArrayList<>(syncDataModel.getInsertCommodity().values());
                commodityRepository.save(valueList);
                for (Commodity commodity : valueList) {
                    if (syncDataModel.getExistCommodityMap().containsKey(commodity.getCaseNumber())) {
                        syncDataModel.getExistCommodityMap().get(commodity.getCaseNumber()).add(commodity);
                    } else {
                        List<Commodity> list = new ArrayList<>();
                        list.add(commodity);
                        syncDataModel.getExistCommodityMap().put(commodity.getCaseNumber(), list);
                    }
                }
            }
            //案件的订单信息
            if (!syncDataModel.getInsertOrderInfoMap().isEmpty()) {
                List<OrderInfo> deleteList = new ArrayList<>(syncDataModel.getDeleteOrderInfoMap().values());
                orderInfoRepository.delete(deleteList);
                List<OrderInfo> valueList = new ArrayList<>(syncDataModel.getInsertOrderInfoMap().values());
                for (OrderInfo orderInfo : valueList) {
                    if (syncDataModel.getExistCommodityMap().containsKey(orderInfo.getCaseNumber())) {
                        List<Commodity> list = new ArrayList<>(syncDataModel.getExistCommodityMap().get(orderInfo.getCaseNumber()));
                        if (list != null && list.size() > 0) {
                            Set<Commodity> set = new HashSet<>(list);
                            orderInfo.setCommodities(set);
                        }
                    }
                }
                orderInfoRepository.save(valueList);
                for (OrderInfo orderInfo : valueList) {
                    syncDataModel.getSelectOrderInfoId().put(orderInfo.getCaseNumber(), orderInfo);
                }
            }
            //订单还款计划
            if (!syncDataModel.getInsertOrderRepaymentPlan().isEmpty()) {
                List<OrderRepaymentPlan> deleteList = new ArrayList<>(syncDataModel.getDeleteOrderRepaymentPlanMap().values());
                orderRepaymentPlanRepository.delete(deleteList);

                List<OrderRepaymentPlan> valueList = new ArrayList<>(syncDataModel.getInsertOrderRepaymentPlan().values());
                for (OrderRepaymentPlan orderRepaymentPlan : valueList) {
                    if (syncDataModel.getSelectOrderInfoId().containsKey(orderRepaymentPlan.getCaseNumber())) {
                        orderRepaymentPlan.setOrderId(syncDataModel.getSelectOrderInfoId().get(orderRepaymentPlan.getCaseNumber()).getId());
                    }
                }
                orderRepaymentPlanRepository.save(valueList);
            }

            //待分配案件(更新)
            if (!syncDataModel.getCaseInfoDistributedUdpateMap().isEmpty()) {
                List<CaseInfoDistributed> valueList = new ArrayList<>(syncDataModel.getCaseInfoDistributedUdpateMap().values());
                caseInfoDistributedRepository.save(valueList);
            }
            //案件信息更新
            if (!syncDataModel.getCaseInfoUpdateMap().isEmpty()) {
                List<CaseInfo> valueList = new ArrayList<>(syncDataModel.getCaseInfoUpdateMap().values());
                caseInfoRepository.save(valueList);
            }
            //附近信息
            if (!syncDataModel.getInsertCaseFileMap().isEmpty()) {
                List<CaseFile> valueList = new ArrayList<>(syncDataModel.getInsertCaseFileMap().values());
                caseFileRepository.save(valueList);
            }
        } else {
            log.error("解析数据失败不保存数据，错误信息如下：{}", resultList.toString());
        }
    }
}
