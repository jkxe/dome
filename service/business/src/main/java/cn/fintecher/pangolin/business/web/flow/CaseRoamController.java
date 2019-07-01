package cn.fintecher.pangolin.business.web.flow;

import cn.fintecher.pangolin.business.model.ApplyCaseInfoRoamParams;
import cn.fintecher.pangolin.business.model.CaseInfoRoamModel;
import cn.fintecher.pangolin.business.model.ProcessApprovalModel;
import cn.fintecher.pangolin.business.repository.UserRepository;
import cn.fintecher.pangolin.business.service.flow.CaseRoamService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.CaseRecordApply;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.ListCompareUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/CaseRoamController")
@Api(value = "caseRoamController", description = "案件流转审批")
public class CaseRoamController extends BaseController {

    final Logger log = LoggerFactory.getLogger(CaseRoamController.class);

    @Autowired
    private CaseRoamService caseRoamService;
    @Inject
    UserRepository userRepository;

    @GetMapping("/getCaseRecordApplyInfo")
    @ApiOperation(value = "获取该用户名下所有待审批的流转案件",notes = "获取该用户名下所有待审批的流转案件")
    public ResponseEntity<Page<CaseInfoRoamModel>> getCaseRecordApplyInfo(@RequestParam(value = "taskId",required = false) String taskId,
                                                                          @ApiIgnore Pageable pageable,
                                                                          @RequestParam(required = false) @ApiParam(value = "案件编号") String caseNumber,
                                                                          @RequestParam(required = false) @ApiParam(value = "客户姓名") String personalName,
                                                                          @RequestParam(required = false) @ApiParam(value = "手机号") String mobileNo,
                                                                          @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                                          @RequestParam(required = false) @ApiParam(value = "批次号") String batchNumber,
                                                                          @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            List<CaseInfoRoamModel> list = caseRoamService.getCaseInfoRoamList(taskId,user);
            Iterator<CaseInfoRoamModel> iterator = list.iterator();
            while(iterator.hasNext()){
                CaseInfoRoamModel model = iterator.next();
                if((StringUtils.isNotEmpty(caseNumber)   && (model.getCaseNumber() == null || !model.getCaseNumber().equalsIgnoreCase( caseNumber))) ||
                        (StringUtils.isNotEmpty(personalName)   && (model.getPersonalName() == null || !model.getPersonalName().equalsIgnoreCase(personalName))) ||
                        (StringUtils.isNotEmpty(mobileNo)  && (model.getPersonalMobileNo() == null || !model.getPersonalMobileNo().equalsIgnoreCase( mobileNo))) ||
                        (StringUtils.isNotEmpty(idCard)  && (model.getPersonalIdCard()== null || !model.getPersonalIdCard().equalsIgnoreCase( idCard))) ||
                        (StringUtils.isNotEmpty(batchNumber)  && (model.getBatchNumber() == null || !model.getBatchNumber().equalsIgnoreCase( batchNumber)))){
                        iterator.remove();
                }
            }
            if(Objects.nonNull(pageable.getSort()) && pageable.getSort().toString().contains("applyTime,desc")){
                ListCompareUtil<CaseInfoRoamModel> listCompareUtil = new ListCompareUtil<CaseInfoRoamModel>();
                listCompareUtil.sortByMethod(list,"applyTime",true);
            }else if (Objects.nonNull(pageable.getSort()) && pageable.getSort().toString().contains("applyTime,asc")){
                ListCompareUtil<CaseInfoRoamModel> listCompareUtil = new ListCompareUtil<CaseInfoRoamModel>();
                listCompareUtil.sortByMethod(list,"applyTime",false);
            }
            List<CaseInfoRoamModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<CaseInfoRoamModel> page = new PageImpl<>(modeList,pageable,list.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "getCaseRecordApplyInfo", "getCaseRecordApplyInfo", e.getMessage())).body(null);
        }
    }

    @PostMapping("/ApprovalCaseRecordApply")
    @ApiOperation(value = "流转案件进行审批",notes = "流转案件进行审批")
    public ResponseEntity<Void> ApprovalCaseRecordApply(@RequestBody ProcessApprovalModel processApprovalModel,
                                                        @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            caseRoamService.caseRecordApproval(processApprovalModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件流转审批成功", "ApprovalCaseRecordApply")).body(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalCaseRecordApply", "ApprovalCaseRecordApply", e.getMessage())).body(null);
        }
    }

    @PostMapping("/CaseRecordInfoApply")
    @ApiOperation(value = "案件流转申请",notes = "案件流转申请")
    public ResponseEntity<Void> CaseRecordInfoApply(@RequestBody ApplyCaseInfoRoamParams applyCaseInfoRoamParams,
                                                    @RequestHeader(value = "X-UserToken") String token){
        try{
            User user = getUserByToken(token);
            caseRoamService.applyCaseInfoRoam(applyCaseInfoRoamParams,user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件流转申请成功", "CaseRecordInfoApply")).body(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseRecordInfoApply", "CaseRecordInfoApply", e.getMessage())).body(null);
        }
    }

    @PostMapping("/CaseRecordInfoDoFlow")
    @ApiOperation(value = "案件流转操作-自动分案调用",notes = "案件流转操作-自动分案调用")
    public ResponseEntity<Void> CaseRecordInfoDoFlow(@RequestBody List<CaseRecordApply > applyList){
        try{
            User user = userRepository.findOne(Constants.ADMINISTRATOR_ID);//定时任务,使用管理员用户
            caseRoamService.doFlow(applyList,0, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件流转申请成功", "CaseRecordInfoApply")).body(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("CaseRecordInfoApply", "CaseRecordInfoApply", e.getMessage())).body(null);
        }
    }


}
