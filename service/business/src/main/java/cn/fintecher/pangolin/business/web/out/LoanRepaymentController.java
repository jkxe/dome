package cn.fintecher.pangolin.business.web.out;

import cn.fintecher.pangolin.business.service.out.LoanRepaymentService;
import cn.fintecher.pangolin.business.web.BaseController;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/LoanRepaymentController")
@Api(value = "LoanRepaymentController", description = "从核心获取案件还款信息")
public class LoanRepaymentController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(LoanRepaymentController.class);

    @Autowired
    private LoanRepaymentService loanRepaymentService;

    @PostMapping(value = "/getLoanRepayment")
    @ApiOperation(value = "从核心获取案件还款信息", notes = "从核心获取案件还款信息")
    public ResponseEntity<Void> getLoanRepayment(@ApiParam(value = "案件申请号", required = true) @RequestParam String caseNumber,
                                                 @RequestHeader(value = "X-UserToken") String token){
        log.debug("REST request to jin cheng ");
        try {
            User user = getUserByToken(token);
            loanRepaymentService.getLoanRepayment(user, caseNumber);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", e.getMessage())).body(null);
        }
    }
}
