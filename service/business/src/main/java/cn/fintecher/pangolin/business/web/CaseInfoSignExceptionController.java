package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.repository.CaseInfoDistributedRepository;
import cn.fintecher.pangolin.business.repository.CaseInfoRepository;
import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.CaseInfoDistributed;
import cn.fintecher.pangolin.enums.EExceptionType;
import cn.fintecher.pangolin.model.CaseInfoSignException;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/caseInfoSignExceptionController")
@Api(value = "caseInfoSignExceptionController", description = "标识异常案件")
public class CaseInfoSignExceptionController {

    private static final String ENTITY_NAME = "caseInfoSignExceptionController";

    @Autowired
    private CaseInfoDistributedRepository caseInfoDistributedRepository;
    @Autowired
    private CaseInfoRepository caseInfoRepository;

    private final Logger log = LoggerFactory.getLogger(CaseInfoSignExceptionController.class);



    @PostMapping("/singCaseInfoDistributed")
    public ResponseEntity<CaseInfo> createCaseInfo(@RequestBody CaseInfoSignException caseInfoSignException) {
        try {
            List<CaseInfoDistributed> list = caseInfoDistributedRepository.findAll(caseInfoSignException.getIdList());
            for (CaseInfoDistributed caseInfoDistributed : list) {
                caseInfoDistributed.setExceptionFlag(1);
                caseInfoDistributed.setExceptionCheckTime(new Date());
                caseInfoDistributed.setExceptionType(caseInfoSignException.getExceptionType());
            }
            caseInfoDistributedRepository.save(list);

            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error("标记异常案件失败:{}",e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("操作失败",ENTITY_NAME)).body(null);        }
    }
    @PostMapping("/singCaseInfo")
    public ResponseEntity<CaseInfo> singCaseInfo(@RequestBody CaseInfoSignException caseInfoSignException) {
        try {
            List<CaseInfo> list = caseInfoRepository.findAll(caseInfoSignException.getIdList());
            for (CaseInfo caseInfo : list) {
                caseInfo.setExceptionFlag(1);
                caseInfo.setExceptionCheckTime(new Date());
                caseInfo.setExceptionType(caseInfoSignException.getExceptionType());
            }
            caseInfoRepository.save(list);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功", ENTITY_NAME)).body(null);
        } catch (Exception e) {
            log.error("标记异常案件失败:{}",e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("操作失败",ENTITY_NAME)).body(null);        }
    }
}
