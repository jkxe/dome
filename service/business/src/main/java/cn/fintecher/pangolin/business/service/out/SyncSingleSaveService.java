package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.business.model.out.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.SyncDataService;
import cn.fintecher.pangolin.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SyncSingleSaveService {

    final Logger log = LoggerFactory.getLogger(SyncSingleSaveService.class);

    @Autowired
    OverdueDetailRepository overdueDetailRepository;

    @Autowired
    OverdueDetailBatchRepository overdueDetailBatchRepository;

    @Autowired
    ExceptionDataRepository exceptionDataRepository;

    @Autowired
    CaseInfoDistributedBatchRepository caseInfoDistributedBatchRepository;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    CaseInfoBatchRepository caseInfoBatchRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    PersonalJobRepository personalJobRepository;

    @Autowired
    PersonalAddressRepository personalAddressRepository;

    @Autowired
    PersonalBankRepository personalBankRepository;

    @Autowired
    SyncDataService syncDataService;


    /**
     * 客户开户信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerAccountlimt(List<HYOverdueCustomerAccountBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户开户信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerAccountTaskSyns(dataList,syncDataModel,handleIndex);
        log.info("客户开户信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        try{
            //开始保存数据
            //客户开户信息(新增)
            if(!syncDataModel.getPersonalBankInsertMap().isEmpty()){
                List<PersonalBank> valueList=new ArrayList<>(syncDataModel.getPersonalBankInsertMap().values());
                personalBankRepository.save(valueList);
            }
            //客户开户信息(更新)
            if(!syncDataModel.getPersonalBankUpdateMap().isEmpty()){
                List<PersonalBank> valueList=new ArrayList<>(syncDataModel.getPersonalBankUpdateMap().values());
                personalBankRepository.save(valueList);
            }
            log.info("客户开户信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData=ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_ACCOUNT.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode()+
                            exceptionData.getDataType()+
                            exceptionData.getDataId(), exceptionData);

            log.error("客户开户信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }

    @Autowired
    PersonalImgFileRepository personalImgFileRepository;
    /**
     * 客户影像文件信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerImgAttachlimt(List<HYOverdueCustomerImgAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户影像文件信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerImgAttachTaskSyns(dataList,syncDataModel,handleIndex);
        log.info("客户影像文件信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        try {
            //开始保存数据
            //客户影像文件信息(新增)
            if (!syncDataModel.getPersonalImgFileInsertMap().isEmpty()) {
                List<PersonalImgFile> valueList = new ArrayList<>(syncDataModel.getPersonalImgFileInsertMap().values());
                personalImgFileRepository.save(valueList);
            }
            //客户影像文件信息(更新)
            if (!syncDataModel.getPersonalImgFileUpdateMap().isEmpty()) {
                List<PersonalImgFile> valueList = new ArrayList<>(syncDataModel.getPersonalImgFileUpdateMap().values());
                personalImgFileRepository.save(valueList);
            }
            log.info("客户影像文件信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData=ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_IMG_ATTACH.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode()+
                            exceptionData.getDataType()+
                            exceptionData.getDataId(), exceptionData);

            log.error("客户影像文件信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }

    @Autowired
    PersonalContactRepository personalContactRepository;
    /**
     * 客户联系人信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerRelationlimt(List<HYOverdueCustomerRelationBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户联系人信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerRelationTaskSyns(dataList,syncDataModel,handleIndex);
        log.info("客户联系人信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }
    @Autowired
    PersonalSocialPlatRepository personalSocialPlatRepository;

    /**
     * 客户社交平台信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerSocialPlatlimt(List<HYOverdueCustomerSocialPlatBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户社交平台信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerSocialPlatTaskSyns(dataList,syncDataModel,handleIndex);
        log.info("客户社交平台信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        try {
            //开始保存数据
            //客户社交平台信息(新增)
            if (!syncDataModel.getPersonalSocialPlatInsertMap().isEmpty()) {
                List<PersonalSocialPlat> valueList = new ArrayList<>(syncDataModel.getPersonalSocialPlatInsertMap().values());
                personalSocialPlatRepository.save(valueList);
            }
            //客户社交平台信息(更新)
            if (!syncDataModel.getPersonalSocialPlatUpdateMap().isEmpty()) {
                List<PersonalSocialPlat> valueList = new ArrayList<>(syncDataModel.getPersonalSocialPlatUpdateMap().values());
                personalSocialPlatRepository.save(valueList);
            }
            log.info("客户社交平台信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData=ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_SOCIAL_PLAT.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode()+
                            exceptionData.getDataType()+
                            exceptionData.getDataId(), exceptionData);

            log.error("客户社交平台信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }
    @Autowired
    PersonalAstOperCrdtRepository personalAstOperCrdtRepository;

    /**
     * 客户资产征信经营信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerAstOperCrdtlimt(List<HYOverdueCustomerAstOperCrdtBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户资产征信经营信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerAstOperCrdtSyns(dataList,syncDataModel,handleIndex);
        log.info("客户资产征信经营信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        try{
            //开始保存数据
            //客户资产征信经营信息(新增)
            if(!syncDataModel.getPersonalAstOperCrdtInsertMap().isEmpty()){
                List<PersonalAstOperCrdt> valueList=new ArrayList<>(syncDataModel.getPersonalAstOperCrdtInsertMap().values());
                personalAstOperCrdtRepository.save(valueList);
            }
            //客户资产征信经营信息(更新)
            if(!syncDataModel.getPersonalAstOperCrdtUpdateMap().isEmpty()){
                List<PersonalAstOperCrdt> valueList=new ArrayList<>(syncDataModel.getPersonalAstOperCrdtUpdateMap().values());
                personalAstOperCrdtRepository.save(valueList);
            }
            log.info("客户资产征信经营信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData=ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_AST_OPER_CRDT.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode()+
                            exceptionData.getDataType()+
                            exceptionData.getDataId(), exceptionData);

            log.error("客户资产征信经营信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }

    @Autowired
    CaseFileRepository caseFileRepository;
    /**
     * 客户文本文件信息批量数据处理
     * @param dataList
     */
    @Async
    public void hyProcessSynDataCustomerFileAttachlimt(List<HYOverdueCustomerFileAttachBean> dataList, int handleIndex, TaskBatchImportLog taskBatchImportLog){
        SyncDataModel syncDataModel = new SyncDataModel();
        log.info("客户文本文件信息第"+handleIndex+"批有"+dataList.size()+"条数据等待处理完成..........");
        syncDataService.doHYCustomerFileAttachTaskSyns(dataList,syncDataModel,handleIndex);
        log.info("客户文本文件信息第"+handleIndex+"批有"+dataList.size()+"条数据解析完成..........");

        try {
            //开始保存数据
            //客户文本文件信息(新增)
            if(!syncDataModel.getCaseFileInsertMap().isEmpty()){
                List<CaseFile> valueList=new ArrayList<>(syncDataModel.getCaseFileInsertMap().values());
                caseFileRepository.save(valueList);
            }
            //客户文本文件信息(更新)
            if(!syncDataModel.getCaseFileUpdateMap().isEmpty()){
                List<CaseFile> valueList=new ArrayList<>(syncDataModel.getCaseFileUpdateMap().values());
                caseFileRepository.save(valueList);
            }
            log.info("客户文本文件信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库成功..........");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            //保存导入异常数据
            ExceptionData exceptionData=ExceptionData.createInstance(
                    ExceptionData.DataType.CUSTOMER_FILE_ATTACH.getValue(),
                    "0",
                    null,
                    ExceptionData.EXCEPTION_CODE_INTODB_BATCH, e);
            syncDataModel.getExceptionDataInsertMap().put(
                    exceptionData.getExceptionCode()+
                            exceptionData.getDataType()+
                            exceptionData.getDataId(), exceptionData);

            log.error("客户文本文件信息第"+handleIndex+"批有"+dataList.size()+"条数据保存数据库失败.........."+ExceptionData.getStackTrace(e));
        }
        //异常数据信息(新增）
        if(!syncDataModel.getExceptionDataInsertMap().isEmpty()){
            List<ExceptionData> valueList=new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
    }
}
