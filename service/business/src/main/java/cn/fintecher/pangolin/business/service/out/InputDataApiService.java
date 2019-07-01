package cn.fintecher.pangolin.business.service.out;

import cn.fintecher.pangolin.business.model.SyncDataModel;
import cn.fintecher.pangolin.business.model.out.*;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.business.service.UserService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.HangYinSmsMessage;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.strategy.DivisionCaseParamModel;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import cn.fintecher.pangolin.util.HttpUtil;
import cn.fintecher.pangolin.util.SFTPUtil;
import cn.fintecher.pangolin.util.ZWDateUtil;
import cn.fintecher.pangolin.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hsjry.lang.common.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

/**
 * @Author: wangzhao
 * @Description
 * @Date 2018/6/15  17:32
 **/
@Service("InputDataApiService")
public class InputDataApiService {

    final Logger log = LoggerFactory.getLogger(InputDataApiService.class);

    @Value("${pangolin.tempFileDir}")
    private String tempFileDir;

    @Autowired
    SysParamRepository sysParamRepository;

    @Autowired
    OutsourcePoolRepository outsourcePoolRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AreaCodeRepository areaCodeRepository;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Autowired
    CaseInfoDistributedRepository caseInfoDistributedRepository;

    @Autowired
    private SynDataSaveService synDataSaveService;

    @Autowired
    private SyncHYDataSaveService syncHYDataSaveService;

    @Autowired
    PersonalRepository personalRepository;
    @Autowired
    PersonalContactRepository personalContactRepository;
    @Autowired
    PersonalAddressRepository personalAddressRepository;
    @Autowired
    PersonalJobRepository personalJobRepository;
    @Autowired
    PersonalBankRepository personalBankRepository;
    @Autowired
    PersonalImgFileRepository personalImgFileRepository;
    @Autowired
    PersonalSocialPlatRepository personalSocialPlatRepository;
    @Autowired
    PersonalAstOperCrdtRepository personalAstOperCrdtRepository;
    @Autowired
    CaseFileRepository caseFileRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReminderService reminderService;
    @Autowired
    UserService userService;
    @Autowired
    TaskBatchImportLogRepository taskBatchImportLogRepository;
    @Autowired
    BatchDataCacheService batchDataCacheService;
    @Autowired
    ExceptionDataRepository exceptionDataRepository;
    @Autowired
    DataDictRepository dictRepository;

    /**
     * HY获取核心推送的逾期数据
     *
     * @param dateStr 指定的日期(格式:yyyyMMdd)
     */
    @Async
    public void getHYData(String dateStr, String taskId, Integer taskType) {
        TaskBatchImportLog taskBatchImportLog = new TaskBatchImportLog();
        taskBatchImportLog.setId(taskId);
        taskBatchImportLog.setTaskDate(dateStr);
        taskBatchImportLog.setTaskType(taskType);
        taskBatchImportLog.setStartTime(new Date());
        taskBatchImportLog.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_TATAL_TASK.getRemark());
        taskBatchImportLog.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
        taskBatchImportLogRepository.save(taskBatchImportLog);

        long taskStartTime = System.currentTimeMillis();
        //本地测试 test设置为true, tempDir设置为本地有数据包的目录
        boolean test = false;
//        String tempDir = "E:\\hangyin\\ZWJK_BATCHDATA_20190426035433";
        String tempDir = "/usr/local/src/pangolin/temp/ZWJK_BATCHDATA_20190222074131";

        //下载sftp数据包
        SftpDownloadPackage sftpDownloadPackage = new SftpDownloadPackage(dateStr, test).invoke(taskBatchImportLog);
        if (sftpDownloadPackage.is()) {
            endTaskCallBack(taskBatchImportLog, taskStartTime);
            return;
        }

        String unzipFile = sftpDownloadPackage.getUnzipFile();
        String destinationFolder = sftpDownloadPackage.getDestinationFolder();

        try {

            if (unzipFile.equals("")) {
                log.error("未找到杭银核心系统生成的文件包...");
                taskBatchImportLog.setFailedCode("未找到杭银核心系统生成的文件包...");
                taskBatchImportLog.setFailedMsg("未找到杭银核心系统生成的文件包...");
                throw new Exception("未找到杭银核心系统生成的文件包...");
            } else {
                //解压批量数据tar包
                try {
                    destinationFolder = unTarGzPackage(test, tempDir, unzipFile, destinationFolder);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    log.error("解压SFTP批量数据包错误...");
                    taskBatchImportLog.setFailedCode("解压SFTP批量数据包错误...");
                    taskBatchImportLog.setFailedMsg("解压SFTP批量数据包错误...");
                    throw e;
                }
                File destinationFolderFile = new File(destinationFolder);
                File[] destinationFiles = destinationFolderFile.listFiles();
                List<String> needParseFiles = null;
                int txtFileCount = 0;
                for (File file : destinationFiles) {
                    if (file.getName().contains(".ctl")) {
                        needParseFiles = readHYCtlFileByLines(destinationFolder + "/" + file.getName());
                    }
                    if (file.getName().contains(".txt")) {
                        txtFileCount++;
                    }
                }
                if (needParseFiles == null || txtFileCount != needParseFiles.size()) {
                    log.error("核心数据推送批量包格式错误...");
                    taskBatchImportLog.setFailedCode("核心数据推送批量包格式错误...");
                    taskBatchImportLog.setFailedMsg("核心数据推送批量包格式错误...");
                    throw new Exception("核心数据推送批量包格式错误...");
                } else {
                    //启动多线程跑批
                    mutilThreadbatchHandleFile(dateStr, destinationFolder, needParseFiles, taskBatchImportLog);
                }

                //触发分案任务
                divisionCaseInfo();

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("核心数据跑批出现异常，中断跑批..." + ExceptionData.getStackTrace(e));
            if (taskBatchImportLog.getFailedMsg() == null) {
                taskBatchImportLog.setFailedCode("核心数据跑批出现异常，中断跑批...");
                taskBatchImportLog.setFailedMsg("核心数据跑批出现异常，中断跑批..." + ExceptionData.getStackTrace(e));
            }
        }
        endTaskCallBack(taskBatchImportLog, taskStartTime);
    }

    public void endTaskCallBack(TaskBatchImportLog taskBatchImportLog, long taskStartTime) {
//        clearDataMap();
        long taskEndTime = System.currentTimeMillis();
        log.info("核心推送的逾期数据更新全部结束所需时间为:" + (taskEndTime - taskStartTime) + "ms");
        taskBatchImportLog.setEndTime(new Date());
        taskBatchImportLog.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
        taskBatchImportLogRepository.save(taskBatchImportLog);
        //发站内信
        if (taskBatchImportLog.getFailedMsg() == null) {
            sendReminderMsg("跑批任务结束",
                    taskBatchImportLog.getTaskDate() + "批量包 批量同步数据任务执行完毕, 总执行数量: " +
                            taskBatchImportLog.getTotalCount() + ", 逾期明细执行数量: " + taskBatchImportLog.getBak1() + ", 成功数量: " +
                            taskBatchImportLog.getSuccessCount() + ", 失败数量: " +
                            taskBatchImportLog.getFailedCount(),
                    ReminderType.BATCH_TASK_WARNING);
        } else {
            sendReminderMsg("跑批任务失败",
                    taskBatchImportLog.getTaskDate() + "批量包 批量同步数据任务执行失败，失败信息: " + taskBatchImportLog.getFailedCode(),
                    ReminderType.BATCH_TASK_WARNING);
        }
    }

    /**
     * 解压数据包
     *
     * @param test
     * @param tempDir
     * @param unzipFile
     * @param destinationFolder
     * @return
     * @throws IOException
     */
    private String unTarGzPackage(boolean test, String tempDir, String unzipFile, String destinationFolder) throws IOException {
        if (!test) {
            log.info("解压下载过的逾期文件包........");
            String unzipDir = unzipFile.substring(0, unzipFile.indexOf("."));
            ZipUtil.unTarGz(destinationFolder + "/" + unzipFile, destinationFolder + "/" + unzipDir);
            destinationFolder = destinationFolder + "/" + unzipDir;
            log.info("解压逾期文件包成功，目录：" + destinationFolder);
        } else {
            destinationFolder = tempDir;
        }
        return destinationFolder;
    }

