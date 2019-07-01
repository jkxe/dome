package cn.fintecher.pangolin.business.job;

import cn.fintecher.pangolin.business.config.ConfigureQuartz;
import cn.fintecher.pangolin.business.repository.*;
import cn.fintecher.pangolin.business.service.ReminderService;
import cn.fintecher.pangolin.entity.*;
import cn.fintecher.pangolin.entity.message.SendReminderMessage;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by sunyanping on 2017/10/16.
 */
@Service("caseRecoverJob")
@DisallowConcurrentExecution
public class CaseRecoverJob implements Job {
    Logger logger = LoggerFactory.getLogger(CaseRecoverJob.class);

    @Autowired
    SchedulerFactoryBean schedulerFactory;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    SysParamRepository sysParamRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;
    @Autowired
    CaseInfoReturnRepository caseInfoReturnRepository;
    @Autowired
    OutsourcePoolRepository outsourcePoolRepository;
    @Autowired
    ReminderService reminderService;
    @Autowired
    CaseTurnRecordRepository caseTurnRecordRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Company> companyList = companyRepository.findAll();

        for (Company company : companyList) {
            SysParam sysParam = null;
            try {
                QSysParam qSysParam = QSysParam.sysParam;
                sysParam = sysParamRepository.findOne(qSysParam.code.eq(Constants.SYSPARAM_RECOVER).and(qSysParam.companyCode.eq(company.getCode())));
            } catch (Exception e) {
                sysParam = null;
                logger.error("获取案件回收系统参数错误");
            }
            if (sysParam != null && sysParam.getStatus() == 0) {
                try {
                    logger.debug("委外案件自动回收任务调度开始...");
                    // 委外自动回收
                    QOutsourcePool qOutsourcePool = QOutsourcePool.outsourcePool;
                    Iterable<OutsourcePool> outsourcePools = outsourcePoolRepository.findAll(qOutsourcePool.caseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()) // 未回收的
                            .and(qOutsourcePool.overOutsourceTime.before(new Date())) // 到期的
                            .and(qOutsourcePool.outStatus.ne(OutsourcePool.OutStatus.OUTSIDE_OVER.getCode()))// 除过委外已结案的
                            .and(qOutsourcePool.caseInfo.recoverWay.eq(CaseInfo.RecoverWay.AUTO.getValue()))
                            .and(qOutsourcePool.caseInfo.companyCode.eq(company.getCode())));// 需要自动回收的
                    Iterator<OutsourcePool> iterator1 = outsourcePools.iterator();
                    List<CaseInfo> caseInfoList = new ArrayList<>();
                    List<CaseTurnRecord> caseTurnRecords = new ArrayList<>();
                    List<CaseInfoReturn> caseInfoReturnList = new ArrayList<>();
                    List<OutsourcePool> outsourcePoolList = new ArrayList<>();
                    while (iterator1.hasNext()) {
                        OutsourcePool outsourcePool = iterator1.next();
                        outsourcePoolList.add(outsourcePool);

                        /**
                         * 对于委外收回的案件，都是达到委外结束时间的案件。需要设置的属性如下：
                         * 1 催收状态 CollectionStatus  设置为: 待分配
                         * 2 操作时间 : 当前时间
                         * 3 回收标志 : RECOVERED(1, "已回收");
                         */
                        CaseInfo caseInfo = outsourcePool.getCaseInfo();
                        //以案件为维度
                        List<CaseInfo> byCaseNumber = caseInfoRepository.findByCaseNumber(caseInfo.getCaseNumber());
                        for (CaseInfo info : byCaseNumber) {
                            info.setOperatorTime(ZWDateUtil.getNowDate());
                            info.setCollectionStatus(CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
                            info.setRecoverRemark(CaseInfo.RecoverRemark.RECOVERED.getValue());
                            caseInfoList.add(caseInfo);
                        }

                        CaseInfoReturn caseInfoReturn = new CaseInfoReturn();
                        caseInfoReturn.setReason("案件到期自动回收");
                        caseInfoReturn.setOperatorTime(new Date());
                        caseInfoReturn.setSource(CaseInfoReturn.Source.OUTSOURCE.getValue()); // 回收来源-委外
                        caseInfoReturn.setCaseId(caseInfo);
                        caseInfoReturn.setCaseNumber(caseInfo.getCaseNumber());
                        caseInfoReturn.setOutBatch(outsourcePool.getOutBatch());
                        caseInfoReturn.setOutsName(Objects.nonNull(outsourcePool.getOutsource()) ? outsourcePool.getOutsource().getOutsName() : "");
                        caseInfoReturn.setOutTime(outsourcePool.getOutTime());
                        caseInfoReturn.setOverOutsourceTime(outsourcePool.getOverOutsourceTime());
                        caseInfoReturn.setCompanyCode(outsourcePool.getCompanyCode());
                        caseInfoReturn.setReturnType(CaseInfoReturn.ReturnType.AUTOMATIC.getValue());
                        caseInfoReturnList.add(caseInfoReturn);

                        // 委外回收(自动回收)流转记录新增
                        //分配完成新增流转记录
                        CaseTurnRecord caseTurnRecord = new CaseTurnRecord();
                        BeanUtils.copyProperties(caseInfo, caseTurnRecord); //将案件信息复制到流转记录
                        caseTurnRecord.setId(null); //主键置空
//                    caseTurnRecord.setCaseId(caseInfo.getId()); //案件ID
                        caseTurnRecord.setCaseNumber(caseInfo.getCaseNumber());
//                        caseTurnRecord.setDepartId(batchInfoModel.getCollectionUser().getDepartment().getId()); //部门ID
//                        caseTurnRecord.setReceiveUserRealName(batchInfoModel.getCollectionUser().getRealName()); //接受人名称
//                        caseTurnRecord.setReceiveDeptName(batchInfoModel.getCollectionUser().getDepartment().getName()); //接收部门名称
                        caseTurnRecord.setReceiveUserId(caseInfo.getCurrentCollector().getId()); //接受人ID
                        caseTurnRecord.setTriggerAction(CaseTurnRecord.triggerActionEnum.AUTO_RECYCLING.getValue()); // 触发动作
                        if (Objects.nonNull(caseInfo.getLatelyCollector())) {
                            caseTurnRecord.setCurrentCollector(caseInfo.getLatelyCollector().getId()); //当前催收员ID
                        }
                        caseTurnRecord.setCirculationType(2); //流转类型 2-正常流转
//                        caseTurnRecord.setOperatorUserName(tokenUser.getUserName()); //操作员用户名
                        caseTurnRecord.setOperatorTime(ZWDateUtil.getNowDateTime()); //操作时间
                        caseTurnRecords.add(caseTurnRecord);
                        //循环外

                    }
                    caseTurnRecordRepository.save(caseTurnRecords);
                    caseInfoRepository.save(caseInfoList);
                    caseInfoReturnRepository.save(caseInfoReturnList);
                    outsourcePoolRepository.delete(outsourcePoolList);
                    logger.debug("委外案件自动回收任务调度结束...");
                } catch (Exception e) {
                    logger.error("委外案件自动回收任务调度错误");
                    logger.error(e.getMessage(), e);
                }

                try {
                    logger.debug("案件手动回收提醒任务调度开始...");
                    // 内催的手动回收
                    QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
                    Iterable<CaseInfo> allM = caseInfoRepository.findAll(qCaseInfo.casePoolType.eq(CaseInfo.CasePoolType.INNER.getValue())
                            .and(qCaseInfo.collectionStatus.ne(CaseInfo.CollectionStatus.CASE_OVER.getValue()))//除过已结案
                            .and(qCaseInfo.recoverRemark.eq(CaseInfo.RecoverRemark.NOT_RECOVERED.getValue()))//未回收的
                            .and(qCaseInfo.recoverWay.eq(CaseInfo.RecoverWay.MANUAL.getValue()))//需要手动回收的
                            .and(qCaseInfo.closeDate.before(new Date()))
                            .and(qCaseInfo.companyCode.eq(company.getCode())));//到期日期
                    Iterator<CaseInfo> iteratorM = allM.iterator();
                    while (iteratorM.hasNext()) {
                        CaseInfo caseInfo = iteratorM.next();
                        if (Objects.nonNull(caseInfo.getCurrentCollector())) {
                            String id = caseInfo.getCurrentCollector().getId();
                            String title = "案件到期";
                            String content = "案件编号[".concat(caseInfo.getCaseNumber()).concat("],批次号[").concat(caseInfo.getBatchNumber()).concat("]的案件到期,请进行回收。");
                            SendReminderMessage sendReminderMessage = new SendReminderMessage();
                            sendReminderMessage.setTitle(title);
                            sendReminderMessage.setContent(content);
                            sendReminderMessage.setType(ReminderType.CASE_EXPIRE);
                            sendReminderMessage.setMode(ReminderMode.POPUP);
                            sendReminderMessage.setCreateTime(new Date());
                            sendReminderMessage.setUserId(id);
                            reminderService.sendReminder(sendReminderMessage);
                        }
                    }
                    logger.debug("案件手动回收提醒任务调度结束...");
                } catch (Exception e) {
                    logger.error("案件自动回收任务调度错误");
                    logger.error(e.getMessage(), e);
                }
                logger.debug("案件回收任务调度结束");
            }
        }
    }

    @Bean(name = "createCaseRecoverJob")
    public List<CronTriggerFactoryBean> CreateCaseRecoverJob() {
        List<CronTriggerFactoryBean> cronTriggerFactoryBeanList = new ArrayList<>();
        try {
            QSysParam qSysParam = QSysParam.sysParam;
            //获取公司码
            List<Company> companyList = companyRepository.findAll();
            for (Company company : companyList) {
                SysParam sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(company.getCode())
                        .and(qSysParam.code.eq(Constants.SYSPARAM_RECOVER))
                        .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
                if (Objects.nonNull(sysParam)) {
                    String cron = sysParam.getValue();
                    String hours = cron.substring(0, 2);
                    String mis = cron.substring(2, 4);
                    String second = cron.substring(4, 6);
                    cron = second.concat(" ").concat(mis).concat(" ").concat(hours).concat(" * * ?");
                    JobDetail jobDetail = ConfigureQuartz.createJobDetail(CaseRecoverJob.class, Constants.RECOVER_JOB_GROUP,
                            Constants.RECOVER_JOB_NAME.concat("_").concat(company.getCode()),
                            Constants.RECOVER_JOB_DESC.concat("_").concat(company.getCode()));
                    JobDataMap jobDataMap = new JobDataMap();
                    jobDataMap.put("companyCode", company.getCode());
                    jobDataMap.put("sysParamCode", Constants.SYSPARAM_RECOVER_STATUS);
                    if(!schedulerFactory.getScheduler().checkExists(jobDetail.getKey())) {
                        if (schedulerFactory.getScheduler().getTriggersOfJob(jobDetail.getKey()).isEmpty()) {
                            CronTriggerFactoryBean cronTriggerFactoryBean = ConfigureQuartz.createCronTrigger(Constants.RECOVER_TRIGGER_GROUP,
                                    Constants.RECOVER_TRIGGER_NAME.concat("_").concat(company.getCode()),
                                    "caseRecoverJobBean".concat("_").concat(company.getCode()),
                                    Constants.RECOVER_TRIGGER_DESC.concat("_").concat(company.getCode()), jobDetail, cron, jobDataMap);
                            cronTriggerFactoryBean.afterPropertiesSet();
                            //加入调度器
                            schedulerFactory.getScheduler().scheduleJob(jobDetail, cronTriggerFactoryBean.getObject());
                            cronTriggerFactoryBeanList.add(cronTriggerFactoryBean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cronTriggerFactoryBeanList;
    }
}
