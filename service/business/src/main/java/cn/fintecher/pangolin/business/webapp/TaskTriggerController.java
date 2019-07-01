package cn.fintecher.pangolin.business.webapp;

import cn.fintecher.pangolin.business.repository.TaskBatchImportLogRepository;
import cn.fintecher.pangolin.business.service.out.InputDataApiService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.TaskBatchImportLog;
import cn.fintecher.pangolin.entity.util.ShortUUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hanwannan
 * Description:
 * Date: 2017-8-1
 */
@RestController
@RequestMapping("/api/scheduleTriggerController")
@Api(value = "数据接口任务触发", description = "数据接口任务触发")
public class TaskTriggerController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(TaskTriggerController.class);

    @Autowired
    private InputDataApiService inputDataApiService;

    @Autowired
    private TaskBatchImportLogRepository taskBatchImportLogRepository;

    @GetMapping("/startTask")
    @ApiOperation(value = "数据接口拉取数据任务", notes = "数据接口拉取数据任务")
    public ResponseEntity startTask() {
        String taskId= ShortUUID.uuid();
        inputDataApiService.getHYData(null, taskId, TaskBatchImportLog.BatchTaskType.MANUAL.getValue());
        return ResponseEntity.ok().body(null);
    }
    @GetMapping("/startTaskByDate/{dateStr}")
    @ApiOperation(value = "数据接口拉取数据任务-指定的日期(格式:yyyyMMdd)", notes = "数据接口拉取数据任务-指定的日期(格式:yyyyMMdd)")
    public ResponseEntity startTaskByDate(@PathVariable(value = "dateStr") String dateStr) {
        log.info("手动开启数据接口拉取数据任务....参数:{}",dateStr);
        String taskId= ShortUUID.uuid();
        inputDataApiService.getHYData(dateStr, taskId, TaskBatchImportLog.BatchTaskType.MANUAL.getValue());
        log.info("开始...");
        return ResponseEntity.ok().body(taskId);
    }

    @GetMapping("/getLatestTask")
    @ApiOperation(value = "获取最新跑批任务", notes = "获取最新跑批任务")
    public ResponseEntity getLatestTask() {
        TaskBatchImportLog taskBatchImportLog = taskBatchImportLogRepository.
                findFirstByTaskStateOrderByStartTimeDesc(
                TaskBatchImportLog.BatchTaskState.EXECUTING.getValue());
        if(taskBatchImportLog!=null){
            if(System.currentTimeMillis()-taskBatchImportLog.getStartTime().getTime()>=60*60*1000){
                taskBatchImportLog = null;
            }
        }
        return ResponseEntity.ok().body(taskBatchImportLog);
    }
}