    /**
     * 调用分案任务
     */
    private void divisionCaseInfo() throws Exception {
        try {
            //开始分案
            String companyName = "杭银消费金融";
            Iterator<Company> companyIterator = companyRepository.findAll(QCompany.company.chinaName.eq(companyName)).iterator();
            if (companyIterator.hasNext()) {
                Company company = companyIterator.next();
                //执行案件分配策略
                log.info("逾期数据入库结束，开始进行策略分配........");
                DivisionCaseParamModel divisionCaseParamModel = new DivisionCaseParamModel();
                divisionCaseParamModel.setCompanyCode(company.getCode());
                restTemplate.postForEntity("http://dataimp-service/api/obtainTaticsStrategyController/everyDayDivisionCase", divisionCaseParamModel, Void.class);
                /**
                 * 如果当前日期是本月的第一天，则执行按月的分案策略。
                 */
                //                boolean flag= ZWDateUtil.todayIsFristDayInMonth();
                //                if(flag){
                //                    restTemplate.postForEntity("http://dataimp-service/api/obtainTaticsStrategyController/monthEarlyDivisionCase",divisionCaseParamModel,Void.class);
                //                }
                log.info("策略分配结束，开始进行对委外案件的处理........");
                //对委外案件进行处理
                updateOutSourcePool();
            }
        } catch (Exception e) {
            log.error("分案发生异常");
            throw new Exception("分案发生异常");
        }
    }

    /**
     * 对数据包文件多线程跑批
     *
     * @param dateStr
     * @param destinationFolder
     * @param needParseFiles
     * @throws InterruptedException
     */
    private void mutilThreadbatchHandleFile(String dateStr, String destinationFolder, List<String> needParseFiles, TaskBatchImportLog taskBatchImportLog) throws Exception {
        //按这个顺序去解析数据文件
        String[] fileNameStarts = {"OVERDUE_CUSTOMER_DETAIL", "OVERDUE_DETAIL",
                "OVERDUE_CUSTOMER_ASTOPERCRDT", "OVERDUE_CUSTOMER_FILEATTACH",
                "OVERDUE_CUSTOMER_IMGATTACH", "OVERDUE_CUSTOMER_RELATION",
                "OVERDUE_CUSTOMER_SOCIALPLAT", "OVERDUE_CUSTOMER_ACCOUNT"};

        //初始化数据库数据到内存数据
        clearDataMap();
        initialDataMap();

        Future<String> result1 = null, result2 = null, result3 = null, result4 = null;
        Future<String> result5 = null, result6 = null, result7 = null, result8 = null;

        boolean isHave1 = false, isHave2 = false, isHave3 = false, isHave4 = false;
        boolean isHave5 = false, isHave6 = false, isHave7 = false, isHave8 = false;

        TaskBatchImportLog taskBatchImportLog1 = null, taskBatchImportLog2 = null;
        TaskBatchImportLog taskBatchImportLog3 = null, taskBatchImportLog4 = null;
        TaskBatchImportLog taskBatchImportLog5 = null, taskBatchImportLog6 = null;
        TaskBatchImportLog taskBatchImportLog7 = null, taskBatchImportLog8 = null;

        long startTime = System.currentTimeMillis();

        for (String needParseFile : needParseFiles) {
            if (needParseFile.contains("OVERDUE_CUSTOMER_DETAIL")) {
                isHave1 = true;
                log.info("客户明细信息跑批开始-01");
                taskBatchImportLog1 = new TaskBatchImportLog();
                taskBatchImportLog1.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog1.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog1.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog1.setStartTime(new Date());
                taskBatchImportLog1.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_DETAIL_TASK.getRemark());
                taskBatchImportLog1.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog1);
                log.info("读取客户明细文件开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户明细文件结束...");
                //计算总数据
                taskBatchImportLog1.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog1.getTotalCount());
                log.info("解析客户明细文件开始...");
                List<HYOverdueCustomerDetailBean> hyOverdueCustomerDetailBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerDetailBean.class);
                log.info("解析客户明细文件结束...");
                result1 = syncHYDataSaveService.importHyCustomerDetail(hyOverdueCustomerDetailBeanList, 0, taskBatchImportLog1);
            }
            if (needParseFile.contains("OVERDUE_DETAIL")) {
                isHave2 = true;
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_ACCOUNT")) {
                isHave3 = true;
                log.info("客户开户信息跑批开始-01");
                taskBatchImportLog3 = new TaskBatchImportLog();
                taskBatchImportLog3.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog3.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog3.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog3.setStartTime(new Date());
                taskBatchImportLog3.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_ACCOUNT_TASK.getRemark());
                taskBatchImportLog3.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog3);
                log.info("读取客户开户信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户开户信息结束...");
                //计算总数据
                taskBatchImportLog3.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog3.getTotalCount());
                log.info("解析客户开户信息开始...");
                List<HYOverdueCustomerAccountBean> hyOverdueCustomerAccountBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerAccountBean.class);
                log.info("解析客户开户信息结束...");
                result3 = syncHYDataSaveService.importHyCustomerAccount(hyOverdueCustomerAccountBeanList, 0, taskBatchImportLog3);
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_IMGATTACH")) {
                isHave4 = true;
                log.info("客户影像文件信息跑批开始-01");
                taskBatchImportLog4 = new TaskBatchImportLog();
                taskBatchImportLog4.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog4.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog4.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog4.setStartTime(new Date());
                taskBatchImportLog4.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_IMG_FILE_TASK.getRemark());
                taskBatchImportLog4.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog4);
                log.info("读取客户影像文件信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户影像文件信息结束...");
                //计算总数据
                taskBatchImportLog4.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog4.getTotalCount());
                log.info("解析客户影像文件信息开始...");
                List<HYOverdueCustomerImgAttachBean> hyOverdueCustomerImgAttachBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerImgAttachBean.class);
                log.info("解析客户影像文件信息结束...");
                result4 = syncHYDataSaveService.importHyCustomerImgFile(hyOverdueCustomerImgAttachBeanList, 0, taskBatchImportLog4);
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_RELATION")) {
                isHave5 = true;
                log.info("客户关联人信息跑批开始-01");
                taskBatchImportLog5 = new TaskBatchImportLog();
                taskBatchImportLog5.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog5.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog5.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog5.setStartTime(new Date());
                taskBatchImportLog5.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_RELATION_TASK.getRemark());
                taskBatchImportLog5.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog5);
                log.info("读取客户关联人信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户关联人信息结束...");
                //计算总数据
                taskBatchImportLog5.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog5.getTotalCount());
                log.info("解析客户关联人信息开始...");
                List<HYOverdueCustomerRelationBean> hyOverdueCustomerRelationBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerRelationBean.class);
                log.info("解析客户关联人信息结束...");
                result5 = syncHYDataSaveService.importHyCustomerRelation(hyOverdueCustomerRelationBeanList, 0, taskBatchImportLog5);
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_SOCIALPLAT")) {
                isHave6 = true;
                log.info("客户社交平台信息跑批开始-01");
                taskBatchImportLog6 = new TaskBatchImportLog();
                taskBatchImportLog6.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog6.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog6.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog6.setStartTime(new Date());
                taskBatchImportLog6.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_SOCIAL_PLAT_TASK.getRemark());
                taskBatchImportLog6.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog6);
                log.info("读取客户社交平台信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户社交平台信息结束...");
                //计算总数据
                taskBatchImportLog6.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog6.getTotalCount());
                log.info("解析客户社交平台信息开始...");
                List<HYOverdueCustomerSocialPlatBean> hyOverdueCustomerSocialPlatBeans = hyTxtToObjectList(readLineList, HYOverdueCustomerSocialPlatBean.class);
                log.info("解析客户社交平台信息结束...");
                result6 = syncHYDataSaveService.importHyCustomerSocialPlat(hyOverdueCustomerSocialPlatBeans, 0, taskBatchImportLog6);
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_ASTOPERCRDT")) {
                isHave7 = true;
                log.info("客户资产经营征信信息跑批开始-01");
                taskBatchImportLog7 = new TaskBatchImportLog();
                taskBatchImportLog7.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog7.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog7.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog7.setStartTime(new Date());
                taskBatchImportLog7.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_ASTOPERCRDT_TASK.getRemark());
                taskBatchImportLog7.setId(ShortUUID.uuid());
