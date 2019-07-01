package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.CaseInfoDistributed;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.entity.response.CaseInfoDistributedListResponse;
import cn.fintecher.pangolin.report.mapper.CaseInfoDistributeMapper;
import cn.fintecher.pangolin.report.model.CaseInfoDistributeQueryParams;
import cn.fintecher.pangolin.report.model.CaseInfoExcelModel;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.hsjry.lang.common.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 待分配案件查询操作
 * Created by sunyanping on 2017/11/6.
 */

@RestController
@RequestMapping("/api/caseInfoDistributeController")
@Api(description = "待分配案件查询操作")
public class CaseInfoDistributeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CaseInfoDistributeController.class);

    @Inject
    private CaseInfoDistributeMapper caseInfoDistributeMapper;

    @PostMapping(value = "/findCaseInfoDistribute")
    @ApiOperation(notes = "多条件查询待分配案件", value = "多条件查询待分配案件")
    public ResponseEntity<Page<CaseInfoDistributedListResponse>> findCaseInfoDistribute(@RequestBody CaseInfoDistributeQueryParams params,
                                                                                        @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        logger.debug("REST request to findCaseInfoDistribute");
        try {
            User user = getUserByToken(token);
            if (Objects.nonNull(user.getCompanyCode())) {
                params.setCompanyCode(user.getCompanyCode());
            }
            if (!Strings.isNullOrEmpty(params.getStartOverDueDate()) && !Strings.isNullOrEmpty(params.getEndOverDueDate())){
                params.setStartOverDueDate(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.getStartOverDueDate()),1)));
                params.setEndOverDueDate(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(DateUtil.deleteDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.getEndOverDueDate()),1)));
            }
            PageHelper.startPage(params.getPage() + 1, params.getSize());
            List<CaseInfoDistributedListResponse> caseInfoDistributes = caseInfoDistributeMapper.findCaseInfoDistribute(params);
            PageInfo<CaseInfoDistributedListResponse> pageInfo = new PageInfo<>(caseInfoDistributes);
            Pageable pageable = new PageRequest(params.getPage(), params.getSize());
            Page<CaseInfoDistributedListResponse> page = new PageImpl<>(caseInfoDistributes, pageable, pageInfo.getTotal());
            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "查询失败")).body(null);
        }
    }
    @GetMapping(value = "/getCaseInfoDistributedDetails")
    @ApiOperation(notes = "案件导入待分配案件详情",value = "案件导入待分配案件详情")
    public ResponseEntity<CaseInfoExcelModel> getCaseInfoDistributedDetails(@RequestParam(value = "id") String id,
                                                                            @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        logger.debug("REST request to getCaseInfoDistributedDetails");
        try {
            User user =getUserByToken(token);
            CaseInfoExcelModel caseInfoExcelModel = caseInfoDistributeMapper.getCaseInfoDistributedDetails(id);
           /* List<PersonalContact> personalContacts = caseInfoDistributeMapper.getPersonalContact(caseInfoExcelModel.getPersonalId());
            for(int i=0; i<personalContacts.size(); i++){
                if(i==0){
                    caseInfoExcelModel.setContactName1(personalContacts.get(i).getName());
                }

                }*/

            return ResponseEntity.ok().body(caseInfoExcelModel);
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("","","查询失败")).body(null);
        }
    }
}
