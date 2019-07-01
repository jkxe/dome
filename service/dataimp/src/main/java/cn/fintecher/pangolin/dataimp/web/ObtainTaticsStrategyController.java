package cn.fintecher.pangolin.dataimp.web;

import cn.fintecher.pangolin.dataimp.entity.CollectionQueue;
import cn.fintecher.pangolin.dataimp.entity.ObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.entity.QCollectionQueue;
import cn.fintecher.pangolin.dataimp.entity.QObtainTaticsStrategy;
import cn.fintecher.pangolin.dataimp.mapper.CaseInfoDistributedMapper;
import cn.fintecher.pangolin.dataimp.model.request.CollectionQueueRequest;
import cn.fintecher.pangolin.dataimp.model.request.ObtainTaticsStrategyRequest;
import cn.fintecher.pangolin.dataimp.repository.CollectionQueueRepository;
import cn.fintecher.pangolin.dataimp.repository.ObtainTaticsStrategyRepository;
import cn.fintecher.pangolin.dataimp.service.ObtainTaticsStrategyService;
import cn.fintecher.pangolin.dataimp.util.UserUtils;
import cn.fintecher.pangolin.entity.User;
import cn.fintecher.pangolin.entity.strategy.DivisionCaseParamModel;
import cn.fintecher.pangolin.entity.util.Constants;
import cn.fintecher.pangolin.util.BeanMapperUtil;
import cn.fintecher.pangolin.web.HeaderUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author frank  braveheart1115@163.com
 * @Description:  案件数据获取策略 即将所有的数据按照该规则过后分配到不同的案件池，如内催案件，特殊案件，停催案件 等。
 * @Package cn.fintecher.pangolin.dataimp.web
 * @ClassName: cn.fintecher.pangolin.dataimp.web.ObtainTaticsStrategyController
 * @date 2018年06月13日 14:49
 */
@RestController
@RequestMapping("/api/obtainTaticsStrategyController")
@Api(value = "ObtainTaticsStrategyController", description = "案件数据获取策略")
@Validated
public class ObtainTaticsStrategyController {

    private final Logger log = LoggerFactory.getLogger(ObtainTaticsStrategyController.class);
    private static final String ENTITY_NAME = "obtainTaticsStrategy";


    @Autowired
    private ObtainTaticsStrategyService obtainTaticsStrategyService;

    @Autowired
    private ObtainTaticsStrategyRepository obtainTaticsStrategyRepository;

    @Autowired
    private CollectionQueueRepository collectionQueueRepository;