//                taskBatchImportLogRepository.save(taskBatchImportLog7);
                log.info("读取客户资产经营征信信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户资产经营征信信息结束...");
                //计算总数据
                taskBatchImportLog7.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog7.getTotalCount());
                log.info("解析客户资产经营征信信息开始...");
                List<HYOverdueCustomerAstOperCrdtBean> hyOverdueCustomerAstOperCrdtBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerAstOperCrdtBean.class);
                log.info("解析客户资产经营征信信息结束...");
                result7 = syncHYDataSaveService.importHYCustomerAstOperCrdt(hyOverdueCustomerAstOperCrdtBeanList, 0, taskBatchImportLog7);
            }
            if (needParseFile.contains("OVERDUE_CUSTOMER_FILEATTACH")) {
                isHave8 = true;
                log.info("客户文本文件信息跑批开始-01");
                taskBatchImportLog8 = new TaskBatchImportLog();
                taskBatchImportLog8.setTaskType(taskBatchImportLog.getTaskType());
                taskBatchImportLog8.setParentId(taskBatchImportLog.getId());
                taskBatchImportLog8.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                taskBatchImportLog8.setStartTime(new Date());
                taskBatchImportLog8.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_CUSTOMER_FILE_ATTACH_TASK.getRemark());
                taskBatchImportLog8.setId(ShortUUID.uuid());
                log.info("读取客户文本文件信息开始...");
                List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                log.info("读取客户文本文件信息结束...");
                //计算总数据
                taskBatchImportLog8.setTotalCount(readLineList.size());
                taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog8.getTotalCount());
                log.info("解析客户文本文件信息开始...");
                List<HYOverdueCustomerFileAttachBean> hyOverdueCustomerFileAttachBeanList = hyTxtToObjectList(readLineList, HYOverdueCustomerFileAttachBean.class);
                log.info("解析客户文本文件信息结束...");
                result8 = syncHYDataSaveService.importHyCustomerFileAttach(hyOverdueCustomerFileAttachBeanList, 0, taskBatchImportLog8);
            }
        }
        boolean isEnd1 = false, isEnd2 = false, isEnd3 = false, isEnd4 = false;
        boolean isEnd5 = false, isEnd6 = false, isEnd7 = false, isEnd8 = false;
        boolean isStart = false;
        Future<Boolean> booleanFuture = null;
        //收完线程 释放内存
        while (true) {
            if (isHave1 && isHave5 && result1 != null && result1.isDone() && result5 != null && result5.isDone() && !isStart) {
                isStart = true;
                log.info("客户联系人-客户联系人保存子任务开始");
                booleanFuture = syncHYDataSaveService.savePersonalContact();
            }
            if ((!isHave1 || (isHave1 && result1 != null && result1.isDone()) && !isEnd1)) {
                isEnd1 = true;
                if (isHave1) {
                    //任务调用完成，释放资源
                    batchDataCacheService.personalAddressMap_delete();
                    batchDataCacheService.personalJobMap_delete();
                    long endTime = System.currentTimeMillis();
                    log.info("客户明细信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                    taskBatchImportLog1.setEndTime(new Date());
                    taskBatchImportLog1.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
                }
                //客户明细线程结束开启逾期明细线程
                for (String needParseFile : needParseFiles) {
                    if (needParseFile.contains("OVERDUE_DETAIL")) {
                        log.info("客户逾期明细信息跑批开始-01");
                        taskBatchImportLog2 = new TaskBatchImportLog();
                        taskBatchImportLog2.setTaskType(taskBatchImportLog.getTaskType());
                        taskBatchImportLog2.setParentId(taskBatchImportLog.getId());
                        taskBatchImportLog2.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
                        taskBatchImportLog2.setStartTime(new Date());
                        taskBatchImportLog2.setTaskName(TaskBatchImportLog.BatchTaskName.BATCH_OVERDUE_DETAIL_TASK.getRemark());
                        taskBatchImportLog2.setId(ShortUUID.uuid());
                        log.info("读取逾期明细信息开始...");
                        List<String> readLineList = readHYFileByLines(destinationFolder + "/" + needParseFile);
                        log.info("读取逾期明细信息结束...");
                        //计算总数据
                        taskBatchImportLog.setBak1(readLineList.size());
                        taskBatchImportLog2.setTotalCount(readLineList.size());
                        taskBatchImportLog.setTotalCount(taskBatchImportLog.getTotalCount() + taskBatchImportLog2.getTotalCount());
                        log.info("解析逾期明细信息开始...");
                        List<HYOverdueDetailBean> hyInterfaceDataCaseInfoBeanList = hyTxtToObjectList(readLineList, HYOverdueDetailBean.class);
                        log.info("解析逾期明细信息结束...");
                        String batch = dateStr;
                        Date closeDate = getCloseDate();
                        result2 = syncHYDataSaveService.importHyCaseInfo(hyInterfaceDataCaseInfoBeanList, batch, closeDate, taskBatchImportLog);
                    }
                }
            }
            if (isHave2 && result2 != null && result2.isDone() && !isEnd2) {
                isEnd2 = true;
                taskBatchImportLog.setFailedCount(taskBatchImportLog.getBak1() - taskBatchImportLog.getSuccessCount());
                //任务调用完成，释放资源
                SyncDataModel.caseNumberTemp = 0;
                batchDataCacheService.productMap_delete();
                //以借据为维度
                batchDataCacheService.caseInfoDistributedMap_delete();
                batchDataCacheService.caseInfoMap_delete();
                //以用户为维度
                batchDataCacheService.caseInfoDistributedMapByUser_delete();
                batchDataCacheService.caseInfoMapByUser_delete();
                //已结清案件
                batchDataCacheService.overCaseInfoMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户逾期明细信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog2.setEndTime(new Date());
                taskBatchImportLog2.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if (isHave3 && result3 != null && result3.isDone() && !isEnd3) {
                isEnd3 = true;
                //任务调用完成，释放资源
                batchDataCacheService.personalBankMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户开户信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog3.setEndTime(new Date());
                taskBatchImportLog3.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if (isHave4 && result4 != null && result4.isDone() && !isEnd4) {
                isEnd4 = true;
                //任务调用完成，释放资源
                batchDataCacheService.personalImgFileMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户影像文件信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog4.setEndTime(new Date());
                taskBatchImportLog4.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if ((isHave1 && result1 != null && result1.isDone()) && isHave5 && result5 != null && result5.isDone() && booleanFuture != null && booleanFuture.isDone() && !isEnd5) {
                isEnd5 = true;
                BatchDataCacheService.personalContactUniqueIdentifyCacheClear();
                BatchDataCacheService.personalContactCacheClear();
                //任务调用完成，释放资源
                long endTime = System.currentTimeMillis();
                log.info("客户关联人信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog5.setEndTime(new Date());
                taskBatchImportLog5.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if (isHave6 && result6 != null && result6.isDone() && !isEnd6) {
                isEnd6 = true;
                //任务调用完成，释放资源
                batchDataCacheService.personalSocialPlatMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户社交平台信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog6.setEndTime(new Date());
                taskBatchImportLog6.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if (isHave7 && result7 != null && result7.isDone() && !isEnd7) {
                isEnd7 = true;
                //任务调用完成，释放资源
                batchDataCacheService.personalAstOperCrdtMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户资产经营征信信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog7.setEndTime(new Date());
                taskBatchImportLog7.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if (isHave8 && result8 != null && result8.isDone() && !isEnd8) {
                isEnd8 = true;
                //任务调用完成，释放资源
                batchDataCacheService.personalFileAttachMap_delete();
                long endTime = System.currentTimeMillis();
                log.info("客户文本文件信息跑批结束-01 所需时间为:" + (endTime - startTime) + "ms");
                taskBatchImportLog8.setEndTime(new Date());
                taskBatchImportLog8.setTaskState(TaskBatchImportLog.BatchTaskState.EXECUTED.getValue());
            }
            if ((!isHave1 || (isHave2 && result1 != null && result1.isDone()))
                    && (!isHave2 || (isHave2 && result2 != null && result2.isDone()))
                    && (!isHave3 || (isHave3 && result3 != null && result3.isDone()))
                    && (!isHave4 || (isHave4 && result4 != null && result4.isDone()))
                    && (!isHave5 || (isHave5 && result5 != null && result5.isDone()) && isEnd5)
                    && (!isHave6 || (isHave6 && result6 != null && result6.isDone()))
                    && (!isHave7 || (isHave7 && result7 != null && result7.isDone()))
                    && (!isHave8 || (isHave8 && result8 != null && result8.isDone()))) {
                //全部任务调用完成，释放资源，跳出循环
                clearDataMap();
                break;
            }
            Thread.sleep(5000);
        }


    }


    /**
     * HY根据文件解析对应的文件
     *
     * @param path
     */
    public List<String> readHYCtlFileByLines(String path) {
        List<String> dataList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            LineIterator it = null;
            try {
                it = FileUtils.lineIterator(file, "utf-8");
                while (it.hasNext()) {
                    String line = it.nextLine();
                    if (line.contains(".txt")) {
                        dataList.add(line.trim());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                LineIterator.closeQuietly(it);
            }
        }
        return dataList;
    }

    private void clearDataMap() {
        //清理内存，初始化跑批数据
        SyncDataModel.caseNumberTemp = 0;
        batchDataCacheService.personalMap_delete();
        batchDataCacheService.personalAddressMap_delete();
        //关联人不同维度保存
        batchDataCacheService.personalContactMap_delete();

        batchDataCacheService.personalJobMap_delete();
        batchDataCacheService.personalBankMap_delete();
        batchDataCacheService.personalImgFileMap_delete();
        batchDataCacheService.personalSocialPlatMap_delete();
        batchDataCacheService.personalAstOperCrdtMap_delete();
        batchDataCacheService.personalFileAttachMap_delete();
        batchDataCacheService.productMap_delete();
        //以借据为维度
        batchDataCacheService.caseInfoDistributedMap_delete();
        batchDataCacheService.caseInfoMap_delete();
        //以用户为维度
        batchDataCacheService.caseInfoDistributedMapByUser_delete();
        batchDataCacheService.caseInfoMapByUser_delete();
        //已结清案件
        batchDataCacheService.overCaseInfoMap_delete();
    }

    private void initialProduct() {
        //产品数据
        log.info("数据库读取产品数据开始");
        List<Product> products = productRepository.findAll();
        log.info("产品数据放入内存开始");
        HashMap<String, Product> productMap = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
//            SyncDataModel.productMap.put(product.getProductName(), product);
            productMap.put(product.getProductName(), product);
            if (productMap.size() >= 10000) {
                batchDataCacheService.productMap_putAll(productMap);
                productMap.clear();
            }
        }
        if (productMap.size() > 0) {
            batchDataCacheService.productMap_putAll(productMap);
            productMap.clear();
        }
    }

    private void initialPersonal() {
        //客户明细信息
        log.info("数据库读取客户明细数据开始");
        List<Object[]> personals = personalRepository.findAllKeyAndUpdateTime();
        log.info("客户明细数据放入内存开始");
        HashMap<String, String> personalHashMap = new HashMap<>();
        for (int i = 0; i < personals.size(); i++) {
            Object[] personal = personals.get(i);
//            SyncDataModel.personalMap.put(personal.getId(),personal);
            convertObjToHashMap(personalHashMap, personal);

            if (personalHashMap.size() >= 10000) {
                batchDataCacheService.personalMap_putAll(personalHashMap);
                personalHashMap.clear();
            }
        }
        if (personalHashMap.size() > 0) {
            batchDataCacheService.personalMap_putAll(personalHashMap);
            personalHashMap.clear();
        }

    }

    private void initialPersonalContact() {
        //客户关联人信息
        log.info("数据库读取客户关联人信息开始");
        List<Object[]> personalContacts = personalContactRepository.findByAllKey();
        log.info("客户关联人数据放入内存开始");
        for (int i = 0; i < personalContacts.size(); i++) {
            Object[] personalContact = personalContacts.get(i);
            String personalId = (String) personalContact[1];
            String relation = String.valueOf(personalContact[2]);
            String phone = (String) personalContact[3];
            String relationName = (String) personalContact[6];
            String identify = DataSyncUtil.personalContactUniqueIdentify(personalId, relation, relationName, phone);
            BatchDataCacheService.personalContactUniqueIdentifyCacheAdd(identify);
        }
    }

    private void initialPersonalAddress() {
        //客户地址信息
        log.info("数据库读取客户地址信息开始");
        List<Object[]> personalAddresses = personalAddressRepository.findAllKeyAndField();
        log.info("客户地址数据放入内存开始");
        for (int i = 0; i < personalAddresses.size(); i++) {
            Object[] personalAddress = personalAddresses.get(i);
            if (personalAddress[0] != null && !personalAddress[0].equals("")
                    && personalAddress[1] != null && !personalAddress[1].equals("")
                    && personalAddress[2] != null && !personalAddress[2].equals("")
                    && personalAddress[3] != null && !personalAddress[3].equals("")
                    && personalAddress[4] != null && !personalAddress[4].equals("")) {
//            SyncDataModel.personalAddressMap.put(personalAddress.getPersonalId()+"-"+personalAddress.getRelation()+"-"+personalAddress.getType(),personalAddress);
                batchDataCacheService.personalAddressMap_put(personalAddress[1]
                        + "-" + personalAddress[2] + "-"
                        + personalAddress[3], personalAddress[0].toString() + "|" + personalAddress[4].toString());
            }
        }
    }

    private void initialPersonalJob() {
        //客户工作信息
        log.info("数据库读取客户工作信息开始");
        List<Object[]> personalJobs = personalJobRepository.findByAllKey();
        log.info("客户工作信息数据放入内存开始");
        HashMap<String, String> personalJobHashMap = new HashMap<>();
        for (int i = 0; i < personalJobs.size(); i++) {
            Object[] personalJob = personalJobs.get(i);
            convertObjToHashMap(personalJobHashMap, personalJob);
//            SyncDataModel.personalJobMap.put(personalJob.getPersonalId(),personalJob);
            if (personalJobHashMap.size() >= 10000) {
                batchDataCacheService.personalJobMap_putAll(personalJobHashMap);
                personalJobHashMap.clear();
            }
        }
        if (personalJobHashMap.size() > 0) {
            batchDataCacheService.personalJobMap_putAll(personalJobHashMap);
            personalJobHashMap.clear();
        }

    }

    private void initialPersonalBank() {
        //客户开户信息
        log.info("数据库读取客户开户信息开始");
        List<Object[]> personalBanks = personalBankRepository.findByAllKey();
        log.info("客户开户信息数据放入内存开始");
        HashMap<String, String> personalBankHashMap = new HashMap<>();
        for (int i = 0; i < personalBanks.size(); i++) {
            Object[] personalBank = personalBanks.get(i);
            convertObjToHashMap(personalBankHashMap, personalBank);
            if (personalBankHashMap.size() >= 10000) {
                batchDataCacheService.personalBankMap_putAll(personalBankHashMap);
                personalBankHashMap.clear();
            }
        }
        if (personalBankHashMap.size() > 0) {
            batchDataCacheService.personalBankMap_putAll(personalBankHashMap);
            personalBankHashMap.clear();
        }

    }

    private void convertObjToHashMap(HashMap<String, String> hashMap, Object[] objs) {
        if (objs.length == 2) {
            if (objs[0] != null) {
                String key = (String) objs[0];
                if (objs[1] != null) {
                    hashMap.put(key, objs[1].toString());
                }

            }
        }
    }

    private void initialPersonalImgFile() {
        //客户影像文件信息
        log.info("数据库读取客户影像文件开始");
        List<Object[]> personalImgFiles = personalImgFileRepository.findByAllKey();
        log.info("客户影像文件数据放入内存开始");
        HashMap<String, String> personalImgFileHashMap = new HashMap<>();
        for (int i = 0; i < personalImgFiles.size(); i++) {
            Object[] personalImgFile = personalImgFiles.get(i);
//            SyncDataModel.personalMap.put(personal.getId(),personal);
            convertObjToHashMap(personalImgFileHashMap, personalImgFile);
//            SyncDataModel.personalImgFileMap.put(personalImgFile.getId(),personalImgFile);
            if (personalImgFileHashMap.size() >= 10000) {
                batchDataCacheService.personalImgFileMap_putAll(personalImgFileHashMap);
                personalImgFileHashMap.clear();
            }
        }
        if (personalImgFileHashMap.size() > 0) {
            batchDataCacheService.personalImgFileMap_putAll(personalImgFileHashMap);
            personalImgFileHashMap.clear();
        }
    }

    private void initialPersonalSocialPlat() {
        //客户社交平台信息
        log.info("数据库读取客户社交平台信息开始");
        List<Object[]> personalSocialPlats = personalSocialPlatRepository.findAllKeyAndUpdateTime();
        log.info("客户社交平台信息数据放入内存开始");
        HashMap<String, String> personalSocialPlatHashMap = new HashMap<>();
        for (int i = 0; i < personalSocialPlats.size(); i++) {
            Object[] personalSocialPlat = personalSocialPlats.get(i);
            convertObjToHashMap(personalSocialPlatHashMap, personalSocialPlat);
//            SyncDataModel.personalSocialPlatMap.put(personalSocialPlat.getId(),personalSocialPlat);
            if (personalSocialPlatHashMap.size() >= 10000) {
                batchDataCacheService.personalSocialPlatMap_putAll(personalSocialPlatHashMap);
                personalSocialPlatHashMap.clear();
            }
        }
        if (personalSocialPlatHashMap.size() > 0) {
            batchDataCacheService.personalSocialPlatMap_putAll(personalSocialPlatHashMap);
            personalSocialPlatHashMap.clear();
        }

    }

    private void initialPersonalAstOperCrdt() {
        //客户资产经营征信信息
        log.info("数据库读取客户资产经营征信信息开始");
        List<Object[]> personalAstOperCrdts = personalAstOperCrdtRepository.findByAllKey();
        log.info("客户资产经营征信信息数据放入内存开始");
        HashMap<String, String> personalAstOperCrdtHashMap = new HashMap<>();
        for (int i = 0; i < personalAstOperCrdts.size(); i++) {
            Object[] personalAstOperCrdt = personalAstOperCrdts.get(i);
            convertObjToHashMap(personalAstOperCrdtHashMap, personalAstOperCrdt);
//            SyncDataModel.personalAstOperCrdtMap.put(personalAstOperCrdt.getId(),personalAstOperCrdt);
            if (personalAstOperCrdtHashMap.size() >= 10000) {
                batchDataCacheService.personalAstOperCrdtMap_putAll(personalAstOperCrdtHashMap);
                personalAstOperCrdtHashMap.clear();
            }
        }
        if (personalAstOperCrdtHashMap.size() > 0) {
            batchDataCacheService.personalAstOperCrdtMap_putAll(personalAstOperCrdtHashMap);
            personalAstOperCrdtHashMap.clear();
        }
    }

    private void initialPersonalFileAttach() {
        //客户文本文件信息
        log.info("数据库读取客户文本文件信息开始");
        List<Object[]> caseFiles = caseFileRepository.findByAllKey();
        log.info("客户文本文件信息数据放入内存开始");
        HashMap<String, String> caseFileHashMap = new HashMap<>();
        for (int i = 0; i < caseFiles.size(); i++) {
            Object[] caseFile = caseFiles.get(i);
            convertObjToHashMap(caseFileHashMap, caseFile);
//            SyncDataModel.caseFileMap.put(caseFile.getId(),caseFile);
            if (caseFileHashMap.size() >= 10000) {
                batchDataCacheService.personalFileAttachMap_putAll(caseFileHashMap);
                caseFileHashMap.clear();
            }
        }
        if (caseFileHashMap.size() > 0) {
            batchDataCacheService.personalFileAttachMap_putAll(caseFileHashMap);
            caseFileHashMap.clear();
        }
    }

    private void dictConvertMap(String typeCode, Map toConvertMap) {
        Iterator<DataDict> dictIterator = dictRepository.findAll(QDataDict.dataDict.typeCode.eq(typeCode)).iterator();
        while (dictIterator.hasNext()) {
            DataDict dataDict = dictIterator.next();
            toConvertMap.put(dataDict.getCode(), dataDict.getName());
        }
    }

    private void initialDictData() {
        //字典枚举初始化
        log.info("初始化字典枚举开始");
        dictConvertMap(DataDict.TypeCode.EDUCATION.getValue(), SyncDataModel.educationMap);
        dictConvertMap(DataDict.TypeCode.MARRAGE.getValue(), SyncDataModel.marrageMap);
        dictConvertMap(DataDict.TypeCode.PROFESSIONAL.getValue(), SyncDataModel.professionalMap);
        dictConvertMap(DataDict.TypeCode.INDUSTRY.getValue(), SyncDataModel.industryMap);
        dictConvertMap(DataDict.TypeCode.POSITION.getValue(), SyncDataModel.positionMap);
        dictConvertMap(DataDict.TypeCode.UNIT_PROPERTY.getValue(), SyncDataModel.unitPropertyMap);
        dictConvertMap(DataDict.TypeCode.CERTIFICATE_TYPE.getValue(), SyncDataModel.certificateTypeMap);
        dictConvertMap(DataDict.TypeCode.PERSONAL_RELATION.getValue(), SyncDataModel.personalRelationMap);

        log.info("初始化字典枚举结束");
    }

    public void initialDataMap() {
        log.info("准备数据初始化......");
        //同步数据库用户数据到内存
        initialProduct();
        initialPersonal();
        initialPersonalContact();
        initialPersonalAddress();
        initialPersonalJob();
        initialPersonalBank();
        initialPersonalImgFile();
        initialPersonalSocialPlat();
        initialPersonalAstOperCrdt();
        initialPersonalFileAttach();
        initialDictData();
        log.info("数据初始化结束......");
    }

    /**
     * HY根据文件解析对应的文件
     *
     * @param path
     */
    public List<String> readHYFileByLines(String path) {
        log.info("读取文件" + path + "--");
        List<String> dataList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            LineIterator it = null;
            try {
                it = FileUtils.lineIterator(file, "utf-8");
                while (it.hasNext()) {
                    String line = it.nextLine();
                    dataList.add(line.trim());
                }
            } catch (Exception e) {
                log.error("读取文件" + path + "失败");
                log.error(e.getMessage(), e);
            } finally {
                LineIterator.closeQuietly(it);
            }
        }
        return dataList;
    }

    /**
     * HY根据文件解析对应的文件
     *
     * @param txtLineList
     */
    public <T> List<T> hyTxtToObjectList(List<String> txtLineList, Class<T> clazz) {
        log.info(clazz.getName() + "char27数据转换为实体类开始---");
        List<T> hyInterfaceDataCaseInfoBeans = new ArrayList<>();
//        List<T> tempList=new ArrayList<>();
        SyncDataModel syncDataModel = new SyncDataModel();
        for (int i = 0; i < txtLineList.size(); i++) {
            String txtLine = txtLineList.get(i);
            T hyInterfaceDataCaseInfoBean = null;
            try {
//                log.debug(clazz.getName()+": char27数据第"+i+"条转换为实体类开始");
                hyInterfaceDataCaseInfoBean = hyTxtToObject(txtLine, clazz, syncDataModel);
//                log.debug(clazz.getName()+": char27数据第"+i+"条转换为实体类成功");
                hyInterfaceDataCaseInfoBeans.add(hyInterfaceDataCaseInfoBean);
            } catch (Exception e) {
                log.error(clazz.getName() + ": char27数据第" + i + "条转换为实体类失败-数据技术解析异常");
                log.error(e.getMessage(), e);
                int exceptionType = 0;
                if (clazz.getName().indexOf("OverdueDetailBean") > 0) {
                    exceptionType = ExceptionData.DataType.OVERDUE_DETAIL.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerDetailBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_DETAIL.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerAccountBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_ACCOUNT.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerFileAttachBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_FILE_ATTACH.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerImgAttachBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_IMG_ATTACH.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerRelationBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_RELATION.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerSocialPlatBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_SOCIAL_PLAT.getValue();
                }
                if (clazz.getName().indexOf("OverdueCustomerAstOperCrdtBean") > 0) {
                    exceptionType = ExceptionData.DataType.CUSTOMER_AST_OPER_CRDT.getValue();
                }
                //保存导入异常数据
                ExceptionData exceptionData = ExceptionData.createInstance(
                        exceptionType,
                        "0",
                        txtLine,
                        ExceptionData.EXCEPTION_CODE_PARSE_TECH, e);
                syncDataModel.getExceptionDataInsertMap().put(
                        exceptionData.getExceptionCode() +
                                exceptionData.getDataType() +
                                exceptionData.getDataId(), exceptionData);
                log.error("数据技术解析异常..." + txtLine + ": " + ExceptionData.getStackTrace(e));
            }
//            tempList.add(hyInterfaceDataCaseInfoBean);
//            log.debug(clazz.getName()+": 临时集合添加元素成功");
//            if(tempList.size()>10000) {
//                hyInterfaceDataCaseInfoBeans.addAll(tempList);
//                tempList.clear();
//                log.debug(clazz.getName()+": hyInterfaceDataCaseInfoBeans添加临时集合成功");
//            }
        }
//        if(tempList.size()>0){
//            hyInterfaceDataCaseInfoBeans.addAll(tempList);
//            tempList.clear();
//        }
        //异常数据信息(新增）
        if (!syncDataModel.getExceptionDataInsertMap().isEmpty()) {
            List<ExceptionData> valueList = new ArrayList<>(syncDataModel.getExceptionDataInsertMap().values());
            exceptionDataRepository.save(valueList);
        }
        log.info(clazz.getName() + "char27数据转换为实体类结束---");
        return hyInterfaceDataCaseInfoBeans;
    }

    String[] overdueDetailColumns = {"intoApplyId", "userId", "clientName", "certificateNo",
            "loanAmount", "productName", "userAccount", "branchName", "intoTime", "merchantName",
            "storeName", "loanPayTime", "overdueDays", "fiveLevel", "applyPeriod", "creditPeriod",
            "applyAmount", "creditAmt", "clearOverdueAmount", "currentMonthDebtAmount", "currentPreRepayPrincipal",
            "beforeCurrentLeftRepayInterest", "leftRepayInterest", "beforeCurrentLeftRepayManagementFee",
            "leftRepayManagementFee", "currentDebtAmount", "loanPurposeName", "leftNum", "contractId",
            "busDate", "loanInvoiceId", "preRepayPrincipal", "flag", "leftRepayFee",
            "beforeCurrentLeftRepayFee", "repayDate", "movingBackFlag", "verificationStatus", "leftOverdueFee",
            "beforeCurrentLeftOverdueFee", "loanAmt", "loanPeriod", "createTime", "updateTime"};

    String[] overdueCustomerDetailColumns = {"userId", "clientName", "certificateNo", "invalidDate", "telephone", "beRecommenderName",
            "companyName", "companyTelephone", "companyTelAreaCode", "companyTelephoneExt", "companyAddress", "contact",
            "contactTel", "livingAddress", "spouseName", "spouseTel", "age", "sex", "education", "marriage", "career",
            "industry", "duty", "unitProperty", "workingYears", "bankcard", "livingProvince", "livingCity", "livingArea",
            "livingProvinceName", "livingCityName", "livingAreaName", "companyProvince", "companyCity", "companyArea",
            "companyProvinceName", "companyCityName", "companyAreaName", "certificateKind", "creditLevel",
            "permanentAddress", "createTime", "updateTime"};

    String[] overdueCustomerAccountColumns = {"userId", "resourceId", "accountKind", "branchName", "accountName",
            "account", "buildDate", "createTime", "updateTime"};

    String[] overdueCustomerFileAttachColumns = {"userId", "contractId", "contractType", "contractStatus", "contractName",
            "creditEndDate", "authorizedValid", "productName", "productNo", "bak1", "bak2", "bak3", "createTime", "updateTime"};

    String[] overdueCustomerImgAttachColumns = {"userId", "resourceId", "imageKind", "imageType", "imageName", "imageUrl",
            "createTime", "updateTime"};

    String[] overdueCustomerRelationColumns = {"userId", "relationUserId", "clientRelation", "relationType", "relationClientName",
            "relationCertificateKind", "relationCertificateNo", "relationTelephone", "createTime", "updateTime"};

    String[] overdueCustomerSocialPlatColumns = {"userId", "stationId", "socialType", "account", "nickName", "createTime", "updateTime"};

    String[] overdueCustomerAstOperCrdtColumns = {"userId", "resourceId", "resourceType", "originalData", "createTime", "updateTime"};

    /**
     * HY将txtLine转化为对象
     *
     * @param txtLine
     * @return
     */
    public <T> T hyTxtToObject(String txtLine, Class<T> clazz, SyncDataModel syncDataModel) throws Exception {
        String[] columnValues = txtLine.split("\u001B");
        T t = clazz.newInstance();
        String[] tempClumns = null;
        if (clazz.getName().indexOf("OverdueDetailBean") > 0) {
            tempClumns = overdueDetailColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerDetailBean") > 0) {
            tempClumns = overdueCustomerDetailColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerAccountBean") > 0) {
            tempClumns = overdueCustomerAccountColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerFileAttachBean") > 0) {
            tempClumns = overdueCustomerFileAttachColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerImgAttachBean") > 0) {
            tempClumns = overdueCustomerImgAttachColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerRelationBean") > 0) {
            tempClumns = overdueCustomerRelationColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerSocialPlatBean") > 0) {
            tempClumns = overdueCustomerSocialPlatColumns;
        }
        if (clazz.getName().indexOf("OverdueCustomerAstOperCrdtBean") > 0) {
            tempClumns = overdueCustomerAstOperCrdtColumns;
        }

        for (int i = 0; i < tempClumns.length; i++) {
            String txtColumn = tempClumns[i];
            if (columnValues.length > i && columnValues[i] != null) {
                Field field = clazz.getDeclaredField(txtColumn);
                field.setAccessible(true);
                field.set(t, columnValues[i]);
            }

        }
        return t;
    }


    /**
     * 获取核心推送的逾期数据
     */
    public void getData() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        BooleanBuilder builder = new BooleanBuilder();
        QSysParam qSysParam = QSysParam.sysParam;
        builder.and(qSysParam.code.eq(Constants.CORE_OVER_DATA_URL));
        builder.and(qSysParam.companyCode.isNull());
        Iterator<SysParam> iterator = sysParamRepository.findAll(builder).iterator();
        if (iterator.hasNext()) { //判断标志文件是否存在
            SysParam sys = iterator.next();
            String fileOkUrl = sys.getValue().concat("/").concat(sdf.format(date)).concat("/").concat(sdf.format(date)).concat(".ok");
            File file = new File(fileOkUrl);
            if (file.exists()) {
                //获取逾期转正成的数据
                BooleanBuilder boolider = new BooleanBuilder();
                boolider.and(qSysParam.code.eq(Constants.CORE_OVER_DATA_URL));
                boolider.and(qSysParam.companyCode.isNull());
                Iterator<SysParam> paramIterator = sysParamRepository.findAll(boolider).iterator();
                if (paramIterator.hasNext()) {
                    SysParam sysParam = paramIterator.next();
                    String fileBUrl = sysParam.getValue().concat("/").concat(sdf.format(date)).concat("/").concat(sdf.format(date)).concat("_B.txt");
                    updateNormal(fileBUrl);
                }

                //获取数据文件的路径
                BooleanBuilder booleanBuilder = new BooleanBuilder();
                booleanBuilder.and(qSysParam.code.eq(Constants.CORE_OVER_DATA_URL));
                booleanBuilder.and(qSysParam.companyCode.isNull());
                Iterator<SysParam> sysParamIterator = sysParamRepository.findAll(booleanBuilder).iterator();
                if (sysParamIterator.hasNext()) {
                    SysParam sysParam = sysParamIterator.next();
                    String fileAUrl = sysParam.getValue().concat("/").concat(sdf.format(date)).concat("/").concat(sdf.format(date)).concat("_A.txt");
                    log.info("开始读取文件:" + sdf.format(new Date()));
                    List<String> dataList = readFileByLines(fileAUrl, 2);
                    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
                    String batch = sd.format(date);
                    if (!dataList.isEmpty()) {
                        Date closeDate = getCloseDate();
                        processSyncData(dataList, batch, closeDate);
                    }
                }

                String companyName = "杭银消费金融";
                Iterator<Company> companyIterator = companyRepository.findAll(QCompany.company.chinaName.eq(companyName)).iterator();
                if (companyIterator.hasNext()) {
                    Company company = companyIterator.next();
                    //执行案件分配策略
                    log.info("逾期数据入库结束，开始进行策略分配........");
                    DivisionCaseParamModel divisionCaseParamModel = new DivisionCaseParamModel();
                    divisionCaseParamModel.setCompanyCode(company.getCode());
                    restTemplate.postForEntity("http://dataimp-service/api/obtainTaticsStrategyController/everyDayDivisionCase", divisionCaseParamModel, Void.class);
                    /**
                     * 如果当前日期是本月的第一天，则执行按月的分案策略。
                     */
                    boolean flag = ZWDateUtil.todayIsFristDayInMonth();
                    if (flag) {
                        restTemplate.postForEntity("http://dataimp-service/api/obtainTaticsStrategyController/monthEarlyDivisionCase", divisionCaseParamModel, Void.class);
                    }
                    log.info("策略分配结束，开始进行对委外案件的处理........");
                    //对委外案件进行处理
                    updateOutSourcePool();
                }
                log.info("晚间跑批结束........");
            } else {
                log.info("逾期数据还没有生成对应的文件........");
            }
        } else {
            log.info("请检查判断逾期文件是否生成的ok文件路径........");
        }
    }

    public void processSyncData(List<String> dataList, String batch, Date closeDate) {
        //1000条一个线程处理
        if (dataList.size() <= 500 && dataList.size() > 0) {
            synDataSaveService.processSynDatalimt(dataList, batch, closeDate);
        } else {
            List<String> perDataList = new ArrayList<>();
            perDataList.addAll(dataList.subList(0, 500));
            synDataSaveService.processSynDatalimt(perDataList, batch, closeDate);
            dataList.removeAll(perDataList);
            processSyncData(dataList, batch, closeDate);
        }
    }

    /**
     * 对委外案件和案件还款记录进行处理
     */
//    @Transactional
    public void updateOutSourcePool() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        //查询委外案件
        booleanBuilder.and(qCaseInfo.casePoolType.in(CaseInfo.CasePoolType.OUTER.getValue()));
        Iterator<CaseInfo> caseInfoIterator = caseInfoRepository.findAll(booleanBuilder).iterator();
        while (caseInfoIterator.hasNext()) {
            CaseInfo caseInfo = caseInfoIterator.next();
            BooleanBuilder builder = new BooleanBuilder();
            QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
            builder.and(qOutsourcePool.caseInfo.id.eq(caseInfo.getId()));
            Iterator<OutsourcePool> iterator = outsourcePoolRepository.findAll(builder).iterator();
            //如果委外案件没有查询出来，则做相应的处理
            if (!iterator.hasNext()) {
                OutsourcePool outsourcePool = new OutsourcePool();
                outsourcePool.setCaseInfo(caseInfo);//案件信息
                outsourcePool.setOutTime(new Date());//委外时间..
                outsourcePool.setOutStatus(OutsourcePool.OutStatus.TO_OUTSIDE.getCode());//委外状态
                BigDecimal b2 = caseInfo.getRealPayAmount();//实际还款金额
                if (Objects.isNull(b2)) {
                    b2 = BigDecimal.ZERO;
                }
                BigDecimal b1 = caseInfo.getOverdueAmount();//原案件金额
                outsourcePool.setContractAmt(b1.subtract(b2));
                outsourcePool.setOutoperationStatus(null);//委外操作状态
                outsourcePool.setOperator("administrator");
                outsourcePool.setOperateTime(new Date());
                outsourcePool.setOverduePeriods(caseInfo.getPayStatus());
                outsourcePoolRepository.save(outsourcePool);
            }
        }
    }


    /**
     * 更新逾期转正常案件
     *
     * @param path
     */
//    @Transactional
    public void updateNormal(String path) {
        List<String> stringList = readFileByLines(path, 1);
        List<CaseInfo> caseInfoList = new ArrayList<>();
        List<CaseInfoDistributed> caseInfoDistributedList = new ArrayList<>();
        List<OutsourcePool> outsourcePoolList = new ArrayList<>();
        if (stringList != null && stringList.size() > 0) {
            for (String data : stringList) {
                List<CtmInterfaceDataBean> ctmInterfaceDataBeanList = jsonToObject(data);
                if (ctmInterfaceDataBeanList != null && ctmInterfaceDataBeanList.size() > 0) {
                    CtmInterfaceDataBean ctmInterfaceDataBean = ctmInterfaceDataBeanList.get(0);
                    String caseNumber = ctmInterfaceDataBean.getApplNo();
                    CaseInfo caseInfo = caseInfoRepository.findOne(QCaseInfo.caseInfo.caseNumber.eq(caseNumber));
                    if (Objects.nonNull(caseInfo)) {
                        caseInfo.setCollectionStatus(CaseInfo.CollectionStatus.NORMAL.getValue());
                        caseInfo.setPayStatus("M0");//催收状态
                        caseInfo.setOverduePeriods(0);//逾期期数
                        caseInfo.setOverdueDays(0);//逾期天数
                        caseInfo.setLatelyCollector(caseInfo.getCurrentCollector());//上一个催收员
                        caseInfo.setCurrentCollector(null);//当前催收员
                        caseInfo.setRecoverRemark(0);//回收标志
                        caseInfo.setCleanDate(new Date());//归c时间
                        caseInfoList.add(caseInfo);
                        OutsourcePool outsourcePool = outsourcePoolRepository.findOne(QOutsourcePool.outsourcePool.caseInfo.id.eq(caseInfo.getId()));
                        if (Objects.nonNull(outsourcePool)) {
                            outsourcePool.setOutStatus(OutsourcePool.OutStatus.CleanUp.getCode());
                            outsourcePoolList.add(outsourcePool);
                        }
                    } else {
                        CaseInfoDistributed caseInfoDistributed = caseInfoDistributedRepository.findOne(QCaseInfoDistributed
                                .caseInfoDistributed.caseNumber.eq(caseNumber));
                        if (Objects.nonNull(caseInfoDistributed)) {
                            caseInfoDistributed.setCollectionStatus(CaseInfo.CollectionStatus.NORMAL.getValue());
                            caseInfoDistributed.setPayStatus("M0");
                            caseInfoDistributed.setOverduePeriods(0);
                            caseInfoDistributed.setOverdueDays(0);
                            caseInfoDistributed.setCleanDate(new Date());//归c时间
                            caseInfoDistributedList.add(caseInfoDistributed);
                        }
                    }
                }
            }
            caseInfoRepository.save(caseInfoList);
            caseInfoDistributedRepository.save(caseInfoDistributedList);
            outsourcePoolRepository.save(outsourcePoolList);
        }
    }

    /**
     * 根据文件解析对应的文件
     *
     * @param path
     */
    public List<String> readFileByLines(String path, Integer type) {
        List<String> dataList = new ArrayList<>();
        File file = new File(path);
        if (file.exists()) {
            LineIterator it = null;
            try {
                it = FileUtils.lineIterator(file, "utf-8");
                while (it.hasNext()) {
                    String line = it.nextLine();
                    dataList.add("[".concat(line).concat("]"));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                LineIterator.closeQuietly(it);
            }
        }
        return dataList;
    }

    /**
     * 将json串转化为对象
     *
     * @param json
     * @return
     */
    public List<CtmInterfaceDataBean> jsonToObject(String json) {
        JSONArray jsonArray = JSONArray.parseArray(json);
        List<CtmInterfaceDataBean> ctmInterfaceDataBean = jsonArray.toJavaList(CtmInterfaceDataBean.class);
        return ctmInterfaceDataBean;
    }


    /**
     * 获取结案日期
     *
     * @return
     */
    public Date getCloseDate() {
        Calendar cal = Calendar.getInstance();//取得3个月后时间
        cal.add(Calendar.MONTH, 3);
        Date date = cal.getTime();
        return date;
    }

    public SysParam getSysParamByCondition(User user, String code) {
        BooleanBuilder exp = new BooleanBuilder();
        exp.and(QSysParam.sysParam.code.eq(code));
        exp.and(QSysParam.sysParam.companyCode.eq(user.getCompanyCode()));
        exp.and(QSysParam.sysParam.status.eq(SysParam.StatusEnum.Start.getValue()));
        return sysParamRepository.findOne(exp);
    }

    private class SftpParams {
        private String ftpUserName;
        private String ftpPassword;
        private String ftpHost;
        private String ftpPort;
        private String fileDir;
        private String alarmTime;
        private String stopLoopTime;
        private String loopInterval;

        public String getFtpUserName() {
            return ftpUserName;
        }

        public String getFtpPassword() {
            return ftpPassword;
        }

        public String getFtpHost() {
            return ftpHost;
        }

        public String getFtpPort() {
            return ftpPort;
        }

        public String getFileDir() {
            return fileDir;
        }

        public String getAlarmTime() {
            return alarmTime;
        }

        public String getStopLoopTime() {
            return stopLoopTime;
        }

        public String getLoopInterval() {
            return loopInterval;
        }

        public SftpParams invoke() {
            BooleanBuilder builder = new BooleanBuilder();
            QSysParam qSysParam = QSysParam.sysParam;
            builder.and(qSysParam.field.eq(Constants.CORE_PUSH_FTP_FIELD));
            Iterator<SysParam> iterator = sysParamRepository.findAll(builder).iterator();

            while (iterator.hasNext()) {
                SysParam sysParam = iterator.next();
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_HOST)) {
                    ftpHost = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_PORT)) {
                    ftpPort = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_USERNAME)) {
                    ftpUserName = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_PD)) {
                    ftpPassword = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_FILE_URL)) {
                    fileDir = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_ALARMTIME)) {
                    alarmTime = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_STOPLOOPTIME)) {
                    stopLoopTime = sysParam.getValue();
                }
                if (sysParam.getCode().equals(Constants.CORE_PUSH_FTP_LOOPINTERVAL)) {
                    loopInterval = sysParam.getValue();
                }
            }
            return this;
        }
    }

    private void sendReminderMsg(String title, String content, ReminderType type) {
        //发站内信
        List<User> users = userService.getUserByRoleName("超级管理员");
        for (User user : users) {
            log.info("发送站内跑批消息对象: " + user.getUserName());
            String id = user.getId();
            SendReminderMessage sendReminderMessage = new SendReminderMessage();
            sendReminderMessage.setTitle(title);
            sendReminderMessage.setContent(content);
            sendReminderMessage.setType(type);
            sendReminderMessage.setMode(ReminderMode.POPUP);
            sendReminderMessage.setCreateTime(new Date());
            sendReminderMessage.setUserId(id);
            reminderService.sendReminder(sendReminderMessage);
            log.info("发送站内跑批消息对象: " + user.getUserName() + ", 发送成功！");
            if(user.getUserName().contains("admin")){
                try {
                    log.info("发送短信跑批提示对象: " + user.getUserName());
                    SysParam sysParam=getSysParamByCondition(user,"SysParam.sms.tag.phone");
                    if(Objects.nonNull(sysParam.getValue())){
                        String[] phoneList=sysParam.getValue().split("，");
                        HangYinSmsMessage hangYinSmsMessage = new HangYinSmsMessage();
                        hangYinSmsMessage.setMessageTopic("行银催收跑批提示短信");
                        hangYinSmsMessage.setContent(content);
                        ArrayList<String> phoneNumList = new ArrayList<>();
                        for(int i=0;i<phoneList.length;i++){
                            if(Objects.nonNull(phoneList[i])){
                                phoneNumList.add(phoneList[i]);
                            }
                        }
                        hangYinSmsMessage.setPhoneNumList(phoneNumList);
                        log.info("开始调用发送短信接口,请求参数:{}",hangYinSmsMessage);
                        SysParam smsAddressParam = getSysParamByCondition(user, Constants.SMS_PUSH_ADDRESS);
                        String smsAddress = smsAddressParam.getValue();
                        String httpStr = HttpUtil.doPost(smsAddress, JSONObject.toJSON(hangYinSmsMessage));
                        log.info("调用发送短信接口结束,返回结果:{}",httpStr);
                        JSONObject resultJson = JSONObject.parseObject(httpStr);
                        if (resultJson.get("code").equals("0")){
                            log.info("发送短信跑批提示成功!");
                        }else {
                            log.info("发送短信跑批提示失败!");
                        }
                    }else{
                        log.info("未配置跑批短信提醒，请配置！");
                    }
                } catch (Exception ex) {
                    //发送失败
                    log.error("跑批短信发送失败："+ex.toString());
                }
            }
        }
    }
    private class SftpDownloadPackage {
        private boolean myResult;
        private String dateStr;
        private boolean test;
        private String destinationFolder;
        private String unzipFile;

        public SftpDownloadPackage(String dateStr, boolean test) {
            this.dateStr = dateStr;
            this.test = test;
        }

        boolean is() {
            return myResult;
        }

        public String getDestinationFolder() {
            return destinationFolder;
        }

        public String getUnzipFile() {
            return unzipFile;
        }

        public SftpDownloadPackage invoke(TaskBatchImportLog taskBatchImportLog) {
            //获取sftp参数
            SftpParams sftpParams = new SftpParams().invoke();
            String ftpHost = sftpParams.getFtpHost();
            String ftpPort = sftpParams.getFtpPort();
            String ftpUserName = sftpParams.getFtpUserName();
            String ftpPassword = sftpParams.getFtpPassword();
            String fileDir = sftpParams.getFileDir();
            String alarmTime = sftpParams.getAlarmTime();
            String stopLoopTime = sftpParams.getStopLoopTime();
            String loopInterval = sftpParams.getLoopInterval();
            destinationFolder = tempFileDir;
            unzipFile = null;
            //sftp下载数据包
            if (!ftpHost.equals("")
                    && !ftpPort.equals("")
                    && !ftpUserName.equals("")
                    && !ftpPassword.equals("")) {
                log.info("sftp配置: host: " + ftpHost + ", port: " + ftpPort + ", username: " + ftpUserName);
                if (!test) {
                    try {
                        //                String unzipFile = FtpOperation.getDataFile(fileDir,"ZWJK_BATCHDATA_"+dateStr,destinationFolder);
                        SFTPUtil sftpUtil = new SFTPUtil(ftpUserName, ftpPassword, ftpHost, Integer.valueOf(ftpPort));
                        boolean isSendMsg = false;
                        boolean isExistSFTPFile = false;
                        //如果是手动触发任务，不再执行轮询查找
                        if (taskBatchImportLog.getTaskType().intValue() == TaskBatchImportLog.BatchTaskType.MANUAL.getValue().intValue()) {
                            isExistSFTPFile = true;
                        }
                        while (!isExistSFTPFile) {
                            log.info("轮询查找sftp批量包......");
                            sftpUtil.login();
                            isExistSFTPFile = sftpUtil.check(fileDir, "ZWJK_BATCHDATA_" + dateStr);
                            sftpUtil.logout();
                            if (isExistSFTPFile) {
                                log.info("轮询查询sftp批量包已找到......");
                                break;
                            }
                            String currentTime = DateUtil.getDate(new Date(), "HH:mm");
                            if (currentTime.compareTo(alarmTime) > 0 && !isSendMsg) {
                                //发站内信
                                sendReminderMsg("跑批预警消息",
                                        "跑批预警-跑批任务未找到核心推送的SFTP数据包，请紧急处理！",
                                        ReminderType.BATCH_TASK_WARNING);

                                isSendMsg = true;
                            } else if (currentTime.compareTo(stopLoopTime) > 0) {
                                log.error("定时跑批任务SFTP到截止时间" + stopLoopTime + "还未发现批量包，中止跑批...");
                                taskBatchImportLog.setFailedCode("定时跑批任务SFTP到截止时间" + stopLoopTime + "还未发现批量包，中止跑批...");
                                taskBatchImportLog.setFailedMsg("定时跑批任务SFTP到截止时间" + stopLoopTime + "还未发现批量包，中止跑批...");
                                myResult = true;
                                return this;
                            }
                            int seconds = Integer.valueOf(loopInterval);
                            log.info("轮询查询sftp批量包未找到，" + seconds + "秒后继续查找......");
                            Thread.sleep(seconds * 1000L);
                        }
                        sftpUtil.login();
                        unzipFile = sftpUtil.download(fileDir, "ZWJK_BATCHDATA_" + dateStr, destinationFolder);
                        sftpUtil.logout();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        log.error("SFTP下载批量文件包出现异常........");
                        taskBatchImportLog.setFailedCode("SFTP下载批量文件包出现异常........");
                        taskBatchImportLog.setFailedMsg("SFTP下载批量文件包出现异常........");
                        myResult = true;
                        return this;
                    }
                } else {
                    unzipFile = "test.zip";
                }

            } else {
                log.info("SFTP批量文件包配置路径不正确...");
                taskBatchImportLog.setFailedCode("SFTP批量文件包配置路径不正确...");
                taskBatchImportLog.setFailedMsg("SFTP批量文件包配置路径不正确...");
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}