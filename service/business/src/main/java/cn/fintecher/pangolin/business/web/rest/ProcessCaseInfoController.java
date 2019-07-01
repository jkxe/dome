package cn.fintecher.pangolin.business.web.rest;

import cn.fintecher.pangolin.business.repository.FlowApprovalRepository;
import cn.fintecher.pangolin.entity.FlowApproval;
import cn.fintecher.pangolin.entity.QFlowApproval;
import cn.fintecher.pangolin.entity.strategy.CaseNumberModel;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/processCaseInfoController")
public class ProcessCaseInfoController {

    private final Logger log = LoggerFactory.getLogger(ProcessCaseInfoController.class);

    @Autowired
    private FlowApprovalRepository flowApprovalRepository;

    @GetMapping("/getApprovalCaseNumber")
    @ApiOperation(value = "获取正在审批流程中的案件",notes = "获取正在审批流程中的案件")
    private ResponseEntity<CaseNumberModel> getApprovalCaseNumber(){
        CaseNumberModel caseNumberModel = new CaseNumberModel();
        List<String> caseNumberList = new ArrayList<>();
        Iterator<FlowApproval> iterator = flowApprovalRepository.findAll(QFlowApproval.flowApproval.processState.eq(FlowApproval.ProcessState.PROCESS_STATE_NORMAL.getValue())).iterator();
        while (iterator.hasNext()){
            FlowApproval flowApproval = iterator.next();
            caseNumberList.add(flowApproval.getCaseNumber());
        }
        caseNumberModel.setCaseNumberList(caseNumberList);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("获取成功", "")).body(caseNumberModel);
    }

}
