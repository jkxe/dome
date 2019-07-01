package cn.fintecher.pangolin.business.web.flow;


import cn.fintecher.pangolin.business.model.AssistApplyApprovaleModel;
import cn.fintecher.pangolin.business.model.AssistApplyProcessMode;
import cn.fintecher.pangolin.business.model.AssistApplyProcessServiceMode;
import cn.fintecher.pangolin.business.service.flow.CaseAssistApplyProcessService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.CaseAssistApply;
import cn.fintecher.pangolin.entity.QCaseAssistApply;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.hsjry.lang.common.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/caseAssistApplyProcessController")
@Api(value = "CaseAssistApplyProcessController", description = "案件协催申请")
public class CaseAssistApplyProcessController extends BaseController {

    final Logger log = LoggerFactory.getLogger(CaseAssistApplyProcessController.class);

    @Autowired
    CaseAssistApplyProcessService caseAssistApplyProcessService;


    @GetMapping("/getAssistApplyApproval")
    @ApiOperation(value = "获取申请协催案件待审批案件列表", notes = "获取申请协催案件待审批案件列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<AssistApplyProcessServiceMode>> getAssistApplyApproval(@QuerydslPredicate(root = CaseAssistApply.class) Predicate predicate,
                                                                                      @RequestParam(value = "taskId", required = false) String taskId,
                                                                                      @RequestParam(required = false) @ApiParam(value = "申请时间开始") String applyDateStart,
                                                                                      @RequestParam(required = false) @ApiParam(value = "申请时间结束") String applyDateEnd,
                                                                                      @RequestParam(required = false) @ApiParam(value = "排序") String sort,
                                                                                      @ApiIgnore Pageable pageable,
                                                                                      @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            BooleanBuilder booleanBuilder = new BooleanBuilder(predicate);
            if(StringUtils.isNotEmpty(applyDateStart)){
                Date date = DateUtil.getDate(applyDateStart,"yyyy-MM-dd");
                booleanBuilder.and(QCaseAssistApply.caseAssistApply.applyDate.after(date));
            }
            if(StringUtils.isNotEmpty(applyDateEnd)){
                Date date = DateUtil.getDate(applyDateStart,"yyyy-MM-dd");
                booleanBuilder.and(QCaseAssistApply.caseAssistApply.applyDate.before(date));
            }
            List<AssistApplyProcessServiceMode> list = caseAssistApplyProcessService.getApplyCaseAssistInfo(taskId, user, booleanBuilder,sort);
//            // 合并共债案件
//            List<AssistApplyProcessServiceMode> list1 = new ArrayList<>();
//            List<String> caseNums = new ArrayList<>();
//            for  ( int  i  =   0 ; i  <  list.size() ; i ++ ) {
//                if (caseNums.contains(list.get(i).getCaseNumber())) {
//                    continue;
//                }
//                caseNums.add(list.get(i).getCaseNumber());
//                AssistApplyProcessServiceMode assistApplyProcessServiceMode = list.get(i);
//                for (int j = i + 1; j < list.size(); j++) {
//                    if (assistApplyProcessServiceMode.getCaseNumber().equals(list.get(j).getCaseNumber())) {
//                        // 有共债案件需要合并(合并逻辑)
//                        if (Objects.nonNull(list.get(j).getOverduePeriods()) && Objects.nonNull(assistApplyProcessServiceMode.getOverduePeriods())){
//                            // 判断逾期期数大小
//                            if (assistApplyProcessServiceMode.getOverduePeriods().compareTo(list.get(j).getOverduePeriods()) < 1){
//                                assistApplyProcessServiceMode.setOverduePeriods(list.get(j).getOverduePeriods());
//                            }
//                        }
//                        // 合并逾期金额
//                        assistApplyProcessServiceMode.setOverdueAmount(assistApplyProcessServiceMode.getOverdueAmount().add(list.get(j).getOverdueAmount()));
//                        // 合并到账金额
//                    }
//                }
//                list1.add(assistApplyProcessServiceMode);
//            }
            List<AssistApplyProcessServiceMode> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<AssistApplyProcessServiceMode> page = new PageImpl<>(modeList, pageable, list.size());
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "getAssistApplyApproval", "getAssistApplyApproval", e.getMessage())).body(null);
        }
    }

    @PostMapping("/applyCaseAssistDerate")
    @ApiOperation(value = "协催案件申请操作", notes = "协催案件申请操作")
    public ResponseEntity<Void> applyCaseAssistDerate(@RequestBody AssistApplyProcessMode assistApplyProcessMode,
                                                      @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            caseAssistApplyProcessService.ApplyCaseAssistApproval(assistApplyProcessMode, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("协催案件申请成功", "applyCaseAssistDerate")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("applyCaseAssistDerate", "applyCaseAssistDerate", e.getMessage())).body(null);
        }
    }

    @PostMapping("/saveCaseAssistApproval")
    @ApiOperation(value = "协催案件的审批操作", notes = "协催案件的审批操作")
    public ResponseEntity<Void> saveCaseAssistApproval(@RequestBody AssistApplyApprovaleModel assistApplyApprovaleModel,
                                                       @RequestHeader(value = "X-UserToken") String token) {
        try {
            User user = getUserByToken(token);
            caseAssistApplyProcessService.assistApplyVisitApprove(assistApplyApprovaleModel, user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("协催案件审批成功", "saveCaseAssistApproval")).body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("saveCaseAssistApproval", "saveCaseAssistApproval", e.getMessage())).body(null);
        }
    }
}
