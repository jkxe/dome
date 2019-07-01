package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.model.CaseRepairParams;
import cn.fintecher.pangolin.report.service.CaseRepairService;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ZhangYaJun
 * @Title: ExportCaseReapirController
 * @ProjectName pangolin
 * @Description:
 * @date 2019/1/19 0019下午 17:12
 */

@RestController
@RequestMapping("/api/ExportCaseRepairController")
@Api(description = "修复案件导出")
public class ExportCaseRepairController  extends   BaseController{

   private static final String ENTITY_NAME = "ExportCaseRepairController";
  private   final   Logger log = LoggerFactory.getLogger(ExportCaseRepairController.class);

   @Autowired
   private CaseRepairService  caseRepairService;

   @GetMapping(value = "/exportCaseRepair")
   @ApiOperation(value = "下载信修案件", notes = "下载信修案件")
   public ResponseEntity<Map<String, String>> exportCaseRepair(CaseRepairParams caseRepairParams,
                                                             @RequestHeader(value = "X-UserToken") String token) {
      try {
           User user = getUserByToken(token);
         if (Objects.nonNull(user.getCompanyCode())) {
            caseRepairParams.setCompanyCode(user.getCompanyCode());
         }
         String Upurl = caseRepairService.queryExportReapair(caseRepairParams,user);
         Map<String, String> map = new HashMap<>();
         map.put("body", Upurl);
         return ResponseEntity.ok().headers(HeaderUtil.createAlert("信修案件导出成功", ENTITY_NAME)).body(map);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "ExportCaseRepairController", e.getMessage())).body(null);
      }
   }
}
