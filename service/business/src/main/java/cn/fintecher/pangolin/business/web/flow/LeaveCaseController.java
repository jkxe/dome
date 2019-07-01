package cn.fintecher.pangolin.business.web.flow;


import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.business.service.flow.LeaveCaseService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.User;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaveCaseController")
@Api(value = "leaveCaseController", description = "案件流案审批")
public class LeaveCaseController extends BaseController {

    final Logger log = LoggerFactory.getLogger(LeaveCaseController.class);

    @Autowired
    private LeaveCaseService leaveCaseService;

    @Autowired
    CaseInfoService caseInfoService;

    @GetMapping("/getLeaveCaseInfoAll")
    @ApiOperation(value = "获取该用户名下所有待审批的留案案件",notes = "获取该用户名下所有待审批的留案案件")
    public ResponseEntity<Page<LeaveCaseInfoModel>> getLeaveCaseInfoAll(@RequestParam(value = "taskId",required = false) String taskId,
                                                                        @ApiIgnore Pageable pageable,
                                                                        @RequestParam(required = false) @ApiParam(value = "案件编号") String caseNumber,
                                                                        @RequestParam(required = false) @ApiParam(value = "客户姓名") String personalName,
                                                                        @RequestParam(required = false) @ApiParam(value = "手机号") String mobileNo,
                                                                        @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                                        @RequestParam(required = false) @ApiParam(value = "批次号") String batchNumber,
                                                                        @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            List<LeaveCaseInfoModel> list1 = leaveCaseService.getLeaveCaseInfo(taskId,user);
            List<LeaveCaseInfoModel> list = new ArrayList<>();
            List<String> lists = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                LeaveCaseInfoModel model = list1.get(i);
                if((StringUtils.isNotEmpty(caseNumber)   && (model.getCaseNumber() == null || !model.getCaseNumber().equalsIgnoreCase( caseNumber))) ||
                        (StringUtils.isNotEmpty(personalName)   && (model.getPersonalName() == null || !model.getPersonalName().equalsIgnoreCase(personalName))) ||
                        (StringUtils.isNotEmpty(mobileNo)  && (model.getMobileNo() == null || !model.getMobileNo().equalsIgnoreCase( mobileNo))) ||
                        (StringUtils.isNotEmpty(idCard)  && (model.getIdCard()== null || !model.getIdCard().equalsIgnoreCase( idCard))) ||
                        (StringUtils.isNotEmpty(batchNumber)  && (model.getBatchNumber() == null || !model.getBatchNumber().equalsIgnoreCase( batchNumber)))){
                        continue;
                }else {
                    if (lists.contains(model.getCaseNumber())){
                        continue;
                    }else {
                        list.add(model);
                        lists.add(model.getCaseNumber());
                    }
                }
            }
            if(Objects.nonNull(pageable.getSort()) && pageable.getSort().toString().contains("applyTime,desc")){
                ListCompareUtil<LeaveCaseInfoModel> listCompareUtil = new ListCompareUtil<LeaveCaseInfoModel>();
                listCompareUtil.sortByMethod(list,"applyTime",true);
            }else if (Objects.nonNull(pageable.getSort()) && pageable.getSort().toString().contains("applyTime,asc")){
                ListCompareUtil<LeaveCaseInfoModel> listCompareUtil = new ListCompareUtil<LeaveCaseInfoModel>();
                listCompareUtil.sortByMethod(list,"applyTime",false);
            }

            List<LeaveCaseInfoModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<LeaveCaseInfoModel> page = new PageImpl<>(modeList,pageable,list.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "LeaveCaseController", "getLeaveCaseInfoAll", e.getMessage())).body(null);
        }
    }

    @PostMapping("/saveApprovaLeaveCase")
    @ApiOperation(value = "留案案件进行审批",notes = "留案案件进行审批")
    public ResponseEntity<Void> saveApprovaLeaveCase(@RequestBody ProcessLeaveParam processLeaveParam,
                                                     @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            leaveCaseService.saveApprovalLeavecase(processLeaveParam,user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("留案案件进行审批成功", "saveApprovaLeaveCase")).body(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LeaveCaseController", "saveApprovaLeaveCase", e.getMessage())).body(null);
        }
    }


    @PostMapping("/leaveCaseApply")
    @ApiOperation(value = "案件留案申请",notes = "案件留案申请")
    public ResponseEntity<Void> leaveCaseApply(@RequestBody ApplyLeaveCaseParam applyLeaveCaseParam,
                                               @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            leaveCaseService.applyLeaveCase(applyLeaveCaseParam,user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件留案申请成功", "leaveCaseApply")).body(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LeaveCaseController", "leaveCaseApply", e.getMessage())).body(null);
        }
    }

    @PostMapping("/leaveCaseApplyApp")
    @ApiOperation(value = "案件留案申请",notes = "案件留案申请")
    public ResponseEntity<DivisionModel> leaveCaseApplyApp(@RequestBody ApplyLeaveCaseParam applyLeaveCaseParam,
                                                           @RequestHeader(value = "X-UserToken") String token){
        try {
            User user = getUserByToken(token);
            DivisionModel divisionModel = new DivisionModel();
            divisionModel.setDescription("案件申请提交成功!");
            leaveCaseService.applyLeaveCase(applyLeaveCaseParam,user);
//            divisionModel.setType(4);
//            divisionModel = caseInfoService.divisionCaseCheck(divisionModel, applyLeaveCaseParam.getCaseIdList());
//            if (divisionModel.getCaseIdList() != null && !divisionModel.getCaseIdList().isEmpty()) {
//                applyLeaveCaseParam.getCaseIdList().clear();
//                applyLeaveCaseParam.setCaseIdList(divisionModel.getCaseIdList());
//                leaveCaseService.applyLeaveCase(applyLeaveCaseParam,user);
//            }
//            User user = getUserByToken(token);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(divisionModel.getDescription(), "CaseRecordInfoApply")).body(divisionModel);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("LeaveCaseController", "leaveCaseApply", e.getMessage())).body(null);
        }
    }
}
