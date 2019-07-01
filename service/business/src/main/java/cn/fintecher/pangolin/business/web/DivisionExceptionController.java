package cn.fintecher.pangolin.business.web;

import cn.fintecher.pangolin.business.model.DivisionExceptionModel;
import cn.fintecher.pangolin.business.model.request.DivisionExceptionRequest;
import cn.fintecher.pangolin.business.service.CaseInfoService;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : suyuchao
 * @Description : 分案异常案件
 * @Date : 2019/01/21.
 */

@RestController
@RequestMapping("/api/divisionExceptionController")
@Api(value = "DivisionExceptionController", description = "分案异常案件")
public class DivisionExceptionController extends BaseController {

    private static final String ENTITY_NAME = "DivisionExceptionController";
    private final Logger log = LoggerFactory.getLogger(DivisionExceptionController.class);

    @Autowired
    private CaseInfoService caseInfoService;

    //分案异常列表
    @PostMapping("/getDivisionExceptionList")
    @ApiOperation(value = "分案异常案件分页查询", notes = "分案异常案件分页查询")
    public ResponseEntity<Page<DivisionExceptionModel>> getCollectionQueue(@RequestBody DivisionExceptionRequest divisionExceptionRequest ) {

        Page<DivisionExceptionModel> divisionExceptionModels = null;
        try {
            List<DivisionExceptionModel> divisionExceptionList = caseInfoService.getDivisionExceptionList(divisionExceptionRequest);
            Pageable pageable = new PageRequest(divisionExceptionRequest.getPage(), divisionExceptionRequest.getSize());
            divisionExceptionModels = new PageImpl<DivisionExceptionModel>(
                    divisionExceptionList.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList()), pageable, divisionExceptionList.size());
        } catch (Exception e) {
            log.error("查询失败:{}",e);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询数据成功", ENTITY_NAME)).body(divisionExceptionModels);
    }

}
