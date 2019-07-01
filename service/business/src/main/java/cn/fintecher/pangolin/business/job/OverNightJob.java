package cn.fintecher.pangolin.business.job;

import cn.fintecher.pangolin.business.config.ConfigureQuartz;
import cn.fintecher.pangolin.business.repository.CompanyRepository;
import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.business.service.JobTaskService;
import cn.fintecher.pangolin.business.service.OverNightBatchService;
import cn.fintecher.pangolin.entity.Company;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: PeiShouWen
 * @Description: pangolin 夜间批量
 * @Date 11:00 2017/8/9
 */
@Service("overNightJob")
@DisallowConcurrentExecution
public class OverNightJob implements Job {

    Logger logger = LoggerFactory.getLogger(OverNightJob.class);

    @Autowired
    SchedulerFactoryBean schedFactory;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SysParamRepository sysParamRepository;
    @Autowired
    JobTaskService jobTaskService;

    @Autowired
    OverNightBatchService overNightBatchService;

    @Autowired
    UserRepository userRepository;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
       /* //中通天鸿的通话记录同步到跟进记录
        Date data = new Date();
        QCaseFollowupRecord qCaseFollowupRecord = QCaseFollowupRecord.caseFollowupRecord;
        Iterator<CaseFollowupRecord> caseFollowupRecordIterator = caseFollowupRecordRepository.findAll(qCaseFollowupRecord.callType.eq(164).and(qCaseFollowupRecord.operatorTime.gt(data)).and(qCaseFollowupRecord.connSecs.isNull()).and(qCaseFollowupRecord.startTime.isNull()).and(qCaseFollowupRecord.endTime.isNull())).iterator();
        List<CaseFollowupRecord> caseFollowupRecordList = IteratorUtils.toList(caseFollowupRecordIterator);
        if (caseFollowupRecordList.size() > 0) {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://common-service/api/smaController/getTianHongVoice", String.class);
            JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<SmaDetailReport> smaDetailReportList = new ArrayList<>();
            if (!jsonArray.isEmpty()) {
                for (Object obj : jsonArray) {
                    JSONObject item = JSON.parseObject(JSON.toJSONString(obj));
                    SmaDetailReport smaDetailReport = new SmaDetailReport();
                    smaDetailReport.setCallId(item.getString("call_id"));
                    smaDetailReport.setStartTime(item.getDate("start_time"));
                    smaDetailReport.setEndTime(item.getDate("end_time"));
                    smaDetailReport.setConnSecs(item.getInteger("conn_secs"));
                    smaDetailReport.setAgNum(item.getString("ag_num"));
                    smaDetailReport.setGroupId(item.getString("group_id"));
                    smaDetailReport.setAgName(item.getString("ag_name"));
                    smaDetailReport.setAgPhone(item.getString("ag_phone"));
                    smaDetailReport.setCusPhone(item.getString("cus_phone"));
                    smaDetailReport.setQueName(item.getString("que_name"));
                    smaDetailReport.setCallType(item.getString("call_type"));
                    smaDetailReport.setCusPhoneAreacode(item.getString("cus_phone_areacode"));
                    smaDetailReport.setCusPhoneAreaname(item.getString("cus_phone_areaname"));
                    smaDetailReport.setAgPhoneAreacode(item.getString("ag_phone_areacode"));
                    smaDetailReport.setAgPhoneAreaname(item.getString("ag_phone_areaname"));
                    smaDetailReportList.add(smaDetailReport);
                }
            }

            for (SmaDetailReport smaDetailReport : smaDetailReportList) {
                for (CaseFollowupRecord caseFollowupRecord : caseFollowupRecordList) {
                    if (Objects.equals(smaDetailReport.getCallId(), caseFollowupRecord.getTaskId()) && Objects.nonNull(caseFollowupRecord.getTaskId())) {
                        if (Objects.nonNull(smaDetailReport.getStartTime())) {
                            caseFollowupRecord.setStartTime(smaDetailReport.getStartTime());
                        }
                        if (Objects.nonNull(smaDetailReport.getEndTime())) {
                            caseFollowupRecord.setEndTime(smaDetailReport.getEndTime());
                        }
                        if (Objects.nonNull(smaDetailReport.getConnSecs())) {
                            caseFollowupRecord.setConnSecs(smaDetailReport.getConnSecs());
                        }
                        caseFollowupRecordRepository.save(caseFollowupRecord);
                    }
                }
            }
        }*/
        JobDataMap jobDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        overNightBatchService.doOverNightTask(jobDataMap);
    }


    @Bean(name = "createOverNightJob")
    public List<CronTriggerFactoryBean> CreateOverNightJob() {
        List<CronTriggerFactoryBean> cronTriggerFactoryBeanList = new ArrayList<>();
        try {
            //获取公司码
            List<Company> companyList = companyRepository.findAll();
            for (Company company : companyList) {
                QSysParam qSysParam = QSysParam.sysParam;
                //晚间批量调度
                SysParam sysParam = sysParamRepository.findOne(qSysParam.companyCode.eq(company.getCode())
                        .and(qSysParam.code.eq(Constants.SYSPARAM_OVERNIGHT))
                        .and(qSysParam.status.eq(SysParam.StatusEnum.Start.getValue())));
                if (Objects.nonNull(sysParam)) {
                    String cronStr = sysParam.getValue();
                    //时间长度必须为6位
                    if (StringUtils.isNotBlank(cronStr) && StringUtils.length(cronStr) == 6) {
                        String hours = cronStr.substring(0, 2);
                        String mis = cronStr.substring(2, 4);
                        String second = cronStr.substring(4, 6);
                        cronStr = second.concat(" ").concat(mis).concat(" ").concat(hours).concat(" * * ?");
                        JobDetail jobDetail = ConfigureQuartz.createJobDetail(OverNightJob.class, Constants.OVERNIGHT_JOB_GROUP,
                                Constants.OVERNIGHT_JOB_NAME.concat("_").concat(company.getCode()),
                                Constants.OVERNIGHT_JOB_DESC.concat("_").concat(company.getCode()));
                        JobDataMap jobDataMap = new JobDataMap();
                        jobDataMap.put("companyCode", company.getCode());
                        jobDataMap.put("sysParamCode", Constants.SYSPARAM_OVERNIGHT_STATUS);
                        if(!schedFactory.getScheduler().checkExists(jobDetail.getKey())) {
                            if (schedFactory.getScheduler().getTriggersOfJob(jobDetail.getKey()).isEmpty()) {
                                CronTriggerFactoryBean cronTriggerFactoryBean = ConfigureQuartz.createCronTrigger(Constants.OVERNIGHT_TRIGGER_GROUP,
                                        Constants.OVERNIGHT_TRIGGER_NAME.concat("_").concat(company.getCode()),
                                        "overNightJobBean".concat("_").concat(company.getCode()),
                                        Constants.OVERNIGHT_TRIGGER_DESC.concat("_").concat(company.getCode()), jobDetail, cronStr, jobDataMap);
                                cronTriggerFactoryBean.afterPropertiesSet();
                                //加入调度器
                                schedFactory.getScheduler().scheduleJob(jobDetail, cronTriggerFactoryBean.getObject());
                                cronTriggerFactoryBeanList.add(cronTriggerFactoryBean);
                            }
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
