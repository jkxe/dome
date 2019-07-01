package cn.fintecher.pangolin.business.web;


import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.Objects;

@RestController
@RequestMapping("/api/caseInfoTestController")
@Api(value = "CaseInfoTestController", description = "针对一些简单程序的测试的类")
public class CaseInfoTestController {

    private final Logger log = LoggerFactory.getLogger(CaseInfoTestController.class);

    @Inject
    CaseInfoRepository caseInfoRepository;

    @GetMapping("/caseInfo")
    public ResponseEntity<CaseInfo> createCaseInfo(@RequestParam String caseId) throws URISyntaxException {
        log.debug("REST request to save caseInfo : {}", caseId);
        if (Objects.isNull(caseId)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "没有案件信息")).body(null);
        }
        CaseInfo result = caseInfoRepository.getOne(caseId);
        return ResponseEntity.ok().body(result);
    }
}
