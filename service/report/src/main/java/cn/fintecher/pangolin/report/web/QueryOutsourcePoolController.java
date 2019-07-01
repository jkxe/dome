package cn.fintecher.pangolin.report.web;

import cn.fintecher.pangolin.entity.CaseInfo;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.model.OutsourcePoolBatchModel;
import cn.fintecher.pangolin.report.mapper.OutsourcePoolDao;
import cn.fintecher.pangolin.report.mapper.QueryOutsourcePoolMapper;
import cn.fintecher.pangolin.report.model.*;
import cn.fintecher.pangolin.report.util.SortUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : huyanmin
 * @Description : 委外催收
 * @Date : 2017/9/25
 */
@RestController
@RequestMapping("/api/QueryOutsourcePoolController")
@Api(value = "QueryOutsourcePoolController", description = "委外催收中查询")
public class QueryOutsourcePoolController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(QueryOutsourcePoolController.class);


    @Inject
    QueryOutsourcePoolMapper queryOutsourcePoolMapper;
    @Autowired
    private OutsourcePoolDao outsourcePoolDao;

    @GetMapping("/queryAllOutsourcePool")
    @ApiOperation(value = "委外催收中查询", notes = "委外催收中查询")
    public ResponseEntity<OutSourcePoolModel> queryAllOutsourcePool(QueryOutsourcePoolParams queryOutsourcePoolParams,
                                                                    @RequestHeader(value = "X-UserToken") String token) {

        try {
            User tokenUser = getUserByToken(token);
            if (Objects.isNull(tokenUser)) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("获取不到登录人信息", "", "获取不到登录人信息")).body(null);
            }
            PageHelper.startPage(queryOutsourcePoolParams.getPage() + 1, queryOutsourcePoolParams.getSize());
            if(Objects.nonNull(tokenUser.getCompanyCode())) {
                queryOutsourcePoolParams.setCompanyCode(tokenUser.getCompanyCode());
            }
            List<QueryOutsourcePool> contents = new ArrayList<>();
            List<QueryOutsourcePool> content = new ArrayList<>();
            //受托放2 批次号1
            if (queryOutsourcePoolParams.getType()==1){
                contents = queryOutsourcePoolMapper.getAllOutSourcePoolByBatchNumber(queryOutsourcePoolParams);
                if (contents.size() > 0){
                    for (int i = 0; i < contents.size(); i++) {
                        // 计算成功结案的案件数和成功比率
                        QueryOutsourcePool queryOutsourcePool = contents.get(i);
                        BatchAndNameParams batchAndNameParams = new BatchAndNameParams();
                        batchAndNameParams.setBatchNumber(queryOutsourcePool.getBatchNumber());
                        BatchAndNameModel countOfEnd = queryOutsourcePoolMapper.findCountOfEnd(batchAndNameParams);
                        if(Objects.nonNull(countOfEnd)){
                            if (countOfEnd.getOutcaseClosedCount().compareTo(new BigInteger("0")) > 0){
                                queryOutsourcePool.setOutcaseClosedCount(countOfEnd.getOutcaseClosedCount().toString());
                                queryOutsourcePool.setOutcaseCountRate(new BigDecimal(countOfEnd.getOutcaseClosedCount()).divide(new BigDecimal(queryOutsourcePool.getOutcaseTotalCount().add(countOfEnd.getOutcaseClosedCount())),2,RoundingMode.HALF_UP).toString());
                            }else {
                                queryOutsourcePool.setOutcaseClosedCount("0");
                                queryOutsourcePool.setOutcaseCountRate("0.00");
                            }
                            if (Objects.nonNull(countOfEnd.getOutcaseClosedAmt()) && countOfEnd.getOutcaseClosedAmt().compareTo(new BigDecimal("0")) > 0){
                                queryOutsourcePool.setOutcaseClosedAmt(countOfEnd.getOutcaseClosedAmt());
                                queryOutsourcePool.setOutcaseAmtRate(countOfEnd.getOutcaseClosedAmt().divide(queryOutsourcePool.getOverdueAmount().add(countOfEnd.getOutcaseClosedAmt()), 2, RoundingMode.HALF_UP).toString());
                            }else {
                                queryOutsourcePool.setOutcaseClosedAmt(new BigDecimal("0"));
                                queryOutsourcePool.setOutcaseAmtRate("0.00");
                            }
                        }
                        content.add(queryOutsourcePool);
                    }
                }
            }else {
                contents = queryOutsourcePoolMapper.getAllOutSourceByOutsName(queryOutsourcePoolParams);
                if (contents.size() > 0){
                    for (int i = 0; i < contents.size(); i++) {
                        // 计算成功结案的案件数和成功比率
                        QueryOutsourcePool queryOutsourcePool = contents.get(i);
                        BatchAndNameParams batchAndNameParams = new BatchAndNameParams();
                        batchAndNameParams.setOutsId(queryOutsourcePool.getOutsId());
                        BatchAndNameModel countOfEnd = queryOutsourcePoolMapper.findCountOfEnd(batchAndNameParams);
                        if(Objects.nonNull(countOfEnd)){
                            if (countOfEnd.getOutcaseClosedCount().compareTo(new BigInteger("0")) > 0){
                                queryOutsourcePool.setOutcaseClosedCount(countOfEnd.getOutcaseClosedCount().toString());
                                queryOutsourcePool.setOutcaseCountRate(new BigDecimal(countOfEnd.getOutcaseClosedCount()).divide(new BigDecimal(queryOutsourcePool.getOutcaseTotalCount().add(countOfEnd.getOutcaseClosedCount())),2,RoundingMode.HALF_UP).toString());
                            }else {
                                queryOutsourcePool.setOutcaseClosedCount("0");
                                queryOutsourcePool.setOutcaseCountRate("0.00");
                            }
                            if (Objects.nonNull(countOfEnd.getOutcaseClosedAmt()) && countOfEnd.getOutcaseClosedAmt().compareTo(new BigDecimal("0")) > 0){
                                queryOutsourcePool.setOutcaseClosedAmt(countOfEnd.getOutcaseClosedAmt());
                                queryOutsourcePool.setOutcaseAmtRate(countOfEnd.getOutcaseClosedAmt().divide(queryOutsourcePool.getOverdueAmount().add(countOfEnd.getOutcaseClosedAmt()), 2, RoundingMode.HALF_UP).toString());
                            }else {
                                queryOutsourcePool.setOutcaseClosedAmt(new BigDecimal("0"));
                                queryOutsourcePool.setOutcaseAmtRate("0.00");
                            }
                        }
                        content.add(queryOutsourcePool);

                    }
                }
            }


            PageInfo pageInfo = new PageInfo(content);
            OutSourcePoolModel outSourcePoolModel = new OutSourcePoolModel();
            outSourcePoolModel.setContent(content);
            outSourcePoolModel.setTotalPages(pageInfo.getPages());
            outSourcePoolModel.setTotalElements(pageInfo.getTotal());
            return ResponseEntity.ok().body(outSourcePoolModel);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", "案件查询失败")).body(null);
        }

    }


    @GetMapping("/queryDistribute")
    @ApiOperation(value = "委外待分配，结案查询", notes = "委外待分配，结案查询")
    public ResponseEntity<Page<OutSourceDistModel>> queryDistribute(QueryOutsourcePoolParams queryOutsourcePoolParams,
                                                                    @RequestHeader(value = "X-UserToken") String token) {

        User tokenUser = null;
        try {
            tokenUser = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
        try {
            List<Integer> CollectionStatusList = Arrays.asList(CaseInfo.CollectionStatus.CASE_OVER.getValue(),CaseInfo.CollectionStatus.WAIT_FOR_DIS.getValue());
            if (!CollectionStatusList.contains(queryOutsourcePoolParams.getCollectionStatus())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", "未指定状态")).body(null);
            }
            // 配置公司权限
            if (!Objects.equals(tokenUser.getUserName(), Constants.ADMIN_USER_NAME)) {
                queryOutsourcePoolParams.setCompanyCode(tokenUser.getCompanyCode());
            }
            queryOutsourcePoolParams.setSort(SortUtil.convertSql(OutSourceDistModel.class, queryOutsourcePoolParams.getSort()));
            PageHelper.startPage(queryOutsourcePoolParams.getPage() + 1, queryOutsourcePoolParams.getSize());
            List<OutSourceDistModel> outSourceDistModels = queryOutsourcePoolMapper.queryDistribute(queryOutsourcePoolParams);
            PageInfo<OutSourceDistModel> pageInfo = new PageInfo<>(outSourceDistModels);
            Pageable pageable = new PageRequest(queryOutsourcePoolParams.getPage(), queryOutsourcePoolParams.getSize());
            Page<OutSourceDistModel> resultPage = new PageImpl<>(pageInfo.getList(), pageable, pageInfo.getTotal());
            return ResponseEntity.ok().body(resultPage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", "案件查询失败")).body(null);
        }

    }

    @GetMapping("/queryReturn")
    @ApiOperation(value = "委外回收案件查询", notes = "委外回收案件查询")
    public ResponseEntity<Page<OutSourceDistModel>> queryReturn(QueryOutsourcePoolParams queryOutsourcePoolParams,
                                                                @RequestHeader(value = "X-UserToken") String token) {

        User tokenUser = null;
        try {
            tokenUser = getUserByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
        try {
            // 配置公司权限
            if (!Objects.equals(tokenUser.getUserName(), Constants.ADMIN_USER_NAME)) {
                queryOutsourcePoolParams.setCompanyCode(tokenUser.getCompanyCode());
            }
           /* if(  null !=queryOutsourcePoolParams.getCaseFollowInTime() && queryOutsourcePoolParams.getCaseFollowInTime()!=""){
                String caseFollowInTime = queryOutsourcePoolParams.getCaseFollowInTime();
                SimpleDateFormat myFormatter = new SimpleDateFormat( "yyyy-MM-dd");
                Date date = myFormatter.parse(caseFollowInTime);
                Date date1 = myFormatter.parse("1970-01-01");
                Long i = date.getTime() - date1.getTime();
                String string = i.toString();
                queryOutsourcePoolParams.setCaseFollowInTime(string);
            }*/
            queryOutsourcePoolParams.setSort(SortUtil.convertSql(OutSourceDistModel.class, queryOutsourcePoolParams.getSort()));
            PageHelper.startPage(queryOutsourcePoolParams.getPage() + 1, queryOutsourcePoolParams.getSize());
            List<OutSourceDistModel> outSourceDistModels = queryOutsourcePoolMapper.queryReturn(queryOutsourcePoolParams);
            // 共债案件合并
//            List<OutSourceDistModel> sourceDistModels = totalOutSourceDebtCase(outSourceDistModels);
            PageInfo<OutSourceDistModel> pageInfo = new PageInfo<>(outSourceDistModels);
            Pageable pageable = new PageRequest(queryOutsourcePoolParams.getPage(), queryOutsourcePoolParams.getSize());
            Page<OutSourceDistModel> resultPage = new PageImpl<>(pageInfo.getList(), pageable, pageInfo.getTotal());
            return ResponseEntity.ok().body(resultPage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryAllOutsourcePool", "案件查询失败")).body(null);
        }

    }

    @GetMapping("/queryCleanUp")
    @ApiOperation(value = "委外归C案件查询",notes = "委外归C案件查询")
    public ResponseEntity<Page<OutSourceDistModel>> queryCleanUp(QueryOutsourcePoolParams queryOutsourcePoolParams,
                                                                 @RequestHeader(value = "X-UserToken") String token){
        User tokenUser = null;
        try {
            tokenUser = getUserByToken(token);
            // 配置公司权限
            if (!Objects.equals(tokenUser.getUserName(), Constants.ADMIN_USER_NAME)) {
                queryOutsourcePoolParams.setCompanyCode(tokenUser.getCompanyCode());
            }
            queryOutsourcePoolParams.setSort(SortUtil.convertSql(OutSourceDistModel.class, queryOutsourcePoolParams.getSort()));
            PageHelper.startPage(queryOutsourcePoolParams.getPage() + 1, queryOutsourcePoolParams.getSize());
            List<OutSourceDistModel> outSourceDistModels = queryOutsourcePoolMapper.queryCleanUpcaseInfo(queryOutsourcePoolParams);
            PageInfo<OutSourceDistModel> pageInfo = new PageInfo<>(outSourceDistModels);
            Pageable pageable = new PageRequest(queryOutsourcePoolParams.getPage(), queryOutsourcePoolParams.getSize());
            Page<OutSourceDistModel> resultPage = new PageImpl<>(pageInfo.getList(), pageable, pageInfo.getTotal());
            return ResponseEntity.ok().body(resultPage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryCleanUp", "案件查询失败")).body(null);
        }
    }

    @GetMapping("/getOutSourceCaseByBatchnum")
    @ApiOperation(value = "按委托方或批次号查看委外案件",notes = "按委托方或批次号查看委外案件")
    public ResponseEntity<Page<OutsourcePoolBatchModel>> getOutSourceCaseByBatchnum(QueryCaseInfoParams queryCaseInfoParams,
                                                                                    @ApiIgnore Pageable pageable){
        try {
            List<OutsourcePoolBatchModel> list = outsourcePoolDao.getOutSourceCaseByBatchnum(queryCaseInfoParams);
            List<OutsourcePoolBatchModel> modeList = list.stream().skip(pageable.getPageNumber() * pageable.getPageSize()).limit(pageable.getPageSize()).collect(Collectors.toList());
            Page<OutsourcePoolBatchModel> page = new PageImpl<>(modeList,pageable,list.size());

            return ResponseEntity.ok().body(page);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("QueryOutsourcePoolController", "queryCleanUp", Constants.SYS_EXCEPTION_NOSESSION)).body(null);
        }
    }

    public List<OutSourceDistModel> totalOutSourceDebtCase(List<OutSourceDistModel> list){
        // 共债案件合并的公共方法
        if (Objects.isNull(list)){
            // 查询出来的数据是空的
            return list;
        }
        List<OutSourceDistModel> list1 = new ArrayList<>();
        List<String> caseNums = new ArrayList<>();
        for  ( int  i  =   0 ; i  <  list.size() ; i ++ )  {
             if(null==list.get(i).getCaseNumber()){
                 throw new RuntimeException("案件查询失败");
             }
            if (caseNums.contains(list.get(i).getCaseNumber())){
                continue;
            }
            caseNums.add(list.get(i).getCaseNumber());
            OutSourceDistModel outSourceDistModel = list.get(i);
            for (int  j  =   i+1 ; j  <  list.size() ; j ++ ){
                if  (outSourceDistModel.getCaseNumber().equals(list.get(j).getCaseNumber()))  {
                    // 合并逾期总金额
                    outSourceDistModel.setOverdueAmount(outSourceDistModel.getOverdueAmount().add(list.get(j).getOverdueAmount()));
                    // 合并账户余额
                    outSourceDistModel.setAccountBalance(outSourceDistModel.getAccountBalance().add(list.get(j).getAccountBalance()));
                }
            }
            list1.add(outSourceDistModel);
        }
        return list1;
    }

}
