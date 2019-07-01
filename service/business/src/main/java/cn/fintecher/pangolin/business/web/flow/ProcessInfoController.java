package cn.fintecher.pangolin.business.web.flow;


import cn.fintecher.pangolin.business.model.ProcessModel;
import cn.fintecher.pangolin.business.model.ProcessNodeModel;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.business.service.flow.TaskInfoService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.FlowHistory;
import cn.fintecher.pangolin.entity.FlowTask;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/processInfoController")
@Api(value = "ProcessInfoController", description = "流程相关的操作")
public class ProcessInfoController extends BaseController {

    final Logger logger = LoggerFactory.getLogger(ProcessInfoController.class);

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getTaskHistoryList")
    @ApiOperation(value = "获取该案件的审批历史记录", notes = "获取该案件的审批历史记录")
    public ResponseEntity<List<FlowHistory>> getTaskHistoryList(@RequestParam("approvalId") String approvalId,
                                                                @RequestParam("caseNumber") String caseNumber){
        List<FlowHistory> flowHistoryList = taskInfoService.getFlowHistoryListByApprovalIdAndCaseNumber(approvalId,caseNumber);
        List<FlowHistory> flowHistorys = new ArrayList<>();
        if(flowHistoryList != null && flowHistoryList.size() != 0){
            for(FlowHistory flowHistory : flowHistoryList){
                FlowHistory flowHistory1 = new FlowHistory();
                BeanUtils.copyProperties(flowHistory,flowHistory1);
                User user = userRepository.findOne(flowHistory.getApprovalUser());
                flowHistory1.setApprovalUser(user.getRealName());
                flowHistorys.add(flowHistory1);
            }
        }
        return ResponseEntity.ok().body(flowHistorys);
    }

    @GetMapping("/getTaskInfoList")
    @ApiOperation(value = "获取任务节点列表信息",notes = "获取任务节点列表信息")
    public ResponseEntity<List<ProcessNodeModel>> getTaskInfoList(@RequestParam(value = "taskId",required = false) String taskId){
        List<ProcessNodeModel> list = taskInfoService.getTaskInfoList(taskId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/queryFlowTaskList")
    @ApiOperation(value = "获取所有的任务",notes = "获取所有的任务")
    public ResponseEntity<Page<FlowTask>> queryFlowTaskList(@RequestParam(value = "taskName",required = false) String taskName,
                                                            @ApiIgnore Pageable pageable){
        List<FlowTask> list = taskInfoService.getFlowTaskAll(taskName);
        List<FlowTask> flowTaskList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
        Page<FlowTask> page = new PageImpl<>(flowTaskList, pageable, list.size());
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("getFlowTaskAll")
    @ApiOperation(value = "获取所有的任务",notes = "获取所有的任务")
    public ResponseEntity<List<FlowTask>> getFlowTaskAll(){
        List<FlowTask> list = taskInfoService.getFlowTaskAll("");
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/saveProcessTask")
    @ApiOperation(value = "创建任务审批链",notes = "创建任务审批链")
    public ResponseEntity<Void> saveProcessTask(@RequestBody ProcessModel processModel){
        taskInfoService.saveProcessTask(processModel);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("创建审批链成功", "saveFlowTask")).body(null);
    }
}
