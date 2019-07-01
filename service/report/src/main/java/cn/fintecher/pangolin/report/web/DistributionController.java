package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.CaseTurnRecord;
import cn.fintecher.pangolin.report.entity.CaseInfo;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.report.mapper.CaseInfoMapper;
import cn.fintecher.pangolin.report.mapper.CaseTurnRecordMapper;
import cn.fintecher.pangolin.report.model.AreaCaseNumberModel;
import cn.fintecher.pangolin.report.model.CaseListParam;
import cn.fintecher.pangolin.report.model.OutSourceNumberModel;
import cn.fintecher.pangolin.report.model.distribution.DistributionCaseParam;
import cn.fintecher.pangolin.report.model.distribution.QueryDistributionCaseInfoModel;
import cn.fintecher.pangolin.report.service.DistributionService;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/distributionController")
@Api(description = "案件预分配")
public class DistributionController extends BaseController{

    Logger log = LoggerFactory.getLogger(DistributionController.class);

    @Autowired
    private DistributionService distributionService;

    /**
     * 晚间跑批将委外待分配案件分配保存临时表
     * @return
     */
    @GetMapping("/predistributionWhip")
    @ApiOperation(value = "晚间跑批将委外待分配案件分配保存临时表",notes = "晚间跑批将委外待分配案件分配保存临时表")
    public ResponseEntity<Void> distributionWhip(){
        distributionService.distributionCases();
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("预分配成功", "predistributionWhip")).body(null);
    }

    @PostMapping("/savePredistributionOutSource")
    @ApiOperation(value = "确定委外待分配案件进行分配",notes = "确定委外待分配案件进行分配")
    public ResponseEntity<Void> savePredistributionOutSource(@RequestBody CaseListParam caseListParam,
                                                             @RequestHeader(value = "X-UserToken") String token){
        try{
            User user = getUserByToken(token);
            distributionService.savePredistributionOutSource(caseListParam.getCaseIdList(),user);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("委外分配成功", "savePredistributionOutSource")).body(null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("savePredistributionOutSource", "distributionController", e.getMessage())).body(null);
        }
    }

//    @PostMapping("/getCaseInfo")
//    @ApiOperation(value = "获取委外待分配案件展示",notes = "获取委外待分配案件展示")
//    public void getCaseInfo(@RequestBody CaseTurnRecord caseTurnRecord){
//        caseTurnRecordMapper.save(caseTurnRecord);
//    }

    @GetMapping("/getOutSourceCaseInfo")
    @ApiOperation(value = "获取委外待分配案件展示",notes = "获取委外待分配案件展示")
    public ResponseEntity<List<OutSourceNumberModel>> getOutSourceCaseInfo(){
        try {
            List<OutSourceNumberModel> list = distributionService.selectDistributionResults();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功","")).body(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("查询失败","")).body(null);
        }

    }

    @GetMapping("/getOutSourceCaseInfoDetail")
    @ApiOperation(value = "获取委外待分配案件详细信息",notes = "获取委外待分配案件详细信息")
    public ResponseEntity<List<AreaCaseNumberModel>> getOutSourceCaseInfoDetail(@ApiParam(value = "outId", required = true)                                                                                     @RequestParam String outId){
        try {
            List<AreaCaseNumberModel> list = distributionService.getOutSourceCaseInfoDetail(outId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功","")).body(list);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询失败","")).body(null);
        }


    }

    @GetMapping("/queryOutSoutPoolCase")
    @ApiOperation(value = "获取预分配好的案件信息",notes = "获取预分配好的案件信息")
    public ResponseEntity<Page<QueryDistributionCaseInfoModel>> queryOutSoutPoolCase(DistributionCaseParam distributionCaseParam){
        List<QueryDistributionCaseInfoModel> list = distributionService.queryDistributionCaseAll(distributionCaseParam);
        Pageable pageable = new PageRequest(distributionCaseParam.getPage(), distributionCaseParam.getSize());
        List<QueryDistributionCaseInfoModel> lister = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
        Page<QueryDistributionCaseInfoModel> page = new PageImpl<>(lister, pageable, list.size());
        return ResponseEntity.ok().body(page);
    }
}
