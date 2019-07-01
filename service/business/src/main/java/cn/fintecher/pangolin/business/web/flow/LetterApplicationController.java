package cn.fintecher.pangolin.business.web.flow;

import cn.fintecher.pangolin.business.model.*;
import cn.fintecher.pangolin.business.model.request.UnReadMessageModel;
import cn.fintecher.pangolin.business.service.flow.LetterApplicationService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.alibaba.fastjson.JSONArray;
import com.hsjry.lang.common.util.DateUtil;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhangYaJun
 * @Title: LetterApplicationController
 * @ProjectName pangolin
 * @Description:
 * @date 2019/1/15 0015下午 17:06
 */

@RestController
@RequestMapping("/api/letterApplicationController")
@Api(value = "LetterApplicationController", description = "信修申请")
public class LetterApplicationController   extends BaseController{

 final Logger   logger =  LoggerFactory.getLogger(LetterApplicationController.class);

 @Autowired
   private LetterApplicationService  letterApplicationService;


   @PostMapping("/applyLetterApplication")
   @ApiOperation(value = "信修申请操作",notes = "信修申请操作")
   public ResponseEntity<Void> applyLetterApplication(@RequestBody ApplyLetterApplocationMode applyLetterApplocationMode ,@RequestHeader(value = "X-UserToken") String token){

    try {
       User user = getUserByToken(token);
       letterApplicationService.applyLetterApplication(applyLetterApplocationMode, user);
       return ResponseEntity.ok().headers(HeaderUtil.createAlert("信修案件申请成功", "applyLetterApplication")).body(null);
    } catch (Exception e) {
       logger.error(e.getMessage(), e);
       return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applyLetterApplication", "applyLetterApplication", e.getMessage())).body(null);
    }

 }




