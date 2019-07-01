package cn.fintecher.pangolin.business.scheduled;

import cn.fintecher.pangolin.business.repository.TaskScheduleLogRepository;
import cn.fintecher.pangolin.business.service.out.InputDataApiService;
import cn.fintecher.pangolin.entity.TaskBatchImportLog;
import cn.fintecher.pangolin.entity.TaskScheduleLog;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import com.hsjry.lang.common.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class GetOverdueDataScheduled {

    private final Logger log = LoggerFactory.getLogger(GetOverdueDataScheduled.class);


    @Autowired
    private InputDataApiService inputDataApiService;
    @Autowired
    TaskScheduleLogRepository taskScheduleLogRepository;

    @Scheduled(cron = "0 0 6 * * ?")
    private void batchExecuteSyncData() {
        log.info("晚上定时更新核心推送的逾期数据.....");
        //获取跑批日期
        String dateStr= DateUtil.getDate(new Date(), "yyyyMMdd");

        //判断跑批是否已执行
        TaskScheduleLog taskScheduleLog=new TaskScheduleLog();
        taskScheduleLog.setExecKey(TaskScheduleLog.TaskCode.BATCH_SYNC_DATA.getValue()+"-"+dateStr);
//        taskScheduleLog.setTaskCode(TaskScheduleLog.TaskCode.BATCH_SYNC_DATA.getValue());
        taskScheduleLog.setCreateTime(new Date());
        try {
            taskScheduleLogRepository.addTaskScheduleLog(taskScheduleLog.getExecKey(),taskScheduleLog.getCreateTime());
        }catch (Exception e){
//            e.printStackTrace();
            log.info("跑批任务已在其他服务执行, 该服务停止执行跑批......");
            return;
        }
        //执行跑批
        String taskId= ShortUUID.uuid();
        inputDataApiService.getHYData(dateStr, taskId, TaskBatchImportLog.BatchTaskType.AUTOMATION.getValue());
    }
}
