package cn.fintecher.pangolin.business.service;

import cn.fintecher.pangolin.business.repository.SysParamRepository;
import cn.fintecher.pangolin.entity.QSysParam;
import cn.fintecher.pangolin.entity.SysParam;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ZWDateUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * @Author: PeiShouWen
 * @Description: 调度服务类
 * @Date 15:29 2017/8/10
 */
@Service("jobTaskService")
public class JobTaskService {

    Logger logger= LoggerFactory.getLogger(JobTaskService.class);

    @Autowired
    SchedulerFactoryBean schedFactory;

    @Autowired
    SysParamRepository sysParamRepository;

    /**
     * 更新CRON任务调度时间
      *
     */
    public void updateJobTask(String cron,String companyCode,String sysParamCode, String triggerName,String triggerGroup,String triggerDesc,
                              String jobName,String jobGroup,String jobDesc,Class objClass,String beanName) throws Exception {
        Scheduler scheduler = schedFactory.getScheduler();
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("companyCode",companyCode);
        jobDataMap.put("sysParamCode",sysParamCode);
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
        Trigger trigger = triggerBuilder.usingJobData(jobDataMap).withDescription(triggerDesc).withIdentity(triggerName,triggerGroup).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        Trigger.TriggerState triggerState= scheduler.getTriggerState(trigger.getKey());
        //加入调度器
        schedFactory.getScheduler().rescheduleJob(trigger.getKey(),trigger);
        if(triggerState.equals(Trigger.TriggerState.PAUSED)){
            scheduler.pauseTrigger(trigger.getKey());
        }
    }

    /**
     * 检查调度是否正在执行
     * @param companyCode
     * @param sysParamCode
     * @return
     */
    public boolean checkJobIsRunning(String companyCode,String sysParamCode){
        QSysParam qSysParam=QSysParam.sysParam;
        SysParam sysParam=sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                                                        .and(qSysParam.code.eq(sysParamCode)));
        if(Objects.nonNull(sysParam)){
            if(sysParam.getValue().equals(Constants.BatchStatus.RUNING.getValue())){
                return true;
            }
        }
        return false;
    }

    /**
     *更新指定的参数值
     * @param companyCode
     * @param sysParamCode
     */
    @Transactional
    public void updateSysparam(String companyCode,String sysParamCode,String value) throws Exception{
        SysParam sysParam=new SysParam();
        sysParam.setCompanyCode(companyCode);
        sysParam.setCode(sysParamCode);
        sysParam=sysParamRepository.findOne(QSysParam.sysParam.companyCode.eq(companyCode).and(QSysParam.sysParam.code.eq(sysParamCode)));
        sysParam.setValue(value);
        sysParam.setOperateTime(ZWDateUtil.getNowDateTime());
        sysParamRepository.save(sysParam);
    }

    /**
     * 获取指定的参数值
     * @param companyCode
     * @param sysParamCode
     * @return
     */
    public SysParam getSysparam(String companyCode,String sysParamCode){
        QSysParam qSysParam=QSysParam.sysParam;
        return sysParamRepository.findOne(qSysParam.companyCode.eq(companyCode)
                                    .and(qSysParam.code.eq(sysParamCode)));
    }



}


