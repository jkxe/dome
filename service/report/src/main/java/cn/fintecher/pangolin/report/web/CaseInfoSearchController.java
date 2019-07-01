package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.report.mapper.CaseSearchMapper;
import cn.fintecher.pangolin.report.model.CaseSearchRequest;
import cn.fintecher.pangolin.report.model.CaseSearchResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by sunyanping on 2019/5/15
 */
@RequestMapping("/api/caseInfoSearchController")
@RestController
public class CaseInfoSearchController extends BaseController {

    @Autowired
    private CaseSearchMapper caseSearchMapper;

    @GetMapping("/caseSearch")
    @ApiOperation(value = "案件查找分页查询", notes = "案件查找分页查询")
    public ResponseEntity<Page<CaseSearchResponse>> caseSearch(@ApiIgnore Pageable pageable,
                                                               @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                               CaseSearchRequest request) throws Exception {
        getUserByToken(token);
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        List<CaseSearchResponse> caseSearchResponses = caseSearchMapper.caseSearch(request);
        PageInfo<CaseSearchResponse> pageInfo = new PageInfo<>(caseSearchResponses);
        Page<CaseSearchResponse> page = new PageImpl<>(caseSearchResponses, pageable, pageInfo.getTotal());
        return ResponseEntity.ok(page);
    }
}