    @Autowired
    private CaseInfoDistributedMapper caseInfoDistributedMapper;

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 添加/修改分案策略，要求每个组织只能有一个可用的策略。
     * @param obtainTaticsStrategyRequest
     * @param token
     * @return
     */
    @PostMapping("/addObtainTaticsStrategy")
    @ApiOperation(value = "新增/修改分案策略", notes = "新增/修改分案策略")
    public ResponseEntity addDivisionalStrategy(@Valid @RequestBody ObtainTaticsStrategyRequest obtainTaticsStrategyRequest,
                                          @RequestHeader(value = "X-UserToken") @ApiParam("用户Token") String token) {
        ResponseEntity<User> userResponseEntity = UserUtils.checkUser(token, restTemplate);
        User user = userResponseEntity.getBody();
        if (Objects.isNull(user.getCompanyCode())) {
            if (StringUtils.isBlank(obtainTaticsStrategyRequest.getCompanyCode())) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "请选择公司")).body(null);
            }
            user.setCompanyCode(obtainTaticsStrategyRequest.getCompanyCode());
        }
        String message = null;
        try {
            message = obtainTaticsStrategyService.addObtainTaticsStrategy(obtainTaticsStrategyRequest, token, user);
            if (StringUtils.isEmpty(message)){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("保存成功", ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保存失败" + ":" + message,ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保存失败",ENTITY_NAME)).body(null);
        }
    }
    /**
     * 添加/修改催收队列
     * @param collectionQueueRequest
     * @param token
     * @return
     */
    @PostMapping("/addCollectionQueue")
    @ApiOperation(value = "新增/修改催收队列", notes = "新增/修改催收队列")
    public ResponseEntity addCollectionQueue(@Valid @RequestBody CollectionQueueRequest collectionQueueRequest,
                                          @RequestHeader(value = "X-UserToken") @ApiParam("用户Token") String token) {
        ResponseEntity<User> userResponseEntity = UserUtils.checkUser(token, restTemplate);
        User user = userResponseEntity.getBody();
        String message = "";
        try {
            if (collectionQueueRequest.getStatus().equals(CollectionQueue.Status.CLOSED.getValue())){
                Iterable<ObtainTaticsStrategy> all = obtainTaticsStrategyRepository.findAll(QObtainTaticsStrategy.obtainTaticsStrategy.collectionQueues.contains(collectionQueueRequest.getId()));
                if (all.iterator().hasNext()){
                    return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("当前队列已关联分案策略,请取消和分案策略的关联后再关闭",ENTITY_NAME)).body(null);
                }
            }
            CollectionQueue collectionQueue = BeanMapperUtil.objConvert(collectionQueueRequest, CollectionQueue.class);
            message = obtainTaticsStrategyService.addCollectionQueue(collectionQueue, token, user);
            if (StringUtils.isEmpty(message)){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert("保存成功", ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保存失败" + ":" + message,ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保存失败" + ":" + message,ENTITY_NAME)).body(null);
        }
    }

    @PostMapping("/isInStrategy")
    @ApiOperation(value = "根据用户id查询是否已关联到催收策略", notes = "根据用户id查询是否已关联到催收策略")
    public ResponseEntity<Boolean> isInStrategy(@RequestBody Map map) {
        Boolean isInStrategy = null;
        try {
            String userId = (String) map.get("userId");
            if (StringUtils.isBlank(userId)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("用户id不能为空",ENTITY_NAME)).body(null);
            }
            QObtainTaticsStrategy qObtqainTaticsStrategy = QObtainTaticsStrategy.obtainTaticsStrategy;
            Iterable<ObtainTaticsStrategy> all = obtainTaticsStrategyRepository.findAll(qObtqainTaticsStrategy.users.contains(userId));
            Iterator<ObtainTaticsStrategy> iterator = all.iterator();
            if (iterator.hasNext()){
                isInStrategy = true;
            }else {
                isInStrategy = false;
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询成功", ENTITY_NAME)).body(isInStrategy);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("","")).body(null);
        }
    }


    @GetMapping("/selectProductSeries")
    @ApiOperation(value = "查询系列的id与名称，返回给前台作为下拉列表。", notes = "查询系列的id与名称，返回给前台作为下拉列表。")
    public ResponseEntity selectProductSeries(){
        return ResponseEntity.ok().body(caseInfoDistributedMapper.selectProductSeries());
    }



    @GetMapping("/getObtainTaticsStrategy")
    @ApiOperation(value = "策略分页查询", notes = "策略分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<ObtainTaticsStrategy>> getObtainTaticsStrategy(@QuerydslPredicate(root = ObtainTaticsStrategy.class) Predicate predicate,
                                                                    @ApiIgnore Pageable pageable,
                                                                    @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                                      @RequestParam(required = false) @ApiParam("策略名称") String name,
                                                                      @RequestParam(required = false) @ApiParam("案件状态") String productType,
                                                                      @RequestParam(required = false) @ApiParam("案件类型") Integer caseType,
                                                                      @RequestParam(required = false) @ApiParam("案件状态") Integer status,
                                                                    @RequestParam(value = "companyCode", required = false) @ApiParam("公司Code") String companyCode) {
        try {
            restTemplate.getForEntity(Constants.USERTOKEN_SERVICE_URL.concat(token), User.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(e.getMessage(), "user", ENTITY_NAME)).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder(predicate);
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Page<ObtainTaticsStrategy> StrategyPage = obtainTaticsStrategyRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询数据成功", ENTITY_NAME)).body(StrategyPage);

    }

    @GetMapping("/getAllCollectionQueue")
    @ApiOperation(value = "查询所有催收队列", notes = "查询所有催收队列")
    public ResponseEntity<List<CollectionQueue>> getAllCollectionQueue(@RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token) {
        try {
            restTemplate.getForEntity(Constants.USERTOKEN_SERVICE_URL.concat(token), User.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(e.getMessage(), "user", ENTITY_NAME)).body(null);
        }
        Iterable<CollectionQueue> all = collectionQueueRepository.findAll(QCollectionQueue.collectionQueue.status.eq(CollectionQueue.Status.OPEN.getValue()));
        List<CollectionQueue> StrategyPage = IteratorUtils.toList(all.iterator());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询数据成功", ENTITY_NAME)).body(StrategyPage);

    }

    @GetMapping("/getCollectionQueue")
    @ApiOperation(value = "催收队列分页查询", notes = "催收队列分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",value = "页数 (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",value = "每页大小."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",value = "依据什么排序: 属性名(,asc|desc). ")
    })
    public ResponseEntity<Page<CollectionQueue>> getCollectionQueue(@QuerydslPredicate(root = CollectionQueue.class) Predicate predicate,
                                                                    @ApiIgnore Pageable pageable,
                                                                    @RequestHeader(value = "X-UserToken") @ApiParam("操作者的Token") String token,
                                                                    @RequestParam(required = false) @ApiParam("队列编号") String code,
                                                                    @RequestParam(required = false) @ApiParam("队列名称") String name,
                                                                    @RequestParam(required = false) @ApiParam("队列状态(504:有效,505:无效)") Integer status) {
        try {
            restTemplate.getForEntity(Constants.USERTOKEN_SERVICE_URL.concat(token), User.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(e.getMessage(), "user", ENTITY_NAME)).body(null);
        }
        BooleanBuilder builder = new BooleanBuilder();
        if(Objects.nonNull(name)) builder.and(QCollectionQueue.collectionQueue.name.like(name.concat("%")));//   队列名称模糊
        if(Objects.nonNull(code))  builder.and(QCollectionQueue.collectionQueue.code.like(code.concat("%")));
        if(Objects.nonNull(status))  builder.and(QCollectionQueue.collectionQueue.status.eq(status));
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Page<CollectionQueue> StrategyPage = collectionQueueRepository.findAll(builder, pageable);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询数据成功", ENTITY_NAME)).body(StrategyPage);
    }


    @GetMapping("/deleteObtainTaticsStrategy")
    @ApiOperation(value = "删除案件数据获取策略", notes = "删除案件数据获取策略")
    public ResponseEntity deleteObtainTaticsStrategy(
            @RequestParam @ApiParam("策略ID") String id,
            @RequestHeader(value = "X-UserToken") String token) {
        try {
            obtainTaticsStrategyRepository.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("删除成功", "")).body(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "", "删除失败")).body(null);
        }
    }


    /**
     * 每天执行的分案策略，针对逾期天数为T+3的案件.
     * 1 每天分案针对电催中逾期期数为1，天数为3的案件。
     * 2 委外的案件不需要分配，只需要进行回收操作。当委外案件的结束时间与当前时间相等，就将委外的案件进行回收。
     *   委外案件回收，当前日期等于委外结束时间就回收案件。
     */
    @PostMapping("/everyDayDivisionCase")
    @ApiOperation(value = "执行每天的分案策略", notes = "执行每天的分案策略")
    public ResponseEntity everyDayDivisionCase(
            @RequestBody(required = false) @ApiParam("公司code码") DivisionCaseParamModel divisionCaseParamModel){
        try {
            obtainTaticsStrategyService.everyDayDivisionCase(divisionCaseParamModel.getCompanyCode());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("执行月初的分案策略成功", "")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "执行月初的分案策略失败")).body(null);
        }
    }

    /**
     * 月初的分案策略，针对除过逾期天数为T+3的案件.
     * 案件的来源是 case_info 表。
     * 对于月初分案的有电催的和外访的，通过 internalType 区分，电催:15,外访:16。
     * 电催的分配到人，外访的分配到组。
     * @return
     */
    @PostMapping("/monthEarlyDivisionCase")
    @ApiOperation(value = "月初的分案策略", notes = "月初的分案策略")
    public ResponseEntity monthEarlyDivisionCase(
            @RequestBody(required = false) @ApiParam("公司code码") DivisionCaseParamModel divisionCaseParamModel){
        try {
            obtainTaticsStrategyService.monthEarlyDivisionCase(divisionCaseParamModel.getCompanyCode());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("执行月初的分案策略成功", "")).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("", "", "执行月初的分案策略失败")).body(null);
        }
    }


    /**
     * 该方法是为了在测试的只启用某个策略，在MongoDB中修改太麻烦才提供的。可以批量修改策略的状态
     * @param companyCode
     * @param status
     * @return
     */
    @GetMapping("/updateStatus")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public ResponseEntity updateStatus(@RequestParam(required = true) @ApiParam("公司code码") String companyCode,
                                       @RequestParam(required = true) @ApiParam("状态 504 开启，505 关闭") Integer status){
        List<ObtainTaticsStrategy> list=obtainTaticsStrategyRepository.findAll();
        ObtainTaticsStrategy strategy=null;
        for (int i = 0; i < list.size(); i++) {
            strategy= list.get(i);
            strategy.setStatus(status);
            obtainTaticsStrategyRepository.save(strategy);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("修改状态成功", "")).body(null);
    }

}