   @PostMapping("/ApprovalCaseRecordApply")
   @ApiOperation(value = "信修案件审批",notes = "信修案件审批")
   public ResponseEntity<Void> ApprovalCaseRepairApply(@RequestBody ProcessCaseRepairModel processCaseRepairModel, @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);
         letterApplicationService.caseRecordApproval(processCaseRepairModel, user);
         return ResponseEntity.ok().headers(HeaderUtil.createAlert("案件信修审批成功", "ApprovalCaseRepairApply")).body(null);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ApprovalCaseRepairApply", "ApprovalCaseRepairApply", e.getMessage())).body(null);
      }
   }



   @GetMapping("/getApplyCaseRepairList")
   @ApiOperation(value = "获取信修申请案件待审批案件列表", notes = "获取信修申请案件待审批案件列表")
   public ResponseEntity<Page<CaseRepairApplyModel>> getApplyCaseRepairList(
                                                                            @RequestParam(value = "taskId",required = false) String taskId,
                                                                            @ApiIgnore Pageable pageable,
                                                                            @RequestParam(required = false) @ApiParam(value = "案件编号") String caseNumber,
                                                                            @RequestParam(required = false) @ApiParam(value = "借据号") String loanInvoiceNumber,
                                                                            @RequestParam(required = false) @ApiParam(value = "客户姓名") String personalName,
                                                                            @RequestParam(required = false) @ApiParam(value = "手机号") String mobileNo,
                                                                            @RequestParam(required = false) @ApiParam(value = "身份证号") String idCard,
                                                                            @RequestParam(required = false) @ApiParam(value = "审核状态") Integer approvalStatus,
                                                                            @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);

         List<CaseRepairApplyModel>   list=  letterApplicationService.getApplyCaseRepairList(taskId,user);
         Iterator<CaseRepairApplyModel> iterator = list.iterator();
         while (iterator.hasNext()){
              CaseRepairApplyModel model = iterator.next();
              if(     (StringUtils.isNotEmpty(caseNumber) && (model.getCaseNumber() == null || !model.getCaseNumber().equalsIgnoreCase(caseNumber))) ||
                      (StringUtils.isNotEmpty(loanInvoiceNumber) && (model.getLoanInvoiceNumber()== null || !model.getLoanInvoiceNumber().equalsIgnoreCase(loanInvoiceNumber))) ||
                      (StringUtils.isNotEmpty(personalName) && (model.getPersonalName() == null || !model.getPersonalName().equalsIgnoreCase(personalName))) ||
                      (StringUtils.isNotEmpty(mobileNo) && (model.getPersonalMobileNo()== null || !model.getPersonalMobileNo().equalsIgnoreCase(mobileNo))) ||
                      (StringUtils.isNotEmpty(idCard) && (model.getPersonalIdCard()== null || !model.getPersonalIdCard().equalsIgnoreCase(idCard))) ||
                      (approvalStatus != null  && (model.getApprovalStatus() != null || model.getApprovalStatus() != approvalStatus))){
                  iterator.remove();
              }
         }
         List<CaseRepairApplyModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
         Page<CaseRepairApplyModel> page = new PageImpl<>(modeList,pageable,list.size());
         return ResponseEntity.ok().body(page);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "getApplyCaseRepairList", "getApplyCaseRepairList", e.getMessage())).body(null);
      }
   }


   @GetMapping("/getCaseRepairApplyRecord")
   @ApiOperation(value = "获取信修记录列表", notes = "获取信修记录")
   public ResponseEntity<Page<CaseRepairApplyModel>> getCaseRepairApplyRecord(
           @RequestParam(value = "loanInvoiceNumber",required = false) String loanInvoiceNumber,
           @RequestParam(value = "caseNumber",required = false) String caseNumber,
           @ApiIgnore Pageable pageable,
           @RequestParam(required = false) @ApiParam(value = "申请时间") String applyTime,
           @RequestParam(required = false) @ApiParam(value = "审核状态") Integer approvalStatus,
           @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);
         List<CaseRepairApplyModel> list = null;
            if(Objects.nonNull(loanInvoiceNumber)){
               list=  letterApplicationService.getCaseRepairRecordList(loanInvoiceNumber);
            }else{
               list=  letterApplicationService.getCaseRepairByCaseNumberRecordList(caseNumber);
            }

         Iterator<CaseRepairApplyModel> iterator = list.iterator();
         String date1 = null;
         if(null != applyTime){
            Date date = DateUtil.getDate(applyTime, "yyyy-MM-dd");
            date1 = DateUtil.getDate(date, "yyyy-MM-dd");
         }
         while (iterator.hasNext()){
            CaseRepairApplyModel model = iterator.next();
            if(     (StringUtils.isNotEmpty(applyTime) && (model.getApplyTime() == null || !DateUtil.getDate(model.getApplyTime(), "yyyy-MM-dd").equalsIgnoreCase(date1))) ||
                    (approvalStatus != null  && (model.getApprovalStatus() == null || model.getApprovalStatus() != approvalStatus))){
               iterator.remove();
            }
         }
         List<CaseRepairApplyModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
         Page<CaseRepairApplyModel> page = new PageImpl<>(modeList,pageable,list.size());
         return ResponseEntity.ok().body(page);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "getCaseRepairRecordList", "getCaseRepairRecordList", e.getMessage())).body(null);
      }
   }





   @GetMapping("/getRepairRecordList")
   @ApiOperation(value = "获取信修查看列表", notes = "获取信修查看列表")
   public ResponseEntity<Page<CaseRepairApplyModel>> getRepairRecordList(
           @RequestParam(value = "loanInvoiceNumber",required = true) String loanInvoiceNumber,
           @ApiIgnore Pageable pageable,
           @RequestParam(required = false) @ApiParam(value = "修复日期") String operatorTime,
           @RequestParam(required = false) @ApiParam(value = "修复人员") String operator,
           @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);
         List<CaseRepairApplyModel>   list=  letterApplicationService.getRepairCaseRecordList(loanInvoiceNumber);
         Iterator<CaseRepairApplyModel> iterator = list.iterator();
         String date1 = null;
         if(null !=operatorTime){
         Date date = DateUtil.getDate(operatorTime, "yyyy-MM-dd");
        date1 = DateUtil.getDate(date, "yyyy-MM-dd");
         }
         while (iterator.hasNext()){
            CaseRepairApplyModel model = iterator.next();
            if((StringUtils.isNotEmpty(operator) && (model.getOperator()== null || !model.getOperator().equalsIgnoreCase(operator)))  ||
               (StringUtils.isNotEmpty(operatorTime) && (model.getOperatorTime() == null || !DateUtil.getDate(model.getOperatorTime(), "yyyy-MM-dd").equalsIgnoreCase(date1)))){
                  iterator.remove();
            }
         }
         List<CaseRepairApplyModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
         Page<CaseRepairApplyModel> page = new PageImpl<>(modeList,pageable,list.size());
         return ResponseEntity.ok().body(page);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "getRepairRecordList", "getCaseRepairRecordList", e.getMessage())).body(null);
      }
   }


   @GetMapping("/getRepairApprovalRecordList")
   @ApiOperation(value = "获取审批记录", notes = "获取审批记录")
   public ResponseEntity<Page<CaseRepairApplyModel>> getRepairApprovalList(
           @RequestParam(value = "id",required = true) String id,
           @ApiIgnore Pageable pageable,
           @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);
         List<CaseRepairApplyModel>   list=  letterApplicationService.getRepairApprovalRecordList(id,user);
         Iterator<CaseRepairApplyModel> iterator = list.iterator();
         List<CaseRepairApplyModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
         Page<CaseRepairApplyModel> page = new PageImpl<>(modeList,pageable,list.size());
         return ResponseEntity.ok().body(page);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "getRepairApprovalRecordList", "getRepairApprovalRecordList", e.getMessage())).body(null);
      }
   }


   @PostMapping("/getRepairSeeCount")
   @ApiOperation(value = "统计未读数", notes = "统计未读数")
   public ResponseEntity getRepairSeeCount(
           @RequestBody UnReadMessageModel model,
           @RequestHeader(value = "X-UserToken") String token){
      try {
          List<String> loanInvoiceNumber = model.getLoanInvoiceNumber();
         User user = getUserByToken(token);
         JSONArray arr = new JSONArray();
         if(loanInvoiceNumber.size()>0){
            for(int i=0;i<loanInvoiceNumber.size();i++){
               JSONObject obj= new JSONObject();
               int count =0;
               List<CaseRepairApplyModel>   list=  letterApplicationService.getRepairCaseRecordList(loanInvoiceNumber.get(i));
               if(list != null && list.size() > 0){
                  for(int k = 0; k<list.size();k++){
                     String readStatus = list.get(k).getReadStatus();
                     if(readStatus.equals("0")){
                        count ++;
                     }
                  }
               }
               obj.put("loanInvoiceNumber",loanInvoiceNumber.get(i));
               obj.put("unReadMessage",count);
               arr.add(obj);
            }
         }
         return ResponseEntity.ok().body(arr);
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "getRepairSeeCount", "getRepairSeeCount", e.getMessage())).body(null);
      }
   }


   @GetMapping("/updateStatus")
   @ApiOperation(value = "批量修改未读状态", notes = "批量修改未读状态")
   public ResponseEntity updateStatus(
           @RequestParam(value = "loanInvoiceNumber",required = true) String loanInvoiceNumber,
           @RequestHeader(value = "X-UserToken") String token){
      try {
         User user = getUserByToken(token);
         letterApplicationService.updateStatus(loanInvoiceNumber);
         return ResponseEntity.ok().body("修改完成");
      }catch (Exception e){
         logger.error(e.getMessage(), e);
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                 "updateStatus", "updateStatus", e.getMessage())).body("修改失败");
      }
   }

}
